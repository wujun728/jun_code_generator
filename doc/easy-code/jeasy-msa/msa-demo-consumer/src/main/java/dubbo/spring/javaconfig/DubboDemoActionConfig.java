package dubbo.spring.javaconfig;

import com.jeasy.DemoJavaConfigAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
@Configuration
public class DubboDemoActionConfig {

    @Bean
    public DemoJavaConfigAction demoAnnotationAction() {
        return new DemoJavaConfigAction();
    }
}
