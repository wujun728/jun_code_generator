package com.jeasy.base.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.manager.BaseManager;
import com.jeasy.base.mybatis.entity.BaseEntity;
import com.jeasy.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * BaseServiceImpl
 *
 * @param <T> DTO
 * @param <M> Manager
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class BaseServiceImpl<M extends BaseManager<E, T>, E extends BaseEntity, T> implements BaseService<T> {

    @Autowired
    protected M manager;

    @Override
    public List<T> find(T dto) {
        return manager.findList(dto);
    }

    @Override
    public T findById(final Long id) {
        return manager.findById(id);
    }

    @Override
    public List<T> findByIds(final List<Long> ids) {
        return manager.findBatchIds(ids);
    }

    @Override
    public PageDTO<T> pagination(T dto, final Integer current, final Integer size) {
        return manager.findPage(dto, current, size);
    }

    @Override
    public T findOne(T dto) {
        return manager.findOne(dto);
    }

    @Override
    public Boolean save(T dto) {
        return manager.saveDTO(dto);
    }

    @Override
    public Boolean saveAllColumn(T dto) {
        return manager.saveAllColumn(dto);
    }

    @Override
    public Boolean saveBatchAllColumn(final List<T> dtoList) {
        return manager.saveBatchAllColumn(dtoList);
    }

    @Override
    public Boolean modify(T dto) {
        return manager.modifyById(dto);
    }

    @Override
    public Boolean modifyAllColumn(T dto) {
        return manager.modifyAllColumnById(dto);
    }

    @Override
    public Boolean remove(final Long id) {
        return manager.removeById(id);
    }

    @Override
    public Boolean removeBatch(final List<Long> ids) {
        return manager.removeBatchIds(ids);
    }

    @Override
    public Boolean removeByParams(T dto) {
        return manager.remove(dto);
    }
}
