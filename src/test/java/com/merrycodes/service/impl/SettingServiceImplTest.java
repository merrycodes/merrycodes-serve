package com.merrycodes.service.impl;

import com.merrycodes.service.intf.SettingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author MerryCodes
 * @date 2020/4/29 14:44
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SettingServiceImplTest {

    private SettingService settingService;

    @Autowired
    public void setSettingService(SettingService settingService) {
        this.settingService = settingService;
    }

    @Test
    public void test() {
        assertEquals("", "");
    }
}