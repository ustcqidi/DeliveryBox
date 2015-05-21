package com.ustc.box.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "sys_params")
public class SysParams {
	
	private Integer id;
	private Integer overday;
	private Double dCabinetPrices;
	private Double zCabinetPrices;
	private Double xCabinetPrices;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOverday() {
		return overday;
	}

	public Double getdCabinetPrices() {
		return dCabinetPrices;
	}

	public Double getzCabinetPrices() {
		return zCabinetPrices;
	}

	public Double getxCabinetPrices() {
		return xCabinetPrices;
	}

	public void setOverday(Integer overday) {
		this.overday = overday;
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


	
	
	
}
