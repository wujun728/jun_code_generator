package org.coderfun.boot.core;
/**
 * 系统内置字典
 * @author klguang
 *
 */
public class BootDict {
	public static final String YES = "yes";
	public static final String NO = "no";
	
	public static class User{	
		public static class State{
			public static final String NORMAL = "normal";
			public static final String FORBIDDEN = "forbidden";
		}
	}
	public static class Permission{
		public static class Type{
			public static final String MENU = "menu";
			public static final String OPERATION = "operation";
		} 
		
		public static class State{
			public static final String OPEN = "open";
			public static final String CLOSE = "close"; 
		}
	}
	
}
