//
// Created by Administrator on 2019/11/19.
//

#include <cstring>
#include <malloc.h>
#include "../Log.h"

#include "EnjoyPlayer.h"

extern "C" {//这个是C语言的，一定要括起来，否则报错
#include <libavformat/avformat.h>
}

void *prepare_t(void *args) {
    EnjoyPlayer *player = static_cast<EnjoyPlayer *>(args);
    player->_prepare();
    return 0;
}

EnjoyPlayer::EnjoyPlayer(JavaCallHelper *helper) : helper(helper) {
    avformat_network_init();
}

EnjoyPlayer::~EnjoyPlayer() {
    avformat_network_deinit();
    delete helper;
    helper = 0;
    if (path) {
        delete[] path;
        path = 0;
    }
}

void EnjoyPlayer::setDataSource(const char *path_) {
//    path  = static_cast< char *>(malloc(strlen(path_) + 1));
//    memset((void *) path, 0, strlen(path) + 1);
//
//    memcpy(path,path_,strlen(path_)); 深拷贝
//以上为C的方式，下面为C++
//这个path_不能直接用this.path=path_,因为path_指针可能在别处被释放掉
    if (path) {
        delete[] path;
        path = 0;
    }
    path = new char[strlen(path_) + 1]; //+1是因为字符串以'0'结尾
    strcpy(path, path_);
}


void EnjoyPlayer::prepare() {
    //解析  耗时！
    pthread_create(&prepareTask, 0, prepare_t, this);
}

void EnjoyPlayer::_prepare() {//这个方法就是在子线程调用的
    avFormatContext = avformat_alloc_context();
    /*
     * 1、打开媒体文件
     */
    //参数3： 输入文件的封装格式 ，传null表示 自动检测格式。 avi / flv
    //参数4： map集合，比如打开网络文件超时设置
//    AVDictionary *opts;
//    av_dict_set(&opts, "timeout", "3000000", 0);
//在子线程中回调java主线程要注意javaVM
    int ret = avformat_open_input(&avFormatContext, path, 0, 0);//传一个指针的指针，是为了改变指针的地址，否则只能改变指针的值
    if (ret != 0) {//只有返回0表示成功
        //.....
        LOGE("打开%s 失败，返回:%d 错误描述:%s", path, ret, av_err2str(ret));
        helper->onError(FFMPEG_CAN_NOT_OPEN_URL, THREAD_CHILD);
        goto ERROR;
    }


    /**
     * 2、查找媒体流
     */
     //>=0就是成功，否则失败
    ret = avformat_find_stream_info(avFormatContext, 0);
    if (ret < 0) {
        LOGE("查找媒体流 %s 失败，返回:%d 错误描述:%s", path, ret, av_err2str(ret));
        helper->onError(FFMPEG_CAN_NOT_FIND_STREAMS, THREAD_CHILD);
        goto ERROR;
    }
    // 得到视频时长，单位是秒
    duration = avFormatContext->duration / AV_TIME_BASE;
    // 这个媒体文件中有几个媒体流 (视频流、音频流)
    for (int i = 0; i < avFormatContext->nb_streams; ++i) {
        AVStream *avStream = avFormatContext->streams[i];
        // 解码信息 各种参数信息，各种配置
        AVCodecParameters *parameters = avStream->codecpar;

        //查找解码器 上一步得到了各种信息后，需要解码，先查找解码器
        AVCodec *dec = avcodec_find_decoder(parameters->codec_id);
        if (!dec) {//可能有些解码器没有找到
            helper->onError(FFMPEG_FIND_DECODER_FAIL, THREAD_CHILD);
            goto ERROR;
        }

        AVCodecContext *codecContext = avcodec_alloc_context3(dec);//内存不足可能失败
        // 把解码信息赋值给了解码上下文中的各种成员
        if (avcodec_parameters_to_context(codecContext, parameters) < 0) {//调用这个方法，把原视频的信息调用到解码器中
            // ，如果不调用这个放法，需要自己设置宽、高、比特率等信息
            helper->onError(FFMPEG_CODEC_CONTEXT_PARAMETERS_FAIL, THREAD_CHILD);
            goto ERROR;
        }
        //多线程解码
        if (parameters->codec_type == AVMEDIA_TYPE_VIDEO) {
            codecContext->thread_count = 8;
        } else if (parameters->codec_type == AVMEDIA_TYPE_AUDIO) {
            codecContext->thread_count = 1;
        }

        // 打开解码器
        if (avcodec_open2(codecContext, dec, 0) != 0) {
            //打开失败
            helper->onError(FFMPEG_OPEN_DECODER_FAIL, THREAD_CHILD);
            goto ERROR;
        }
        //对音频，视频进行区分
        if (parameters->codec_type == AVMEDIA_TYPE_AUDIO) {
            audioChannel = new AudioChannel(i, helper, codecContext, avStream->time_base);
        } else if (parameters->codec_type == AVMEDIA_TYPE_VIDEO) {
            //处理视频有的但是音频没有的信息
            // 帧率 单位时间内播放的图像数
            double fps = av_q2d(avStream->avg_frame_rate);
            if (isnan(fps) || fps == 0) {
                fps = av_q2d(avStream->r_frame_rate);
            }
            if (isnan(fps) || fps == 0) {
                fps = av_q2d(av_guess_frame_rate(avFormatContext, avStream, 0));
            }

            videoChannel = new VideoChannel(i, helper, codecContext, avStream->time_base, fps);//codecContext有宽高等具体信息
            videoChannel->setWindow(window);
        }
    }

    // 如果媒体文件中没有音视频
    if (!videoChannel && !audioChannel) {
        helper->onError(FFMPEG_NOMEDIA, THREAD_CHILD);
        goto ERROR;
    }

    //告诉java准备好了，可以播放了
    helper->onParpare(THREAD_CHILD);
    return;
    ERROR:
    LOGE("失败释放");
    release();
}

