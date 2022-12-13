package com.jeasy.common.support;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface Filter<T> {

    /**
     * 修改过滤后的结果
     *
     * @param t 被过滤的对象
     * @return 修改后的对象，如果被过滤返回<code>null</code>
     */
    public T modify(T t);
}
