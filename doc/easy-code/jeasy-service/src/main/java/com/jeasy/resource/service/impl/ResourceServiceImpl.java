package com.jeasy.resource.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.resource.dto.*;
import com.jeasy.resource.entity.ResourceEntity;
import com.jeasy.resource.manager.ResourceManager;
import com.jeasy.resource.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceManager, ResourceEntity, ResourceDTO> implements ResourceService {

    @Override
    public List<ResourceListResDTO> list(final ResourceListReqDTO resourceListReqDTO) {
        return manager.list(resourceListReqDTO);
    }

    @Override
    public ResourceListResDTO listOne(final ResourceListReqDTO resourceListReqDTO) {
        return manager.listOne(resourceListReqDTO);
    }

    @Override
    public PageDTO<ResourcePageResDTO> pagination(final ResourcePageReqDTO resourcePageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(resourcePageReqDTO, current, size);
    }

    @Override
    public Boolean add(final ResourceAddReqDTO resourceAddReqDTO) {
        return manager.add(resourceAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final ResourceAddReqDTO resourceAddReqDTO) {
        return manager.addAllColumn(resourceAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<ResourceAddReqDTO> resourceAddReqDTOList) {
        return manager.addBatchAllColumn(resourceAddReqDTOList);
    }

    @Override
    public ResourceShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<ResourceShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final ResourceModifyReqDTO resourceModifyReqDTO) {
        return manager.modify(resourceModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final ResourceModifyReqDTO resourceModifyReqDTO) {
        return manager.modifyAllColumn(resourceModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final ResourceRemoveReqDTO resourceRemoveReqDTO) {
        return manager.removeByParams(resourceRemoveReqDTO);
    }

    @Override
    public List<UserMenuResourceDTO> listUserMenu() {
        return manager.listUserMenu();
    }

    @Override
    public List<UserMenuOperationDTO> listUserMenuOperation(final String menuPath) {
        return manager.listUserMenuOperation(menuPath);
    }
}
