package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.entity.Article;
import com.merrycodes.mapper.ArticleMapper;
import com.merrycodes.service.ArticleService;
import com.merrycodes.vo.PaginationVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    private ArticleMapper articleMapper;

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Test
    public void getArticleInfo() {
        Article article = articleService.selectArticleInfo(4);
        System.out.println(article);
        assertEquals(new Integer(4), article.getId());
    }

    @Test
    public void selectArticlePage() {
        IPage<Article> iPage = articleService.selectArticlePage(1, 10, new Article());
        PaginationVo<Article> paginationVo = new PaginationVo<>(iPage);
        assertTrue(paginationVo.getList().size() <= 10);
    }

    @Test
    public void saveOrUpdate() {
        Article article = new Article();
        article.setId(10);
        article.setStatus(2);
        boolean update = articleService.saveOrUpdate(article);
        assertTrue(update);
    }

    @Test
    public void test() {
//        Integer count = articleService.lambdaQuery().like(Article::getTags, "Banana").count();
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("count(1) count").like("tags", "Banana");
        List<Map<String, Object>> mapList = articleMapper.selectMaps(wrapper);
        System.out.println(mapList);
//        assertEquals(new Integer(10), count);
    }
}