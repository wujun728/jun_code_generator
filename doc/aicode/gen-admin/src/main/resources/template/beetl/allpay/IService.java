<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.service;

import ${g.codePackage}.model.${g.entityName}Model;

import java.util.List;
import java.util.Map;
import java.io.Serializable;


/**
 * ${g.name}Service
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
public interface I${g.entityName}Service {

        /**
         * <p>
         * 插入一条记录
         * </p>
         *
         * @param entity 实体对象
         * @return int
         */
        Integer insert(${g.entityName}Model entity);

        /**
         * <p>
         * 根据 ID 删除
         * </p>
         *
         * @param id 主键ID
         * @return int
         */
        Integer deleteById(Serializable id);

        /**
         * <p>
         * 根据 ID 修改
         * </p>
         *
         * @param entity 实体对象
         * @return int
         */
        Integer updateById(${g.entityName}Model entity);

        /**
         * <p>
         * 根据 ID 查询
         * </p>
         *
         * @param id 主键ID
         * @return ${g.entityName}Model
         */
        ${g.entityName}Model selectById(Serializable id);

        /**
         * <p>
         * 根据 entity 条件，查询一条记录
         * </p>
         *
         * @param entity 实体对象
         * @return ${g.entityName}Model
         */
        ${g.entityName}Model selectOne(${g.entityName}Model entity);


        /**
         * <p>
         * 根据 entity 条件，查询全部记录
         * </p>
         *
         * @param model 实体对象封装操作类（可以为 null）
         * @return List<${g.entityName}Model>
         */
        List<${g.entityName}Model> selectList(${g.entityName}Model model);
}