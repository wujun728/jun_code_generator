package com.jeasy.fileattach.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.fileattach.dto.*;
import com.jeasy.fileattach.entity.FileAttachEntity;
import com.jeasy.fileattach.manager.FileAttachManager;
import com.jeasy.fileattach.service.FileAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件附件 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
@Service
public class FileAttachServiceImpl extends BaseServiceImpl<FileAttachManager, FileAttachEntity, FileAttachDTO> implements FileAttachService {

    @Override
    public List<FileAttachListResDTO> list(final FileAttachListReqDTO fileAttachListReqDTO) {
        return manager.list(fileAttachListReqDTO);
    }

    @Override
    public List<FileAttachListResDTO> listByVersion1(final FileAttachListReqDTO fileAttachListReqDTO) {
        return manager.listByVersion1(fileAttachListReqDTO);
    }

    @Override
    public List<FileAttachListResDTO> listByVersion2(final FileAttachListReqDTO fileAttachListReqDTO) {
        return manager.listByVersion2(fileAttachListReqDTO);
    }

    @Override
    public List<FileAttachListResDTO> listByVersion3(final FileAttachListReqDTO fileAttachListReqDTO) {
        return manager.listByVersion3(fileAttachListReqDTO);
    }

    @Override
    public FileAttachListResDTO listOne(final FileAttachListReqDTO fileAttachListReqDTO) {
        return manager.listOne(fileAttachListReqDTO);
    }

    @Override
    public PageDTO<FileAttachPageResDTO> pagination(final FileAttachPageReqDTO fileAttachPageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(fileAttachPageReqDTO, current, size);
    }

    @Override
    public Boolean add(final FileAttachAddReqDTO fileAttachAddReqDTO) {
        return manager.add(fileAttachAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final FileAttachAddReqDTO fileAttachAddReqDTO) {
        return manager.addAllColumn(fileAttachAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<FileAttachAddReqDTO> fileAttachAddReqDTOList) {
        return manager.addBatchAllColumn(fileAttachAddReqDTOList);
    }

    @Override
    public FileAttachShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<FileAttachShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final FileAttachModifyReqDTO fileAttachModifyReqDTO) {
        return manager.modify(fileAttachModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final FileAttachModifyReqDTO fileAttachModifyReqDTO) {
        return manager.modifyAllColumn(fileAttachModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final FileAttachRemoveReqDTO fileAttachRemoveReqDTO) {
        return manager.removeByParams(fileAttachRemoveReqDTO);
    }
}
