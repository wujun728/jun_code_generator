package com.jeasy.jdk;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCurrentUserResponse", propOrder = {
    "returnUser"
})
public class GetCurrentUserResponse {

    @XmlElement(name = "return")
    protected User returnUser;

    /**
     * Gets the value of the return property.
     *
     * @return possible object is
     * {@link User }
     */
    public User getReturn() {
        return returnUser;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value allowed object is
     *              {@link User }
     */
    public void setReturn(User value) {
        this.returnUser = value;
    }

}
