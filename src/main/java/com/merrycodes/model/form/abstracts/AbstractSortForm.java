package com.merrycodes.model.form.abstracts;

import com.merrycodes.constant.consist.SortMapConsist;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author MerryCodes
 * @date 2020/5/13 20:01
 */
@Data
public abstract class AbstractSortForm implements Serializable {

    /**
     * order by updateTime desc
     * 默认值 {name=update, sort=desc}
     * 前端传来是一个对象，使用 Map 接收
     *
     * @see SortMapConsist
     */
    @ApiModelProperty(value = "列表排序 default = {\"name\":\"update\", \"sort\":\"desc\"})",
            example = "{\"name\":\"update\", \"sort\":\"desc\"}")
    private Map<String, String> sort;
}
