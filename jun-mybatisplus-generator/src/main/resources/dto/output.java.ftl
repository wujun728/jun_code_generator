package ${package.Entity?replace("model","dto.output")};

<#if swaggerV3>
import io.swagger.v3.oas.annotations.media.Schema;
<#else>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.Getter;
import lombok.Setter;
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
<#if swaggerV3>
@Schema(name = "${entity}OutputDTO", description = "${table.comment!}")
<#else>
@ApiModel(value="${entity}OutputDTO对象", description="${table.comment!}")
</#if>
public class ${entity}OutputDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    <#assign commonFields = ["createBy", "createDate", "updateBy", "updateDate", "delFlag", "remark"]>

<#list table.fields as field>
    <#if !commonFields?seq_contains(field.propertyName)>
        <#if field.comment!?length gt 0>
    <#if swaggerV3>
    @Schema(description = "${field.comment}")
    <#else>
    @ApiModelProperty(value = "${field.comment}")
    </#if>
        </#if>
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>

}