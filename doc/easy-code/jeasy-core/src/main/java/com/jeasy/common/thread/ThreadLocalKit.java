package com.jeasy.common.thread;

import com.jeasy.base.web.dto.CurrentUser;
import com.jeasy.base.web.dto.Device;
import org.springframework.core.NamedThreadLocal;

import java.lang.reflect.Method;

/**
 * ThreadLocal 工具类
 *
 * @author taomk
 * @version 1.0
 * @since 15-6-6 上午11:48
 */
public class ThreadLocalKit {

    private static final NamedThreadLocal<Device> DEVICE_THREAD_LOCAL = new NamedThreadLocal<>("DEVICE");

    private static final NamedThreadLocal<Long> TIME_THREAD_LOCAL = new NamedThreadLocal<>("TIME");

    private static final NamedThreadLocal<CurrentUser> USER_THREAD_LOCAL = new NamedThreadLocal<>("USER");

    private static final NamedThreadLocal<Method> METHOD_NAMED_THREAD_LOCAL = new NamedThreadLocal<>("METHOD");

    /**
     * 存放当前方法
     *
     * @param method
     */
    public static final void putCurrentMethod(Method method) {
        METHOD_NAMED_THREAD_LOCAL.set(method);
    }

    /**
     * 获取当前方法
     */
    public static final Method getCurrentMethod() {
        return METHOD_NAMED_THREAD_LOCAL.get();
    }

    /**
     * 删除当前方法
     */
    public static final void removeCurrentMethod() {
        METHOD_NAMED_THREAD_LOCAL.remove();
    }

    /**
     * 存放版本信息
     *
     * @param device
     */
    public static final void putDevice(Device device) {
        DEVICE_THREAD_LOCAL.set(device);
    }

    /**
     * 获取版本信息
     */
    public static final Device getDevice() {
        return DEVICE_THREAD_LOCAL.get();
    }

    /**
     * 删除版本信息
     */
    public static final void removeDevice() {
        DEVICE_THREAD_LOCAL.remove();
    }

    /**
     * 存放USER
     *
     * @param user
     */
    public static final void putCurrentUser(CurrentUser user) {
        USER_THREAD_LOCAL.set(user);
    }

    /**
     * 获取APP_USER
     */
    public static final CurrentUser getCurrentUser() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 删除APP_USER
     */
    public static final void removeUser() {
        USER_THREAD_LOCAL.remove();
    }

    /**
     * 存放时间
     *
     * @param time
     */
    public static final void putTime(long time) {
        TIME_THREAD_LOCAL.set(time);
    }

    /**
     * 获取时间
     */
    public static final long getTime() {
        return TIME_THREAD_LOCAL.get();
    }

    /**
     * 删除时间
     */
    public static final void removeTime() {
        TIME_THREAD_LOCAL.remove();
    }
}
