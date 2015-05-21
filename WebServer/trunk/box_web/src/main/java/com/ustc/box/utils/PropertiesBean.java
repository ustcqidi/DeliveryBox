/*
 * Copyright (c) 2010-2011 IFLYTEK. All Rights Reserved.
 * [Id:PropertiesBean.java  2012-07-05 下午3:58 poplar.yfyang ]
 */
package com.ustc.box.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 读取系统配置文件
 * 
 * @author Administrator
 */
public class PropertiesBean extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {

		super.processProperties(beanFactoryToProcess, props);
		Map<String, String> resolvePrperties = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			resolvePrperties.put(keyStr, props.getProperty(keyStr));
		}
		PropertiesUtils.setProperties(resolvePrperties);
	}

}
