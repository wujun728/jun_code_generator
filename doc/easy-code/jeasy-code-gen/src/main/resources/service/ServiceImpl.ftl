package ${conf.basePackage}.${table.lowerCamelName}.service.impl;

import ${conf.basePackage}.base.dto.PageDTO;
import ${conf.basePackage}.base.service.impl.BaseServiceImpl;
import ${conf.basePackage}.${table.lowerCamelName}.dto.*;
import ${conf.basePackage}.${table.lowerCamelName}.entity.${table.className}Entity;
import ${conf.basePackage}.${table.lowerCamelName}.manager.${table.className}Manager;
import ${conf.basePackage}.${table.lowerCamelName}.service.${table.className}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${table.comment} ServiceImpl
 *
 * @author ${conf.author}
 * @version ${conf.version}
 * @since ${conf.createDate}
 */
@Slf4j
@Service
public class ${table.className}ServiceImpl extends BaseServiceImpl<${table.className}Manager, ${table.className}Entity, ${table.className}DTO> implements ${table.className}Service {

    @Override
    public List<${table.className}ListResDTO> list(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        return manager.list(${table.camelName}ListReqDTO);
    }

    @Override
    public List<${table.className}ListResDTO> listByVersion1(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        return manager.listByVersion1(${table.camelName}ListReqDTO);
    }

    @Override
    public List<${table.className}ListResDTO> listByVersion2(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        return manager.listByVersion2(${table.camelName}ListReqDTO);
    }

    @Override
    public List<${table.className}ListResDTO> listByVersion3(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        return manager.listByVersion3(${table.camelName}ListReqDTO);
    }

    @Override
    public ${table.className}ListResDTO listOne(final ${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        return manager.listOne(${table.camelName}ListReqDTO);
    }

    @Override
    public PageDTO<${table.className}PageResDTO> pagination(final ${table.className}PageReqDTO ${table.camelName}PageReqDTO, final Integer current, final Integer size) {
        return manager.pagination(${table.camelName}PageReqDTO, current, size);
    }

    @Override
    public Boolean add(final ${table.className}AddReqDTO ${table.camelName}AddReqDTO) {
        return manager.add(${table.camelName}AddReqDTO);
    }

    @Override
    public Boolean addAllColumn(final ${table.className}AddReqDTO ${table.camelName}AddReqDTO) {
        return manager.addAllColumn(${table.camelName}AddReqDTO);
    }

    @Override
    public Boolean addBatchAllColumn(final List<${table.className}AddReqDTO> ${table.camelName}AddReqDTOList) {
        return manager.addBatchAllColumn(${table.camelName}AddReqDTOList);
    }

    @Override
    public ${table.className}ShowResDTO show(final Long id) {
        return manager.show(id);
    }

    @Override
    public List<${table.className}ShowResDTO> showByIds(final List<Long> ids) {
        return manager.showByIds(ids);
    }

    @Override
    public Boolean modify(final ${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO) {
        return manager.modify(${table.camelName}ModifyReqDTO);
    }

    @Override
    public Boolean modifyAllColumn(final ${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO) {
        return manager.modifyAllColumn(${table.camelName}ModifyReqDTO);
    }

    @Override
    public Boolean removeByParams(final ${table.className}RemoveReqDTO ${table.camelName}RemoveReqDTO) {
        return manager.removeByParams(${table.camelName}RemoveReqDTO);
    }
}
