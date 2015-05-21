package com.ustc.deliverybox.gpio;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.ustc.deliverybox.util.Logger;

/**
 * Author: Qi Di Date: 2014-12-22 Brief: GPIO Hander for DeliveryBox
 */

public class DeliveryBoxGPIO {

	// code stand for box id
	static final String GPIO47 = "gpio47";
	static final String GPIO48 = "gpio48";
	static final String GPIO49 = "gpio49";
	static final String GPIO50 = "gpio50";
	static final String GPIO51 = "gpio51";
	static final String GPIO52 = "gpio52";

	// enable or disable box id code
	static final String GPIO53 = "gpio53";
	static final String GPIO56 = "gpio56";
	static final String GPIO57 = "gpio57";

	//
	// GPIO handler
	//
	static final int DIRECTION_GPIO_IN = 0;
	static final int DIRECTION_GPIO_OUT = 1;
	static final int DIRECTION_GPIO_OUT_LOW = 2;
	static final int DIRECTION_GPIO_OUT_HIGN = 3;

	static final int OUT_GPIO_LOW = 0;
	static final int OUT_GPIO_HIGH = 1;
	private static int gpioSignalMappings[];

	private static final String TAG = DeliveryBoxGPIO.class.getSimpleName();

	/**
	 * @param nodeName
	 *            node path name
	 * @param State
	 *            state of output 0,1,2 or 3
	 * @return return file hander else return <0 on fail
	 */
	public native static int setdirectionGPIO(String nodeName, int State);

	/**
	 * @param fileHander
	 * @return read GPIO state
	 */
	public native static int readGPIO(String nodeName);

	/**
	 * @param nodeName
	 *            node path name
	 * @param State
	 *            state of output 0,1 for LOW or HIGH
	 * @return 1 for OK, 0 or -neg for fail
	 */
	public native static int setoutputGPIO(String nodeName, int State);

	public native static int exportGPIO();

	static {
		System.loadLibrary("DeliveryBoxGPIO");
	}

	public static int Init() {

		if (grantPermision4Export() == -1)
			return -1; // fail

		// export GPIO
		if (exportGPIO() == -1)
			return -1; // fail

		// Output initially LOW
		if (setdirectionGPIO(GPIO47, DIRECTION_GPIO_OUT_LOW) == -1)
			return -1;
		if (setdirectionGPIO(GPIO48, DIRECTION_GPIO_OUT_LOW) == -1)
			return -1;
		if (setdirectionGPIO(GPIO49, DIRECTION_GPIO_OUT_LOW) == -1)
			return -1;
		if (setdirectionGPIO(GPIO50, DIRECTION_GPIO_OUT_LOW) == -1)
			return -1;
		if (setdirectionGPIO(GPIO51, DIRECTION_GPIO_OUT_LOW) == -1)
			return -1;
		if (setdirectionGPIO(GPIO52, DIRECTION_GPIO_OUT_LOW) == -1)
			return -1;

		// Output initially HIGH
		if (setdirectionGPIO(GPIO53, DIRECTION_GPIO_OUT_HIGN) == -1)
			return -1;

		if (setdirectionGPIO(GPIO56, DIRECTION_GPIO_OUT_HIGN) == -1)
			return -1;

		if (setdirectionGPIO(GPIO57, DIRECTION_GPIO_OUT_HIGN) == -1)
			return -1;

		// success
		return 1;
	}

	private static int grantPermision4Export() {
		try {
			Process sh = Runtime.getRuntime().exec("su", null,
					new File("/system/bin/"));
			OutputStream os = sh.getOutputStream();

			os.write(("chmod 777 /sys/class/gpio/export").getBytes("ASCII"));
			os.flush();
			os.close();
			try {
				sh.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return -1;
			}

			return 1;

		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int openBox(int boxID) {

		// boxID = boxID - 1;

		// /*2*/ {0, 0, 0, 0, 0, 0, 1, 1, 1},
		// /*4*/ {1, 0, 0, 0, 0, 0, 1, 1, 1},
		// /*6*/ {0, 1, 0, 0, 0, 0, 1, 1, 1},
		// /*8*/ {1, 1, 0, 0, 0, 0, 1, 1, 1},
		// /*9*/ {0, 0, 1, 0, 0, 0, 1, 1, 1},
		// /*10*/ {1, 0, 1, 0, 0, 0, 1, 1, 1},

		// convert boxID to mapping table index
		int mappingTableIndex = -1;
		if (boxID == 2) {
			mappingTableIndex = 0;
		} else if (boxID == 4) {
			mappingTableIndex = 1;
		} else if (boxID == 6) {
			mappingTableIndex = 2;
		} else if (boxID == 8) {
			mappingTableIndex = 3;
		} else if (boxID > 8 && boxID <= 180){
			mappingTableIndex = boxID - 5;
		} else {
			return -1;
		}
		// end

		if (mappingTableIndex >= OpenBoxGPIOMapping.signalMapping.length
				|| mappingTableIndex < 0) {
			Logger.error(TAG,
					"openBox failue, mappingTableIndex is illegal: mappingTableIndex = "
							+ mappingTableIndex);
			return -1;
		}

		gpioSignalMappings = OpenBoxGPIOMapping.signalMapping[mappingTableIndex];

		for (int index = 0; index < OpenBoxGPIOMapping.GPIO_NUMBER; index++) {
			int ret = setoutputGPIO(OpenBoxGPIOMapping.nameMapping[index],
					gpioSignalMappings[index]);
			
			Logger.info(TAG, "setoutputGPIO GPIO name is " + OpenBoxGPIOMapping.nameMapping[index] + " signal is " + gpioSignalMappings[index]);

			if (ret == -1)
				return -1;
		}

		if (enableOpenBoxSignal(boxID) == -1)
			return -1;
		try {
			Thread.sleep(80);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		}
		if (disableOpenBoxSignal(boxID) == -1)
			return -1;

		return 1; // success
	}

	private static int enableOpenBoxSignal(int boxID) {

		int ret = -1;

		if (boxID <= 60)
			ret = setoutputGPIO(GPIO53, OUT_GPIO_LOW);
		else if (boxID > 60 && boxID <= 120)
			ret = setoutputGPIO(GPIO56, OUT_GPIO_LOW);
		else if (boxID > 120 && boxID <= 180)
			ret = setoutputGPIO(GPIO57, OUT_GPIO_LOW);

		return ret;
	}

	private static int disableOpenBoxSignal(int boxID) {

		int ret = -1;

		if (boxID <= 60)
			ret = setoutputGPIO(GPIO53, OUT_GPIO_HIGH);
		else if (boxID > 60 && boxID <= 120)
			ret = setoutputGPIO(GPIO56, OUT_GPIO_HIGH);
		else if (boxID > 120 && boxID <= 180)
			ret = setoutputGPIO(GPIO57, OUT_GPIO_HIGH);

		return ret;
	}

}
