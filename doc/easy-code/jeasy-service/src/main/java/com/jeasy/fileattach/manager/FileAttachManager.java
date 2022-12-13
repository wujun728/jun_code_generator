package com.jeasy.fileattach.manager;

import com.google.common.collect.Lists;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.manager.impl.BaseManagerImpl;
import com.jeasy.common.Func;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.object.MapKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.fileattach.biz.FileAttachBiz;
import com.jeasy.fileattach.dao.FileAttachDAO;
import com.jeasy.fileattach.dto.*;
import com.jeasy.fileattach.entity.FileAttachEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 文件附件 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
@Component
public class FileAttachManager extends BaseManagerImpl<FileAttachDAO, FileAttachEntity, FileAttachDTO> {

    public static FileAttachManager me() {
        return SpringContextHolder.getBean(FileAttachManager.class);
    }

    public List<FileAttachListResDTO> list(final FileAttachListReqDTO fileAttachListReqDTO) {
        FileAttachDTO paramsDTO = FileAttachBiz.me().buildListParamsDTO(fileAttachListReqDTO);
        List<FileAttachDTO> fileAttachDTOList = super.findList(paramsDTO);

        return FileAttachBiz.me().transferFileAttachListResDTOs(fileAttachDTOList);
    }

    public List<FileAttachListResDTO> listByVersion1(final FileAttachListReqDTO fileAttachListReqDTO) {
        return list(fileAttachListReqDTO);
    }

    public List<FileAttachListResDTO> listByVersion2(final FileAttachListReqDTO fileAttachListReqDTO) {
        return list(fileAttachListReqDTO);
    }

    public List<FileAttachListResDTO> listByVersion3(final FileAttachListReqDTO fileAttachListReqDTO) {
        return list(fileAttachListReqDTO);
    }

    public FileAttachListResDTO listOne(final FileAttachListReqDTO fileAttachListReqDTO) {
        FileAttachDTO paramsDTO = FileAttachBiz.me().buildListParamsDTO(fileAttachListReqDTO);
        FileAttachDTO fileAttachDTO = super.findOne(paramsDTO);

        return FileAttachBiz.me().transferFileAttachListResDTO(fileAttachDTO);
    }

    public PageDTO<FileAttachPageResDTO> pagination(final FileAttachPageReqDTO fileAttachPageReqDTO, final Integer current, final Integer size) {
        FileAttachDTO paramsDTO = FileAttachBiz.me().buildPageParamsDTO(fileAttachPageReqDTO);
        PageDTO<FileAttachDTO> fileAttachDTOPage = super.findPage(paramsDTO, current, size);

        return FileAttachBiz.me().transferFileAttachPageResDTOPage(fileAttachDTOPage);
    }

    public Boolean add(final FileAttachAddReqDTO fileAttachAddReqDTO) {
        FileAttachBiz.me().validateFileAttachAddReqDTO(fileAttachAddReqDTO);
        return super.saveDTO(FileAttachBiz.me().buildAddFileAttachDTO(fileAttachAddReqDTO));
    }

    public Boolean addAllColumn(final FileAttachAddReqDTO fileAttachAddReqDTO) {
        FileAttachBiz.me().validateFileAttachAddReqDTO(fileAttachAddReqDTO);
        return super.saveAllColumn(FileAttachBiz.me().buildAddFileAttachDTO(fileAttachAddReqDTO));
    }

    public Boolean addBatchAllColumn(final List<FileAttachAddReqDTO> fileAttachAddReqDTOList) {
        FileAttachBiz.me().validateFileAttachAddReqDTOList(fileAttachAddReqDTOList);
        return super.saveBatchAllColumn(FileAttachBiz.me().buildAddBatchFileAttachDTO(fileAttachAddReqDTOList));
    }

    public FileAttachShowResDTO show(final Long id) {
        FileAttachDTO fileAttachDTO = super.findById(id);
        return FileAttachBiz.me().transferFileAttachShowResDTO(fileAttachDTO);
    }

    public List<FileAttachShowResDTO> showByIds(final List<Long> ids) {
        List<FileAttachDTO> fileAttachDTOList = super.findBatchIds(ids);
        return FileAttachBiz.me().transferFileAttachShowResDTOList(fileAttachDTOList);
    }

    public Boolean modify(final FileAttachModifyReqDTO fileAttachModifyReqDTO) {
        FileAttachBiz.me().validateFileAttachModifyReqDTO(fileAttachModifyReqDTO);
        return super.modifyById(FileAttachBiz.me().buildModifyFileAttachDTO(fileAttachModifyReqDTO));
    }

    public Boolean modifyAllColumn(final FileAttachModifyReqDTO fileAttachModifyReqDTO) {
        FileAttachBiz.me().validateFileAttachModifyReqDTO(fileAttachModifyReqDTO);
        return super.modifyAllColumnById(FileAttachBiz.me().buildModifyFileAttachDTO(fileAttachModifyReqDTO));
    }

    public Boolean removeByParams(final FileAttachRemoveReqDTO fileAttachRemoveReqDTO) {
        FileAttachBiz.me().validateFileAttachRemoveReqDTO(fileAttachRemoveReqDTO);
        return super.remove(FileAttachBiz.me().buildRemoveFileAttachDTO(fileAttachRemoveReqDTO));
    }

    @Override
    protected List<FileAttachDTO> entityToDTOList(final List<FileAttachEntity> fileAttachEntityList) {
        List<FileAttachDTO> fileAttachDTOList = null;
        if (!Func.isEmpty(fileAttachEntityList)) {
            fileAttachDTOList = Lists.newArrayList();
            for (FileAttachEntity fileAttachEntity : fileAttachEntityList) {
                fileAttachDTOList.add(entityToDTO(fileAttachEntity));
            }
        }
        return fileAttachDTOList;
    }

    @Override
    protected FileAttachDTO entityToDTO(final FileAttachEntity fileAttachEntity) {
        FileAttachDTO fileAttachDTO = null;
        if (!Func.isEmpty(fileAttachEntity)) {
            fileAttachDTO = new FileAttachDTO();
            BeanKit.copyProperties(fileAttachEntity, fileAttachDTO);
        }
        return fileAttachDTO;
    }

    @Override
    protected List<FileAttachEntity> dtoToEntityList(final List<FileAttachDTO> fileAttachDTOList) {
        List<FileAttachEntity> fileAttachEntityList = null;
        if (!Func.isEmpty(fileAttachDTOList)) {
            fileAttachEntityList = Lists.newArrayList();
            for (FileAttachDTO fileAttachDTO : fileAttachDTOList) {
                fileAttachEntityList.add(dtoToEntity(fileAttachDTO));
            }
        }
        return fileAttachEntityList;
    }

    @Override
    protected FileAttachEntity dtoToEntity(final FileAttachDTO fileAttachDTO) {
        FileAttachEntity fileAttachEntity = null;
        if (!Func.isEmpty(fileAttachDTO)) {
            fileAttachEntity = new FileAttachEntity();
            BeanKit.copyProperties(fileAttachDTO, fileAttachEntity);
        }
        return fileAttachEntity;
    }

    @Override
    protected FileAttachEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new FileAttachEntity();
        }
        return (FileAttachEntity) MapKit.toBean(map, FileAttachEntity.class);
    }

    @Override
    protected FileAttachDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new FileAttachDTO();
        }
        return (FileAttachDTO) MapKit.toBean(map, FileAttachDTO.class);
    }
}
