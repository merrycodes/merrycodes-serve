package com.merrycodes.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章分类
 *
 * @author MerryCodes
 * @date 2020/4/13 21:15
 */
@Api(value = "API - CategoryController",tags = "文章分类API接口")
@Slf4j
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryController {


}
