package com.merrycodes.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.model.entity.Article;
import com.merrycodes.utils.ToStringStyleUtils;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 文章分类对象模型
 *
 * @author MerryCodes
 * @date 2020/4/22 22:21
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryVo implements Serializable {

    private Integer id;

    private String name;

    private List<Article> articleList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
