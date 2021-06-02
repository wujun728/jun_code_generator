<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.dao;

import ${g.codePackage}.model.${g.entityName}Model;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.io.Serializable;
/**
 * ${g.entityName}Dao
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@Repository
public interface ${g.entityName}Dao{

    /**
     * 新增
     */
    public int insert(${g.entityName}Model model);
    /**
     * 修改
     */
    public int updateById(${g.entityName}Model model);
    /**
     * 删除
     */
    public int deleteById(Serializable id);
        /**
         * <p>
         * 根据 ID 查询
         * </p>
         *
         * @param id 主键ID
         * @return ${g.entityName}Model
         */
    public ${g.entityName}Model selectById(Serializable id);

        /**
         * <p>
         * 根据 entity 条件，查询一条记录
         * </p>
         *
         * @param entity 实体对象
         * @return ${g.entityName}Model
         */
    public ${g.entityName}Model selectOne(${g.entityName}Model entity);

    /**
     * 查询列表
     * @param model
     * @return
     */
    public List<${g.entityName}Model> selectList(@Param("e")${g.entityName}Model model);

}
