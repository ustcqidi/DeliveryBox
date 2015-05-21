package com.ustc.box.main.vo;

import java.util.List;
import java.util.Map;

public class DeliveryResponse extends ResponseData{
     private String tel;
     private String cabinetId;
     private Integer boxId;
     private String  status;
     private Double  price;
     private List<Map<String,Object>> list;
	public String getTel() {
		return tel;
	}
	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
    
     
     
}
