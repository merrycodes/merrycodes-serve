package com.merrycodes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Miracle.Lt
 * @date 2020/3/30 12:15
 */
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.merrycodes.mapper")
public class MerryCodesServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerryCodesServeApplication.class, args);
    }

}
