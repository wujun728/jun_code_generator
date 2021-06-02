package org.coderfun.boot.web.router;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.coderfun.boot.core.BootSettings;
import org.coderfun.boot.core.entity.Permission;
import org.coderfun.boot.core.service.UserService;
import org.coderfun.boot.core.shiro.SessionUser;
import org.coderfun.boot.core.shiro.ShiroSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * 入口
 * 
 * @author klguang[klguang@foxmail.com]
 * @date 2018年11月10日
 */
@Controller
@RequestMapping(value = "{admin.path}")
public class AdminRouter {
	@Autowired
	UserService userService;

	/**
	 * 跳转到后台登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {// 已登录
			return "redirect:" + BootSettings.getAdminPath() + "/";
		}
		return "/admin/login";
	}

	/**
	 * 跳转到后台首页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	public String index(Model model, HttpSession session) {

		SessionUser sessionUser = ShiroSessionUtil.getSessionUser();
		if (sessionUser != null && !StringUtils.isEmpty(sessionUser.getLoginName())) {
			// 查询我的菜单
			List<Permission> menus = userService.queryMenus(sessionUser.getId());
			model.addAttribute("menus", menus);
		}
		return "/admin/index";
	}
}