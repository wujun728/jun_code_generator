package com.jeasy.jdk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sayToUser", propOrder = {
    "user"
})
public class SayToUser {

    protected User user;

    /**
     * Gets the value of the user property.
     *
     * @return possible object is
     * {@link User }
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     *
     * @param value allowed object is
     *              {@link User }
     */
    public void setUser(User value) {
        this.user = value;
    }

}
