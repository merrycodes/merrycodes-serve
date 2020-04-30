package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.time.LocalDateTime;

/**
 * 网站设置（key-value形式存储）
 *
 * @author MerryCodes
 * @date 2020/4/29 11:06
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Setting {

    /**
     * 网站设置id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 网站设置的key
     */
    private String settingKey;

    /**
     * 网站设置的key
     */
    private String settingValue;

    /**
     * 网站设置的创建时间
     */
    private LocalDateTime createTime;

    /**
     * 网站设置的更新时间
     */
    private LocalDateTime updateTime;

    @Tolerate
    public Setting() {
    }
}
