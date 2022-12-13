package ${conf.basePackage}.${table.lowerCamelName}.biz;

import com.google.common.collect.Lists;
import ${conf.basePackage}.base.dto.PageDTO;
import ${conf.basePackage}.base.web.dto.ModelResult;
import ${conf.basePackage}.common.Func;
import ${conf.basePackage}.common.object.AbstractConverter;
import ${conf.basePackage}.common.object.BeanKit;
import ${conf.basePackage}.common.spring.SpringContextHolder;
import ${conf.basePackage}.exception.MessageException;
import ${conf.basePackage}.${table.lowerCamelName}.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${table.comment} Biz
 *
 * @author ${conf.author}
 * @version ${conf.version}
 * @since ${conf.createDate}
 */
@Slf4j
@Component
public class ${table.className}Biz {

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

    public static ${table.className}Biz me() {
        return SpringContextHolder.getBean(${table.className}Biz.class);
    }

    public ${table.className}DTO buildListParamsDTO(${table.className}ListReqDTO ${table.camelName}ListReqDTO) {
        ${table.className}DTO paramsDTO = new ${table.className}DTO();
        if (!Func.isEmpty(${table.camelName}ListReqDTO)) {
            BeanKit.copyProperties(${table.camelName}ListReqDTO, paramsDTO, DEMO_CONVERTER);
        }
        return paramsDTO;
    }

