package com.jeasy.base.manager.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.manager.BaseManager;
import com.jeasy.base.mybatis.dao.BaseDAO;
import com.jeasy.base.mybatis.entity.BaseEntity;
import com.jeasy.base.web.dto.CurrentUser;
import com.jeasy.base.web.dto.Device;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.collection.CollectionKit;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.object.MapKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.thread.ThreadLocalKit;
import com.jeasy.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * BaseManagerImpl
 *
 * @param <D> DAO
 * @param <E> Entity
 * @param <T> DTO
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public abstract class BaseManagerImpl<D extends BaseDAO<E>, E extends BaseEntity, T> extends ServiceImpl<D, E> implements BaseManager<E, T> {

    private static final String ID = "id";

    @Override
    public Boolean saveDTO(T dto) {
        if (Func.isEmpty(dto)) {
            throw new ServiceException(ModelResult.CODE_500, "dto is null or empty or empty while invoke save() method");
        }

        E entity = dtoToEntity(dto);
        boolean result = super.save(handleSaveInfo(entity));

        if (result) {
            BeanKit.setProperty(dto, ID, entity.getId());
        }
        return result;
    }

    @Override
    public Boolean saveMap(final Map<String, Object> dtoMap) {
        if (Func.isEmpty(dtoMap)) {
            throw new ServiceException(ModelResult.CODE_500, "dtoMap is null or empty or empty while invoke save() method");
        }

        E entity = mapToEntity(dtoMap);
        boolean result = super.save(handleSaveInfo(entity));

        if (result) {
            dtoMap.put(ID, entity.getId());
        }
        return result;
    }

    @Override
    public Boolean saveAllColumn(T dto) {
        if (Func.isEmpty(dto)) {
            throw new ServiceException(ModelResult.CODE_500, "dto is null or empty or empty while invoke saveAllColumn() method");
        }

        E entity = dtoToEntity(dto);
        boolean result = super.save(handleSaveInfo(entity));

        if (result) {
            BeanKit.setProperty(dto, ID, entity.getId());
        }
        return result;
    }

    @Override
    public Boolean saveBatchAllColumn(final List<T> dtos) {
        if (Func.isEmpty(dtos)) {
            throw new ServiceException(ModelResult.CODE_500, "dtos is null or empty while invoke saveBatchAllColumn() method");
        }

        List<E> entityList = Lists.newArrayList();
        for (T dto : dtos) {
            entityList.add(handleSaveInfo(dtoToEntity(dto)));
        }

        boolean result = super.saveBatch(entityList);
        if (result) {
            for (int i = 0; i < dtos.size(); i++) {
                BeanKit.setProperty(dtos.get(i), ID, entityList.get(i).getId());
            }
        }
        return result;
    }

    @Override
    public Boolean saveAllColumn(final Map<String, Object> dtoMap) {
        if (Func.isEmpty(dtoMap)) {
            throw new ServiceException(ModelResult.CODE_500, "dtoMap is null or empty while invoke saveAllColumn() method");
        }

        E entity = mapToEntity(dtoMap);
        boolean result = super.save(handleSaveInfo(entity));

        if (result) {
            dtoMap.put(ID, entity.getId());
        }
        return result;
    }

    @Override
    public Boolean removeById(final Long id) {
        if (Func.isNullOrZero(id)) {
            throw new ServiceException(ModelResult.CODE_500, "id is null or empty while invoke removeById() method");
        }

        return super.removeById(id);
    }

    @Override
    public Boolean remove(T dto) {
        if (Func.isEmpty(dto)) {
            throw new ServiceException(ModelResult.CODE_500, "dto is null or empty while invoke remove() method");
        }

        E entity = dtoToEntity(dto);
        return remove(MapKit.toObjMap(entity));
    }

    @Override
    public Boolean remove(final Map<String, Object> params) {
        if (Func.isEmpty(params)) {
            throw new ServiceException(ModelResult.CODE_500, "params is null or empty while invoke remove() method");
        }

        return super.removeByMap(params);
    }

    @Override
    public Boolean removeByWrapper(final Wrapper<E> wrapper) {
        if (Func.isEmpty(wrapper)) {
            throw new ServiceException(ModelResult.CODE_500, "wrapper is null or empty while invoke removeByWrapper() method");
        }

        return super.remove(wrapper);
    }

    @Override
    public Boolean removeBatchIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new ServiceException(ModelResult.CODE_500, "ids is null or empty while invoke removeBatchIds() method");
        }

        return super.removeByIds(ids);
    }

    @Override
    public Boolean modifyById(T dto) {
        if (Func.isEmpty(dto)) {
            throw new ServiceException(ModelResult.CODE_500, "dto is null or empty while invoke modifyById() method");
        }

        return super.updateById(handleUpdateInfo(dtoToEntity(dto)));
    }

    @Override
    public Boolean modifyById(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            throw new ServiceException(ModelResult.CODE_500, "map is null or empty while invoke modifyById() method");
        }

        return super.updateById(handleUpdateInfo(mapToEntity(map)));
    }

    @Override
    public Boolean modifyAllColumnById(T dto) {
        if (Func.isEmpty(dto)) {
            throw new ServiceException(ModelResult.CODE_500, "dto is null or empty while invoke modifyAllColumnById() method");
        }

        return super.updateById(handleUpdateInfo(dtoToEntity(dto)));
    }

    @Override
    public Boolean modifyAllColumnById(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            throw new ServiceException(ModelResult.CODE_500, "map is null or empty while invoke modifyAllColumnById() method");
        }

        return super.updateById(handleUpdateInfo(mapToEntity(map)));
    }

    @Override
    public Boolean modify(T dto, final Map<String, Object> whereMap) {
        if (Func.isEmpty(whereMap)) {
            throw new ServiceException(ModelResult.CODE_500, "whereMap is null or empty while invoke modify() method");
        }

        return super.update(handleUpdateInfo(dtoToEntity(dto)), new UpdateWrapper<E>().allEq(whereMap));
    }

    @Override
    public Boolean modify(T dto, final Wrapper<E> wrapper) {
        if (Func.isEmpty(wrapper)) {
            throw new ServiceException(ModelResult.CODE_500, "wrapper is null or empty while invoke modify() method");
        }

        return super.update(handleUpdateInfo(dtoToEntity(dto)), wrapper);
    }

    @Override
    public T findById(final Long id) {
        if (Func.isEmpty(id)) {
            throw new ServiceException(ModelResult.CODE_500, "id is null or empty while invoke findById() method");
        }

        E entity = super.getById(id);
        return entityToDTO(entity);
    }

    @Override
    public List<T> findBatchIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new ServiceException(ModelResult.CODE_500, "ids is null or empty while invoke findBatchIds() method");
        }

        List<E> entityList = CollectionKit.toArrayList(super.listByIds(ids));
        return entityToDTOList(entityList);
    }

    @Override
    public List<T> findByMap(final Map<String, Object> params) {
        List<E> entityList = CollectionKit.toArrayList(super.listByMap(params));
        return entityToDTOList(entityList);
    }

    @Override
    public T findOne(T dto) {
        if (Func.isEmpty(dto)) {
            throw new ServiceException(ModelResult.CODE_500, "dto is null or empty while invoke findOne() method");
        }

        E entity = super.getOne(new QueryWrapper<>(dtoToEntity(dto)));
        return entityToDTO(entity);
    }

    @Override
    public T findOne(final Wrapper<E> wrapper) {
        if (Func.isEmpty(wrapper)) {
            throw new ServiceException(ModelResult.CODE_500, "wrapper is null or empty while invoke findOne() method");
        }

        E entity = super.getOne(wrapper);
        return entityToDTO(entity);
    }

    @Override
    public T findOne(final Map<String, Object> params) {
        if (Func.isEmpty(params)) {
            throw new ServiceException(ModelResult.CODE_500, "params is null or empty while invoke findOne() method");
        }

        E entity = super.getOne(new QueryWrapper<>(mapToEntity(params)));
        return entityToDTO(entity);
    }

    @Override
    public Integer findCount(T dto) {
        return super.count(new QueryWrapper<>(dtoToEntity(dto)));
    }

    @Override
    public Integer findCount(final Map<String, Object> params) {
        return super.count(new QueryWrapper<>(mapToEntity(params)));
    }

    @Override
    public Integer findCount(final Wrapper<E> wrapper) {
        return super.count(wrapper);
    }

    @Override
    public List<T> findList(T dto) {
        List<E> entityList = super.list(new QueryWrapper<>(dtoToEntity(dto)));
        return entityToDTOList(entityList);
    }

    @Override
    public List<T> findList(final Map<String, Object> params) {
        List<E> entityList = super.list(new QueryWrapper<>(mapToEntity(params)));
        return entityToDTOList(entityList);
    }

    @Override
    public List<T> findList(final Wrapper<E> wrapper) {
        List<E> entityList = super.list(wrapper);
        return entityToDTOList(entityList);
    }

    @Override
    public List<Map<String, Object>> findMaps(T dto) {
        return super.listMaps(new QueryWrapper<>(dtoToEntity(dto)));
    }

    @Override
    public List<Map<String, Object>> findMaps(final Map<String, Object> params) {
        return super.listMaps(new QueryWrapper<>(mapToEntity(params)));
    }

    @Override
    public List<Map<String, Object>> findMaps(final Wrapper<E> wrapper) {
        return super.listMaps(wrapper);
    }

    @Override
    public List<Object> findObjs(T dto) {
        return super.listObjs(new QueryWrapper<>(dtoToEntity(dto)));
    }

    @Override
    public List<Object> findObjs(final Map<String, Object> params) {
        return super.listObjs(new QueryWrapper<>(mapToEntity(params)));
    }

    @Override
    public Object findObj(T dto) {
        return super.getObj(new QueryWrapper<>(dtoToEntity(dto)), null);
    }

    @Override
    public Object findObj(final Map<String, Object> params) {
        return super.getObj(new QueryWrapper<>(mapToEntity(params)), null);
    }

    @Override
    public Object findObj(final Wrapper<E> wrapper) {
        return super.getObj(wrapper, null);
    }

    @Override
    public List<Object> findObjs(final Wrapper<E> wrapper) {
        return super.listObjs(wrapper);
    }

    @Override
    public PageDTO<T> findPage(T dto, final Integer current, final Integer size) {
        IPage<E> page = new Page<>(current, size);
        page = super.page(page, new QueryWrapper<>(dtoToEntity(dto)));

        return new PageDTO<T>(current, size).setRecords(entityToDTOList(page.getRecords()));
    }

    @Override
    public PageDTO<T> findPage(final Map<String, Object> params, final Integer current, final Integer size) {
        IPage<E> page = new Page<>(current, size);
        page = super.page(page, new QueryWrapper<>(mapToEntity(params)));

        return new PageDTO<T>(current, size).setRecords(entityToDTOList(page.getRecords()));
    }

    @Override
    public PageDTO<T> findPage(final Wrapper<E> wrapper, final Integer current, final Integer size) {
        IPage<E> page = new Page<>(current, size);
        page = super.page(page, wrapper);

        return new PageDTO<T>(current, size).setRecords(entityToDTOList(page.getRecords()));
    }

    @Override
    public PageDTO<Map<String, Object>> findMapsPage(T dto, final Integer current, final Integer size) {
        IPage<E> page = new Page<>(current, size);
        IPage<Map<String, Object>> result = super.pageMaps(page, new QueryWrapper<>(dtoToEntity(dto)));
        return new PageDTO<Map<String, Object>>(current, size).setRecords(result.getRecords());
    }

    @Override
    public PageDTO<Map<String, Object>> findMapsPage(final Map<String, Object> params, final Integer current, final Integer size) {
        IPage<E> page = new Page<>(current, size);
        IPage<Map<String, Object>> result = super.pageMaps(page, new QueryWrapper<>(mapToEntity(params)));
        return new PageDTO<Map<String, Object>>(current, size).setRecords(result.getRecords());
    }

    @Override
    public PageDTO<Map<String, Object>> findMapsPage(final Wrapper<E> wrapper, final Integer current, final Integer size) {
        IPage<E> page = new Page<>(current, size);
        IPage<Map<String, Object>> result = super.pageMaps(page, wrapper);
        return new PageDTO<Map<String, Object>>(current, size).setRecords(result.getRecords());
    }

    private E handleSaveInfo(E entity) {
        handleDelInfo(entity);
        handleTestInfo(entity);
        handleUpdateInfo(entity);
        handleCreateInfo(entity);
        return entity;
    }

    private Map<String, Object> toUnderlineMap(final Map<String, Object> params) {
        Map<String, Object> underLineMap = Maps.newHashMap();
        if (!Func.isEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                underLineMap.put(StrKit.toUnderlineCase(entry.getKey()), entry.getValue());
            }
        }
        return underLineMap;
    }

    private Map<String, Object> toLowerFirstCamelMap(final Map<String, Object> params) {
        Map<String, Object> lowerFirstCamelMap = Maps.newHashMap();
        if (!Func.isEmpty(params)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                lowerFirstCamelMap.put(StrKit.lowerFirst(StrKit.toCamelCase(entry.getKey())), entry.getValue());
            }
        }
        return lowerFirstCamelMap;
    }

    /**
     * 获取当前登录User信息
     *
     * @return
     */
    protected final CurrentUser getCurrentUser() {
        return ThreadLocalKit.getCurrentUser();
    }

    /**
     * 获取当前登录User设备信息
     *
     * @return
     */
    protected final Device getDevice() {
        return ThreadLocalKit.getDevice();
    }

    /**
     * 处理创建信息
     *
     * @param entity
     * @return
     */
    protected final E handleCreateInfo(E entity) {
        entity.setCreateAt(System.currentTimeMillis());
        if (getCurrentUser() != null) {
            entity.setCreateBy(getCurrentUser().getId());
            entity.setCreateName(getCurrentUser().getName());
        }
        return entity;
    }

    /**
     * 处理更新信息
     *
     * @param entity
     * @return
     */
    protected final E handleUpdateInfo(E entity) {
        entity.setUpdateAt(System.currentTimeMillis());
        if (getCurrentUser() != null) {
            entity.setUpdateBy(getCurrentUser().getId());
            entity.setUpdateName(getCurrentUser().getName());
        }
        return entity;
    }

    /**
     * 处理测试信息
     *
     * @param entity 实体
     * @return
     */
    protected final E handleTestInfo(E entity) {
        if (getCurrentUser() != null) {
            entity.setIsTest(getCurrentUser().getIsTest());
        }
        return entity;
    }

    /**
     * 处理删除信息
     *
     * @param entity 实体
     * @return
     */
    protected final E handleDelInfo(E entity) {
        if (!Func.isEmpty(entity)) {
            entity.setIsDel(0);
        }
        return entity;
    }

    /**
     * entityToDTOList
     *
     * @param entityList
     * @return
     */
    protected abstract List<T> entityToDTOList(List<E> entityList);

    /**
     * entityToDTO
     *
     * @param entity
     * @return
     */
    protected abstract T entityToDTO(E entity);

    /**
     * dtoToEntityList
     *
     * @param dtoList
     * @return
     */
    protected abstract List<E> dtoToEntityList(List<T> dtoList);

    /**
     * dtoToEntity
     *
     * @param dto
     * @return
     */
    protected abstract E dtoToEntity(T dto);

    /**
     * mapToEntity
     *
     * @param map
     * @return
     */
    protected abstract E mapToEntity(Map<String, Object> map);

    /**
     * mapToDTO
     *
     * @param map
     * @return
     */
    protected abstract T mapToDTO(Map<String, Object> map);
}
