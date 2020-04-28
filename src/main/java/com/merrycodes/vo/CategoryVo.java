package com.merrycodes.vo;

import com.merrycodes.entity.Article;
import lombok.Data;

import java.util.List;

/**
 * 文章分类对象模型
 *
 * @author MerryCodes
 * @date 2020/4/22 22:21
 */
@Data
public class CategoryVo {

    private Integer id;

    private String name;

    private List<Article> articleList;
}
