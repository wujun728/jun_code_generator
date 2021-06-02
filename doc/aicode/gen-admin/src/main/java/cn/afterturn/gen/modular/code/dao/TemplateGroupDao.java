package cn.afterturn.gen.modular.code.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import cn.afterturn.gen.modular.code.model.TemplateGroupModel;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TemplateGroupDao
 *
 * @author JueYue
 * @Date 2017-09-12 13:42
 */
@Repository
public interface TemplateGroupDao extends BaseMapper<TemplateGroupModel> {

    /**
     * 统计数量
     *
     * @param model
     * @return
     */
    Integer selectCount(@Param("e") TemplateGroupModel model);

    /**
     * 查询列表
     *
     * @param model
     * @return
     */
    List<TemplateGroupModel> selectList(@Param("e") TemplateGroupModel model);

    /**
     * 分页查询信息
     *
     * @param pagination
     * @param model
     * @param wrapper
     * @return
     */
    List<TemplateGroupModel> selectPage(@Param("p") Pagination pagination, @Param("e") TemplateGroupModel model, @Param("w") Wrapper<TemplateGroupModel> wrapper);

    /**
     * 分享
     *
     * @param id
     */
    void share(@Param("id") Integer id);
}
