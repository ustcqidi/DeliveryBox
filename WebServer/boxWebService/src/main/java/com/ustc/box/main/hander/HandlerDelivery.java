package com.ustc.box.main.hander;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.ustc.box.core.utils.BizUtils;
import com.ustc.box.core.utils.SecureUtils;
import com.ustc.box.core.utils.Send;
import com.ustc.box.main.service.DeliveryService;
import com.ustc.box.main.service.LogService;
import com.ustc.box.main.service.UserAccountService;
import com.ustc.box.main.service.UserService;
import com.ustc.box.main.vo.BoxInfo;
import com.ustc.box.main.vo.DeliveryResponse;
import com.ustc.box.main.vo.ErrorConstant;
import com.ustc.box.main.vo.PickupConstant;
import com.ustc.box.main.vo.RecordInfo;
import com.ustc.box.main.vo.RequestMessage;
import com.ustc.box.main.vo.ResponseBase;
import com.ustc.box.main.vo.UserResponse;

/**
 * Banner获取处理类
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional
public class HandlerDelivery extends RequestHandler {

	private static final Logger LOGGER = Logger
			.getLogger(HandlerDelivery.class);

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private LogService logService;

	

	@Autowired
	private UserService userService;

	@Autowired
	private UserAccountService userAccountService;

	@Transactional
	public String handle(RequestMessage msg) {
		LOGGER.debug(">>>handle(msg)");
		Gson gson = new Gson();
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		String type = msg.getRequestType().name();
		String tel = msg.getBase().getTel();
		String cabinetId = msg.getParam().get("cabinetId");
		String cabinetType = msg.getParam().get("cabinetType");
		String receivetel = msg.getParam().get("receivetel");// 收件人手机号
		String expressNumber = msg.getParam().get("expressNumber");// 收件人手机号
		if (type.equals("deliveryreq")) {
			// step1,首先打开用户未确认的柜子，不收取费用
			Integer boxId = deliveryService.getNoConfirmDeliveryBox(tel,
					cabinetId);
			BoxInfo boxInfo = null;
			// 如果没有未确认的柜子就打开新的柜子
			Boolean isPay = true;
			if (boxId == null) {
				boxInfo =  deliveryService.getDeliveryBox(cabinetId,
						Integer.parseInt(cabinetType));
			}else{
				isPay = false;//不收取费用
				boxInfo = new BoxInfo();
				boxInfo.setBoxId(boxId);
				boxInfo.setPrice(null);
			}
			
			if (boxInfo == null) {
				resp.setErrorCode("000010");
				resp.setDesc("该类型的格子已满");
				LOGGER.debug("<<<handle(msg)");
				return gson.toJson(resp);
			}
			// 查看用户是否足够的钱
			Boolean isHasMoney = userAccountService.isHasEnoughMoney(tel,
					boxInfo.getPrice(), 1);
			if (!isHasMoney) {
				resp.setErrorCode(ErrorConstant.NOMONEY);
				resp.setDesc("余额不足");
				LOGGER.debug("<<<handle(msg)");
				return gson.toJson(resp);
			}
			// step5请求打开柜子
			Boolean result = BizUtils.openBox(cabinetId, boxInfo.getBoxId());
			if (result) {
				//表示是快递员打开的之前的柜子
				if(!isPay){
					//将之前的柜子状态更新
					deliveryService.updateRecordBack(cabinetId, boxInfo.getBoxId(), tel);
				}
				
				Integer recordId = deliveryService.deliveryed(cabinetId, boxInfo.getBoxId(),
						expressNumber, tel, receivetel,boxInfo.getPrice());
				deliveryService.updateCabinetStatus(cabinetId, boxInfo.getBoxId(), 1);
				Double deliveryToMoney = boxInfo.getPrice();
				// 扣除该用户对于的钱,对于未确认的情况，重新打开不收取快递员费用
				if(isPay&&deliveryToMoney!=null){
					userAccountService.deductMoney(deliveryToMoney, tel, recordId,cabinetId,boxInfo.getBoxId());
				}
				resp.fillSuccessResp();
			} else {
				resp.setErrorCode(ErrorConstant.OPENERROR);
				resp.setDesc("打开柜子出错");
			}

			logService.recordOpenInfo(cabinetId, boxInfo.getBoxId(), type, tel, result);
			DeliveryResponse dr = new DeliveryResponse();
			dr.setCabinetId(cabinetId);
			dr.setBoxId(boxInfo.getBoxId());
			dr.setTel(tel);
			resp.setRes(dr);

		} else if (type.equals("validatefull")) {
			BoxInfo boxInfo = null;
			boxInfo = deliveryService.getDeliveryBox(cabinetId,
					Integer.parseInt(cabinetType));
			if (boxInfo == null) {
				resp.setErrorCode(ErrorConstant.ISFULL);
				resp.setDesc("该类型的格子已满");
			} else {
				resp.fillSuccessResp();
				DeliveryResponse dr = new DeliveryResponse();
				dr.setBoxId(boxInfo.getBoxId());
				dr.setCabinetId(cabinetId);
				dr.setTel(tel);
				dr.setPrice(boxInfo.getPrice());
				resp.setRes(dr);
			}

		} else if (type.equals("clicksuccess")) {
			Integer boxId = Integer.parseInt(msg.getParam().get("boxId"));
			String validatecode = SecureUtils.gerValidatecode(4);
			deliveryService.pdateDeliveryBox(tel, cabinetId, boxId,
					validatecode);
			List<Map<String,Object>> address = deliveryService.getAddress(cabinetId, boxId);
			try {
				if(address.size() > 0){
					UserResponse up = userService.getUserInfoByTel(tel);
					Send.sendUserCodeByTel(receivetel, validatecode, expressNumber,
							address.get(0).get("cabinetName").toString(),boxId,up.getCompany());
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			resp.fillSuccessResp();
			DeliveryResponse dr = new DeliveryResponse();
			dr.setCabinetId(cabinetId);
			dr.setBoxId(boxId);
			dr.setTel(tel);
			resp.setRes(dr);

		} else if (type.equals("clickback")) {
			Integer boxId = Integer.parseInt(msg.getParam().get("boxId"));
			RecordInfo record = deliveryService.backDeliveryBox(tel, cabinetId, boxId);
			userAccountService.recordPayCancel(record.getId());
			userAccountService.addMoney(record.getDeliveryToMoney(), tel,-1);//-1代表用户取消返回退款
			resp.fillSuccessResp();
			DeliveryResponse dr = new DeliveryResponse();
			dr.setCabinetId(cabinetId);
			dr.setBoxId(boxId);
			dr.setTel(tel);
			resp.setRes(dr);
		} else if (type.equals("deliverying")) {
			List<Integer> queryState = new ArrayList<Integer>();
			queryState.add(PickupConstant.confirm);
			String page = msg.getParam().get("page");
			List<Map<String, Object>> list = deliveryService.queryDeliving(tel,
					queryState,page);
			resp.fillSuccessResp();
			DeliveryResponse dr = new DeliveryResponse();
			dr.setStatus("deliverying");
			dr.setTel(tel);
			dr.setList(list);
			resp.setRes(dr);

		} else if (type.equals("deliverypickup")) {
			Integer boxId = Integer.parseInt(msg.getParam().get("boxId"));
			Boolean hasOpen = deliveryService.checkHasOpened(tel, cabinetId, boxId);
			if(!hasOpen){
				resp.setErrorCode(ErrorConstant.SCANERROR);
				resp.setDesc("该柜子不存在该用户的快递");
				LOGGER.debug("<<<handle(msg)");
				return gson.toJson(resp);
			}
			Boolean result = BizUtils.openBox(cabinetId, boxId);
			if (result) {
				// 得到延期的天数
				Integer days = deliveryService.getOverDayCount(tel, cabinetId,
						boxId);
				deliveryService.deliverypickup(tel, cabinetId, boxId,
						days);
				resp.fillSuccessResp();
				DeliveryResponse dr = new DeliveryResponse();
				dr.setTel(tel);
				dr.setBoxId(boxId);
				resp.setRes(dr);
			} else {
				resp.setErrorCode(ErrorConstant.OPENERROR);
				resp.setDesc("打开柜子出错");
			}

		} else if (type.equals("deliveryed")) {
			List<Integer> queryState = new ArrayList<Integer>();
			queryState.add(PickupConstant.deliveryopen);
			queryState.add(PickupConstant.useropen);
			queryState.add(PickupConstant.helpopen);
			String page = msg.getParam().get("page");
			List<Map<String, Object>> list = deliveryService.queryDeliving(tel,queryState,page);
			resp.fillSuccessResp();
			DeliveryResponse dr = new DeliveryResponse();
			dr.setTel(tel);
			dr.setStatus("deliveryed");
			dr.setList(list);
			resp.setRes(dr);
		}

		LOGGER.debug("<<<handle(msg)");
		return gson.toJson(resp);
	}

}
