package com.merrycodes.model.entity;

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
 * 文章实体类
 *
 * @author MerryCodes
 * @date 2020/3/30 15:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章实体类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article extends AbstractBaseEntiry implements Serializable {

    private static final long serialVersionUID = 926744113667809796L;

    /**
     * 文章标题
     */
    @ApiModelProperty("文章标题")
    private String title;

    /**
     * 文章标签
     */
    @ApiModelProperty("文章标签")
    private String tags;

    /**
     * 文章分类
     */
    @ApiModelProperty("文章分类")
    private String category;

    /**
     * 文章内容-HTML格式
     */
    @ApiModelProperty("文章内容-HTML格式")
    private String htmlContent;

    /**
     * 文章内容-Markdown格式
     */
    @ApiModelProperty("文章内容-Markdown格式")
    private String mdContent;

    /**
     * 文章概括内容-HTML格式
     */
    @ApiModelProperty("文章概括内容-HTML格式")
    private String summaryContent;

    /**
     * 文章浏览量
     */
    @ApiModelProperty("文章浏览量")
    private Integer browse;

    /**
     * 默认为已发布
     * 0表示草稿，1表示已发布，2表示取消发布
     */
    @ApiModelProperty("文章状态")
    private Integer status;

    /**
     * 默认为true
     * 文章是否可以评论
     */
    @ApiModelProperty("文章是否可以评论，默认未ture")
    private Boolean allowComment;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}