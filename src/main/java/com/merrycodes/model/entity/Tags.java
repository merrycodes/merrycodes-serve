package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.model.entity.abstracts.AbstractBaseEntiry;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 标签实体类
 *
 * @author MerryCodes
 * @date 2020/4/11 9:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "标签实体类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tags extends AbstractBaseEntiry implements Serializable {

    private static final long serialVersionUID = 5439906147142478578L;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
