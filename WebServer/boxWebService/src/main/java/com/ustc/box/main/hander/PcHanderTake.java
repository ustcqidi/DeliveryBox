package com.ustc.box.main.hander;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ustc.box.core.utils.BizUtils;
import com.ustc.box.core.utils.ParamConstant;
import com.ustc.box.main.service.BoxRegisterService;
import com.ustc.box.main.service.LogService;
import com.ustc.box.main.service.TakeService;
import com.ustc.box.main.vo.DeliveryResponse;
import com.ustc.box.main.vo.ErrorConstant;
import com.ustc.box.main.vo.PickupConstant;
import com.ustc.box.main.vo.ResponseBase;

/**
 * Banner获取处理类
 * 
 * @author Administrator
 * 
 */
@Service
public class PcHanderTake  {

	private static final Logger LOGGER = Logger
			.getLogger(PcHanderTake.class);
	
	@Autowired
    private TakeService takeService;
	
	@Autowired
	private LogService logService;

	public String handle(Map<String,String> msg) {
		LOGGER.debug(">>>handle(msg)");
		Gson gson = new Gson();
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		String tel = msg.get("tel");
		String cabinetId = msg.get("cabinetId");
		String validatecode = msg.get("validatecode");
		List<String> codes = takeService.getCodeList(tel, cabinetId);
		if (codes.size() == 0) {
			resp.setErrorCode(ErrorConstant.SCANERROR);
			resp.setDesc("该柜子不存在该用户的快递");
			LOGGER.debug("<<<handle(msg)");
			return gson.toJson(resp);
		} else if (!codes.contains(validatecode)) {
			resp.setErrorCode(ErrorConstant.NOVALIDATECODE);
			resp.setDesc("验证码错误");
			LOGGER.debug("<<<handle(msg)");
			return gson.toJson(resp);
		}

		Integer boxId = takeService.getHelpBoxId(tel, cabinetId,validatecode);
		Boolean result = BizUtils.openBox(cabinetId, boxId);
		logService.recordOpenInfo(cabinetId, boxId, "2", tel, result);
		if (result) {
			Integer days = takeService.getOverDayCount(tel, cabinetId, boxId);
			// 更新开柜的信息
			takeService.pickedDeliveryBox(tel, cabinetId, boxId, days, 0.0,PickupConstant.helpopen);
			// 更新用户的账户信息
		} else {
			resp.setErrorCode(ErrorConstant.OPENERROR);
			resp.setDesc("打开柜子出错");
			LOGGER.debug("<<<handle(msg)");
			return gson.toJson(resp);
		}
		resp.fillSuccessResp();
		DeliveryResponse dr = new DeliveryResponse();
		dr.setCabinetId(cabinetId);
		dr.setTel(tel);
		resp.setRes(dr);
		resp.fillSuccessResp();

		
		LOGGER.debug("<<<handle(msg)");
		return gson.toJson(resp);
	}
}
