package com.ustc.box.utils;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


public class EhcacheHelper {
	static Cache configCache = null ;
	public static Cache permissionCache = null;
	public static Cache tokenCache = null;
	static{
	    CacheManager manager = new CacheManager(EhcacheHelper.class.getClassLoader().getResourceAsStream("ehcache.xml"));
	    //获取指定Cache对象
	    configCache = manager.getCache("configCache");
	    permissionCache = manager.getCache("validateCodeCache");
	}
	
	public static void put(String key, String value){
		Element element = new Element(key,value) ;
		configCache.put(element) ;
	}
	
	public static Object get(String key){
		Element element2 = configCache.get(key);
		if(element2!=null)
			return element2.getValue();
		else
			return null ;
	}
	
	
}
