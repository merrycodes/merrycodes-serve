package com.merrycodes.model.entity.abstracts;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础抽象实体类
 *
 * @author MerryCodes
 * @date 2020/5/5 12:08
 */
@Data
public abstract class AbstractBaseEntiry implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;

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
