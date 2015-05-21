package com.ustc.box.main.hander;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.ustc.box.core.utils.BizUtils;
import com.ustc.box.main.service.DeliveryService;
import com.ustc.box.main.service.LogService;
import com.ustc.box.main.service.TakeService;
import com.ustc.box.main.service.UserAccountService;
import com.ustc.box.main.vo.DeliveryResponse;
import com.ustc.box.main.vo.ErrorConstant;
import com.ustc.box.main.vo.NearByResponse;
import com.ustc.box.main.vo.PickupConstant;
import com.ustc.box.main.vo.RequestMessage;
import com.ustc.box.main.vo.ResponseBase;
import com.ustc.box.main.vo.TakeResponse;

/**
 * Banner获取处理类
 * 
 * @author Administrator
 * 
 */
@Service
@Transactional
public class HandlerTake extends RequestHandler {

	private static final Logger LOGGER = Logger.getLogger(HandlerTake.class);

	@Autowired
	private TakeService takeService;
	
	@Autowired
	private DeliveryService deliveryService;
	

	@Autowired
	private LogService logService;

	@Autowired
	private UserAccountService userAccountService;

	@Transactional
	public String handle(RequestMessage msg) {
		LOGGER.debug(">>>handle(msg)");
		Gson gson = new Gson();
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		String cmd = msg.getRequestType().name();
		String type = msg.getBase().getType();
		String tel = msg.getBase().getTel();
		String cabinetId = msg.getParam().get("cabinetId");
		if (cmd.equals("userpickup")) {
			List<Integer> confirmState = new ArrayList<Integer>();
			confirmState.add(PickupConstant.confirm);
			List<Integer> hasOpenState = new ArrayList<Integer>();
			hasOpenState.add(PickupConstant.helpopen);
			hasOpenState.add(PickupConstant.useropen);
			String page = msg.getParam().get("page");
			List<Map<String, Object>> listpickuping = takeService
					.queryDeliving(tel, confirmState,0,page);
			List<Map<String, Object>> listpickuped = takeService.queryDeliving(
					tel, hasOpenState,1,page);
			resp.fillSuccessResp();
			TakeResponse tr = new TakeResponse();
			tr.setTel(tel);
			tr.setListpickuping(listpickuping);
			tr.setListpickuped(listpickuped);
			resp.setRes(tr);
		} else if (cmd.equals("useropenbox")) {
			Integer boxId = Integer.parseInt(msg.getParam().get("boxId"));
			//得到延期的天数
			Integer days = takeService.getOverDayCount(tel, cabinetId, boxId);
			//首先判断用户有没有扫描错柜子
			if(days == null){
				resp.setErrorCode(ErrorConstant.SCANERROR);
				resp.setDesc("扫描错柜子,请到正确的地点扫描");
				resp.setRes(null);
				LOGGER.debug("<<<handle(msg)");
				return gson.toJson(resp);
			}
			
//			//没有延期，表示可以直接打开柜子
//			if(days > Integer.parseInt(maxOverDay)){
//				// 验证账户是否有余额
//				Double accountMount = userAccountService
//						.getUserAccountRemaind(tel);
//				// 验证账户余额是否满足格子的价格
//				Boolean isHasMoney = userAccountService
//						.accountRemaindIsOverPrice(accountMount,selectcabinetType,days);
//				if (!isHasMoney) {
//					resp.setErrorCode(ErrorConstant.NOMONEY);
//					resp.setDesc("余额不足");
//					LOGGER.debug("<<<handle(msg)");
//					logService.recordInfo(msg, resp);
//					return gson.toJson(resp);
//				}
//			}

			Boolean result = BizUtils.openBox(cabinetId, boxId);
			logService.recordOpenInfo(cabinetId, boxId, type, tel, result);
			if (result) {
				// 扣除该用户的钱
//				Double deliveryToMoney = userAccountService
//						.getCabinetPriceByType(selectcabinetType, days);
				// 更新开柜的信息
				takeService.pickedDeliveryBox(tel, cabinetId, boxId, days,
						null,PickupConstant.useropen);
				// 更新用户的账户信息
				resp.fillSuccessResp();
				DeliveryResponse dr = new DeliveryResponse();
				dr.setBoxId(boxId);
				dr.setCabinetId(cabinetId);
				dr.setTel(tel);
				resp.setRes(dr);
			} else {
				resp.setErrorCode(ErrorConstant.OPENERROR);
				resp.setDesc("打开柜子出错");
			}
		}else if(cmd.equals("nearbydelivery")){
			//第一次使用的用户获取不到
			List<String> cabinetIds = takeService.getCabinetList(tel);
			resp.fillSuccessResp();
			NearByResponse nb = new NearByResponse();
			nb.setTel(tel);
			if(cabinetIds!=null&cabinetIds.size()>0){
				 List<Map<String,Object>> datas = takeService.getNearDeliveryList(cabinetIds,tel);
				 nb.setData(datas);
			}else{
				 nb.setData(new ArrayList<Map<String, Object>>());
			}
			resp.setRes(nb);
			
		}
		LOGGER.debug("<<<handle(msg)");
		return gson.toJson(resp);
	}

}
