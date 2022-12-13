package com.jeasy.user.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jeasy.common.Func;
import com.jeasy.common.date.DateKit;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.security.PasswordHash;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.common.str.StrKit;
import com.jeasy.dictionary.DictionaryKit;
import com.jeasy.organization.dto.OrganizationDTO;
import com.jeasy.role.dto.RoleDTO;
import com.jeasy.user.dto.*;
import com.jeasy.user.entity.UserEntity;
import com.jeasy.userorg.dto.UserOrgDTO;
import com.jeasy.userrole.dto.UserRoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class UserBiz {

    public static UserBiz me() {
        return SpringContextHolder.getBean(UserBiz.class);
    }

    public UserPageResDTO transferUserPageResDTO(UserDTO userDTO) {
        UserPageResDTO userPageResDTO = new UserPageResDTO();
        BeanKit.copyProperties(userDTO, userPageResDTO);

        if (Func.isNotEmpty(userDTO.getStatusVal()) && Func.isNotEmpty(userDTO.getStatusCode())) {
            userPageResDTO.setStatusName(DictionaryKit.YHZT_MAP().get(userDTO.getStatusCode()).getName());
        }

        if (Func.isNotEmpty(userDTO.getUpdateAt())) {
            userPageResDTO.setUpdateAt(DateKit.formatDateTime(DateKit.getDate(userDTO.getUpdateAt())));
        }
        return userPageResDTO;
    }

    public QueryWrapper<UserEntity> transferUserQueryWrapper(UserPageReqDTO userPageReqDTO) {
        UserEntity userEntity = new UserEntity();
        if (!Func.isEmpty(userPageReqDTO)) {
            BeanKit.copyProperties(userPageReqDTO, userEntity);
        }

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>(userEntity);
        if (Func.isNotEmpty(userPageReqDTO.getUpdateStartAt())) {
            queryWrapper.ge(UserEntity.DB_COL_UPDATE_AT, DateKit.getMillis(DateKit.parseDateTime(userPageReqDTO.getUpdateStartAt())));
        }

        if (Func.isNotEmpty(userPageReqDTO.getUpdateEndAt())) {
            queryWrapper.le(UserEntity.DB_COL_UPDATE_AT, DateKit.getMillis(DateKit.parseDateTime(userPageReqDTO.getUpdateEndAt())));
        }

        queryWrapper.orderByDesc(UserEntity.DB_COL_ID);
        return queryWrapper;
    }

    public UserDTO transferUserDTO(UserModifyReqDTO userModifyReqDTO) {
        UserDTO userDTO = new UserDTO();
        BeanKit.copyProperties(userModifyReqDTO, userDTO);

        if (Func.isNotEmpty(userDTO.getPwd())) {
            userDTO.setPwd(PasswordHash.me().toHex(userDTO.getPwd(), Func.isEmpty(userDTO.getSalt()) ? StrKit.S_EMPTY : userDTO.getSalt()));
        }

        userDTO.setStatusCode(DictionaryKit.YHZT_MAP().get(userModifyReqDTO.getStatusCode()).getCode());
        userDTO.setStatusVal(DictionaryKit.YHZT_MAP().get(userModifyReqDTO.getStatusCode()).getValue());
        return userDTO;
    }

    public UserDTO transferUserDTO(UserAddReqDTO userAddReqDTO) {
        UserDTO userDTO = new UserDTO();
        BeanKit.copyProperties(userAddReqDTO, userDTO);

        userDTO.setPwd(PasswordHash.me().toHex(userDTO.getPwd(), Func.isEmpty(userDTO.getSalt()) ? StrKit.S_EMPTY : userDTO.getSalt()));
        userDTO.setStatusCode(DictionaryKit.YHZT_MAP().get(userAddReqDTO.getStatusCode()).getCode());
        userDTO.setStatusVal(DictionaryKit.YHZT_MAP().get(userAddReqDTO.getStatusCode()).getValue());
        return userDTO;
    }

    public UserShowResDTO transferUserShowResDTO(UserDTO userDTO, List<RoleDTO> roleDTOList, List<OrganizationDTO> organizationDTOList) {
        UserShowResDTO userShowResDTO = new UserShowResDTO();
        BeanKit.copyProperties(userDTO, userShowResDTO);

        if (Func.isNotEmpty(userDTO.getStatusVal()) && Func.isNotEmpty(userDTO.getStatusCode())) {
            userShowResDTO.setStatusName(DictionaryKit.YHZT_MAP().get(userDTO.getStatusCode()).getName());
        }

        if (Func.isNotEmpty(roleDTOList)) {
            StringBuilder roleNames = new StringBuilder();
            for (RoleDTO roleDTO : roleDTOList) {
                roleNames.append(roleDTO.getName()).append(StrKit.S_SEMICOLON);
            }
            userShowResDTO.setRoleNames(roleNames.toString());
        }

        if (Func.isNotEmpty(organizationDTOList)) {
            StringBuilder orgNames = new StringBuilder();
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                orgNames.append(organizationDTO.getName()).append(StrKit.S_SEMICOLON);
            }
            userShowResDTO.setOrgNames(orgNames.toString());
        }
        return userShowResDTO;
    }

    public UserRoleDTO transferUserRoleDTO(UserPageRoleReqDTO userPageRoleReqDTO) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        BeanKit.copyProperties(userPageRoleReqDTO, userRoleDTO);
        return userRoleDTO;
    }

    public UserRoleDTO transferUserRoleDTO(UserListRoleReqDTO userListRoleReqDTO) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        BeanKit.copyProperties(userListRoleReqDTO, userRoleDTO);
        return userRoleDTO;
    }

    public UserRoleDTO transferUserRoleDTO(UserModifyRoleReqDTO userModifyRoleReqDTO) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserId(userModifyRoleReqDTO.getUserId());
        return userRoleDTO;
    }

    public Set<Long> buildUserRoleIdSet(List<UserRoleDTO> userRoleDTOList) {
        Set<Long> userRoleIdSet = Sets.newHashSet();
        if (Func.isNotEmpty(userRoleDTOList)) {
            for (UserRoleDTO userRoleDTO : userRoleDTOList) {
                userRoleIdSet.add(userRoleDTO.getRoleId());
            }
        }
        return userRoleIdSet;
    }

    public UserPageRoleResDTO transferUserPageRoleResDTO(RoleDTO roleDTO, Set<Long> userRoleIdSet) {
        UserPageRoleResDTO userPageRoleResDTO = new UserPageRoleResDTO();
        BeanKit.copyProperties(roleDTO, userPageRoleResDTO);

        if (Func.isNotEmpty(roleDTO.getUpdateAt())) {
            userPageRoleResDTO.setUpdateAt(DateKit.formatDateTime(DateKit.getDate(roleDTO.getUpdateAt())));
        }

        if (userRoleIdSet.contains(roleDTO.getId())) {
            userPageRoleResDTO.setChecked(true);
        } else {
            userPageRoleResDTO.setChecked(false);
        }
        return userPageRoleResDTO;
    }

    public List<UserRoleDTO> buildUserRoleDTOList(UserDTO userDTO, List<RoleDTO> roleDTOList) {
        List<UserRoleDTO> userRoleDTOList = Lists.newArrayList();
        for (RoleDTO roleDTO : roleDTOList) {
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setUserCode(userDTO.getCode());
            userRoleDTO.setUserName(userDTO.getName());
            userRoleDTO.setUserId(userDTO.getId());
            userRoleDTO.setRoleName(roleDTO.getName());
            userRoleDTO.setRoleCode(roleDTO.getCode());
            userRoleDTO.setRoleId(roleDTO.getId());
            userRoleDTOList.add(userRoleDTO);
        }
        return userRoleDTOList;
    }

    public List<UserListRoleResDTO> transferUserListRoleResDTO(List<UserRoleDTO> userRoleDTOList) {
        List<UserListRoleResDTO> userListRoleResDTOList = Lists.newArrayList();
        if (Func.isNotEmpty(userRoleDTOList)) {
            for (UserRoleDTO userRoleDTO : userRoleDTOList) {
                UserListRoleResDTO userListRoleResDTO = new UserListRoleResDTO();
                userListRoleResDTO.setRoleId(userRoleDTO.getRoleId());
                userListRoleResDTOList.add(userListRoleResDTO);
            }
        }
        return userListRoleResDTOList;
    }

    public UserOrgDTO transferUserOrgDTO(UserListOrganizationReqDTO userListOrganizationReqDTO) {
        UserOrgDTO userOrgDTO = new UserOrgDTO();
        BeanKit.copyProperties(userListOrganizationReqDTO, userOrgDTO);
        return userOrgDTO;
    }

    public UserOrgDTO transferUserOrgDTO(UserModifyOrganizationReqDTO userModifyOrganizationReqDTO) {
        UserOrgDTO userOrgDTO = new UserOrgDTO();
        BeanKit.copyProperties(userModifyOrganizationReqDTO, userOrgDTO);
        return userOrgDTO;
    }

    public List<UserListOrganizationResDTO> transferUserListOrganizationResDTO(List<UserOrgDTO> userOrgDTOList, List<OrganizationDTO> organizationDTOList) {
        Set<Long> userOrgIdSet = Sets.newHashSet();
        if (Func.isNotEmpty(userOrgDTOList)) {
            for (UserOrgDTO userOrgDTO : userOrgDTOList) {
                userOrgIdSet.add(userOrgDTO.getOrgId());
            }
        }

        Map<Long, UserListOrganizationResDTO> userListOrganizationResDTOMap = Maps.newHashMap();
        if (Func.isNotEmpty(organizationDTOList)) {
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                UserListOrganizationResDTO userListOrganizationResDTO = new UserListOrganizationResDTO();
                userListOrganizationResDTO.setId(organizationDTO.getId());
                userListOrganizationResDTO.setTitle(organizationDTO.getName());
                userListOrganizationResDTO.setPid(organizationDTO.getPid());
                userListOrganizationResDTO.setChecked(userOrgIdSet.contains(organizationDTO.getId()));
                userListOrganizationResDTOMap.put(organizationDTO.getId(), userListOrganizationResDTO);
            }
        }

        List<UserListOrganizationResDTO> userListOrganizationResDTOList = Lists.newArrayList();
        for (OrganizationDTO organizationDTO : organizationDTOList) {
            UserListOrganizationResDTO userListOrganizationResDTO = userListOrganizationResDTOMap.get(organizationDTO.getId());
            if (Func.isNullOrZero(organizationDTO.getPid())) {
                userListOrganizationResDTOList.add(userListOrganizationResDTO);
            } else {
                UserListOrganizationResDTO parentUserListOrganizationResDTO = userListOrganizationResDTOMap.get(organizationDTO.getPid());
                List<UserListOrganizationResDTO> children = parentUserListOrganizationResDTO.getChildren();
                if (children == null) {
                    children = Lists.newArrayList();
                    parentUserListOrganizationResDTO.setChildren(children);
                }
                children.add(userListOrganizationResDTO);
            }
        }
        return userListOrganizationResDTOList;
    }

    public List<UserOrgDTO> buildUserOrgDTOList(UserDTO userDTO, List<OrganizationDTO> organizationDTOList) {
        List<UserOrgDTO> userOrgDTOList = Lists.newArrayList();
        for (OrganizationDTO organizationDTO : organizationDTOList) {
            UserOrgDTO userOrgDTO = new UserOrgDTO();
            userOrgDTO.setUserCode(userDTO.getCode());
            userOrgDTO.setUserName(userDTO.getName());
            userOrgDTO.setUserId(userDTO.getId());
            userOrgDTO.setOrgName(organizationDTO.getName());
            userOrgDTO.setOrgCode(organizationDTO.getCode());
            userOrgDTO.setOrgId(organizationDTO.getId());
            userOrgDTOList.add(userOrgDTO);
        }
        return userOrgDTOList;
    }

    public QueryWrapper<UserEntity> transferUserQueryWrapper(UserAddReqDTO userAddReqDTO) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();

        if (Func.isNotEmpty(userAddReqDTO.getLoginName())) {
            queryWrapper.eq(UserEntity.DB_COL_LOGIN_NAME, userAddReqDTO.getLoginName());
        }
        return queryWrapper;
    }

    public QueryWrapper<UserEntity> transferUserQueryWrapper(UserModifyReqDTO userModifyReqDTO) {
        QueryWrapper<UserEntity> updateWrapper = new QueryWrapper<>();

        if (Func.isNotEmpty(userModifyReqDTO.getLoginName())) {
            updateWrapper.eq(UserEntity.DB_COL_LOGIN_NAME, userModifyReqDTO.getLoginName());
        }

        updateWrapper.ne(UserEntity.DB_COL_ID, userModifyReqDTO.getId());
        return updateWrapper;
    }
}
