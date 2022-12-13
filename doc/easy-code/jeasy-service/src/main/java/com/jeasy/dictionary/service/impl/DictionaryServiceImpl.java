package com.jeasy.dictionary.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.dictionary.dto.*;
import com.jeasy.dictionary.entity.DictionaryEntity;
import com.jeasy.dictionary.manager.DictionaryManager;
import com.jeasy.dictionary.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryManager, DictionaryEntity, DictionaryDTO> implements DictionaryService {

    @Override
    public List<DictionaryListResDTO> list(final DictionaryListReqDTO dictionaryListReqDTO) {
        return manager.list(dictionaryListReqDTO);
    }

    @Override
    public List<DictionaryListResDTO> listByVersion1(final DictionaryListReqDTO dictionaryListReqDTO) {
        return manager.listByVersion1(dictionaryListReqDTO);
    }

    @Override
    public List<DictionaryListResDTO> listByVersion2(final DictionaryListReqDTO dictionaryListReqDTO) {
        return manager.listByVersion2(dictionaryListReqDTO);
    }

    @Override
    public List<DictionaryListResDTO> listByVersion3(final DictionaryListReqDTO dictionaryListReqDTO) {
        return manager.listByVersion3(dictionaryListReqDTO);
    }

    @Override
    public DictionaryListResDTO listOne(final DictionaryListReqDTO dictionaryListReqDTO) {
        return manager.listOne(dictionaryListReqDTO);
    }

    @Override
    public PageDTO<DictionaryPageResDTO> pagination(final DictionaryPageReqDTO dictionaryPageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(dictionaryPageReqDTO, current, size);
    }

    @Override
    public Boolean add(final DictionaryAddReqDTO dictionaryAddReqDTO) {
        return manager.add(dictionaryAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final DictionaryAddReqDTO dictionaryAddReqDTO) {
        return manager.addAllColumn(dictionaryAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<DictionaryAddReqDTO> dictionaryAddReqDTOList) {
        return manager.addBatchAllColumn(dictionaryAddReqDTOList);
    }

    @Override
    public DictionaryShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<DictionaryShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final DictionaryModifyReqDTO dictionaryModifyReqDTO) {
        return manager.modify(dictionaryModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final DictionaryModifyReqDTO dictionaryModifyReqDTO) {
        return manager.modifyAllColumn(dictionaryModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final DictionaryRemoveReqDTO dictionaryRemoveReqDTO) {
        return manager.removeByParams(dictionaryRemoveReqDTO);
    }

    @Override
    public List<DictionaryTypeListResDTO> listType() {
        return manager.listType();
    }
}
