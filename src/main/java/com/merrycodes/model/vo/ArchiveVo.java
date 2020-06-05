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
 * 归档对象模型
 *
 * @author MerryCodes
 * @date 2020/4/23 21:40
 */
@Data
@ApiModel(description = "归档对象模型")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveVo implements Serializable {

    /**
     * 年份
     */
    @ApiModelProperty("年份")
    public String year;

    /**
     * 文章实体类集合
     *
     * @see Article
     */
    @ApiModelProperty("文章实体类集合")
    public List<Article> articleList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
