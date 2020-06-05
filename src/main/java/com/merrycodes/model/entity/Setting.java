package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.model.entity.abstracts.AbstractBaseEntiry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 网站设置（key-value形式存储）
 *
 * @author MerryCodes
 * @date 2020/4/29 11:06
 */
@Data
@Builder
@ApiModel(description = "网站设置")
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Setting extends AbstractBaseEntiry implements Serializable {

    private static final long serialVersionUID = -4183424349092027069L;

    /**
     * 网站设置的key
     */
    @ApiModelProperty("网站设置id")
    private String settingKey;

    /**
     * 网站设置的key
     */
    @ApiModelProperty("网站设置id")
    private String settingValue;

    @Tolerate
    public Setting() {
    }
}
