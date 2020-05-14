package com.merrycodes.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.Article;
import com.merrycodes.model.form.ArticleQueryForm;
import com.merrycodes.model.vo.PaginationVo;
import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.service.intf.ArticleService;
import com.merrycodes.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import static com.merrycodes.constant.consist.CacheValueConsist.*;

/**
 * 文章
 *
 * @author MerryCodes
 * @date 2020/3/30 23:24
 */
@Api(value = "API - ArticleController - admin", tags = "文章API")
@Slf4j
@RestController
@RequestMapping("/admin/article")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 保存或更新文章
     *
     * @param article 文章实体类
     * @return 文章id
     */
    @PostMapping("save")
    @ApiOperation(value = "文章保存或更新接口", notes = "文章保存或更新接口")
    @ApiImplicitParam(name = "article", value = "文章实体类", required = true, dataTypeClass = Article.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_VALUE_ARTICLE, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_TAG_ARTICLE, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_CATEGORY_ARTICLE, beforeInvocation = true, allEntries = true)
    })
    public ResponseVo<Integer> save(Article article) {
        if (!articleService.saveOrUpdate(article)) {
            log.info("【save 文章保存/更新失败】");
            return ResponseUtils.fail(article.getId() == null ? "保存失败" : "更新失败");
        }
        log.info("【save 文章保存/更新 成功】 id={}", article.getId());
        return ResponseUtils.success(article.getId());
    }

    /**
     * 获取文章详情接口
     *
     * @param id 文章id
     * @return 文章实体类 {@link Article}
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取文章详情接口", notes = "获取文章详情接口")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataTypeClass = String.class)
    public ResponseVo<Article> info(@PathVariable("id") Integer id) {
        Article article = articleService.selectEditArticleInfo(id);
        log.info("info 获取文章详情 Article={}", article);
        return ResponseUtils.success(article);
    }

    /**
     * 获取文章列表接口
     *
     * @param current          当前页数
     * @param size             当前分页总条数
     * @param articleQueryForm 文章查询表单类
     * @return 文章列表实体类 (分页) {@link Article}
     */
    @GetMapping
    @ApiOperation(value = "获取文章列表接口", notes = "获取文章列表接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "articleQueryForm", value = "文章查询表单类", dataTypeClass = ArticleQueryForm.class)
    })
    public ResponseVo<PaginationVo<Article>> list(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                                                  @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                  ArticleQueryForm articleQueryForm) {
        IPage<Article> iPage = articleService.selectArticlePage(current, size, articleQueryForm);
        log.info("【list 获取文章列表】 总条数={} 当前分页总页数={} 当前页数={}", iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return ResponseUtils.success(new PaginationVo<>(iPage));
    }

}
