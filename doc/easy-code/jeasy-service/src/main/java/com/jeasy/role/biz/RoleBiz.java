package com.jeasy.role.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jeasy.common.Func;
import com.jeasy.common.date.DateKit;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.resource.dto.ResourceDTO;
import com.jeasy.role.dto.*;
import com.jeasy.role.entity.RoleEntity;
import com.jeasy.roleresource.dto.RoleResourceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class RoleBiz {

    public static RoleBiz me() {
        return SpringContextHolder.getBean(RoleBiz.class);
    }

    public RolePageResDTO transferRolePageResDTO(RoleDTO roleDTO) {
        RolePageResDTO rolePageResDTO = new RolePageResDTO();
        BeanKit.copyProperties(roleDTO, rolePageResDTO);
        if (Func.isNotEmpty(roleDTO.getUpdateAt())) {
            rolePageResDTO.setUpdateAt(DateKit.formatDateTime(DateKit.getDate(roleDTO.getUpdateAt())));
        }
        return rolePageResDTO;
    }

    public QueryWrapper<RoleEntity> transferRoleQueryWrapper(RoleAddReqDTO roleAddReqDTO) {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        if (Func.isNotEmpty(roleAddReqDTO.getName())) {
            queryWrapper.eq(RoleEntity.DB_COL_NAME, roleAddReqDTO.getName());
        }

        if (Func.isNotEmpty(roleAddReqDTO.getCode())) {
            queryWrapper.or().eq(RoleEntity.DB_COL_CODE, roleAddReqDTO.getCode());
        }

        return queryWrapper;
    }

    public QueryWrapper<RoleEntity> transferRoleQueryWrapper(RoleModifyReqDTO roleModifyReqDTO) {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.ne(RoleEntity.DB_COL_ID, roleModifyReqDTO.getId());

        if (Func.isNotEmpty(roleModifyReqDTO.getName()) && Func.isNotEmpty(roleModifyReqDTO.getCode())) {
            queryWrapper.and(i -> i.eq(RoleEntity.DB_COL_NAME, roleModifyReqDTO.getName()).or().eq(RoleEntity.DB_COL_CODE, roleModifyReqDTO.getCode()));
            return queryWrapper;
        }

        if (Func.isNotEmpty(roleModifyReqDTO.getName())) {
            queryWrapper.eq(RoleEntity.DB_COL_NAME, roleModifyReqDTO.getName());
        }

        if (Func.isNotEmpty(roleModifyReqDTO.getCode())) {
            queryWrapper.eq(RoleEntity.DB_COL_CODE, roleModifyReqDTO.getCode());
        }

        return queryWrapper;
    }

    public QueryWrapper<RoleEntity> transferRoleQueryWrapper(RolePageReqDTO rolePageReqDTO) {
        RoleEntity roleEntity = new RoleEntity();
        if (!Func.isEmpty(rolePageReqDTO)) {
            BeanKit.copyProperties(rolePageReqDTO, roleEntity);
        }

        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>(roleEntity);
        if (Func.isNotEmpty(rolePageReqDTO.getUpdateStartAt())) {
            queryWrapper.ge(RoleEntity.DB_COL_UPDATE_AT, DateKit.getMillis(DateKit.parseDateTime(rolePageReqDTO.getUpdateStartAt())));
        }

        if (Func.isNotEmpty(rolePageReqDTO.getUpdateEndAt())) {
            queryWrapper.le(RoleEntity.DB_COL_UPDATE_AT, DateKit.getMillis(DateKit.parseDateTime(rolePageReqDTO.getUpdateEndAt())));
        }

        queryWrapper.orderByDesc(RoleEntity.DB_COL_ID);
        return queryWrapper;
    }

    public RoleDTO transferRoleDTO(RoleAddReqDTO roleAddReqDTO) {
        RoleDTO roleDTO = new RoleDTO();
        BeanKit.copyProperties(roleAddReqDTO, roleDTO);
        return roleDTO;
    }

    public RoleDTO transferRoleDTO(RoleModifyReqDTO roleModifyReqDTO) {
        RoleDTO roleDTO = new RoleDTO();
        BeanKit.copyProperties(roleModifyReqDTO, roleDTO);
        return roleDTO;
    }

    public RoleResourceDTO transferRoleResourceDTO(RoleListPermissionReqDTO roleListPermissionReqDTO) {
        RoleResourceDTO roleResourceDTO = new RoleResourceDTO();
        BeanKit.copyProperties(roleListPermissionReqDTO, roleResourceDTO);
        return roleResourceDTO;
    }

    public List<RoleListPermissionResDTO> transferRoleListPermissionResDTO(List<RoleResourceDTO> roleResourceDTOList, List<ResourceDTO> resourceDTOList) {
        Set<Long> roleResourceIdSet = Sets.newHashSet();
        if (Func.isNotEmpty(roleResourceDTOList)) {
            for (RoleResourceDTO roleResourceDTO : roleResourceDTOList) {
                roleResourceIdSet.add(roleResourceDTO.getResourceId());
            }
        }

        Map<Long, RoleListPermissionResDTO> roleListPermissionResDTOMap = Maps.newHashMap();
        if (Func.isNotEmpty(resourceDTOList)) {
            for (ResourceDTO resourceDTO : resourceDTOList) {
                RoleListPermissionResDTO roleListPermissionResDTO = new RoleListPermissionResDTO();
                roleListPermissionResDTO.setId(resourceDTO.getId());
                roleListPermissionResDTO.setTitle(resourceDTO.getName());
                roleListPermissionResDTO.setPid(resourceDTO.getPid());
                roleListPermissionResDTO.setChecked(roleResourceIdSet.contains(resourceDTO.getId()));
                roleListPermissionResDTOMap.put(resourceDTO.getId(), roleListPermissionResDTO);
            }
        }

        List<RoleListPermissionResDTO> roleListPermissionResDTOList = Lists.newArrayList();
        for (ResourceDTO resourceDTO : resourceDTOList) {
            RoleListPermissionResDTO roleListPermissionResDTO = roleListPermissionResDTOMap.get(resourceDTO.getId());
            if (Func.isNullOrZero(resourceDTO.getPid())) {
                roleListPermissionResDTOList.add(roleListPermissionResDTO);
            } else {
                RoleListPermissionResDTO parentRoleListPermissionResDTO = roleListPermissionResDTOMap.get(resourceDTO.getPid());
                List<RoleListPermissionResDTO> children = parentRoleListPermissionResDTO.getChildren();
                if (children == null) {
                    children = Lists.newArrayList();
                    parentRoleListPermissionResDTO.setChildren(children);
                }
                children.add(roleListPermissionResDTO);
            }
        }
        return roleListPermissionResDTOList;
    }

    public RoleResourceDTO transferRoleResourceDTO(RoleModifyPermissionReqDTO roleModifyPermissionReqDTO) {
        RoleResourceDTO roleResourceDTO = new RoleResourceDTO();
        BeanKit.copyProperties(roleModifyPermissionReqDTO, roleResourceDTO);
        return roleResourceDTO;
    }

    public List<RoleResourceDTO> buildRoleResourceDTOList(RoleDTO roleDTO, List<ResourceDTO> resourceDTOList) {
        List<RoleResourceDTO> roleResourceDTOList = Lists.newArrayList();
        for (ResourceDTO resourceDTO : resourceDTOList) {
            RoleResourceDTO roleResourceDTO = new RoleResourceDTO();
            roleResourceDTO.setRoleCode(roleDTO.getCode());
            roleResourceDTO.setRoleName(roleDTO.getName());
            roleResourceDTO.setRoleId(roleDTO.getId());
            roleResourceDTO.setResourceName(resourceDTO.getName());
            roleResourceDTO.setResourceCode(resourceDTO.getCode());
            roleResourceDTO.setResourceId(resourceDTO.getId());
            roleResourceDTOList.add(roleResourceDTO);
        }
        return roleResourceDTOList;
    }
}
