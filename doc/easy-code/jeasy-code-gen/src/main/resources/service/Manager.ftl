package ${conf.basePackage}.${table.lowerCamelName}.manager;

import com.google.common.collect.Lists;
import ${conf.basePackage}.base.dto.PageDTO;
import ${conf.basePackage}.base.manager.impl.BaseManagerImpl;
import ${conf.basePackage}.common.Func;
import ${conf.basePackage}.common.object.BeanKit;
import ${conf.basePackage}.common.object.MapKit;
import ${conf.basePackage}.common.spring.SpringContextHolder;
import ${conf.basePackage}.${table.lowerCamelName}.biz.${table.className}Biz;
import ${conf.basePackage}.${table.lowerCamelName}.dao.${table.className}DAO;
import ${conf.basePackage}.${table.lowerCamelName}.dto.*;
import ${conf.basePackage}.${table.lowerCamelName}.entity.${table.className}Entity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ${table.comment} Manager
 *
 * @author ${conf.author}
 * @version ${conf.version}
 * @since ${conf.createDate}
 */
@Slf4j
@Component
public class ${table.className}Manager extends BaseManagerImpl<${table.className}DAO, ${table.className}Entity, ${table.className}DTO> {

    public static ${table.className}Manager me() {
        return SpringContextHolder.getBean(${table.className}Manager.class);
    }

    public List<${table.className}ListResDTO> list(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        ${table.className}DTO paramsDTO = ${table.className}Biz.me().buildListParamsDTO(${table.camelName}ListReqDTO);
        List<${table.className}DTO> ${table.camelName}DTOList = super.findList(paramsDTO);

        return ${table.className}Biz.me().transfer${table.className}ListResDTOs(${table.camelName}DTOList);
    }

    public List<${table.className}ListResDTO> listByVersion1(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        return list(${table.camelName}ListReqDTO);
    }

    public List<${table.className}ListResDTO> listByVersion2(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        return list(${table.camelName}ListReqDTO);
    }

    public List<${table.className}ListResDTO> listByVersion3(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        return list(${table.camelName}ListReqDTO);
    }

    public ${table.className}ListResDTO listOne(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        ${table.className}DTO paramsDTO = ${table.className}Biz.me().buildListParamsDTO(${table.camelName}ListReqDTO);
        ${table.className}DTO ${table.camelName}DTO = super.findOne(paramsDTO);

        return ${table.className}Biz.me().transfer${table.className}ListResDTO(${table.camelName}DTO);
    }

    public PageDTO<${table.className}PageResDTO> pagination(final ${table.className}PageReqDTO ${table.camelName}PageReqDTO, final Integer current, final Integer size) {
        ${table.className}DTO paramsDTO = ${table.className}Biz.me().buildPageParamsDTO(${table.camelName}PageReqDTO);
        PageDTO<${table.className}DTO> ${table.camelName}DTOPage = super.findPage(paramsDTO, current, size);

        return ${table.className}Biz.me().transfer${table.className}PageResDTOPage(${table.camelName}DTOPage);
    }

    public Boolean add(final ${table.className}AddReqDTO ${table.camelName}AddReqDTO) {
        ${table.className}Biz.me().validate${table.className}AddReqDTO(${table.camelName}AddReqDTO);
        return super.saveDTO(${table.className}Biz.me().buildAdd${table.className}DTO(${table.camelName}AddReqDTO));
    }

    public Boolean addAllColumn(final ${table.className}AddReqDTO ${table.camelName}AddReqDTO) {
        ${table.className}Biz.me().validate${table.className}AddReqDTO(${table.camelName}AddReqDTO);
        return super.saveAllColumn(${table.className}Biz.me().buildAdd${table.className}DTO(${table.camelName}AddReqDTO));
    }

    public Boolean addBatchAllColumn(final List<${table.className}AddReqDTO> ${table.camelName}AddReqDTOList) {
        ${table.className}Biz.me().validate${table.className}AddReqDTOList(${table.camelName}AddReqDTOList);
        return super.saveBatchAllColumn(${table.className}Biz.me().buildAddBatch${table.className}DTO(${table.camelName}AddReqDTOList));
    }

