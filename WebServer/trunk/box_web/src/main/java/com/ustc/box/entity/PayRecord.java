package com.ustc.box.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "pay_record")
public class PayRecord {
	
	private Integer id;
	private String out_trade_no;
	private String trade_status;
	private String buyer_id;
	private String buyer_email;
	private String gmt_payment;
	private String total_fee;
	private String userTel;
	private String pay_balance;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "out_trade_no")
	public String getOut_trade_no() {
		return out_trade_no;
	}

	@Column(name = "trade_status")
	public String getTrade_status() {
		return trade_status;
	}

	@Column(name = "buyer_id")
	public String getBuyer_id() {
		return buyer_id;
	}

	@Column(name = "buyer_email")
	public String getBuyer_email() {
		return buyer_email;
	}

	@Column(name = "gmt_payment")
	public String getGmt_payment() {
		return gmt_payment;
	}

	@Column(name = "total_fee")
	public String getTotal_fee() {
		return total_fee;
	}

	@Column(name = "userTel")
	public String getUserTel() {
		return userTel;
	}

	@Column(name = "pay_balance")
	public String getPay_balance() {
		return pay_balance;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public void setPay_balance(String pay_balance) {
		this.pay_balance = pay_balance;
	}

	

	
	
	
}
