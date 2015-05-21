package com.ustc.box.core.utils;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
	 * 根据KEY取得资源配置信息
	 * 
	 * @param key
	 *            key，资源文件中配置的key
	 * @return 资源配置信息
	 */
	public static String getDefaultProperty(String key,String defaultValue) {

		String result = properties.get(key);
		if (StringUtils.isEmpty(result)) {
			return defaultValue;
		}
		return result.trim();
	}
	
	
}
