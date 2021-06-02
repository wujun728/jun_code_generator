package cn.afterturn.gen.core.parse;

import java.util.List;

import cn.afterturn.gen.core.model.GenBeanEntity;
import cn.afterturn.gen.core.model.GenerationEntity;

/**
 * 模板解析接口
 *
 * @author JueYue
 * @date 2014年12月23日
 */
public interface IParse {

    static final String GEN_PARAMS = "g";
    static final String TABLE_DETAIL = "t";

    /**
     * 解析模板 生成文件
     */
    public List<String> parse(GenerationEntity generationEntity, GenBeanEntity tableEntity, List<String> fileList);

}
