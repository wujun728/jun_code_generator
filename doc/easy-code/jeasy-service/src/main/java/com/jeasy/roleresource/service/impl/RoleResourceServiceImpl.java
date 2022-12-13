package com.jeasy.roleresource.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.roleresource.dto.*;
import com.jeasy.roleresource.entity.RoleResourceEntity;
import com.jeasy.roleresource.manager.RoleResourceManager;
import com.jeasy.roleresource.service.RoleResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色资源 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Service
public class RoleResourceServiceImpl extends BaseServiceImpl<RoleResourceManager, RoleResourceEntity, RoleResourceDTO> implements RoleResourceService {

    @Override
    public List<RoleResourceListResDTO> list(final RoleResourceListReqDTO roleresourceListReqDTO) {
        return manager.list(roleresourceListReqDTO);
    }

    @Override
    public List<RoleResourceListResDTO> listByVersion1(final RoleResourceListReqDTO roleresourceListReqDTO) {
        return manager.listByVersion1(roleresourceListReqDTO);
    }

    @Override
    public List<RoleResourceListResDTO> listByVersion2(final RoleResourceListReqDTO roleresourceListReqDTO) {
        return manager.listByVersion2(roleresourceListReqDTO);
    }

    @Override
    public List<RoleResourceListResDTO> listByVersion3(final RoleResourceListReqDTO roleresourceListReqDTO) {
        return manager.listByVersion3(roleresourceListReqDTO);
    }

    @Override
    public RoleResourceListResDTO listOne(final RoleResourceListReqDTO roleresourceListReqDTO) {
        return manager.listOne(roleresourceListReqDTO);
    }

    @Override
    public PageDTO<RoleResourcePageResDTO> pagination(final RoleResourcePageReqDTO roleresourcePageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(roleresourcePageReqDTO, current, size);
    }

    @Override
    public Boolean add(final RoleResourceAddReqDTO roleresourceAddReqDTO) {
        return manager.add(roleresourceAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final RoleResourceAddReqDTO roleresourceAddReqDTO) {
        return manager.addAllColumn(roleresourceAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<RoleResourceAddReqDTO> roleresourceAddReqDTOList) {
        return manager.addBatchAllColumn(roleresourceAddReqDTOList);
    }

    @Override
    public RoleResourceShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<RoleResourceShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final RoleResourceModifyReqDTO roleresourceModifyReqDTO) {
        return manager.modify(roleresourceModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final RoleResourceModifyReqDTO roleresourceModifyReqDTO) {
        return manager.modifyAllColumn(roleresourceModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final RoleResourceRemoveReqDTO roleresourceRemoveReqDTO) {
        return manager.removeByParams(roleresourceRemoveReqDTO);
    }

    @Override
    public List<RoleResourceDTO> findByRoleIds(final List<Long> roleIdList) {
        return manager.findByRoleIds(roleIdList);
    }
}
