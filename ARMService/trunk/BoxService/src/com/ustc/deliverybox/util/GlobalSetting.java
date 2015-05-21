package com.ustc.deliverybox.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GlobalSetting {
	
	private static final String SETTING_CONFIG = "config";
	private static final String SETTING_HAS_REGISTER = "register";
	
	public static int hasRegister(Context context)
	{
		SharedPreferences setting = context.getSharedPreferences(SETTING_CONFIG, Context.MODE_PRIVATE);
		//return setting.getBoolean(SETTING_HAS_REGISTER, false);
		return setting.getInt(SETTING_HAS_REGISTER, -1);
	}
	
	public static void saveRegisterStatus(Context context, boolean bRegister)
	{
		SharedPreferences setting = context.getSharedPreferences(SETTING_CONFIG, Context.MODE_PRIVATE);
		Editor edit = setting.edit();
		
//		edit.putBoolean(SETTING_HAS_REGISTER, bRegister);
		if(bRegister){
			edit.putInt(SETTING_HAS_REGISTER, 1);
		} else {
			edit.putInt(SETTING_HAS_REGISTER, 0);
		}
		edit.commit();
	}
	
}
