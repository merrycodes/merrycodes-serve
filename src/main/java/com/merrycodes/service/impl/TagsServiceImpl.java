package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.entity.Tags;
import com.merrycodes.mapper.TagsMapper;
import com.merrycodes.service.TagsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *
     * @param current 当前页数
     * @param size    当前分页总页数
     * @return 分页 Page 对象接口 {@link IPage}
     */
    @Override
    public IPage<Tags> selectTagsPageWithCount(Integer current, Integer size) {
        Page<Tags> tagsPage = new Page<>(current, size);
        return tagsMapper.selectTagsPageWithCont(tagsPage);
    }
}
