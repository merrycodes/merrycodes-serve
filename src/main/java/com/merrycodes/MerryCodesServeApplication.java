package com.merrycodes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Miracle.Lt
 * @date 2020/3/30 12:15
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.merrycodes.mapper")
public class MerryCodesServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MerryCodesServeApplication.class, args);
    }

}
