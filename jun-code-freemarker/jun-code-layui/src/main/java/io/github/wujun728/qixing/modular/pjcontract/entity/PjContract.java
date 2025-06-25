
package io.github.wujun728.qixing.modular.pjcontract.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.github.wujun728.generate.core.ref.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.*;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 业务约定书
 * @author wujun
 * @date 2025-06-25 00:58:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("pj_contract")
public class PjContract extends BaseEntity {

    /**
     * 合同ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 
     */
    private String refidProjectCodeHide;

    /**
     * 项目名称
     */
    private String refProjectName;

    /**
     * 合同状态
     */
    private String dictContractState;

    /**
     * 合同类型
     */
    private String dictContractType;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 合同标题
     */
    private String contractName;

    /**
     * 签约日期
     */
    @Excel(name = "签约日期", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", width = 20)
    private Date signTime;

    /**
     * 报告数量
     */
    private Integer reportCount;

    /**
     * 报告详细描述
     */
    private String reportDetail;

    /**
     * 约定书金额
     */
    private double contractMoney;

    /**
     * 签约/承诺人
     */
    private String signBy;

    /**
     * 执业天数
     */
    private Integer contractDays;

    /**
     * 约定书详细描述
     */
    private String contractDetails;

    /**
     * 备注
     */
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
