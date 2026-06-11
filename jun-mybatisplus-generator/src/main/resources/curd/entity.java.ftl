package ${package.Entity};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swaggerV3>
import io.swagger.v3.oas.annotations.media.Schema;
<#else>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.Getter;
import lombok.Setter;
<#if chainModel>
import lombok.experimental.Accessors;
</#if>
import java.io.Serializable;

/**
 * <p>
 * ${table.comment!}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Getter
@Setter
<#if chainModel>
@Accessors(chain = true)
</#if>
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if swaggerV3>
@Schema(name = "${entity}", description = "$!{table.comment}")
<#else>
@ApiModel(value = "${entity}对象", description = "${table.comment!}")
</#if>
public class ${entity} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swaggerV3>
    @Schema(description = "${field.comment}")
        <#else>
    @ApiModelProperty("${field.comment}")
        </#if>
    </#if>
    <#if field.keyFlag>
        <#if field.keyIdentityFlag>
    @TableId(value = "${field.annotationColumnName}", type = IdType.AUTO)
        <#elseif idType??>
    @TableId(value = "${field.annotationColumnName}", type = IdType.${idType})
        <#elseif field.convert>
    @TableId("${field.annotationColumnName}")
        </#if>
    <#elseif field.fill??>
        <#if field.convert>
    @TableField(value = "${field.annotationColumnName}", fill = FieldFill.${field.fill})
        <#else>
    @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
    @TableField("${field.annotationColumnName}")
    </#if>
    <#if field.versionField>
    @Version
    </#if>
    <#if field.logicDeleteField>
    @TableLogic
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>

}