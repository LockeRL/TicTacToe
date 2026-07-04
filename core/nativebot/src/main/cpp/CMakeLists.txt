cmake_minimum_required(VERSION 3.10.2)
project("native_bot")

add_library(native_bot SHARED
    ultimate_ttt.c
    native_bot_jni.c
)

target_include_directories(native_bot PRIVATE .)
find_library(log-lib log)
target_link_libraries(native_bot ${log-lib})
