package com.jeasy.userorg.biz;

import com.jeasy.BaseJUnitTester4SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户机构 BizJUnitTest
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
public class UserOrgBizJUnitTest extends BaseJUnitTester4SpringContext {

    @Autowired
    private UserOrgBiz userOrgBiz;

    @Test
    public void testFind() {
        log.info("================> Test find success");
    }
}
