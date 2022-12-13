package com.jeasy;

import com.jeasy.user.User;
import com.jeasy.user.facade.RegistrationResult;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public class MsaDemoConsumer {

    public static final int NUM_200 = 200;

    /**
     * 场景1：非dubbo的消费端调用dubbo的REST服务
     * <p/>
     * 这种场景的客户端与dubbo本身无关，直接选用相应语言和框架中合适的方式即可。
     * <p/>
     * 如果是还是java的客户端（但没用dubbo），可以考虑直接使用标准的JAX-RS
     * Client API或者特定REST实现的Client API来调用REST服务。下面是用JAX-RS
     * Client API来访问上述的UserService的registerUser()
     */
    public void nonDubboToDubbo() {
        User user = new User();
        user.setName("Larry");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/services/users/register.json");
        Response response = target.request().post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE));

        try {
            if (response.getStatus() != NUM_200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            System.out.println("The generated id is " + response.readEntity(RegistrationResult.class).getId());
        } finally {
            response.close();
            client.close(); // 在真正开发中不要每次关闭client，比如HTTP长连接是由client持有的
        }

        // 上面代码片段中的User和RegistrationResult类都是消费端自己编写的，JAX-RS Client API会
        // 自动对它们做序列化/反序列化。

        // 当然，在java中也可以直接用自己熟悉的比如HttpClient，FastJson，XStream等等各种不同技术
        // 来实现REST客户端，在此不再详述。
    }

    /**
     * 场景2：dubbo消费端调用dubbo的REST服务
     * <p/>
     * 这种场景下，和使用其他dubbo的远程调用方式一样，直接在服务提供端和服务消费端共享Java服务接口，并添加spring xml配置（当然也可以用spring/dubbo的annotation配置），即可透明的调用远程REST服务
     */
    public void dubboToDubbo() {
        // <dubbo:reference id="userService" interface="xxx.UserService"/>
        // 如前所述，这种场景下必须把JAX-RS的annotation添加到服务接口上，这样在dubbo在消费端才能共享相应的REST配置信息，并据之做远程调用:
        //
        // @Path("users")
        // public interface UserService {
        //
        //  @GET
        //  @Path("{id : \\d+}")
        //  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        //  User getUser(@PathParam("id") Long id);
        // }
        // 如果服务接口的annotation中配置了多种数据格式，这里由于两端都是dubbo系统，REST的大量细节被屏蔽了，
        // 所以不存在用前述URL后缀之类选择数据格式的可能。目前在这种情况下，排名最靠前的数据格式将直接被使用。
        // 因此，我们建议你在定义annotation的时候最好把最合适的数据格式放到前面，
        // 比如以上我们是把json放在xml前面，因为json的传输性能优于xml。
    }

    /**
     * 场景3：dubbo的消费端调用非dubbo的REST服务
     * <p/>
     * 这种场景下，可以直接用场景1中描述的Java的方式来调用REST服务。但其实也可以采用场景2中描述的方式，即更透明
     * 的调用REST服务，即使这个服务并不是dubbo提供的。
     * <p/>
     * 如果用场景2的方式，由于这里REST服务并非dubbo提供，一般也就没有前述的共享的Java服务接口，所以在此我们需要
     * 根据外部REST服务的情况，自己来编写Java接口以及相应参数类，并添加JAX-RS、JAXB、Jackson等的annotation，
     * dubbo的REST底层实现会据此去自动生成请求消息，自动解析响应消息等等，从而透明的做远程调用。或者这种方式也可
     * 以理解为，我们尝试用JAX-RS的方式去仿造实现一遍外部的REST服务提供端，然后把写成服务接口放到客户端来直接使用，
     * dubbo的REST底层实现就能像调用dubbo的REST服务一样调用其他REST服务。
     */
    public void dubboToNonDubbo() {
        // 例如，我们要调用如下的外部服务
        // http://api.foo.com/services/users/1001
        // http://api.foo.com/services/users/1002

        // 获取不同ID的用户资料，返回格式是JSON
        //        {
        //            "id": 1001,
        //            "name": "Larry"
        //        }

        // 我们可根据这些信息，编写服务接口和参数类即可：
        // @Path("users")
        // public interface UserService {
        //
        //    @GET
        //    @Path("{id : \\d+}")
        //    @Produces({MediaType.APPLICATION_JSON})
        //    User getUser(@PathParam("id") Long id);
        // }
        //
        // public class User implements Serializable {
        //
        //    private Long id;
        //
        //    private String name;
        // }

        // 对于spring中的配置，因为这里的REST服务不是dubbo提供的，所以无法使用dubbo的注册中心，
        // 直接配置外部REST服务的url地址即可（如多个地址用逗号分隔）：
        // <dubbo:reference id="userService" interface="xxx.UserService" url="rest://api.foo.com/services/"/>
        // 注意：这里协议必须用rest://而不是http://之类。如果外部的REST服务有context path，则在
        // url中也必须添加上（除非你在每个服务接口的@Path annotation中都带上context path），例如
        // 上面的/services/。同时这里的services后面必须带上/，这样才能使dubbo正常工作。

        // 另外，这里依然可以配置客户端可启动的最大连接数和超时时间：
        // <dubbo:reference id="userService" interface="xxx.UserService" url="rest://api.foo.com/services/" timeout="2000" connections="10"/>
    }
}
