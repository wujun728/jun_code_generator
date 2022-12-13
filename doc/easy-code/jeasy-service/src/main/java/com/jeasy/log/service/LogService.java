package com.jeasy.log.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.log.dto.*;

import java.util.List;

/**
 * 日志 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
public interface LogService extends BaseService<LogDTO> {

    /**
     * 列表
     *
     * @param logListReqDTO 入参DTO
     * @return
     */
    List<LogListResDTO> list(LogListReqDTO logListReqDTO);

    /**
     * 列表Version1
     *
     * @param logListReqDTO 入参DTO
     * @return
     */
    List<LogListResDTO> listByVersion1(LogListReqDTO logListReqDTO);

    /**
     * 列表Version2
     *
     * @param logListReqDTO 入参DTO
     * @return
     */
    List<LogListResDTO> listByVersion2(LogListReqDTO logListReqDTO);

    /**
     * 列表Version3
     *
     * @param logListReqDTO 入参DTO
     * @return
     */
    List<LogListResDTO> listByVersion3(LogListReqDTO logListReqDTO);

    /**
     * First查询
     *
     * @param logListReqDTO 入参DTO
     * @return
     */
    LogListResDTO listOne(LogListReqDTO logListReqDTO);

    /**
     * 分页
     *
     * @param logPageReqDTO 入参DTO
     * @param current       当前页
     * @param size          每页大小
     * @return
     */
    PageDTO<LogPageResDTO> pagination(LogPageReqDTO logPageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param logAddReqDTO 入参DTO
     * @return
     */
    Boolean add(LogAddReqDTO logAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param logAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(LogAddReqDTO logAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param logAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<LogAddReqDTO> logAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    LogShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<LogShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param logModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(LogModifyReqDTO logModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param logModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(LogModifyReqDTO logModifyReqDTO);

    /**
     * 参数删除
     *
     * @param logRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(LogRemoveReqDTO logRemoveReqDTO);
}
