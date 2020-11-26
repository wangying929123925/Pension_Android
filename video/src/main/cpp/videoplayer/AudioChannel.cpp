//
// Created by Administrator on 2019/11/26.
//

#include "AudioChannel.h"
#include "../Log.h"

extern "C"{
#include <libavutil/time.h>
}




void *audioPlay_t(void *args) {
    AudioChannel *audioChannel = static_cast<AudioChannel *>(args);
    audioChannel->_play();
    return 0;
}

void *audioDecode_t(void *args) {
    AudioChannel *audioChannel = static_cast<AudioChannel *>(args);
    audioChannel->decode();
    return 0;
}

AudioChannel::AudioChannel(int channelId, JavaCallHelper *helper, AVCodecContext *avCodecContext,
                           const AVRational &base) : BaseChannel(channelId, helper, avCodecContext,
                                                                 base) {
    //2 双声道
    out_channls = av_get_channel_layout_nb_channels(AV_CH_LAYOUT_STEREO);
    //采樣位 2字節
    out_sampleSize = av_get_bytes_per_sample(AV_SAMPLE_FMT_S16);
    //采樣率
    int out_sampleRate = 44100;

    //計算轉換后數據的最大字節數 这个是要传给转换器的接收者的容量
    bufferCount = out_sampleRate * out_sampleSize * out_channls;//采样率*样本大小*声道
    buffer = static_cast<uint8_t *>(malloc(out_sampleRate * out_sampleSize * out_channls));

}

AudioChannel::~AudioChannel() {
    free(buffer);
    buffer = 0;
}

void AudioChannel::play() {//纯虚函数，一定要实现  播放这里使用OpenSESL
//设置转换器
    swrContext = swr_alloc_set_opts(0, AV_CH_LAYOUT_STEREO, AV_SAMPLE_FMT_S16, 44100,
                                    avCodecContext->channel_layout,
                                    avCodecContext->sample_fmt,
                                    avCodecContext->sample_rate,
                                    0, 0);
    swr_init(swrContext);

    isPlaying = 1;
    setEnable(1);

    //解码
    pthread_create(&audioDecodeTask, 0, audioDecode_t, this);
    //播放
    pthread_create(&audioPlayTask, 0, audioPlay_t, this);
}


void AudioChannel::stop() {
    isPlaying = 0;
    helper = 0;
    setEnable(0);
    pthread_join(audioDecodeTask, 0);
    pthread_join(audioPlayTask, 0);

    _releaseOpenSL();//释放掉openSL
    if (swrContext) {
        swr_free(&swrContext);
        swrContext = 0;
    }
}

void AudioChannel::decode() {
    AVPacket *packet = 0;
    while (isPlaying) {
        int ret = pkt_queue.deQueue(packet);
        if (!isPlaying) {
            break;
        }
        if (!ret) {
            continue;
        }

        ret = avcodec_send_packet(avCodecContext, packet);//解码  解码的过程也存在缓存，将待解码的包放在一个队列里，然后把解码后的包放在另一个队列中，从另一个队列中取出包读数据
        releaseAvPacket(packet);
        if (ret < 0) {
            break;
        }
        AVFrame *frame = av_frame_alloc();
        ret = avcodec_receive_frame(avCodecContext, frame);//获取解码数据
        if (ret == AVERROR(EAGAIN)) {
            continue;
        } else if (ret < 0) {
            break;
        }
        while (frame_queue.size() > 100 && isPlaying) {
            av_usleep(1000 * 10);
        }
        frame_queue.enQueue(frame);
    }
}


void bqPlayerCallback(SLAndroidSimpleBufferQueueItf queue, void *pContext) {
    AudioChannel *audioChannel = static_cast<AudioChannel *>(pContext);
    int dataSize = audioChannel->_getData();
    if (dataSize > 0) {
        (*queue)->Enqueue(queue, audioChannel->buffer, dataSize);
    }
}


int AudioChannel::_getData() {
    int dataSize = 0;
    AVFrame *frame = 0;
    while (isPlaying) {
        int ret = frame_queue.deQueue(frame);
        if (!isPlaying) {
            break;
        }
        if (!ret) {
            continue;
        }
        //转换
        int nb = swr_convert(swrContext, &buffer, bufferCount,
                             (const uint8_t **) frame->data, frame->nb_samples);//这里的frame->data不是data数据的字节数，是一个声道的有效样本数
        dataSize = nb * out_channls * out_sampleSize;
        //播放这一段声音的时刻  pts以1/20为单位 这句话是pts * 单位
        clock = frame->pts * av_q2d(time_base);
        break;
    }
    releaseAvFrame(frame);
    return dataSize;
}

