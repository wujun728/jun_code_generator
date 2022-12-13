package com.jeasy.common.collection;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @param <U> 第一个元素的数据类型
 * @param <V> 第二个元素的数据类型
 * @author taomk
 *         大小为2的Tuple
 */
public class Pair<U, V> implements Serializable, Cloneable {

    private static final long serialVersionUID = 5429741192255267602L;

    private U first;

    private V second;

    public Pair() {
    }

    public Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public void setFirst(U first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append('<');
        buf.append(first);
        buf.append(',');
        buf.append(second);
        buf.append('>');
        return buf.toString();
    }

    @Override
    public int hashCode() {
        int hash1 = 0;
        if (first != null) {
            hash1 = first.hashCode();
        }
        int hash2 = 0;
        if (second != null) {
            hash2 = second.hashCode();
        }
        return hash1 * 31 + hash2;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair<?, ?> p = (Pair<?, ?>) o;
            return Arrays.equals(new Object[]{first, second}, new Object[]{p.first, p.second});
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pair<U, V> clone() throws CloneNotSupportedException {
        return (Pair<U, V>) super.clone();
    }

}
