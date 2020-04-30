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
    public ResponseVo<PaginationVo<Article>> articleList(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                         @RequestParam(value = "size", defaultValue = "5") Integer size) {
        IPage<Article> iPage = articleService.selectArticlePageByStatus(current, size);
        return ResponseUtils.success(new PaginationVo<>(iPage));
    }

    @GetMapping("/articles")
    public ResponseVo<List<Article>> articleList() {
        List<Article> articleList = articleService.selectArticleList();
        return ResponseUtils.success(articleList);
    }

    @GetMapping("/article/{id}")
    public ResponseVo<Article> articleInfo(@PathVariable("id") Integer id) {
        Article article = articleService.selectArticleInfo(id);
        return ResponseUtils.success(article);
    }

    @GetMapping("/category")
    public ResponseVo<List<CategoryVo>> categoryList() {
        List<CategoryVo> categoryVos = categoryService.selectCategoryWithArticle();
        return ResponseUtils.success(categoryVos);
    }

    @GetMapping("/tags")
    public ResponseVo<List<TagsVo>> tageList() {
        List<TagsVo> tagsVos = tagsService.selectTagsWithArticle();
        return ResponseUtils.success(tagsVos);
    }

    @GetMapping("/archive")
    public ResponseVo<List<ArchiveVo>> archiveList() {
        List<ArchiveVo> archiveVos = articleService.selectArchiveList();
        return ResponseUtils.success(archiveVos);
    }

    @GetMapping("/setting")
    public ResponseVo<Map<String, String>> selectAllSetting() {
        Map<String, String> map = settingService.selectSettingMap();
        return ResponseUtils.success(map);
    }

}
