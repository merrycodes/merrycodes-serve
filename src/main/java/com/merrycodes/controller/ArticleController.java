package com.merrycodes.controller;

import com.merrycodes.entity.Article;
import com.merrycodes.service.ArticleService;
import com.merrycodes.util.ResponseUtil;
import com.merrycodes.vo.ResponseVo;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
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
     * 后期将改为先保存到Redis中，然后开启一个定时任务再保存到数据库中
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
     * @return 文章
     */
    @ApiOperation(value = "获取文章详情接口", notes = "获取文章详情接口")
    @ApiImplicitParam(name = "文章id", value = "文章id", required = true, dataTypeClass = String.class)
    @GetMapping("/{id}")
    public ResponseVo<Article> info(@PathVariable("id") Integer id) {
        Article article = articleService.getArticleInfo(id);
        return ResponseUtil.success(article);
    }

}
