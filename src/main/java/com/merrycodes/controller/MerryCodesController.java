package com.merrycodes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.Article;
import com.merrycodes.model.vo.*;
import com.merrycodes.service.intf.ArticleService;
import com.merrycodes.service.intf.CategoryService;
import com.merrycodes.service.intf.SettingService;
import com.merrycodes.service.intf.TagsService;
import com.merrycodes.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    private final SettingService settingService;

    @GetMapping("/article")
    @ApiOperation(value = "获取文章列表", notes = "获取文章列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class)
    })
    public ResponseVo<PaginationVo<Article>> articleList(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                         @RequestParam(value = "size", defaultValue = "5") Integer size) {
        IPage<Article> iPage = articleService.selectArticlePageByStatus(current, size);
        return ResponseUtils.success(new PaginationVo<>(iPage));
    }

    @GetMapping("/articles")
    @ApiOperation(value = "获取全部的文章列表(SEO)", notes = "获取全部的文章列表(SEO)")
    public ResponseVo<List<Article>> articleList() {
        List<Article> articleList = articleService.selectArticleList();
        return ResponseUtils.success(articleList);
    }

    @GetMapping("/article/{id}")
    @ApiOperation(value = "获取文章详情", notes = "获取文章详情")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataTypeClass = Integer.class)
    public ResponseVo<Article> articleInfo(@PathVariable("id") Integer id) {
        Article article = articleService.selectArticleInfo(id);
        return ResponseUtils.success(article);
    }

    @GetMapping("/category")
    @ApiOperation(value = "获取分类集合", notes = "获取分类集合")
    public ResponseVo<List<CategoryVo>> categoryList() {
        List<CategoryVo> categoryVos = categoryService.selectCategoryWithArticle();
        return ResponseUtils.success(categoryVos);
    }

    @GetMapping("/tags")
    @ApiOperation(value = "获取标签集合", notes = "获取标签集合")
    public ResponseVo<List<TagsVo>> tageList() {
        List<TagsVo> tagsVos = tagsService.selectTagsWithArticle();
        return ResponseUtils.success(tagsVos);
    }

    @GetMapping("/archive")
    @ApiOperation(value = "获取归档集合", notes = "获取归档集合")
    public ResponseVo<List<ArchiveVo>> archiveList() {
        List<ArchiveVo> archiveVos = articleService.selectArchiveList();
        return ResponseUtils.success(archiveVos);
    }

    @GetMapping("/setting")
    @ApiOperation(value = "获取设置集合", notes = "获取设置集合")
    public ResponseVo<Map<String, String>> selectAllSetting() {
        Map<String, String> map = settingService.selectSettingMap();
        return ResponseUtils.success(map);
    }

}
