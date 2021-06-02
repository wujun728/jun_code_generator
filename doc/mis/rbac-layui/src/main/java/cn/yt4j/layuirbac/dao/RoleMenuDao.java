package cn.yt4j.layuirbac.dao;

import cn.yt4j.layuirbac.model.RoleMenu;
import java.util.List;

/**
 * 角色权限(RoleMenu)表数据库访问层
 *
 * @author gyv12345@163.com
 */
public interface RoleMenuDao {


    /**
     * 通过实体作为筛选条件查询
     *
     * @param roleMenu 实例对象
     * @return 对象列表
     */
    List<RoleMenu> list(RoleMenu roleMenu);

    /**
     * 新增数据
     *
     * @param roleMenu 实例对象
     * @return 影响行数
     */
    int insert(RoleMenu roleMenu);

    /**
     * 修改数据
     *
     * @param roleMenu 实例对象
     * @return 影响行数
     */
    int update(RoleMenu roleMenu);

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 影响行数
     */
    int deleteById( );

}