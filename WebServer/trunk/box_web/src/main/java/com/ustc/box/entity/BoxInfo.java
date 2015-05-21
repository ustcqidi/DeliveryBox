package com.ustc.box.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "box_info")
public class BoxInfo {
	
	private String cabinetId;
	private String communityName;
	private String cabinetType;
	private String province;
	private String city;
	private String region;
	private String communityAddress;
	private String communityUser;
	private String communityContact;
	private String communityPropertyCompany;
	private String manager;
	private Integer status;
	private Date update_time;
	private String cabinetName;
	private Integer smallCount;

	private Double dCabinetPrices;
	private Double zCabinetPrices;
	private Double xCabinetPrices;
	
	private String img_url;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cabinetId")	
	public String getCabinetId() {
		return cabinetId;
	}
	
	@Column(name = "communityName")	
	public String getCommunityName() {
		return communityName;
	}
	
	@Column(name = "cabinetType")	
	public String getCabinetType() {
		return cabinetType;
	}
	
	@Column(name = "province")	
	public String getProvince() {
		return province;
	}
	
	@Column(name = "city")	
	public String getCity() {
		return city;
	}
	
	@Column(name = "region")	
	public String getRegion() {
		return region;
	}
	
	@Column(name = "communityAddress")	
	public String getCommunityAddress() {
		return communityAddress;
	}
	
	@Column(name = "communityUser")	
	public String getCommunityUser() {
		return communityUser;
	}
	
	@Column(name = "communityContact")	
	public String getCommunityContact() {
		return communityContact;
	}
	
	@Column(name = "communityPropertyCompany")	
	public String getCommunityPropertyCompany() {
		return communityPropertyCompany;
	}
	
	@Column(name = "manager")	
	public String getManager() {
		return manager;
	}
	
	@Column(name = "status")	
	public Integer getStatus() {
		return status;
	}
	
	@Column(name = "update_time")	
	public Date getUpdate_time() {
		return update_time;
	}
	
	@Column(name = "cabinetName")	
	public String getCabinetName() {
		return cabinetName;
	}
	
	
	@Column(name = "smallCount")	
	public Integer getSmallCount() {
		return smallCount;
	}
	
	@Column(name = "img_url")	
	public String getImg_url() {
		return img_url;
	}
	
	
	@Column(name = "dCabinetPrices")	
	public Double getdCabinetPrices() {
		return dCabinetPrices;
	}

	@Column(name = "zCabinetPrices")	
	public Double getzCabinetPrices() {
		return zCabinetPrices;
	}

	@Column(name = "xCabinetPrices")	
	public Double getxCabinetPrices() {
		return xCabinetPrices;
	}

	public void setdCabinetPrices(Double dCabinetPrices) {
		this.dCabinetPrices = dCabinetPrices;
	}

	public void setzCabinetPrices(Double zCabinetPrices) {
		this.zCabinetPrices = zCabinetPrices;
	}

	public void setxCabinetPrices(Double xCabinetPrices) {
		this.xCabinetPrices = xCabinetPrices;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setCommunityAddress(String communityAddress) {
		this.communityAddress = communityAddress;
	}
	public void setCommunityUser(String communityUser) {
		this.communityUser = communityUser;
	}
	public void setCommunityContact(String communityContact) {
		this.communityContact = communityContact;
	}
	public void setCommunityPropertyCompany(String communityPropertyCompany) {
		this.communityPropertyCompany = communityPropertyCompany;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public void setCabinetName(String cabinetName) {
		this.cabinetName = cabinetName;
	}
	public void setSmallCount(Integer smallCount) {
		this.smallCount = smallCount;
	}
	
	
	

	
	
	
}
