package com.jeasy.userorg.manager;

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
import com.jeasy.userorg.dao.UserOrgDAO;
import com.jeasy.userorg.dto.*;
import com.jeasy.userorg.entity.UserOrgEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 用户机构 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class UserOrgManager extends BaseManagerImpl<UserOrgDAO, UserOrgEntity, UserOrgDTO> {

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

    public static UserOrgManager me() {
        return SpringContextHolder.getBean(UserOrgManager.class);
    }

    public List<UserOrgListResDTO> list(final UserOrgListReqDTO userorgListReqDTO) {
        UserOrgDTO userorgParamsDTO = new UserOrgDTO();
        if (!Func.isEmpty(userorgListReqDTO)) {
            BeanKit.copyProperties(userorgListReqDTO, userorgParamsDTO, DEMO_CONVERTER);
        }

        List<UserOrgDTO> userorgDTOList = super.findList(userorgParamsDTO);

        if (!Func.isEmpty(userorgDTOList)) {
            List<UserOrgListResDTO> items = Lists.newArrayList();
            for (UserOrgDTO userorgDTO : userorgDTOList) {
                UserOrgListResDTO userorgListResDTO = new UserOrgListResDTO();
                BeanKit.copyProperties(userorgDTO, userorgListResDTO, DEMO_CONVERTER);
                items.add(userorgListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<UserOrgListResDTO> listByVersion1(final UserOrgListReqDTO userorgListReqDTO) {
        return list(userorgListReqDTO);
    }

    public List<UserOrgListResDTO> listByVersion2(final UserOrgListReqDTO userorgListReqDTO) {
        return list(userorgListReqDTO);
    }

    public List<UserOrgListResDTO> listByVersion3(final UserOrgListReqDTO userorgListReqDTO) {
        return list(userorgListReqDTO);
    }

    public UserOrgListResDTO listOne(final UserOrgListReqDTO userorgListReqDTO) {
        UserOrgDTO userorgParamsDTO = new UserOrgDTO();
        if (!Func.isEmpty(userorgListReqDTO)) {
            BeanKit.copyProperties(userorgListReqDTO, userorgParamsDTO, DEMO_CONVERTER);
        }

        UserOrgDTO userorgDTO = super.findOne(userorgParamsDTO);
        if (!Func.isEmpty(userorgDTO)) {
            UserOrgListResDTO userorgListResDTO = new UserOrgListResDTO();
            BeanKit.copyProperties(userorgDTO, userorgListResDTO, DEMO_CONVERTER);
            return userorgListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<UserOrgPageResDTO> pagination(final UserOrgPageReqDTO userorgPageReqDTO, final Integer current, final Integer size) {
        UserOrgDTO userorgParamsDTO = new UserOrgDTO();
        if (!Func.isEmpty(userorgPageReqDTO)) {
            BeanKit.copyProperties(userorgPageReqDTO, userorgParamsDTO, DEMO_CONVERTER);
        }

        PageDTO<UserOrgDTO> userorgDTOPage = super.findPage(userorgParamsDTO, current, size);

        if (Func.isNotEmpty(userorgDTOPage) && Func.isNotEmpty(userorgDTOPage.getRecords())) {
            List<UserOrgPageResDTO> userorgPageResDTOs = Lists.newArrayList();
            for (UserOrgDTO userorgDTO : userorgDTOPage.getRecords()) {
                UserOrgPageResDTO userorgPageResDTO = new UserOrgPageResDTO();
                BeanKit.copyProperties(userorgDTO, userorgPageResDTO, DEMO_CONVERTER);
                userorgPageResDTOs.add(userorgPageResDTO);
            }

            PageDTO<UserOrgPageResDTO> userorgPageResDTOPage = new PageDTO<>();
            userorgPageResDTOPage.setRecords(userorgPageResDTOs);
            userorgPageResDTOPage.setTotal(userorgDTOPage.getTotal());
            return userorgPageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean add(final UserOrgAddReqDTO userorgAddReqDTO) {
        if (Func.isEmpty(userorgAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserOrgDTO userorgDTO = new UserOrgDTO();
        BeanKit.copyProperties(userorgAddReqDTO, userorgDTO, DEMO_CONVERTER);
        return super.saveDTO(userorgDTO);
    }

    public Boolean addAllColumn(final UserOrgAddReqDTO userorgAddReqDTO) {
        if (Func.isEmpty(userorgAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserOrgDTO userorgDTO = new UserOrgDTO();
        BeanKit.copyProperties(userorgAddReqDTO, userorgDTO, DEMO_CONVERTER);
        return super.saveAllColumn(userorgDTO);
    }

    public Boolean addBatchAllColumn(final List<UserOrgAddReqDTO> userorgAddReqDTOList) {
        if (Func.isEmpty(userorgAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        List<UserOrgDTO> userorgDTOList = Lists.newArrayList();
        for (UserOrgAddReqDTO userorgAddReqDTO : userorgAddReqDTOList) {
            UserOrgDTO userorgDTO = new UserOrgDTO();
            BeanKit.copyProperties(userorgAddReqDTO, userorgDTO, DEMO_CONVERTER);
            userorgDTOList.add(userorgDTO);
        }
        return super.saveBatchAllColumn(userorgDTOList);
    }

    public UserOrgShowResDTO show(final Long id) {
        UserOrgDTO userorgDTO = super.findById(id);

        if (!Func.isEmpty(userorgDTO)) {
            UserOrgShowResDTO userorgShowResDTO = new UserOrgShowResDTO();
            BeanKit.copyProperties(userorgDTO, userorgShowResDTO, DEMO_CONVERTER);
            return userorgShowResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<UserOrgShowResDTO> showByIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new MessageException(ModelResult.CODE_200, "集合不能为空且大小大于0");
        }

        List<UserOrgDTO> userorgDTOList = super.findBatchIds(ids);

        if (!Func.isEmpty(userorgDTOList)) {
            List<UserOrgShowResDTO> userorgShowResDTOList = Lists.newArrayList();
            for (UserOrgDTO userorgDTO : userorgDTOList) {
                UserOrgShowResDTO userorgShowResDTO = new UserOrgShowResDTO();
                BeanKit.copyProperties(userorgDTO, userorgShowResDTO, DEMO_CONVERTER);
                userorgShowResDTOList.add(userorgShowResDTO);
            }
            return userorgShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean modify(final UserOrgModifyReqDTO userorgModifyReqDTO) {
        if (Func.isEmpty(userorgModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
        UserOrgDTO userorgDTO = new UserOrgDTO();
        BeanKit.copyProperties(userorgModifyReqDTO, userorgDTO, DEMO_CONVERTER);
        return super.modifyById(userorgDTO);
    }

    public Boolean modifyAllColumn(final UserOrgModifyReqDTO userorgModifyReqDTO) {
        if (Func.isEmpty(userorgModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserOrgDTO userorgDTO = new UserOrgDTO();
        BeanKit.copyProperties(userorgModifyReqDTO, userorgDTO, DEMO_CONVERTER);
        return super.modifyAllColumnById(userorgDTO);
    }

    public Boolean removeByParams(final UserOrgRemoveReqDTO userorgRemoveReqDTO) {
        if (Func.isEmpty(userorgRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        UserOrgDTO userorgParamsDTO = new UserOrgDTO();
        BeanKit.copyProperties(userorgRemoveReqDTO, userorgParamsDTO, DEMO_CONVERTER);
        return super.remove(userorgParamsDTO);
    }


    @Override
    protected List<UserOrgDTO> entityToDTOList(final List<UserOrgEntity> userorgEntityList) {
        List<UserOrgDTO> userorgDTOList = null;
        if (!Func.isEmpty(userorgEntityList)) {
            userorgDTOList = Lists.newArrayList();
            for (UserOrgEntity userorgEntity : userorgEntityList) {
                userorgDTOList.add(entityToDTO(userorgEntity));
            }
        }
        return userorgDTOList;
    }

    @Override
    protected UserOrgDTO entityToDTO(final UserOrgEntity userorgEntity) {
        UserOrgDTO userorgDTO = null;
        if (!Func.isEmpty(userorgEntity)) {
            userorgDTO = new UserOrgDTO();
            BeanKit.copyProperties(userorgEntity, userorgDTO);
        }
        return userorgDTO;
    }

    @Override
    protected List<UserOrgEntity> dtoToEntityList(final List<UserOrgDTO> userorgDTOList) {
        List<UserOrgEntity> userorgEntityList = null;
        if (!Func.isEmpty(userorgDTOList)) {
            userorgEntityList = Lists.newArrayList();
            for (UserOrgDTO userorgDTO : userorgDTOList) {
                userorgEntityList.add(dtoToEntity(userorgDTO));
            }
        }
        return userorgEntityList;
    }

    @Override
    protected UserOrgEntity dtoToEntity(final UserOrgDTO userorgDTO) {
        UserOrgEntity userorgEntity = null;
        if (!Func.isEmpty(userorgDTO)) {
            userorgEntity = new UserOrgEntity();
            BeanKit.copyProperties(userorgDTO, userorgEntity);
        }
        return userorgEntity;
    }

    @Override
    protected UserOrgEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new UserOrgEntity();
        }
        return (UserOrgEntity) MapKit.toBean(map, UserOrgEntity.class);
    }

    @Override
    protected UserOrgDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new UserOrgDTO();
        }
        return (UserOrgDTO) MapKit.toBean(map, UserOrgDTO.class);
    }
}
