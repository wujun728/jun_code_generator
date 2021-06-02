package cn.afterturn.gen.modular.code.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;

import cn.afterturn.gen.modular.code.model.TemplateFileModel;
import cn.afterturn.gen.modular.code.model.TemplateModel;


/**
 * 模板管理Service
 *
 * @author JueYue
 * @Date 2017-09-11 11:22
 */
public interface ITemplateService {

    /**
     * <p> 插入一条记录 </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer insert(TemplateModel entity, TemplateFileModel fileModel);

    /**
     * <p> 根据 ID 删除 </p>
     *
     * @param id 主键ID
     * @return int
     */
    Integer deleteById(Integer id);

    /**
     * <p> 根据 ID 修改 </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer updateById(TemplateModel entity, TemplateFileModel fileModel);

    /**
     * <p> 根据 ID 查询 </p>
     *
     * @param id 主键ID
     * @return TemplateModel
     */
    TemplateModel selectById(Integer id);

    /**
     * <p> 根据 entity 条件，查询一条记录 </p>
     *
     * @param entity 实体对象
     * @return TemplateModel
     */
    TemplateModel selectOne(TemplateModel entity);

    /**
     * <p> 根据 model 条件，查询总记录数 </p>
     *
     * @param model 实体对象
     * @return int
     */
    Integer selectCount(TemplateModel model);

    /**
     * <p> 根据 entity 条件，查询全部记录 </p>
     *
     * @param model 实体对象封装操作类（可以为 null）
     * @return List<TemplateModel>
     */
    List<TemplateModel> selectList(TemplateModel model);


    /**
     * <p> 根据 entity 条件，查询全部记录（并翻页） </p>
     *
     * @param pagination 分页查询条件
     * @param model      实体对象封装操作��以为 null）
     * @param wrapper    SQL包装
     * @return List<TemplateModel>
     */
    List<TemplateModel> selectPage(Pagination pagination, TemplateModel model, Wrapper<TemplateModel> wrapper);

    /**
     * 查询模板列表
     * @param templates
     * @return
     */
    List<TemplateModel> getTemplateByIds(String[] templates);
    /**
     * 查询这个组的所有的模板
     */
    List<TemplateModel> getAllTemplateByGroupId(String groupId);

    /**
     * 克隆一个公开的模板
     * @param groupId
     * @param userId
     */
    void cloneGroup(String groupId, Integer userId);
}
