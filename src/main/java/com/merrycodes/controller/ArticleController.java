package com.merrycodes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.merrycodes.entity.Article;
import com.merrycodes.service.ArticleService;
import com.merrycodes.util.ResponseUtil;
import com.merrycodes.vo.PaginationVo;
import com.merrycodes.vo.ResponseVo;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章
 *
 * @author MerryCodes
 * @date 2020/3/30 23:24
 */
@Api(value = "API - ArticleController", tags = "文章API接口")
@Slf4j
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 保存或更新到数据库中
     * TODO 后期将改为先保存到Redis中，然后开启一个定时任务再保存到数据库中
     *
     * @param article 文章实体类
     * @return 文章id
     */
    @ApiOperation(value = "文章保存或更新接口", notes = "文章保存或更新接口")
    @ApiImplicitParam(name = "article", value = "文章实体类", required = true, dataTypeClass = Article.class)
    @PostMapping("save")
    public ResponseVo<Integer> save(Article article) {
        if (!articleService.saveOrUpdate(article)) {
            return ResponseUtil.fail("保存失败");
        }
        return ResponseUtil.success(article.getId());
    }

    /**
     * 获取文章详情接口
     *
     * @param id 文章id
     * @return 文章实体类
     */
    @ApiOperation(value = "获取文章详情接口", notes = "获取文章详情接口")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataTypeClass = String.class)
    @GetMapping("/{id}")
    public ResponseVo<Article> info(@PathVariable("id") Integer id) {
        Article article = articleService.selectArticleInfo(id);
        return ResponseUtil.success(article);
    }

    /**
     * 获取文章列表接口
     * TODO 后期在Service层,把查询到的数据保存到Redis中
     *
     * @return 文章列表实体类
     */
    @ApiOperation(value = "获取文章列表接口", notes = "获取文章列表接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "article", value = "文章实体类", dataTypeClass = Article.class)
    })
    @GetMapping
    public ResponseVo<PaginationVo<Article>> list(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                                                  @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                  Article article) {
        Page<Article> page = new Page<>(current, size);
        IPage<Article> iPage = articleService.selectArticlePage(current, size, article);
        return ResponseUtil.success(new PaginationVo<>(iPage));
    }

}
