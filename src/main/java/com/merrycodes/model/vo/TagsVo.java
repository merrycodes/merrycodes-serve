package com.merrycodes.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.model.entity.Article;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 文章标签对象模型
 *
 * @author MerryCodes
 * @date 2020/4/22 23:11
 */
@Data
@ApiModel(description = "文章标签对象模型")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagsVo implements Serializable {

    /**
     * 文章标签id
     */
    @ApiModelProperty("文章标签id")
    private Integer id;

    /**
     * 文章标签名
     */
    @ApiModelProperty("文章标签名")
    private String name;

    /**
     * 文章实体类集合
     *
     * @see Article
     */
    @ApiModelProperty("文章实体类集合")
    private List<Article> articleList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
