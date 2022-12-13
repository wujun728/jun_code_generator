package com.jeasy.base.event;

import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * WebHook 异步消息
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Component
@EnableAsync(mode = AdviceMode.ASPECTJ)
public class WebHooksListener implements ApplicationListener<WebHooksEvent> {

    public static final String PUSH_HOOKS = "push_hooks";
    @Value("${git.hook.pwd}")
    private String hookPwd;
    @Value("${git.hook.script_path}")
    private String scriptPath;

    /**
     * 新线程需要同时 @EnableAsync + @Async
     */
    @Async
    @Override
    public void onApplicationEvent(final WebHooksEvent event) {
        Map<String, Object> hookMap = (Map<String, Object>) event.getSource();
        Object password = hookMap.get("password");
        // 密码不一致
        if (null == password || !password.equals(hookPwd)) {
            return;
        }
        Object hookName = hookMap.get("hook_name");
        if (Func.isEmpty(hookName) || !StrKit.equalsIgnoreCase(PUSH_HOOKS, hookName.toString())) {
            return;
        }
        // 执行自动部署脚本
        runShell(scriptPath);
    }

    /**
     * 运行shell
     *
     * @param script
     */
    public static void runShell(final String script) {
        Process process = null;
        try {
            String[] cmd = {"sh", script};
            //执行liunx命令
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            if (null != process) {
                process.destroy();
            }
        }
    }
}
