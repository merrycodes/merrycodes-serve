package com.merrycodes.model.entity.abstracts;

import com.baomidou.mybatisplus.annotation.TableField;
import com.merrycodes.constant.consist.SortMapConsist;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 基础抽象实体类
 *
 * @author MerryCodes
 * @date 2020/5/5 12:08
 */
@Data
public abstract class AbstractBaseEntiry implements Serializable {

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

    /**
     * order by updateTime desc
     * 默认值 {name=update, sort=desc}
     * 前端传来是一个对象，使用 Map 接收
     *
     * @see SortMapConsist
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "列表排序 default = {\"name\":\"update\", \"sort\":\"desc\"})",
            example = "{\"name\":\"update\", \"sort\":\"desc\"}")
    private Map<String, String> sort;
}
