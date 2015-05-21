package com.ustc.box.main.vo;

public class UserResponse extends ResponseData{
     private String tel;
     private String username;
     private String password;
     private String courier;
     private String company;
     private String delivery_price_desc;
     private Boolean isneedcomplete;
     private String avatar_url;
     private String validatecode ;
     private String type;// 用户类型，
 	private String imei;// Sim卡号
 	private String token;//
 	private String balance;//
 	private Boolean isFirstLogin;
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getValidatecode() {
		return validatecode;
	}
	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}
	public String getType() {
		return type;
	}
	public String getImsi() {
		return imei;
	}
	public String getToken() {
		return token;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getImei() {
		return imei;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getDelivery_price_desc() {
		return delivery_price_desc;
	}
	public void setDelivery_price_desc(String delivery_price_desc) {
		this.delivery_price_desc = delivery_price_desc;
	}
	public Boolean getIsneedcomplete() {
		return isneedcomplete;
	}
	public void setIsneedcomplete(Boolean isneedcomplete) {
		this.isneedcomplete = isneedcomplete;
	}
	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}
	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}
	
	
     
    
     
}
