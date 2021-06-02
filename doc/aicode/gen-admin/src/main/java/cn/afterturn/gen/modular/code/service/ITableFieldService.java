package cn.afterturn.gen.modular.code.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.modular.code.model.TableFieldModel;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;


/**
 * Service
 *
 * @author JueYue
 * @Date 2017-09-20 09:22
 */
public interface ITableFieldService {

     /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(TableFieldModel entity);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return int
     */
    Integer deleteById(Integer id);

    /**
     * <p>
     * 根据 ID 修改
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer updateById(TableFieldModel entity);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return TableFieldModel
     */
    TableFieldModel selectById(Integer id);

    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return TableFieldModel
     */
    TableFieldModel selectOne(TableFieldModel entity);

    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    Integer selectCount(TableFieldModel model);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<TableFieldModel>
     */
    List<TableFieldModel> selectList(TableFieldModel model);


    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param pagination 分页查询条件
     * @param model   实体对象封装操作可以为 null）
     * @param wrapper   SQL包装
     * @return List<TableFieldModel>
     */
    List<TableFieldModel> selectPage(Pagination pagination, TableFieldModel model,Wrapper<TableFieldModel> wrapper);

    /**
     * 批量保存或者更新
     * @param tableFields
     * @param userId
     */
    void batchSaveOrUpdate(List<TableFieldModel> tableFields, int userId);

    /**
     * 通过table查询集合
     * @param tableId
     * @return
     */
    List<TableFieldModel> selectByTableId(Integer tableId);
}
