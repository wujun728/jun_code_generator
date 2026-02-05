<#assign entityLower = entity?uncap_first>
package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

import java.util.List;

/**
* <p>
    * ${table.comment!} 服务类
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if kotlin>
    interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    ${entity} detail(Long id);

    boolean insert(${entity} ${entityLower});

    boolean update(${entity} ${entityLower});

    boolean delete(Long id);

    List<${entity}> list();
}
</#if>
