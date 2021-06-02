<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import ${g.codePackage}.dao.${g.entityName}Dao;
import ${g.codePackage}.model.${g.entityName}Model;
import ${g.codePackage}.service.I${g.entityName}Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * ${g.name}Service
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@Service
public class ${g.entityName}ServiceImpl extends ServiceImpl<${g.entityName}Dao, ${g.entityName}Model> implements I${g.entityName}Service{

    private static final Logger LOGGER = LoggerFactory.getLogger(${g.entityName}ServiceImpl.class);

    @Autowired
    private ${g.entityName}Dao ${g.lowerEntityName}Dao;


    @Override
    public ${g.entityName}Model selectOne(${g.entityName}Model entity){
        return ${g.lowerEntityName}Dao.selectOne(entity);
    }

    @Override
    public List<${g.entityName}Model>selectList(${g.entityName}Model model){
         return ${g.lowerEntityName}Dao.selectList(model, new EntityWrapper<${g.entityName}Model>());
    }

    @Override
    public List<${g.entityName}Model>selectList(${g.entityName}Model model,Wrapper<${g.entityName}Model>wrapper){
        return ${g.lowerEntityName}Dao.selectList(model,wrapper);
    }

    @Override
    public List<${g.entityName}Model>selectPage(Pagination pagination,${g.entityName}Model model,Wrapper<${g.entityName}Model>wrapper){
        return ${g.lowerEntityName}Dao.selectPage(pagination,model,wrapper);
    }

}
