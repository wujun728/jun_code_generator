package com.jeasy.log.biz;

import com.google.common.collect.Lists;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.object.AbstractConverter;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.exception.MessageException;
import com.jeasy.log.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日志 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
@Component
public class LogBiz {

    /**
     * this is a converter demo only for BeanKit.copyProperties
     *
     * @see BeanKit#copyProperties(Object source, Object target, AbstractConverter... converters)
     */
    private static final AbstractConverter<String, String> DEMO_CONVERTER = new AbstractConverter<String, String>("filed1", "filed2") {
        @Override
        public String convert(final String val) {
            return val;
        }
    };

    public static LogBiz me() {
        return SpringContextHolder.getBean(LogBiz.class);
    }

    public LogDTO buildListParamsDTO(LogListReqDTO logListReqDTO) {
        LogDTO paramsDTO = new LogDTO();
        if (!Func.isEmpty(logListReqDTO)) {
            BeanKit.copyProperties(logListReqDTO, paramsDTO, DEMO_CONVERTER);
        }
        return paramsDTO;
    }

    public List<LogListResDTO> transferLogListResDTOs(List<LogDTO> logDTOList) {
        if (!Func.isEmpty(logDTOList)) {
            List<LogListResDTO> items = Lists.newArrayList();
            for (LogDTO logDTO : logDTOList) {
                LogListResDTO logListResDTO = new LogListResDTO();
                BeanKit.copyProperties(logDTO, logListResDTO, DEMO_CONVERTER);
                items.add(logListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public LogListResDTO transferLogListResDTO(LogDTO logDTO) {
        if (!Func.isEmpty(logDTO)) {
            LogListResDTO logListResDTO = new LogListResDTO();
            BeanKit.copyProperties(logDTO, logListResDTO, DEMO_CONVERTER);
            return logListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public LogDTO buildPageParamsDTO(LogPageReqDTO logPageReqDTO) {
        LogDTO paramsDTO = new LogDTO();
        if (!Func.isEmpty(logPageReqDTO)) {
            BeanKit.copyProperties(logPageReqDTO, paramsDTO, DEMO_CONVERTER);
        }
        return paramsDTO;
    }

    public PageDTO<LogPageResDTO> transferLogPageResDTOPage(PageDTO<LogDTO> logDTOPage) {
        if (Func.isNotEmpty(logDTOPage) && Func.isNotEmpty(logDTOPage.getRecords())) {
            List<LogPageResDTO> logPageResDTOs = Lists.newArrayList();
            for (LogDTO logDTO : logDTOPage.getRecords()) {
                LogPageResDTO logPageResDTO = new LogPageResDTO();
                BeanKit.copyProperties(logDTO, logPageResDTO, DEMO_CONVERTER);
                logPageResDTOs.add(logPageResDTO);
            }

            PageDTO<LogPageResDTO> logPageResDTOPage = new PageDTO<>();
            logPageResDTOPage.setRecords(logPageResDTOs);
            logPageResDTOPage.setTotal(logDTOPage.getTotal());
            return logPageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public void validateLogAddReqDTO(LogAddReqDTO logAddReqDTO) {
        if (Func.isEmpty(logAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public LogDTO buildAddLogDTO(LogAddReqDTO logAddReqDTO) {
        LogDTO logDTO = new LogDTO();
        BeanKit.copyProperties(logAddReqDTO, logDTO, DEMO_CONVERTER);
        return logDTO;
    }

    public void validateLogAddReqDTOList(List<LogAddReqDTO> logAddReqDTOList) {
        if (Func.isEmpty(logAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public List<LogDTO> buildAddBatchLogDTO(List<LogAddReqDTO> logAddReqDTOList) {
        List<LogDTO> logDTOList = Lists.newArrayList();
        for (LogAddReqDTO logAddReqDTO : logAddReqDTOList) {
            LogDTO logDTO = new LogDTO();
            BeanKit.copyProperties(logAddReqDTO, logDTO, DEMO_CONVERTER);
            logDTOList.add(logDTO);
        }
        return logDTOList;
    }

    public LogShowResDTO transferLogShowResDTO(LogDTO logDTO) {
        if (!Func.isEmpty(logDTO)) {
            LogShowResDTO logShowResDTO = new LogShowResDTO();
            BeanKit.copyProperties(logDTO, logShowResDTO, DEMO_CONVERTER);
            return logShowResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<LogShowResDTO> transferLogShowResDTOList(List<LogDTO> logDTOList) {
        if (!Func.isEmpty(logDTOList)) {
            List<LogShowResDTO> logShowResDTOList = Lists.newArrayList();
            for (LogDTO logDTO : logDTOList) {
                LogShowResDTO logShowResDTO = new LogShowResDTO();
                BeanKit.copyProperties(logDTO, logShowResDTO, DEMO_CONVERTER);
                logShowResDTOList.add(logShowResDTO);
            }
            return logShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public void validateLogModifyReqDTO(LogModifyReqDTO logModifyReqDTO) {
        if (Func.isEmpty(logModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public LogDTO buildModifyLogDTO(LogModifyReqDTO logModifyReqDTO) {
        LogDTO logDTO = new LogDTO();
        BeanKit.copyProperties(logModifyReqDTO, logDTO, DEMO_CONVERTER);
        return logDTO;
    }

    public void validateLogRemoveReqDTO(LogRemoveReqDTO logRemoveReqDTO) {
        if (Func.isEmpty(logRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public LogDTO buildRemoveLogDTO(LogRemoveReqDTO logRemoveReqDTO) {
        LogDTO logParamsDTO = new LogDTO();
        BeanKit.copyProperties(logRemoveReqDTO, logParamsDTO, DEMO_CONVERTER);
        return logParamsDTO;
    }
}
