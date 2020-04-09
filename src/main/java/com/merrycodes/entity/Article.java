package com.merrycodes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.constant.WebConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章实体类
 *
 * @author MerryCodes
 * @date 2020/3/30 15:36
 */
@ApiModel(description = "文章实体类")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article implements Serializable {

    private static final long serialVersionUID = 926744113667809796L;

    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "文章id")
    private Integer id;

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
    @ApiModelProperty("0表示草稿，1表示已发布，2表示取消发布")
    private Integer status;

    /**
     * 文章创建时间，默认为当前时间
     */
    @ApiModelProperty("文章创建时间，默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 文章更新时间，默认为当前时间
     */
    @ApiModelProperty("文章更新时间，默认为当前时间")
    private LocalDateTime updateTime;

    /**
     * 列表排序 （default = desc）
     * 排序字段为更新时间
     */
    @TableField(exist = false)
    @ApiModelProperty("列表排序 （default = desc）")
    private String sort = WebConstant.DESC;

}