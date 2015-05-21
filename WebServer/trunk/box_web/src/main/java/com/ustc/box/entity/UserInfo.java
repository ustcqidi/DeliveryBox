package com.ustc.box.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;





@Entity
@Table(name = "admin_user")
public class UserInfo {
	private Integer id;
	private String name;
	private String trueName;
	private String password;
	private String des;
	private List<AdminUserResource> resourceList;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")	
	public String getName() {
		return name;
	}

	@Column(name = "password")	
	public String getPassword() {
		return password;
	}

	@Column(name = "des")	
	public String getDes() {
		return des;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Column(name = "trueName")	
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	@OneToMany(mappedBy="userInfo",cascade = {
			javax.persistence.CascadeType.PERSIST,
			javax.persistence.CascadeType.MERGE},fetch=FetchType.EAGER)
	public List<AdminUserResource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<AdminUserResource> resourceList) {
		this.resourceList = resourceList;
	}

	

	    
	
	
	
}
