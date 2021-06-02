package org.coderfun.boot.core.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;


/**
 * 
 * 增加 hashPassword 方法，方便加密
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年11月10日
 */
public class SimplePasswordMatcher extends HashedCredentialsMatcher{
	
	public Hash hashPassword(String plainPassword,String salt) throws IllegalArgumentException{
		return new SimpleHash(
				getHashAlgorithmName(),
				plainPassword,
				salt,
				getHashIterations());
		
	}
}
