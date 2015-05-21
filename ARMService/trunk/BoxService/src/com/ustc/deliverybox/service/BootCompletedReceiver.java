package com.ustc.deliverybox.service;

import com.ustc.deliverybox.util.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
Author: Qi Di
Date: 2014-1-31
Brief: Auto Launch Application when reboot success
*/

public class BootCompletedReceiver extends BroadcastReceiver{

	private static final String TAG = BroadcastReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		 Logger.info(TAG, "onReceive");  
	     
		 //TODO check whether is register
		 Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()); 
		 context.startActivity(i); 
	}

}
