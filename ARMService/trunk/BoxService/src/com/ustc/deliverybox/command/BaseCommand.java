package com.ustc.deliverybox.command;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import com.ustc.deliverybox.register.RegisterParams;
import com.ustc.deliverybox.util.ConstDef;

/**
Author: Qi Di
Date: 2014-1-20
Brief: Base command for web API
*/

public abstract class BaseCommand {
	
	protected static final String REGISTER_COMMAND_URL = ConstDef.BASE_URL+":8888/boxWebService/register?c=2001";
	
	protected static final String REGISTER_STATUS_COMMAND_URL = ConstDef.BASE_URL+":8888/boxWebService/register?c=2002";
	
	protected HttpRequestExecutor mHttpExecutor;
	
	abstract JSONObject prepareCommand() throws JSONException;
	
	public abstract void doCommand(RegisterParams regParam);
	
	void addRequestHeaders(HttpPost post) {
		post.addHeader("Content_type", "Application/json;charset=UTF-8");
	}

}
