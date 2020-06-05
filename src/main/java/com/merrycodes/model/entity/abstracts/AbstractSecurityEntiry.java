package com.merrycodes.model.entity.abstracts;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Security 抽象实体类
 *
 * @author MerryCodes
 * @date 2020/5/5 13:27
 */
@Data
public abstract class AbstractSecurityEntiry implements Serializable {

    /**
     * 创建者，默认当前操作用户
     */
    @ApiModelProperty("创建者")
    private String createBy;

    /**
     * 更新者，默认当前操作用户
     */
    @ApiModelProperty("更新者")
    private String updateBy;

    /**
     * 创建时间，默认为当前时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间，默认为当前时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
