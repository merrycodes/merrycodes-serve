package com.merrycodes.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.Category;
import com.merrycodes.model.form.query.CategoryQueryForm;
import com.merrycodes.model.vo.CategoryVo;
import com.merrycodes.model.vo.PaginationVo;
import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.service.intf.ArticleService;
import com.merrycodes.service.intf.CategoryService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.merrycodes.constant.consist.CacheValueConsist.*;

/**
 * 文章分类
 *
 * @author MerryCodes
 * @date 2020/4/13 21:15
 */
@Api(value = "API - CategoryController - admin", tags = "文章分类API")
@Slf4j
@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryController {

    private final CategoryService categoryService;

    private final ArticleService articleService;

    /**
     * 保存或更新文章分类
     *
     * @param category 文章分类实体类 {@link Category}
     * @return 文章分类id
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "文章分类保存或更新接口", notes = "文章分类保存或更新接口")
    @ApiImplicitParam(name = "category", value = "文章分类实体类", required = true, dataTypeClass = Category.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_VALUE_ARTICLE, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_CATEGORY, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_CATEGORY_ARTICLE, beforeInvocation = true, allEntries = true)
    })
    public ResponseVo<Integer> saveOrUpdate(Category category) {
        // 有ID则是更新分类，文章还有使用此分类就不能更新
        if (category.getId() != null) {
            if (articleService.selectCountByCategory(category.getName()) > 0) {
                log.error("【还有文章使用此分类:{}，修改失败】", category.getName());
                return ResponseUtils.fail("还有文章使用此分类，修改失败");
            }
        }
        if (!categoryService.saveOrUpdate(category)) {
            log.info("【saveOrUpdate 文章分类 保存/更新 失败】");
        }
        log.info("【saveOrUpdate 文章分类 保存/更新 成功】 id={}", category.getId());
        return ResponseUtils.success(category.getId());
    }

    /**
     * 文章分类列表分页查询接口
     *
     * @param current           当前页数
     * @param size              当前分页总条数
     * @param categoryQueryForm categoryQueryForm
     * @return 文章分类列表实体类 (分页) {@link Category}
     */
    @GetMapping
    @ApiOperation(value = "文章分类列表分页查询接口", notes = "文章分类列表分页查询接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "categoryQueryForm", value = "文章分类查询表单类", dataTypeClass = CategoryQueryForm.class)
    })
    public ResponseVo<PaginationVo<Category>> selectCategoryPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                                 @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                 CategoryQueryForm categoryQueryForm) {
        IPage<Category> iPage = categoryService.selectCategoryPageWithCount(current, size, categoryQueryForm);
        log.info("【selectCategoryPageWithCount 获取文章分类】 总条数={} 当前分页总页数={} 当前页数={}", iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return ResponseUtils.success(new PaginationVo<>(iPage));
    }

    /**
     * 获取文章分类名的全部集合（用于文章列表查询的选项）
     *
     * @return 文章分类名实体类 {@link CategoryVo}
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取文章分类名字的全部集合（用于文章列表查询的选项）", notes = "获取文章分类名字的全部集合（用于文章列表查询的选项）")
    public ResponseVo<List<String>> categoryNameList() {
        List<String> list = categoryService.selectCategoryNameList();
        log.info("【categoryNameList 获取文章分类名 list={}】", list);
        return ResponseUtils.success(list);
    }

    /**
     * 获取生效的文章分类名的集合（用于新建文章的选项）
     *
     * @return 文章分类名集合
     */
    @GetMapping("/stausList")
    @ApiOperation(value = "获取生效的文章分类名字的集合（用于新建文章的选项）", notes = "获取生效的文章分类名字的集合（用于新建文章的选项）")
    public ResponseVo<List<String>> categoryNameListByStaus() {
        List<String> list = categoryService.selectCategoryNameListByStatus();
        log.info("【categoryNameListByStaus 获取文章分类名（by status） list={}】", list);
        return ResponseUtils.success(list);
    }

}
