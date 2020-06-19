package com.merrycodes.model.form.query;

import com.merrycodes.constant.enums.StatusEnum;
import com.merrycodes.model.form.abstracts.AbstractSortForm;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 分类查询表单类
 *
 * @author MerryCodes
 * @date 2020/5/13 20:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章分类查询表单类")
public class CategoryQueryForm extends AbstractSortForm {

    /**
     * 文章分类状态
     * 0 表示分类失效，1 表示分类生效
     *
     * @see StatusEnum
     */
    @ApiModelProperty("文章分类状态")
    private Integer status;

    /**
     * 文章分类名
     */
    @ApiModelProperty("文章分类名")
    private String name;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
