package com.jeasy.resource.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeasy.common.Func;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.resource.dto.*;
import com.jeasy.resource.entity.ResourceEntity;
import com.jeasy.roleresource.dto.RoleResourceDTO;
import com.jeasy.userrole.dto.UserRoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 菜单 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class ResourceBiz {

    public static ResourceBiz me() {
        return SpringContextHolder.getBean(ResourceBiz.class);
    }

    public List<UserMenuResourceDTO> transferUserMenuResourceDTOList(List<ResourceDTO> resourceDTOList) {
        List<UserMenuResourceDTO> userMenuResourceDTOList = Lists.newArrayList();
        Map<Long, UserMenuResourceDTO> userMenuResourceDTOMap = Maps.newHashMap();
        if (Func.isNotEmpty(resourceDTOList)) {
            for (ResourceDTO resourceDTO : resourceDTOList) {
                UserMenuResourceDTO userMenuResourceDTO = new UserMenuResourceDTO();
                BeanKit.copyProperties(resourceDTO, userMenuResourceDTO);
                userMenuResourceDTOMap.put(userMenuResourceDTO.getId(), userMenuResourceDTO);
            }

            for (ResourceDTO resourceDTO : resourceDTOList) {
                UserMenuResourceDTO userMenuResourceDTO = userMenuResourceDTOMap.get(resourceDTO.getId());
                if (Func.isNullOrZero(resourceDTO.getPid())) {
                    userMenuResourceDTOList.add(userMenuResourceDTO);
                } else {
                    UserMenuResourceDTO parentUserMenuResourceDTO = userMenuResourceDTOMap.get(resourceDTO.getPid());
                    List<UserMenuResourceDTO> childrens = parentUserMenuResourceDTO.getChildrens();
                    if (childrens == null) {
                        childrens = Lists.newArrayList();
                        parentUserMenuResourceDTO.setChildrens(childrens);
                    }
                    childrens.add(userMenuResourceDTO);
                }
            }
        }
        return userMenuResourceDTOList;
    }

    public List<Long> transferUserRoleIdList(List<UserRoleDTO> userRoleDTOs) {
        List<Long> roleIdList = Lists.newArrayList();
        if (Func.isNotEmpty(userRoleDTOs)) {
            for (UserRoleDTO userRoleDTO : userRoleDTOs) {
                roleIdList.add(userRoleDTO.getRoleId());
            }
        }
        return roleIdList;
    }

    public UserRoleDTO transferUserRoleDTO(Long userId) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(userId);
        return userRoleDTO;
    }

    public List<Long> transferUserResourceIdList(List<RoleResourceDTO> roleResourceDTOList) {
        List<Long> resourceIdList = Lists.newArrayList();
        if (Func.isNotEmpty(roleResourceDTOList)) {
            for (RoleResourceDTO roleResourceDTO : roleResourceDTOList) {
                resourceIdList.add(roleResourceDTO.getResourceId());
            }
        }
        return resourceIdList;
    }

    public ResourceDTO transferResourceParamDTO(String menuPath) {
        ResourceDTO resourceParamDTO = new ResourceDTO();
        resourceParamDTO.setUrl(menuPath);
        return resourceParamDTO;
    }

    public List<ResourceListResDTO> transferResourceListResDTO(List<ResourceDTO> resourceDTOList) {
        Map<Long, ResourceListResDTO> resourceListResDTOMap = Maps.newHashMap();
        if (Func.isNotEmpty(resourceDTOList)) {
            for (ResourceDTO resourceDTO : resourceDTOList) {
                ResourceListResDTO resourceListResDTO = new ResourceListResDTO();
                resourceListResDTO.setId(resourceDTO.getId());
                resourceListResDTO.setTitle(resourceDTO.getName());
                resourceListResDTO.setPid(resourceDTO.getPid());
                resourceListResDTOMap.put(resourceDTO.getId(), resourceListResDTO);
            }
        }

        List<ResourceListResDTO> resourceListResDTOList = Lists.newArrayList();
        for (ResourceDTO resourceDTO : resourceDTOList) {
            ResourceListResDTO resourceListResDTO = resourceListResDTOMap.get(resourceDTO.getId());
            if (Func.isNullOrZero(resourceDTO.getPid())) {
                resourceListResDTOList.add(resourceListResDTO);
            } else {
                ResourceListResDTO parentResourceListResDTO = resourceListResDTOMap.get(resourceDTO.getPid());
                List<ResourceListResDTO> children = parentResourceListResDTO.getChildren();
                if (children == null) {
                    children = Lists.newArrayList();
                    parentResourceListResDTO.setChildren(children);
                }
                children.add(resourceListResDTO);
            }
        }
        return resourceListResDTOList;
    }

    public ResourceShowResDTO transferResourceShowResDTO(ResourceDTO resourceDTO, ResourceDTO parentResourceDTO) {
        ResourceShowResDTO resourceShowResDTO = new ResourceShowResDTO();
        BeanKit.copyProperties(resourceDTO, resourceShowResDTO);
        if (Func.isNotEmpty(parentResourceDTO)) {
            resourceShowResDTO.setPname(parentResourceDTO.getName());
        }
        return resourceShowResDTO;
    }

    public QueryWrapper<ResourceEntity> transferResourceQueryWrapper(ResourceAddReqDTO resourceAddReqDTO) {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper<>();

        if (Func.isNotEmpty(resourceAddReqDTO.getCode())) {
            queryWrapper.eq(ResourceEntity.DB_COL_CODE, resourceAddReqDTO.getCode());
        }

        return queryWrapper;
    }

    public QueryWrapper<ResourceEntity> transferResourceQueryWrapper(ResourceModifyReqDTO resourceModifyReqDTO) {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper<>();

        if (Func.isNotEmpty(resourceModifyReqDTO.getCode())) {
            queryWrapper.eq(ResourceEntity.DB_COL_CODE, resourceModifyReqDTO.getCode());
        }

        queryWrapper.ne(ResourceEntity.DB_COL_ID, resourceModifyReqDTO.getId());
        return queryWrapper;
    }

    public QueryWrapper<ResourceEntity> buildParentQueryWrapper(Long pid) {
        QueryWrapper<ResourceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ResourceEntity.DB_COL_PID, pid);
        return queryWrapper;
    }
}