    public List<${table.className}ListResDTO> transfer${table.className}ListResDTOs(List<${table.className}DTO> ${table.camelName}DTOList) {
        if (!Func.isEmpty(${table.camelName}DTOList)) {
            List<${table.className}ListResDTO> items = Lists.newArrayList();
            for (${table.className}DTO ${table.camelName}DTO : ${table.camelName}DTOList) {
                ${table.className}ListResDTO ${table.camelName}ListResDTO = new ${table.className}ListResDTO();
                BeanKit.copyProperties(${table.camelName}DTO, ${table.camelName}ListResDTO, DEMO_CONVERTER);
                items.add(${table.camelName}ListResDTO);
            }
            return items;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public ${table.className}ListResDTO transfer${table.className}ListResDTO(${table.className}DTO ${table.camelName}DTO) {
        if (!Func.isEmpty(${table.camelName}DTO)) {
            ${table.className}ListResDTO ${table.camelName}ListResDTO = new ${table.className}ListResDTO();
            BeanKit.copyProperties(${table.camelName}DTO, ${table.camelName}ListResDTO, DEMO_CONVERTER);
            return ${table.camelName}ListResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public ${table.className}DTO buildPageParamsDTO(${table.className}PageReqDTO ${table.camelName}PageReqDTO) {
        ${table.className}DTO paramsDTO = new ${table.className}DTO();
        if (!Func.isEmpty(${table.camelName}PageReqDTO)) {
            BeanKit.copyProperties(${table.camelName}PageReqDTO, paramsDTO, DEMO_CONVERTER);
        }
        return paramsDTO;
    }

    public PageDTO<${table.className}PageResDTO> transfer${table.className}PageResDTOPage(PageDTO<${table.className}DTO> ${table.camelName}DTOPage) {
        if (Func.isNotEmpty(${table.camelName}DTOPage) && Func.isNotEmpty(${table.camelName}DTOPage.getRecords())) {
            List<${table.className}PageResDTO> ${table.camelName}PageResDTOs = Lists.newArrayList();
            for (${table.className}DTO ${table.camelName}DTO : ${table.camelName}DTOPage.getRecords()) {
                ${table.className}PageResDTO ${table.camelName}PageResDTO = new ${table.className}PageResDTO();
                BeanKit.copyProperties(${table.camelName}DTO, ${table.camelName}PageResDTO, DEMO_CONVERTER);
                ${table.camelName}PageResDTOs.add(${table.camelName}PageResDTO);
            }

            PageDTO<${table.className}PageResDTO> ${table.camelName}PageResDTOPage = new PageDTO<>();
            ${table.camelName}PageResDTOPage.setRecords(${table.camelName}PageResDTOs);
            ${table.camelName}PageResDTOPage.setTotal(${table.camelName}DTOPage.getTotal());
            return ${table.camelName}PageResDTOPage;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public void validate${table.className}AddReqDTO(${table.className}AddReqDTO ${table.camelName}AddReqDTO) {
        if (Func.isEmpty(${table.camelName}AddReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public ${table.className}DTO buildAdd${table.className}DTO(${table.className}AddReqDTO ${table.camelName}AddReqDTO) {
        ${table.className}DTO ${table.camelName}DTO = new ${table.className}DTO();
        BeanKit.copyProperties(${table.camelName}AddReqDTO, ${table.camelName}DTO, DEMO_CONVERTER);
        return ${table.camelName}DTO;
    }

    public void validate${table.className}AddReqDTOList(List<${table.className}AddReqDTO> ${table.camelName}AddReqDTOList) {
        if (Func.isEmpty(${table.camelName}AddReqDTOList)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public List<${table.className}DTO> buildAddBatch${table.className}DTO(List<${table.className}AddReqDTO> ${table.camelName}AddReqDTOList) {
        List<${table.className}DTO> ${table.camelName}DTOList = Lists.newArrayList();
        for (${table.className}AddReqDTO ${table.camelName}AddReqDTO : ${table.camelName}AddReqDTOList) {
            ${table.className}DTO ${table.camelName}DTO = new ${table.className}DTO();
            BeanKit.copyProperties(${table.camelName}AddReqDTO, ${table.camelName}DTO, DEMO_CONVERTER);
            ${table.camelName}DTOList.add(${table.camelName}DTO);
        }
        return ${table.camelName}DTOList;
    }

    public ${table.className}ShowResDTO transfer${table.className}ShowResDTO(${table.className}DTO ${table.camelName}DTO) {
        if (!Func.isEmpty(${table.camelName}DTO)) {
            ${table.className}ShowResDTO ${table.camelName}ShowResDTO = new ${table.className}ShowResDTO();
            BeanKit.copyProperties(${table.camelName}DTO, ${table.camelName}ShowResDTO, DEMO_CONVERTER);
            return ${table.camelName}ShowResDTO;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public List<${table.className}ShowResDTO> transfer${table.className}ShowResDTOList(List<${table.className}DTO> ${table.camelName}DTOList) {
        if (!Func.isEmpty(${table.camelName}DTOList)) {
            List<${table.className}ShowResDTO> ${table.camelName}ShowResDTOList = Lists.newArrayList();
            for (${table.className}DTO ${table.camelName}DTO : ${table.camelName}DTOList) {
                ${table.className}ShowResDTO ${table.camelName}ShowResDTO = new ${table.className}ShowResDTO();
                BeanKit.copyProperties(${table.camelName}DTO, ${table.camelName}ShowResDTO, DEMO_CONVERTER);
                ${table.camelName}ShowResDTOList.add(${table.camelName}ShowResDTO);
            }
            return ${table.camelName}ShowResDTOList;
        }
        throw new MessageException(ModelResult.CODE_200, "未查找到记录");
    }

    public void validate${table.className}ModifyReqDTO(${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO) {
        if (Func.isEmpty(${table.camelName}ModifyReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public ${table.className}DTO buildModify${table.className}DTO(${table.className}ModifyReqDTO ${table.camelName}ModifyReqDTO) {
        ${table.className}DTO ${table.camelName}DTO = new ${table.className}DTO();
        BeanKit.copyProperties(${table.camelName}ModifyReqDTO, ${table.camelName}DTO, DEMO_CONVERTER);
        return ${table.camelName}DTO;
    }

    public void validate${table.className}RemoveReqDTO(${table.className}RemoveReqDTO ${table.camelName}RemoveReqDTO) {
        if (Func.isEmpty(${table.camelName}RemoveReqDTO)) {
            throw new MessageException(ModelResult.CODE_200, "入参不能为空");
        }
    }

    public ${table.className}DTO buildRemove${table.className}DTO(${table.className}RemoveReqDTO ${table.camelName}RemoveReqDTO) {
        ${table.className}DTO ${table.camelName}ParamsDTO = new ${table.className}DTO();
        BeanKit.copyProperties(${table.camelName}RemoveReqDTO, ${table.camelName}ParamsDTO, DEMO_CONVERTER);
        return ${table.camelName}ParamsDTO;
    }
}
