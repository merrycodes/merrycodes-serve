package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.merrycodes.entity.Tags;
import com.merrycodes.service.TagsService;
import com.merrycodes.vo.PaginationVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author MerryCodes
 * @date 2020/4/11 16:54
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TagsServiceImplTest {

    private TagsService tagsService;

    @Autowired
    public void setTagsService(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @Test
    public void selectTagsPageWithCount() {
        IPage<Tags> iPage = tagsService.selectTagsPageWithCount(1, 1);
        PaginationVo<Tags> paginationVo = new PaginationVo<>(iPage);
        System.out.printf("当前页数:%d\n", paginationVo.getCurrent());
        System.out.printf("当前分页总页数:%d\n", paginationVo.getSize());
        System.out.printf("总条数:%d\n", paginationVo.getTotal());
        paginationVo.getList().forEach(System.out::println);
        assertEquals(1, paginationVo.getList().size());
    }
}