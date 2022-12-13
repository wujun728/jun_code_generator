package com.jeasy.user.facade;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.jeasy.user.User;
import com.jeasy.user.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
// JAX-RS是标准的Java REST API，得到了业界的广泛支持和应用，其著名的开源实现就有很多，
// 包括Oracle的Jersey，RedHat的RestEasy，Apache的CXF和Wink，以及restlet等等。
// 另外，所有支持JavaEE 6.0以上规范的商用JavaEE应用服务器都对JAX-RS提供了支持。
// 因此，JAX-RS是一种已经非常成熟的解决方案，并且采用它没有任何所谓vendor lock-in的问题。
// http://www.ibm.com/developerworks/cn/java/j-lo-jaxrs/
// 更多的资料请自行google或者百度一下。就学习JAX-RS来说，一般主要掌握其各种annotation的用法即可。
// 注意：dubbo是基于JAX-RS 2.0版本的，有时候需要注意一下资料或REST实现所涉及的版本。

// @Path("users")：指定访问UserService的URL相对路径是/users，即http://localhost:8080/users
@Path("users")
// @Consumes({MediaType.APPLICATION_JSON})：指定UserRestServiceImpl接收JSON格式的数据。REST框架会自动将JSON数据反序列化为User对象
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
// @Produces({MediaType.APPLICATION_JSON_UTF_8})：指定UserRestServiceImpl输出JSON格式的数据。框架会自动将User对象序列化为JSON数据。
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Slf4j
public class UserRestServiceImpl implements UserRestService {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //    @GET：指定用HTTP GET方法访问
    @Override
    @GET
//    @Path("{id : \d+}")：根据上面的功能需求，访问getUser()的URL应当是“http://localhost:8080/users/ + 任意数字"，并且这个数字要被做为参数传入getUser()方法。 这里的annotation配置中，@Path中间的{id: xxx}指定URL相对路径中包含了名为id参数，而它的值也将被自动传递给下面用@PathParam("id")修饰的方法参数id。{id:后面紧跟的\d+是一个正则表达式，指定了id参数必须是数字。
    @Path("{id : \\d+}")
    public User getUser(@PathParam("id") Long id, @Context HttpServletRequest request) {
        // 第一种方式，用JAX-RS标准的@Context annotation, 用@Context修饰getUser()的一个方法参数后，
        // 就可以将当前的HttpServletRequest注入进来，然后直接调用servlet api获取IP。

        // 注意：这种方式只能在设置server="tjws"或者server="tomcat"或者server="jetty"或者server="servlet"的时候才能工作，
        // 因为只有这几种REST server的实现才提供了servlet容器。另外，标准的JAX-RS还支持用@Context修饰service类的一个实例字段
        // 来获取HttpServletRequest，但在dubbo中我们没有对此作出支持。
        System.out.println("Client address from @Context injection: " + (request != null ? request.getRemoteAddr() : ""));

        // 第二种方式，用dubbo中常用的RpcContext
        // 注意：这种方式只能在设置server="jetty"或者server="tomcat"或者server="servlet"或者server="tjws"的时候才能工作。另外，目前dubbo的RpcContext是一种比较有侵入性的用法，未来我们很可能会做出重构。
        System.out.println("Client address from RpcContext: " + RpcContext.getContext().getRemoteAddressString());
        // 如果你想保持你的项目对JAX-RS的兼容性，未来脱离dubbo也可以运行，请选择第一种方式。如果你想要更优雅的服务接口定义，请选用第二种方式。

        // 此外，在最新的dubbo rest中，还支持通过RpcContext来获取HttpServletRequest和HttpServletResponse，以提供更大的灵活性来方便用户实现某些复杂功能，比如在dubbo标准的filter中访问HTTP Header。
        // 为了简化编程，在此你也可以用泛型的方式来直接获取特定类型的request/response,如果request/response不符合指定的类型，这里也会返回null。如下:
        if (RpcContext.getContext().getRequest(HttpServletRequest.class) != null) {
            System.out.println("Client IP address from RpcContext: " + RpcContext.getContext().getRequest(HttpServletRequest.class).getRemoteAddr());
        }
        if (RpcContext.getContext().getResponse(HttpServletResponse.class) != null) {
            System.out.println("Response object from RpcContext: " + RpcContext.getContext().getResponse(HttpServletResponse.class));
        }
        // 注意：为了保持协议的中立性，RpcContext.getRequest()和RpcContext.getResponse()返回的仅仅是一个Object类，
        // 而且可能为null。所以，你必须自己做null和类型的检查。

        // 注意：只有在设置server="jetty"或者server="tomcat"或者server="servlet"的时候，你才能通过以上方法正确的得
        // 到HttpServletRequest和HttpServletResponse，因为只有这几种server实现了servlet容器。

        return userService.getUser(id);
    }

    //    @POST：指定访问registerUser()用HTTP POST方法
    @Override
    @POST
//    @Path("register")：指定访问registerUser()方法的URL相对路径是/register，再结合上一个@Path为UserService指定的路径，则调用UserService.register()的完整路径为http://localhost:8080/users/register
    @Path("register")
    public RegistrationResult registerUser(User user) {
        Long id = userService.registerUser(user);
        RegistrationResult registrationResult = new RegistrationResult();
        registrationResult.setId(id);
        return registrationResult;
    }
}
