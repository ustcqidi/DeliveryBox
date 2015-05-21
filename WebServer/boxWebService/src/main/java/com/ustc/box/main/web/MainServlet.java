package com.ustc.box.main.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.google.gson.Gson;
import com.ustc.box.core.utils.EhcacheHelper;
import com.ustc.box.main.hander.RequestHandler;
import com.ustc.box.main.hander.RequestHandlerFactory;
import com.ustc.box.main.service.LogService;
import com.ustc.box.main.service.UserService;
import com.ustc.box.main.vo.ErrorConstant;
import com.ustc.box.main.vo.RequestMessage;
import com.ustc.box.main.vo.RequestMessageType;
import com.ustc.box.main.vo.ResponseBase;



@SuppressWarnings("serial")
public class MainServlet extends ServletBase {
	private static final Logger LOGGER = LogManager.getLogger(MainServlet.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private RequestHandlerFactory requestHandlerFactory;

	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info(">>> doPost(request,response)");
		long oldTime = System.currentTimeMillis();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		// 1. 解析请求url上的cvt参数
		String c = request.getParameter("c");
		if (c == null) {
			output(response, "cmd参数不全！");
			long newTime = System.currentTimeMillis();
			LOGGER.info("<<< doPost(request,response)(版本时间戳cmd参数不全!)(time:"+(newTime-oldTime)+")");
			return;
		}
		// 2. 获取post请求传递的参数并解析
		byte[] buffer = readInputStream(request.getInputStream());
		String json = new String(buffer, "UTF-8");
		LOGGER.info(String.format("收到消息：%s", json));
		logService.logRequestJsonInfo(json);
		RequestMessage param = null;
		String result = "";
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		Gson gson = new Gson();
		try {
			param = gson.fromJson(json, RequestMessage.class);
			param.setRequestType(RequestMessageType.parseValue(param.getCmd()));
		} catch (Exception ex) {
			LOGGER.error(String.format("收到消息格式解析错误：%s", json), ex);
		}
		
		if (param == null || param.getBase() == null) {
			resp.setErrorCode("999998");
			resp.setDesc("解析的post参数为空或者格式错误");
			resp.setType(param.getBase().getType());
		} else {
			// 3.权限控制
		 	Boolean isPermited = secureHandle(param);
            if(!isPermited){
            	resp.setErrorCode(ErrorConstant.NOPERMIT);
            	resp.setDesc("用户无权限");
            }else{
            	RequestHandler handler = requestHandlerFactory.getHandler(param
    					.getRequestType());
    			try {
    				result = handler.handle(param);
    			} catch (Exception e) {
    				resp.setErrorCode("999997");
    				resp.setDesc("请求处理过程中出现异常，请求参数：" + json);
    				LOGGER.error("请求处理过程中出现异常，请求参数：" + json, e);
    			}
            }
			// 4.调用运营服务，获取返回结果
		}

		if (StringUtils.isEmpty(result)) {
			result = gson.toJson(resp);
		}

		//记录返回日志
		logService.logResponseMessage(result);
		// 5.把相应结果返回客户端
		LOGGER.info(String.format("返回消息：%s", result));
		output(response, result);
		long newTime = System.currentTimeMillis();
		LOGGER.info("<<< doPost(request,response)(time:"+(newTime-oldTime)+")");
	}

	private Boolean secureHandle(RequestMessage param) {
		String token = param.getBase().getToken();
		String tel = param.getBase().getTel();
		if(StringUtils.isEmpty(tel)){
			tel = param.getParam().get("tel");
		}
		if(tel.length() > 11){
			tel = tel.substring(tel.length() - 11, tel.length());
		}
		param.getBase().setTel(tel);
		
		if(param.getCmd().equals("register")||param.getCmd().equals("validatecode")||
				param.getCmd().equals("login")){
			return true;
		}
		LOGGER.debug(">>> preHandle auth (gson,param)");
	
		if(StringUtils.isEmpty(token)||StringUtils.isEmpty(tel)){
			LOGGER.debug(">>> 鉴权参数不全");
			return false;
		}
		try {
			Object cachedToken = EhcacheHelper.get(tel);
			if(token.equals(cachedToken)){
				return true;
			}else{
				int count = userService.getUserByToken(token,tel);
				if(count > 0){
					EhcacheHelper.put(tel,token);
					return true;
				}else{
					return false;
				}
			}
			
		} catch (Exception ioe) {
			LOGGER.error(
					String.format("鉴权失败，Token为："+token),
					ioe);
			return false;
		}
	}
}
