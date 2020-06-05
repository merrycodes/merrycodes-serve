package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.merrycodes.model.entity.Category;
import com.merrycodes.model.vo.CategoryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章分类 CRUD操作
 *
 * @author MerryCodes
 * @date 2020/4/13 22:58
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 文章分类分页查询 包括每个分类文章的数目
     *
     * @param page      简单分页模型 {@link Page}
     * @param wrapper   条件构造抽象类 {@link Wrapper}
     * @param countSort 标签的文章数 排序方法
     * @param status    文章标签状态 （用于查询）
     * @param name      文章标签的名字 （用于查询）
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Category> selectCategoryPageWithCount(Page<Category> page, @Param(Constants.WRAPPER) Wrapper<Category> wrapper,
                                                @Param("countSort") String countSort, @Param("staus") Integer status,
                                                @Param("name") String name);

    /**
     * 获取分类与发布的文章
     *
     * @return 文章标签对象模型
     */
    List<CategoryVo> selectCategoryWithArticle();
}
