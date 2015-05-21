package com.ustc.box.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "charge_record")
public class ChargeRecord {
	
	private Integer id;
	private String tel;
	private Double money;
	private Date addDate;
	private Integer charge_type;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "tel")
	public String getTel() {
		return tel;
	}

	@Column(name = "money")
	public Double getMoney() {
		return money;
	}

	@Column(name = "addDate")
	public Date getAddDate() {
		return addDate;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	@Column(name = "charge_type")
	public Integer getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(Integer charge_type) {
		this.charge_type = charge_type;
	}




	

	
	
	
}
