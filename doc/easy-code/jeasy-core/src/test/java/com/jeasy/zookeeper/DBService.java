package com.jeasy.zookeeper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String user;
    @Value("${db.password}")
    private String passwd;

    /**
     * get url
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * set url
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * get user
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     * set user
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * get passwd
     *
     * @return
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * set passwd
     *
     * @param passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

}