void *start_t(void *args) {
    EnjoyPlayer *player = static_cast<EnjoyPlayer *>(args);
    player->_start();
    return 0;
}

void EnjoyPlayer::start() {
    //1、读取媒体源的数据
    //2、根据数据类型放入Audio/VideoChannel的队列中
    isPlaying = 1;
    if (videoChannel) {
        videoChannel->audioChannel = audioChannel;
        videoChannel->play();
    }
    if (audioChannel) {
        audioChannel->play();
    }
    pthread_create(&startTask, 0, start_t, this);
}

void EnjoyPlayer::_start() {
    int ret;
    while (isPlaying) {
        AVPacket *packet = av_packet_alloc();
        ret = av_read_frame(avFormatContext, packet);//执行完后packet里有了数据
        if (ret == 0) {//ok
            if (videoChannel && packet->stream_index == videoChannel->channelId) {//表示是视频
                videoChannel->pkt_queue.enQueue(packet);
            } else if (audioChannel && packet->stream_index == audioChannel->channelId) {
                audioChannel->pkt_queue.enQueue(packet);
            } else {//既不是音频也不是视频，也要释放掉
                av_packet_free(&packet);
            }
        } else {
            av_packet_free(&packet);
            if (ret == AVERROR_EOF) { //end of file
                //读取完毕，不一定播放完毕
                if (videoChannel->pkt_queue.empty() && videoChannel->frame_queue.empty()//待解码的队列，同时待播放的队列没有数据，直播没有这个问题
                    && audioChannel->pkt_queue.empty() && audioChannel->frame_queue.empty()) {
                    //播放完毕
                    break;
                }
//            av_usleep(10000);
            } else {
                LOGE("读取数据包失败，返回:%d 错误描述:%s", ret, av_err2str(ret));
                break;
            }
        }
    }

    isPlaying = 0;
    audioChannel->stop();
    videoChannel->stop();
}

void EnjoyPlayer::setWindow(ANativeWindow *window) {
    this->window = window;//记录下window，把这个设置为成员属性
    if (videoChannel) {//videoChannel已经创建好
        videoChannel->setWindow(window);
    }
}

void EnjoyPlayer::stop() {
    isPlaying = 0;
    pthread_join(prepareTask, 0);//类似于Java的thread join 等待线程执行完了，否则一直阻塞 不然直接释放，可能出现野指针
    pthread_join(startTask, 0);
    release();
}

void EnjoyPlayer::release() {
    if (audioChannel) {
        delete audioChannel;
        audioChannel = 0;
    }
    if (videoChannel) {
        delete videoChannel;
        videoChannel = 0;
    }
    if (avFormatContext) {
        avformat_close_input(&avFormatContext);
        avformat_free_context(avFormatContext);
        avFormatContext = 0;
    }
}