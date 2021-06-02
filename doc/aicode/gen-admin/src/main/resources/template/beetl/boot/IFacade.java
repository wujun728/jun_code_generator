<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package cn.afterturn.boot.facade;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * ${g.name}接口
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@FeignClient(value = "${g.lowerEntityName}Facade")
public interface I${g.entityName}Facade {

}
