package com.jeasy.organization.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.manager.impl.BaseManagerImpl;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.object.AbstractConverter;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.object.MapKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.exception.MessageException;
import com.jeasy.organization.biz.OrganizationBiz;
import com.jeasy.organization.dao.OrganizationDAO;
import com.jeasy.organization.dto.*;
import com.jeasy.organization.entity.OrganizationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 机构 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class OrganizationManager extends BaseManagerImpl<OrganizationDAO, OrganizationEntity, OrganizationDTO> {

    /**
     * this is a converter demo only for BeanKit.copyProperties
     *
     * @see BeanKit#copyProperties(Object source, Object target, AbstractConverter... converters)
     */
    private static final AbstractConverter<String, String> DEMO_CONVERTER = new AbstractConverter<String, String>("filed1", "filed2") {
        @Override
        public String convert(final String val) {
            return val;
        }
    };

    public static OrganizationManager me() {
        return SpringContextHolder.getBean(OrganizationManager.class);
    }

    public List<OrganizationListResDTO> list(OrganizationListReqDTO organizationListReqDTO) {
        OrganizationDTO organizationParamsDTO = new OrganizationDTO();
        if (!Func.isEmpty(organizationListReqDTO)) {
            BeanKit.copyProperties(organizationListReqDTO, organizationParamsDTO, DEMO_CONVERTER);
        }

        List<OrganizationDTO> organizationDTOList = super.findList(organizationParamsDTO);

        if (!Func.isEmpty(organizationDTOList)) {
            return OrganizationBiz.me().transferOrganizationListResDTO(organizationDTOList);
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public OrganizationListResDTO listOne(final OrganizationListReqDTO organizationListReqDTO) {
        OrganizationDTO organizationParamsDTO = new OrganizationDTO();
        if (!Func.isEmpty(organizationListReqDTO)) {
            BeanKit.copyProperties(organizationListReqDTO, organizationParamsDTO, DEMO_CONVERTER);
        }

        OrganizationDTO organizationDTO = super.findOne(organizationParamsDTO);
        if (!Func.isEmpty(organizationDTO)) {
            OrganizationListResDTO organizationListResDTO = new OrganizationListResDTO();
            BeanKit.copyProperties(organizationDTO, organizationListResDTO, DEMO_CONVERTER);
            return organizationListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<OrganizationPageResDTO> pagination(final OrganizationPageReqDTO organizationPageReqDTO, final Integer current, final Integer size) {
        OrganizationDTO organizationParamsDTO = new OrganizationDTO();
        if (!Func.isEmpty(organizationPageReqDTO)) {
            BeanKit.copyProperties(organizationPageReqDTO, organizationParamsDTO, DEMO_CONVERTER);
        }

        PageDTO<OrganizationDTO> organizationDTOPage = super.findPage(organizationParamsDTO, current, size);

        if (Func.isNotEmpty(organizationDTOPage) && Func.isNotEmpty(organizationDTOPage.getRecords())) {
            List<OrganizationPageResDTO> organizationPageResDTOs = Lists.newArrayList();
            for (OrganizationDTO organizationDTO : organizationDTOPage.getRecords()) {
                OrganizationPageResDTO organizationPageResDTO = new OrganizationPageResDTO();
                BeanKit.copyProperties(organizationDTO, organizationPageResDTO, DEMO_CONVERTER);
                organizationPageResDTOs.add(organizationPageResDTO);
            }

            PageDTO<OrganizationPageResDTO> organizationPageResDTOPage = new PageDTO<>();
            organizationPageResDTOPage.setRecords(organizationPageResDTOs);
            organizationPageResDTOPage.setTotal(organizationDTOPage.getTotal());
            return organizationPageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean add(final OrganizationAddReqDTO organizationAddReqDTO) {
        if (OrganizationBiz.me().validateOrganizationAddReqDTO(organizationAddReqDTO)) {
            QueryWrapper<OrganizationEntity> queryWrapper = OrganizationBiz.me().transferOrganizationQueryWrapper(organizationAddReqDTO);
            Integer count = OrganizationManager.me().findCount(queryWrapper);
            if (!Func.isNullOrZero(count)) {
                throw new MessageException(ModelResult.CODE_200, "名称或编码已存在");
            }

            return super.saveDTO(OrganizationBiz.me().transferOrganizationDTO(organizationAddReqDTO));
        }
        return false;
    }

    public Boolean addAllColumn(final OrganizationAddReqDTO organizationAddReqDTO) {
        if (Func.isEmpty(organizationAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanKit.copyProperties(organizationAddReqDTO, organizationDTO, DEMO_CONVERTER);
        return super.saveAllColumn(organizationDTO);
    }

    public Boolean addBatchAllColumn(final List<OrganizationAddReqDTO> organizationAddReqDTOList) {
        if (Func.isEmpty(organizationAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        List<OrganizationDTO> organizationDTOList = Lists.newArrayList();
        for (OrganizationAddReqDTO organizationAddReqDTO : organizationAddReqDTOList) {
            OrganizationDTO organizationDTO = new OrganizationDTO();
            BeanKit.copyProperties(organizationAddReqDTO, organizationDTO, DEMO_CONVERTER);
            organizationDTOList.add(organizationDTO);
        }
        return super.saveBatchAllColumn(organizationDTOList);
    }

    public OrganizationShowResDTO show(final Long id) {
        OrganizationDTO organizationDTO = super.findById(id);

        if (!Func.isEmpty(organizationDTO)) {
            OrganizationDTO parentOrganizationDTO = super.findById(organizationDTO.getPid());
            return OrganizationBiz.me().transferOrganizationShowResDTO(organizationDTO, parentOrganizationDTO);
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<OrganizationShowResDTO> showByIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new MessageException(ModelResult.CODE_200, "集合不能为空且大小大于0");
        }

        List<OrganizationDTO> organizationDTOList = super.findBatchIds(ids);

        if (!Func.isEmpty(organizationDTOList)) {
            List<OrganizationShowResDTO> organizationShowResDTOList = Lists.newArrayList();
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                OrganizationShowResDTO organizationShowResDTO = new OrganizationShowResDTO();
                BeanKit.copyProperties(organizationDTO, organizationShowResDTO, DEMO_CONVERTER);
                organizationShowResDTOList.add(organizationShowResDTO);
            }
            return organizationShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean modify(final OrganizationModifyReqDTO organizationModifyReqDTO) {
        if (OrganizationBiz.me().validateOrganizationModifyReqDTO(organizationModifyReqDTO)) {
            QueryWrapper<OrganizationEntity> queryWrapper = OrganizationBiz.me().transferOrganizationQueryWrapper(organizationModifyReqDTO);
            Integer count = OrganizationManager.me().findCount(queryWrapper);
            if (!Func.isNullOrZero(count)) {
                throw new MessageException(ModelResult.CODE_200, "名称或编码已存在");
            }

            return super.modifyById(OrganizationBiz.me().transferOrganizationDTO(organizationModifyReqDTO));
        }
        return false;
    }

    public Boolean modifyAllColumn(final OrganizationModifyReqDTO organizationModifyReqDTO) {
        if (Func.isEmpty(organizationModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanKit.copyProperties(organizationModifyReqDTO, organizationDTO, DEMO_CONVERTER);
        return super.modifyAllColumnById(organizationDTO);
    }

    public Boolean removeByParams(final OrganizationRemoveReqDTO organizationRemoveReqDTO) {
        if (Func.isEmpty(organizationRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        OrganizationDTO organizationParamsDTO = new OrganizationDTO();
        BeanKit.copyProperties(organizationRemoveReqDTO, organizationParamsDTO, DEMO_CONVERTER);
        return super.remove(organizationParamsDTO);
    }

    @Override
    public Boolean removeById(final Long id) {

        QueryWrapper<OrganizationEntity> parentOrganizationWrapper = OrganizationBiz.me().buildParentQueryWrapper(id);
        if (Func.isNotEmpty(parentOrganizationWrapper)) {
            List<OrganizationDTO> subOrganizationDTOs = super.findList(parentOrganizationWrapper);
            if (Func.isNotEmpty(subOrganizationDTOs)) {
                throw new MessageException(ModelResult.CODE_200, "请先删除子机构");
            }
        }

        return super.removeById(id);
    }


    @Override
    protected List<OrganizationDTO> entityToDTOList(final List<OrganizationEntity> organizationEntityList) {
        List<OrganizationDTO> organizationDTOList = null;
        if (!Func.isEmpty(organizationEntityList)) {
            organizationDTOList = Lists.newArrayList();
            for (OrganizationEntity organizationEntity : organizationEntityList) {
                organizationDTOList.add(entityToDTO(organizationEntity));
            }
        }
        return organizationDTOList;
    }

    @Override
    protected OrganizationDTO entityToDTO(final OrganizationEntity organizationEntity) {
        OrganizationDTO organizationDTO = null;
        if (!Func.isEmpty(organizationEntity)) {
            organizationDTO = new OrganizationDTO();
            BeanKit.copyProperties(organizationEntity, organizationDTO);
        }
        return organizationDTO;
    }

    @Override
    protected List<OrganizationEntity> dtoToEntityList(final List<OrganizationDTO> organizationDTOList) {
        List<OrganizationEntity> organizationEntityList = null;
        if (!Func.isEmpty(organizationDTOList)) {
            organizationEntityList = Lists.newArrayList();
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                organizationEntityList.add(dtoToEntity(organizationDTO));
            }
        }
        return organizationEntityList;
    }

    @Override
    protected OrganizationEntity dtoToEntity(final OrganizationDTO organizationDTO) {
        OrganizationEntity organizationEntity = null;
        if (!Func.isEmpty(organizationDTO)) {
            organizationEntity = new OrganizationEntity();
            BeanKit.copyProperties(organizationDTO, organizationEntity);
        }
        return organizationEntity;
    }

    @Override
    protected OrganizationEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new OrganizationEntity();
        }
        return (OrganizationEntity) MapKit.toBean(map, OrganizationEntity.class);
    }

    @Override
    protected OrganizationDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new OrganizationDTO();
        }
        return (OrganizationDTO) MapKit.toBean(map, OrganizationDTO.class);
    }
}
