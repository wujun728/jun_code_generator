package com.jeasy.common.http.exception;

/**
 * HTTP请求异常
 *
 * @author taomk
 * @version 1.0
 * @since 2017/08/21 18:29
 */
public class HttpProcessException extends Exception {

    private static final long serialVersionUID = -2749168865492921426L;

    public HttpProcessException(Exception e) {
        super(e);
    }

    /**
     * @param msg
     */
    public HttpProcessException(String msg) {
        super(msg);
    }

    /**
     * @param message
     * @param e
     */
    public HttpProcessException(String message, Exception e) {
        super(message, e);
    }

}
