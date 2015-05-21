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
@Table(name = "consume_record")
public class ConsumeRecord {
	
	private Integer id;
	private String tel;
	private Double money;
	private Date addDate;
	private String cabinetId;
	private Integer cabinetNumber;
	private RecordInfo recordInfo;
	
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


	@OneToOne
	@JoinColumn(name="record_id")
	public RecordInfo getRecordInfo() {
		return recordInfo;
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


	public void setRecordInfo(RecordInfo recordInfo) {
		this.recordInfo = recordInfo;
	}

	@Column(name = "cabinetId")
	public String getCabinetId() {
		return cabinetId;
	}

	@Column(name = "cabinetNumber")
	public Integer getCabinetNumber() {
		return cabinetNumber;
	}

	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}

	public void setCabinetNumber(Integer cabinetNumber) {
		this.cabinetNumber = cabinetNumber;
	}



	

	
	
	
}
