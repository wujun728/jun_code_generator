package com.jeasy.userorg.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.userorg.dto.*;

import java.util.List;

/**
 * 用户机构 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
public interface UserOrgService extends BaseService<UserOrgDTO> {

    /**
     * 列表
     *
     * @param userorgListReqDTO 入参DTO
     * @return
     */
    List<UserOrgListResDTO> list(UserOrgListReqDTO userorgListReqDTO);

    /**
     * 列表Version1
     *
     * @param userorgListReqDTO 入参DTO
     * @return
     */
    List<UserOrgListResDTO> listByVersion1(UserOrgListReqDTO userorgListReqDTO);

    /**
     * 列表Version2
     *
     * @param userorgListReqDTO 入参DTO
     * @return
     */
    List<UserOrgListResDTO> listByVersion2(UserOrgListReqDTO userorgListReqDTO);

    /**
     * 列表Version3
     *
     * @param userorgListReqDTO 入参DTO
     * @return
     */
    List<UserOrgListResDTO> listByVersion3(UserOrgListReqDTO userorgListReqDTO);

    /**
     * First查询
     *
     * @param userorgListReqDTO 入参DTO
     * @return
     */
    UserOrgListResDTO listOne(UserOrgListReqDTO userorgListReqDTO);

    /**
     * 分页
     *
     * @param userorgPageReqDTO 入参DTO
     * @param current           当前页
     * @param size              每页大小
     * @return
     */
    PageDTO<UserOrgPageResDTO> pagination(UserOrgPageReqDTO userorgPageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param userorgAddReqDTO 入参DTO
     * @return
     */
    Boolean add(UserOrgAddReqDTO userorgAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param userorgAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(UserOrgAddReqDTO userorgAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param userorgAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<UserOrgAddReqDTO> userorgAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    UserOrgShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<UserOrgShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param userorgModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(UserOrgModifyReqDTO userorgModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param userorgModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(UserOrgModifyReqDTO userorgModifyReqDTO);

    /**
     * 参数删除
     *
     * @param userorgRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(UserOrgRemoveReqDTO userorgRemoveReqDTO);
}
