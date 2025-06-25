
package io.github.wujun728.qixing.modular.pjcontract.controller;

import io.github.wujun728.generate.core.annotion.BusinessLog;
import io.github.wujun728.generate.core.annotion.Permission;
import io.github.wujun728.generate.core.enums.LogAnnotionOpTypeEnum;
import io.github.wujun728.generate.core.ref.PageResult;
import io.github.wujun728.generate.core.ref.ResponseData;
import io.github.wujun728.generate.core.ref.SuccessResponseData;
import io.github.wujun728.qixing.modular.pjcontract.entity.PjContract;
import io.github.wujun728.qixing.modular.pjcontract.param.PjContractParam;
import io.github.wujun728.qixing.modular.pjcontract.service.PjContractService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.List;

/**
 * 业务约定书控制器
 * @author wujun
 * @date 2025-06-25 00:58:22
 */
@Controller
public class PjContractController {

    private String PATH_PREFIX = "pjContract/";

    @Resource
    private PjContractService pjContractService;

    /**
     * 业务约定书页面
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    @Permission
    @GetMapping("/pjContract/index")
    public String index() {
        return PATH_PREFIX + "index.html";
    }

    /**
     * 业务约定书表单页面
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    @GetMapping("/pjContract/form")
    public String form() {
        return PATH_PREFIX + "form.html";
    }

    /**
     * 查询业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    @Permission
    @ResponseBody
    @GetMapping("/pjContract/page")
    @BusinessLog(title = "业务约定书_查询", opType = LogAnnotionOpTypeEnum.QUERY)
    public PageResult<PjContract> page(PjContractParam pjContractParam) {
        return pjContractService.page(pjContractParam);
    }

    /**
     * 添加业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    @Permission
    @ResponseBody
    @PostMapping("/pjContract/add")
    @BusinessLog(title = "业务约定书_增加", opType = LogAnnotionOpTypeEnum.ADD)
    public ResponseData add(@RequestBody @Validated(PjContractParam.add.class) PjContractParam pjContractParam) {
        pjContractService.add(pjContractParam);
        return new SuccessResponseData();
    }

    /**
     * 删除业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    @Permission
    @ResponseBody
    @PostMapping("/pjContract/delete")
    @BusinessLog(title = "业务约定书_删除", opType = LogAnnotionOpTypeEnum.DELETE)
    public ResponseData delete(@RequestBody @Validated(PjContractParam.delete.class) List<PjContractParam> pjContractParamList) {
        pjContractService.delete(pjContractParamList);
        return new SuccessResponseData();
    }

    /**
     * 编辑业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    @Permission
    @ResponseBody
    @PostMapping("/pjContract/edit")
    @BusinessLog(title = "业务约定书_编辑", opType = LogAnnotionOpTypeEnum.EDIT)
    public ResponseData edit(@RequestBody @Validated(PjContractParam.edit.class) PjContractParam pjContractParam) {
        pjContractService.edit(pjContractParam);
        return new SuccessResponseData();
    }

    /**
     * 查看业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    @Permission
    @ResponseBody
    @GetMapping("/pjContract/detail")
    @BusinessLog(title = "业务约定书_查看", opType = LogAnnotionOpTypeEnum.DETAIL)
    public ResponseData detail(@Validated(PjContractParam.detail.class) PjContractParam pjContractParam) {
        return new SuccessResponseData(pjContractService.detail(pjContractParam));
    }

    /**
     * 业务约定书列表
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    @Permission
    @ResponseBody
    @GetMapping("/pjContract/list")
    @BusinessLog(title = "业务约定书_列表", opType = LogAnnotionOpTypeEnum.QUERY)
    public ResponseData list(PjContractParam pjContractParam) {
        return new SuccessResponseData(pjContractService.list(pjContractParam));
    }

}
