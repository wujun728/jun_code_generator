package com.jeasy.role.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.role.dto.*;

import java.util.List;

/**
 * 角色 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
public interface RoleService extends BaseService<RoleDTO> {

    /**
     * 列表
     *
     * @param roleListReqDTO 入参DTO
     * @return
     */
    List<RoleListResDTO> list(RoleListReqDTO roleListReqDTO);

    /**
     * 列表Version1
     *
     * @param roleListReqDTO 入参DTO
     * @return
     */
    List<RoleListResDTO> listByVersion1(RoleListReqDTO roleListReqDTO);

    /**
     * 列表Version2
     *
     * @param roleListReqDTO 入参DTO
     * @return
     */
    List<RoleListResDTO> listByVersion2(RoleListReqDTO roleListReqDTO);

    /**
     * 列表Version3
     *
     * @param roleListReqDTO 入参DTO
     * @return
     */
    List<RoleListResDTO> listByVersion3(RoleListReqDTO roleListReqDTO);

    /**
     * First查询
     *
     * @param roleListReqDTO 入参DTO
     * @return
     */
    RoleListResDTO listOne(RoleListReqDTO roleListReqDTO);

    /**
     * 分页
     *
     * @param rolePageReqDTO 入参DTO
     * @param current        当前页
     * @param size           每页大小
     * @return
     */
    PageDTO<RolePageResDTO> pagination(RolePageReqDTO rolePageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param roleAddReqDTO 入参DTO
     * @return
     */
    Boolean add(RoleAddReqDTO roleAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param roleAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(RoleAddReqDTO roleAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param roleAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<RoleAddReqDTO> roleAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    RoleShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<RoleShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param roleModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(RoleModifyReqDTO roleModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param roleModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(RoleModifyReqDTO roleModifyReqDTO);

    /**
     * 参数删除
     *
     * @param roleRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(RoleRemoveReqDTO roleRemoveReqDTO);

    /**
     * 获取角色权限列表
     *
     * @param roleListPermissionReqDTO
     * @return
     */
    List<RoleListPermissionResDTO> listPermission(RoleListPermissionReqDTO roleListPermissionReqDTO);

    /**
     * 修改角色权限
     *
     * @param roleModifyPermissionReqDTO
     * @return
     */
    Boolean modifyPermission(RoleModifyPermissionReqDTO roleModifyPermissionReqDTO);
}
