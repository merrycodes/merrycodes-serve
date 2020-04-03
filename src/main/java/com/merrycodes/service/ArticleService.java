package com.merrycodes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.entity.Article;

/**
 * @author MerryCodes
 * @date 2020/3/30 15:56
 */
public interface ArticleService extends IService<Article> {

    /**
     * 根据文章id获取文章
     *
     * @param id 文章id
     * @return 文章实体类 {@link Article}
     */
    Article getArticleInfo(Integer id);

}
