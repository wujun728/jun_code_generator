package com.jeasy.resource.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.resource.dto.*;

import java.util.List;

/**
 * 菜单 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
public interface ResourceService extends BaseService<ResourceDTO> {

    /**
     * 列表
     *
     * @param resourceListReqDTO 入参DTO
     * @return
     */
    List<ResourceListResDTO> list(ResourceListReqDTO resourceListReqDTO);

    /**
     * First查询
     *
     * @param resourceListReqDTO 入参DTO
     * @return
     */
    ResourceListResDTO listOne(ResourceListReqDTO resourceListReqDTO);

    /**
     * 分页
     *
     * @param resourcePageReqDTO 入参DTO
     * @param current            当前页
     * @param size               每页大小
     * @return
     */
    PageDTO<ResourcePageResDTO> pagination(ResourcePageReqDTO resourcePageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param resourceAddReqDTO 入参DTO
     * @return
     */
    Boolean add(ResourceAddReqDTO resourceAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param resourceAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(ResourceAddReqDTO resourceAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param resourceAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<ResourceAddReqDTO> resourceAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    ResourceShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<ResourceShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param resourceModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(ResourceModifyReqDTO resourceModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param resourceModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(ResourceModifyReqDTO resourceModifyReqDTO);

    /**
     * 参数删除
     *
     * @param resourceRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(ResourceRemoveReqDTO resourceRemoveReqDTO);

    /**
     * 获取当前用户的菜单权限
     *
     * @return
     */
    List<UserMenuResourceDTO> listUserMenu();

    /**
     * 获取菜单页面操作集合
     *
     * @param menuId
     * @return
     */
    List<UserMenuOperationDTO> listUserMenuOperation(String menuId);
}
