
package com.ustc.box.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

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
