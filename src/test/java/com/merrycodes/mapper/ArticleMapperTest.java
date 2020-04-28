package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.merrycodes.model.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

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
    public void test() {
        // Integer count = articleService.lambdaQuery().like(Article::getTags, "Banana").count();
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("count(1) count").like("tags", "Banana");
        List<Map<String, Object>> mapList = articleMapper.selectMaps(wrapper);
        System.out.println(mapList);
        // assertEquals(new Integer(10), count);
    }
}