#include <jni.h>
#include <string>
#include <iostream>
#include <vector>


extern "C" {
#include <libavformat/avformat.h>
#include <libavutil/ffversion.h>

#include "libavcodec/avcodec.h"
#include "libavcodec/version.h"

}
extern "C" JNIEXPORT jstring JNICALL
Java_com_google_ffmpeg_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_google_ffmpeg_MainActivity_configurationinfo(JNIEnv *env, jobject obj)
{
    char info[10000] = { 0 };

    sprintf(info, "%s\n", avcodec_configuration());

    //LOGE("%s", info);
    return (*env).NewStringUTF(info);
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_google_ffmpeg_MainActivity_getVersion(JNIEnv *env, jobject thiz) {
    char info[10000] = { 0 };
    std::cout << FFMPEG_VERSION << std::endl;
    AVFormatContext* avformatctx = avformat_alloc_context();
    std::vector<std::string> a = {"1,2,3", "3.3.2", "5.1.2"};
    //LOGE("%s", info);
    return (*env).NewStringUTF(a[2].c_str());
}