package com.jeasy.fileattach.biz;

import com.jeasy.BaseJUnitTester4SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件附件 BizJUnitTest
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Slf4j
public class FileAttachBizJUnitTest extends BaseJUnitTester4SpringContext {

    @Autowired
    private FileAttachBiz fileAttachBiz;

    @Test
    public void testFind() {
        log.info("================> Test find success");
    }
}
