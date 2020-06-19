package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.constant.enums.StatusEnum;
import com.merrycodes.mapper.CategoryMapper;
import com.merrycodes.model.entity.Category;
import com.merrycodes.model.form.query.CategoryQueryForm;
import com.merrycodes.model.vo.CategoryVo;
import com.merrycodes.service.intf.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_CATEGORY;
import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_CATEGORY_ARTICLE;
import static com.merrycodes.constant.consist.SortMapConsist.*;

/**
 * 文章分类service接口实现类
 *
 * @author MerryCodes
 * @date 2020/4/14 8:49
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 文章分类分页查询 包括每个分类文章的数目
     * 使用 orderByDesc / orderByDesc 编译器会有警告 使用注解抹去
     *
     * @param current           当前页数
     * @param size              当前分页总页数
     * @param categoryQueryForm 文章分类查询表单类 {@link CategoryQueryForm}
     * @return 分页 Page 对象接口 {@link IPage}
     * @see <a href="https://github.com/baomidou/mybatis-plus/issues/467">参考链接</a>
     */
    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(cacheNames = CACHE_VALUE_CATEGORY, key = "'admin::categoryList['+#current+':'+#size+':'+#categoryQueryForm+']'")
    public IPage<Category> selectCategoryPageWithCount(Integer current, Integer size, CategoryQueryForm categoryQueryForm) {
        Page<Category> categoryPage = new Page<>(current, size);
        LambdaQueryWrapper<Category> wrapper = Wrappers.lambdaQuery();
        // 前端传来的排序数据 example sortMap = {name=update, sort=desc})
        Map<String, String> sortMap = categoryQueryForm.getSort();
        // count 字段排序
        String countSort = null;
        if (sortMap != null) {
            // 判断是否等于 name 是否等于 update 如果是则 order by updateTime 否者 order by createTime
            if (StringUtils.equals(CREATE_TIME, sortMap.get(NAME_KEY))) {
                // 判断前端传来按 顺序/倒叙 排序
                wrapper.orderByAsc(StringUtils.equals(ASC, sortMap.get(SORT_KEY)), Category::getCreateTime)
                        .orderByDesc(StringUtils.equals(DESC, sortMap.get(SORT_KEY)), Category::getCreateTime);
            } else if (StringUtils.equals(UPDATE_TIME, sortMap.get(NAME_KEY))) {
                // 同上
                wrapper.orderByAsc(StringUtils.equals(ASC, sortMap.get(SORT_KEY)), Category::getUpdateTime)
                        .orderByDesc(StringUtils.equals(DESC, sortMap.get(SORT_KEY)), Category::getUpdateTime);
            } else {
                // 文章数排序
                countSort = sortMap.get(SORT_KEY);
            }
        }
        return categoryMapper.selectCategoryPageWithCount(categoryPage, wrapper, countSort, categoryQueryForm.getStatus(), categoryQueryForm.getName());
    }

    /**
     * 获取文章分类名的全部集合（用于文章列表查询的选项）
     *
     * @return 文章分类名集合
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_CATEGORY, key = "'admin::categoriesList'")
    public List<String> selectCategoryNameList() {
        LambdaQueryWrapper<Category> wrapper = Wrappers.<Category>lambdaQuery()
                // 查询出来的结果仅包括下面的字段
                .select(Category::getName).orderByAsc(Category::getName);
        List<Category> categoryList = categoryMapper.selectList(wrapper);
        return categoryList.stream().map(Category::getName).collect(Collectors.toList());
    }

    /**
     * 获取生效的文章分类名的集合（用于新建文章的选项）
     *
     * @return 文章分类名集合
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_CATEGORY, key = "'admin::categoriesListByStatus'")
    public List<String> selectCategoryNameListByStatus() {
        LambdaQueryWrapper<Category> wrapper = Wrappers.<Category>lambdaQuery()
                // 查询出来的结果仅包括下面的字段
                .select(Category::getName).eq(Category::getStatus, StatusEnum.VALID.getCode())
                .orderByAsc(Category::getName);
        List<Category> categoryList = categoryMapper.selectList(wrapper);
        return categoryList.stream().map(Category::getName).collect(Collectors.toList());
    }

    /**
     * 获取分类与发布的文章
     *
     * @return 文章标签对象模型
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_CATEGORY_ARTICLE, key = "'front::categoryWithArticle'")
    public List<CategoryVo> selectCategoryWithArticle() {
        return categoryMapper.selectCategoryWithArticle();
    }

}
