package com.jeasy.common.log;

import com.jeasy.common.Func;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * LogKit.
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class LogKit {

    /**
     * Do nothing.
     */
    public static void logNothing(Throwable t) {

    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void println(String message, Object... values) {
        System.out.println(Func.format(message, values));
    }

    public static void println(String message, Map<?, ?> map) {
        System.out.println(Func.format(message, map));
    }

    public static void debug(String message) {
        log.debug(message);
    }

    public static void debug(String message, Throwable t) {
        log.debug(message, t);
    }

    public static void info(String message) {
        log.info(message);
    }

    public static void info(String message, Throwable t) {
        log.info(message, t);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void warn(String message, Throwable t) {
        log.warn(message, t);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }

    public static boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

}
