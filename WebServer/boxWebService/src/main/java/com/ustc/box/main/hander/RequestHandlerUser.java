package com.ustc.box.main.hander;

import java.io.IOException;

import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ustc.box.core.utils.EhcacheHelper;
import com.ustc.box.core.utils.SecureUtils;
import com.ustc.box.core.utils.Send;
import com.ustc.box.main.service.LogService;
import com.ustc.box.main.service.UserService;
import com.ustc.box.main.vo.ErrorConstant;
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
public class RequestHandlerUser extends RequestHandler {

	private static final Logger LOGGER = Logger
			.getLogger(RequestHandlerUser.class);

	@Autowired
	private UserService userService;
	

	public String handle(RequestMessage msg) {
		LOGGER.debug(">>>handle(msg)");
		Gson gson = new Gson();
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		UserResponse res = new UserResponse();
		String type = msg.getRequestType().name();
		String tel = msg.getBase().getTel();
		// 获取验证码
		if (type.equals("validatecode")) {
			String validatecode = SecureUtils.gerValidatecode(4);
			String result = "";
			try {
				result = Send.sendValidateCodeByTel(tel, validatecode);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (result.indexOf("\"code\":0") == -1) {
				resp.setErrorCode(ErrorConstant.VALIDATECODEERROR);
				resp.setDesc("验证码发送失败");
			} else {
				Element e = new Element(tel, validatecode);
				EhcacheHelper.permissionCache.put(e);
				resp.fillSuccessResp();
				resp.setType(msg.getBase().getType());
				res.setValidatecode(validatecode);
				res.setTel(tel);
				resp.setRes(res);
			}

		} else if (type.equals("register")) {
			String validatecode = msg.getParam().get("validatecode");
			Element e = EhcacheHelper.permissionCache.get(tel);
			// 没有输入验证码
			if (e == null || !e.getValue().equals(validatecode)) {
				resp.setErrorCode(ErrorConstant.NOVALIDATECODE);
				resp.setDesc("验证码错误或无效");
			} else {
				UserResponse user = getUserInfo(msg);
				String token = SecureUtils.getTokenInfo();
				user.setToken(token);
				// 反正重新注册
				Integer isExist = userService.isExistedNumber(user.getTel());
				if (isExist > 0) {
					resp.setErrorCode(ErrorConstant.TELHASREG);
					resp.setDesc("号码已经被注册，请登陆");
				} else {
					userService.saveUserBaseInfo(user);
					resp.fillSuccessResp();
					resp.setRes(user);
					EhcacheHelper.put(tel, token);
				}
			}
		} else if (type.equals("detailuserinfo")) {
			UserResponse user = getUserInfo(msg);
			userService.updateUserBaseInfo(user);
			resp.fillSuccessResp();
			resp.setRes(user);
		} else if (type.equals("login")) {
			// 登陆
			String validatecode = msg.getParam().get("validatecode");
			UserResponse user = getUserInfo(msg);
			if (StringUtils.isEmpty(validatecode)) {
				resp.setErrorCode(ErrorConstant.NOPERMIT);
				resp.setDesc("用户无权限");
				resp.setRes(user);
			} else {
				Element e = EhcacheHelper.permissionCache.get(tel);
				// 没有输入验证码
				if (e == null || !e.getValue().equals(validatecode)) {
					resp.setErrorCode(ErrorConstant.NOVALIDATECODE);
					resp.setDesc("验证码错误或无效");
				} else {
					String token = userService.queryTokenByTel(tel);
					if(StringUtils.isEmpty(token)){
						user.setIsFirstLogin(true);
					}else{
						user.setIsFirstLogin(false);
					}
					token = userService.loginTel(user);
					EhcacheHelper.put(tel, token);
					resp.fillSuccessResp();
					user.setTel(tel);
					user.setToken(token);
					UserResponse rUser = userService.getUserInfoByTel(tel);
					rUser.setIsFirstLogin(user.getIsFirstLogin());
					rUser.setIsneedcomplete(userService.isNeedComplete(rUser));
					resp.setRes(rUser);
				}
			}
		} else if (type.equals("userinfo")) {
			UserResponse user = userService.getUserInfoByTel(msg.getBase().getTel());
			user.setIsneedcomplete(userService.isNeedComplete(user));
			resp.fillSuccessResp();
			resp.setRes(user);
		} else if (type.equals("updateuserinfo")) {
			UserResponse user = getUserInfo(msg);
			userService.updateUserBaseInfo(user);
			resp.fillSuccessResp();
			resp.setRes(user);
		}
		LOGGER.debug("<<<handle(msg)");
		return gson.toJson(resp);
	}
}
