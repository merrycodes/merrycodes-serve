package com.merrycodes.mapper;

import com.merrycodes.model.vo.CategoryVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author MerryCodes
 * @date 2020/4/22 22:50
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryMapperTest {

    private CategoryMapper categoryMapper;

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Test
    public void selectCategoryPageWithCount() {

    }

    @Test
    public void selectCategoryListWithArticleName() {
        List<CategoryVo> categoryVos = categoryMapper.selectCategoryWithArticle();
        System.out.println(categoryVos.size());
        assertNotNull(categoryVos);
    }
}