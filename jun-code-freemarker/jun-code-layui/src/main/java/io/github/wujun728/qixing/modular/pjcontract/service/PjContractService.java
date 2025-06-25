
package io.github.wujun728.qixing.modular.pjcontract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.wujun728.generate.core.ref.PageResult;
import io.github.wujun728.qixing.modular.pjcontract.entity.PjContract;
import io.github.wujun728.qixing.modular.pjcontract.param.PjContractParam;
import java.util.List;

/**
 * 业务约定书service接口
 * @author wujun
 * @date 2025-06-25 00:58:22
 */
public interface PjContractService extends IService<PjContract> {

    /**
     * 查询业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    PageResult<PjContract> page(PjContractParam pjContractParam);

    /**
     * 业务约定书列表
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    List<PjContract> list(PjContractParam pjContractParam);

    /**
     * 添加业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    void add(PjContractParam pjContractParam);

    /**
     * 删除业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    void delete(List<PjContractParam> pjContractParamList);

    /**
     * 编辑业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    void edit(PjContractParam pjContractParam);

    /**
     * 查看业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
     PjContract detail(PjContractParam pjContractParam);
}
