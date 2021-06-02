<%if (t.isProtocol == 1) {%>
${g.copyright!}
<%}%>
package ${g.codePackage}.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

<%if (t.isImport == 1 || t.isExport == 1) {%>
import cn.afterturn.easypoi.excel.annotation.Excel
<%}%>

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * ${g.name}
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@TableName("${g.tableName}")
public class ${g.entityName}Model extends Model<${g.entityName}Model> {

    private static final long serialVersionUID = 1L;


<%for(field in t.fields){%>
    /**
     * ${field.chinaName}
     * ${field.comment}
     */
    <%if (field.name == g.idName) {%>
    @TableId(value = "g.idName",type = IdType.AUTO)
    <%}else{%>
    @TableField(value="${field.fieldName}")
    <%}%>
    <%if (t.isImport == 1 || t.isExport == 1) {%>
    @Excel(name = "${field.chinaName}")
    <%}%>
    private ${field.type} ${field.name};
<%}%>
<%for(field in t.fields){%>
    /**
     * 获取: ${field.chinaName}
     * ${field.comment}
     */
    public ${field.type} get${strutil.toUpperCase(strutil.subStringTo (field.name,0,1))}${strutil.subString  (field.name,1)}() {
    return ${field.name};
    }
    /**
     * 设置: ${field.chinaName}
     * ${field.comment}
     */
    public void set${strutil.toUpperCase(strutil.subStringTo (field.name,0,1))}${strutil.subString  (field.name,1)}(${field.type} ${field.name}) {
    this.${field.name} = ${field.name};
    }
<%}%>

    @Override
    protected Serializable pkVal() {
    return this.id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
