package cn.yt4j.layuirbac.dao;

import cn.yt4j.layuirbac.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单按钮(Menu)表数据库访问层
 *
 * @author gyv12345@163.com
 */
public interface MenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Menu getById(Integer id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param menu 实例对象
     * @return 对象列表
     */
    List<Menu> list(Menu menu);

    /**
     *  通过角色ID查询菜单
     * @param roleId
     * @return
     */
    List<Menu> listByRoleId(@Param("roleId") Integer roleId);

    /**
     * 新增数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int insert(Menu menu);

    /**
     * 修改数据
     *
     * @param menu 实例对象
     * @return 影响行数
     */
    int update(Menu menu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);



}