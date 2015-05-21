package com.ustc.box.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "record_info")
public class RecordInfo {
	
	private Integer id;
	private Integer boxId;
	private Integer pickupType;
	private String expressNumber;
	private String expressCode;
	private String deliveryTel;
	private Date deliveryDate;
	private Date pickupDate;
	private String receiveTel;
	private Double deliveryToMoney;
	private String dateCount;
	private Double pickupToMoney;
	private String cabinetId;
	private String validatecode;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "boxId")
	public Integer getBoxId() {
		return boxId;
	}

	@Column(name = "pickupType")
	public Integer getPickupType() {
		return pickupType;
	}

	@Column(name = "expressNumber")
	public String getExpressNumber() {
		return expressNumber;
	}

	@Column(name = "expressCode")
	public String getExpressCode() {
		return expressCode;
	}

	@Column(name = "deliveryTel")
	public String getDeliveryTel() {
		return deliveryTel;
	}

	@Column(name = "deliveryDate")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	@Column(name = "pickupDate")
	public Date getPickupDate() {
		return pickupDate;
	}

	@Column(name = "receiveTel")
	public String getReceiveTel() {
		return receiveTel;
	}

	@Column(name = "deliveryToMoney")
	public Double getDeliveryToMoney() {
		return deliveryToMoney;
	}

	@Column(name = "dateCount")
	public String getDateCount() {
		return dateCount;
	}

	@Column(name = "pickupToMoney")
	public Double getPickupToMoney() {
		return pickupToMoney;
	}

	@Column(name = "cabinetId")
	public String getCabinetId() {
		return cabinetId;
	}

	@Column(name = "validatecode")
	public String getValidatecode() {
		return validatecode;
	}

	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}

	public void setPickupType(Integer pickupType) {
		this.pickupType = pickupType;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public void setDeliveryTel(String deliveryTel) {
		this.deliveryTel = deliveryTel;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public void setReceiveTel(String receiveTel) {
		this.receiveTel = receiveTel;
	}

	public void setDeliveryToMoney(Double deliveryToMoney) {
		this.deliveryToMoney = deliveryToMoney;
	}

	public void setDateCount(String dateCount) {
		this.dateCount = dateCount;
	}

	public void setPickupToMoney(Double pickupToMoney) {
		this.pickupToMoney = pickupToMoney;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}

	

	
	
	
}
