package com.jeasy.base.mybatis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeasy.base.mybatis.entity.BaseEntity;

import java.util.List;

/**
 * BaseDAO
 *
 * @param <E> Entity
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface BaseDAO<E extends BaseEntity> extends BaseMapper<E> {

    /**
     * 批量插入(所有字段)
     *
     * @param entityList
     * @return
     */
    Integer insertBatchAllColumn(List<E> entityList);
}
