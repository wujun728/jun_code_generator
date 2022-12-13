package com.jeasy.userrole.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.userrole.dto.*;

import java.util.List;

/**
 * 用户角色 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
public interface UserRoleService extends BaseService<UserRoleDTO> {

    /**
     * 列表
     *
     * @param userroleListReqDTO 入参DTO
     * @return
     */
    List<UserRoleListResDTO> list(UserRoleListReqDTO userroleListReqDTO);

    /**
     * 列表Version1
     *
     * @param userroleListReqDTO 入参DTO
     * @return
     */
    List<UserRoleListResDTO> listByVersion1(UserRoleListReqDTO userroleListReqDTO);

    /**
     * 列表Version2
     *
     * @param userroleListReqDTO 入参DTO
     * @return
     */
    List<UserRoleListResDTO> listByVersion2(UserRoleListReqDTO userroleListReqDTO);

    /**
     * 列表Version3
     *
     * @param userroleListReqDTO 入参DTO
     * @return
     */
    List<UserRoleListResDTO> listByVersion3(UserRoleListReqDTO userroleListReqDTO);

    /**
     * First查询
     *
     * @param userroleListReqDTO 入参DTO
     * @return
     */
    UserRoleListResDTO listOne(UserRoleListReqDTO userroleListReqDTO);

    /**
     * 分页
     *
     * @param userrolePageReqDTO 入参DTO
     * @param current            当前页
     * @param size               每页大小
     * @return
     */
    PageDTO<UserRolePageResDTO> pagination(UserRolePageReqDTO userrolePageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param userroleAddReqDTO 入参DTO
     * @return
     */
    Boolean add(UserRoleAddReqDTO userroleAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param userroleAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(UserRoleAddReqDTO userroleAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param userroleAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<UserRoleAddReqDTO> userroleAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    UserRoleShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<UserRoleShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param userroleModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(UserRoleModifyReqDTO userroleModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param userroleModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(UserRoleModifyReqDTO userroleModifyReqDTO);

    /**
     * 参数删除
     *
     * @param userroleRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(UserRoleRemoveReqDTO userroleRemoveReqDTO);
}
