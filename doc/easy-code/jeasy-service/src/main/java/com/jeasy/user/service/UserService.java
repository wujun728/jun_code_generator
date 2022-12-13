package com.jeasy.user.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.user.dto.*;

import java.util.List;

/**
 * 用户 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
public interface UserService extends BaseService<UserDTO> {

    /**
     * 列表
     *
     * @param userListReqDTO 入参DTO
     * @return
     */
    List<UserListResDTO> list(UserListReqDTO userListReqDTO);

    /**
     * 列表Version1
     *
     * @param userListReqDTO 入参DTO
     * @return
     */
    List<UserListResDTO> listByVersion1(UserListReqDTO userListReqDTO);

    /**
     * 列表Version2
     *
     * @param userListReqDTO 入参DTO
     * @return
     */
    List<UserListResDTO> listByVersion2(UserListReqDTO userListReqDTO);

    /**
     * 列表Version3
     *
     * @param userListReqDTO 入参DTO
     * @return
     */
    List<UserListResDTO> listByVersion3(UserListReqDTO userListReqDTO);

    /**
     * First查询
     *
     * @param userListReqDTO 入参DTO
     * @return
     */
    UserListResDTO listOne(UserListReqDTO userListReqDTO);

    /**
     * 分页
     *
     * @param userPageReqDTO 入参DTO
     * @param current        当前页
     * @param size           每页大小
     * @return
     */
    PageDTO<UserPageResDTO> pagination(UserPageReqDTO userPageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param userAddReqDTO 入参DTO
     * @return
     */
    Boolean add(UserAddReqDTO userAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param userAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(UserAddReqDTO userAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param userAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<UserAddReqDTO> userAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    UserShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<UserShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param userModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(UserModifyReqDTO userModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param userModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(UserModifyReqDTO userModifyReqDTO);

    /**
     * 参数删除
     *
     * @param userRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(UserRemoveReqDTO userRemoveReqDTO);

    /**
     * 用户角色分页
     *
     * @param userPageRoleReqDTO 入参DTO
     * @param current            当前页
     * @param size               大小
     * @return
     */
    PageDTO<UserPageRoleResDTO> pagination(UserPageRoleReqDTO userPageRoleReqDTO, Integer current, Integer size);

    /**
     * 修改用户角色
     *
     * @param userModifyRoleReqDTO
     * @return
     */
    Boolean modifyRole(UserModifyRoleReqDTO userModifyRoleReqDTO);

    /**
     * 获取用户角色列表
     *
     * @param userListRoleReqDTO
     * @return
     */
    List<UserListRoleResDTO> listRole(UserListRoleReqDTO userListRoleReqDTO);

    /**
     * 获取用户机构列表
     *
     * @param userListOrganizationReqDTO
     * @return
     */
    List<UserListOrganizationResDTO> listOrganization(UserListOrganizationReqDTO userListOrganizationReqDTO);

    /**
     * 修改用户机构
     *
     * @param userModifyOrganizationReqDTO
     * @return
     */
    Boolean modifyOrganization(UserModifyOrganizationReqDTO userModifyOrganizationReqDTO);
}
