package ${conf.basePackage};

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * 首页测试
 */
public class IndexTest extends BaseJUnitTester4SpringContext {

    /**
     * 参考链接：Spring MVC测试框架详解——服务端测试
     * <URL>http://jinnianshilongnian.iteye.com/blog/2004660</URL>
     */
    @Test
    public void index() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
            .get("/")
            .accept(MediaType.TEXT_HTML))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();
    }
}
