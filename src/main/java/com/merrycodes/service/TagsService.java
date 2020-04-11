package com.merrycodes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.entity.Tags;

/**
 * 文章标签service接口
 *
 * @author MerryCodes
 * @date 2020/4/11 16:42
 */
public interface TagsService extends IService<Tags> {

    /**
     * 文章标签分页查询 包括每个标签文章的数目
     *
     * @param current 当前页数
     * @param size    当前分页总页数
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Tags> selectTagsPageWithCount(Integer current, Integer size);

}
