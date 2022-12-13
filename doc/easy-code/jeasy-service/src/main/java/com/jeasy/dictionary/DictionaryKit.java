package com.jeasy.dictionary;

import com.google.common.collect.Maps;
import com.jeasy.dictionary.dto.DictionaryDTO;
import com.jeasy.dictionary.manager.DictionaryManager;
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

    /**
     * 用户状态.
     */
    public static final String TYPE_YHZT = "YHZT";

    /**
     * 用户状态-启用.
     */
    public static final String YHZT_QY = "QY";

    /**
     * 用户状态-停用.
     */
    public static final String YHZT_TY = "TY";

    /**
     * 用户状态字典集合.
     */
    public static List<DictionaryDTO> YHZT_DICS() {
        return DictionaryManager.me().findByType(TYPE_YHZT);
    }

    /**
     * 用户状态字典MAP.
     */
    public static Map<String, DictionaryDTO> YHZT_MAP() {
        List<DictionaryDTO> dictionaryDTOList = YHZT_DICS();
        Map<String, DictionaryDTO> YHZT_MAP = Maps.newHashMap();
        for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
            YHZT_MAP.put(dictionaryDTO.getCode(), dictionaryDTO);
        }
        return YHZT_MAP;
    }

    /**
     * 用户状态-启用.
     */
    public static DictionaryDTO YHZT_QY() {
        return DictionaryManager.me().getByCode(TYPE_YHZT, YHZT_QY);
    }

    /**
     * 用户状态-启用ID.
     */
    public static Long YHZT_QY_ID() {
        return YHZT_QY().getId();
    }

    /**
     * 用户状态-启用名称.
     */
    public static String YHZT_QY_NAME() {
        return YHZT_QY().getName();
    }

    /**
     * 用户状态-启用值.
     */
    public static Integer YHZT_QY_VAL() {
        return YHZT_QY().getValue();
    }

    /**
     * 用户状态-停用.
     */
    public static DictionaryDTO YHZT_TY() {
        return DictionaryManager.me().getByCode(TYPE_YHZT, YHZT_TY);
    }

    /**
     * 用户状态-停用ID.
     */
    public static Long YHZT_TY_ID() {
        return YHZT_TY().getId();
    }

    /**
     * 用户状态-停用名称.
     */
    public static String YHZT_TY_NAME() {
        return YHZT_TY().getName();
    }

    /**
     * 用户状态-停用值.
     */
    public static Integer YHZT_TY_VAL() {
        return YHZT_TY().getValue();
    }

    /**
     * 机构类型.
     */
    public static final String TYPE_JGLX = "JGLX";

    /**
     * 机构类型-其他.
     */
    public static final String JGLX_QT = "QT";

    /**
     * 机构类型字典集合.
     */
    public static List<DictionaryDTO> JGLX_DICS() {
        return DictionaryManager.me().findByType(TYPE_JGLX);
    }

    /**
     * 机构类型字典MAP.
     */
    public static Map<String, DictionaryDTO> JGLX_MAP() {
        List<DictionaryDTO> dictionaryDTOList = JGLX_DICS();
        Map<String, DictionaryDTO> JGLX_MAP = Maps.newHashMap();
        for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
            JGLX_MAP.put(dictionaryDTO.getCode(), dictionaryDTO);
        }
        return JGLX_MAP;
    }

    /**
     * 机构类型-其他.
     */
    public static DictionaryDTO JGLX_QT() {
        return DictionaryManager.me().getByCode(TYPE_JGLX, JGLX_QT);
    }

    /**
     * 机构类型-其他ID.
     */
    public static Long JGLX_QT_ID() {
        return JGLX_QT().getId();
    }

    /**
     * 机构类型-其他名称.
     */
    public static String JGLX_QT_NAME() {
        return JGLX_QT().getName();
    }

    /**
     * 机构类型-其他值.
     */
    public static Integer JGLX_QT_VAL() {
        return JGLX_QT().getValue();
    }

    /**
     * 日志类型.
     */
    public static final String TYPE_RZLX = "RZLX";

    /**
     * 日志类型-登录登出.
     */
    public static final String RZLX_DLDC = "DLDC";

    /**
     * 日志类型-启用停用.
     */
    public static final String RZLX_QYTY = "QYTY";

    /**
     * 日志类型字典集合.
     */
    public static List<DictionaryDTO> RZLX_DICS() {
        return DictionaryManager.me().findByType(TYPE_RZLX);
    }

    /**
     * 日志类型字典MAP.
     */
    public static Map<String, DictionaryDTO> RZLX_MAP() {
        List<DictionaryDTO> dictionaryDTOList = RZLX_DICS();
        Map<String, DictionaryDTO> RZLX_MAP = Maps.newHashMap();
        for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
            RZLX_MAP.put(dictionaryDTO.getCode(), dictionaryDTO);
        }
        return RZLX_MAP;
    }

    /**
     * 日志类型-登录登出.
     */
    public static DictionaryDTO RZLX_DLDC() {
        return DictionaryManager.me().getByCode(TYPE_RZLX, RZLX_DLDC);
    }

    /**
     * 日志类型-登录登出ID.
     */
    public static Long RZLX_DLDC_ID() {
        return RZLX_DLDC().getId();
    }

    /**
     * 日志类型-登录登出名称.
     */
    public static String RZLX_DLDC_NAME() {
        return RZLX_DLDC().getName();
    }

    /**
     * 日志类型-登录登出值.
     */
    public static Integer RZLX_DLDC_VAL() {
        return RZLX_DLDC().getValue();
    }

    /**
     * 日志类型-启用停用.
     */
    public static DictionaryDTO RZLX_QYTY() {
        return DictionaryManager.me().getByCode(TYPE_RZLX, RZLX_QYTY);
    }

    /**
     * 日志类型-启用停用ID.
     */
    public static Long RZLX_QYTY_ID() {
        return RZLX_QYTY().getId();
    }

    /**
     * 日志类型-启用停用名称.
     */
    public static String RZLX_QYTY_NAME() {
        return RZLX_QYTY().getName();
    }

    /**
     * 日志类型-启用停用值.
     */
    public static Integer RZLX_QYTY_VAL() {
        return RZLX_QYTY().getValue();
    }

    /**
     * 操作类型.
     */
    public static final String TYPE_CZLX = "CZLX";

    /**
     * 操作类型-登录.
     */
    public static final String CZLX_DL = "DL";

    /**
     * 操作类型-登出.
     */
    public static final String CZLX_DC = "DC";

    /**
     * 操作类型-启用.
     */
    public static final String CZLX_QY = "QY";

    /**
     * 操作类型-停用.
     */
    public static final String CZLX_TY = "TY";

    /**
     * 操作类型字典集合.
     */
    public static List<DictionaryDTO> CZLX_DICS() {
        return DictionaryManager.me().findByType(TYPE_CZLX);
    }

    /**
     * 操作类型字典MAP.
     */
    public static Map<String, DictionaryDTO> CZLX_MAP() {
        List<DictionaryDTO> dictionaryDTOList = CZLX_DICS();
        Map<String, DictionaryDTO> CZLX_MAP = Maps.newHashMap();
        for (DictionaryDTO dictionaryDTO : dictionaryDTOList) {
            CZLX_MAP.put(dictionaryDTO.getCode(), dictionaryDTO);
        }
        return CZLX_MAP;
    }

    /**
     * 操作类型-登录.
     */
    public static DictionaryDTO CZLX_DL() {
        return DictionaryManager.me().getByCode(TYPE_CZLX, CZLX_DL);
    }

    /**
     * 操作类型-登录ID.
     */
    public static Long CZLX_DL_ID() {
        return CZLX_DL().getId();
    }

    /**
     * 操作类型-登录名称.
     */
    public static String CZLX_DL_NAME() {
        return CZLX_DL().getName();
    }

    /**
     * 操作类型-登录值.
     */
    public static Integer CZLX_DL_VAL() {
        return CZLX_DL().getValue();
    }

    /**
     * 操作类型-登出.
     */
    public static DictionaryDTO CZLX_DC() {
        return DictionaryManager.me().getByCode(TYPE_CZLX, CZLX_DC);
    }

    /**
     * 操作类型-登出ID.
     */
    public static Long CZLX_DC_ID() {
        return CZLX_DC().getId();
    }

    /**
     * 操作类型-登出名称.
     */
    public static String CZLX_DC_NAME() {
        return CZLX_DC().getName();
    }

    /**
     * 操作类型-登出值.
     */
    public static Integer CZLX_DC_VAL() {
        return CZLX_DC().getValue();
    }

    /**
     * 操作类型-启用.
     */
    public static DictionaryDTO CZLX_QY() {
        return DictionaryManager.me().getByCode(TYPE_CZLX, CZLX_QY);
    }

    /**
     * 操作类型-启用ID.
     */
    public static Long CZLX_QY_ID() {
        return CZLX_QY().getId();
    }

    /**
     * 操作类型-启用名称.
     */
    public static String CZLX_QY_NAME() {
        return CZLX_QY().getName();
    }

    /**
     * 操作类型-启用值.
     */
    public static Integer CZLX_QY_VAL() {
        return CZLX_QY().getValue();
    }

    /**
     * 操作类型-停用.
     */
    public static DictionaryDTO CZLX_TY() {
        return DictionaryManager.me().getByCode(TYPE_CZLX, CZLX_TY);
    }

    /**
     * 操作类型-停用ID.
     */
    public static Long CZLX_TY_ID() {
        return CZLX_TY().getId();
    }

    /**
     * 操作类型-停用名称.
     */
    public static String CZLX_TY_NAME() {
        return CZLX_TY().getName();
    }

    /**
     * 操作类型-停用值.
     */
    public static Integer CZLX_TY_VAL() {
        return CZLX_TY().getValue();
    }
}
