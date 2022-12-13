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
@XmlType(name = "say", propOrder = {
    "arg0"
})
public class Say {

    protected String arg0;

    /**
     * Gets the value of the arg0 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setArg0(String value) {
        this.arg0 = value;
    }

}
