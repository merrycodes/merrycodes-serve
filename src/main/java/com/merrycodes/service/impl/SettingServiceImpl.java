package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.mapper.SettingMapper;
import com.merrycodes.model.entity.Setting;
import com.merrycodes.service.intf.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_SETTING;

/**
 * 网站设置service接口实现类
 *
 * @author MerryCodes
 * @date 2020/4/29 11:34
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

    private final SettingMapper settingMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(Map<String, String> settingMap) {
        settingMap.forEach(this::save);
    }

    /**
     * 保存网站设置
     *
     * @param settingKey   网站设置的key
     * @param settingValue 网站设置的value
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void save(String settingKey, String settingValue) {
        Setting setting = findSystemSettingByKey(settingKey);
        if (setting != null) {
            setting.setSettingValue(settingValue);
            settingMapper.updateById(setting);
        } else {
            setting = Setting.builder().settingKey(settingKey).settingValue(settingValue).build();
            settingMapper.insert(setting);
        }
    }

    /**
     * 通过网站设置的key查找
     *
     * @param settingKey 网站设置的key
     * @return 网站设置实体类 {@link Setting}
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_SETTING, key = "'setting['+#settingKey+']'")
    public Setting findSystemSettingByKey(String settingKey) {
        LambdaQueryWrapper<Setting> wrapper = Wrappers.<Setting>lambdaQuery()
                .select(Setting::getId, Setting::getSettingKey, Setting::getSettingValue)
                .eq(Setting::getSettingKey, settingKey);
        return settingMapper.selectOne(wrapper);
    }

    /**
     * 查找所有的网站设置，以Map返回
     *
     * @return 网站设置集合
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_SETTING, key = "'settingMap'")
    public Map<String, String> selectSettingMap() {
        LambdaQueryWrapper<Setting> wrapper = Wrappers.<Setting>lambdaQuery()
                .select(Setting::getSettingKey, Setting::getSettingValue);
        List<Setting> options = settingMapper.selectList(wrapper);
        return options.stream().collect(Collectors.toMap(Setting::getSettingKey, Setting::getSettingValue));
    }
}
