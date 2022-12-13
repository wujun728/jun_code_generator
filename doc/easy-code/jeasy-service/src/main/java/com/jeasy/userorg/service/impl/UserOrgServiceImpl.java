package com.jeasy.userorg.service.impl;

import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.service.impl.BaseServiceImpl;
import com.jeasy.userorg.dto.*;
import com.jeasy.userorg.entity.UserOrgEntity;
import com.jeasy.userorg.manager.UserOrgManager;
import com.jeasy.userorg.service.UserOrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户机构 ServiceImpl
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Service
public class UserOrgServiceImpl extends BaseServiceImpl<UserOrgManager, UserOrgEntity, UserOrgDTO> implements UserOrgService {

    @Override
    public List<UserOrgListResDTO> list(final UserOrgListReqDTO userorgListReqDTO) {
        return manager.list(userorgListReqDTO);
    }

    @Override
    public List<UserOrgListResDTO> listByVersion1(final UserOrgListReqDTO userorgListReqDTO) {
        return manager.listByVersion1(userorgListReqDTO);
    }

    @Override
    public List<UserOrgListResDTO> listByVersion2(final UserOrgListReqDTO userorgListReqDTO) {
        return manager.listByVersion2(userorgListReqDTO);
    }

    @Override
    public List<UserOrgListResDTO> listByVersion3(final UserOrgListReqDTO userorgListReqDTO) {
        return manager.listByVersion3(userorgListReqDTO);
    }

    @Override
    public UserOrgListResDTO listOne(final UserOrgListReqDTO userorgListReqDTO) {
        return manager.listOne(userorgListReqDTO);
    }

    @Override
    public PageDTO<UserOrgPageResDTO> pagination(final UserOrgPageReqDTO userorgPageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(userorgPageReqDTO, current, size);
    }

    @Override
    public Boolean add(final UserOrgAddReqDTO userorgAddReqDTO) {
        return manager.add(userorgAddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final UserOrgAddReqDTO userorgAddReqDTO) {
        return manager.addAllColumn(userorgAddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<UserOrgAddReqDTO> userorgAddReqDTOList) {
        return manager.addBatchAllColumn(userorgAddReqDTOList);
    }

    @Override
    public UserOrgShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<UserOrgShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final UserOrgModifyReqDTO userorgModifyReqDTO) {
        return manager.modify(userorgModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final UserOrgModifyReqDTO userorgModifyReqDTO) {
        return manager.modifyAllColumn(userorgModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final UserOrgRemoveReqDTO userorgRemoveReqDTO) {
        return manager.removeByParams(userorgRemoveReqDTO);
    }
}
