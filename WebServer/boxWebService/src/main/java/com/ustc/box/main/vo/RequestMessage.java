package com.ustc.box.main.vo;

import java.util.Map;

/**
 * 请求消息
 * 
 * @author Administrator
 * 
 */
public class RequestMessage {
	private String cmd;
	private RequestMessageType requestType;
	private RequestBasePart base;
	private Map<String, String> param;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public RequestBasePart getBase() {
		return base;
	}

	public void setBase(RequestBasePart base) {
		this.base = base;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	public RequestMessageType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestMessageType requestType) {
		this.requestType = requestType;
	}

	
}
