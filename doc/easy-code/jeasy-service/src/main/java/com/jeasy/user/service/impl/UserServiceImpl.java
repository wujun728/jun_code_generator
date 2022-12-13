package com.jeasy.user.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.user.dto.*;
import com.jeasy.user.entity.UserEntity;
import com.jeasy.user.manager.UserManager;
import com.jeasy.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserManager, UserEntity, UserDTO> implements UserService {

    @Override
    public List<UserListResDTO> list(final UserListReqDTO userListReqDTO) {
        return manager.list(userListReqDTO);
    }

    @Override
    public List<UserListResDTO> listByVersion1(final UserListReqDTO userListReqDTO) {
        return manager.listByVersion1(userListReqDTO);
    }

    @Override
    public List<UserListResDTO> listByVersion2(final UserListReqDTO userListReqDTO) {
        return manager.listByVersion2(userListReqDTO);
    }

    @Override
    public List<UserListResDTO> listByVersion3(final UserListReqDTO userListReqDTO) {
        return manager.listByVersion3(userListReqDTO);
    }

    @Override
    public UserListResDTO listOne(final UserListReqDTO userListReqDTO) {
        return manager.listOne(userListReqDTO);
    }

    @Override
    public PageDTO<UserPageResDTO> pagination(final UserPageReqDTO userPageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(userPageReqDTO, current, size);
    }

    @Override
    public Boolean add(final UserAddReqDTO userAddReqDTO) {
        return manager.add(userAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final UserAddReqDTO userAddReqDTO) {
        return manager.addAllColumn(userAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<UserAddReqDTO> userAddReqDTOList) {
        return manager.addBatchAllColumn(userAddReqDTOList);
    }

    @Override
    public UserShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<UserShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final UserModifyReqDTO userModifyReqDTO) {
        return manager.modify(userModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final UserModifyReqDTO userModifyReqDTO) {
        return manager.modifyAllColumn(userModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final UserRemoveReqDTO userRemoveReqDTO) {
        return manager.removeByParams(userRemoveReqDTO);
    }

    @Override
    public PageDTO<UserPageRoleResDTO> pagination(final UserPageRoleReqDTO userPageRoleReqDTO, final Integer current, final Integer size) {
        return manager.pagination(userPageRoleReqDTO, current, size);
    }

    @Override
    public Boolean modifyRole(final UserModifyRoleReqDTO userModifyRoleReqDTO) {
        return manager.modifyRole(userModifyRoleReqDTO);
    }

    @Override
    public List<UserListRoleResDTO> listRole(final UserListRoleReqDTO userListRoleReqDTO) {
        return manager.listRole(userListRoleReqDTO);
    }

    @Override
    public List<UserListOrganizationResDTO> listOrganization(final UserListOrganizationReqDTO userListOrganizationReqDTO) {
        return manager.listOrganization(userListOrganizationReqDTO);
    }

    @Override
    public Boolean modifyOrganization(final UserModifyOrganizationReqDTO userModifyOrganizationReqDTO) {
        return manager.modifyOrganization(userModifyOrganizationReqDTO);
    }
}
