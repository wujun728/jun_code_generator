<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.service.impl;

import ${g.codePackage}.dao.${g.entityName}Dao;
import ${g.codePackage}.model.${g.entityName}Model;
import ${g.codePackage}.service.I${g.entityName}Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.io.Serializable;
/**
 * ${g.name}Service
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@Service
public class ${g.entityName}ServiceImpl implements I${g.entityName}Service {

        private static final Logger LOGGER = LoggerFactory.getLogger(${g.entityName}ServiceImpl.class);

        @Autowired
        private ${g.entityName}Dao ${g.lowerEntityName}Dao;
        @Override
        public Integer insert(${g.entityName}Model entity) {
                return ${g.lowerEntityName}Dao.insert(entity);
                }

        @Override
        public Integer deleteById(Serializable id) {
                return ${g.lowerEntityName}Dao.deleteById(id);
                }

        @Override
        public Integer updateById(${g.entityName}Model entity) {
                return ${g.lowerEntityName}Dao.updateById(entity);
                }

        @Override
        public ${g.entityName}Model selectById(Serializable id) {
                return ${g.lowerEntityName}Dao.selectById(id);
                }

        @Override
        public ${g.entityName}Model selectOne(${g.entityName}Model entity) {
                return ${g.lowerEntityName}Dao.selectOne(entity);
                }

        @Override
        public Integer selectCount(${g.entityName}Model model) {
                return ${g.lowerEntityName}Dao.selectCount(model);
                }

        @Override
        public List<${g.entityName}Model> selectList(${g.entityName}Model model) {
                return ${g.lowerEntityName}Dao.selectList(model);
                }

        @Override
        public List<${g.entityName}Model> selectPage(Pagination pagination, ${g.entityName}Model model, Wrapper<${g.entityName}Model> wrapper) {
                return ${g.lowerEntityName}Dao.selectPage(pagination,model,wrapper);
                }

}