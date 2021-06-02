<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.service;

import ${g.codePackage}.model.${g.entityName}Model;
import cn.afterturn.boot.bussiness.base.service.IBaseService;


/**
 * ${g.name}服务
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
public interface I${g.entityName}Service extends IBaseService<${g.entityName}Model> {

}
