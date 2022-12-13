package com.jeasy.base.controller;

import com.jeasy.base.event.WebHooksEvent;
import com.jeasy.base.web.controller.ControllerSupport;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.common.str.StrKit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * WebHooks 自动更新部署
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Controller
public class WebHooksController extends ControllerSupport {

    /**
     * git@osc WebHooks 设置
     * WIKI: http://git.oschina.net/oschina/git-osc/wikis/WebHook-%E4%BD%BF%E7%94%A8%E7%AE%80%E4%BB%8B
     */
    @RequestMapping(value = "/hook", method = {RequestMethod.POST})
    @ResponseBody
    public void hook() {
        String hook = request.getParameter("hook");
        if (StrKit.isBlank(hook)) {
            responseMessage(ModelResult.CODE_200, "json hook isBlank!");
        }
        Map hookMap = JsonKit.fromJson(hook, JsonKit.MAP_OBJ_TYPE_TOKEN.getType());

        // 发送事件 ThreadPoolTaskExecutor
        SpringContextHolder.publishEvent(new WebHooksEvent(hookMap));
        responseMessage(ModelResult.CODE_200, ModelResult.SUCCESS);
    }
}
