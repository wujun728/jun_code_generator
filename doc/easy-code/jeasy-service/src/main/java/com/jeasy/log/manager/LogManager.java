package com.jeasy.log.manager;

import com.google.common.collect.Lists;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.manager.impl.BaseManagerImpl;
import com.jeasy.common.Func;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.object.MapKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.log.biz.LogBiz;
import com.jeasy.log.dao.LogDAO;
import com.jeasy.log.dto.*;
import com.jeasy.log.entity.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 日志 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
@Component
public class LogManager extends BaseManagerImpl<LogDAO, LogEntity, LogDTO> {

    public static LogManager me() {
        return SpringContextHolder.getBean(LogManager.class);
    }

    public List<LogListResDTO> list(final LogListReqDTO logListReqDTO) {
        LogDTO paramsDTO = LogBiz.me().buildListParamsDTO(logListReqDTO);
        List<LogDTO> logDTOList = super.findList(paramsDTO);

        return LogBiz.me().transferLogListResDTOs(logDTOList);
    }

    public List<LogListResDTO> listByVersion1(final LogListReqDTO logListReqDTO) {
        return list(logListReqDTO);
    }

    public List<LogListResDTO> listByVersion2(final LogListReqDTO logListReqDTO) {
        return list(logListReqDTO);
    }

    public List<LogListResDTO> listByVersion3(final LogListReqDTO logListReqDTO) {
        return list(logListReqDTO);
    }

    public LogListResDTO listOne(final LogListReqDTO logListReqDTO) {
        LogDTO paramsDTO = LogBiz.me().buildListParamsDTO(logListReqDTO);
        LogDTO logDTO = super.findOne(paramsDTO);

        return LogBiz.me().transferLogListResDTO(logDTO);
    }

    public PageDTO<LogPageResDTO> pagination(final LogPageReqDTO logPageReqDTO, final Integer current, final Integer size) {
        LogDTO paramsDTO = LogBiz.me().buildPageParamsDTO(logPageReqDTO);
        PageDTO<LogDTO> logDTOPage = super.findPage(paramsDTO, current, size);

        return LogBiz.me().transferLogPageResDTOPage(logDTOPage);
    }

    public Boolean add(final LogAddReqDTO logAddReqDTO) {
        LogBiz.me().validateLogAddReqDTO(logAddReqDTO);
        return super.saveDTO(LogBiz.me().buildAddLogDTO(logAddReqDTO));
    }

    public Boolean addAllColumn(final LogAddReqDTO logAddReqDTO) {
        LogBiz.me().validateLogAddReqDTO(logAddReqDTO);
        return super.saveAllColumn(LogBiz.me().buildAddLogDTO(logAddReqDTO));
    }

    public Boolean addBatchAllColumn(final List<LogAddReqDTO> logAddReqDTOList) {
        LogBiz.me().validateLogAddReqDTOList(logAddReqDTOList);
        return super.saveBatchAllColumn(LogBiz.me().buildAddBatchLogDTO(logAddReqDTOList));
    }

    public LogShowResDTO show(final Long id) {
        LogDTO logDTO = super.findById(id);
        return LogBiz.me().transferLogShowResDTO(logDTO);
    }

    public List<LogShowResDTO> showByIds(final List<Long> ids) {
        List<LogDTO> logDTOList = super.findBatchIds(ids);
        return LogBiz.me().transferLogShowResDTOList(logDTOList);
    }

    public Boolean modify(final LogModifyReqDTO logModifyReqDTO) {
        LogBiz.me().validateLogModifyReqDTO(logModifyReqDTO);
        return super.modifyById(LogBiz.me().buildModifyLogDTO(logModifyReqDTO));
    }

    public Boolean modifyAllColumn(final LogModifyReqDTO logModifyReqDTO) {
        LogBiz.me().validateLogModifyReqDTO(logModifyReqDTO);
        return super.modifyAllColumnById(LogBiz.me().buildModifyLogDTO(logModifyReqDTO));
    }

    public Boolean removeByParams(final LogRemoveReqDTO logRemoveReqDTO) {
        LogBiz.me().validateLogRemoveReqDTO(logRemoveReqDTO);
        return super.remove(LogBiz.me().buildRemoveLogDTO(logRemoveReqDTO));
    }

    @Override
    protected List<LogDTO> entityToDTOList(final List<LogEntity> logEntityList) {
        List<LogDTO> logDTOList = null;
        if (!Func.isEmpty(logEntityList)) {
            logDTOList = Lists.newArrayList();
            for (LogEntity logEntity : logEntityList) {
                logDTOList.add(entityToDTO(logEntity));
            }
        }
        return logDTOList;
    }

    @Override
    protected LogDTO entityToDTO(final LogEntity logEntity) {
        LogDTO logDTO = null;
        if (!Func.isEmpty(logEntity)) {
            logDTO = new LogDTO();
            BeanKit.copyProperties(logEntity, logDTO);
        }
        return logDTO;
    }

    @Override
    protected List<LogEntity> dtoToEntityList(final List<LogDTO> logDTOList) {
        List<LogEntity> logEntityList = null;
        if (!Func.isEmpty(logDTOList)) {
            logEntityList = Lists.newArrayList();
            for (LogDTO logDTO : logDTOList) {
                logEntityList.add(dtoToEntity(logDTO));
            }
        }
        return logEntityList;
    }

    @Override
    protected LogEntity dtoToEntity(final LogDTO logDTO) {
        LogEntity logEntity = null;
        if (!Func.isEmpty(logDTO)) {
            logEntity = new LogEntity();
            BeanKit.copyProperties(logDTO, logEntity);
        }
        return logEntity;
    }

    @Override
    protected LogEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new LogEntity();
        }
        return (LogEntity) MapKit.toBean(map, LogEntity.class);
    }

    @Override
    protected LogDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new LogDTO();
        }
        return (LogDTO) MapKit.toBean(map, LogDTO.class);
    }
}
