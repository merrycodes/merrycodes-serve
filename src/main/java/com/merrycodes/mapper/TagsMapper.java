package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.merrycodes.entity.Tags;
import org.apache.ibatis.annotations.Param;
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
     * @param page      简单分页模型 {@link Page}
     * @param wrapper   条件构造抽象类 {@link Wrapper}
     * @param countSort 标签的文章数 排序方法
     * @param status    文章标签状态 （用于查询）
     * @param name      文章标签的名字 （用于查询）
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Tags> selectTagsPageWithCont(Page<Tags> page, @Param("ew") Wrapper<Tags> wrapper
            , @Param("countSort") String countSort, @Param("staus") Integer status, @Param("name") String name);
}
