package com.ustc.box.main.vo;

import java.util.Set;

/**
 * 基础参数部分
 * 
 * @author Administrator
 * 
 */
public class RequestBasePart {
	private String type;// 用户类型，
	private String imei;// eim卡号
	private String token;// 通行证认证令牌，部分接口可能为空
	private String tel;// 通行证认证令牌，部分接口可能为空
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}



}
