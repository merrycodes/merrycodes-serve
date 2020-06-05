package com.merrycodes.model.form.query;

import com.merrycodes.model.form.abstracts.AbstractSortForm;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 用户查询表单类
 *
 * @author MerryCodes
 * @date 2020/5/16 22:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户查询表单类")
public class UserQueryForm extends AbstractSortForm {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }

}
