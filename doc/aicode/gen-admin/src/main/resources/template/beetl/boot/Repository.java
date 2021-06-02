<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import ${g.codePackage}.model.${g.entityName}Model;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${g.entityName}持久化
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@Repository
public interface ${g.entityName}Repository extends BaseMapper<${g.entityName}Model>{

}
