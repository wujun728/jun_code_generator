package cn.afterturn.gen.modular.code.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

import cn.afterturn.gen.modular.code.model.DbInfoModel;
import com.baomidou.mybatisplus.service.IService;


/**
 * 数据库管理Service
 *
 * @author JueYue
 * @Date 2017-09-11 11:15
 */
public interface IDbInfoService extends IService<DbInfoModel> {

    /**
     * <p>
     * 根据 model 条件，查询一条记录
     * </p>
     *
     * @param model 实体对象 非空
     * @return DbInfoModel
     */
    DbInfoModel selectOne(DbInfoModel model);

    /**
     * <p> 根据 entity 条件，查询全部记录 </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<DbInfoModel>
     */
    List<DbInfoModel> selectList(DbInfoModel model);

    /**
     * <p> 根据 entity 条件，查询全部记录 </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @param wrapper    SQL包装
     * @return List<DbInfoModel>
     */
    List<DbInfoModel> selectList(DbInfoModel model, Wrapper<DbInfoModel> wrapper);


    /**
     * <p> 根据 entity 条件，查询全部记录（并翻页） </p>
     *
     * @param pagination 分页查询条件
     * @param model      实体对象封装操作��以为 null）
     * @param wrapper    SQL包装
     * @return List<DbInfoModel>
     */
    List<DbInfoModel> selectPage(Pagination pagination, DbInfoModel model, Wrapper<DbInfoModel> wrapper);

}
