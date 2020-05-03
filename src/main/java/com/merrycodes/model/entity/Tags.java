package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.constant.consist.SortMapConsist;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 标签实体类
 *
 * @author MerryCodes
 * @date 2020/4/11 9:58
 */
@ApiModel(description = "标签实体类")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tags implements Serializable {

    private static final long serialVersionUID = 5439906147142478578L;

    /**
     * 文章标签id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("文章标签id")
    private Integer id;

    /**
     * 文章标签名
     */
    @ApiModelProperty("文章标签名")
    private String name;

    /**
     * 文章标签状态
     * 0 表示标签失效，1 表示标签生效
     *
     * @see com.merrycodes.constant.enums.StatusEnum
     */
    @ApiModelProperty("文章标签状态")
    private Integer status;

    /**
     * 文章标签文章数
     */
    @TableField(exist = false)
    @ApiModelProperty("文章标签文章数")
    private Integer count;

    /**
     * 文章标签创建时间，默认为当前时间
     */
    @ApiModelProperty("文章标签创建时间")
    private LocalDateTime createTime;

    /**
     * 文章标签更新时间，默认为当前时间
     */
    @ApiModelProperty("文章标签更新时间")
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
