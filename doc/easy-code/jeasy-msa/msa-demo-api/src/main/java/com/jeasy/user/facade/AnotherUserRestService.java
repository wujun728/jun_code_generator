package com.jeasy.user.facade;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.jeasy.user.User;

import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
// 在Dubbo中开发REST服务主要都是通过JAX-RS的annotation来完成配置的，
// 在上面的示例中，我们都是将annotation放在服务的实现类中。但其实，我
// 们完全也可以将annotation放到服务的接口上，这两种方式是完全等价的.
//
// 在一般应用中，我们建议将annotation放到服务实现类，这样annotation和
// java实现代码位置更接近，更便于开发和维护。另外更重要的是，我们一般倾向
// 于避免对接口的污染，保持接口的纯净性和广泛适用性。

// 但是，如后文所述，如果我们要用dubbo直接开发的消费端来访问此服务，则annotation必须放到接口上。
// 如果接口和实现类都同时添加了annotation，则实现类的annotation配置会生效，接口上的annotation被直接忽略。
@Path("u")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public interface AnotherUserRestService {

    /**
     * 在一个REST服务同时对多种数据格式支持的情况下，根据JAX-RS标准，
     * 一般是通过HTTP中的MIME header（content-type和accept）来指定当前想用的是哪种格式的数据。
     * // @Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
     * <p>
     * 但是在dubbo中，我们还自动支持目前业界普遍使用的方式，即用一个URL后缀（.json和.xml）来指定
     * 想用的数据格式。例如，在添加上述annotation后，直接访问http://localhost:8888/users/1001.json
     * 则表示用json格式，直接访问http://localhost:8888/users/1002.xml则表示用xml格式，
     * 比用HTTP Header更简单直观。Twitter、微博等的REST API都是采用这种方式。
     * <p>
     * 如果你既不加HTTP header，也不加后缀，则dubbo的REST会优先启用在以上annotation定义中排位最靠前的那种数据格式。
     * 注意：这里要支持XML格式数据，在annotation中既可以用MediaType.TEXT_XML，也可以用MediaType.APPLICATION_XML，
     * 但是TEXT_XML是更常用的，并且如果要利用上述的URL后缀方式来指定数据格式，只能配置为TEXT_XML才能生效。
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id : \\d+}")
    User getUser(@PathParam("id") @Min(1L) Long id);

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @POST
    @Path("register")
    RegistrationResult registerUser(User user);
}
