package com.jeasy.roleresource.manager;

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
import com.jeasy.roleresource.dao.RoleResourceDAO;
import com.jeasy.roleresource.dto.*;
import com.jeasy.roleresource.entity.RoleResourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 角色资源 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class RoleResourceManager extends BaseManagerImpl<RoleResourceDAO, RoleResourceEntity, RoleResourceDTO> {

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

    public static RoleResourceManager me() {
        return SpringContextHolder.getBean(RoleResourceManager.class);
    }

    public List<RoleResourceListResDTO> list(final RoleResourceListReqDTO roleresourceListReqDTO) {
        RoleResourceDTO roleresourceParamsDTO = new RoleResourceDTO();
        if (!Func.isEmpty(roleresourceListReqDTO)) {
            BeanKit.copyProperties(roleresourceListReqDTO, roleresourceParamsDTO, DEMO_CONVERTER);
        }

        List<RoleResourceDTO> roleresourceDTOList = super.findList(roleresourceParamsDTO);

        if (!Func.isEmpty(roleresourceDTOList)) {
            List<RoleResourceListResDTO> items = Lists.newArrayList();
            for (RoleResourceDTO roleresourceDTO : roleresourceDTOList) {
                RoleResourceListResDTO roleresourceListResDTO = new RoleResourceListResDTO();
                BeanKit.copyProperties(roleresourceDTO, roleresourceListResDTO, DEMO_CONVERTER);
                items.add(roleresourceListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<RoleResourceListResDTO> listByVersion1(final RoleResourceListReqDTO roleresourceListReqDTO) {
        return list(roleresourceListReqDTO);
    }

    public List<RoleResourceListResDTO> listByVersion2(final RoleResourceListReqDTO roleresourceListReqDTO) {
        return list(roleresourceListReqDTO);
    }

    public List<RoleResourceListResDTO> listByVersion3(final RoleResourceListReqDTO roleresourceListReqDTO) {
        return list(roleresourceListReqDTO);
    }

    public RoleResourceListResDTO listOne(final RoleResourceListReqDTO roleresourceListReqDTO) {
        RoleResourceDTO roleresourceParamsDTO = new RoleResourceDTO();
        if (!Func.isEmpty(roleresourceListReqDTO)) {
            BeanKit.copyProperties(roleresourceListReqDTO, roleresourceParamsDTO, DEMO_CONVERTER);
        }

        RoleResourceDTO roleresourceDTO = super.findOne(roleresourceParamsDTO);
        if (!Func.isEmpty(roleresourceDTO)) {
            RoleResourceListResDTO roleresourceListResDTO = new RoleResourceListResDTO();
            BeanKit.copyProperties(roleresourceDTO, roleresourceListResDTO, DEMO_CONVERTER);
            return roleresourceListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<RoleResourcePageResDTO> pagination(final RoleResourcePageReqDTO roleresourcePageReqDTO, final Integer current, final Integer size) {
        RoleResourceDTO roleResourceParamsDTO = new RoleResourceDTO();
        if (!Func.isEmpty(roleresourcePageReqDTO)) {
            BeanKit.copyProperties(roleresourcePageReqDTO, roleResourceParamsDTO, DEMO_CONVERTER);
        }

        PageDTO<RoleResourceDTO> roleResourceDTOPage = super.findPage(roleResourceParamsDTO, current, size);

        if (Func.isNotEmpty(roleResourceDTOPage) && Func.isNotEmpty(roleResourceDTOPage.getRecords())) {
            List<RoleResourcePageResDTO> roleResourcePageResDTOs = Lists.newArrayList();
            for (RoleResourceDTO roleresourceDTO : roleResourceDTOPage.getRecords()) {
                RoleResourcePageResDTO roleresourcePageResDTO = new RoleResourcePageResDTO();
                BeanKit.copyProperties(roleresourceDTO, roleresourcePageResDTO, DEMO_CONVERTER);
                roleResourcePageResDTOs.add(roleresourcePageResDTO);
            }

            PageDTO<RoleResourcePageResDTO> roleResourcePageResDTOPage = new PageDTO<>();
            roleResourcePageResDTOPage.setRecords(roleResourcePageResDTOs);
            roleResourcePageResDTOPage.setTotal(roleResourceDTOPage.getTotal());
            return roleResourcePageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean add(final RoleResourceAddReqDTO roleresourceAddReqDTO) {
        if (Func.isEmpty(roleresourceAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        RoleResourceDTO roleresourceDTO = new RoleResourceDTO();
        BeanKit.copyProperties(roleresourceAddReqDTO, roleresourceDTO, DEMO_CONVERTER);
        return super.saveDTO(roleresourceDTO);
    }

    public Boolean addAllColumn(final RoleResourceAddReqDTO roleresourceAddReqDTO) {
        if (Func.isEmpty(roleresourceAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        RoleResourceDTO roleresourceDTO = new RoleResourceDTO();
        BeanKit.copyProperties(roleresourceAddReqDTO, roleresourceDTO, DEMO_CONVERTER);
        return super.saveAllColumn(roleresourceDTO);
    }

    public Boolean addBatchAllColumn(final List<RoleResourceAddReqDTO> roleresourceAddReqDTOList) {
        if (Func.isEmpty(roleresourceAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        List<RoleResourceDTO> roleresourceDTOList = Lists.newArrayList();
        for (RoleResourceAddReqDTO roleresourceAddReqDTO : roleresourceAddReqDTOList) {
            RoleResourceDTO roleresourceDTO = new RoleResourceDTO();
            BeanKit.copyProperties(roleresourceAddReqDTO, roleresourceDTO, DEMO_CONVERTER);
            roleresourceDTOList.add(roleresourceDTO);
        }
        return super.saveBatchAllColumn(roleresourceDTOList);
    }

    public RoleResourceShowResDTO show(final Long id) {
        RoleResourceDTO roleresourceDTO = super.findById(id);

        if (!Func.isEmpty(roleresourceDTO)) {
            RoleResourceShowResDTO roleresourceShowResDTO = new RoleResourceShowResDTO();
            BeanKit.copyProperties(roleresourceDTO, roleresourceShowResDTO, DEMO_CONVERTER);
            return roleresourceShowResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<RoleResourceShowResDTO> showByIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new MessageException(ModelResult.CODE_200, "集合不能为空且大小大于0");
        }

        List<RoleResourceDTO> roleresourceDTOList = super.findBatchIds(ids);

        if (!Func.isEmpty(roleresourceDTOList)) {
            List<RoleResourceShowResDTO> roleresourceShowResDTOList = Lists.newArrayList();
            for (RoleResourceDTO roleresourceDTO : roleresourceDTOList) {
                RoleResourceShowResDTO roleresourceShowResDTO = new RoleResourceShowResDTO();
                BeanKit.copyProperties(roleresourceDTO, roleresourceShowResDTO, DEMO_CONVERTER);
                roleresourceShowResDTOList.add(roleresourceShowResDTO);
            }
            return roleresourceShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean modify(final RoleResourceModifyReqDTO roleresourceModifyReqDTO) {
        if (Func.isEmpty(roleresourceModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
        RoleResourceDTO roleresourceDTO = new RoleResourceDTO();
        BeanKit.copyProperties(roleresourceModifyReqDTO, roleresourceDTO, DEMO_CONVERTER);
        return super.modifyById(roleresourceDTO);
    }

    public Boolean modifyAllColumn(final RoleResourceModifyReqDTO roleresourceModifyReqDTO) {
        if (Func.isEmpty(roleresourceModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        RoleResourceDTO roleresourceDTO = new RoleResourceDTO();
        BeanKit.copyProperties(roleresourceModifyReqDTO, roleresourceDTO, DEMO_CONVERTER);
        return super.modifyAllColumnById(roleresourceDTO);
    }

    public Boolean removeByParams(final RoleResourceRemoveReqDTO roleresourceRemoveReqDTO) {
        if (Func.isEmpty(roleresourceRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        RoleResourceDTO roleresourceParamsDTO = new RoleResourceDTO();
        BeanKit.copyProperties(roleresourceRemoveReqDTO, roleresourceParamsDTO, DEMO_CONVERTER);
        return super.remove(roleresourceParamsDTO);
    }

    public List<RoleResourceDTO> findByRoleIds(final List<Long> roleIdList) {
        if (Func.isEmpty(roleIdList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        return super.findList(new QueryWrapper<RoleResourceEntity>().in(RoleResourceEntity.DB_COL_ROLE_ID, roleIdList));
    }

    @Override
    protected List<RoleResourceDTO> entityToDTOList(final List<RoleResourceEntity> roleresourceEntityList) {
        List<RoleResourceDTO> roleresourceDTOList = null;
        if (!Func.isEmpty(roleresourceEntityList)) {
            roleresourceDTOList = Lists.newArrayList();
            for (RoleResourceEntity roleresourceEntity : roleresourceEntityList) {
                roleresourceDTOList.add(entityToDTO(roleresourceEntity));
            }
        }
        return roleresourceDTOList;
    }

    @Override
    protected RoleResourceDTO entityToDTO(final RoleResourceEntity roleresourceEntity) {
        RoleResourceDTO roleresourceDTO = null;
        if (!Func.isEmpty(roleresourceEntity)) {
            roleresourceDTO = new RoleResourceDTO();
            BeanKit.copyProperties(roleresourceEntity, roleresourceDTO);
        }
        return roleresourceDTO;
    }

    @Override
    protected List<RoleResourceEntity> dtoToEntityList(final List<RoleResourceDTO> roleresourceDTOList) {
        List<RoleResourceEntity> roleresourceEntityList = null;
        if (!Func.isEmpty(roleresourceDTOList)) {
            roleresourceEntityList = Lists.newArrayList();
            for (RoleResourceDTO roleresourceDTO : roleresourceDTOList) {
                roleresourceEntityList.add(dtoToEntity(roleresourceDTO));
            }
        }
        return roleresourceEntityList;
    }

    @Override
    protected RoleResourceEntity dtoToEntity(final RoleResourceDTO roleresourceDTO) {
        RoleResourceEntity roleresourceEntity = null;
        if (!Func.isEmpty(roleresourceDTO)) {
            roleresourceEntity = new RoleResourceEntity();
            BeanKit.copyProperties(roleresourceDTO, roleresourceEntity);
        }
        return roleresourceEntity;
    }

    @Override
    protected RoleResourceEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new RoleResourceEntity();
        }
        return (RoleResourceEntity) MapKit.toBean(map, RoleResourceEntity.class);
    }

    @Override
    protected RoleResourceDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new RoleResourceDTO();
        }
        return (RoleResourceDTO) MapKit.toBean(map, RoleResourceDTO.class);
    }
}
