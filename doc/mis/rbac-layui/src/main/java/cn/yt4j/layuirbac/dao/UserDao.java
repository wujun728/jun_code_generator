package cn.yt4j.layuirbac.dao;

import cn.yt4j.layuirbac.model.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户(User)表数据库访问层
 *
 * @author gyv12345@163.com
 */
public interface UserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User getById(Integer id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> list(User user);

    /**
     *  通过用户名查询
     * @param username
     * @return
     */
    User getByUsername(@Param("username") String username);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);



}