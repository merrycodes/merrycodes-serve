package com.merrycodes.mapper;

import com.merrycodes.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author MerryCodes
 * @date 2020/3/30 15:57
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testMapper() {
        List<Article> articleList = articleMapper.selectList(null);
        assertEquals(0, articleList.size());
    }
}