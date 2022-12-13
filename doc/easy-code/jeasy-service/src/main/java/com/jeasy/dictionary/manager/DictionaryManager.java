package com.jeasy.dictionary.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.base.manager.impl.BaseManagerImpl;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.object.AbstractConverter;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.object.MapKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.dictionary.biz.DictionaryBiz;
import com.jeasy.dictionary.dao.DictionaryDAO;
import com.jeasy.dictionary.dto.*;
import com.jeasy.dictionary.entity.DictionaryEntity;
import com.jeasy.exception.MessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 字典 Manager
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class DictionaryManager extends BaseManagerImpl<DictionaryDAO, DictionaryEntity, DictionaryDTO> {

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

    public static DictionaryManager me() {
        return SpringContextHolder.getBean(DictionaryManager.class);
    }

    public List<DictionaryListResDTO> list(final DictionaryListReqDTO dictionaryListReqDTO) {
        DictionaryDTO dictionaryParamsDTO = new DictionaryDTO();
        if (!Func.isEmpty(dictionaryListReqDTO)) {
            BeanKit.copyProperties(dictionaryListReqDTO, dictionaryParamsDTO, DEMO_CONVERTER);
        }

        List<DictionaryDTO> dictionaryDTOList = super.findList(dictionaryParamsDTO);

        if (!Func.isEmpty(dictionaryDTOList)) {
            List<DictionaryListResDTO> items = Lists.newArrayList();
            for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
                DictionaryListResDTO dictionaryListResDTO = new DictionaryListResDTO();
                BeanKit.copyProperties(dictionaryDTO, dictionaryListResDTO, DEMO_CONVERTER);
                items.add(dictionaryListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<DictionaryListResDTO> listByVersion1(final DictionaryListReqDTO dictionaryListReqDTO) {
        return list(dictionaryListReqDTO);
    }

    public List<DictionaryListResDTO> listByVersion2(final DictionaryListReqDTO dictionaryListReqDTO) {
        return list(dictionaryListReqDTO);
    }

    public List<DictionaryListResDTO> listByVersion3(final DictionaryListReqDTO dictionaryListReqDTO) {
        return list(dictionaryListReqDTO);
    }

    public DictionaryListResDTO listOne(final DictionaryListReqDTO dictionaryListReqDTO) {
        DictionaryDTO dictionaryParamsDTO = new DictionaryDTO();
        if (!Func.isEmpty(dictionaryListReqDTO)) {
            BeanKit.copyProperties(dictionaryListReqDTO, dictionaryParamsDTO, DEMO_CONVERTER);
        }

        DictionaryDTO dictionaryDTO = super.findOne(dictionaryParamsDTO);
        if (!Func.isEmpty(dictionaryDTO)) {
            DictionaryListResDTO dictionaryListResDTO = new DictionaryListResDTO();
            BeanKit.copyProperties(dictionaryDTO, dictionaryListResDTO, DEMO_CONVERTER);
            return dictionaryListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public PageDTO<DictionaryPageResDTO> pagination(final DictionaryPageReqDTO dictionaryPageReqDTO, final Integer current, final Integer size) {
        // 构建父查询条件
        QueryWrapper<DictionaryEntity> parentEntityWrapper = DictionaryBiz.me().buildParentQueryWrapper(dictionaryPageReqDTO);
        List<DictionaryDTO> parentDictionaryDTOs = Lists.newArrayList();

        // 若父查询条件不为空
        if (Func.isNotEmpty(parentEntityWrapper)) {
            parentDictionaryDTOs = super.findList(parentEntityWrapper);
            // 父查询条件，结果集为空
            if (Func.isEmpty(parentDictionaryDTOs)) {
                return new PageDTO<>();
            }
        }

        QueryWrapper<DictionaryEntity> dictionaryEntityWrapper = DictionaryBiz.me().buildDictionaryQueryWrapper(dictionaryPageReqDTO, parentDictionaryDTOs);
        PageDTO<DictionaryDTO> dictionaryDTOPage = super.findPage(dictionaryEntityWrapper, current, size);

        parentDictionaryDTOs = Lists.newArrayList();
        List<Long> parentIds = DictionaryBiz.me().buildParentIds(dictionaryDTOPage);

        if (Func.isNotEmpty(parentIds)) {
            parentDictionaryDTOs = super.findBatchIds(parentIds);
        }

        return DictionaryBiz.me().transferDictionaryPageResDTOPage(dictionaryDTOPage, parentDictionaryDTOs);
    }

    public Boolean add(final DictionaryAddReqDTO dictionaryAddReqDTO) {
        DictionaryDTO dictionaryDTO = BeanKit.copy(dictionaryAddReqDTO, DictionaryDTO.class);

        DictionaryDTO parentDictionaryDTO = null;
        if (Func.isNotEmpty(dictionaryDTO.getPid())) {
            parentDictionaryDTO = super.findById(dictionaryDTO.getPid());
        }

        DictionaryBiz.me().buildDictionaryAddDTO(dictionaryDTO, parentDictionaryDTO);

        QueryWrapper<DictionaryEntity> queryWrapper = DictionaryBiz.me().buildRepeatCodeWrapper(dictionaryDTO);
        Integer count = super.findCount(queryWrapper);

        if (!Func.isNullOrZero(count)) {
            throw new MessageException(ModelResult.CODE_200, "字典编码已存在");
        }

        return super.saveDTO(dictionaryDTO);
    }

    public DictionaryShowResDTO show(final Long id) {
        DictionaryDTO dictionaryDTO = super.findById(id);
        DictionaryDTO parentDictionaryDTO = null;
        if (Func.isNotEmpty(dictionaryDTO.getPid())) {
            parentDictionaryDTO = super.findById(dictionaryDTO.getPid());
        }
        return DictionaryBiz.me().transferDictionaryShowResDTO(dictionaryDTO, parentDictionaryDTO);
    }

    public Boolean modify(final DictionaryModifyReqDTO dictionaryModifyReqDTO) {
        DictionaryDTO newDictionaryDTO = BeanKit.copy(dictionaryModifyReqDTO, DictionaryDTO.class);

        QueryWrapper<DictionaryEntity> queryWrapper = DictionaryBiz.me().buildRepeatCodeWrapper(newDictionaryDTO);
        Integer count = super.findCount(queryWrapper);

        if (!Func.isNullOrZero(count)) {
            throw new MessageException(ModelResult.CODE_200, "字典编码已存在");
        }

        // 更新子字典父编码
        QueryWrapper<DictionaryEntity> parentDictionaryWrapper = DictionaryBiz.me().buildParentQueryWrapper(newDictionaryDTO);
        if (Func.isNotEmpty(parentDictionaryWrapper)) {
            List<DictionaryDTO> childDictionaryDTOs = super.findList(parentDictionaryWrapper);
            if (Func.isNotEmpty(childDictionaryDTOs)) {
                for (DictionaryDTO dictionary : childDictionaryDTOs) {
                    dictionary.setPcode(newDictionaryDTO.getCode());
                    super.modifyById(dictionary);
                }
            }
        }

        // 判断是否是该字典类型的根字典，如果是，递归更新根字典下子字典
        if (Func.isNullOrZero(newDictionaryDTO.getPid())) {
            DictionaryDTO oldDictionaryDTO = super.findById(newDictionaryDTO.getId());
            if (!newDictionaryDTO.getType().equals(oldDictionaryDTO.getType()) || !newDictionaryDTO.getTypeName().equals(oldDictionaryDTO.getTypeName())) {
                List<DictionaryDTO> dictionaryDTOs = findSubDictionaryList(newDictionaryDTO);
                for (DictionaryDTO dictionary : dictionaryDTOs) {
                    dictionary.setType(newDictionaryDTO.getType());
                    dictionary.setTypeName(newDictionaryDTO.getTypeName());
                    super.modifyById(dictionary);
                }
            }
        }

        return super.modifyById(newDictionaryDTO);
    }

    private List<DictionaryDTO> findSubDictionaryList(final DictionaryDTO dictionaryDTO) {
        List<DictionaryDTO> dictionaryDTOs = Lists.newArrayList();
        QueryWrapper<DictionaryEntity> parentDictionaryWrapper = DictionaryBiz.me().buildParentQueryWrapper(dictionaryDTO);
        List<DictionaryDTO> subDictionaryDTOs = super.findList(parentDictionaryWrapper);
        if (Func.isNotEmpty(subDictionaryDTOs)) {
            dictionaryDTOs.addAll(subDictionaryDTOs);
            for (DictionaryDTO dictionary : subDictionaryDTOs) {
                dictionaryDTOs.addAll(findSubDictionaryList(dictionary));
            }
        }
        return dictionaryDTOs;
    }

    @Override
    public Boolean removeById(final Long id) {

        QueryWrapper<DictionaryEntity> parentDictionaryWrapper = DictionaryBiz.me().buildParentQueryWrapper(id);
        if (Func.isNotEmpty(parentDictionaryWrapper)) {
            List<DictionaryDTO> subDictionaryDTOs = super.findList(parentDictionaryWrapper);
            if (Func.isNotEmpty(subDictionaryDTOs)) {
                throw new MessageException(ModelResult.CODE_200, "请先删除子字典");
            }
        }

        return super.removeById(id);
    }

    public Boolean addAllColumn(final DictionaryAddReqDTO dictionaryAddReqDTO) {
        if (Func.isEmpty(dictionaryAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        DictionaryDTO dictionaryDTO = new DictionaryDTO();
        BeanKit.copyProperties(dictionaryAddReqDTO, dictionaryDTO, DEMO_CONVERTER);
        return super.saveAllColumn(dictionaryDTO);
    }

    public Boolean addBatchAllColumn(final List<DictionaryAddReqDTO> dictionaryAddReqDTOList) {
        if (Func.isEmpty(dictionaryAddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        List<DictionaryDTO> dictionaryDTOList = Lists.newArrayList();
        for (DictionaryAddReqDTO dictionaryAddReqDTO : dictionaryAddReqDTOList) {
            DictionaryDTO dictionaryDTO = new DictionaryDTO();
            BeanKit.copyProperties(dictionaryAddReqDTO, dictionaryDTO, DEMO_CONVERTER);
            dictionaryDTOList.add(dictionaryDTO);
        }
        return super.saveBatchAllColumn(dictionaryDTOList);
    }

    public List<DictionaryShowResDTO> showByIds(final List<Long> ids) {
        if (Func.isEmpty(ids)) {
            throw new MessageException(ModelResult.CODE_200, "集合不能为空且大小大于0");
        }

        List<DictionaryDTO> dictionaryDTOList = super.findBatchIds(ids);

        if (!Func.isEmpty(dictionaryDTOList)) {
            List<DictionaryShowResDTO> dictionaryShowResDTOList = Lists.newArrayList();
            for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
                DictionaryShowResDTO dictionaryShowResDTO = new DictionaryShowResDTO();
                BeanKit.copyProperties(dictionaryDTO, dictionaryShowResDTO, DEMO_CONVERTER);
                dictionaryShowResDTOList.add(dictionaryShowResDTO);
            }
            return dictionaryShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public Boolean modifyAllColumn(final DictionaryModifyReqDTO dictionaryModifyReqDTO) {
        if (Func.isEmpty(dictionaryModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        DictionaryDTO dictionaryDTO = new DictionaryDTO();
        BeanKit.copyProperties(dictionaryModifyReqDTO, dictionaryDTO, DEMO_CONVERTER);
        return super.modifyAllColumnById(dictionaryDTO);
    }

    public Boolean removeByParams(final DictionaryRemoveReqDTO dictionaryRemoveReqDTO) {
        if (Func.isEmpty(dictionaryRemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }

        DictionaryDTO dictionaryParamsDTO = new DictionaryDTO();
        BeanKit.copyProperties(dictionaryRemoveReqDTO, dictionaryParamsDTO, DEMO_CONVERTER);
        return super.remove(dictionaryParamsDTO);
    }

    public DictionaryDTO getByCode(final String type, final String code) {
        DictionaryDTO paramDTO = new DictionaryDTO();
        paramDTO.setType(type);
        paramDTO.setCode(code);
        return super.findOne(paramDTO);
    }

    public List<DictionaryDTO> findByType(final String type) {
        DictionaryDTO paramDTO = new DictionaryDTO();
        paramDTO.setType(type);
        return super.findList(paramDTO);
    }

    public List<DictionaryTypeListResDTO> listType() {
        List<DictionaryDTO> dictionarys = findList(new DictionaryDTO());
        return DictionaryBiz.me().transferDictionaryTypeResDTOs(dictionarys);
    }

    @Override
    protected List<DictionaryDTO> entityToDTOList(final List<DictionaryEntity> dictionaryEntityList) {
        List<DictionaryDTO> dictionaryDTOList = null;
        if (!Func.isEmpty(dictionaryEntityList)) {
            dictionaryDTOList = Lists.newArrayList();
            for (DictionaryEntity dictionaryEntity : dictionaryEntityList) {
                dictionaryDTOList.add(entityToDTO(dictionaryEntity));
            }
        }
        return dictionaryDTOList;
    }

    @Override
    protected DictionaryDTO entityToDTO(final DictionaryEntity dictionaryEntity) {
        DictionaryDTO dictionaryDTO = null;
        if (!Func.isEmpty(dictionaryEntity)) {
            dictionaryDTO = new DictionaryDTO();
            BeanKit.copyProperties(dictionaryEntity, dictionaryDTO);
        }
        return dictionaryDTO;
    }

    @Override
    protected List<DictionaryEntity> dtoToEntityList(final List<DictionaryDTO> dictionaryDTOList) {
        List<DictionaryEntity> dictionaryEntityList = null;
        if (!Func.isEmpty(dictionaryDTOList)) {
            dictionaryEntityList = Lists.newArrayList();
            for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
                dictionaryEntityList.add(dtoToEntity(dictionaryDTO));
            }
        }
        return dictionaryEntityList;
    }

    @Override
    protected DictionaryEntity dtoToEntity(final DictionaryDTO dictionaryDTO) {
        DictionaryEntity dictionaryEntity = null;
        if (!Func.isEmpty(dictionaryDTO)) {
            dictionaryEntity = new DictionaryEntity();
            BeanKit.copyProperties(dictionaryDTO, dictionaryEntity);
        }
        return dictionaryEntity;
    }

    @Override
    protected DictionaryEntity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new DictionaryEntity();
        }
        return (DictionaryEntity) MapKit.toBean(map, DictionaryEntity.class);
    }

    @Override
    protected DictionaryDTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new DictionaryDTO();
        }
        return (DictionaryDTO) MapKit.toBean(map, DictionaryDTO.class);
    }
}
