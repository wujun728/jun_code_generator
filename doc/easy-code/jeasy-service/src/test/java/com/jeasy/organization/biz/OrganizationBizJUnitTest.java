package com.jeasy.organization.biz;

import com.jeasy.BaseJUnitTester4SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 机构 BizJUnitTest
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Slf4j
public class OrganizationBizJUnitTest extends BaseJUnitTester4SpringContext {

    @Autowired
    private OrganizationBiz organizationBiz;

    @Test
    public void testFind() {
        log.info("================> Test find success");
    }
}
