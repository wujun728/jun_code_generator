package cn.afterturn.gen.modular.system.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import cn.afterturn.gen.common.persistence.model.Dict;

/**
 * 字典的dao
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午11:10:24
 */
public interface DictDao {

    /**
     * 根据编码获取词典列表
     *
     * @date 2017年2月13日 下午11:11:28
     */
    List<Dict> selectByCode(@Param("code") String code);

    /**
     * 查询字典列表
     *
     * @author fengshuonan
     * @Date 2017/4/26 13:04
     */
    List<Map<String, Object>> list(@Param("condition") String conditiion);

    Dict getDataByKey(String dictName, int num);
}
