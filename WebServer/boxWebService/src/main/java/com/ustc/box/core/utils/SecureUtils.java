package com.ustc.box.core.utils;

import java.util.UUID;

/**
 * @author gpzhang
 *
 * @date : 2014-11-28
 */
public class SecureUtils {
	public static String gerValidatecode(Integer length) {
		String s = "";
		while (s.length() < length)
			s += (int) (Math.random() * 10);
		return s;
	}
	
	/**
	 * 随机生成32位token
	 * @return
	 */
	public static String getTokenInfo(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	

}
