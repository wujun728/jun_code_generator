package com.jeasy.roleresource.biz;

import com.jeasy.common.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 角色资源 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class RoleResourceBiz {

    public static RoleResourceBiz me() {
        return SpringContextHolder.getBean(RoleResourceBiz.class);
    }
}
