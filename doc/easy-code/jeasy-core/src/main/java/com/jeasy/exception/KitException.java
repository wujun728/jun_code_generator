package com.jeasy.exception;

import com.jeasy.common.str.StrKit;

/**
 * 工具类 异常
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class KitException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    public KitException(Throwable e) {
        super(e.getMessage(), e);
    }

    public KitException(String message) {
        super(message);
    }

    public KitException(String messageTemplate, Object... params) {
        super(StrKit.format(messageTemplate, params));
    }

    public KitException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public KitException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrKit.format(messageTemplate, params), throwable);
    }
}
