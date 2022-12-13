package com.jeasy.jdk;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@WebService(name = "HelloService", targetNamespace = "http://service.jdk.jeasy.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HelloService {


    /**
     * sayHi
     *
     * @param text
     * @return
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayHi", targetNamespace = "http://service.jdk.jeasy.com/", className = "com.jeasy.jdk.SayHi")
    @ResponseWrapper(localName = "sayHiResponse", targetNamespace = "http://service.jdk.jeasy.com/", className = "com.jeasy.jdk.SayHiResponse")
    String sayHi(
        @WebParam(name = "text", targetNamespace = "")
            String text);

    /**
     * getCurrentUser
     *
     * @return
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getCurrentUser", targetNamespace = "http://service.jdk.jeasy.com/", className = "com.jeasy.jdk.GetCurrentUser")
    @ResponseWrapper(localName = "getCurrentUserResponse", targetNamespace = "http://service.jdk.jeasy.com/", className = "com.jeasy.jdk.GetCurrentUserResponse")
    User getCurrentUser();

    /**
     * sayToUser
     *
     * @param user
     * @return
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sayToUser", targetNamespace = "http://service.jdk.jeasy.com/", className = "com.jeasy.jdk.SayToUser")
    @ResponseWrapper(localName = "sayToUserResponse", targetNamespace = "http://service.jdk.jeasy.com/", className = "com.jeasy.jdk.SayToUserResponse")
    String sayToUser(
        @WebParam(name = "user", targetNamespace = "")
            User user);

}
