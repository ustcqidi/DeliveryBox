/*
 * Copyright (c) 2010-2011 IFLYTEK. All Rights Reserved.
 * [Id:ErcConfigConstant.java  2012-07-05 下午4:03 poplar.yfyang ]
 */
package com.ustc.box.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 系统配置文件读取工具类
 * 
 * @author Administrator
 */
public class PropertiesUtils {

	/** 资源文件中配置的信息 */
	private static Map<String, String> properties;

	/**
	 * 设置资源文件中的配置信息
	 * 
	 * @param properties
	 *            配置信息
	 */
	public static void setProperties(Map<String, String> properties) {

		PropertiesUtils.properties = properties;
	}

	/**
	 * 根据KEY取得资源配置信息
	 * 
	 * @param key
	 *            key，资源文件中配置的key
	 * @return 资源配置信息
	 */
	public static String getProperty(String key) {

		String result = properties.get(key);
		if (StringUtils.isEmpty(result)) {
			result = "";
		}
		return result.trim();
	}
	
	/**
	 * 获取服务器文件存放根路径
	 * @return
	 */
	public static String getStorage(){
		return getProperty("storage");
	}
	
	public static String getDownUrl(){
		return getProperty("downurl");
	}
	public static String getConfPath(){
		return getProperty("confPath");
	}
	
	public static String getSuperUser(){
		String ret=getProperty("superuser");
		if (StringUtils.isEmpty(ret)) {
			return "superuser";
		}
		return ret;
	}
}
