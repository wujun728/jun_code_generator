package com.jeasy.user.biz;

import com.jeasy.BaseJUnitTester4SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户 BizJUnitTest
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
public class UserBizJUnitTest extends BaseJUnitTester4SpringContext {

    @Autowired
    private UserBiz userBiz;

    @Test
    public void testFind() {
        log.info("================> Test find success");
    }
}
