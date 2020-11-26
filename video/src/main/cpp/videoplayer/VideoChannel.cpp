//
// Created by Administrator on 2019/11/21.
//


#include "VideoChannel.h"
#include <SLES/OpenSLES_Android.h>


extern "C" {
#include <libswscale/swscale.h>
#include <libavutil/rational.h>
#include <libavutil/imgutils.h>
#include <libavutil/time.h>
}

#define AV_SYNC_THRESHOLD_MIN 0.04
#define AV_SYNC_THRESHOLD_MAX 0.1

VideoChannel::VideoChannel(int channelId, JavaCallHelper *helper, AVCodecContext *avCodecContext,
                           const AVRational &base, double fps) :
        BaseChannel(channelId, helper, avCodecContext, base), fps(fps) {
    pthread_mutex_init(&surfaceMutex, 0);

}

VideoChannel::~VideoChannel() {
    pthread_mutex_destroy(&surfaceMutex);
}

void *videoDecode_t(void *args) {
    VideoChannel *videoChannel = static_cast<VideoChannel *>(args);
    videoChannel->decode();
    return 0;
}

void *videoPlay_t(void *args) {
    VideoChannel *videoChannel = static_cast<VideoChannel *>(args);
    videoChannel->_play();
    return 0;
}


void VideoChannel::_play() {
    // 缩放、格式转换的上下文 这里可以进行缩放
    SwsContext *swsContext = sws_getContext(avCodecContext->width,
                                            avCodecContext->height,//原始图像的宽高
                                            avCodecContext->pix_fmt,
                                            avCodecContext->width,
                                            avCodecContext->height,//转换结果的宽高
                                            AV_PIX_FMT_RGBA, SWS_FAST_BILINEAR,
                                            0, 0, 0);

    uint8_t *data[4];//接收转换后的结果，这里还没有申请内存
    int linesize[4];
    av_image_alloc(data, linesize, avCodecContext->width, avCodecContext->height,
                   AV_PIX_FMT_RGBA, 1);

    AVFrame *frame = 0;  //100x100 ,window : 100x100
    double frame_delay = 1.0 / fps;
    int ret;
    while (isPlaying) {
        //阻塞方法
        ret = frame_queue.deQueue(frame);
        //todo frame的格式一定要是RGBA才能显示到window上去
        // FFmpeg在解码视频时一般会将视频解码为YUV数据，所以要将YUV格式转换为RGB进行显示，swscale提供颜色空间转换的功能，但是效率低，使用google的libyuv库效率高
        // 阻塞完了之后可能用户已经停止播放
        // RGBA的存放格式是byte[][4],byte[][0]表示R
        if (!isPlaying) {
            break;
        }

        if (!ret) {
            continue;
        }
        // 让视频流畅播放
        double extra_delay = frame->repeat_pict / (2 * fps);
        double delay = extra_delay + frame_delay;

        if (audioChannel) {
            // 值： frame->best_effort_timestamp的值就是pts  在一些特殊的情况下，FFMpeg会对它进行一些调整，让他成为AVFrame的最优展示时间，比pts更好
            clock = frame->best_effort_timestamp * av_q2d(time_base);
            double diff = clock - audioChannel->clock;//视频的clock-音频的clock

            /**
             * 把误差控制在0.04-0.1之间，这个参考别人的
             * 1、delay < 0.04, 同步阈值就是0.
             * 2、delay > 0.1 同步阈值就是0.1
             * 3、0.04 < delay < 0.1 ，同步阈值是delay
             */
            // 根据每秒视频需要播放的图象数，确定音视频的时间差允许范围
            // 给到一个时间差的允许范围  max(0.04,min(0.1,x))
            double sync = FFMAX(AV_SYNC_THRESHOLD_MIN, FFMIN(AV_SYNC_THRESHOLD_MAX, delay));
            //todo 以下相当于处理比绝对值大的
            //视频落后了，落后太多了，我们需要去同步
            if (diff <= -sync) {
                //就要让delay时间减小
                delay = FFMAX(0, delay + diff);//这个值不能为复数
            } else if (diff > sync) {
                //视频快了,让delay久一些，等待音频赶上来
                delay = delay + diff;
            }

            LOGE("Video:%lf Audio:%lf delay:%lf A-V=%lf", clock, audioChannel->clock, delay, -diff);
        }

        av_usleep(delay * 1000000);

        //todo 2、指针数据，比如RGBA，每一个维度的数据就是一个指针，那么RGBA需要4个指针，所以就是4个元素的数组，数组的元素就是指针，指针数据
        // 3、每一行数据他的数据个数
        // 4、 offset 下标偏移量
        // 5、 要转化图像的高
        sws_scale(swsContext, frame->data, frame->linesize, 0,
                  frame->height, data, linesize);
        //画画
        _onDraw(data, linesize, avCodecContext->width, avCodecContext->height);
        releaseAvFrame(frame);
    }
    av_free(&data[0]);
    isPlaying = 0;  //在循环外面释放
    releaseAvFrame(frame);
    sws_freeContext(swsContext);
}


