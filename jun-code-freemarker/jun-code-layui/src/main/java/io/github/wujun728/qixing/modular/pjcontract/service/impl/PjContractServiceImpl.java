
package io.github.wujun728.qixing.modular.pjcontract.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wujun728.generate.core.ServiceException;
import io.github.wujun728.generate.core.ref.PageFactory;
import io.github.wujun728.generate.core.ref.PageResult;
import io.github.wujun728.qixing.modular.pjcontract.entity.PjContract;
import io.github.wujun728.qixing.modular.pjcontract.mapper.PjContractMapper;
import io.github.wujun728.qixing.modular.pjcontract.param.PjContractParam;
import io.github.wujun728.qixing.modular.pjcontract.service.PjContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 业务约定书service接口实现类
 * @author wujun
 * @date 2025-06-25 00:58:22
 */
@Service
public class PjContractServiceImpl extends ServiceImpl<PjContractMapper, PjContract> implements PjContractService {

    @Override
    public PageResult<PjContract> page(PjContractParam pjContractParam) {
        QueryWrapper<PjContract> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(pjContractParam)) {

            // 根据项目名称 查询
            if (ObjectUtil.isNotEmpty(pjContractParam.getRefProjectName())) {
                queryWrapper.lambda().like(PjContract::getRefProjectName, pjContractParam.getRefProjectName());
            }
            // 根据合同类型 查询
            if (ObjectUtil.isNotEmpty(pjContractParam.getDictContractType())) {
                queryWrapper.lambda().eq(PjContract::getDictContractType, pjContractParam.getDictContractType());
            }
            // 根据合同标题 查询
            if (ObjectUtil.isNotEmpty(pjContractParam.getContractName())) {
                queryWrapper.lambda().like(PjContract::getContractName, pjContractParam.getContractName());
            }
        }
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<PjContract> list(PjContractParam pjContractParam) {
        return this.list();
    }

    @Override
    public void add(PjContractParam pjContractParam) {
        PjContract pjContract = new PjContract();
        BeanUtil.copyProperties(pjContractParam, pjContract);
        this.save(pjContract);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PjContractParam> pjContractParamList) {
        pjContractParamList.forEach(pjContractParam -> {
        PjContract pjContract = this.queryPjContract(pjContractParam);
            this.removeById(pjContract.getId());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PjContractParam pjContractParam) {
        PjContract pjContract = this.queryPjContract(pjContractParam);
        BeanUtil.copyProperties(pjContractParam, pjContract);
        this.updateById(pjContract);
    }

    @Override
    public PjContract detail(PjContractParam pjContractParam) {
        return this.queryPjContract(pjContractParam);
    }

    /**
     * 获取业务约定书
     *
     * @author wujun
     * @date 2025-06-25 00:58:22
     */
    private PjContract queryPjContract(PjContractParam pjContractParam) {
        PjContract pjContract = this.getById(pjContractParam.getId());
        if (ObjectUtil.isNull(pjContract)) {
            throw new ServiceException();
        }
        return pjContract;
    }
}
