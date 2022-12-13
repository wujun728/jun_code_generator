package com.jeasy.resource.biz;

import com.jeasy.BaseJUnitTester4SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 菜单 BizJUnitTest
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
public class ResourceBizJUnitTest extends BaseJUnitTester4SpringContext {

    @Autowired
    private ResourceBiz resourceBiz;

    @Test
    public void testFind() {
        log.info("================> Test find success");
    }
}
