package com.ustc.deliverybox.util;

import android.util.Log;

public class Logger {
	
	public static void info(String tag, String message)
	{
		Log.i(tag, message);
	}
	
	public static void debug(String tag, String message)
	{
		Log.d(tag, message);
	}
	
	public static void error(String tag, String message)
	{
		Log.e(tag, message);
	}

}
