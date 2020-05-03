package com.merrycodes.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.Tags;
import com.merrycodes.model.vo.PaginationVo;
import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.service.intf.TagsService;
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

import java.util.List;

import static com.merrycodes.constant.consist.CacheValueConsist.*;

/**
 * 文章标签
 *
 * @author MerryCodes
 * @date 2020/4/11 12:36
 */
@Api(value = "API - TagsController - admin", tags = "文章标签API")
@Slf4j
@RestController
@RequestMapping("/admin/tags")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TagsController {

    private final TagsService tagsService;

    /**
     * 保存或更新文章标签接口
     *
     * @param tags 文章标签实体类 {@link Tags}
     * @return 文章标签id
     */
    @PostMapping("/save")
    @ApiOperation(value = "文章标签保存或更新接口", notes = "文章标签保存或更新接口")
    @ApiImplicitParam(name = "tags", value = "文章标签实体类", dataTypeClass = Tags.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_VALUE_TAG, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_ARTICLE, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_TAG_ARTICLE, beforeInvocation = true, allEntries = true)
    })
    public ResponseVo<Integer> saveOrUpdate(Tags tags) {
        if (!tagsService.saveOrUpdate(tags)) {
            log.info("【saveOrUpdate 文章保存/更新 失败】");
            return ResponseUtils.fail(tags.getId() == null ? "保存失败" : "更新失败");
        }
        log.info("【saveOrUpdate 文章保存/更新 成功】 id={}", tags.getId());
        return ResponseUtils.success(tags.getId());
    }

    /**
     * 文章标签列表分页查询接口
     *
     * @param current 当前页数
     * @param size    当前分页总条数
     * @param tags    文章标签实体类
     * @return 文章标签列表实体类
     */
    @GetMapping
    @ApiOperation(value = "获取文章标签列表接口", notes = "获取文章标签列表接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "tags", value = "文章标签实体类", dataTypeClass = Tags.class)
    })
    public ResponseVo<PaginationVo<Tags>> selectTagsPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                         Tags tags) {
        IPage<Tags> iPage = tagsService.selectTagsPageWithCount(current, size, tags);
        log.info("【selectTagsPage 获取文章列表】 总条数={} 当前分页总页数={} 当前页数={}", iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return ResponseUtils.success(new PaginationVo<>(iPage));
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取文章标签名字的全部集合（用于文章列表查询的选线）", notes = "获取文章标签名字的全部集合（用于文章列表查询的选线）")
    public ResponseVo<List<String>> tagsNameList() {
        List<String> list = tagsService.selectTagsNameList();
        log.info("【tagsNameList 获取文章标签名 list={}】", list);
        return ResponseUtils.success(list);
    }

    @GetMapping("/stausList")
    @ApiOperation(value = "获取生效的文章标签名字的集合（用于新建文章的选项）", notes = "获取生效的文章标签名字的集合（用于新建文章的选项）")
    public ResponseVo<List<String>> tagsNameListByStaus() {
        List<String> list = tagsService.selectTagsNameListByStatus();
        log.info("【tagsNameListByStaus 获取文章标签名（by status） list={}】", list);
        return ResponseUtils.success(list);
    }
}
