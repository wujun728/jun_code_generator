package com.jeasy.userrole.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.userrole.dto.*;
import com.jeasy.userrole.entity.UserRoleEntity;
import com.jeasy.userrole.manager.UserRoleManager;
import com.jeasy.userrole.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleManager, UserRoleEntity, UserRoleDTO> implements UserRoleService {

    @Override
    public List<UserRoleListResDTO> list(final UserRoleListReqDTO userroleListReqDTO) {
        return manager.list(userroleListReqDTO);
    }

    @Override
    public List<UserRoleListResDTO> listByVersion1(final UserRoleListReqDTO userroleListReqDTO) {
        return manager.listByVersion1(userroleListReqDTO);
    }

    @Override
    public List<UserRoleListResDTO> listByVersion2(final UserRoleListReqDTO userroleListReqDTO) {
        return manager.listByVersion2(userroleListReqDTO);
    }

    @Override
    public List<UserRoleListResDTO> listByVersion3(final UserRoleListReqDTO userroleListReqDTO) {
        return manager.listByVersion3(userroleListReqDTO);
    }

    @Override
    public UserRoleListResDTO listOne(final UserRoleListReqDTO userroleListReqDTO) {
        return manager.listOne(userroleListReqDTO);
    }

    @Override
    public PageDTO<UserRolePageResDTO> pagination(final UserRolePageReqDTO userrolePageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(userrolePageReqDTO, current, size);
    }

    @Override
    public Boolean add(final UserRoleAddReqDTO userroleAddReqDTO) {
        return manager.add(userroleAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final UserRoleAddReqDTO userroleAddReqDTO) {
        return manager.addAllColumn(userroleAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<UserRoleAddReqDTO> userroleAddReqDTOList) {
        return manager.addBatchAllColumn(userroleAddReqDTOList);
    }

    @Override
    public UserRoleShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<UserRoleShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final UserRoleModifyReqDTO userroleModifyReqDTO) {
        return manager.modify(userroleModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final UserRoleModifyReqDTO userroleModifyReqDTO) {
        return manager.modifyAllColumn(userroleModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final UserRoleRemoveReqDTO userroleRemoveReqDTO) {
        return manager.removeByParams(userroleRemoveReqDTO);
    }
}
