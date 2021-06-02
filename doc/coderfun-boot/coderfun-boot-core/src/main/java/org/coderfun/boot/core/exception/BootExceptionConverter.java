package org.coderfun.boot.core.exception;

import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.coderfun.common.exception.AppException;
import org.coderfun.common.exception.ErrorCodeEnum;
import org.coderfun.common.exception.ExceptionConverter;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

public class BootExceptionConverter implements ExceptionConverter{

	@Override
	public AppException convertException(Exception ex) {
		// TODO Auto-generated method stub
		if(ex instanceof LockedAccountException){
			return new AppException(BootErrorCode.LOGIN_FORBIDDEN);
		} 
		
		if(ex instanceof AuthorizationException){
			return new AppException(ErrorCodeEnum.UNAUTHORIZED);
		}
		
		if(ex instanceof InvalidDataAccessResourceUsageException){
			return new AppException(BootErrorCode.DEMO_NOT_UPDATE);
		}
		
		return null;
	}
}
