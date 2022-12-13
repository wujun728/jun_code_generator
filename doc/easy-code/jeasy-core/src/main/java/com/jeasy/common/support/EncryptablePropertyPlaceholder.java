package com.jeasy.common.support;

import com.jeasy.common.security.AesKit;
import com.jeasy.common.ConvertKit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * Spring参数加密配置
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class EncryptablePropertyPlaceholder extends PropertyPlaceholderConfigurer {

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        try {
            for (Object key : props.keySet()) {
                if (ConvertKit.toStr(key).contains("encrypt")) {
                    String value = props.getProperty(ConvertKit.toStr(key));
                    if (null != value) {
                        try {
                            String desryptValue = AesKit.decrypt(value);
                            props.setProperty(ConvertKit.toStr(key), desryptValue);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            super.processProperties(beanFactory, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BeanInitializationException(e.getMessage());
        }
    }

}
