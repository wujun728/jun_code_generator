package org.coderfun.boot.core;

import klg.common.utils.AppProperties;

public class BootSettings {
	public static AppProperties properties;
	
	public BootSettings(AppProperties appProperties){
		properties = appProperties;
	}
	
	public static String getAdminPath(){
		return properties.getProperty("admin.path");
	}
	
	public static boolean enableLoginKickout(){
		return properties.getProperty("login.enableKickout").equals(Boolean.TRUE.toString());
	}
	
	public static boolean enableLoginLog(){
		return properties.getProperty("login.enableLog").equals(Boolean.TRUE.toString());
	}
}
