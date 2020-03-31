package com.merrycodes.controller;

import com.merrycodes.entity.Article;
import com.merrycodes.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 保存或修改文章
 *
 * @author MerryCodes
 * @date 2020/3/30 23:24
 */
@Slf4j
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("save")
    public String save(Article article) {
        System.out.println(article);
        return "success";
    }

}
