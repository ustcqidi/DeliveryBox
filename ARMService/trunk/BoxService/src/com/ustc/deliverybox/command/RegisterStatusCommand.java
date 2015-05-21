package com.ustc.deliverybox.command;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.ustc.deliverybox.register.RegisterParams;

public class RegisterStatusCommand extends BaseCommand{
	
	public interface RegisterStatusCommandSink {
		void onRegisterStatus(boolean hasRegister);
	}

	private RegisterParams mReqParam;
	
	private RegisterStatusCommandSink mSink;
	
	public void setListener(RegisterStatusCommandSink sink) {
		this.mSink = sink;
	}
	
	public RegisterStatusCommand() {
		 mHttpExecutor = new HttpRequestExecutor();
		 mHttpExecutor.abort();
	}
	
	@Override
	JSONObject prepareCommand() throws JSONException {
		JSONObject param = new JSONObject();
		param.put("cabinetId", mReqParam.cabinetId);
		
		return param;
	}

	@Override
	public void doCommand(RegisterParams regParam) {
		this.mReqParam = regParam;
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				JSONObject params = null;
				try {
					params = prepareCommand();
				} catch (JSONException e) {
					e.printStackTrace();
					
					if(mSink != null) {
						mSink.onRegisterStatus(false);
					}
				}
				
				HttpPost request = new HttpPost(REGISTER_STATUS_COMMAND_URL);
				addRequestHeaders(request);
				
				try {
					request.setEntity(new StringEntity(params.toString(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					if(mSink != null) {
						mSink.onRegisterStatus(false);
					}
				}
				HttpEntity entity;
				try {
					entity = mHttpExecutor.execute(request);
					String json = EntityUtils.toString(entity, "UTF-8");
					parseResponse(json);
				} catch (IOException e) {
					e.printStackTrace();
					
					if(mSink != null) {
						mSink.onRegisterStatus(false);
					}
				}
				
			}
		}).start();
	}
	
	
	private void parseResponse(String response){
		try {
			JSONObject resp = new JSONObject(response);
			String errorCode = resp.getString("errorCode");
			if (errorCode.equals("000001")) {
				if(mSink != null)
					mSink.onRegisterStatus(true);
			} else {
				if (mSink != null)
					mSink.onRegisterStatus(false);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			if (mSink != null)
				mSink.onRegisterStatus(false);
		}
	}

}
