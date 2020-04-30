package com.merrycodes.service.intf;

import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.model.entity.Setting;

import java.util.Map;

/**
 * 网站设置service接口实现类
 *
 * @author MerryCodes
 * @date 2020/4/29 11:34
 */
public interface SettingService extends IService<Setting> {

    /**
     * 保存网站设置
     *
     * @param settingMap 网站设置集合
     */
    void save(Map<String, String> settingMap);

    /**
     * 保存网站设置
     *
     * @param settingKey   网站设置的key
     * @param settingValue 网站设置的value
     */
    void save(String settingKey, String settingValue);

    /**
     * 通过网站设置的key查找
     *
     * @param settingKey 网站设置的key
     * @return 网站设置实体类 {@link Setting}
     */
    Setting findSystemSettingByKey(String settingKey);

    /**
     * 查找所有的网站设置，已Map返回
     *
     * @return 网站设置集合
     */
    Map<String, String> selectSettingMap();
}
