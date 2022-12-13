package com.jeasy.user.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.jeasy.user.User;
import com.jeasy.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
@Service(protocol = {"rest", "dubbo"}, group = "annotationConfig", validation = "true")
@Path("customers")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Slf4j
public class AnnotationDrivenUserRestServiceImpl implements UserRestService {

    @Autowired
    private UserService userService;

    @Override
    @GET
    @Path("{id : \\d+}")
    public User getUser(@PathParam("id") Long id, @Context HttpServletRequest request) {
        // test context injection
        System.out.println("Client address from @Context injection: " + (request != null ? request.getRemoteAddr() : ""));
        System.out.println("Client address from RpcContext: " + RpcContext.getContext().getRemoteAddressString());
        return userService.getUser(id);
    }

    @Override
    @POST
    @Path("register")
    public RegistrationResult registerUser(User user) {
        Long id = userService.registerUser(user);
        RegistrationResult registrationResult = new RegistrationResult();
        registrationResult.setId(id);
        return registrationResult;
    }
}
