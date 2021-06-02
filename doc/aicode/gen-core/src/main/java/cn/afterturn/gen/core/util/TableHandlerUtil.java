package cn.afterturn.gen.core.util;

import cn.afterturn.gen.core.model.enmus.DBType;
import cn.afterturn.gen.core.model.enmus.GenFieldType;
import cn.afterturn.gen.core.model.enmus.TypeConvertEnum;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import cn.afterturn.gen.core.model.GenFieldEntity;

/**
 * 表处理通用类
 *
 * @author JueYue on 2017/10/25.
 */
public class TableHandlerUtil {

    private TableHandlerUtil() {

    }

    /**
     * 处理一遍字段,根据字段类型做合适的处理
     */
    public static void handlerFields(List<GenFieldEntity> fields, DBType dbType) {
        GenFieldEntity entity;
        for (int i = 0, le = fields.size(); i < le; i++) {
            entity = fields.get(i);
            entity.setChinaName(getFieldName(entity.getFieldName(), entity.getComment()));
            if (entity.getChinaName().equals(entity.getComment())) {
                entity.setComment(null);
            }
            entity.setName(NameUtil.getFieldHumpName(entity.getFieldName()));
            entity.setType(convertType(dbType, entity.getFieldType(), entity.getFieldPointLength()));
        }
    }

    /**
     * 处理字段名称
     */
    public static String getFieldName(String fieldName, String comment) {
        if (StringUtils.isNotEmpty(comment)) {
            String[] nameAndComment = comment.split(",");
            return nameAndComment[0];
        }
        return NameUtil.getEntityHumpName(fieldName);
    }

    /**
     * 转换类型
     */
    public static String convertType(DBType dbType, String dataType, Integer pointLength) {
        dataType = dataType.toUpperCase();
        String type = TypeConvertEnum.getTypeByDb(dbType, dataType);
        if (type == null) {
            return GenFieldType.OBJECT.getType();
        }
        return type;
    }
}
