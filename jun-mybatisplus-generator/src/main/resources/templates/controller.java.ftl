<#assign serviceLower = table.serviceName?uncap_first>
<#assign entityLower = entity?uncap_first>
<#assign RequestMappingCustom = table.entityPath?substring(0,table.entityPath?length-6)>
package ${package.Controller};

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.trueland.scrm.common.model.rpc.Rsp;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

import java.util.List;

<#if restControllerStyle>
<#--import org.springframework.web.bind.annotation.RestController;-->
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
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
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${RequestMappingCustom}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} ${serviceLower};

    @GetMapping("/detail")
    @Operation(summary = "${table.comment}详情")
    public Rsp<${entity}> detail(@RequestParam("id") Long id){
        return Rsp.success(${serviceLower}.detail(id));
    }

    @PostMapping("/insert")
    @Operation(summary = "${table.comment}新增")
    public Rsp<Boolean> insert(@RequestBody ${entity} ${entityLower}){
        return Rsp.success(${serviceLower}.insert(${entityLower}));
    }

    @PostMapping("/update")
    @Operation(summary = "${table.comment}修改")
    public Rsp<Boolean> update(@RequestBody ${entity} ${entityLower}){
        return Rsp.success(${serviceLower}.update(${entityLower}));
    }

    @PostMapping("/delete")
    @Operation(summary = "${table.comment}删除")
    public Rsp<Boolean> delete(@RequestParam("id") Long id){
        return Rsp.success(${serviceLower}.delete(id));
    }

    @GetMapping("/list")
    @Operation(summary = "${table.comment}列表")
    public Rsp< List<${entity}> > list(){
        return Rsp.success(${serviceLower}.list());
    }

}
</#if>
