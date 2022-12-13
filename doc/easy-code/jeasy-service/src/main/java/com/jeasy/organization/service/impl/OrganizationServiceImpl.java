package com.jeasy.organization.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.organization.dto.*;
import com.jeasy.organization.entity.OrganizationEntity;
import com.jeasy.organization.manager.OrganizationManager;
import com.jeasy.organization.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Service
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationManager, OrganizationEntity, OrganizationDTO> implements OrganizationService {

    @Override
    public List<OrganizationListResDTO> list(final OrganizationListReqDTO organizationListReqDTO) {
        return manager.list(organizationListReqDTO);
    }

    @Override
    public OrganizationListResDTO listOne(final OrganizationListReqDTO organizationListReqDTO) {
        return manager.listOne(organizationListReqDTO);
    }

    @Override
    public PageDTO<OrganizationPageResDTO> pagination(final OrganizationPageReqDTO organizationPageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(organizationPageReqDTO, current, size);
    }

    @Override
    public Boolean add(final OrganizationAddReqDTO organizationAddReqDTO) {
        return manager.add(organizationAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final OrganizationAddReqDTO organizationAddReqDTO) {
        return manager.addAllColumn(organizationAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<OrganizationAddReqDTO> organizationAddReqDTOList) {
        return manager.addBatchAllColumn(organizationAddReqDTOList);
    }

    @Override
    public OrganizationShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<OrganizationShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final OrganizationModifyReqDTO organizationModifyReqDTO) {
        return manager.modify(organizationModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final OrganizationModifyReqDTO organizationModifyReqDTO) {
        return manager.modifyAllColumn(organizationModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final OrganizationRemoveReqDTO organizationRemoveReqDTO) {
        return manager.removeByParams(organizationRemoveReqDTO);
    }
}
