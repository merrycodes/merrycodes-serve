package com.merrycodes.controller.admin;

import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.service.intf.SettingService;
import com.merrycodes.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 网站设置
 *
 * @author MerryCodes
 * @date 2020/4/29 11:23
 */
@Slf4j
@RestController
@RequestMapping("/admin/setting")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SettingController {

    private final SettingService settingService;

    @PostMapping("/save")
    public ResponseVo<Integer> save(@RequestParam Map<String, String> settingMap) {
        settingMap.forEach((k, v) -> System.out.printf("%s:%s", k, v));
        settingService.save(settingMap);
        return ResponseUtils.success();
    }

    @GetMapping
    public ResponseVo<Map<String, String>> selectAllSetting() {
        Map<String, String> map = settingService.selectSettingMap();
        return ResponseUtils.success(map);
    }
}
