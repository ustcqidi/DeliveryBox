package com.ustc.box.main.vo;

import java.util.List;
import java.util.Map;

public class NearByResponse extends ResponseData{
     private String tel;
     private List<Map<String,Object>> data;
	public String getTel() {
		return tel;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
    
	  
	
    
     
     
}
