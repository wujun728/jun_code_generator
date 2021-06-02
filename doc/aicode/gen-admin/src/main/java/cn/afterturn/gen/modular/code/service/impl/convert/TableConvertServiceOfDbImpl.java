package cn.afterturn.gen.modular.code.service.impl.convert;

import cn.afterturn.gen.core.model.enmus.BooleanType;
import cn.afterturn.gen.core.model.enmus.QueryType;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import cn.afterturn.gen.core.model.GenBeanEntity;
import cn.afterturn.gen.core.model.GenFieldEntity;
import cn.afterturn.gen.modular.code.model.TableFieldDbinfoModel;
import cn.afterturn.gen.modular.code.model.TableFieldModel;
import cn.afterturn.gen.modular.code.model.TableFieldVerifyModel;
import cn.afterturn.gen.modular.code.model.TableInfoModel;
import cn.afterturn.gen.modular.code.model.TableServiceConfigModel;
import cn.afterturn.gen.modular.code.service.ITableConvertServer;
import cn.afterturn.gen.modular.code.service.ITableInfoService;

/**
 * 导入的数据库版本实现
 *
 * @author JueYue on 2017/10/25.
 */
@Service("dbTableConvertServer")
public class TableConvertServiceOfDbImpl implements ITableConvertServer {

    @Autowired
    private ITableInfoService tableInfoService;

    @Override
    public void importBean(String json, int userId) {
        GenBeanEntity bean = JSON.parseObject(json, GenBeanEntity.class);
        TableInfoModel entity = new TableInfoModel();
        entity.setClassName(bean.getName());
        if (StringUtils.isEmpty(bean.getChinaName())) {
            entity.setContent(bean.getTableName());
        } else {
            entity.setContent(bean.getChinaName());
        }
        entity.setTableName(bean.getTableName());
        entity.setUserId(userId);
        entity.setServiceConfig(getDefaultServceConfig());
        entity.setTableFields(getTableFields(bean.getFields()));
        tableInfoService.insert(entity, userId);
    }

    private List<TableFieldModel> getTableFields(List<GenFieldEntity> fields) {
        List<TableFieldModel> list = new ArrayList<TableFieldModel>();
        TableFieldModel fieldModel;
        GenFieldEntity tableField;
        TableFieldVerifyModel verifyModel;
        TableFieldDbinfoModel dbinfoModel;
        for (int i = 0; i < fields.size(); i++) {
            fieldModel = new TableFieldModel();
            tableField = fields.get(i);
            fieldModel.setFieldName(tableField.getFieldName());
            fieldModel.setName(tableField.getName());
            fieldModel.setContent(tableField.getChinaName());
            fieldModel.setType(tableField.getType());
            fieldModel.setIsKey(tableField.getKey());
            fieldModel.setIsQuery(BooleanType.YES.getIntD());
            fieldModel.setQueryMode(QueryType.EQ.getCode());
            fieldModel.setShowType(1);
            fieldModel.setOrderNum(2);
            fieldModel.setDictType(1);
            verifyModel = new TableFieldVerifyModel();
            verifyModel.setNotNull(tableField.getNotNull());
            fieldModel.setVerifyModel(verifyModel);
            dbinfoModel = new TableFieldDbinfoModel();
            dbinfoModel.setFieldName(tableField.getFieldName());
            dbinfoModel.setFieldContent(tableField.getComment());
            dbinfoModel.setFieldLength(tableField.getFieldLength());
            dbinfoModel.setFieldPointLength(tableField.getFieldPointLength());
            dbinfoModel.setFieldType(tableField.getFieldType());
            fieldModel.setDbinfoModel(dbinfoModel);
            list.add(fieldModel);
        }
        return list;
    }

    private List<TableServiceConfigModel> getDefaultServceConfig() {
        List<TableServiceConfigModel> list = new ArrayList<TableServiceConfigModel>();
        list.add(new TableServiceConfigModel("list", 1, 1, 2, "02"));
        list.add(new TableServiceConfigModel("add", 1, 1, 1, "01"));
        list.add(new TableServiceConfigModel("edit", 1, 1, 1, "01"));
        list.add(new TableServiceConfigModel("delete", 1, 1, 1, "01"));
        list.add(new TableServiceConfigModel("detail", 1, 1, 2, "02"));
        return list;
    }
}
