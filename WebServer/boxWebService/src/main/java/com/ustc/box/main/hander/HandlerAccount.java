package com.ustc.box.main.hander;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ustc.box.main.service.UserAccountService;
import com.ustc.box.main.vo.ChargeResponse;
import com.ustc.box.main.vo.RequestMessage;
import com.ustc.box.main.vo.ResponseBase;

/**
 * Banner获取处理类
 * 
 * @author Administrator
 * 
 */
@Service
public class HandlerAccount extends RequestHandler {

	private static final Logger LOGGER = Logger
			.getLogger(HandlerAccount.class);

	
	@Autowired
	private UserAccountService userAccountService;

	public String handle(RequestMessage msg) {
		LOGGER.debug(">>>handle(msg)");
		Gson gson = new Gson();
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		ChargeResponse res = new ChargeResponse();
		String type = msg.getRequestType().name();
		String tel = msg.getBase().getTel();
		// 获取验证码
	    if (type.equals("chargerecord")) {
			List<Map<String,Object>> pdata = userAccountService.queryPayRecord(tel);
			List<Map<String,Object>> cdata = userAccountService.queryChargeRecord(tel);
			res.setTel(msg.getBase().getTel());
			res.setPayTotal(userAccountService.queryPayTot(tel).toString());
			res.setPdata(pdata);
			res.setCdata(cdata);
			resp.setRes(res);
			resp.fillSuccessResp();
		}
		LOGGER.debug("<<<handle(msg)");
		return gson.toJson(resp);
	}
}
