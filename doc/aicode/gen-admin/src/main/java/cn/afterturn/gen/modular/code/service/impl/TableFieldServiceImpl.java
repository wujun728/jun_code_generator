package cn.afterturn.gen.modular.code.service.impl;

import cn.afterturn.gen.core.model.enmus.BooleanType;
import cn.afterturn.gen.modular.code.model.TableBaseFieldModel;
import cn.afterturn.gen.modular.code.service.ITableBaseFieldService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import cn.afterturn.gen.core.support.CollectionKit;
import cn.afterturn.gen.modular.code.dao.TableFieldDao;
import cn.afterturn.gen.modular.code.model.TableFieldDbinfoModel;
import cn.afterturn.gen.modular.code.model.TableFieldModel;
import cn.afterturn.gen.modular.code.model.TableFieldVerifyModel;
import cn.afterturn.gen.modular.code.service.ITableFieldDbinfoService;
import cn.afterturn.gen.modular.code.service.ITableFieldService;
import cn.afterturn.gen.modular.code.service.ITableFieldVerifyService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service
 *
 * @author JueYue
 * @Date 2017-09-20 09:22
 */
@Service
public class TableFieldServiceImpl implements ITableFieldService {

    @Autowired
    private TableFieldDao tableFieldDao;

    @Autowired
    private ITableFieldVerifyService tableFieldVerifyService;
    @Autowired
    private ITableFieldDbinfoService tableFieldDbinfoService;
    @Autowired
    private ITableBaseFieldService tableBaseFieldService;

    @Override
    @Transactional
    public Integer insert(TableFieldModel entity) {
        return tableFieldDao.insert(entity);
    }

    @Override
    public Integer deleteById(Integer id) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("table_id", id);
        List<TableFieldModel> list = tableFieldDao.selectByMap(temp);
        if (CollectionKit.isNotEmpty(list)) {
            List<Integer> ids = getIds(list);
            tableFieldDao.deleteBatchIds(ids);
            tableFieldVerifyService.deleteByFieldIds(ids);
            tableFieldDbinfoService.deleteByFieldIds(ids);
        }
        return list.size();
    }

    @Override
    public Integer updateById(TableFieldModel entity) {
        return tableFieldDao.updateById(entity);
    }

    @Override
    public TableFieldModel selectById(Integer id) {
        return tableFieldDao.selectById(id);
    }

    @Override
    public TableFieldModel selectOne(TableFieldModel entity) {
        return tableFieldDao.selectOne(entity);
    }

    @Override
    public Integer selectCount(TableFieldModel model) {
        return tableFieldDao.selectCount(model);
    }

    @Override
    public List<TableFieldModel> selectList(TableFieldModel model) {
        return tableFieldDao.selectList(model);
    }

    @Override
    public List<TableFieldModel> selectPage(Pagination pagination, TableFieldModel model, Wrapper<TableFieldModel> wrapper) {
        return tableFieldDao.selectPage(pagination, model, wrapper);
    }

    @Override
    public void batchSaveOrUpdate(List<TableFieldModel> tableFields, int userId) {
        // 删除旧数据
        int isUpdate = deleteById(tableFields.get(0).getTableId());
        //设置默认值
        for (int i = 0; i < tableFields.size(); i++) {
            //只在新增的时候处理数据,修改的时候不处理
            if(isUpdate == 0){
                //判断是不是基础参数如果是,就是用基础参数
                TableBaseFieldModel baseFieldModel = tableBaseFieldService.queryBaseField(tableFields.get(i).getFieldName().toUpperCase(), userId);
                if (baseFieldModel != null) {
                    if (baseFieldModel.getFieldModel() != null && tableFields.get(i) != null) {
                        BeanUtils.copyProperties(baseFieldModel.getFieldModel(), tableFields.get(i),
                                "id", "tableId", "verifyModel", "dbinfoModel");
                    }
                    if (baseFieldModel.getVerifyModel() != null &&
                            tableFields.get(i).getVerifyModel() != null) {
                        BeanUtils.copyProperties(baseFieldModel.getVerifyModel(),
                                tableFields.get(i).getVerifyModel(), "id", "fieldId");
                    }
                } else {
                    if (tableFields.get(i).getIsQuery() == null) {
                        tableFields.get(i).setIsQuery(BooleanType.YES.getIntD());
                    }
                    if (tableFields.get(i).getIsShowAdd() == null) {
                        tableFields.get(i).setIsShowAdd(BooleanType.YES.getIntD());
                    }
                    if (tableFields.get(i).getIsShowDetail() == null) {
                        tableFields.get(i).setIsShowDetail(BooleanType.YES.getIntD());
                    }
                    if (tableFields.get(i).getIsShowEdit() == null) {
                        tableFields.get(i).setIsShowEdit(BooleanType.YES.getIntD());
                    }
                    if (tableFields.get(i).getIsQuery() == null) {
                        tableFields.get(i).setIsQuery(BooleanType.YES.getIntD());
                    }
                    if (tableFields.get(i).getIsShowList() == null) {
                        tableFields.get(i).setIsShowList(BooleanType.YES.getIntD());
                    }
                    if (tableFields.get(i).getIsExport() == null) {
                        tableFields.get(i).setIsExport(BooleanType.NO.getIntD());
                    }
                    if (tableFields.get(i).getIsImport() == null) {
                        tableFields.get(i).setIsImport(BooleanType.NO.getIntD());
                    }
                }
            }
        }
        tableFieldDao.batchInsert(tableFields);

        List<TableFieldVerifyModel> verifyModelList = new ArrayList<TableFieldVerifyModel>(tableFields.size());
        List<TableFieldDbinfoModel> dbInfoModelList = new ArrayList<TableFieldDbinfoModel>(tableFields.size());
        for (int i = 0; i < tableFields.size(); i++) {
            tableFields.get(i).getVerifyModel().setFieldId(tableFields.get(i).getId());
            verifyModelList.add(tableFields.get(i).getVerifyModel());
            tableFields.get(i).getDbinfoModel().setFieldId(tableFields.get(i).getId());
            tableFields.get(i).getDbinfoModel().setFieldName(tableFields.get(i).getFieldName());
            dbInfoModelList.add(tableFields.get(i).getDbinfoModel());
        }
        tableFieldVerifyService.batchInsert(verifyModelList);
        tableFieldDbinfoService.batchInsert(dbInfoModelList);

    }

    @Override
    public List<TableFieldModel> selectByTableId(Integer tableId) {
        TableFieldModel entity = new TableFieldModel();
        entity.setTableId(tableId);
        List<TableFieldModel> list = selectList(entity);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setVerifyModel(tableFieldVerifyService.
                    selectOne(new TableFieldVerifyModel(list.get(i).getId())));
            list.get(i).setDbinfoModel(tableFieldDbinfoService.
                    selectOne(new TableFieldDbinfoModel(list.get(i).getId())));
        }
        return list;
    }

    /**
     * @param list
     * @return
     */
    private List<Integer> getIds(List<TableFieldModel> list) {
        List<Integer> ids = new ArrayList<Integer>(list.size());
        for (int i = 0; i < list.size(); i++) {
            ids.add(list.get(i).getId());
        }
        return ids;
    }

}
