package cn.afterturn.gen.modular.code.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import cn.afterturn.gen.modular.code.model.TemplateModel;

/**
 * TemplateDao
 *
 * @author JueYue
 * @Date 2017-09-11 11:22
 */
@Repository
public interface TemplateDao extends BaseMapper<TemplateModel> {

    /**
     * 统计数量
     */
    Integer selectCount(@Param("e") TemplateModel model);

    /**
     * 查询列表
     */
    List<TemplateModel> selectList(@Param("e") TemplateModel model);

    /**
     * 分页查询信息
     */
    List<TemplateModel> selectPage(@Param("p") Pagination pagination, @Param("e") TemplateModel model, @Param("w") Wrapper<TemplateModel> wrapper);

    List<TemplateModel> getTemplateByIds(@Param("ids") String[] templates);

    List<TemplateModel> getAllTemplateByGroupId(String groupId);
}
