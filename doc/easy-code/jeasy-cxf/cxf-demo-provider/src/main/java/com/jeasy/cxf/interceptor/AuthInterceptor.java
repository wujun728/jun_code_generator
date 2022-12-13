package com.jeasy.cxf.interceptor;

import com.jeasy.common.Func;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * <p>类描述：用户权限验证拦截器  </p>
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private static final String USER_NAME = "admin";

    private static final String PASSWORD = "admin";

    /**
     * 在调用之前拦截
     */
    public AuthInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    /**
     * 自定义拦截器需要实现handleMessage方法，该方法抛出Fault异常，可以自定义异常集成自Fault，
     * 也可以new Fault(new Throwable())
     */
    @Override
    public void handleMessage(SoapMessage soap) throws Fault {
        System.out.println("开始验证用户信息");
        List<Header> headers = soap.getHeaders();

        //检查headers是否存在
        if (Func.isEmpty(headers)) {
            throw new Fault(new IllegalArgumentException("找不到Header，无法验证用户信息"));
        }

        Header header = headers.get(0);

        Element el = (Element) header.getObject();
        NodeList users = el.getElementsByTagName("username");
        NodeList passwords = el.getElementsByTagName("password");

        //检查是否有用户名和密码元素
        if (users.getLength() < 1) {
            throw new Fault(new IllegalArgumentException("找不到用户信息"));
        }
        String username = users.item(0).getTextContent().trim();

        if (passwords.getLength() < 1) {
            throw new Fault(new IllegalArgumentException("找不到密码信息"));
        }
        String password = passwords.item(0).getTextContent();

        //检查用户名和密码是否正确
        if (!USER_NAME.equals(username) || !PASSWORD.equals(password)) {
            throw new Fault(new IllegalArgumentException("用户名或密码不正确"));
        } else {
            System.out.println("用户名密码正确允许访问");
        }
    }
}
