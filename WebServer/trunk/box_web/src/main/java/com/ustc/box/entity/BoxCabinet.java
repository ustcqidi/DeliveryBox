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
@Table(name = "box_cabinet")
public class BoxCabinet {
	
	private String cabinetId;
	private Integer cabinetNumber;
	private Integer cabinetType;//'格子类型 1大2中3小',
	private Integer cabinetIsBlank;
	private Integer maxDate;
	private Double cabinetPrice;
	private Integer status;
	private Integer type;
	private BoxInfo boxInfo;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")	
	public String getCabinetId() {
		return cabinetId;
	}


	@Column(name = "cabinetNumber")	
	public Integer getCabinetNumber() {
		return cabinetNumber;
	}


	public void setCabinetNumber(Integer cabinetNumber) {
		this.cabinetNumber = cabinetNumber;
	}

	@Column(name = "cabinetType")	
	public Integer getCabinetType() {
		return cabinetType;
	}


	public void setCabinetType(Integer cabinetType) {
		this.cabinetType = cabinetType;
	}

	@Column(name = "cabinetIsBlank")	
	public Integer getCabinetIsBlank() {
		return cabinetIsBlank;
	}


	public void setCabinetIsBlank(Integer cabinetIsBlank) {
		this.cabinetIsBlank = cabinetIsBlank;
	}

	@Column(name = "maxDate")	
	public Integer getMaxDate() {
		return maxDate;
	}


	public void setMaxDate(Integer maxDate) {
		this.maxDate = maxDate;
	}

	@Column(name = "cabinetPrice")
	public Double getCabinetPrice() {
		return cabinetPrice;
	}


	public void setCabinetPrice(Double cabinetPrice) {
		this.cabinetPrice = cabinetPrice;
	}


	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	@Column(name = "type")
	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	@ManyToOne
	@JoinColumn(name="cabinetId")
	public BoxInfo getBoxInfo() {
		return boxInfo;
	}


	public void setBoxInfo(BoxInfo boxInfo) {
		this.boxInfo = boxInfo;
	}


	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}
	
	
	
	

	
	
	
}
