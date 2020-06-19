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
 * 文章标签查询表单类
 *
 * @author MerryCodes
 * @date 2020/5/13 20:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章标签查询表单类")
public class TagsQueryForm extends AbstractSortForm {

    /**
     * 文章标签状态
     * 0 表示标签失效，1 表示标签生效
     *
     * @see StatusEnum
     */
    @ApiModelProperty("文章标签状态")
    private Integer status;

    /**
     * 文章标签名
     */
    @ApiModelProperty("文章标签名")
    private String name;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
