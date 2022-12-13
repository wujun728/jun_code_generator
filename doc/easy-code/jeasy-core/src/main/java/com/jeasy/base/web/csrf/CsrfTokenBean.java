package com.jeasy.base.web.csrf;

import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class CsrfTokenBean implements Serializable {

    private static final long serialVersionUID = -6865031901744243607L;

    private final String token;
    private final String parameterName;
    private final String headerName;

    /**
     * Creates a new instance
     *
     * @param headerName    the HTTP header name to use
     * @param parameterName the HTTP parameter name to use
     * @param token         the value of the token (i.e. expected value of the HTTP parameter of
     *                      parametername).
     */
    public CsrfTokenBean(final String headerName, final String parameterName, final String token) {
        Assert.hasLength(headerName, "headerName cannot be null or empty");
        Assert.hasLength(parameterName, "parameterName cannot be null or empty");
        Assert.hasLength(token, "token cannot be null or empty");
        this.headerName = headerName;
        this.parameterName = parameterName;
        this.token = token;
    }

    public final String getHeaderName() {
        return this.headerName;
    }

    public final String getParameterName() {
        return this.parameterName;
    }

    public final String getToken() {
        return this.token;
    }
}