    public ${table.className}ShowResDTO show(final Long id) {
        ${table.className}DTO ${table.camelName}DTO = super.findById(id);
        return ${table.className}Biz.me().transfer${table.className}ShowResDTO(${table.camelName}DTO);
    }

    public List<${table.className}ShowResDTO> showByIds(final List<Long> ids) {
        List<${table.className}DTO> ${table.camelName}DTOList = super.findBatchIds(ids);
        return ${table.className}Biz.me().transfer${table.className}ShowResDTOList(${table.camelName}DTOList);
    }

    public Boolean modify(final ${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO) {
        ${table.className}Biz.me().validate${table.className}ModifyReqDTO(${table.camelName}ModifyReqDTO);
        return super.modifyById(${table.className}Biz.me().buildModify${table.className}DTO(${table.camelName}ModifyReqDTO));
    }

    public Boolean modifyAllColumn(final ${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO) {
        ${table.className}Biz.me().validate${table.className}ModifyReqDTO(${table.camelName}ModifyReqDTO);
        return super.modifyAllColumnById(${table.className}Biz.me().buildModify${table.className}DTO(${table.camelName}ModifyReqDTO));
    }

    public Boolean removeByParams(final ${table.className}RemoveReqDTO ${table.camelName}RemoveReqDTO) {
        ${table.className}Biz.me().validate${table.className}RemoveReqDTO(${table.camelName}RemoveReqDTO);
        return super.remove(${table.className}Biz.me().buildRemove${table.className}DTO(${table.camelName}RemoveReqDTO));
    }

    @Override
    protected List<${table.className}DTO> entityToDTOList(final List<${table.className}Entity> ${table.camelName}EntityList) {
        List<${table.className}DTO> ${table.camelName}DTOList = null;
        if (!Func.isEmpty(${table.camelName}EntityList)) {
            ${table.camelName}DTOList = Lists.newArrayList();
            for (${table.className}Entity ${table.camelName}Entity : ${table.camelName}EntityList) {
                ${table.camelName}DTOList.add(entityToDTO(${table.camelName}Entity));
            }
        }
        return ${table.camelName}DTOList;
    }

    @Override
    protected ${table.className}DTO entityToDTO(final ${table.className}Entity ${table.camelName}Entity) {
        ${table.className}DTO ${table.camelName}DTO = null;
        if (!Func.isEmpty(${table.camelName}Entity)) {
            ${table.camelName}DTO = new ${table.className}DTO();
            BeanKit.copyProperties(${table.camelName}Entity, ${table.camelName}DTO);
        }
        return ${table.camelName}DTO;
    }

    @Override
    protected List<${table.className}Entity> dtoToEntityList(final List<${table.className}DTO> ${table.camelName}DTOList) {
        List<${table.className}Entity> ${table.camelName}EntityList = null;
        if (!Func.isEmpty(${table.camelName}DTOList)) {
            ${table.camelName}EntityList = Lists.newArrayList();
            for (${table.className}DTO ${table.camelName}DTO : ${table.camelName}DTOList) {
                ${table.camelName}EntityList.add(dtoToEntity(${table.camelName}DTO));
            }
        }
        return ${table.camelName}EntityList;
    }

    @Override
    protected ${table.className}Entity dtoToEntity(final ${table.className}DTO ${table.camelName}DTO) {
        ${table.className}Entity ${table.camelName}Entity = null;
        if (!Func.isEmpty(${table.camelName}DTO)) {
            ${table.camelName}Entity = new ${table.className}Entity();
            BeanKit.copyProperties(${table.camelName}DTO, ${table.camelName}Entity);
        }
        return ${table.camelName}Entity;
    }

    @Override
    protected ${table.className}Entity mapToEntity(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new ${table.className}Entity();
        }
        return (${table.className}Entity) MapKit.toBean(map, ${table.className}Entity.class);
    }

    @Override
    protected ${table.className}DTO mapToDTO(final Map<String, Object> map) {
        if (Func.isEmpty(map)) {
            return new ${table.className}DTO();
        }
        return (${table.className}DTO) MapKit.toBean(map, ${table.className}DTO.class);
    }
}
