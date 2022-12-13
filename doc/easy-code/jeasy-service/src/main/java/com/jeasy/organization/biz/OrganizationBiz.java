package com.jeasy.organization.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.object.BeanKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.exception.MessageException;
import com.jeasy.organization.dto.*;
import com.jeasy.organization.entity.OrganizationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 机构 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class OrganizationBiz {

    public static OrganizationBiz me() {
        return SpringContextHolder.getBean(OrganizationBiz.class);
    }

    public List<OrganizationListResDTO> transferOrganizationListResDTO(List<OrganizationDTO> organizationDTOList) {
        Map<Long, OrganizationListResDTO> organizationListResDTOMap = Maps.newHashMap();
        if (Func.isNotEmpty(organizationDTOList)) {
            for (OrganizationDTO organizationDTO : organizationDTOList) {
                OrganizationListResDTO organizationListResDTO = new OrganizationListResDTO();
                organizationListResDTO.setId(organizationDTO.getId());
                organizationListResDTO.setTitle(organizationDTO.getName());
                organizationListResDTO.setPid(organizationDTO.getPid());
                organizationListResDTOMap.put(organizationDTO.getId(), organizationListResDTO);
            }
        }

        List<OrganizationListResDTO> organizationListResDTOList = Lists.newArrayList();
        for (OrganizationDTO organizationDTO : organizationDTOList) {
            OrganizationListResDTO organizationListResDTO = organizationListResDTOMap.get(organizationDTO.getId());
            if (Func.isNullOrZero(organizationDTO.getPid())) {
                organizationListResDTOList.add(organizationListResDTO);
            } else {
                OrganizationListResDTO parentOrganizationListResDTO = organizationListResDTOMap.get(organizationDTO.getPid());
                List<OrganizationListResDTO> children = parentOrganizationListResDTO.getChildren();
                if (children == null) {
                    children = Lists.newArrayList();
                    parentOrganizationListResDTO.setChildren(children);
                }
                children.add(organizationListResDTO);
            }
        }
        return organizationListResDTOList;
    }

    public OrganizationShowResDTO transferOrganizationShowResDTO(OrganizationDTO organizationDTO, OrganizationDTO parentOrganizationDTO) {
        OrganizationShowResDTO organizationShowResDTO = new OrganizationShowResDTO();
        BeanKit.copyProperties(organizationDTO, organizationShowResDTO);
        if (Func.isNotEmpty(parentOrganizationDTO)) {
            organizationShowResDTO.setPname(parentOrganizationDTO.getName());
        }
        return organizationShowResDTO;
    }

    public OrganizationDTO transferOrganizationDTO(OrganizationModifyReqDTO organizationModifyReqDTO) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanKit.copyProperties(organizationModifyReqDTO, organizationDTO);
        return organizationDTO;
    }

    public QueryWrapper<OrganizationEntity> transferOrganizationQueryWrapper(OrganizationModifyReqDTO organizationModifyReqDTO) {
        QueryWrapper<OrganizationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne(OrganizationEntity.DB_COL_ID, organizationModifyReqDTO.getId());

        if (Func.isNotEmpty(organizationModifyReqDTO.getName()) && Func.isNotEmpty(organizationModifyReqDTO.getCode())) {
            queryWrapper.and(i -> i.eq(OrganizationEntity.DB_COL_NAME, organizationModifyReqDTO.getName()).or().eq(OrganizationEntity.DB_COL_CODE, organizationModifyReqDTO.getCode()));
            return queryWrapper;
        }

        if (Func.isNotEmpty(organizationModifyReqDTO.getName())) {
            queryWrapper.eq(OrganizationEntity.DB_COL_NAME, organizationModifyReqDTO.getName());
        }

        if (Func.isNotEmpty(organizationModifyReqDTO.getCode())) {
            queryWrapper.eq(OrganizationEntity.DB_COL_CODE, organizationModifyReqDTO.getCode());
        }

        return queryWrapper;
    }

    public boolean validateOrganizationModifyReqDTO(OrganizationModifyReqDTO organizationModifyReqDTO) {
        if (Func.isEmpty(organizationModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
        return true;
    }

    public QueryWrapper<OrganizationEntity> transferOrganizationQueryWrapper(OrganizationAddReqDTO organizationAddReqDTO) {
        QueryWrapper<OrganizationEntity> queryWrapper = new QueryWrapper<>();

        if (Func.isNotEmpty(organizationAddReqDTO.getName()) && Func.isNotEmpty(organizationAddReqDTO.getCode())) {
            queryWrapper.and(i -> i.eq(OrganizationEntity.DB_COL_NAME, organizationAddReqDTO.getName()).or().eq(OrganizationEntity.DB_COL_CODE, organizationAddReqDTO.getCode()));
            return queryWrapper;
        }

        if (Func.isNotEmpty(organizationAddReqDTO.getName())) {
            queryWrapper.eq(OrganizationEntity.DB_COL_NAME, organizationAddReqDTO.getName());
        }

        if (Func.isNotEmpty(organizationAddReqDTO.getCode())) {
            queryWrapper.eq(OrganizationEntity.DB_COL_CODE, organizationAddReqDTO.getCode());
        }

        return queryWrapper;
    }

    public OrganizationDTO transferOrganizationDTO(OrganizationAddReqDTO organizationAddReqDTO) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        BeanKit.copyProperties(organizationAddReqDTO, organizationDTO);
        return organizationDTO;
    }

    public boolean validateOrganizationAddReqDTO(OrganizationAddReqDTO organizationAddReqDTO) {
        if (Func.isEmpty(organizationAddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
        return true;
    }

    public QueryWrapper<OrganizationEntity> buildParentQueryWrapper(final Long pid) {
        QueryWrapper<OrganizationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(OrganizationEntity.DB_COL_PID, pid);
        return queryWrapper;
    }
}
