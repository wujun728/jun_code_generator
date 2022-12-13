package com.jeasy.userorg.biz;

import com.jeasy.common.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户机构 Biz
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
@Component
public class UserOrgBiz {

    public static UserOrgBiz me() {
        return SpringContextHolder.getBean(UserOrgBiz.class);
    }
}
