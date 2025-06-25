
package io.github.wujun728.qixing.modular.pjcontract.param;

import io.github.wujun728.generate.core.ref.BaseParam;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
* 业务约定书参数类
 * @author wujun
 * @date 2025-06-25 00:58:22
*/
@Data
public class PjContractParam extends BaseParam {

    /**
     * 合同ID
     */
    @NotNull(message = "合同ID不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private String id;

    /**
     * 
     */
    private String refidProjectCodeHide;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空，请检查refProjectName参数", groups = {add.class, edit.class})
    private String refProjectName;

    /**
     * 合同状态
     */
    private String dictContractState;

    /**
     * 合同类型
     */
    @NotBlank(message = "合同类型不能为空，请检查dictContractType参数", groups = {add.class, edit.class})
    private String dictContractType;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 合同标题
     */
    @NotBlank(message = "合同标题不能为空，请检查contractName参数", groups = {add.class, edit.class})
    private String contractName;

    /**
     * 签约日期
     */
    @NotNull(message = "签约日期不能为空，请检查signTime参数", groups = {add.class, edit.class})
    private String signTime;

    /**
     * 报告数量
     */
    @NotNull(message = "报告数量不能为空，请检查reportCount参数", groups = {add.class, edit.class})
    private Integer reportCount;

    /**
     * 报告详细描述
     */
    @NotBlank(message = "报告详细描述不能为空，请检查reportDetail参数", groups = {add.class, edit.class})
    private String reportDetail;

    /**
     * 约定书金额
     */
    @NotNull(message = "约定书金额不能为空，请检查contractMoney参数", groups = {add.class, edit.class})
    private double contractMoney;

    /**
     * 签约/承诺人
     */
    @NotBlank(message = "签约/承诺人不能为空，请检查signBy参数", groups = {add.class, edit.class})
    private String signBy;

    /**
     * 执业天数
     */
    @NotNull(message = "执业天数不能为空，请检查contractDays参数", groups = {add.class, edit.class})
    private Integer contractDays;

    /**
     * 约定书详细描述
     */
    @NotBlank(message = "约定书详细描述不能为空，请检查contractDetails参数", groups = {add.class, edit.class})
    private String contractDetails;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空，请检查remark参数", groups = {add.class, edit.class})
    private String remark;

    /**
     * 
     */
    private String creator;

    /**
     * 
     */
    private String createId;

    /**
     * 
     */
    private String updateId;

    /**
     * 
     */
    private Integer deleted;

    /**
     * 
     */
    private String orderId;

    /**
     * 
     */
    private Integer orderStatus;

}
