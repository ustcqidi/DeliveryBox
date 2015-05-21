LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

TARGET_PLATFORM := android-3
LOCAL_MODULE    := DeliveryBoxGPIO
LOCAL_LDLIBS 	:= -llog
### Add all source file names to be included in lib separated by a whitespace
LOCAL_SRC_FILES := DeliveryBoxGPIO.c

include $(BUILD_SHARED_LIBRARY)
