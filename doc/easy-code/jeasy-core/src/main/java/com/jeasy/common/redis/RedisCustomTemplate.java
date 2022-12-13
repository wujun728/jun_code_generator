package com.jeasy.common.redis;

import com.jeasy.common.str.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisTemplate重写方法，主要是捕获异常
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class RedisCustomTemplate<K, V> extends RedisTemplate<K, V> {

    private Logger logger = LoggerFactory.getLogger(RedisCustomTemplate.class);

    @Override
    public <T> T execute(RedisCallback<T> action) {
        try {
            return execute(action, isExposeConnection());
        } catch (Exception ex) {
            logger.error(StrKit.S_EMPTY, ex);
            return null;
        }
    }
}
