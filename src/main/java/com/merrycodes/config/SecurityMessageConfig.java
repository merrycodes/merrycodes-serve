package com.merrycodes.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Spring Security 异常信息中文化
 *
 * @author MerryCodes
 * @date 2020/5/7 12:51
 */
@Configuration
public class SecurityMessageConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages_zh_CN");
        return messageSource;
    }

}
