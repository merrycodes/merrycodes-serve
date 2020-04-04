package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public List<Article> selectArticleList() {
        return articleMapper.selectList(null);
    }

    @Override
    public IPage<Article> selectArticlePage(Page<Article> page, Article article) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        return articleMapper.selectPage(page, wrapper);
    }

}
