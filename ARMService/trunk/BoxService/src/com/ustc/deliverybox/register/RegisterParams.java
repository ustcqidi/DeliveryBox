package com.ustc.deliverybox.register;

public class RegisterParams {
	public String imei;
	public String cabinetType;
	public String cabinetSort;
	public String province;
	public String city;
	public String region;
	public String cabinetId;
	public String communityAddress;
	public String communityUser;
	public String communityContact;
	public String communityPropertyCompany;
	public String manager;
	
	public String cabinetName;
	
	public String communityName;
	
	public String  smallCount;
	
	public RegisterParams()
	{
		this.imei = "";
		this.cabinetSort = "";
		this.cabinetType = "";

		this.province = "";
		this.city = "";
		this.region = "";
		
		this.communityAddress = "";
		this.communityContact = "";
		this.communityPropertyCompany = "";
		this.communityUser = "";
		this.manager = "";
		
		this.communityName = "";
		
		this.cabinetName = "";
	}
}
