package com.jeasy.role.manager;

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
import com.jeasy.resource.dto.ResourceDTO;
import com.jeasy.resource.manager.ResourceManager;
import com.jeasy.role.biz.RoleBiz;
import com.jeasy.role.dao.RoleDAO;
import com.jeasy.role.dto.*;
import com.jeasy.role.entity.RoleEntity;
import com.jeasy.roleresource.dto.RoleResourceDTO;
import com.jeasy.roleresource.manager.RoleResourceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 角色 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class RoleManager extends BaseManagerImpl<RoleDAO, RoleEntity, RoleDTO> {

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

    public static RoleManager me() {
        return SpringContextHolder.getBean(RoleManager.class);
    }

    public List<RoleListResDTO> list(final RoleListReqDTO roleListReqDTO) {
        RoleDTO roleParamsDTO = new RoleDTO();
        if (!Func.isEmpty(roleListReqDTO)) {
            BeanKit.copyProperties(roleListReqDTO, roleParamsDTO, DEMO_CONVERTER);
        }

        List<RoleDTO> roleDTOList = super.findList(roleParamsDTO);

        if (!Func.isEmpty(roleDTOList)) {
            List<RoleListResDTO> items = Lists.newArrayList();
            for (RoleDTO roleDTO : roleDTOList) {
                RoleListResDTO roleListResDTO = new RoleListResDTO();
                BeanKit.copyProperties(roleDTO, roleListResDTO, DEMO_CONVERTER);
                items.add(roleListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<RoleListResDTO> listByVersion1(final RoleListReqDTO roleListReqDTO) {
        return list(roleListReqDTO);
    }

    public List<RoleListResDTO> listByVersion2(final RoleListReqDTO roleListReqDTO) {
        return list(roleListReqDTO);
    }

    public List<RoleListResDTO> listByVersion3(final RoleListReqDTO roleListReqDTO) {
        return list(roleListReqDTO);
    }

    public RoleListResDTO listOne(final RoleListReqDTO roleListReqDTO) {
        RoleDTO roleParamsDTO = new RoleDTO();
        if (!Func.isEmpty(roleListReqDTO)) {
            BeanKit.copyProperties(roleListReqDTO, roleParamsDTO, DEMO_CONVERTER);
        }

        RoleDTO roleDTO = super.findOne(roleParamsDTO);
        if (!Func.isEmpty(roleDTO)) {
            RoleListResDTO roleListResDTO = new RoleListResDTO();
            BeanKit.copyProperties(roleDTO, roleListResDTO, DEMO_CONVERTER);
            return roleListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<RolePageResDTO> pagination(final RolePageReqDTO rolePageReqDTO, final Integer current, final Integer size) {
        QueryWrapper<RoleEntity> queryWrapper = RoleBiz.me().transferRoleQueryWrapper(rolePageReqDTO);
        PageDTO<RoleDTO> roleDTOPage = super.findPage(queryWrapper, current, size);

        if (Func.isNotEmpty(roleDTOPage) && Func.isNotEmpty(roleDTOPage.getRecords())) {
            List<RolePageResDTO> rolePageResDTOs = Lists.newArrayList();
            for (RoleDTO roleDTO : roleDTOPage.getRecords()) {
                rolePageResDTOs.add(RoleBiz.me().transferRolePageResDTO(roleDTO));
            }

            PageDTO<RolePageResDTO> rolePageResDTOPage = new PageDTO<>();
            rolePageResDTOPage.setRecords(rolePageResDTOs);
            rolePageResDTOPage.setTotal(roleDTOPage.getTotal());
            return rolePageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean add(final RoleAddReqDTO roleAddReqDTO) {
        if (Func.isEmpty(roleAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        QueryWrapper<RoleEntity> queryWrapper = RoleBiz.me().transferRoleQueryWrapper(roleAddReqDTO);
        Integer count = RoleManager.me().findCount(queryWrapper);
        if (!Func.isNullOrZero(count)) {
            throw new MessageException(ModelResult.CODE_200, "名称或编码已存在");
        }

        return super.saveDTO(RoleBiz.me().transferRoleDTO(roleAddReqDTO));
    }

    public Boolean addAllColumn(final RoleAddReqDTO roleAddReqDTO) {
        if (Func.isEmpty(roleAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        RoleDTO roleDTO = new RoleDTO();
        BeanKit.copyProperties(roleAddReqDTO, roleDTO, DEMO_CONVERTER);
        return super.saveAllColumn(roleDTO);
    }

    public Boolean addBatchAllColumn(final List<RoleAddReqDTO> roleAddReqDTOList) {
        if (Func.isEmpty(roleAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        List<RoleDTO> roleDTOList = Lists.newArrayList();
        for (RoleAddReqDTO roleAddReqDTO : roleAddReqDTOList) {
            RoleDTO roleDTO = new RoleDTO();
            BeanKit.copyProperties(roleAddReqDTO, roleDTO, DEMO_CONVERTER);
            roleDTOList.add(roleDTO);
        }
        return super.saveBatchAllColumn(roleDTOList);
    }

    public RoleShowResDTO show(final Long id) {
        RoleDTO roleDTO = super.findById(id);

        if (!Func.isEmpty(roleDTO)) {
            RoleShowResDTO roleShowResDTO = new RoleShowResDTO();
            BeanKit.copyProperties(roleDTO, roleShowResDTO, DEMO_CONVERTER);
            return roleShowResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<RoleShowResDTO> showByIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new MessageException(ModelResult.CODE_200, "集合不能为空且大小大于0");
        }

        List<RoleDTO> roleDTOList = super.findBatchIds(ids);

        if (!Func.isEmpty(roleDTOList)) {
            List<RoleShowResDTO> roleShowResDTOList = Lists.newArrayList();
            for (RoleDTO roleDTO : roleDTOList) {
                RoleShowResDTO roleShowResDTO = new RoleShowResDTO();
                BeanKit.copyProperties(roleDTO, roleShowResDTO, DEMO_CONVERTER);
                roleShowResDTOList.add(roleShowResDTO);
            }
            return roleShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean modify(final RoleModifyReqDTO roleModifyReqDTO) {
        if (Func.isEmpty(roleModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        QueryWrapper<RoleEntity> queryWrapper = RoleBiz.me().transferRoleQueryWrapper(roleModifyReqDTO);
        Integer count = RoleManager.me().findCount(queryWrapper);
        if (!Func.isNullOrZero(count)) {
            throw new MessageException(ModelResult.CODE_200, "名称或编码已存在");
        }

        return super.modifyById(RoleBiz.me().transferRoleDTO(roleModifyReqDTO));
    }

    public Boolean modifyAllColumn(final RoleModifyReqDTO roleModifyReqDTO) {
        if (Func.isEmpty(roleModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        RoleDTO roleDTO = new RoleDTO();
        BeanKit.copyProperties(roleModifyReqDTO, roleDTO, DEMO_CONVERTER);
        return super.modifyAllColumnById(roleDTO);
    }

    public Boolean removeByParams(final RoleRemoveReqDTO roleRemoveReqDTO) {
        if (Func.isEmpty(roleRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        RoleDTO roleParamsDTO = new RoleDTO();
        BeanKit.copyProperties(roleRemoveReqDTO, roleParamsDTO, DEMO_CONVERTER);
        return super.remove(roleParamsDTO);
    }

    public List<RoleListPermissionResDTO> listPermission(RoleListPermissionReqDTO roleListPermissionReqDTO) {
        RoleResourceDTO roleResourceDTO = RoleBiz.me().transferRoleResourceDTO(roleListPermissionReqDTO);
        List<RoleResourceDTO> roleResourceDTOList = RoleResourceManager.me().findList(roleResourceDTO);
        List<ResourceDTO> resourceDTOList = ResourceManager.me().findList(new ResourceDTO());
        return RoleBiz.me().transferRoleListPermissionResDTO(roleResourceDTOList, resourceDTOList);
    }

    public Boolean modifyPermission(RoleModifyPermissionReqDTO roleModifyPermissionReqDTO) {
        RoleDTO roleDTO = RoleManager.me().findById(roleModifyPermissionReqDTO.getRoleId());

        if (Func.isEmpty(roleDTO)) {
            throw new MessageException(ModelResult.CODE_200, "角色ID参数错误");
        }

        RoleResourceDTO roleResourceDTO = RoleBiz.me().transferRoleResourceDTO(roleModifyPermissionReqDTO);
        RoleResourceManager.me().remove(roleResourceDTO);

        if (Func.isNotEmpty(roleModifyPermissionReqDTO.getPermissionIds())) {
            List<ResourceDTO> resourceDTOList = ResourceManager.me().findBatchIds(roleModifyPermissionReqDTO.getPermissionIds());
            List<RoleResourceDTO> roleResourceDTOList = RoleBiz.me().buildRoleResourceDTOList(roleDTO, resourceDTOList);
            return RoleResourceManager.me().saveBatchAllColumn(roleResourceDTOList);
        }
        return true;
    }

    @Override
    protected List<RoleDTO> entityToDTOList(final List<RoleEntity> roleEntityList) {
        List<RoleDTO> roleDTOList = null;
        if (!Func.isEmpty(roleEntityList)) {
            roleDTOList = Lists.newArrayList();
            for (RoleEntity roleEntity : roleEntityList) {
                roleDTOList.add(entityToDTO(roleEntity));
            }
        }
        return roleDTOList;
    }

    @Override
    protected RoleDTO entityToDTO(final RoleEntity roleEntity) {
        RoleDTO roleDTO = null;
        if (!Func.isEmpty(roleEntity)) {
            roleDTO = new RoleDTO();
            BeanKit.copyProperties(roleEntity, roleDTO);
        }
        return roleDTO;
    }

    @Override
    protected List<RoleEntity> dtoToEntityList(final List<RoleDTO> roleDTOList) {
        List<RoleEntity> roleEntityList = null;
        if (!Func.isEmpty(roleDTOList)) {
            roleEntityList = Lists.newArrayList();
            for (RoleDTO roleDTO : roleDTOList) {
                roleEntityList.add(dtoToEntity(roleDTO));
            }
        }
        return roleEntityList;
    }

    @Override
    protected RoleEntity dtoToEntity(final RoleDTO roleDTO) {
        RoleEntity roleEntity = null;
        if (!Func.isEmpty(roleDTO)) {
            roleEntity = new RoleEntity();
            BeanKit.copyProperties(roleDTO, roleEntity);
        }
        return roleEntity;
    }

    @Override
    protected RoleEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new RoleEntity();
        }
        return (RoleEntity) MapKit.toBean(map, RoleEntity.class);
    }

    @Override
    protected RoleDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new RoleDTO();
        }
        return (RoleDTO) MapKit.toBean(map, RoleDTO.class);
    }
}
