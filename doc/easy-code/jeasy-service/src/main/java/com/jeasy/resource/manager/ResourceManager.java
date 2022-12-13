package com.jeasy.resource.manager;

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
import com.jeasy.resource.biz.ResourceBiz;
import com.jeasy.resource.dao.ResourceDAO;
import com.jeasy.resource.dto.*;
import com.jeasy.resource.entity.ResourceEntity;
import com.jeasy.roleresource.dto.RoleResourceDTO;
import com.jeasy.roleresource.manager.RoleResourceManager;
import com.jeasy.userrole.dto.UserRoleDTO;
import com.jeasy.userrole.manager.UserRoleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 菜单 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class ResourceManager extends BaseManagerImpl<ResourceDAO, ResourceEntity, ResourceDTO> {

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

    public static ResourceManager me() {
        return SpringContextHolder.getBean(ResourceManager.class);
    }

    public List<ResourceListResDTO> list(ResourceListReqDTO resourceListReqDTO) {
        ResourceDTO resourceParamsDTO = new ResourceDTO();
        if (!Func.isEmpty(resourceListReqDTO)) {
            BeanKit.copyProperties(resourceListReqDTO, resourceParamsDTO, DEMO_CONVERTER);
        }

        List<ResourceDTO> resourceDTOList = super.findList(resourceParamsDTO);

        if (!Func.isEmpty(resourceDTOList)) {
            return ResourceBiz.me().transferResourceListResDTO(resourceDTOList);
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public ResourceListResDTO listOne(final ResourceListReqDTO resourceListReqDTO) {
        ResourceDTO resourceParamsDTO = new ResourceDTO();
        if (!Func.isEmpty(resourceListReqDTO)) {
            BeanKit.copyProperties(resourceListReqDTO, resourceParamsDTO, DEMO_CONVERTER);
        }

        ResourceDTO resourceDTO = super.findOne(resourceParamsDTO);
        if (!Func.isEmpty(resourceDTO)) {
            ResourceListResDTO resourceListResDTO = new ResourceListResDTO();
            BeanKit.copyProperties(resourceDTO, resourceListResDTO, DEMO_CONVERTER);
            return resourceListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<ResourcePageResDTO> pagination(final ResourcePageReqDTO resourcePageReqDTO, final Integer current, final Integer size) {
        ResourceDTO resourceParamsDTO = new ResourceDTO();
        if (!Func.isEmpty(resourcePageReqDTO)) {
            BeanKit.copyProperties(resourcePageReqDTO, resourceParamsDTO, DEMO_CONVERTER);
        }

        PageDTO<ResourceDTO> resourceDTOPage = super.findPage(resourceParamsDTO, current, size);

        if (Func.isNotEmpty(resourceDTOPage) && Func.isNotEmpty(resourceDTOPage.getRecords())) {
            List<ResourcePageResDTO> resourcePageResDTOs = Lists.newArrayList();
            for (ResourceDTO resourceDTO : resourceDTOPage.getRecords()) {
                ResourcePageResDTO resourcePageResDTO = new ResourcePageResDTO();
                BeanKit.copyProperties(resourceDTO, resourcePageResDTO, DEMO_CONVERTER);
                resourcePageResDTOs.add(resourcePageResDTO);
            }

            PageDTO<ResourcePageResDTO> resourcePageResDTOPage = new PageDTO<>();
            resourcePageResDTOPage.setRecords(resourcePageResDTOs);
            resourcePageResDTOPage.setTotal(resourceDTOPage.getTotal());
            return resourcePageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean add(final ResourceAddReqDTO resourceAddReqDTO) {
        if (Func.isEmpty(resourceAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        QueryWrapper<ResourceEntity> queryWrapper = ResourceBiz.me().transferResourceQueryWrapper(resourceAddReqDTO);
        Integer count = ResourceManager.me().findCount(queryWrapper);
        if (!Func.isNullOrZero(count)) {
            throw new MessageException(ModelResult.CODE_200, "编码已存在");
        }

        ResourceDTO resourceDTO = new ResourceDTO();
        BeanKit.copyProperties(resourceAddReqDTO, resourceDTO, DEMO_CONVERTER);
        return super.saveDTO(resourceDTO);
    }

    public Boolean addAllColumn(final ResourceAddReqDTO resourceAddReqDTO) {
        if (Func.isEmpty(resourceAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        ResourceDTO resourceDTO = new ResourceDTO();
        BeanKit.copyProperties(resourceAddReqDTO, resourceDTO, DEMO_CONVERTER);
        return super.saveAllColumn(resourceDTO);
    }

    public Boolean addBatchAllColumn(final List<ResourceAddReqDTO> resourceAddReqDTOList) {
        if (Func.isEmpty(resourceAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        List<ResourceDTO> resourceDTOList = Lists.newArrayList();
        for (ResourceAddReqDTO resourceAddReqDTO : resourceAddReqDTOList) {
            ResourceDTO resourceDTO = new ResourceDTO();
            BeanKit.copyProperties(resourceAddReqDTO, resourceDTO, DEMO_CONVERTER);
            resourceDTOList.add(resourceDTO);
        }
        return super.saveBatchAllColumn(resourceDTOList);
    }

    public ResourceShowResDTO show(final Long id) {
        ResourceDTO resourceDTO = super.findById(id);

        if (!Func.isEmpty(resourceDTO)) {
            ResourceDTO parentResourceDTO = super.findById(resourceDTO.getPid());
            return ResourceBiz.me().transferResourceShowResDTO(resourceDTO, parentResourceDTO);
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<ResourceShowResDTO> showByIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new MessageException(ModelResult.CODE_200, "集合不能为空且大小大于0");
        }

        List<ResourceDTO> resourceDTOList = super.findBatchIds(ids);

        if (!Func.isEmpty(resourceDTOList)) {
            List<ResourceShowResDTO> resourceShowResDTOList = Lists.newArrayList();
            for (ResourceDTO resourceDTO : resourceDTOList) {
                ResourceShowResDTO resourceShowResDTO = new ResourceShowResDTO();
                BeanKit.copyProperties(resourceDTO, resourceShowResDTO, DEMO_CONVERTER);
                resourceShowResDTOList.add(resourceShowResDTO);
            }
            return resourceShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean modify(final ResourceModifyReqDTO resourceModifyReqDTO) {
        if (Func.isEmpty(resourceModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        QueryWrapper<ResourceEntity> queryWrapper = ResourceBiz.me().transferResourceQueryWrapper(resourceModifyReqDTO);
        Integer count = ResourceManager.me().findCount(queryWrapper);
        if (!Func.isNullOrZero(count)) {
            throw new MessageException(ModelResult.CODE_200, "编码已存在");
        }

        ResourceDTO resourceDTO = new ResourceDTO();
        BeanKit.copyProperties(resourceModifyReqDTO, resourceDTO, DEMO_CONVERTER);
        return super.modifyById(resourceDTO);
    }

    public Boolean modifyAllColumn(final ResourceModifyReqDTO resourceModifyReqDTO) {
        if (Func.isEmpty(resourceModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        ResourceDTO resourceDTO = new ResourceDTO();
        BeanKit.copyProperties(resourceModifyReqDTO, resourceDTO, DEMO_CONVERTER);
        return super.modifyAllColumnById(resourceDTO);
    }

    public Boolean removeByParams(final ResourceRemoveReqDTO resourceRemoveReqDTO) {
        if (Func.isEmpty(resourceRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        ResourceDTO resourceParamsDTO = new ResourceDTO();
        BeanKit.copyProperties(resourceRemoveReqDTO, resourceParamsDTO, DEMO_CONVERTER);
        return super.remove(resourceParamsDTO);
    }

    public List<UserMenuResourceDTO> listUserMenu() {
        List<ResourceDTO> resourceDTOList = findUserResourceDTOList();
        return ResourceBiz.me().transferUserMenuResourceDTOList(resourceDTOList);
    }

    private List<ResourceDTO> findUserResourceDTOList() {
        UserRoleDTO userRoleDTO = ResourceBiz.me().transferUserRoleDTO(getCurrentUser().getId());

        List<UserRoleDTO> userRoleDTOs = UserRoleManager.me().findList(userRoleDTO);
        List<Long> roleIdList = ResourceBiz.me().transferUserRoleIdList(userRoleDTOs);

        List<ResourceDTO> resourceDTOList = Lists.newArrayList();
        if (Func.isNotEmpty(roleIdList)) {
            List<RoleResourceDTO> roleResourceDTOList = RoleResourceManager.me().findByRoleIds(roleIdList);
            List<Long> resourceIdList = ResourceBiz.me().transferUserResourceIdList(roleResourceDTOList);
            if (Func.isNotEmpty(resourceIdList)) {
                resourceDTOList = super.findBatchIds(resourceIdList);
            }
        }
        return resourceDTOList;
    }

    public List<UserMenuOperationDTO> listUserMenuOperation(final String menuPath) {
        ResourceDTO resourceParamDTO = ResourceBiz.me().transferResourceParamDTO(menuPath);
        ResourceDTO menuDTO = super.findOne(resourceParamDTO);

        if (Func.isEmpty(menuDTO)) {
            throw new MessageException(ModelResult.CODE_200, "当前菜单不存在");
        }

        List<UserMenuOperationDTO> userMenuOperationDTOList = Lists.newArrayList();
        List<ResourceDTO> resourceDTOList = findUserResourceDTOList();
        if (Func.isNotEmpty(resourceDTOList)) {
            for (ResourceDTO resourceDTO : resourceDTOList) {
                if (resourceDTO.getPid().longValue() == menuDTO.getId().longValue()) {
                    UserMenuOperationDTO userMenuOperationDTO = new UserMenuOperationDTO();
                    BeanKit.copyProperties(resourceDTO, userMenuOperationDTO);
                    userMenuOperationDTOList.add(userMenuOperationDTO);
                }
            }
        }
        return userMenuOperationDTOList;
    }

    @Override
    public Boolean removeById(final Long id) {

        QueryWrapper<ResourceEntity> parentResourceWrapper = ResourceBiz.me().buildParentQueryWrapper(id);
        if (Func.isNotEmpty(parentResourceWrapper)) {
            List<ResourceDTO> subResourceDTOs = super.findList(parentResourceWrapper);
            if (Func.isNotEmpty(subResourceDTOs)) {
                throw new MessageException(ModelResult.CODE_200, "请先删除子菜单");
            }
        }

        return super.removeById(id);
    }

    @Override
    protected List<ResourceDTO> entityToDTOList(final List<ResourceEntity> resourceEntityList) {
        List<ResourceDTO> resourceDTOList = null;
        if (!Func.isEmpty(resourceEntityList)) {
            resourceDTOList = Lists.newArrayList();
            for (ResourceEntity resourceEntity : resourceEntityList) {
                resourceDTOList.add(entityToDTO(resourceEntity));
            }
        }
        return resourceDTOList;
    }

    @Override
    protected ResourceDTO entityToDTO(final ResourceEntity resourceEntity) {
        ResourceDTO resourceDTO = null;
        if (!Func.isEmpty(resourceEntity)) {
            resourceDTO = new ResourceDTO();
            BeanKit.copyProperties(resourceEntity, resourceDTO);
        }
        return resourceDTO;
    }

    @Override
    protected List<ResourceEntity> dtoToEntityList(final List<ResourceDTO> resourceDTOList) {
        List<ResourceEntity> resourceEntityList = null;
        if (!Func.isEmpty(resourceDTOList)) {
            resourceEntityList = Lists.newArrayList();
            for (ResourceDTO resourceDTO : resourceDTOList) {
                resourceEntityList.add(dtoToEntity(resourceDTO));
            }
        }
        return resourceEntityList;
    }

    @Override
    protected ResourceEntity dtoToEntity(final ResourceDTO resourceDTO) {
        ResourceEntity resourceEntity = null;
        if (!Func.isEmpty(resourceDTO)) {
            resourceEntity = new ResourceEntity();
            BeanKit.copyProperties(resourceDTO, resourceEntity);
        }
        return resourceEntity;
    }

    @Override
    protected ResourceEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new ResourceEntity();
        }
        return (ResourceEntity) MapKit.toBean(map, ResourceEntity.class);
    }

    @Override
    protected ResourceDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new ResourceDTO();
        }
        return (ResourceDTO) MapKit.toBean(map, ResourceDTO.class);
    }
}
