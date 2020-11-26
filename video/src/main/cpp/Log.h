//
// Created by Administrator on 2019/11/19.
//

#ifndef ENJOYPLAYER_LOG_H
#define ENJOYPLAYER_LOG_H

#include <android/log.h>
//...表示可以传任何参数，这个参数传给android_log_print来打印日志
#define  LOGE(...)    __android_log_print(ANDROID_LOG_ERROR,"FFMPEG",__VA_ARGS__)

#endif //ENJOYPLAYER_LOG_H
