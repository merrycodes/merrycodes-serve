package com.merrycodes.config;

import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import com.merrycodes.filter.RemoveAdvertisementFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Druid 配置类
 *
 * @author MerryCodes
 * @date 2020/5/14 22:54
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true")
public class DruidConfig {

    /**
     * 带有广告的common.js路径
     */
    private static final String FILE_PATH = "support/http/resources/js/common.js";

    /**
     * 触发构建广告的js代码
     */
    private static final String ORIGIN_JS = "this.buildFooter();";

    /**
     * 注释源代码
     */
    private static final String NEW_JS = "//this.buildFooter();";

    @Bean
    public FilterRegistrationBean<RemoveAdvertisementFilter> filterRegistrationBean(DruidStatProperties properties) throws IOException {
        // 获取web监控页面的参数
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // 提取common.js的配置路径
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
        // 获取common.js
        String text = Utils.readFromResource(FILE_PATH);
        // 屏蔽 this.buildFooter(); 不构建广告
        final String newJs = text.replace(ORIGIN_JS, NEW_JS);
        FilterRegistrationBean<RemoveAdvertisementFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RemoveAdvertisementFilter(newJs));
        // 过滤指定的文件
        registration.addUrlPatterns(commonJsPattern);
        return registration;
    }
}
