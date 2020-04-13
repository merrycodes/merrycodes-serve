package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.constant.SortMapConstant;
import com.merrycodes.entity.Tags;
import com.merrycodes.enums.StatusEnum;
import com.merrycodes.mapper.TagsMapper;
import com.merrycodes.service.TagsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
     * @param size    当前分页总页数
     * @param tags    文章实体类 （查询）{@link Tags}
     * @return 分页 Page 对象接口 {@link IPage}
     * @see <a href="https://github.com/baomidou/mybatis-plus/issues/467"></a>
     */
    @Override
    @SuppressWarnings("unchecked")
    public IPage<Tags> selectTagsPageWithCount(Integer current, Integer size, Tags tags) {
        Page<Tags> tagsPage = new Page<>(current, size);
        LambdaQueryWrapper<Tags> wrapper = Wrappers.<Tags>lambdaQuery();
        // 前端传来的排序数据 example sortMap = {name=update, sort=desc})
        Map<String, String> sortMap = tags.getSort();
        // count 字段排序
        String countSort = null;
        // 判断是否等于 name 是否等于 update 如果是则 order by updateTime 否者 order by createTime
        if (StringUtils.equals(SortMapConstant.UPDATE_TIME, sortMap.get(SortMapConstant.NAME_KEY))) {
            // 判断前端传来按 顺序/倒叙 排序
            wrapper.orderByAsc(StringUtils.equals(SortMapConstant.ASC, sortMap.get(SortMapConstant.SORT_KEY)), Tags::getUpdateTime)
                    .orderByDesc(StringUtils.equals(SortMapConstant.DESC, sortMap.get(SortMapConstant.SORT_KEY)), Tags::getUpdateTime);
        } else if (StringUtils.equals(SortMapConstant.CREATE_TIME, sortMap.get(SortMapConstant.NAME_KEY))) {
            // 同上
            wrapper.orderByAsc(StringUtils.equals(SortMapConstant.ASC, sortMap.get(SortMapConstant.SORT_KEY)), Tags::getCreateTime)
                    .orderByDesc(StringUtils.equals(SortMapConstant.DESC, sortMap.get(SortMapConstant.SORT_KEY)), Tags::getCreateTime);
        } else {
            countSort = sortMap.get(SortMapConstant.SORT_KEY);
        }
        return tagsMapper.selectTagsPageWithCont(tagsPage, wrapper, countSort, tags.getStatus(), tags.getName());
    }

    @Override
    public List<String> selectTagsNameList() {
        LambdaQueryWrapper<Tags> wrapper = Wrappers.<Tags>lambdaQuery()
                .select(Tags::getName).orderByAsc(Tags::getName);
        List<Tags> tagsList = tagsMapper.selectList(wrapper);
        return tagsList.stream().map(Tags::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> selectTagsNameListByStatus() {
        LambdaQueryWrapper<Tags> wrapper = Wrappers.<Tags>lambdaQuery()
                .select(Tags::getName).eq(Tags::getStatus, StatusEnum.VALID.getCode())
                .orderByAsc(Tags::getName);
        List<Tags> tagsList = tagsMapper.selectList(wrapper);
        return tagsList.stream().map(Tags::getName).collect(Collectors.toList());
    }
}
