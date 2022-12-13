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
@XmlType(name = "sayHiResponse", propOrder = {
    "returnStr"
})
public class SayHiResponse {

    @XmlElement(name = "return")
    protected String returnStr;

    /**
     * Gets the value of the return property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getReturn() {
        return returnStr;
    }

    /**
     * Sets the value of the return property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReturn(String value) {
        this.returnStr = value;
    }

}
