package com.jeasy.dictionary.service;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.BaseService;
import com.jeasy.dictionary.dto.*;

import java.util.List;

/**
 * 字典 Service
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
public interface DictionaryService extends BaseService<DictionaryDTO> {

    /**
     * 列表
     *
     * @param dictionaryListReqDTO 入参DTO
     * @return
     */
    List<DictionaryListResDTO> list(DictionaryListReqDTO dictionaryListReqDTO);

    /**
     * 列表Version1
     *
     * @param dictionaryListReqDTO 入参DTO
     * @return
     */
    List<DictionaryListResDTO> listByVersion1(DictionaryListReqDTO dictionaryListReqDTO);

    /**
     * 列表Version2
     *
     * @param dictionaryListReqDTO 入参DTO
     * @return
     */
    List<DictionaryListResDTO> listByVersion2(DictionaryListReqDTO dictionaryListReqDTO);

    /**
     * 列表Version3
     *
     * @param dictionaryListReqDTO 入参DTO
     * @return
     */
    List<DictionaryListResDTO> listByVersion3(DictionaryListReqDTO dictionaryListReqDTO);

    /**
     * First查询
     *
     * @param dictionaryListReqDTO 入参DTO
     * @return
     */
    DictionaryListResDTO listOne(DictionaryListReqDTO dictionaryListReqDTO);

    /**
     * 分页
     *
     * @param dictionaryPageReqDTO 入参DTO
     * @param current              当前页
     * @param size                 每页大小
     * @return
     */
    PageDTO<DictionaryPageResDTO> pagination(DictionaryPageReqDTO dictionaryPageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param dictionaryAddReqDTO 入参DTO
     * @return
     */
    Boolean add(DictionaryAddReqDTO dictionaryAddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param dictionaryAddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(DictionaryAddReqDTO dictionaryAddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param dictionaryAddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<DictionaryAddReqDTO> dictionaryAddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    DictionaryShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<DictionaryShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param dictionaryModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(DictionaryModifyReqDTO dictionaryModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param dictionaryModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(DictionaryModifyReqDTO dictionaryModifyReqDTO);

    /**
     * 参数删除
     *
     * @param dictionaryRemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(DictionaryRemoveReqDTO dictionaryRemoveReqDTO);

    /**
     * 字典类型列表
     *
     * @return
     */
    List<DictionaryTypeListResDTO> listType();
}
