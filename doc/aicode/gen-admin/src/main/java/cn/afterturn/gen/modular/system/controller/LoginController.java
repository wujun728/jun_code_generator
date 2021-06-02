package cn.afterturn.gen.modular.system.controller;

import com.google.code.kaptcha.Constants;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.afterturn.gen.common.constant.state.ManagerStatus;
import cn.afterturn.gen.common.exception.BizExceptionEnum;
import cn.afterturn.gen.common.exception.InvalidKaptchaException;
import cn.afterturn.gen.common.persistence.dao.UserMapper;
import cn.afterturn.gen.common.persistence.model.User;
import cn.afterturn.gen.core.base.controller.BaseController;
import cn.afterturn.gen.core.log.LogManager;
import cn.afterturn.gen.core.log.factory.LogTaskFactory;
import cn.afterturn.gen.core.node.MenuNode;
import cn.afterturn.gen.core.shiro.ShiroKit;
import cn.afterturn.gen.core.shiro.ShiroUser;
import cn.afterturn.gen.core.util.ApiMenuFilter;
import cn.afterturn.gen.core.util.KaptchaUtil;
import cn.afterturn.gen.core.util.PatternUtil;
import cn.afterturn.gen.core.util.ToolUtil;
import cn.afterturn.gen.modular.system.dao.MenuDao;
import cn.afterturn.gen.modular.system.dao.UserMgrDao;

import static cn.afterturn.gen.core.support.HttpKit.getIp;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    MenuDao menuDao;

    @Resource
    private UserMgrDao managerDao;

    @Autowired
    UserMapper userMapper;

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/share.html";
        }
        List<MenuNode> menus = menuDao.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
        Integer id = ShiroKit.getUser().getId();
        User user = userMapper.selectById(id);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }

    /**
     * 跳转到注册页面
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "/register.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerVali(String username, String password) {
        User user = new User();
        user.setAccount(username.trim());
        user.setPassword(password.trim());
        // 判断账号是否重复
        User theUser = managerDao.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new UnknownAccountException(BizExceptionEnum.USER_ALREADY_REG.getMessage());
        }

        if (!PatternUtil.isPhone(username)) {
            throw new UnknownAccountException("手机号非法");
        }

        // 完善账号信息
        user.setName(username.trim());
        user.setBirthday(new Date());
        user.setSex(1);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());
        this.userMapper.insert(user);
        this.managerDao.setRoles(user.getId(), "6");

        return REDIRECT + "/login";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/login";
    }
}
