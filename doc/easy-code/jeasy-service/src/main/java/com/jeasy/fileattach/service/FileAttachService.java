package com.jeasy.fileattach.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.fileattach.dto.*;

import java.util.List;

/**
 * 文件附件 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
public interface FileAttachService extends BaseService<FileAttachDTO> {

    /**
     * 列表
     *
     * @param fileAttachListReqDTO 入参DTO
     * @return
     */
    List<FileAttachListResDTO> list(FileAttachListReqDTO fileAttachListReqDTO);

    /**
     * 列表Version1
     *
     * @param fileAttachListReqDTO 入参DTO
     * @return
     */
    List<FileAttachListResDTO> listByVersion1(FileAttachListReqDTO fileAttachListReqDTO);

    /**
     * 列表Version2
     *
     * @param fileAttachListReqDTO 入参DTO
     * @return
     */
    List<FileAttachListResDTO> listByVersion2(FileAttachListReqDTO fileAttachListReqDTO);

    /**
     * 列表Version3
     *
     * @param fileAttachListReqDTO 入参DTO
     * @return
     */
    List<FileAttachListResDTO> listByVersion3(FileAttachListReqDTO fileAttachListReqDTO);

    /**
     * First查询
     *
     * @param fileAttachListReqDTO 入参DTO
     * @return
     */
    FileAttachListResDTO listOne(FileAttachListReqDTO fileAttachListReqDTO);

    /**
     * 分页
     *
     * @param fileAttachPageReqDTO 入参DTO
     * @param current       当前页
     * @param size          每页大小
     * @return
     */
    PageDTO<FileAttachPageResDTO> pagination(FileAttachPageReqDTO fileAttachPageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param fileAttachAddReqDTO 入参DTO
     * @return
     */
    Boolean add(FileAttachAddReqDTO fileAttachAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param fileAttachAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(FileAttachAddReqDTO fileAttachAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param fileAttachAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<FileAttachAddReqDTO> fileAttachAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    FileAttachShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<FileAttachShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param fileAttachModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(FileAttachModifyReqDTO fileAttachModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param fileAttachModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(FileAttachModifyReqDTO fileAttachModifyReqDTO);

    /**
     * 参数删除
     *
     * @param fileAttachRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(FileAttachRemoveReqDTO fileAttachRemoveReqDTO);
}