void VideoChannel::_onDraw(uint8_t **data, int *linesize, int width, int height) {
    pthread_mutex_lock(&surfaceMutex);
    if (!window) {
        pthread_mutex_unlock(&surfaceMutex);
        return;
    }
    ANativeWindow_setBuffersGeometry(window, width, height, WINDOW_FORMAT_RGBA_8888);//window的宽高和要显示的图像的宽高一样

    ANativeWindow_Buffer buffer;
    if (ANativeWindow_lock(window, &buffer, 0) != 0) {
        ANativeWindow_release(window);
        window = 0;
        pthread_mutex_unlock(&surfaceMutex);
        return;
    }
    //把视频数据刷到buffer中
    uint8_t *dstData = static_cast<uint8_t *>(buffer.bits);
    int dstSize = buffer.stride * 4;//步长是每一行像素的个数 步长可能>=宽 字节对齐是为了优化读取速度  这是window的
    // 视频图像的rgba数据
    uint8_t *srcData = data[0];//拿第0个数据，因为经过转换后，所有的数据都保存在第0个 这是FFmpeg转换之后保存的数据，和dstSize可能不一样   它字节对齐的位数不一样
    int srcSize = linesize[0];

    //一行一行拷贝
    for (int i = 0; i < buffer.height; ++i) {
        memcpy(dstData + i * dstSize, srcData + i * srcSize, srcSize);//从dst到src,每次拷贝srcsize得数据
    }
//todo
//例如：在window中的数据是 4*4 ，步长为8，
//        FFmpeg 中，是    4*4 步长为4.
//window中的存储是 [x,x,x,x,x,x,x,x   每一行只有前4个数是有效的，后面的是用来字节对齐的，那么拷贝的时候，就会从FFmpeg中每次考4个
//                  x,x,x,x,x,x,x,x
//                  x,x,x,x,x,x,x,x
//                  x,x,x,x,x,x,x,x]
    ANativeWindow_unlockAndPost(window);
    pthread_mutex_unlock(&surfaceMutex);

}

void VideoChannel::play() {
    isPlaying = 1;
    // 开启队列的工作，不设置不能向里面加数据
    setEnable(true);
    //解码  解码和播放使用两个线程，否则会卡顿
    pthread_create(&videoDecodeTask, 0, videoDecode_t, this);
    //播放
    pthread_create(&videoPlayTask, 0, videoPlay_t, this);
}


void VideoChannel::decode() {
    AVPacket *packet = 0;
    while (isPlaying) {
        //阻塞
        // 1、能够取到数据，结束阻塞
        // 2、停止播放，结束阻塞
        int ret = pkt_queue.deQueue(packet);//取出包，阻塞方法，像java的阻塞队列
        if (!isPlaying) {
            break;
        }
        if (!ret) {
            continue;
        }
        // 向解码器发送解码数据
        ret = avcodec_send_packet(avCodecContext, packet);
        releaseAvPacket(packet);//这是堆中动态申请的内存，要进行释放
        if (ret < 0) {
            break;
        }

        //从解码器取出解码好的数据
        AVFrame *frame = av_frame_alloc();
        ret = avcodec_receive_frame(avCodecContext, frame);
        if (ret == AVERROR(EAGAIN)) { //还要更多的数据才能解码（音视频同步）
            continue;
        } else if (ret < 0) {
            break;
        }
        //todo 这里还有音频的，更好的实现是需要时再解码，只需要一个线程与一个待解码队列
        while (frame_queue.size() > fps * 10 && isPlaying) {//frame_queue解码好的队列
            av_usleep(1000 * 10);
        }
        frame_queue.enQueue(frame);
    }
    releaseAvPacket(packet);
}


void VideoChannel::setWindow(ANativeWindow *window) {//播放的时候是在子线程videoPlayTask播放window
    //调用这个setWindow方法的和播放的不是同一个线程，因此要注意线程安全
    pthread_mutex_lock(&surfaceMutex);//互斥量相当于java里的synchronize    只有RGB才能在window显示
    if (this->window) {//类似java的操作
        ANativeWindow_release(this->window);
    }
    this->window = window;
    pthread_mutex_unlock(&surfaceMutex);
}


void VideoChannel::stop() {
    isPlaying = 0;
    helper = 0;
    setEnable(0);
    pthread_join(videoDecodeTask, 0);
    pthread_join(videoPlayTask, 0);
    if (window) {
        ANativeWindow_release(window);
        window = 0;
    }
}


