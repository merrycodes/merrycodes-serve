package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.Article;
import com.merrycodes.model.form.ArticleQueryForm;
import com.merrycodes.model.vo.ArchiveVo;
import com.merrycodes.model.vo.PaginationVo;
import com.merrycodes.service.intf.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Test
    public void getArticleInfo() {
        Article article = articleService.selectEditArticleInfo(4);
        System.out.println(article);
        assertEquals(new Integer(4), article.getId());
    }

    @Test
    public void selectArticlePage() {
        IPage<Article> iPage = articleService.selectArticlePage(1, 10, new ArticleQueryForm());
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
    public void selectArticlePageByStatus() {
        IPage<Article> iPage = articleService.selectArticlePageByStatus(1, 5);
        PaginationVo<Article> paginationVo = new PaginationVo<>(iPage);
        assertEquals(5, paginationVo.getList().size());
    }

    @Test
    public void selectArchiveList() {
        List<ArchiveVo> archiveVos = articleService.selectArchiveList();
        archiveVos.forEach(archiveVo -> {
            System.out.println(archiveVo.year);
            archiveVo.articleList.forEach(System.out::println);
        });
    }
}