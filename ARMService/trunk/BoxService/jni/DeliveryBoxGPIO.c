#include "DeliveryBoxGPIO.h"

#define  LOG_TAG    "DeliveryBoxGPIO"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

/**
 Author: Qi Di
 Date: 2014-12-22
 Brief: GPIO Hander for DeliveryBox
 */

//***************************************************************************
// Sets the direction of the GPIO pin
// We expect only GPIO0, GPIO181 etc. No need for the full path as this is
// handled within the function
// Direction = 0 for input
// 			 = 1 for output
//           = 2 for output initially low
//           = 3 for output initially high
//***************************************************************************
JNIEXPORT jint JNICALL Java_com_ustc_deliverybox_gpio_DeliveryBoxGPIO_setdirectionGPIO(
		JNIEnv *env, jobject obj, jstring gpio, jint direction) {
	char fileName[64];
	char bufByte[10];
	const jbyte *str;
	int j, len;
	int fd;				// File handle

	str = (*env)->GetStringUTFChars(env, gpio, NULL);
	if (str == NULL) {
		LOGI("Can't get file name!");
		return -1;
	}

	sprintf(fileName, "/sys/class/gpio/%s/direction", str);
	(*env)->ReleaseStringUTFChars(env, gpio, str);

	fd = open(fileName, O_RDWR);
//	LOGI("setdirectionGPIO will open GPIO %s, fd is %d", fileName, fd);
	if (fd <= 0)					// Failed to open?
			{
		LOGI("setdirectionGPIO Failed to open GPIO %s", fileName);
		return -1;
	}

	if (direction == 0)			// Input
			{
		strcpy(bufByte, "in");
	} else if (direction == 1)		// Output
			{
		strcpy(bufByte, "out");
	} else if (direction == 2)		// Output initially LOW
			{
		strcpy(bufByte, "low");
	} else						// Output initially HIGH
	{
		strcpy(bufByte, "high");
	}

	len = strlen(bufByte);
	if ((j = write(fd, bufByte, len)) != len) {
		LOGE("write fail in GPIO direction setting");
		close(fd);
		return -1;
	} else {
		LOGI("write success in GPIO direction setting");
	}

	close(fd);
	return 1;	// Successful
}

//***************************************************************************
// Sets the state of the GPIO pin if this is an output
// We expect only GPIO0, GPIO181 etc. No need for the full path as this is
// handled within the function
//***************************************************************************

JNIEXPORT jint JNICALL Java_com_ustc_deliverybox_gpio_DeliveryBoxGPIO_setoutputGPIO(
		JNIEnv *env, jobject obj, jstring gpio, jint state) {
	char fileName[64];
	char bufByte[10];
	const jbyte *str;
	int j, len;
	int fd;				// File handle

	str = (*env)->GetStringUTFChars(env, gpio, NULL);
	if (str == NULL) {
		LOGI("Can't get file name!");
		return -1;
	}
	sprintf(fileName, "/sys/class/gpio/%s/value", str);

//	LOGI("setoutputGPIO will open GPIO %s", fileName);

	(*env)->ReleaseStringUTFChars(env, gpio, str);

	fd = open(fileName, O_RDWR);

	if (fd == 0)					// Failed to open?
			{
		LOGI("setoutputGPIO Failed to open GPIO %s", fileName);
		return -1;
	}

	if (state == 0)				// LOW
			{
		strcpy(bufByte, "0");
	} else						// HIGH
	{
		strcpy(bufByte, "1");
	}
	len = strlen(bufByte);
	if ((j = write(fd, bufByte, len)) != len) {
		LOGE("write fail in GPIO output setting");
		close(fd);
		return -1;
	}

	close(fd);
	return 1;	// Successful
}

//***************************************************************************
// Read the state of the GPIO pin if this is an input
// We expect only GPIO0, GPIO181 etc. No need for the full path as this is
// handled within the function
//***************************************************************************

JNIEXPORT jint JNICALL Java_com_ustc_deliverybox_gpio_DeliveryBoxGPIO_readGPIO(
		JNIEnv *env, jobject obj, jstring gpio) {
	char fileName[64];
	char bufByte[10];
	const jbyte *str;
	int j;
	int fd;				// File handle

	str = (*env)->GetStringUTFChars(env, gpio, NULL);
	if (str == NULL) {
		LOGI("Can't get file name!");
		return -1;
	}
	sprintf(fileName, "/sys/class/gpio/%s/value", str);

//	LOGI("readGPIO will open GPIO %s", fileName);

	(*env)->ReleaseStringUTFChars(env, gpio, str);

	fd = open(fileName, O_RDWR);

	if (fd == 0)					// Failed to open?
			{
		LOGI("readGPIO Failed to open GPIO %s", fileName);
		return (-2);
	}
	if ((j = read(fd, bufByte, 9)) == 0) {
		LOGE("read fail in GPIO input reading");
	}
	close(fd);
	if (bufByte[0] == '1') {
		return 1;				// HIGH
	}
	return 0;					// LOW
}

JNIEXPORT jint JNICALL Java_com_ustc_deliverybox_gpio_DeliveryBoxGPIO_exportGPIO(
		JNIEnv *env, jobject obj) {
	char fileName[64];
	int fd;
	char bufByte[10];
	int j, len;

	LOGI("exportGPIO start");

	FILE *p = NULL;

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 47);
	fclose(p);

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 48);
	fclose(p);

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 49);
	fclose(p);

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 50);
	fclose(p);

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 51);
	fclose(p);

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 52);
	fclose(p);

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 53);
	fclose(p);

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 56);
	fclose(p);

	p = fopen("/sys/class/gpio/export", "w");

	if (p == NULL)
		return -1;

	fprintf(p, "%d", 57);
	fclose(p);

	LOGI("exportGPIO end");

	return 1;	// Successful

}
