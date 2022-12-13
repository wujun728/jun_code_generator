package com.jeasy.role.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.role.dto.*;
import com.jeasy.role.entity.RoleEntity;
import com.jeasy.role.manager.RoleManager;
import com.jeasy.role.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleManager, RoleEntity, RoleDTO> implements RoleService {

    @Override
    public List<RoleListResDTO> list(final RoleListReqDTO roleListReqDTO) {
        return manager.list(roleListReqDTO);
    }

    @Override
    public List<RoleListResDTO> listByVersion1(final RoleListReqDTO roleListReqDTO) {
        return manager.listByVersion1(roleListReqDTO);
    }

    @Override
    public List<RoleListResDTO> listByVersion2(final RoleListReqDTO roleListReqDTO) {
        return manager.listByVersion2(roleListReqDTO);
    }

    @Override
    public List<RoleListResDTO> listByVersion3(final RoleListReqDTO roleListReqDTO) {
        return manager.listByVersion3(roleListReqDTO);
    }

    @Override
    public RoleListResDTO listOne(final RoleListReqDTO roleListReqDTO) {
        return manager.listOne(roleListReqDTO);
    }

    @Override
    public PageDTO<RolePageResDTO> pagination(final RolePageReqDTO rolePageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(rolePageReqDTO, current, size);
    }

    @Override
    public Boolean add(final RoleAddReqDTO roleAddReqDTO) {
        return manager.add(roleAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final RoleAddReqDTO roleAddReqDTO) {
        return manager.addAllColumn(roleAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<RoleAddReqDTO> roleAddReqDTOList) {
        return manager.addBatchAllColumn(roleAddReqDTOList);
    }

    @Override
    public RoleShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<RoleShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final RoleModifyReqDTO roleModifyReqDTO) {
        return manager.modify(roleModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final RoleModifyReqDTO roleModifyReqDTO) {
        return manager.modifyAllColumn(roleModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final RoleRemoveReqDTO roleRemoveReqDTO) {
        return manager.removeByParams(roleRemoveReqDTO);
    }

    @Override
    public List<RoleListPermissionResDTO> listPermission(RoleListPermissionReqDTO roleListPermissionReqDTO) {
        return manager.listPermission(roleListPermissionReqDTO);
    }

    @Override
    public Boolean modifyPermission(RoleModifyPermissionReqDTO roleModifyPermissionReqDTO) {
        return manager.modifyPermission(roleModifyPermissionReqDTO);
    }


}
