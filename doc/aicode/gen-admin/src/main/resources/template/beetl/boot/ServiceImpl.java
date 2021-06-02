<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.service.impl;

import ${g.codePackage}.repository.${g.entityName}Repository;
import ${g.codePackage}.model.${g.entityName}Model;
import ${g.codePackage}.service.I${g.entityName}Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.afterturn.boot.bussiness.base.service.BaseServiceCacheImpl;
import java.util.List;
import java.util.Map;

/**
 * ${g.name}服务实现
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@Service("${g.lowerEntityName}Service")
public class ${g.entityName}ServiceImpl extends BaseServiceCacheImpl<${g.entityName}Repository, ${g.entityName}Model> implements I${g.entityName}Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(${g.entityName}ServiceImpl.class);

    @Autowired
    private ${g.entityName}Repository ${g.lowerEntityName}Repository;

}
