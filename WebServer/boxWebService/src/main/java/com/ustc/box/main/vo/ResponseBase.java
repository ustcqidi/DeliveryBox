package com.ustc.box.main.vo;






public class ResponseBase {
	private String status;
	private String errorCode;
	private String desc;
	private String type;
	private ResponseData res;
	private ResponseBase() {

	}

	
     
	public  void  fillSuccessResp() {
		this.status="success";
		this.errorCode="000000";
		this.desc="成功";
	}

	public  static ResponseBase getDefaultErrorResp() {
    	ResponseBase rb  =  new ResponseBase();
    	rb.setStatus("fail");
    	rb.setErrorCode("999999");
    	rb.setDesc("未知错误！");
    	return rb;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ResponseData getRes() {
		return res;
	}

	public void setRes(ResponseData res) {
		this.res = res;
	}
	
}
