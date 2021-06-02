package cn.afterturn.gen.modular.code.service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.modular.code.model.GenParamModel;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.List;
import java.util.Map;


/**
 * 生成参数Service
 *
 * @author JueYue
 * @Date 2017-09-13 09:14
 */
public interface IGenParamService {

     /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(GenParamModel entity);

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
    Integer updateById(GenParamModel entity);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return GenParamModel
     */
    GenParamModel selectById(Integer id);

    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return GenParamModel
     */
    GenParamModel selectOne(GenParamModel entity);

    /**
     * <p>
     * 根据 model 条件，查询总记录数
     * </p>
     *
     * @param model 实体对象
     * @return int
     */
    Integer selectCount(GenParamModel model);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<GenParamModel>
     */
    List<GenParamModel> selectList(GenParamModel model);


    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param pagination 分页查询条件
     * @param model   实体对象封装操作��以为 null）
     * @param wrapper   SQL包装
     * @return List<GenParamModel>
     */
    List<GenParamModel> selectPage(Pagination pagination, GenParamModel model, Wrapper<GenParamModel> wrapper);

}
