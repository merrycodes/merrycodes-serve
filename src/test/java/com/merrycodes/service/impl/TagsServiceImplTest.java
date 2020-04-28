package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.Tags;
import com.merrycodes.service.intf.TagsService;
import com.merrycodes.model.vo.PaginationVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * TagsServiceImpl 测试类
 *
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
        Tags tags = new Tags();
        tags.setStatus(1);
        Map<String, String> map = new HashMap<>();
        map.put("name", "update");
        map.put("sort", "asc");
        tags.setSort(map);
        IPage<Tags> iPage = tagsService.selectTagsPageWithCount(1, 10, tags);
        PaginationVo<Tags> paginationVo = new PaginationVo<>(iPage);
        System.out.printf("当前页数:%d\n", paginationVo.getCurrent());
        System.out.printf("当前分页总页数:%d\n", paginationVo.getSize());
        System.out.printf("总条数:%d\n", paginationVo.getTotal());
        paginationVo.getList().forEach(System.out::println);
    }

    @Test
    public void selectTagsNameList() {
        List<String> list = tagsService.selectTagsNameList();
        assertNotNull(list);
        list.forEach(System.out::println);
    }

    @Test
    public void selectTagsNameListByStatus() {
        List<String> list = tagsService.selectTagsNameListByStatus();
        assertNotNull(list);
        list.forEach(System.out::println);
    }
}