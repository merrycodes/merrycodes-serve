package com.merrycodes.model.vo;

import com.merrycodes.model.entity.Article;
import lombok.Data;

import java.util.List;

/**
 * 文章标签对象模型
 *
 * @author MerryCodes
 * @date 2020/4/22 23:11
 */
@Data
public class TagsVo {

    private Integer id;

    private String name;

    private List<Article> articleList;
}
