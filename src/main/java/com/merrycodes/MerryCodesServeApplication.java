package com.merrycodes;

import com.merrycodes.service.intf.InitApplicationService;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Miracle.Lt
 * @date 2020/3/30 12:15
 */
@EnableCaching
@SpringBootApplication
@MapperScan("com.merrycodes.mapper")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerryCodesServeApplication implements ApplicationRunner {

    private final InitApplicationService initApplicationService;

    public static void main(String[] args) {
        SpringApplication.run(MerryCodesServeApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        initApplicationService.initDataBase();
    }
}
