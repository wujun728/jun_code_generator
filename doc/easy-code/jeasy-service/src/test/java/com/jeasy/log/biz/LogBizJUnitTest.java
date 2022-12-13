package com.jeasy.log.biz;

import com.jeasy.BaseJUnitTester4SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 日志 BizJUnitTest
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
public class LogBizJUnitTest extends BaseJUnitTester4SpringContext {

    @Autowired
    private LogBiz logBiz;

    @Test
    public void testFind() {
        log.info("================> Test find success");
    }
}
