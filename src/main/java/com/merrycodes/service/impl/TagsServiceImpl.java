package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.constant.enums.StatusEnum;
import com.merrycodes.mapper.TagsMapper;
import com.merrycodes.model.entity.Tags;
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

import static com.merrycodes.constant.consist.SortMapConsist.*;
import static com.merrycodes.constant.consist.CacheValueConsist.*;

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
     * @param current 当前页数
     * @param size    当前分页总条数
     * @param tags    文章实体类 （查询）{@link Tags}
     * @return 分页 Page 对象接口 {@link IPage}
     * @see <a href="https://github.com/baomidou/mybatis-plus/issues/467">参考链接</a>
     */
    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(cacheNames = CACHE_VALUE_TAG, key = "'tagsList['+#current+':'+#size+':'+#tags+']'")
    public IPage<Tags> selectTagsPageWithCount(Integer current, Integer size, Tags tags) {
        Page<Tags> tagsPage = new Page<>(current, size);
        LambdaQueryWrapper<Tags> wrapper = Wrappers.lambdaQuery();
        // 前端传来的排序数据 example sortMap = {name=update, sort=desc})
        Map<String, String> sortMap = tags.getSort();
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
                countSort = sortMap.get(SORT_KEY);
            }
        }
        return tagsMapper.selectTagsPageWithCont(tagsPage, wrapper, countSort, tags.getStatus(), tags.getName());
    }

    @Override
    @Cacheable(cacheNames = CACHE_VALUE_TAG, key = "'tagsList'")
    public List<String> selectTagsNameList() {
        LambdaQueryWrapper<Tags> wrapper = Wrappers.<Tags>lambdaQuery()
                .select(Tags::getName).orderByAsc(Tags::getName);
        List<Tags> tagsList = tagsMapper.selectList(wrapper);
        return tagsList.stream().map(Tags::getName).collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = CACHE_VALUE_TAG, key = "'tagsListByStatus'")
    public List<String> selectTagsNameListByStatus() {
        LambdaQueryWrapper<Tags> wrapper = Wrappers.<Tags>lambdaQuery()
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
    @Cacheable(cacheNames = CACHE_VALUE_TAG_ARTICLE, key = "'tagsWithArticle'")
    public List<TagsVo> selectTagsWithArticle() {
        return tagsMapper.selectTagsWithArticle();
    }

}
