package com.jeasy.roleresource.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.roleresource.dto.*;

import java.util.List;

/**
 * 角色资源 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
public interface RoleResourceService extends BaseService<RoleResourceDTO> {

    /**
     * 列表
     *
     * @param roleresourceListReqDTO 入参DTO
     * @return
     */
    List<RoleResourceListResDTO> list(RoleResourceListReqDTO roleresourceListReqDTO);

    /**
     * 列表Version1
     *
     * @param roleresourceListReqDTO 入参DTO
     * @return
     */
    List<RoleResourceListResDTO> listByVersion1(RoleResourceListReqDTO roleresourceListReqDTO);

    /**
     * 列表Version2
     *
     * @param roleresourceListReqDTO 入参DTO
     * @return
     */
    List<RoleResourceListResDTO> listByVersion2(RoleResourceListReqDTO roleresourceListReqDTO);

    /**
     * 列表Version3
     *
     * @param roleresourceListReqDTO 入参DTO
     * @return
     */
    List<RoleResourceListResDTO> listByVersion3(RoleResourceListReqDTO roleresourceListReqDTO);

    /**
     * First查询
     *
     * @param roleresourceListReqDTO 入参DTO
     * @return
     */
    RoleResourceListResDTO listOne(RoleResourceListReqDTO roleresourceListReqDTO);

    /**
     * 分页
     *
     * @param roleresourcePageReqDTO 入参DTO
     * @param current                当前页
     * @param size                   每页大小
     * @return
     */
    PageDTO<RoleResourcePageResDTO> pagination(RoleResourcePageReqDTO roleresourcePageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param roleresourceAddReqDTO 入参DTO
     * @return
     */
    Boolean add(RoleResourceAddReqDTO roleresourceAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param roleresourceAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(RoleResourceAddReqDTO roleresourceAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param roleresourceAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<RoleResourceAddReqDTO> roleresourceAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    RoleResourceShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<RoleResourceShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param roleresourceModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(RoleResourceModifyReqDTO roleresourceModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param roleresourceModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(RoleResourceModifyReqDTO roleresourceModifyReqDTO);

    /**
     * 参数删除
     *
     * @param roleresourceRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(RoleResourceRemoveReqDTO roleresourceRemoveReqDTO);

    /**
     * findByRoleIds
     *
     * @param roleIdList
     * @return
     */
    List<RoleResourceDTO> findByRoleIds(List<Long> roleIdList);
}
