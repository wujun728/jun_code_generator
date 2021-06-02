<%if (t.isProtocol == 1) {%>
${g.copyright!}
<%}%>
package ${g.codePackage}.model;

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
public class ${g.entityName}Model {

    private static final long serialVersionUID = 1L;


<%for(field in t.fields){%>
    /**
     * ${field.chinaName}
     * ${field.comment}
     */
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
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
