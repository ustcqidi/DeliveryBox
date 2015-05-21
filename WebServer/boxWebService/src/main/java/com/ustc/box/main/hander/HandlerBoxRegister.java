package com.ustc.box.main.hander;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ustc.box.core.utils.ParamConstant;
import com.ustc.box.main.service.BoxRegisterService;
import com.ustc.box.main.vo.ResponseBase;

/**
 * Banner获取处理类
 * 
 * @author Administrator
 * 
 */
@Service
public class HandlerBoxRegister {

	private static final Logger LOGGER = Logger
			.getLogger(HandlerBoxRegister.class);

	@Autowired
	private BoxRegisterService boxRegisterService;

	public String handle(Map<String, String> msg) {
		LOGGER.debug(">>>handle(msg)");
		Gson gson = new Gson();
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		String cabinetId = msg.get("cabinetId");
		String type = msg.get("c");
		String province = msg.get("province");
		String city = msg.get("city");
		String region = msg.get("region");
		String communityName = msg.get("communityName");
		String cabinetName = msg.get("cabinetName");
		String smallCount = msg.get("smallCount");

		if ("2002".equals(type)) {
			if (boxRegisterService.isRegister(cabinetId)) {
				resp.setErrorCode("000001");
				resp.setDesc("该柜子已经被注册了");
			}else{
				resp.fillSuccessResp();
			}
			LOGGER.debug("<<<handle(msg)");
			return gson.toJson(resp);
		} else {
			// 注册柜子
			if (boxRegisterService.isRegister(cabinetId)) {
				resp.setErrorCode("000001");
				resp.setDesc("该柜子已经被注册了");
				LOGGER.debug("<<<handle(msg)");
				return gson.toJson(resp);
			}
			// 注册柜子
			boxRegisterService.registerBox(cabinetId, province, city, region,
					communityName, cabinetName, smallCount);

			// 注册主柜唯一的大格子,对于2号大柜子
			boxRegisterService.addbox_cabinet(cabinetId, 1, 2, 0, 1);

			// 注册4、6、8小柜子
			boxRegisterService.addbox_cabinet(cabinetId, 3, 4, 0, 1);
			boxRegisterService.addbox_cabinet(cabinetId, 3, 6, 0, 1);
			boxRegisterService.addbox_cabinet(cabinetId, 3, 8, 0, 1);
			// 9到14是小柜子
			for (int i = 9; i <= 14; i++) {
				boxRegisterService.addbox_cabinet(cabinetId, 3, i, 0, 1);
			}
			// 15到20是中柜子
			for (int i = 15; i <= 20; i++) {
				boxRegisterService.addbox_cabinet(cabinetId, 2, i, 0, 1);
			}

			// 以下是处理副柜子的情况
			// 大柜前两个
			int count = 21;// 从21号开始
			int typeCount = 2; //小柜的类型
			for (int j = 0; j < Integer.parseInt(smallCount); j++) {

				// 副柜大柜子
				for (int i = 1; i <= ParamConstant.BDCabinetSize; i++) {
					boxRegisterService
							.addbox_cabinet(cabinetId, 1, count, 0, typeCount);
					count++;
				}

				// 副柜子小柜
				for (int i = 1; i <= ParamConstant.BXCabinetSize; i++) {
					boxRegisterService
							.addbox_cabinet(cabinetId, 3, count, 0, typeCount);
					count++;
				}

				for (int i = 1; i <= ParamConstant.BZCabinetSize; i++) {
					boxRegisterService
							.addbox_cabinet(cabinetId, 2, count, 0, typeCount);
					count++;
				}
				
				typeCount++;
			}
			resp.fillSuccessResp();
			LOGGER.debug("<<<handle(msg)");
			return gson.toJson(resp);

		}
	
	}
}
