package com.ustc.box.main.hander;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ustc.box.main.vo.RequestMessageType;

@Service
public class RequestHandlerFactory {
	@Autowired
	RequestHandlerDefault defaultHandler;
	@Autowired
	RequestHandlerUser requestHandlerUser;
	@Autowired
	HandlerBoxRegister boxRegisterHandlerUser;
	@Autowired
	HandlerDelivery handlerDelivery;
	@Autowired
	HandlerTake handlerTake;
	@Autowired
	HandlerAccount handlerAccount;

	public RequestHandler getHandler(RequestMessageType type) {
		switch (type) {
		case login:
			return requestHandlerUser;
		case register:
			return requestHandlerUser;
		case detailuserinfo:
			return requestHandlerUser;
		case validatecode:
			return requestHandlerUser;
		case userinfo:
			return requestHandlerUser;
		case updateuserinfo:
			return requestHandlerUser;
		case deliveryreq:
			return handlerDelivery;
		case validatefull:
			return handlerDelivery;
		case clicksuccess:
			return handlerDelivery;
		case clickback:
		case deliverying:
			return handlerDelivery;
		case deliverypickup:
			return handlerDelivery;
		case deliveryed:
			return handlerDelivery;
		case userpickup:
			return handlerTake;
		case useropenbox:
			return handlerTake;
		case chargerecord:
			return handlerAccount;
		case nearbydelivery:
			return handlerTake;
		default:
			return defaultHandler;
		}
	}
}
