package com.merrycodes.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.constant.enums.StatusEnum;
import com.merrycodes.model.entity.abstracts.AbstractBaseEntiry;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 分类实体类
 *
 * @author MerryCodes
 * @date 2020/4/13 22:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章分类实体类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category extends AbstractBaseEntiry implements Serializable {

    private static final long serialVersionUID = -5089249540992174478L;

    /**
     * 文章分类名
     */
    @ApiModelProperty("文章分类名")
    private String name;

    /**
     * 文章分类状态
     * 0 表示分类失效，1 表示分类生效
     *
     * @see StatusEnum
     */
    @ApiModelProperty("文章分类状态")
    private Integer status;

    /**
     * 文章分类文章数
     */
    @TableField(exist = false)
    @ApiModelProperty("文章分类文章数")
    private Integer count;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
