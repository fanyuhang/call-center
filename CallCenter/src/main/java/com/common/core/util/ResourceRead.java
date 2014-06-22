package com.common.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourceRead {
	private static  ResourceBundle bundle = ResourceBundle.getBundle("application");
	private static Map<String,String> keyValue=new HashMap<String,String>();
	public static String  getValueByKey(String key){
		if(!keyValue.containsKey(key)){
			keyValue.put(key, bundle.getString(key));
		}
		return keyValue.get(key);
	}
}
