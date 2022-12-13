package com.jeasy.log.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.log.dto.*;
import com.jeasy.log.entity.LogEntity;
import com.jeasy.log.manager.LogManager;
import com.jeasy.log.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
@Service
public class LogServiceImpl extends BaseServiceImpl<LogManager, LogEntity, LogDTO> implements LogService {

    @Override
    public List<LogListResDTO> list(final LogListReqDTO logListReqDTO) {
        return manager.list(logListReqDTO);
    }

    @Override
    public List<LogListResDTO> listByVersion1(final LogListReqDTO logListReqDTO) {
        return manager.listByVersion1(logListReqDTO);
    }

    @Override
    public List<LogListResDTO> listByVersion2(final LogListReqDTO logListReqDTO) {
        return manager.listByVersion2(logListReqDTO);
    }

    @Override
    public List<LogListResDTO> listByVersion3(final LogListReqDTO logListReqDTO) {
        return manager.listByVersion3(logListReqDTO);
    }

    @Override
    public LogListResDTO listOne(final LogListReqDTO logListReqDTO) {
        return manager.listOne(logListReqDTO);
    }

    @Override
    public PageDTO<LogPageResDTO> pagination(final LogPageReqDTO logPageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(logPageReqDTO, current, size);
    }

    @Override
    public Boolean add(final LogAddReqDTO logAddReqDTO) {
        return manager.add(logAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final LogAddReqDTO logAddReqDTO) {
        return manager.addAllColumn(logAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<LogAddReqDTO> logAddReqDTOList) {
        return manager.addBatchAllColumn(logAddReqDTOList);
    }

    @Override
    public LogShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<LogShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final LogModifyReqDTO logModifyReqDTO) {
        return manager.modify(logModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final LogModifyReqDTO logModifyReqDTO) {
        return manager.modifyAllColumn(logModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final LogRemoveReqDTO logRemoveReqDTO) {
        return manager.removeByParams(logRemoveReqDTO);
    }
}
