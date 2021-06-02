<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import ${g.codePackage}.model.${g.entityName}Model;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${g.entityName}Dao
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@Repository
public interface ${g.entityName}Dao extends BaseMapper<${g.entityName}Model>{

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<${g.entityName}Model> selectList(@Param("e")${g.entityName}Model model,@Param("w") Wrapper<${g.entityName}Model> wrapper);

    /**
     * 分页查询信息
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<${g.entityName}Model> selectPage(@Param("p")Pagination pagination,@Param("e") ${g.entityName}Model model,@Param("w") Wrapper<${g.entityName}Model> wrapper);

}
