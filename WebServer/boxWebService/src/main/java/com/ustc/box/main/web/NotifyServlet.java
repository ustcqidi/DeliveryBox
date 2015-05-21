package com.ustc.box.main.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.ustc.box.core.utils.RequestUtils;
import com.ustc.box.main.service.UserAccountService;

@SuppressWarnings("serial")
public class NotifyServlet extends ServletBase {
	private static final Logger LOGGER = LogManager
			.getLogger(NotifyServlet.class);

	@Autowired
	private UserAccountService userAccountService;

	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
				config.getServletContext());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("收到支付宝异步通知消息......");
		Map params = new HashMap();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = valueStr + values[i] + ",";
			}
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
		}

		long oldTime = System.currentTimeMillis();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		Map<String,String> param = new HashMap<String, String>();
		param = RequestUtils.parameterToMap(request);
		if(param.get("trade_status").equalsIgnoreCase("TRADE_SUCCESS")){
			userAccountService.saveUserPayMoneyInfo(param);
		}
		LOGGER.info("消息参数："+param);
		output(response, "success");
		long newTime = System.currentTimeMillis();
		LOGGER.info("<<< doPost(request,response)(time:" + (newTime - oldTime)
				+ ")");
	}

}
