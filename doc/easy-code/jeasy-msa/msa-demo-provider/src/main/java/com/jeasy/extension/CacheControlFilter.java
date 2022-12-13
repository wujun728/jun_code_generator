package com.jeasy.extension;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public class CacheControlFilter implements ContainerResponseFilter {

    public static final String STR_GET = "GET";

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) {
        if (STR_GET.equals(req.getMethod())) {
            res.getHeaders().add("Cache-Control", "someValue");
        }
    }
}
