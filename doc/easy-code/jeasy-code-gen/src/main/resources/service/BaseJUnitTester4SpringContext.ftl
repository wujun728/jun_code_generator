package ${conf.basePackage};

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext-*.xml"})
@Slf4j
public class BaseJUnitTester4SpringContext {

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("log4j.configurationFile", "/Users/TaoBangren/git@osc/easy-code/jeasy-web/src/main/resources/dev/log4j2.xml");
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @AfterClass
    public static void afterClass() {
    }
}
