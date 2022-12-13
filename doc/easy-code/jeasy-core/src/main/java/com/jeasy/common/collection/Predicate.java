package com.jeasy.common.collection;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface Predicate<T> {
    /**
     * 计算
     *
     * @param o
     * @return
     */
    boolean evaluate(T o);
}
