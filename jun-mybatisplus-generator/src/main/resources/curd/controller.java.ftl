<#assign serviceLower = table.serviceName?uncap_first>
<#assign entityLower = entity?uncap_first>
package ${package.Controller};

<#if swaggerV3>
import io.swagger.v3.oas.annotations.Operation;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
<#if responseClass??>
import ${responseClass};
</#if>
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

import java.util.List;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/${table.entityPath}")
public class ${table.controllerName} {

    @Autowired
    private ${table.serviceName} ${serviceLower};

<#if swaggerV3>
    @Operation(summary = "${table.comment}详情")
</#if>
    @GetMapping("/detail")
    public <#if responseClass??>${responseClass?keep_after_last(".")}<${entity}>><#else>${entity}</#if> detail(@RequestParam("id") Long id) {
    <#if responseClass??>
        return ${responseClass?keep_after_last(".")}.success(${serviceLower}.detail(id));
    <#else>
        return ${serviceLower}.detail(id);
    </#if>
    }

<#if swaggerV3>
    @Operation(summary = "${table.comment}新增")
</#if>
    @PostMapping("/insert")
    public <#if responseClass??>${responseClass?keep_after_last(".")}<Boolean><#else>boolean</#if> insert(@RequestBody ${entity} ${entityLower}) {
    <#if responseClass??>
        return ${responseClass?keep_after_last(".")}.success(${serviceLower}.insert(${entityLower}));
    <#else>
        return ${serviceLower}.insert(${entityLower});
    </#if>
    }

<#if swaggerV3>
    @Operation(summary = "${table.comment}修改")
</#if>
    @PostMapping("/update")
    public <#if responseClass??>${responseClass?keep_after_last(".")}<Boolean><#else>boolean</#if> update(@RequestBody ${entity} ${entityLower}) {
    <#if responseClass??>
        return ${responseClass?keep_after_last(".")}.success(${serviceLower}.update(${entityLower}));
    <#else>
        return ${serviceLower}.update(${entityLower});
    </#if>
    }

<#if swaggerV3>
    @Operation(summary = "${table.comment}删除")
</#if>
    @PostMapping("/delete")
    public <#if responseClass??>${responseClass?keep_after_last(".")}<Boolean><#else>boolean</#if> delete(@RequestParam("id") Long id) {
    <#if responseClass??>
        return ${responseClass?keep_after_last(".")}.success(${serviceLower}.delete(id));
    <#else>
        return ${serviceLower}.delete(id);
    </#if>
    }

<#if swaggerV3>
    @Operation(summary = "${table.comment}列表")
</#if>
    @GetMapping("/list")
    public <#if responseClass??>${responseClass?keep_after_last(".")}<List<${entity}>><#else>List<${entity}></#if> list() {
    <#if responseClass??>
        return ${responseClass?keep_after_last(".")}.success(${serviceLower}.list());
    <#else>
        return ${serviceLower}.list();
    </#if>
    }

}