package com.merrycodes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 *
 * @author MerryCodes
 * @date 2020/4/2 20:27
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("spring.profiles.active")
    private String profile;

    private static final String VERSION = "1.0.0";

    private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.merrycodes";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .version(VERSION)
                .title("MerryCode")
                .description("MerryCodes API 接口文档")
                .termsOfServiceUrl("")
                .license("Apache 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("MerryCodes", "http://www.baidu.com", "merrycodes@163.com"))
                .build();
    }

    @Bean
    public Docket restfulApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .enable("dev".equals(profile));
    }

}
