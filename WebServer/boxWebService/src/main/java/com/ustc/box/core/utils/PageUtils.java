/**
 * 
 */
package com.ustc.box.core.utils;




/**
 * @author ZGP
 *
 * 
 */
public class PageUtils {
	private static String pageSize = PropertiesUtils.getDefaultProperty("pageSize","10");
	
    public static String getPageSQL(Integer page){
    	StringBuffer sb = new StringBuffer();
    	if(page <= 0){
    		return "";
    	}
    	
    	Integer start = (page-1)*Integer.parseInt(pageSize);
    	Integer end = page*Integer.parseInt(pageSize);
    	sb.append(" limit "+start+","+end);
    	return sb.toString();
   }
}
