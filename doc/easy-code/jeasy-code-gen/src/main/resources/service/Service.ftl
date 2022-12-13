package ${conf.basePackage}.${table.lowerCamelName}.service;

import ${conf.basePackage}.base.dto.PageDTO;
import ${conf.basePackage}.base.service.BaseService;
import ${conf.basePackage}.${table.lowerCamelName}.dto.*;

import java.util.List;

/**
 * ${table.comment} Service
 *
 * @author ${conf.author}
 * @version ${conf.version}
 * @since ${conf.createDate}
 */
public interface ${table.className}Service extends BaseService<${table.className}DTO> {

    /**
     * 列表
     *
     * @param ${table.camelName}ListReqDTO 入参DTO
     * @return
     */
    List<${table.className}ListResDTO> list(${table.className}ListReqDTO ${table.camelName}ListReqDTO);

    /**
     * 列表Version1
     *
     * @param ${table.camelName}ListReqDTO 入参DTO
     * @return
     */
    List<${table.className}ListResDTO> listByVersion1(${table.className}ListReqDTO ${table.camelName}ListReqDTO);

    /**
     * 列表Version2
     *
     * @param ${table.camelName}ListReqDTO 入参DTO
     * @return
     */
    List<${table.className}ListResDTO> listByVersion2(${table.className}ListReqDTO ${table.camelName}ListReqDTO);

    /**
     * 列表Version3
     *
     * @param ${table.camelName}ListReqDTO 入参DTO
     * @return
     */
    List<${table.className}ListResDTO> listByVersion3(${table.className}ListReqDTO ${table.camelName}ListReqDTO);

    /**
     * First查询
     *
     * @param ${table.camelName}ListReqDTO 入参DTO
     * @return
     */
    ${table.className}ListResDTO listOne(${table.className}ListReqDTO ${table.camelName}ListReqDTO);

    /**
     * 分页
     *
     * @param ${table.camelName}PageReqDTO 入参DTO
     * @param current       当前页
     * @param size          每页大小
     * @return
     */
    PageDTO<${table.className}PageResDTO> pagination(${table.className}PageReqDTO ${table.camelName}PageReqDTO, Integer current, Integer size);

    /**
     * 新增
     *
     * @param ${table.camelName}AddReqDTO 入参DTO
     * @return
     */
    Boolean add(${table.className}AddReqDTO ${table.camelName}AddReqDTO);

    /**
     * 新增(所有字段)
     *
     * @param ${table.camelName}AddReqDTO 入参DTO
     * @return
     */
    Boolean addAllColumn(${table.className}AddReqDTO ${table.camelName}AddReqDTO);

    /**
     * 批量新增(所有字段)
     *
     * @param ${table.camelName}AddReqDTOList 入参DTO
     * @return
     */
    Boolean addBatchAllColumn(List<${table.className}AddReqDTO> ${table.camelName}AddReqDTOList);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return
     */
    ${table.className}ShowResDTO show(Long id);

    /**
     * 批量详情
     *
     * @param ids 主键IDs
     * @return
     */
    List<${table.className}ShowResDTO> showByIds(List<Long> ids);

    /**
     * 修改
     *
     * @param ${table.camelName}ModifyReqDTO 入参DTO
     * @return
     */
    Boolean modify(${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO);

    /**
     * 修改(所有字段)
     *
     * @param ${table.camelName}ModifyReqDTO 入参DTO
     * @return
     */
    Boolean modifyAllColumn(${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO);

    /**
     * 参数删除
     *
     * @param ${table.camelName}RemoveReqDTO 入参DTO
     * @return
     */
    Boolean removeByParams(${table.className}RemoveReqDTO ${table.camelName}RemoveReqDTO);
}
