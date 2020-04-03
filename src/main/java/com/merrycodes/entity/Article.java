package com.merrycodes.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author MerryCodes
 * @date 2020/3/30 15:36
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article implements Serializable {

    private static final long serialVersionUID = 926744113667809796L;

    /**
     * 文章id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章标签
     */
    private String tags;

    /**
     * 文章分类
     */
    private String category;

    /**
     * 文章内容-HTML格式
     */
    private String htmlContent;

    /**
     * 文章内容-Markdown格式
     */
    private String mdContent;

    /**
     * 文章概括内容-HTML格式
     */
    private String summaryContent;

    /**
     * 文章浏览量
     */
    private Integer browse;

    /**
     * 默认为已发布
     * 0表示草稿，1表示已发布，2表示取消发布
     */
    private Integer status;

    /**
     * 文章创建时间，默认为当前时间
     */
    private LocalDateTime createTime;

    /**
     * 文章更新时间，默认为当前时间
     */
    private LocalDateTime updateTime;

}