package com.jeasy.shiro.controller;

import com.jeasy.base.web.controller.ControllerSupport;
import com.jeasy.base.web.csrf.CsrfToken;
import com.jeasy.base.web.dto.CurrentUser;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.base.web.resolver.FromJson;
import com.jeasy.common.Func;
import com.jeasy.common.security.PasswordHash;
import com.jeasy.common.str.StrKit;
import com.jeasy.doc.annotation.MethodDoc;
import com.jeasy.doc.annotation.StatusEnum;
import com.jeasy.exception.MessageException;
import com.jeasy.shiro.service.CaptchaCacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录退出
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Controller
public class LoginController extends ControllerSupport {

    private static final String REDIRECT_LOGIN = "redirect:/#/login";

    private static final String REDIRECT_HOME = "redirect:/#/home";

    @Autowired
    private CaptchaCacheService captchaCacheService;

    /**
     * S_GET 登录 shiro 写法
     */
    @CsrfToken(create = true)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            return REDIRECT_HOME;
        }
        return REDIRECT_LOGIN;
    }

    /**
     * POST 登录 shiro 写法
     */
    @MethodDoc(entity = LoginResDTO.class, desc = {"5.登录管理", "2.用户登录", "1.用户登录接口"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2015-07-31")
    @CsrfToken(remove = true)
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public void login(@FromJson LoginReqDTO loginDTO) {

        boolean isCheckSuccess = captchaCacheService.validate(request, response, loginDTO.getCaptcha());
        if (!isCheckSuccess) {
            responseMessage(ModelResult.CODE_500, "验证码错误!");
            return;
        }

        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginDTO.getUsername(), loginDTO.getPassword());

        // 设置记住密码
        boolean isRememberMe = true;
        if (Func.isNotEmpty(loginDTO.getRememberMe())) {
            isRememberMe = 1 == loginDTO.getRememberMe();
        }
        token.setRememberMe(isRememberMe);

        try {
            user.login(token);
        } catch (UnknownAccountException e) {
            responseMessage(ModelResult.CODE_200, "账号不存在!");
            return;
        } catch (DisabledAccountException e) {
            responseMessage(ModelResult.CODE_200, "账号未启用!");
            return;
        } catch (IncorrectCredentialsException e) {
            responseMessage(ModelResult.CODE_200, "密码错误!");
            return;
        } catch (AuthenticationException e) {
            if (MessageException.class.isInstance(e.getCause())) {
                MessageException me = (MessageException) e.getCause();
                responseMessage(me.getCode(), me.getMessage());
            } else {
                responseMessage(ModelResult.CODE_500, e.getMessage());
            }
            return;
        } catch (Throwable e) {
            responseMessage(ModelResult.CODE_500, e.getMessage());
            return;
        }

        // 返回当前登录用户信息
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getPrincipal();
        LoginResDTO loginResDTO = new LoginResDTO();
        loginResDTO.setAccess(currentUser.getName());
        loginResDTO.setAvatar("https://avatars0.githubusercontent.com/u/20942571?s=460&v=4");
        loginResDTO.setToken(currentUser.getName());
        loginResDTO.setName(currentUser.getName());
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, loginResDTO);
    }


    /**
     * POST 登录 shiro 写法
     */
    @MethodDoc(entity = LoginResDTO.class, desc = {"5.登录管理", "2.用户登录", "2.获取当前登录用户信息接口"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2015-07-31")
    @CsrfToken(remove = true)
    @RequestMapping(value = "/userInfo", method = {RequestMethod.POST})
    @ResponseBody
    public void userInfo() {
        // 返回当前登录用户信息
        CurrentUser currentUser = (CurrentUser) SecurityUtils.getSubject().getPrincipal();
        LoginResDTO loginResDTO = new LoginResDTO();
        loginResDTO.setAccess(currentUser.getName());
        loginResDTO.setAvatar("https://avatars0.githubusercontent.com/u/20942571?s=460&v=4");
        loginResDTO.setToken(currentUser.getName());
        loginResDTO.setName(currentUser.getName());
        responseEntity(ModelResult.CODE_200, ModelResult.SUCCESS, loginResDTO);
    }

    /**
     * 未授权
     */
    @RequestMapping(value = "/unauth", method = {RequestMethod.GET})
    public String unauth() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            return REDIRECT_LOGIN;
        }
        return "unauth";
    }

    @MethodDoc(desc = {"5.登录管理", "2.用户登录", "3.验证码接口"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2015-07-31")
    @CsrfToken(create = true)
    @RequestMapping("/captcha.jpg")
    @ResponseBody
    public void captcha() {
        captchaCacheService.generate(request, response);
    }

    /**
     * 退出
     */
    @MethodDoc(desc = {"5.登录管理", "3.用户登出", "1.用户登出接口"}, status = StatusEnum.DONE, author = "taomk", finishTime = "2015-07-31")
    @RequestMapping(value = "/logout", method = {RequestMethod.POST})
    @ResponseBody
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        responseMessage(ModelResult.CODE_200, ModelResult.SUCCESS);
    }

    public static void main(String[] args) {
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.setAlgorithmName("md5");
        passwordHash.setHashIterations(1);

        System.out.println(passwordHash.toHex("admin", "abc"));
        System.out.println(System.getProperty("java.io.tmpdir"));
    }
}
