package com.ustc.box.main.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import com.ustc.box.main.hander.HandlerBoxRegister;
import com.ustc.box.main.hander.PcHanderTake;
import com.ustc.box.main.hander.RequestHandler;
import com.ustc.box.main.hander.RequestHandlerFactory;
import com.ustc.box.main.service.UserService;
import com.ustc.box.main.vo.ErrorConstant;
import com.ustc.box.main.vo.RequestMessage;
import com.ustc.box.main.vo.RequestMessageType;
import com.ustc.box.main.vo.ResponseBase;



@SuppressWarnings("serial")
public class PcTakeServlet extends ServletBase {
	private static final Logger LOGGER = LogManager.getLogger(PcTakeServlet.class);
	
	
	@Autowired
	private PcHanderTake pcHanderTake;

	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}


	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info(">>> doPost(request,response)");
		long oldTime = System.currentTimeMillis();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		System.out.println("step1:-------"+params);
		// 2. 获取post请求传递的参数并解析
		byte[] buffer = readInputStream(request.getInputStream());
		String json = new String(buffer, "UTF-8");
		LOGGER.info(String.format("收到消息：%s", json));
		Map<String,String> param= new HashMap<String, String>();
		String result = "";
		ResponseBase resp = ResponseBase.getDefaultErrorResp();
		Gson gson = new Gson();
		try {
			param = gson.fromJson(json,Map.class);
		} catch (Exception ex) {
			LOGGER.error(String.format("收到消息格式解析错误：%s", json), ex);
		}
		
		if (param == null ) {
			resp.setErrorCode("999998");
			resp.setDesc("解析的post参数为空或者格式错误");
		} else {
    			try {
    				result = pcHanderTake.handle(param);
    			} catch (Exception e) {
    				resp.setErrorCode("999997");
    				resp.setDesc("请求处理过程中出现异常，请求参数：" + json);
    				LOGGER.error("请求处理过程中出现异常，请求参数：" + json, e);
    			}
		}

		if (StringUtils.isEmpty(result)) {
			result = gson.toJson(resp);
		}

		// 5.把相应结果返回客户端
		LOGGER.info(String.format("返回消息：%s", result));
		output(response, result);
		long newTime = System.currentTimeMillis();
		LOGGER.info("<<< doPost(request,response)(time:"+(newTime-oldTime)+")");
	}

}
