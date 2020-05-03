package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.merrycodes.model.entity.Setting;
import org.springframework.stereotype.Repository;

/**
 * 网站设置 curd操作
 *
 * @author MerryCodes
 * @date 2020/4/29 11:33
 */
@Repository
public interface SettingMapper extends BaseMapper<Setting> {
}
