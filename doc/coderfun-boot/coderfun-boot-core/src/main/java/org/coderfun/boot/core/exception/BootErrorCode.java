package org.coderfun.boot.core.exception;

import org.coderfun.common.exception.IErrorCode;

/**
 * 错误码，参考新浪微博 open api
 * 
 * <pre>
http://open.weibo.com/wiki/Error_code
https://blog.csdn.net/huangwenyi1010/article/details/51581906
 * </pre>
 * 
 * <pre>
 错误码格式说明（示例：202001），1为系统级错误，2为业务逻辑错误
--------------------------------------------------------------------
服务级错误（1为系统级错误）	服务模块代码(即业务模块标识)		具体错误代码
        2                            02	                    001
--------------------------------------------------------------------
 * </pre>
 * 
 * //2 00 001 释义：  00 = System 业务模块标识，001为具体的错误代码
 * @author klguang
 *
 */
public enum BootErrorCode implements IErrorCode{
	DEMO_NOT_UPDATE	(403, 202000L,"演示模式，无法更新数据!"),
	
	USER_LOGIN_NAME_EXITS	(400, 202001L,"管理员账号已经存在!"),
	LOGIN_FAILED			(400, 202002L, "用户名或密码错误！"),
	OLD_PASSWORD_WRONG		(400, 202004L,"原密码错误！"),
	CAPTCHA_WORNG			(400, 202005L,"验证码错误！"),
	
	LOGIN_FORBIDDEN			(403, 202003L,"登录被禁用！");
	

	Long code;
	int httpStatus;
	String messageFormat;

	private BootErrorCode(int httpStatus, Long code, String messageFormat) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.messageFormat = messageFormat;
	}

	@Override
	public long getCode() {
		// TODO Auto-generated method stub
		return this.code;
	}

	@Override
	public String getMessageFormat() {
		// TODO Auto-generated method stub
		return this.messageFormat;
	}
	
	@Override
	public int getHttpStatus() {
		return this.httpStatus;
	}
}
