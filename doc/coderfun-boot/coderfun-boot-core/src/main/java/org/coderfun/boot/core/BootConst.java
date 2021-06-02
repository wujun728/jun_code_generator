package org.coderfun.boot.core;

public class BootConst {
	
	public static final String SESSION_USER_MENUS = "SessionUser-menus";
	
	/*
	 * 超级管理员Id
	 */
	public static final Long SUPER_ADMIN_ID = 1L;
	
	public enum KickoutMode{
		/**
		 * 后者登录的用户踢出前者登录的用户
		 */
		BEFORE,
		/**
		 * 全部踢出
		 */
		ALL
	}
	
}
