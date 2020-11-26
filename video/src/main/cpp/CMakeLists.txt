
cmake_minimum_required(VERSION 3.4.1)

aux_source_directory(. SOURCE)#加入库路径下的所有代码
aux_source_directory( ${PROJECT_SOURCE_DIR}/videoplayer SOURCE )#添加/videoplayer文件代码


add_library(
             native-lib
             SHARED
         ${SOURCE}
)


#设置一个变量
set(libs ${CMAKE_SOURCE_DIR}/${ANDROID_ABI})
#-L：引入库查找路径 CMAKE_SOURCE_DIR表示CMakeLists的绝对路径 ANDROID_ABI表示指定的编译文件夹，例如armeabi-v7a
#这里libs表示库的路径
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${libs}")#传给编译器的参数-L表示库路径 这句话用于替代add_library



#引入头文件
include_directories(include)
include_directories("${CMAKE_SOURCE_DIR}"/videoplayer)


target_link_libraries(
                       native-lib

            avfilter avformat  avcodec avutil swresample swscale rtmp android z #NDK文件中去掉lib,去掉.a
        OpenSLES
                       log )