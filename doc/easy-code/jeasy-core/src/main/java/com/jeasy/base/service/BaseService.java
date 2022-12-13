package com.jeasy.base.service;

import com.jeasy.base.dto.PageDTO;

import java.util.List;

/**
 * BaseService
 *
 * @param <T> DTO
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface BaseService<T> {

    /**
     * 查询
     *
     * @param dto 参数DTO
     * @return
     */
    List<T> find(T dto);

    /**
     * ID查询
     *
     * @param id
     * @return
     */
    T findById(Long id);

    /**
     * ID批量查询
     *
     * @param ids
     * @return
     */
    List<T> findByIds(List<Long> ids);

    /**
     * 参数分页查询
     *
     * @param dto     参数DTO
     * @param current 当前页
     * @param size    大小
     * @return
     */
    PageDTO<T> pagination(T dto, Integer current, Integer size);

    /**
     * First查询
     *
     * @param dto
     * @return
     */
    T findOne(T dto);

    /**
     * 保存
     *
     * @param dto
     * @return
     */
    Boolean save(T dto);

    /**
     * 选择保存
     *
     * @param dto
     * @return
     */
    Boolean saveAllColumn(T dto);

    /**
     * 选择保存
     *
     * @param dtoList
     * @return
     */
    Boolean saveBatchAllColumn(List<T> dtoList);

    /**
     * 修改
     *
     * @param dto
     * @return
     */
    Boolean modify(T dto);

    /**
     * 选择修改
     *
     * @param dto
     * @return
     */
    Boolean modifyAllColumn(T dto);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Boolean remove(Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    Boolean removeBatch(List<Long> ids);

    /**
     * 参数删除
     *
     * @param dto
     * @return
     */
    Boolean removeByParams(T dto);
}
