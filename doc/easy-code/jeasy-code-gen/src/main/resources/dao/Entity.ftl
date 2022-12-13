package ${conf.basePackage}.${table.lowerCamelName}.entity;

<#list table.columns as col>
<#if (col.classImport != "")>
import ${col.classImport};
</#if>
</#list>
import com.baomidou.mybatisplus.annotation.TableName;
import ${conf.basePackage}.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ${table.comment}
 *
 * @author ${conf.author}
 * @version ${conf.version}
 * @since ${conf.createDate}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${table.name}")
public class ${table.className}Entity extends BaseEntity {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_TABLE_NAME = "${table.name}";

    <#list table.columns as col>
    <#if col.camelName != "id" && col.camelName != "isDel" && col.camelName != "isTest" && col.camelName != "createAt" && col.camelName != "createBy" && col.camelName != "createName" && col.camelName != "updateAt" && col.camelName != "updateBy" && col.camelName != "updateName">
    public static final String DB_COL_${col.underLineUpperName} = "${col.name}";

    </#if>
    </#list>

    <#list table.columns as col>
    <#if col.camelName != "id" && col.camelName != "isDel" && col.camelName != "isTest" && col.camelName != "createAt" && col.camelName != "createBy" && col.camelName != "createName" && col.camelName != "updateAt" && col.camelName != "updateBy" && col.camelName != "updateName">
    /**
     * ${col.comment}
     */
    private ${col.javaType} ${col.camelName};

    </#if>
    </#list>
}
