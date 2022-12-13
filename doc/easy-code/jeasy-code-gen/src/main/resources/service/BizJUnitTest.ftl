package ${conf.basePackage}.${table.lowerCamelName}.biz;

import ${conf.basePackage}.BaseJUnitTester4SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ${table.comment} BizJUnitTest
 *
 * @author ${conf.author}
 * @version ${conf.version}
 * @since ${conf.createDate}
 */
@Slf4j
public class ${table.className}BizJUnitTest extends BaseJUnitTester4SpringContext {

    @Autowired
    private ${table.className}Biz ${table.camelName}Biz;

    @Test
    public void testFind() {
        log.info("================> Test find success");
    }
}
