package com.merrycodes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.entity.Category;
import com.merrycodes.vo.CategoryVo;

import java.util.List;

/**
 * 文章分类service接口
 *
 * @author MerryCodes
 * @date 2020/4/14 8:45
 */
public interface CategoryService extends IService<Category> {

    /**
     * 文章分类分页查询 包括每个分类文章的数目
     *
     * @param current  当前页数
     * @param size     当前分页总页数
     * @param category 文章分类实体类（查询）{@link Category}
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Category> selectCategoryPageWithCount(Integer current, Integer size, Category category);


    /**
     * 获取文章分类名的全部集合（用于文章列表查询的选项）
     *
     * @return 文章分类名集合
     */
    List<String> selectCategoryNameList();

    /**
     * 获取生效的文章分类名的集合（用于新建文章的选项）
     *
     * @return 文章分类名集合
     */
    List<String> selectCategoryNameListByStatus();

    /**
     * 获取标签与发布的文章
     *
     * @return 文章标签对象模型
     */
    List<CategoryVo> selectCategoryWithArticle();
}
