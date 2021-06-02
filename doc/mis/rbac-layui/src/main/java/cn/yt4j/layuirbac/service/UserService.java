package cn.yt4j.layuirbac.service;

import cn.yt4j.layuirbac.model.User;
import cn.yt4j.layuirbac.security.RbacUser;
import com.github.pagehelper.PageInfo;

/**
 * @author gyv12345@163.com
 */
public interface UserService {
    /**
     * 通过用户名称获取用户信息
     * @param username
     * @return
     */
    RbacUser loadByUsername(String username);

    /**
     *  查询用户集合
     * @param page 页数
     * @param limit 条数
     * @param user 用户对象
     * @return
     */
   PageInfo<User> list(Integer page, Integer limit,User user);

    /**
     * 通过ID获取用户信息
     * @param id
     * @return
     */
   User get(Integer id);

    /**
     *  添加
     * @param user
     * @return
     */
   Boolean add(User user);

    /**
     *  修改
     * @param user
     * @return
     */
    Boolean update(User user);

    /**
     *  删除
     * @param id
     * @return
     */
    Boolean delete(Integer id);
}
