<#assign mapperLower = table.mapperName?uncap_first>
<#assign entityLower = entity?uncap_first>
package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Slf4j
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

    }
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    @Autowired
    private ${table.mapperName} ${mapperLower};


    public ${entity} detail(Long id) {
        return ${mapperLower}.selectById(id);
    }

    public boolean insert(${entity} ${entityLower}) {
        ${mapperLower}.insert(${entityLower});
        return true;
    }

    public boolean update(${entity} ${entityLower}) {
        ${mapperLower}.updateById(${entityLower});
        return true;
    }

    public boolean delete(Long id) {
        ${mapperLower}.deleteById(id);
        return true;
    }

    public List<${entity}> list() {
        return ${mapperLower}.selectList(null);
    }
}
</#if>
