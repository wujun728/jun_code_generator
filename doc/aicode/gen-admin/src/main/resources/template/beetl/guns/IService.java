<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import ${g.codePackage}.model.${g.entityName}Model;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;


/**
 * ${g.name}Service
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
public interface I${g.entityName}Service extends IService<${g.entityName}Model> {

    /**
     * <p>
     * 根据 model 条件，查询一条记录
     * </p>
     *
     * @param model 实体对象 非空
     * @return ${g.entityName}Model
     */
    ${g.entityName}Model selectOne(${g.entityName}Model model);
    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<${g.entityName}Model>
     */
    List<${g.entityName}Model> selectList(${g.entityName}Model model);

        /**
         * <p>
         * 根据 entity 条件，查询全部记录
         * </p>
         *
         * @param model 实体对象封装操作类（可以为 null）
         * @param wrapper   SQL包装
         * @return List<${g.entityName}Model>
         */
    List<${g.entityName}Model> selectList(${g.entityName}Model model,Wrapper<${g.entityName}Model> wrapper);


    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param pagination 分页查询条件
     * @param model   实体对象封装操作可以为 null）
     * @param wrapper   SQL包装
     * @return List<${g.entityName}Model>
     */
    List<${g.entityName}Model> selectPage(Pagination pagination, ${g.entityName}Model model,Wrapper<${g.entityName}Model> wrapper);

}
