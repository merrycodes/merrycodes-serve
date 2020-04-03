package com.merrycodes.service.impl;

import com.merrycodes.entity.Article;
import com.merrycodes.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * ArticleServiceImpl 单元测试
 *
 * @author MerryCodes
 * @date 2020/4/3 21:28
 * @see ArticleServiceImpl
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceImplTest {

    private ArticleService articleService;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Test
    public void getArticleInfo() {
        Article article = articleService.getArticleInfo(4);
        System.out.println(article);
        assertEquals(new Integer(4), article.getId());
    }
}