void AudioChannel::_play() {
    /**
     * 1、创建引擎
     */
    // 创建引擎engineObject
    SLresult result;
    result = slCreateEngine(&engineObject, 0, NULL, 0, NULL, NULL);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }
    //初始化这个引擎
    result = (*engineObject)->Realize(engineObject, SL_BOOLEAN_FALSE);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }

    // 获取引擎接口engineInterface
    //只有通过接口才能创建混音器

    result = (*engineObject)->GetInterface(engineObject, SL_IID_ENGINE,
                                           &engineInterface);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }

    /**
     * 2、创建混音器 形式类似于C调用JNI
     */
    //通过引擎接口创建混音器

    result = (*engineInterface)->CreateOutputMix(engineInterface, &outputMixObject, 0, 0, 0);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }

    // 初始化混音器outputMixObject
    result = (*outputMixObject)->Realize(outputMixObject, SL_BOOLEAN_FALSE);
    if (SL_RESULT_SUCCESS != result) {
        return;
    }


    /**
     * 3、创建播放器  这个是Opensesl为安卓创建的一个队列
     */
    SLDataLocator_AndroidSimpleBufferQueue android_queue = {SL_DATALOCATOR_ANDROIDSIMPLEBUFFERQUEUE,
                                                            2};
    //pcm数据格式: pcm、声道数、采样率、采样位、容器大小、通道掩码(双声道)、字节序(小端)
    SLDataFormat_PCM pcm = {SL_DATAFORMAT_PCM, 2, SL_SAMPLINGRATE_44_1,
                            SL_PCMSAMPLEFORMAT_FIXED_16,
                            SL_PCMSAMPLEFORMAT_FIXED_16,
                            SL_SPEAKER_FRONT_LEFT | SL_SPEAKER_FRONT_RIGHT,
                            SL_BYTEORDER_LITTLEENDIAN};
    //数据源 （数据获取器+格式）  从哪里获得播放数据 可以是一个队列
    SLDataSource slDataSource = {&android_queue, &pcm};

    //设置混音器 在播放的时候，需要把混音器设置给播放器 这是真正播放声音的
    SLDataLocator_OutputMix outputMix = {SL_DATALOCATOR_OUTPUTMIX, outputMixObject};
    SLDataSink audioSnk = {&outputMix, NULL};


    const SLInterfaceID ids[1] = {SL_IID_BUFFERQUEUE};
    const SLboolean req[1] = {SL_BOOLEAN_TRUE};

    //播放器相当于对混音器进行了一层包装， 提供了额外的如：开始，停止等方法。
    // 混音器来播放声音

    (*engineInterface)->CreateAudioPlayer(engineInterface, &bqPlayerObject, &slDataSource,
                                          &audioSnk, 1,
                                          ids, req);
    //初始化播放器
    (*bqPlayerObject)->Realize(bqPlayerObject, SL_BOOLEAN_FALSE);

    //获得播放数据队列操作接口

    (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_BUFFERQUEUE,
                                    &bqPlayerBufferQueue);
    //设置回调（启动播放器后执行回调来获取数据并播放）
    (*bqPlayerBufferQueue)->RegisterCallback(bqPlayerBufferQueue, bqPlayerCallback, this);

    //获取播放状态接口
    (*bqPlayerObject)->GetInterface(bqPlayerObject, SL_IID_PLAY, &bqPlayerInterface);
    // 设置播放状态
    (*bqPlayerInterface)->SetPlayState(bqPlayerInterface, SL_PLAYSTATE_PLAYING);

    //还要手动调用一次 回调方法，才能开始播放
    bqPlayerCallback(bqPlayerBufferQueue, this);
}

void AudioChannel::_releaseOpenSL() {
    LOGE("停止播放");
    //设置停止状态
    if (bqPlayerInterface) {
        (*bqPlayerInterface)->SetPlayState(bqPlayerInterface, SL_PLAYSTATE_STOPPED);
        bqPlayerInterface = 0;
    }
    //销毁播放器
    if (bqPlayerObject) {
        (*bqPlayerObject)->Destroy(bqPlayerObject);
        bqPlayerObject = 0;
        bqPlayerBufferQueue = 0;
    }
    //销毁混音器
    if (outputMixObject) {
        (*outputMixObject)->Destroy(outputMixObject);
        outputMixObject = 0;
    }
    //销毁引擎
    if (engineObject) {
        (*engineObject)->Destroy(engineObject);
        engineObject = 0;
        engineInterface = 0;
    }
}