package cn.yt4j.layuirbac.service.impl;

import cn.yt4j.layuirbac.dao.MenuDao;
import cn.yt4j.layuirbac.dao.UserDao;
import cn.yt4j.layuirbac.model.Menu;
import cn.yt4j.layuirbac.model.User;
import cn.yt4j.layuirbac.security.RbacUser;
import cn.yt4j.layuirbac.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gyv12345@163.com
 */
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final MenuDao menuDao;

    private final PasswordEncoder encoder;

    @Override
    public RbacUser loadByUsername(String username) {
        User user = this.userDao.getByUsername(username);
        List<Menu> menus = this.menuDao.listByRoleId(user.getRoleId());
        List<GrantedAuthority> authorities = menus
                .stream()
                .map(menu -> new SimpleGrantedAuthority(menu.getMenuCode()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(user.getRoleCode()));
        RbacUser rbacUser = new RbacUser(user.getUsername(), user.getPassword(), authorities);
        return rbacUser;
    }

    @Override
    public PageInfo<User> list(Integer page, Integer limit, User user) {
        PageHelper.startPage(page, limit);
        List<User> list = this.userDao.list(user);
        PageInfo info = new PageInfo(list);
        return info;
    }

    @Override
    public User get(Integer id) {
        return this.userDao.getById(id);
    }

    @Override
    public Boolean add(User user) {
        user.setPassword(encoder.encode("123456"));
        this.userDao.insert(user);
        return Boolean.TRUE;
    }

    @Override
    public Boolean update(User user) {
        this.userDao.update(user);
        return Boolean.TRUE;
    }

    @Override
    public Boolean delete(Integer id) {
        if (id.equals(1)) {
            throw new RuntimeException("超级管理员不要删除");
        }

        this.userDao.deleteById(id);
        return Boolean.TRUE;
    }
}
