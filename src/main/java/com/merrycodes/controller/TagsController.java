package com.merrycodes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.entity.Tags;
import com.merrycodes.service.TagsService;
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

/**
 * 文章标签
 *
 * @author MerryCodes
 * @date 2020/4/11 12:36
 */
@Api(value = "API - TagsController", tags = "文章标签API接口")
@Slf4j
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TagsController {

    private final TagsService tagsService;

    /**
     * 保存或更新文章标签
     *
     * @param tags 文章标签实体类 {@link Tags}
     * @return 文章标签id
     */
    @ApiOperation(value = "文章标签保存或更新接口", notes = "文章标签保存或更新接口")
    @ApiImplicitParam(name = "tags", value = "文章标签实体类", dataTypeClass = Tags.class)
    @PostMapping("/save")
    public ResponseVo<Integer> saveOrUpdate(Tags tags) {
        if (!tagsService.saveOrUpdate(tags)) {
            log.info("【save 文章保存/更新 失败】");
            return ResponseUtil.fail(tags.getId() == null ? "保存失败" : "更新失败");
        }
        log.info("【save 文章保存/更新 成功】 id={}", tags.getId());
        return ResponseUtil.success(tags.getId());
    }

    /**
     * 文章标签列表分页查询
     *
     * @param current 当前页数
     * @param size    当前分页总页数
     * @return 文章列表实体类
     */
    @ApiOperation(value = "获取文章标签列表接口", notes = "获取文章标签列表接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class)
    })
    @GetMapping
    public ResponseVo<PaginationVo<Tags>> tagsPage(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        IPage<Tags> iPage = tagsService.selectTagsPageWithCount(current, size);
        log.info("【list 获取文章列表】 总条数={} 当前分页总页数={} 当前页数={}", iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return ResponseUtil.success(new PaginationVo<>(iPage));
    }
}
