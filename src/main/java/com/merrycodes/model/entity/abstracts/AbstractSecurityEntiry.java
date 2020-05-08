package com.merrycodes.model.entity.abstracts;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Security 抽象实体类
 *
 * @author MerryCodes
 * @date 2020/5/5 13:27
 */
@Data
public abstract class AbstractSecurityEntiry {

    /**
     * 创建者，默认当前操作用户
     */
    private String createBy;

    /**
     * 更新者，默认当前操作用户
     */
    private String updateBy;

    /**
     * 创建创建，默认为当前时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间，默认为当前时间
     */
    private LocalDateTime updateTiem;
}
