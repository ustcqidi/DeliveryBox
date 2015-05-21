package com.ustc.box.main.hander;

import org.apache.commons.lang.StringUtils;

import com.ustc.box.main.vo.RequestMessage;
import com.ustc.box.main.vo.UserResponse;

public abstract class RequestHandler {

	public abstract String handle(RequestMessage msg) throws Exception;
    
	public UserResponse getUserInfo(RequestMessage msg){
		UserResponse ur = new UserResponse();
		if(StringUtils.isNotEmpty(msg.getBase().getTel())){
			ur.setTel(msg.getBase().getTel());
		}
		if(StringUtils.isNotEmpty(msg.getParam().get("username"))){
			ur.setUsername(msg.getParam().get("username"));
		}
		if(StringUtils.isNotEmpty(msg.getParam().get("password"))){
			ur.setPassword(msg.getParam().get("password"));
		}
		if(StringUtils.isNotEmpty(msg.getParam().get("courier"))){
			ur.setCourier(msg.getParam().get("courier"));
		}
		if(StringUtils.isNotEmpty(msg.getParam().get("company"))){
			ur.setCompany(msg.getParam().get("company"));
		}
		
		if(StringUtils.isNotEmpty(msg.getParam().get("avatar_url"))){
			ur.setAvatar_url(msg.getParam().get("avatar_url"));
		}
		
		if(StringUtils.isNotEmpty(msg.getParam().get("delivery_price_desc"))){
			ur.setDelivery_price_desc(msg.getParam().get("delivery_price_desc"));
		}
		
		if(StringUtils.isNotEmpty(msg.getBase().getImei())){
			ur.setImei(msg.getBase().getImei());
		}else{
			ur.setImei("");
		}
		if(StringUtils.isNotEmpty(msg.getBase().getToken())){
			ur.setToken(msg.getBase().getToken());
		}else{
			ur.setToken("");
		}
		if(StringUtils.isNotEmpty(msg.getBase().getType())){
			ur.setType(msg.getBase().getType());
		}else if(StringUtils.isNotEmpty(msg.getParam().get("type"))){
			ur.setType(msg.getParam().get("type"));
		}else{
			ur.setType("");
		}
		return ur;
	}
}
