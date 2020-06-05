package com.merrycodes.model.form.query;

import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 角色查询表单类
 *
 * @author MerryCodes
 * @date 2020/6/2 12:07
 */
@Data
@ApiModel(description = "角色查询表当类")
public class RoleQueryForm implements Serializable {

    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String name;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }

}
