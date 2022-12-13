package com.jeasy.fileattach.biz;

import com.google.common.collect.Lists;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.object.AbstractConverter;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.exception.MessageException;
import com.jeasy.fileattach.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件附件 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
@Component
public class FileAttachBiz {

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

    public static FileAttachBiz me() {
        return SpringContextHolder.getBean(FileAttachBiz.class);
    }

    public FileAttachDTO buildListParamsDTO(FileAttachListReqDTO fileAttachListReqDTO) {
        FileAttachDTO paramsDTO = new FileAttachDTO();
        if (!Func.isEmpty(fileAttachListReqDTO)) {
            BeanKit.copyProperties(fileAttachListReqDTO, paramsDTO, DEMO_CONVERTER);
        }
        return paramsDTO;
    }

    public List<FileAttachListResDTO> transferFileAttachListResDTOs(List<FileAttachDTO> fileAttachDTOList) {
        if (!Func.isEmpty(fileAttachDTOList)) {
            List<FileAttachListResDTO> items = Lists.newArrayList();
            for (FileAttachDTO fileAttachDTO : fileAttachDTOList) {
                FileAttachListResDTO fileAttachListResDTO = new FileAttachListResDTO();
                BeanKit.copyProperties(fileAttachDTO, fileAttachListResDTO, DEMO_CONVERTER);
                items.add(fileAttachListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public FileAttachListResDTO transferFileAttachListResDTO(FileAttachDTO fileAttachDTO) {
        if (!Func.isEmpty(fileAttachDTO)) {
            FileAttachListResDTO fileAttachListResDTO = new FileAttachListResDTO();
            BeanKit.copyProperties(fileAttachDTO, fileAttachListResDTO, DEMO_CONVERTER);
            return fileAttachListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public FileAttachDTO buildPageParamsDTO(FileAttachPageReqDTO fileAttachPageReqDTO) {
        FileAttachDTO paramsDTO = new FileAttachDTO();
        if (!Func.isEmpty(fileAttachPageReqDTO)) {
            BeanKit.copyProperties(fileAttachPageReqDTO, paramsDTO, DEMO_CONVERTER);
        }
        return paramsDTO;
    }

    public PageDTO<FileAttachPageResDTO> transferFileAttachPageResDTOPage(PageDTO<FileAttachDTO> fileAttachDTOPage) {
        if (Func.isNotEmpty(fileAttachDTOPage) && Func.isNotEmpty(fileAttachDTOPage.getRecords())) {
            List<FileAttachPageResDTO> fileAttachPageResDTOs = Lists.newArrayList();
            for (FileAttachDTO fileAttachDTO : fileAttachDTOPage.getRecords()) {
                FileAttachPageResDTO fileAttachPageResDTO = new FileAttachPageResDTO();
                BeanKit.copyProperties(fileAttachDTO, fileAttachPageResDTO, DEMO_CONVERTER);
                fileAttachPageResDTOs.add(fileAttachPageResDTO);
            }

            PageDTO<FileAttachPageResDTO> fileAttachPageResDTOPage = new PageDTO<>();
            fileAttachPageResDTOPage.setRecords(fileAttachPageResDTOs);
            fileAttachPageResDTOPage.setTotal(fileAttachDTOPage.getTotal());
            return fileAttachPageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public void validateFileAttachAddReqDTO(FileAttachAddReqDTO fileAttachAddReqDTO) {
        if (Func.isEmpty(fileAttachAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public FileAttachDTO buildAddFileAttachDTO(FileAttachAddReqDTO fileAttachAddReqDTO) {
        FileAttachDTO fileAttachDTO = new FileAttachDTO();
        BeanKit.copyProperties(fileAttachAddReqDTO, fileAttachDTO, DEMO_CONVERTER);
        return fileAttachDTO;
    }

    public void validateFileAttachAddReqDTOList(List<FileAttachAddReqDTO> fileAttachAddReqDTOList) {
        if (Func.isEmpty(fileAttachAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public List<FileAttachDTO> buildAddBatchFileAttachDTO(List<FileAttachAddReqDTO> fileAttachAddReqDTOList) {
        List<FileAttachDTO> fileAttachDTOList = Lists.newArrayList();
        for (FileAttachAddReqDTO fileAttachAddReqDTO : fileAttachAddReqDTOList) {
            FileAttachDTO fileAttachDTO = new FileAttachDTO();
            BeanKit.copyProperties(fileAttachAddReqDTO, fileAttachDTO, DEMO_CONVERTER);
            fileAttachDTOList.add(fileAttachDTO);
        }
        return fileAttachDTOList;
    }

    public FileAttachShowResDTO transferFileAttachShowResDTO(FileAttachDTO fileAttachDTO) {
        if (!Func.isEmpty(fileAttachDTO)) {
            FileAttachShowResDTO fileAttachShowResDTO = new FileAttachShowResDTO();
            BeanKit.copyProperties(fileAttachDTO, fileAttachShowResDTO, DEMO_CONVERTER);
            return fileAttachShowResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<FileAttachShowResDTO> transferFileAttachShowResDTOList(List<FileAttachDTO> fileAttachDTOList) {
        if (!Func.isEmpty(fileAttachDTOList)) {
            List<FileAttachShowResDTO> fileAttachShowResDTOList = Lists.newArrayList();
            for (FileAttachDTO fileAttachDTO : fileAttachDTOList) {
                FileAttachShowResDTO fileAttachShowResDTO = new FileAttachShowResDTO();
                BeanKit.copyProperties(fileAttachDTO, fileAttachShowResDTO, DEMO_CONVERTER);
                fileAttachShowResDTOList.add(fileAttachShowResDTO);
            }
            return fileAttachShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public void validateFileAttachModifyReqDTO(FileAttachModifyReqDTO fileAttachModifyReqDTO) {
        if (Func.isEmpty(fileAttachModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public FileAttachDTO buildModifyFileAttachDTO(FileAttachModifyReqDTO fileAttachModifyReqDTO) {
        FileAttachDTO fileAttachDTO = new FileAttachDTO();
        BeanKit.copyProperties(fileAttachModifyReqDTO, fileAttachDTO, DEMO_CONVERTER);
        return fileAttachDTO;
    }

    public void validateFileAttachRemoveReqDTO(FileAttachRemoveReqDTO fileAttachRemoveReqDTO) {
        if (Func.isEmpty(fileAttachRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public FileAttachDTO buildRemoveFileAttachDTO(FileAttachRemoveReqDTO fileAttachRemoveReqDTO) {
        FileAttachDTO fileAttachParamsDTO = new FileAttachDTO();
        BeanKit.copyProperties(fileAttachRemoveReqDTO, fileAttachParamsDTO, DEMO_CONVERTER);
        return fileAttachParamsDTO;
    }
}
