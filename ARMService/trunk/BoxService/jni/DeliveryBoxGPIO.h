#include <jni.h>
/* Header for class DeliveryBoxGPIO */
#include <stdio.h>
#include <android/log.h>
#include <fcntl.h>
#include <linux/i2c.h>
#include <memory.h>
#include <malloc.h>

/**
Author: Qi Di
Date: 2014-12-22
Brief: GPIO Hander for DeliveryBox
*/

#ifndef _Included_axon_io
#define _Included_axon_io
#ifdef __cplusplus
  extern "C" {
#endif 
/*
 * Class:     DeliveryBoxGPIO
 * Method:    setdirectionGPIO
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_ustc_deliverybox_gpio_DeliveryBoxGPIO_setdirectionGPIO
  (JNIEnv *, jobject, jstring, jint state);

/*
 * Class:     DeliveryBoxGPIO
 * Method:    readGPIO
 * Signature: (II[II)I
 */
JNIEXPORT jint JNICALL Java_com_ustc_deliverybox_gpio_DeliveryBoxGPIO_readGPIO
  (JNIEnv *, jobject, jstring);

/*
 * Class:     DeliveryBoxGPIO
 * Method:    setoutputGPIO
 * Signature: (III[II)I
 */
JNIEXPORT jint JNICALL Java_com_ustc_deliverybox_gpio_DeliveryBoxGPIO_setoutputGPIO
  (JNIEnv *, jobject, jstring, jint state);


JNIEXPORT jint JNICALL Java_com_ustc_deliverybox_gpio_DeliveryBoxGPIO_exportGPIO
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
