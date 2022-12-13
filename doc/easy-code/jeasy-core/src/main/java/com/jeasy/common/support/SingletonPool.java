package com.jeasy.common.support;

import com.jeasy.common.clazz.ClassKit;
import com.jeasy.common.str.StrKit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例对象集合
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class SingletonPool {

    private static Map<Class<?>, Object> pool = new ConcurrentHashMap<Class<?>, Object>();
    private static Map<String, Object> poolName = new ConcurrentHashMap<String, Object>();

    private static final SingletonPool ME = new SingletonPool();

    public static SingletonPool me() {
        return ME;
    }

    private SingletonPool() {

    }

    /**
     * 创建单例对象.放入缓冲池中
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        if (null == clazz) {
            return null;
        }
        T obj = (T) pool.get(clazz);
        if (null == obj) {
            synchronized (SingletonPool.class) {
                obj = (T) pool.get(clazz);
                if (null == obj) {
                    obj = ClassKit.newInstance(clazz);
                    pool.put(clazz, obj);
                }
            }
        }
        return obj;
    }

    /**
     * 创建单例对象.放入缓冲池中
     *
     * @param className
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(String className) {
        if (StrKit.isBlank(className)) {
            return null;
        }
        T obj = (T) poolName.get(className);
        if (null == obj) {
            synchronized (SingletonPool.class) {
                obj = (T) poolName.get(className);
                if (null == obj) {
                    obj = ClassKit.newInstance(className);
                    poolName.put(className, obj);
                }
            }
        }
        return obj;
    }


    /**
     * 移除指定缓冲池中对象
     *
     * @param clazz 类
     */
    public static void remove(Class<?> clazz) {
        pool.remove(clazz);
    }

    /**
     * 清除所有缓冲池中对象
     */
    public static void destroy() {
        pool.clear();
    }

}
