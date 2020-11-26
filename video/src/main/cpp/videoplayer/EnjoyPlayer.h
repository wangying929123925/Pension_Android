//
// Created by Administrator on 2019/11/19.
//

#ifndef ENJOYPLAYER_ENJOYPLAYER_H
#define ENJOYPLAYER_ENJOYPLAYER_H

#include <pthread.h>
#include <android/native_window_jni.h>

#include "JavaCallHelper.h"
#include "VideoChannel.h"
#include "AudioChannel.h"

extern "C" {
#include <libavformat/avformat.h>
};

class EnjoyPlayer {
    friend void *prepare_t(void *args);//定义友元函数是想获取私有成员变量，但是有不想设置为public

    friend void *start_t(void *args);

public:
    EnjoyPlayer(JavaCallHelper *helper);

    ~EnjoyPlayer();

    void start();

    void stop();

    void setWindow(ANativeWindow *window);

public:
    void setDataSource(const char *path);

    void prepare();

private:
    void _prepare();

    void _start();

    void release();

private:
    char *path=0;
    pthread_t prepareTask;
    JavaCallHelper *helper;
    int64_t duration;
    VideoChannel *videoChannel = 0;//要初始化等于0，因为这个指针指向一个随意的地址
    AudioChannel *audioChannel = 0;
    pthread_t startTask;
    bool isPlaying;
    AVFormatContext *avFormatContext = 0;

    ANativeWindow *window = 0;
};


#endif //ENJOYPLAYER_ENJOYPLAYER_H
