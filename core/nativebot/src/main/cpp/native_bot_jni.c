#include <jni.h>
#include "ultimate_ttt.h"

JNIEXPORT jobject JNICALL
Java_com_locker_core_nativebot_NativeBotBridge_getNativeMoveInternal(
    JNIEnv *env, jobject obj, jintArray board, jintArray macroBoard, jint activeBlock, jint player, jint depth) {

    jint *boardPtr = (*env)->GetIntArrayElements(env, board, NULL);
    jint *macroPtr = (*env)->GetIntArrayElements(env, macroBoard, NULL);

    // Hardcoded 1000ms limit
    NativeMove move = get_best_move(boardPtr, macroPtr, activeBlock, player, depth, 1000);

    (*env)->ReleaseIntArrayElements(env, board, boardPtr, JNI_ABORT);
    (*env)->ReleaseIntArrayElements(env, macroBoard, macroPtr, JNI_ABORT);

    jclass moveClass = (*env)->FindClass(env, "com/locker/core/models/model/Move");
    jmethodID constructor = (*env)->GetMethodID(env, moveClass, "<init>", "(IIII)V");

    return (*env)->NewObject(env, moveClass, constructor, move.fieldI, move.fieldJ, move.blockI, move.blockJ);
}
