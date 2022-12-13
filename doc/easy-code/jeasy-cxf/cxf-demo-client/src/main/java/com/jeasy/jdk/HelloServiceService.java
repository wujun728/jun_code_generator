package com.jeasy.jdk;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@WebServiceClient(name = "HelloService", targetNamespace = "http://impl.service.jdk.jeasy.com/", wsdlLocation = "http://localhost:9000/helloWorld?wsdl")
public class HelloServiceService
    extends Service {

    private static final URL HELLOSERVICE_WSDL_LOCATION;
    private static final WebServiceException HELLOSERVICE_EXCEPTION;
    private static final QName HELLOSERVICE_QNAME = new QName("http://impl.service.jdk.jeasy.com/", "HelloService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:9002/helloWorld?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        HELLOSERVICE_WSDL_LOCATION = url;
        HELLOSERVICE_EXCEPTION = e;
    }

    public HelloServiceService() {
        super(getWsdlLocation(), HELLOSERVICE_QNAME);
    }

    public HelloServiceService(WebServiceFeature... features) {
        super(getWsdlLocation(), HELLOSERVICE_QNAME, features);
    }

    public HelloServiceService(URL wsdlLocation) {
        super(wsdlLocation, HELLOSERVICE_QNAME);
    }

    public HelloServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, HELLOSERVICE_QNAME, features);
    }

    public HelloServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelloServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * @return returns HelloService
     */
    @WebEndpoint(name = "HelloServicePort")
    public HelloService getHelloServicePort() {
        return super.getPort(new QName("http://impl.service.jdk.jeasy.com/", "HelloServicePort"), HelloService.class);
    }

    /**
     * @param features A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns HelloService
     */
    @WebEndpoint(name = "HelloServicePort")
    public HelloService getHelloServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://impl.service.jdk.jeasy.com/", "HelloServicePort"), HelloService.class, features);
    }

    private static URL getWsdlLocation() {
        if (HELLOSERVICE_EXCEPTION != null) {
            throw HELLOSERVICE_EXCEPTION;
        }
        return HELLOSERVICE_WSDL_LOCATION;
    }

}
