package com.ustc.box.main.vo;

import java.util.List;
import java.util.Map;

public class ChargeResponse extends ResponseData{
     private String tel;
     private String payTotal ;
     private List<Map<String,Object>> cdata;
     private List<Map<String,Object>> pdata;
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPayTotal() {
		return payTotal;
	}
	public void setPayTotal(String payTotal) {
		this.payTotal = payTotal;
	}
	public List<Map<String, Object>> getCdata() {
		return cdata;
	}
	public void setCdata(List<Map<String, Object>> cdata) {
		this.cdata = cdata;
	}
	public List<Map<String, Object>> getPdata() {
		return pdata;
	}
	public void setPdata(List<Map<String, Object>> pdata) {
		this.pdata = pdata;
	}
	
	
	
    
     
     
}
