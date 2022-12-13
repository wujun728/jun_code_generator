package com.jeasy.organization.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.organization.dto.*;

import java.util.List;

/**
 * 机构 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
public interface OrganizationService extends BaseService<OrganizationDTO> {

    /**
     * 列表
     *
     * @param organizationListReqDTO 入参DTO
     * @return
     */
    List<OrganizationListResDTO> list(OrganizationListReqDTO organizationListReqDTO);

    /**
     * First查询
     *
     * @param organizationListReqDTO 入参DTO
     * @return
     */
    OrganizationListResDTO listOne(OrganizationListReqDTO organizationListReqDTO);

    /**
     * 分页
     *
     * @param organizationPageReqDTO 入参DTO
     * @param current                当前页
     * @param size                   每页大小
     * @return
     */
    PageDTO<OrganizationPageResDTO> pagination(OrganizationPageReqDTO organizationPageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param organizationAddReqDTO 入参DTO
     * @return
     */
    Boolean add(OrganizationAddReqDTO organizationAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param organizationAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(OrganizationAddReqDTO organizationAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param organizationAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<OrganizationAddReqDTO> organizationAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    OrganizationShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<OrganizationShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param organizationModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(OrganizationModifyReqDTO organizationModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param organizationModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(OrganizationModifyReqDTO organizationModifyReqDTO);

    /**
     * 参数删除
     *
     * @param organizationRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(OrganizationRemoveReqDTO organizationRemoveReqDTO);
}
