package com.jeasy.jdk;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName GET_CURRENT_USER_RESPONSE_QNAME = new QName("http://service.jdk.jeasy.com/", "getCurrentUserResponse");
    private static final QName SAY_HI_RESPONSE_QNAME = new QName("http://service.jdk.jeasy.com/", "sayHiResponse");
    private static final QName GET_CURRENT_USER_QNAME = new QName("http://service.jdk.jeasy.com/", "getCurrentUser");
    private static final QName SAY_TO_USER_QNAME = new QName("http://service.jdk.jeasy.com/", "sayToUser");
    private static final QName SAY_TO_USER_RESPONSE_QNAME = new QName("http://service.jdk.jeasy.com/", "sayToUserResponse");
    private static final QName SAY_HI_QNAME = new QName("http://service.jdk.jeasy.com/", "sayHi");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.jeasy.jdk
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayToUserResponse }
     */
    public SayToUserResponse createSayToUserResponse() {
        return new SayToUserResponse();
    }

    /**
     * Create an instance of {@link SayToUser }
     */
    public SayToUser createSayToUser() {
        return new SayToUser();
    }

    /**
     * Create an instance of {@link SayHiResponse }
     */
    public SayHiResponse createSayHiResponse() {
        return new SayHiResponse();
    }

    /**
     * Create an instance of {@link GetCurrentUserResponse }
     */
    public GetCurrentUserResponse createGetCurrentUserResponse() {
        return new GetCurrentUserResponse();
    }

    /**
     * Create an instance of {@link GetCurrentUser }
     */
    public GetCurrentUser createGetCurrentUser() {
        return new GetCurrentUser();
    }

    /**
     * Create an instance of {@link SayHi }
     */
    public SayHi createSayHi() {
        return new SayHi();
    }

    /**
     * Create an instance of {@link User }
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentUserResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.jdk.jeasy.com/", name = "getCurrentUserResponse")
    public JAXBElement<GetCurrentUserResponse> createGetCurrentUserResponse(GetCurrentUserResponse value) {
        return new JAXBElement<GetCurrentUserResponse>(GET_CURRENT_USER_RESPONSE_QNAME, GetCurrentUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHiResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.jdk.jeasy.com/", name = "sayHiResponse")
    public JAXBElement<SayHiResponse> createSayHiResponse(SayHiResponse value) {
        return new JAXBElement<SayHiResponse>(SAY_HI_RESPONSE_QNAME, SayHiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentUser }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.jdk.jeasy.com/", name = "getCurrentUser")
    public JAXBElement<GetCurrentUser> createGetCurrentUser(GetCurrentUser value) {
        return new JAXBElement<GetCurrentUser>(GET_CURRENT_USER_QNAME, GetCurrentUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayToUser }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.jdk.jeasy.com/", name = "sayToUser")
    public JAXBElement<SayToUser> createSayToUser(SayToUser value) {
        return new JAXBElement<SayToUser>(SAY_TO_USER_QNAME, SayToUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayToUserResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.jdk.jeasy.com/", name = "sayToUserResponse")
    public JAXBElement<SayToUserResponse> createSayToUserResponse(SayToUserResponse value) {
        return new JAXBElement<SayToUserResponse>(SAY_TO_USER_RESPONSE_QNAME, SayToUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHi }{@code >}}
     */
    @XmlElementDecl(namespace = "http://service.jdk.jeasy.com/", name = "sayHi")
    public JAXBElement<SayHi> createSayHi(SayHi value) {
        return new JAXBElement<SayHi>(SAY_HI_QNAME, SayHi.class, null, value);
    }

}
