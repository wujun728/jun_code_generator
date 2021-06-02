package org.coderfun.member.core.exception;

import org.coderfun.common.exception.IErrorCode;

/**
 * 
 * //2 03 001 释义：  03 = 会员  业务模块标识，001为具体的错误代码
 * @author klguang
 *
 */
public enum MemberErrorCode implements IErrorCode{
	
	USER_EMAIL_EXITS		(400, 203001L,"email已经存在!"),
	LOGIN_FAILED			(400, 203002L, "用户名或密码错误！"),
	USER_NAME_EXITS			(400, 203003L,"用户名已经存在!"),
	OLD_PASSWORD_WRONG		(400, 203004L,"原密码错误！"),
	CAPTCHA_WORNG			(400, 203005L,"验证码错误！"),
	
	LOGIN_FORBIDDEN			(403, 203003L,"登录被禁用！");
	

	Long code;
	int httpStatus;
	String messageFormat;

	private MemberErrorCode(int httpStatus, Long code, String messageFormat) {
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
