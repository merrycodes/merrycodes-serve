package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.merrycodes.entity.Tags;
import org.springframework.stereotype.Repository;

/**
 * 文章标签 CRUD操作
 *
 * @author MerryCodes
 * @date 2020/4/11 14:54
 */
@Repository
public interface TagsMapper extends BaseMapper<Tags> {

    /**
     * 文章标签分页查询 包括每个标签文章的数目
     *
     * @param page 简单分页模型 {@link Page}
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Tags> selectTagsPageWithCont(Page<Tags> page);
}
