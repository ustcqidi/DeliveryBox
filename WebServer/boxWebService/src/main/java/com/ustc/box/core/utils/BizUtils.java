/**
 * 
 */
package com.ustc.box.core.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.ustc.box.main.hander.HandlerDelivery;



/**
 * @author ZGP
 *
 * 
 */
public class BizUtils {
	private static String url = PropertiesUtils.getProperty("openbox_url");
	private static String isOpenBox = PropertiesUtils.getProperty("isOpenBox");
	private static final Log LOG = LogFactory.getLog(BizUtils.class);
	private static final Logger LOGGER = Logger
			.getLogger(BizUtils.class);
	
    public static Boolean openBox(String cabinetId,Integer bixId){
    if(!isOpenBox.equals("true")){
    	return true;
    }
    String result = null;
    try{
    	 String openurl = url+"?cabinetid="+cabinetId+"&boxid="+bixId;
    	 LOGGER.info("发起开柜请求url:---"+openurl);
    	 result =HttpUtils.sendGet(openurl);
    	 LOGGER.info("开柜返回信息:---"+result);
    	 
    }catch (Exception e) {
    	LOG.error("打开柜子出错...");
	}
	   Map<String,Object> map = JsonParser.generateMapByJson(result);
	   if(StringUtils.isEmpty(result)){
		   return false;
	   }
	   if(map.get("errorCode").toString() .equals("0")){
		   return true;
	   }else{
		   return false;
	   }
   }
}
