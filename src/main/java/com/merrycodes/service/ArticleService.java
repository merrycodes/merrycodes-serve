package com.merrycodes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.entity.Article;

import java.util.List;

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
    Article selectArticleInfo(Integer id);


    /**
     * 获取文章实体类
     *
     * @return 文章列表实体类
     */
    List<Article> selectArticleList();


    /**
     * 获取文章实体类 (分页)
     *
     * @param page    分页模型 {@link Page}
     * @param article 文章实体类 {@link Article}
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Article> selectArticlePage(Page<Article> page, Article article);

}
