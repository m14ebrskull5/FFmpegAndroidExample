#include <jni.h>
#include <string>

extern "C" {
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
    //LOGE("%s", info);
    return (*env).NewStringUTF(FFMPEG_VERSION);
}