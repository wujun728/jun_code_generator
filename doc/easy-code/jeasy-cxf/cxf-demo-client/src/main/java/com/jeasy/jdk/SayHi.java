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
@XmlType(name = "sayHi", propOrder = {
    "text"
})
public class SayHi {

    protected String text;

    /**
     * Gets the value of the text property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setText(String value) {
        this.text = value;
    }

}
