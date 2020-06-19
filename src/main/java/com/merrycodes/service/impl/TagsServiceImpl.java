package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.constant.enums.StatusEnum;
import com.merrycodes.mapper.TagsMapper;
import com.merrycodes.model.entity.Tags;
import com.merrycodes.model.form.query.TagsQueryForm;
import com.merrycodes.model.vo.TagsVo;
import com.merrycodes.service.intf.TagsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_TAG;
import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_TAG_ARTICLE;
import static com.merrycodes.constant.consist.SortMapConsist.*;

/**
 * 文章标签service接口实现类
 *
 * @author MerryCodes
 * @date 2020/4/11 16:43
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    private final TagsMapper tagsMapper;


    /**
     * 文章标签分页查询
     * 使用 orderByDesc / orderByDesc 编译器会有警告 使用注解抹去
     *
     * @param current       当前页数
     * @param size          当前分页总条数
     * @param tagsQueryForm 文章标签查询表单类 （查询）{@link TagsQueryForm}
     * @return 分页 Page 对象接口 {@link IPage}
     * @see <a href="https://github.com/baomidou/mybatis-plus/issues/467">参考链接</a>
     */
    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(cacheNames = CACHE_VALUE_TAG, key = "'admin::tagsList['+#current+':'+#size+':'+#tagsQueryForm+']'")
    public IPage<Tags> selectTagsPageWithCount(Integer current, Integer size, TagsQueryForm tagsQueryForm) {
        Page<Tags> tagsPage = new Page<>(current, size);
        LambdaQueryWrapper<Tags> wrapper = Wrappers.lambdaQuery();
        // 前端传来的排序数据 example sortMap = {name=update, sort=desc})
        Map<String, String> sortMap = tagsQueryForm.getSort();
        // count 字段排序
        String countSort = null;
        if (sortMap != null) {
            // 判断是否等于 name 是否等于 update 如果是则 order by updateTime 否者 order by createTime
            if (StringUtils.equals(UPDATE_TIME, sortMap.get(NAME_KEY))) {
                // 判断前端传来按 顺序/倒叙 排序
                wrapper.orderByAsc(StringUtils.equals(ASC, sortMap.get(SORT_KEY)), Tags::getUpdateTime)
                        .orderByDesc(StringUtils.equals(DESC, sortMap.get(SORT_KEY)), Tags::getUpdateTime);
            } else if (StringUtils.equals(CREATE_TIME, sortMap.get(NAME_KEY))) {
                // 同上
                wrapper.orderByAsc(StringUtils.equals(ASC, sortMap.get(SORT_KEY)), Tags::getCreateTime)
                        .orderByDesc(StringUtils.equals(DESC, sortMap.get(SORT_KEY)), Tags::getCreateTime);
            } else {
                // 文章数排序
                countSort = sortMap.get(SORT_KEY);
            }
        }
        return tagsMapper.selectTagsPageWithCont(tagsPage, wrapper, countSort, tagsQueryForm.getStatus(), tagsQueryForm.getName());
    }

    @Override
    @Cacheable(cacheNames = CACHE_VALUE_TAG, key = "'admin::tagsList'")
    public List<String> selectTagsNameList() {
        LambdaQueryWrapper<Tags> wrapper = Wrappers.<Tags>lambdaQuery()
                // 查询到数据不为空则更新
                .select(Tags::getName).orderByAsc(Tags::getName);
        List<Tags> tagsList = tagsMapper.selectList(wrapper);
        return tagsList.stream().map(Tags::getName).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = CACHE_VALUE_TAG, key = "'admin::tagsListByStatus'")
    public List<String> selectTagsNameListByStatus() {
        LambdaQueryWrapper<Tags> wrapper = Wrappers.<Tags>lambdaQuery()
                // 查询到数据不为空则更新
                .select(Tags::getName).eq(Tags::getStatus, StatusEnum.VALID.getCode())
                .orderByAsc(Tags::getName);
        List<Tags> tagsList = tagsMapper.selectList(wrapper);
        return tagsList.stream().map(Tags::getName).collect(Collectors.toList());
    }

    /**
     * 获取标签与发布的文章
     *
     * @return 文章标签对象模型
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_TAG_ARTICLE, key = "'front::tagsWithArticle'")
    public List<TagsVo> selectTagsWithArticle() {
        return tagsMapper.selectTagsWithArticle();
    }

}
