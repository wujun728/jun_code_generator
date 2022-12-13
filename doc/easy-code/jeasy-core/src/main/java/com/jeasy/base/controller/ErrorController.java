package com.jeasy.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    /**
     * 400页面地址
     */
    private static final String ERROR_400 = "error/400";

    /**
     * 401页面地址
     */
    private static final String ERROR_401 = "error/401";

    /**
     * 403页面地址
     */
    private static final String ERROR_403 = "error/403";

    /**
     * 404页面地址
     */
    private static final String ERROR_404 = "error/404";

    /**
     * 405页面地址
     */
    private static final String ERROR_405 = "error/405";

    /**
     * 500页面地址
     */
    private static final String ERROR_500 = "error/500";

    @RequestMapping("/error400")
    public ModelAndView error400() {
        return new ModelAndView(ERROR_400);
    }

    @RequestMapping("/error401")
    public ModelAndView error401() {
        return new ModelAndView(ERROR_401);
    }

    @RequestMapping("/error403")
    public ModelAndView error403() {
        return new ModelAndView(ERROR_403);
    }

    @RequestMapping("/error404")
    public ModelAndView error404() {
        return new ModelAndView(ERROR_404);
    }

    @RequestMapping("/error405")
    public ModelAndView error405() {
        return new ModelAndView(ERROR_405);
    }

    @RequestMapping("/error500")
    public ModelAndView error500() {
        return new ModelAndView(ERROR_500);
    }
}
