package com.ustc.deliverybox.service;

import com.ustc.deliverybox.gpio.DeliveryBoxGPIO;
import com.ustc.deliverybox.util.GlobalSetting;
import com.ustc.deliverybox.util.Logger;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;
import android.widget.Toast;

public class DeliveryBox extends Application {

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Logger.info(TAG, "onConfigurationChanged");
	}

	private static final String TAG = DeliveryBox.class.getSimpleName();

	private static DeliveryBox mDeliveryBox = null;

	public static DeliveryBox getInstance() {
		return mDeliveryBox;
	}
	
	public void startService() {
		int ret = DeliveryBoxGPIO.Init();

		if (ret == -1) {
			Toast.makeText(getApplicationContext(), R.string.init_gpio_fail, 2000).show();;
			return;
		}

		if (GlobalSetting.hasRegister(getApplicationContext()) == 1 && ret != -1)
			startSocketService();
	}

	public void exit() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);  
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Logger.info(TAG, "onCreate");

		mDeliveryBox = this;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		Logger.info(TAG, "onTerminate");

		stopSocketService();
	}

	public void startSocketService() {
		Intent intent = new Intent();
		intent.setClassName(getPackageName(), DeliveryBoxSocket.class.getName());
		boolean ret = bindService(intent, mSocketServiceConnection,
				Context.BIND_AUTO_CREATE);

		Logger.info(TAG, "startSocketService ret is " + ret);
	}

	public void stopSocketService() {
		unbindService(mSocketServiceConnection);
	}

	final ServiceConnection mSocketServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Logger.info(TAG, "onServiceConnected");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Logger.info(TAG, "onServiceDisconnected");

			startSocketService();
		}

	};

}
