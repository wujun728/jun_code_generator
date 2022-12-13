package com.jeasy.dictionary;

import com.jeasy.dictionary.dto.DictionaryDTO;
import com.jeasy.dictionary.manager.DictionaryManager;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 *
 * @author TaoBangren
 * @version 1.0
 * @since 2017/4/26 上午10:20
 */
@Slf4j
public final class DictionaryKit {

    /**
     * YES
     */
    public static final Integer YES = 1;

    /**
     * NO
     */
    public static final Integer NO = 0;

    <#list dicTypes as dicType>
    /**
     * ${dicType.name}.
     */
    public static final String TYPE_${dicType.code} = "${dicType.code}";

    <#list dicType.dics as dic>
    /**
     * ${dicType.name}-${dic.name}.
     */
    public static final String ${dicType.code}_${dic.code} = "${dic.origCode}";

    </#list>
    /**
     * ${dicType.name}字典集合.
     */
    public static List<DictionaryDTO> ${dicType.code}_DICS() {
        return DictionaryManager.me().findByType(TYPE_${dicType.code});
    }

    /**
     * ${dicType.name}字典MAP.
     */
    public static Map<String, DictionaryDTO> ${dicType.code}_MAP() {
        List<DictionaryDTO> dictionaryDTOList = ${dicType.code}_DICS();
        Map<String, DictionaryDTO> ${dicType.code}_MAP = Maps.newHashMap();
        for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
            ${dicType.code}_MAP.put(dictionaryDTO.getCode(), dictionaryDTO);
        }
        return ${dicType.code}_MAP;
    }

    <#list dicType.dics as dic>
    /**
     * ${dicType.name}-${dic.name}.
     */
    public static DictionaryDTO ${dicType.code}_${dic.code}() {
        return DictionaryManager.me().getByCode(TYPE_${dicType.code}, ${dicType.code}_${dic.code});
    }
    /**
     * ${dicType.name}-${dic.name}ID.
     */
    public static Long ${dicType.code}_${dic.code}_ID() {
        return ${dicType.code}_${dic.code}().getId();
    }
    /**
     * ${dicType.name}-${dic.name}名称.
     */
    public static String ${dicType.code}_${dic.code}_NAME() {
        return ${dicType.code}_${dic.code}().getName();
    }
    /**
     * ${dicType.name}-${dic.name}值.
     */
    public static Integer ${dicType.code}_${dic.code}_VAL() {
        return ${dicType.code}_${dic.code}().getValue();
    }
    </#list>
    </#list>
}
