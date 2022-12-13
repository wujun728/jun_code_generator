package com.jeasy.exception;

import com.jeasy.common.str.StrKit;

/**
 * Client层 异常
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class ClientException extends RuntimeException {

    private static final long serialVersionUID = 8247610319171014183L;

    public ClientException(Throwable e) {
        super(e.getMessage(), e);
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String messageTemplate, Object... params) {
        super(StrKit.format(messageTemplate, params));
    }

    public ClientException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ClientException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrKit.format(messageTemplate, params), throwable);
    }
}
