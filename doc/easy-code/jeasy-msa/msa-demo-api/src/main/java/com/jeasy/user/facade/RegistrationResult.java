package com.jeasy.user.facade;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
// 此外，如果service方法中的返回值是Java的 primitive类型（如int，long，float，double等），
// 最好为它们添加一层wrapper对象，因为JAXB不能直接序列化primitive类型。这样不但能够解决XML序列化的问题，
// 而且使得返回的数据都符合XML和JSON的规范。

// 这种wrapper对象其实利用所谓Data Transfer Object（DTO）模式，采用DTO还能对传输数据做更多有用的定制。
@Data
@XmlRootElement
public class RegistrationResult implements Serializable {

    private Long id;
}
