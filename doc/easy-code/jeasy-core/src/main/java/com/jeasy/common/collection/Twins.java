package com.jeasy.common.collection;

/**
 * @author taomk
 * 大小为2的Tuple的特例，两个数据类型相等
 * @param <U>
 */
public class Twins<U> extends Pair<U, U> {

    private static final long serialVersionUID = 2510232699472854432L;

    public Twins(U first, U second) {
        super(first, second);
    }
}
