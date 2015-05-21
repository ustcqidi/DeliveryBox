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



public class RegisterCommand extends BaseCommand {

	public interface RegisterCommandSink {
		void onRegisterSuccess();
		void onRegisterFailure(String errorMessage);
	}

	private RegisterCommandSink mRegisterCommandSink;
	
	public void setListener(RegisterCommandSink sink) {
		this.mRegisterCommandSink = sink;
	}

	private RegisterParams mReqParam;

	public RegisterCommand() {
		mHttpExecutor = new HttpRequestExecutor();
		mHttpExecutor.abort();
	}

	@Override
	JSONObject prepareCommand() throws JSONException {

		JSONObject param = new JSONObject();
		param.put("cabinetId", mReqParam.cabinetId);
		param.put("province", mReqParam.province);
		param.put("city", mReqParam.city);
		param.put("region", mReqParam.region);
		param.put("smallCount", mReqParam.smallCount);
		param.put("communityName", mReqParam.communityName);
		param.put("cabinetName", mReqParam.cabinetName);

		return param;
	}

	@Override
	public void doCommand(RegisterParams regParam) {
		
		mReqParam = regParam;
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				JSONObject params = null;
				try {
					params = prepareCommand();
				} catch (JSONException e) {
					e.printStackTrace();
					
					if(mRegisterCommandSink != null) {
						mRegisterCommandSink.onRegisterFailure(e.getMessage());
					}
				}
				
				HttpPost request = new HttpPost(REGISTER_COMMAND_URL);
				addRequestHeaders(request);
				
				try {
					request.setEntity(new StringEntity(params.toString(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					if(mRegisterCommandSink != null) {
						mRegisterCommandSink.onRegisterFailure(e.getMessage());
					}
				}
				HttpEntity entity;
				try {
					entity = mHttpExecutor.execute(request);
					String json = EntityUtils.toString(entity, "UTF-8");
					parseResponse(json);
				} catch (IOException e) {
					e.printStackTrace();
					
					if(mRegisterCommandSink != null) {
						mRegisterCommandSink.onRegisterFailure(e.getMessage());
					}
				}
				
			}
		}).start();
	}
	
	
	private void parseResponse(String response){
		try {
			JSONObject resp = new JSONObject(response);
			String status = resp.getString("status");
			if (status.equals("success")) {
				if(mRegisterCommandSink != null)
					mRegisterCommandSink.onRegisterSuccess();
			} else {
				String errorMsg = resp.getString("desc");
				if (mRegisterCommandSink != null)
					mRegisterCommandSink.onRegisterFailure(errorMsg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			if (mRegisterCommandSink != null)
				mRegisterCommandSink.onRegisterFailure(e.getMessage());
		}
	}
	
}
