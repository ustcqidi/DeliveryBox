package com.ustc.deliverybox.service;

import com.ustc.deliverybox.activity.OpenBoxActivity;
import com.ustc.deliverybox.command.RegisterStatusCommand;
import com.ustc.deliverybox.command.RegisterStatusCommand.RegisterStatusCommandSink;
import com.ustc.deliverybox.register.RegisterActivity;
import com.ustc.deliverybox.register.RegisterParams;
import com.ustc.deliverybox.util.ConstDef;
import com.ustc.deliverybox.util.GlobalSetting;
import com.ustc.deliverybox.util.HardwareUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity implements ConstDef, RegisterStatusCommandSink {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				RegisterStatusCommand regStatus = new RegisterStatusCommand();
				regStatus.setListener(SplashActivity.this);
				
				RegisterParams param = new RegisterParams();
				param.cabinetId = HardwareUtil.getDeviceID(getApplicationContext());
				
				regStatus.doCommand(param);
			}

		}, SPLASH_DISPLAY_LENGHT);
	}

	private void startNextActivity(boolean hasRegister) {
		Intent nextAcitivity = null;
		if (GlobalSetting.hasRegister(SplashActivity.this) == 1) { //�ɹ�ע��״̬
			nextAcitivity = new Intent(SplashActivity.this,OpenBoxActivity.class);
			DeliveryBox.getInstance().startService();
		} else {
			
			if(GlobalSetting.hasRegister(SplashActivity.this) == -1) //��Чע��״̬
			{
				
				if(hasRegister) {
					GlobalSetting.saveRegisterStatus(SplashActivity.this, true);
					nextAcitivity = new Intent(SplashActivity.this,OpenBoxActivity.class);
					DeliveryBox.getInstance().startService();
				} else {
					nextAcitivity = new Intent(SplashActivity.this,RegisterActivity.class);	
				}
				
			}
			else if(GlobalSetting.hasRegister(SplashActivity.this) == 0) //δע��״̬
			{
				nextAcitivity = new Intent(SplashActivity.this,RegisterActivity.class);
			}
		}

		SplashActivity.this.startActivity(nextAcitivity);
		SplashActivity.this.finish();

	}

	@Override
	public void onRegisterStatus(final boolean hasRegister) {
		SplashActivity.this.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				startNextActivity(hasRegister);
			}
			
		});
	}

}
