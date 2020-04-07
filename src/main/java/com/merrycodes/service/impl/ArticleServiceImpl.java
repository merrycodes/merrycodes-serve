package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.constant.WebConstant;
import com.merrycodes.entity.Article;
import com.merrycodes.mapper.ArticleMapper;
import com.merrycodes.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MerryCodes
 * @date 2020/3/30 15:56
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleMapper articleMapper;

    @Override
    public Article selectArticleInfo(Integer id) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .select(Article.class, article -> !"create_time".equals(article.getColumn()) &&
                        !"update_time".equals(article.getColumn()) &&
                        !"browse".equals(article.getColumn()))
                .eq(Article::getId, id);
        return articleMapper.selectOne(wrapper);

    }

    @Override
    public IPage<Article> selectArticlePage(Integer current, Integer size, Article article) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .eq(article.getStatus() != null, Article::getStatus, article.getStatus())
                .like(StringUtils.isNotEmpty(article.getTitle()), Article::getTitle, article.getTitle())
                .like(StringUtils.isNotEmpty(article.getMdContent()), Article::getMdContent, article.getMdContent())
                .like(StringUtils.isNotEmpty(article.getTags()), Article::getTags, article.getTags())
                .like(StringUtils.isNotEmpty(article.getCategory()), Article::getCategory, article.getCategory());
        if (WebConstant.ASC.equals(article.getSort())) {
            wrapper.orderByAsc(Article::getUpdateTime);
        } else {
            wrapper.orderByDesc(Article::getUpdateTime);
        }
        System.out.println(article);
        return articleMapper.selectPage(page, wrapper);
    }

}
