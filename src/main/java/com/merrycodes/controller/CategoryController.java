package com.merrycodes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.entity.Category;
import com.merrycodes.service.CategoryService;
import com.merrycodes.util.ResponseUtil;
import com.merrycodes.vo.PaginationVo;
import com.merrycodes.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章分类
 *
 * @author MerryCodes
 * @date 2020/4/13 21:15
 */
@Api(value = "API - CategoryController", tags = "文章分类API接口")
@Slf4j
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 保存或更新文章分类
     *
     * @param category 文章分类实体类 {@link Category}
     * @return 文章分类id
     */
    @ApiOperation(value = "文章分类保存或更新接口", notes = "文章分类保存或更新接口")
    @ApiImplicitParam(name = "category", value = "文章分类实体类", dataTypeClass = Category.class)
    @PostMapping("/save")
    public ResponseVo<Integer> saveOrUpdate(Category category) {

        if (!categoryService.saveOrUpdate(category)) {
            log.info("【saveOrUpdate 文章分类 保存/更新 失败】");
        }
        log.info("【saveOrUpdate 文章分类 保存/更新 成功】 id={}", category.getId());
        return ResponseUtil.success(category.getId());
    }

    /**
     * 文章分类列表分页查询接口
     *
     * @param current  当前页数
     * @param size     当前分页总条数
     * @param category 文章分类实体类
     * @return 文章分类列表实体类
     */
    @ApiOperation(value = "文章分类列表分页查询接口", notes = "文章分类列表分页查询接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "category", value = "文章分类实体类", dataTypeClass = Category.class)
    })
    @GetMapping
    public ResponseVo<PaginationVo<Category>> selectCategoryPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                 Category category) {
        IPage<Category> iPage = categoryService.selectCategoryPageWithCount(current, size, category);
        log.info("【selectCategoryPageWithCount 获取文章分类】 总条数={} 当前分页总页数={} 当前页数={}", iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return ResponseUtil.success(new PaginationVo<>(iPage));
    }

    @ApiOperation(value = "获取文章分类名字的全部集合（用于文章列表查询的选线）", notes = "获取文章分类名字的全部集合（用于文章列表查询的选线）")
    @GetMapping("/list")
    public ResponseVo<List<String>> categoryNameList() {
        List<String> list = categoryService.selectCategoryNameList();
        log.info("【categoryNameList 获取文章分类名 list={}】", list);
        return ResponseUtil.success(list);
    }

    @ApiOperation(value = "获取生效的文章分类名字的集合（用于新建文章的选项）", notes = "获取生效的文章分类名字的集合（用于新建文章的选项）")
    @GetMapping("/stausList")
    public ResponseVo<List<String>> categoryNameListByStaus() {
        List<String> list =  categoryService.selectCategoryNameListByStatus();
        log.info("【categoryNameListByStaus 获取文章分类名（by status） list={}】", list);
        return ResponseUtil.success(list);
    }

}
