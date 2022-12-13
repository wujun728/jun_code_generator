package com.jeasy;

import com.jeasy.user.User;

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
public class RestClient {

    public static void main(String[] args) {
//        final String port = "8888";
        final String port = "8080";

//        for (int i = 0; i < 500; i++) {
//            final int index = i + 1;
//            new Thread(new Runnable() {
//                public void run() {
//                    System.out.println("Starting thread " + index + "...");
//                    for (int j = 0; j < 500; j++) {
        registerUser("http://localhost:" + port + "/rpc/users/register.json", MediaType.APPLICATION_JSON_TYPE);

        registerUser("http://localhost:" + port + "/rpc/users/register.xml", MediaType.TEXT_XML_TYPE);

        getUser("http://localhost:" + port + "/rpc/users/1.json");

        getUser("http://localhost:" + port + "/rpc/users/2.xml");

        registerUser("http://localhost:" + port + "/rpc/u/register.json", MediaType.APPLICATION_JSON_TYPE);

        registerUser("http://localhost:" + port + "/rpc/u/register.xml", MediaType.TEXT_XML_TYPE);

        getUser("http://localhost:" + port + "/rpc/u/1.json");

        getUser("http://localhost:" + port + "/rpc/u/2.xml");

        registerUser("http://localhost:" + port + "/rpc/customers/register.json", MediaType.APPLICATION_JSON_TYPE);

        registerUser("http://localhost:" + port + "/rpc/customers/register.xml", MediaType.TEXT_XML_TYPE);

        getUser("http://localhost:" + port + "/rpc/customers/1.json");

        getUser("http://localhost:" + port + "/rpc/customers/2.xml");
//                    }
//                }
//            }).start();
//        }
    }

    private static void registerUser(String url, MediaType mediaType) {
        System.out.println("Registering user via " + url);
        User user = new User();
        user.setId(1L);
        user.setName("larrypage");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Response response = target.request().post(Entity.entity(user, mediaType));

        try {
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            System.out.println("Successfully got result: " + response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    }

    private static void getUser(String url) {
        System.out.println("Getting user via " + url);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Response response = target.request().get();
        try {
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatus());
            }
            System.out.println("Successfully got result: " + response.readEntity(String.class));
        } finally {
            response.close();
            client.close();
        }
    }
}
