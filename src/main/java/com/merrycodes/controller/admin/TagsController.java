package com.merrycodes.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.Tags;
import com.merrycodes.model.form.query.TagsQueryForm;
import com.merrycodes.model.vo.PaginationVo;
import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.model.vo.TagsVo;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "文章标签保存或更新接口", notes = "文章标签保存或更新接口")
    @ApiImplicitParam(name = "tags", value = "文章标签实体类", required = true, dataTypeClass = Tags.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_VALUE_TAG, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_ARTICLE, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_TAG_ARTICLE, beforeInvocation = true, allEntries = true)
    })
    public ResponseVo<Integer> saveOrUpdate(Tags tags) {
        // TODO: MerryCodes 2020-06-03 08:47:27 查修文章是否还有使用该标签的文章，如果有则提示用户不能修改此标签，如果没有则运行修改逻辑
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
     * @param current       当前页数
     * @param size          当前分页总条数
     * @param tagsQueryForm 文章标签查询表单类
     * @return 文章标签列表实体类 (分页) {@link Tags}
     */
    @GetMapping
    @ApiOperation(value = "获取文章标签列表接口", notes = "获取文章标签列表接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "tagsQueryForm", value = "文章标签查询表单类", dataTypeClass = TagsQueryForm.class)
    })
    public ResponseVo<PaginationVo<Tags>> selectTagsPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                         TagsQueryForm tagsQueryForm) {
        IPage<Tags> iPage = tagsService.selectTagsPageWithCount(current, size, tagsQueryForm);
        log.info("【selectTagsPage 获取文章列表】 总条数={} 当前分页总页数={} 当前页数={}", iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return ResponseUtils.success(new PaginationVo<>(iPage));
    }

    /**
     * 获取文章标签名字的全部集合（用于文章列表查询的选项）
     *
     * @return 文章标签名字实体类 {@link TagsVo}
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取文章标签名字的全部集合（用于文章列表查询的选项）", notes = "获取文章标签名字的全部集合（用于文章列表查询的选项）")
    public ResponseVo<List<String>> tagsNameList() {
        List<String> list = tagsService.selectTagsNameList();
        log.info("【tagsNameList 获取文章标签名 list={}】", list);
        return ResponseUtils.success(list);
    }

    /**
     * 获取生效的文章标签名字的集合（用于新建文章的选项）
     *
     * @return 文章标签名字集合
     */
    @GetMapping("/stausList")
    @ApiOperation(value = "获取生效的文章标签名字的集合（用于新建文章的选项）", notes = "获取生效的文章标签名字的集合（用于新建文章的选项）")
    public ResponseVo<List<String>> tagsNameListByStaus() {
        List<String> list = tagsService.selectTagsNameListByStatus();
        log.info("【tagsNameListByStaus 获取文章标签名（by status） list={}】", list);
        return ResponseUtils.success(list);
    }
}
