package com.ustc.box.main.vo;

public enum RequestMessageType {
	register("1001"), validatecode("1002"), login("1003"), detailuserinfo(
			"1004"), userinfo("1005"), deliveryreq("1006"), validatefull("1007"), clicksuccess(
			"1008"), deliverying("1009"), deliverypickup("1010"), deliveryed(
			"1011"), updateuserinfo("1012"), userpickup("1013"), useropenbox(
			"1014"), clickback("1015"),chargerecord("1016"),nearbydelivery("1017"),unknown("unknown");
	private String value;

	RequestMessageType(String cmd) {
		this.value = cmd;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static RequestMessageType parseValue(String value) {
		for (RequestMessageType type : RequestMessageType.values()) {
			// System.out.println(type.value);
			if (type.name().equals(value))
				return type;
		}
		return unknown;
	}
}
