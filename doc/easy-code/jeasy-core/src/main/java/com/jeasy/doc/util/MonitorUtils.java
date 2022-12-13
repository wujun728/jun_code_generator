package com.jeasy.doc.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author taomk
 * @version 1.0
 * @since 15-9-24 下午3:11
 */
public class MonitorUtils {

    public static volatile ConcurrentHashMap<String, AtomicLong> controllerTimeMap = new ConcurrentHashMap<>();

    public static  volatile ConcurrentHashMap<String, AtomicLong> controllerCountMap = new ConcurrentHashMap<>();

    public static volatile ConcurrentHashMap<String, AtomicLong> serviceTimeMap = new ConcurrentHashMap<>();

    public static  volatile ConcurrentHashMap<String, AtomicLong> serviceCountMap = new ConcurrentHashMap<>();

    public static volatile ConcurrentHashMap<String, AtomicLong> daoTimeMap = new ConcurrentHashMap<>();

    public static  volatile ConcurrentHashMap<String, AtomicLong> daoCountMap = new ConcurrentHashMap<>();

    public static  volatile ConcurrentHashMap<String, AtomicLong> exceptionCountMap = new ConcurrentHashMap<>();

    public static void incTimeForController(String className, String methodName, long time) {
        String key = className + "#" + methodName;
        AtomicLong oldValue;
        if (!controllerTimeMap.containsKey(key)) {
            // 在key不存在的时候加入一个值,如果key存在就不放入
            oldValue = controllerTimeMap.putIfAbsent(key, new AtomicLong(time));
        } else {
            oldValue = controllerTimeMap.get(key);
        }
        if (oldValue != null) {
            oldValue.addAndGet(time);
        }

        incCountForController(key);
    }

    private static void incCountForController(String key) {
        AtomicLong oldValue;
        if (!controllerCountMap.containsKey(key)) {
            // 在key不存在的时候加入一个值,如果key存在就不放入
            oldValue = controllerCountMap.putIfAbsent(key, new AtomicLong(1));
        } else {
            oldValue = controllerCountMap.get(key);
        }

        if (oldValue != null) {
            oldValue.addAndGet(1);
        }
    }

    public static void incTimeForService(String className, String methodName, long time) {
        String key = className + "#" + methodName;
        AtomicLong oldValue;
        if (!serviceTimeMap.containsKey(key)) {
            // 在key不存在的时候加入一个值,如果key存在就不放入
            oldValue = serviceTimeMap.putIfAbsent(key, new AtomicLong(time));
        } else {
            oldValue = serviceTimeMap.get(key);
        }
        if (oldValue != null) {
            oldValue.addAndGet(time);
        }

        incCountForService(key);
    }

    private static void incCountForService(String key) {
        AtomicLong oldValue;
        if (!serviceCountMap.containsKey(key)) {
            // 在key不存在的时候加入一个值,如果key存在就不放入
            oldValue = serviceCountMap.putIfAbsent(key, new AtomicLong(1));
        } else {
            oldValue = serviceCountMap.get(key);
        }

        if (oldValue != null) {
            oldValue.addAndGet(1);
        }
    }

    public static void incTimeForDao(String className, String methodName, long time) {
        String key = className + "#" + methodName;
        AtomicLong oldValue;
        if (!daoTimeMap.containsKey(key)) {
            // 在key不存在的时候加入一个值,如果key存在就不放入
            oldValue = daoTimeMap.putIfAbsent(key, new AtomicLong(time));
        } else {
            oldValue = daoTimeMap.get(key);
        }
        if (oldValue != null) {
            oldValue.addAndGet(time);
        }

        incCountForDao(key);
    }

    private static void incCountForDao(String key) {
        AtomicLong oldValue;
        if (!daoCountMap.containsKey(key)) {
            // 在key不存在的时候加入一个值,如果key存在就不放入
            oldValue = daoCountMap.putIfAbsent(key, new AtomicLong(1));
        } else {
            oldValue = daoCountMap.get(key);
        }

        if (oldValue != null) {
            oldValue.addAndGet(1);
        }
    }

    public static void incCountForException(String className, String methodName) {
        String key = className + "#" + methodName;
        AtomicLong oldValue;
        if (!exceptionCountMap.containsKey(key)) {
            // 在key不存在的时候加入一个值,如果key存在就不放入
            oldValue = exceptionCountMap.putIfAbsent(key, new AtomicLong(1));
        } else {
            oldValue = exceptionCountMap.get(key);
        }

        if (oldValue != null) {
            oldValue.addAndGet(1);
        }
    }
}
