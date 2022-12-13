package com.jeasy.user;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
// 由于JAX-RS的实现一般都用标准的JAXB（Java API for XML Binding）来序列化和反序列化XML格式数据，
// 所以我们需要为每一个要用XML传输的对象添加一个类级别的JAXB annotation(@XmlRootElement) ，否则序列化将报错。
@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    @NotNull
    @Min(1L)
    private Long id;

    // REST的底层实现会在service的对象和JSON/XML数据格式之间自动做序列化/反序列化。
    // 但有些场景下，如果觉得这种自动转换不满足要求，可以对其做定制。
    // Dubbo中的REST实现是用JAXB做XML序列化，用Jackson做JSON序列化，
    // 所以在对象上添加JAXB或Jackson的annotation即可以定制映射。
    @JsonProperty("username")
    @XmlElement(name = "username")
    @NotNull
    @Size(min = 6, max = 50)
    private String name;
}
