package com.jeasy.common.security;

import com.jeasy.common.spring.SpringContextHolder;
import lombok.Data;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * shiro密码加密配置
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
public class PasswordHash implements InitializingBean {

    private String algorithmName;

    private int hashIterations;

    public static PasswordHash me() {
        return SpringContextHolder.getBean(PasswordHash.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(algorithmName, "algorithmName mast be md5、SHA-1、SHA-256、SHA-384、SHA-512");
    }

    public String toHex(Object source, Object salt) {
        return hashByShiro(algorithmName, source, salt, hashIterations);
    }

    /**
     * 使用shiro的hash方式
     *
     * @param algorithmName  算法
     * @param source         源对象
     * @param salt           加密盐
     * @param hashIterations hash次数
     * @return 加密后的字符
     */
    private String hashByShiro(String algorithmName, Object source, Object salt, int hashIterations) {
        return new SimpleHash(algorithmName, source, salt, hashIterations).toHex();
    }
}
