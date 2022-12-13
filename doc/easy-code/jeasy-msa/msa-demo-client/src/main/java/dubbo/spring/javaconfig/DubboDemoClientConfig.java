package dubbo.spring.javaconfig;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
@Configuration
public class DubboDemoClientConfig {

    public static final String APPLICATION_NAME = "consumer-of-helloworld-app";

    public static final String REGISTRY_ADDRESS = "zookeeper://127.0.0.1:2181";

    public static final String ANNOTATION_PACKAGE = "com.alibaba.dubbo.demo.consumer";

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(APPLICATION_NAME);
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(REGISTRY_ADDRESS);
        return registryConfig;
    }

    @Bean
    public AnnotationBean annotationBean() {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(ANNOTATION_PACKAGE);
        return annotationBean;
    }
}
