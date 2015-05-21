package com.ustc.deliverybox.register;

import com.ustc.deliverybox.activity.OpenBoxActivity;
import com.ustc.deliverybox.command.RegisterCommand;
import com.ustc.deliverybox.service.DeliveryBox;
import com.ustc.deliverybox.service.MainActivity;
import com.ustc.deliverybox.service.R;
import com.ustc.deliverybox.service.SplashActivity;
import com.ustc.deliverybox.util.ConstDef;
import com.ustc.deliverybox.util.GlobalSetting;
import com.ustc.deliverybox.util.HardwareUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements IRegisterManager.Listener, View.OnClickListener, ConstDef{
	
	private Button mRegBtn = null;
	private EditText mProvince = null;
	private EditText mCity = null;
	private EditText mRegion = null;
	private EditText mCommunity = null;
	private EditText mCabinetName = null;
	private EditText mSmallCnt = null;
	
	private IRegisterManager mRegisterMgr = null;
	
	private static final String TAG = RegisterCommand.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_register);
		
		initViews();
		initManagers();
	}

	private void initManagers() {
		mRegisterMgr = new RegisterManager();
		mRegisterMgr.setListener(this);
	}

	private void initViews() {
		mRegBtn = (Button) findViewById(R.id.reg_btn);
		mRegBtn.setOnClickListener(this);
		
		mProvince = (EditText) findViewById(R.id.province);
		mCity = (EditText) findViewById(R.id.city);
		mRegion = (EditText) findViewById(R.id.region);
		mCommunity = (EditText) findViewById(R.id.community);
		mCabinetName = (EditText) findViewById(R.id.cabinetname);
		mSmallCnt = (EditText) findViewById(R.id.small_count);
	}
	
	private RegisterParams buildRegParams()
	{
		RegisterParams req = new RegisterParams();
		
		req.cabinetId = HardwareUtil.getDeviceID(RegisterActivity.this);
		req.province = mProvince.getText().toString();
		req.city = mCity.getText().toString();
		req.region = mRegion.getText().toString();
		req.communityName = mCommunity.getText().toString();
		req.cabinetName = mCabinetName.getText().toString();
		req.smallCount = mSmallCnt.getText().toString();
		
		return req;
	}
	
	
	private Context getContext()
	{
		return RegisterActivity.this;
	}

	
	//View.onClickListener
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.reg_btn:
			mRegisterMgr.register(this.buildRegParams());
			break;
		}
	}

	
	//IRegisterManager.Listener
	@Override
	public void onRegisterSuccess() {
		
		RegisterActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getContext(), "Register success", TOAST_DURATION).show();
				
				finishRegister();
			}
			
		});
		
	}
	
	private void finishRegister()
	{
		GlobalSetting.saveRegisterStatus(getContext(), true);
		Intent intent = new Intent(getContext(),OpenBoxActivity.class); 

		DeliveryBox.getInstance().startService();
		
		finish();
		startActivity(intent);
	}

	@Override
	public void onRegisterFailure(final String errorMessage) {
		RegisterActivity.this.runOnUiThread(new Runnable(){
		@Override
		public void run() {
			
				Toast.makeText(getContext(), errorMessage, TOAST_DURATION).show();
			
		}
		});
	}

}
