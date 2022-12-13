package com.jeasy.common.cache.ehcache;

/**
 * Ehcache对象序列化接口
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface IEhcacheSerializer {

    /**
     * 序列化
     *
     * @param value
     * @return
     * @throws Exception
     */
    String serialize(Object value) throws Exception;

    /**
     * 反序列化对象
     *
     * @param value
     * @return
     */
    Object deserialize(String value);
}
