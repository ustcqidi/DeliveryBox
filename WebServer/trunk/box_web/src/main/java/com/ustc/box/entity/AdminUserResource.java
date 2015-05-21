package com.ustc.box.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "admin_user_resource")
public class AdminUserResource {
	
	private Integer id;
	private UserInfo userInfo ;
	private String cabinetId;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    
	@Column(name = "cabinetId")
	public String getCabinetId() {
		return cabinetId;
	}

	
	@ManyToOne
	@JoinColumn(name="admin_user_id")
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

    
	
	
	
	
}
