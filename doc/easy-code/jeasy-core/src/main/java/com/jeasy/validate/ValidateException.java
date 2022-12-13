package com.jeasy.validate;

/**
 * The validate exception
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
public class ValidateException extends RuntimeException {

    private static final long serialVersionUID = -5374157736257347033L;

    public ValidateException(Exception ex){
        super(ex.getMessage(), ex);
    }

    public ValidateException(String message){
        super(message);
    }

    public ValidateException(String message, Exception ex){
        super(message, ex);
    }
}
