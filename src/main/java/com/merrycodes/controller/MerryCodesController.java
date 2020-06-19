package com.merrycodes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.merrycodes.entity.Article;
import com.merrycodes.service.ArticleService;
import com.merrycodes.service.CategoryService;
import com.merrycodes.service.TagsService;
import com.merrycodes.util.ResponseUtil;
import com.merrycodes.vo.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author MerryCodes
 * @date 2020/4/20 9:47
 */
@Api(value = "API - MerryCodesController - front", tags = "前端博客API")
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerryCodesController {

    private final ArticleService articleService;

    private final CategoryService categoryService;

    private final TagsService tagsService;

    @GetMapping("/article")
    public ResponseVo<PaginationVo<Article>> articleList(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                         @RequestParam(value = "size", defaultValue = "5") Integer size) {
        IPage<Article> iPage = articleService.selectArticlePageByStatus(current, size);
        return ResponseUtil.success(new PaginationVo<>(iPage));
    }

    @GetMapping("/articles")
    public ResponseVo<List<Article>> articleList() {
        List<Article> articleList = articleService.selectArticleList();
        return ResponseUtil.success(articleList);
    }

    @GetMapping("/article/{id}")
    public ResponseVo<Article> articleInfo(@PathVariable("id") Integer id) {
        Article article = articleService.selectArticleInfo(id);
        return ResponseUtil.success(article);
    }

    @GetMapping("/category")
    public ResponseVo<List<CategoryVo>> categoryList() {
        List<CategoryVo> categoryVos = categoryService.selectCategoryWithArticle();
        return ResponseUtil.success(categoryVos);
    }

    @GetMapping("/tags")
    public ResponseVo<List<TagsVo>> tageList() {
        List<TagsVo> tagsVos = tagsService.selectTagsWithArticle();
        return ResponseUtil.success(tagsVos);
    }

    @GetMapping("/archive")
    public ResponseVo<List<ArchiveVo>> archiveList() {
        List<ArchiveVo> archiveVos = articleService.selectArchiveList();
        return ResponseUtil.success(archiveVos);
    }

}
