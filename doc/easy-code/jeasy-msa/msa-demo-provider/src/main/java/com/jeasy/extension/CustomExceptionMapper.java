package com.jeasy.extension;

import com.alibaba.dubbo.rpc.RpcContext;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public class CustomExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        System.out.println("Exception mapper successfully got an exception: " + e + ":" + e.getMessage());
        System.out.println("Client IP is " + RpcContext.getContext().getRemoteAddressString());
        return Response.status(Response.Status.NOT_FOUND).entity("Oops! the requested resource is not found!").type("text/plain").build();
    }
}
