package com.jeasy.extension;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.IOException;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public class ClientTraceFilter implements ClientRequestFilter, ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        System.out.println("Client request filter invoked");
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext, ClientResponseContext clientResponseContext) throws IOException {
        System.out.println("Client response filter invoked");
    }
}
