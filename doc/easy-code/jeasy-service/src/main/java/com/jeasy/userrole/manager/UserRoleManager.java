package com.jeasy.userrole.manager;

import com.google.common.collect.Lists;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.manager.impl.BaseManagerImpl;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.object.AbstractConverter;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.object.MapKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.exception.MessageException;
import com.jeasy.userrole.dao.UserRoleDAO;
import com.jeasy.userrole.dto.*;
import com.jeasy.userrole.entity.UserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 用户角色 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class UserRoleManager extends BaseManagerImpl<UserRoleDAO, UserRoleEntity, UserRoleDTO> {

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

    public static UserRoleManager me() {
        return SpringContextHolder.getBean(UserRoleManager.class);
    }

    public List<UserRoleListResDTO> list(final UserRoleListReqDTO userroleListReqDTO) {
        UserRoleDTO userroleParamsDTO = new UserRoleDTO();
        if (!Func.isEmpty(userroleListReqDTO)) {
            BeanKit.copyProperties(userroleListReqDTO, userroleParamsDTO, DEMO_CONVERTER);
        }

        List<UserRoleDTO> userroleDTOList = super.findList(userroleParamsDTO);

        if (!Func.isEmpty(userroleDTOList)) {
            List<UserRoleListResDTO> items = Lists.newArrayList();
            for (UserRoleDTO userroleDTO : userroleDTOList) {
                UserRoleListResDTO userroleListResDTO = new UserRoleListResDTO();
                BeanKit.copyProperties(userroleDTO, userroleListResDTO, DEMO_CONVERTER);
                items.add(userroleListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<UserRoleListResDTO> listByVersion1(final UserRoleListReqDTO userroleListReqDTO) {
        return list(userroleListReqDTO);
    }

    public List<UserRoleListResDTO> listByVersion2(final UserRoleListReqDTO userroleListReqDTO) {
        return list(userroleListReqDTO);
    }

    public List<UserRoleListResDTO> listByVersion3(final UserRoleListReqDTO userroleListReqDTO) {
        return list(userroleListReqDTO);
    }

    public UserRoleListResDTO listOne(final UserRoleListReqDTO userroleListReqDTO) {
        UserRoleDTO userroleParamsDTO = new UserRoleDTO();
        if (!Func.isEmpty(userroleListReqDTO)) {
            BeanKit.copyProperties(userroleListReqDTO, userroleParamsDTO, DEMO_CONVERTER);
        }

        UserRoleDTO userroleDTO = super.findOne(userroleParamsDTO);
        if (!Func.isEmpty(userroleDTO)) {
            UserRoleListResDTO userroleListResDTO = new UserRoleListResDTO();
            BeanKit.copyProperties(userroleDTO, userroleListResDTO, DEMO_CONVERTER);
            return userroleListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<UserRolePageResDTO> pagination(final UserRolePageReqDTO userrolePageReqDTO, final Integer current, final Integer size) {
        UserRoleDTO userroleParamsDTO = new UserRoleDTO();
        if (!Func.isEmpty(userrolePageReqDTO)) {
            BeanKit.copyProperties(userrolePageReqDTO, userroleParamsDTO, DEMO_CONVERTER);
        }

        PageDTO<UserRoleDTO> userroleDTOPage = super.findPage(userroleParamsDTO, current, size);

        if (Func.isNotEmpty(userroleDTOPage) && Func.isNotEmpty(userroleDTOPage.getRecords())) {
            List<UserRolePageResDTO> userrolePageResDTOs = Lists.newArrayList();
            for (UserRoleDTO userroleDTO : userroleDTOPage.getRecords()) {
                UserRolePageResDTO userrolePageResDTO = new UserRolePageResDTO();
                BeanKit.copyProperties(userroleDTO, userrolePageResDTO, DEMO_CONVERTER);
                userrolePageResDTOs.add(userrolePageResDTO);
            }

            PageDTO<UserRolePageResDTO> userrolePageResDTOPage = new PageDTO<>();
            userrolePageResDTOPage.setRecords(userrolePageResDTOs);
            userrolePageResDTOPage.setTotal(userroleDTOPage.getTotal());
            return userrolePageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean add(final UserRoleAddReqDTO userroleAddReqDTO) {
        if (Func.isEmpty(userroleAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserRoleDTO userroleDTO = new UserRoleDTO();
        BeanKit.copyProperties(userroleAddReqDTO, userroleDTO, DEMO_CONVERTER);
        return super.saveDTO(userroleDTO);
    }

    public Boolean addAllColumn(final UserRoleAddReqDTO userroleAddReqDTO) {
        if (Func.isEmpty(userroleAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserRoleDTO userroleDTO = new UserRoleDTO();
        BeanKit.copyProperties(userroleAddReqDTO, userroleDTO, DEMO_CONVERTER);
        return super.saveAllColumn(userroleDTO);
    }

    public Boolean addBatchAllColumn(final List<UserRoleAddReqDTO> userroleAddReqDTOList) {
        if (Func.isEmpty(userroleAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        List<UserRoleDTO> userroleDTOList = Lists.newArrayList();
        for (UserRoleAddReqDTO userroleAddReqDTO : userroleAddReqDTOList) {
            UserRoleDTO userroleDTO = new UserRoleDTO();
            BeanKit.copyProperties(userroleAddReqDTO, userroleDTO, DEMO_CONVERTER);
            userroleDTOList.add(userroleDTO);
        }
        return super.saveBatchAllColumn(userroleDTOList);
    }

    public UserRoleShowResDTO show(final Long id) {
        UserRoleDTO userroleDTO = super.findById(id);

        if (!Func.isEmpty(userroleDTO)) {
            UserRoleShowResDTO userroleShowResDTO = new UserRoleShowResDTO();
            BeanKit.copyProperties(userroleDTO, userroleShowResDTO, DEMO_CONVERTER);
            return userroleShowResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<UserRoleShowResDTO> showByIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new MessageException(ModelResult.CODE_200, "集合不能为空且大小大于0");
        }

        List<UserRoleDTO> userroleDTOList = super.findBatchIds(ids);

        if (!Func.isEmpty(userroleDTOList)) {
            List<UserRoleShowResDTO> userroleShowResDTOList = Lists.newArrayList();
            for (UserRoleDTO userroleDTO : userroleDTOList) {
                UserRoleShowResDTO userroleShowResDTO = new UserRoleShowResDTO();
                BeanKit.copyProperties(userroleDTO, userroleShowResDTO, DEMO_CONVERTER);
                userroleShowResDTOList.add(userroleShowResDTO);
            }
            return userroleShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean modify(final UserRoleModifyReqDTO userroleModifyReqDTO) {
        if (Func.isEmpty(userroleModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
        UserRoleDTO userroleDTO = new UserRoleDTO();
        BeanKit.copyProperties(userroleModifyReqDTO, userroleDTO, DEMO_CONVERTER);
        return super.modifyById(userroleDTO);
    }

    public Boolean modifyAllColumn(final UserRoleModifyReqDTO userroleModifyReqDTO) {
        if (Func.isEmpty(userroleModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserRoleDTO userroleDTO = new UserRoleDTO();
        BeanKit.copyProperties(userroleModifyReqDTO, userroleDTO, DEMO_CONVERTER);
        return super.modifyAllColumnById(userroleDTO);
    }

    public Boolean removeByParams(final UserRoleRemoveReqDTO userroleRemoveReqDTO) {
        if (Func.isEmpty(userroleRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserRoleDTO userroleParamsDTO = new UserRoleDTO();
        BeanKit.copyProperties(userroleRemoveReqDTO, userroleParamsDTO, DEMO_CONVERTER);
        return super.remove(userroleParamsDTO);
    }


    @Override
    protected List<UserRoleDTO> entityToDTOList(final List<UserRoleEntity> userroleEntityList) {
        List<UserRoleDTO> userroleDTOList = null;
        if (!Func.isEmpty(userroleEntityList)) {
            userroleDTOList = Lists.newArrayList();
            for (UserRoleEntity userroleEntity : userroleEntityList) {
                userroleDTOList.add(entityToDTO(userroleEntity));
            }
        }
        return userroleDTOList;
    }

    @Override
    protected UserRoleDTO entityToDTO(final UserRoleEntity userroleEntity) {
        UserRoleDTO userroleDTO = null;
        if (!Func.isEmpty(userroleEntity)) {
            userroleDTO = new UserRoleDTO();
            BeanKit.copyProperties(userroleEntity, userroleDTO);
        }
        return userroleDTO;
    }

    @Override
    protected List<UserRoleEntity> dtoToEntityList(final List<UserRoleDTO> userroleDTOList) {
        List<UserRoleEntity> userroleEntityList = null;
        if (!Func.isEmpty(userroleDTOList)) {
            userroleEntityList = Lists.newArrayList();
            for (UserRoleDTO userroleDTO : userroleDTOList) {
                userroleEntityList.add(dtoToEntity(userroleDTO));
            }
        }
        return userroleEntityList;
    }

    @Override
    protected UserRoleEntity dtoToEntity(final UserRoleDTO userroleDTO) {
        UserRoleEntity userroleEntity = null;
        if (!Func.isEmpty(userroleDTO)) {
            userroleEntity = new UserRoleEntity();
            BeanKit.copyProperties(userroleDTO, userroleEntity);
        }
        return userroleEntity;
    }

    @Override
    protected UserRoleEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new UserRoleEntity();
        }
        return (UserRoleEntity) MapKit.toBean(map, UserRoleEntity.class);
    }

    @Override
    protected UserRoleDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new UserRoleDTO();
        }
        return (UserRoleDTO) MapKit.toBean(map, UserRoleDTO.class);
    }
}
