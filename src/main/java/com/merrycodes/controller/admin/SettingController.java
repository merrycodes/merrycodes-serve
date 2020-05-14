package com.merrycodes.controller.admin;

import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.service.intf.SettingService;
import com.merrycodes.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_SETTING;

/**
 * 网站设置
 *
 * @author MerryCodes
 * @date 2020/4/29 11:23
 */
@Api(value = "API - SettingController - admin", tags = "网站设置API")
@Slf4j
@RestController
@RequestMapping("/admin/setting")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SettingController {

    private final SettingService settingService;

    @PostMapping("/save")
    @ApiOperation(value = "保存网站设置", notes = "保存网站设置")
    @ApiImplicitParam(name = "settingMap", value = "网站设置Map", required = true, dataTypeClass = Map.class)
    @CacheEvict(cacheNames = CACHE_VALUE_SETTING, beforeInvocation = true, allEntries = true)
    public ResponseVo<Integer> save(@RequestParam Map<String, String> settingMap) {
        settingService.save(settingMap);
        return ResponseUtils.success();
    }

    @GetMapping
    @ApiOperation(value = "查询所有的网站设置", notes = "查询所有的网站设置")
    public ResponseVo<Map<String, String>> selectAllSetting() {
        Map<String, String> map = settingService.selectSettingMap();
        return ResponseUtils.success(map);
    }
}
