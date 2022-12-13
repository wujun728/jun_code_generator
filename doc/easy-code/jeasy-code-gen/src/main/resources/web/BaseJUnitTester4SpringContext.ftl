package ${conf.basePackage};

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext-*.xml"})
@Slf4j
public class BaseJUnitTester4SpringContext {

    protected MockMvc mockMvc;

    protected MockHttpServletRequest request;

    protected MockHttpServletResponse response;

    @Autowired
    protected WebApplicationContext wac;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("log4j.configurationFile", "/Users/TaoBangren/git@osc/easy-code/jeasy-web/src/main/resources/dev/log4j2.xml");
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .alwaysDo(MockMvcResultHandlers.print())
            .build();
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }

    @After
    public void tearDown() {
    }

    @AfterClass
    public static void afterClass() {
    }
}
