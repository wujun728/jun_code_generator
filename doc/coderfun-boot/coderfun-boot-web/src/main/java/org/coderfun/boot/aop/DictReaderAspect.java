package org.coderfun.boot.aop;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.coderfun.common.dict.DictReader;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class DictReaderAspect {
	private static final Logger logger = LoggerFactory.getLogger(DictReaderAspect.class);
	@Autowired
	DictReader dictReader;

	/**
	 * 字典改动，重新load
	 * 
	 * @param joinPoint
	 */
	@After("execution(* org.coderfun.common.dict.dao.*.*(..))")
	public void rebuildWebFrontDictReader(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		if (methodName.startsWith("save") || methodName.startsWith("update") || methodName.startsWith("delete")) {
			logger.info("重新从数据库加载前端字典，joinPoint:{}", joinPoint.getSignature().getName());
			// 重新从数据库加载前端字典
			try {
				dictReader.buildWebFrontDictReader();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
