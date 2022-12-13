package com.jeasy.dictionary.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jeasy.base.dto.PageDTO;
import com.jeasy.common.Func;
import com.jeasy.common.collection.CollectionKit;
import com.jeasy.common.date.DateKit;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.dictionary.dto.*;
import com.jeasy.dictionary.entity.DictionaryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字典 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class DictionaryBiz {

    public static DictionaryBiz me() {
        return SpringContextHolder.getBean(DictionaryBiz.class);
    }

    /**
     * 根据查询入参，构建父字典查询条件
     *
     * @param dictionaryPageReqDTO 查询入参
     * @return
     */
    public QueryWrapper<DictionaryEntity> buildParentQueryWrapper(final DictionaryPageReqDTO dictionaryPageReqDTO) {
        boolean isBuild = Func.isNotEmpty(dictionaryPageReqDTO) && (Func.isNotEmpty(dictionaryPageReqDTO.getPcode()) || Func.isNotEmpty(dictionaryPageReqDTO.getPname()) || Func.isNotEmpty(dictionaryPageReqDTO.getPvalue()));
        if (!isBuild) {
            return null;
        }

        QueryWrapper<DictionaryEntity> queryWrapper = new QueryWrapper<>();

        if (Func.isNotEmpty(dictionaryPageReqDTO.getPcode())) {
            queryWrapper.like(DictionaryEntity.DB_COL_CODE, dictionaryPageReqDTO.getPcode().trim().toUpperCase());
        }

        if (Func.isNotEmpty(dictionaryPageReqDTO.getPname())) {
            queryWrapper.like(DictionaryEntity.DB_COL_NAME, dictionaryPageReqDTO.getPname().trim().toUpperCase());
        }

        if (Func.isNotEmpty(dictionaryPageReqDTO.getPvalue())) {
            queryWrapper.eq(DictionaryEntity.DB_COL_VALUE, dictionaryPageReqDTO.getPvalue());
        }

        return queryWrapper;
    }

    /**
     * 根据查询入参+父字典集合，构建字典查询条件
     *
     * @param dictionaryPageReqDTO 查询入参
     * @param parentDictionaryDTOs 父字典集合
     * @return
     */
    public QueryWrapper<DictionaryEntity> buildDictionaryQueryWrapper(final DictionaryPageReqDTO dictionaryPageReqDTO, final List<DictionaryDTO> parentDictionaryDTOs) {
        QueryWrapper<DictionaryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(DictionaryEntity.DB_COL_ID);

        if (Func.isNotEmpty(dictionaryPageReqDTO.getName())) {
            queryWrapper.like(DictionaryEntity.DB_COL_NAME, dictionaryPageReqDTO.getName().trim());
        }

        if (Func.isNotEmpty(dictionaryPageReqDTO.getCode())) {
            queryWrapper.like(DictionaryEntity.DB_COL_CODE, dictionaryPageReqDTO.getCode().trim().toUpperCase());
        }

        if (Func.isNotEmpty(dictionaryPageReqDTO.getValue())) {
            queryWrapper.eq(DictionaryEntity.DB_COL_VALUE, dictionaryPageReqDTO.getValue());
        }

        if (Func.isNotEmpty(dictionaryPageReqDTO.getType())) {
            queryWrapper.eq(DictionaryEntity.DB_COL_TYPE, dictionaryPageReqDTO.getType());
        }

        if (Func.isNotEmpty(dictionaryPageReqDTO.getUpdateStartAt())) {
            Long updateStartAt = DateKit.getMillis(DateKit.parseDateTime(dictionaryPageReqDTO.getUpdateStartAt()));
            queryWrapper.ge(DictionaryEntity.DB_COL_UPDATE_AT, updateStartAt);
        }

        if (Func.isNotEmpty(dictionaryPageReqDTO.getUpdateEndAt())) {
            Long updateEndAt = DateKit.getMillis(DateKit.parseDateTime(dictionaryPageReqDTO.getUpdateEndAt()));
            queryWrapper.le(DictionaryEntity.DB_COL_UPDATE_AT, updateEndAt);
        }

        if (Func.isNotEmpty(parentDictionaryDTOs)) {
            queryWrapper.in(DictionaryEntity.DB_COL_PID, buildDictionaryIds(parentDictionaryDTOs));
        }
        return queryWrapper;
    }

    public final PageDTO<DictionaryPageResDTO> transferDictionaryPageResDTOPage(final PageDTO<DictionaryDTO> dictionaryDTOPage, final List<DictionaryDTO> parentDictionaryDTOs) {
        PageDTO<DictionaryPageResDTO> dictionaryPageResDTOPage = new PageDTO<>();

        if (Func.isEmpty(dictionaryDTOPage) || Func.isEmpty(dictionaryDTOPage.getRecords())) {
            return dictionaryPageResDTOPage;
        }

        Map<Long, DictionaryDTO> parentDictionaryMap = Maps.newHashMap();
        if (Func.isNotEmpty(parentDictionaryDTOs)) {
            for (DictionaryDTO dictionaryDTO : parentDictionaryDTOs) {
                parentDictionaryMap.put(dictionaryDTO.getId(), dictionaryDTO);
            }
        }

        List<DictionaryPageResDTO> dictionaryPageResDTOS = Lists.newArrayList();
        for (DictionaryDTO dictionaryDTO : dictionaryDTOPage.getRecords()) {
            DictionaryPageResDTO dictionaryPageResDTO = new DictionaryPageResDTO();
            BeanKit.copy(dictionaryDTO, dictionaryPageResDTO);
            dictionaryPageResDTO.setUpdateAt(DateKit.formatDateTime(DateKit.getDate(dictionaryDTO.getUpdateAt())));

            DictionaryDTO parentDictionaryDTO = parentDictionaryMap.get(dictionaryPageResDTO.getPid());
            if (Func.isNotEmpty(parentDictionaryDTO)) {
                dictionaryPageResDTO.setPname(parentDictionaryDTO.getName());
                dictionaryPageResDTO.setPcode(parentDictionaryDTO.getCode());
                dictionaryPageResDTO.setPvalue(parentDictionaryDTO.getValue());
            }
            dictionaryPageResDTOS.add(dictionaryPageResDTO);
        }

        BeanKit.copy(dictionaryDTOPage, dictionaryPageResDTOPage);
        dictionaryPageResDTOPage.setRecords(dictionaryPageResDTOS);
        return dictionaryPageResDTOPage;
    }

    public final List<Long> buildParentIds(final PageDTO<DictionaryDTO> dictionaryDTOPage) {
        return buildParentDictionaryIds(dictionaryDTOPage.getRecords());
    }

    private List<Long> buildParentDictionaryIds(final List<DictionaryDTO> dictionaryDTOS) {
        if (Func.isEmpty(dictionaryDTOS)) {
            return null;
        }

        List<Long> ids = Lists.newArrayList();
        for (DictionaryDTO dictionaryDTO : dictionaryDTOS) {
            ids.add(dictionaryDTO.getPid());
        }
        return ids;
    }

    private List<Long> buildDictionaryIds(final List<DictionaryDTO> dictionaryDTOS) {
        if (Func.isEmpty(dictionaryDTOS)) {
            return null;
        }

        List<Long> ids = Lists.newArrayList();
        for (DictionaryDTO dictionaryDTO : dictionaryDTOS) {
            ids.add(dictionaryDTO.getId());
        }
        return ids;
    }

    public final void buildDictionaryAddDTO(final DictionaryDTO dictionaryDTO, final DictionaryDTO parentDictionaryDTO) {
        dictionaryDTO.setCode(dictionaryDTO.getCode().toUpperCase());

        if (Func.isNotEmpty(parentDictionaryDTO)) {
            dictionaryDTO.setPcode(parentDictionaryDTO.getCode());
        }

        dictionaryDTO.setSort(0);
    }

    public final DictionaryShowResDTO transferDictionaryShowResDTO(final DictionaryDTO dictionaryDTO, final DictionaryDTO parentDictionaryDTO) {
        DictionaryShowResDTO dictionaryShowResDTO = BeanKit.copy(dictionaryDTO, DictionaryShowResDTO.class);

        if (Func.isNotEmpty(parentDictionaryDTO)) {
            dictionaryShowResDTO.setPname(parentDictionaryDTO.getName());
            dictionaryShowResDTO.setPcode(parentDictionaryDTO.getCode());
            dictionaryShowResDTO.setPvalue(parentDictionaryDTO.getValue());
        }

        return dictionaryShowResDTO;
    }

    public final QueryWrapper<DictionaryEntity> buildRepeatCodeWrapper(final DictionaryDTO dictionaryDTO) {
        QueryWrapper<DictionaryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DictionaryEntity.DB_COL_CODE, dictionaryDTO.getCode());
        if (!Func.isNullOrZero(dictionaryDTO.getId())) {
            queryWrapper.ne(DictionaryEntity.DB_COL_ID, dictionaryDTO.getId());
        }
        return queryWrapper;
    }

    public List<DictionaryTypeListResDTO> transferDictionaryTypeResDTOs(final List<DictionaryDTO> dictionarys) {
        Set<String> dictionaryTypeCodeSet = Sets.newHashSet();
        List<DictionaryTypeListResDTO> dictionaryTypeListResDTOS = Lists.newArrayList();
        for (DictionaryDTO dictionary : dictionarys) {
            if (!dictionaryTypeCodeSet.contains(dictionary.getType())) {
                dictionaryTypeListResDTOS.add(new DictionaryTypeListResDTO(dictionary.getTypeName(), dictionary.getType()));
                dictionaryTypeCodeSet.add(dictionary.getType());
            }
        }

        return CollectionKit.sort(dictionaryTypeListResDTOS, new Comparator<DictionaryTypeListResDTO>() {
            @Override
            public int compare(DictionaryTypeListResDTO o1, DictionaryTypeListResDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public QueryWrapper<DictionaryEntity> buildParentQueryWrapper(final DictionaryDTO dictionaryDTO) {
        return buildParentQueryWrapper(dictionaryDTO.getId());
    }

    public QueryWrapper<DictionaryEntity> buildParentQueryWrapper(final Long pid) {
        QueryWrapper<DictionaryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DictionaryEntity.DB_COL_PID, pid);
        return queryWrapper;
    }
}
