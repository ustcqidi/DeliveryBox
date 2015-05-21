package com.ustc.box.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.subject.WebSubject;

import com.ustc.box.shiro.ShiroUser;
/**
 * 
 * @author kjhe
 *
 */
public class RequestUtils {
    /**
     * 将请求中的参数转化为Map
     * @param request
     * @return
     */
   public static Map<String,String> parameterToMap(HttpServletRequest request){
       Map<String , String> params = new HashMap<String, String>();
       Enumeration<String> paramNames = request.getParameterNames();
       while (paramNames.hasMoreElements()) {
           String paramName = paramNames.nextElement();
           params.put(paramName, request.getParameter(paramName));
       }
       return params;
   }
   
	 /**
	  * 根据dataTable的排序字段，拼接排序sql
	 * @param sb
	 * @param requestMap
	 * @return 
	 */
	public  static  void setTableSort(StringBuilder sb,Map<String, String> requestMap){
	    	try{
	    	String  sSortDir_0 = requestMap.get("sSortDir_0");
	 	    String  sColumn =  requestMap.get("sColumns").split(",")[Integer.valueOf(requestMap.get("iSortCol_0"))];
	 	    if(StringUtils.isNotEmpty(sColumn))
	 	    sb.append(" order by "+sColumn+" "+sSortDir_0);
	    	}catch (RuntimeException e) {
	    		e.printStackTrace();
			}
	    	
	    }	
	

	public static ShiroUser getCurrentShiroUser() {
		try {
			WebSubject ws = (WebSubject) SecurityUtils.getSubject();
			return (ShiroUser) ws.getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
