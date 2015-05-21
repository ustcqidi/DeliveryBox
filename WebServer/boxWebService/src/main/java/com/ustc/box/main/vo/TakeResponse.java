package com.ustc.box.main.vo;

import java.util.List;
import java.util.Map;

public class TakeResponse extends ResponseData{
     private String tel;
     private String cabinetId;
     private Integer boxId;
     private String  status;
     private List<Map<String,Object>> listpickuping;
     private List<Map<String,Object>> listpickuped;
	public String getTel() {
		return tel;
	}
	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}
	public String getCabinetId() {
		return cabinetId;
	}
	public Integer getBoxId() {
		return boxId;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Map<String, Object>> getListpickuping() {
		return listpickuping;
	}
	public List<Map<String, Object>> getListpickuped() {
		return listpickuped;
	}
	public void setListpickuping(List<Map<String, Object>> listpickuping) {
		this.listpickuping = listpickuping;
	}
	public void setListpickuped(List<Map<String, Object>> listpickuped) {
		this.listpickuped = listpickuped;
	}
	
	
    
     
     
}
