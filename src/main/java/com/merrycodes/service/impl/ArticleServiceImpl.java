package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.merrycodes.constant.SortMapConstant;
import com.merrycodes.entity.Article;
import com.merrycodes.enums.ArticleEnum;
import com.merrycodes.mapper.ArticleMapper;
import com.merrycodes.service.ArticleService;
import com.merrycodes.vo.ArchiveVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 文章service接口实现类
 *
 * @author MerryCodes
 * @date 2020/3/30 15:56
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private final ArticleMapper articleMapper;

    /**
     * 根据文章id获取文章
     *
     * @param id 文章id
     * @return 文章实体类 {@link Article}
     */
    @Override
    public Article selectArticleInfo(Integer id) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .select(Article.class, article -> !StringUtils.equals("status", article.getColumn()) &&
                        !StringUtils.equals("summary_content", article.getColumn()) &&
                        !StringUtils.equals("md_content", article.getColumn()))
                .eq(Article::getId, id);
        return articleMapper.selectOne(wrapper);
    }

    /**
     * 根据id查询文章
     *
     * @param id 文章id
     * @return 文章实体类 {@link Article}
     */
    @Override
    public Article selectEditArticleInfo(Integer id) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .select(Article.class, article -> !StringUtils.equals("create_time", article.getColumn()) &&
                        !StringUtils.equals("update_time", article.getColumn()) &&
                        !StringUtils.equals("browse", article.getColumn()))
                .eq(Article::getId, id);
        return articleMapper.selectOne(wrapper);
    }

    /**
     * 条件查询获取文章列表
     * 使用 orderByDesc / orderByDesc 编译器会有警告 使用注解抹去
     *
     * @param current 当前页数
     * @param size    当前分页总页数
     * @param article 文章实体类 {@link Article}
     * @return 分页 Page 对象接口 {@link IPage}
     * @see <a href="https://github.com/baomidou/mybatis-plus/issues/467"></a>
     */
    @Override
    @SuppressWarnings("unchecked")
    public IPage<Article> selectArticlePage(Integer current, Integer size, Article article) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .eq(article.getStatus() != null, Article::getStatus, article.getStatus())
                .like(StringUtils.isNotEmpty(article.getTitle()), Article::getTitle, article.getTitle())
                .like(StringUtils.isNotEmpty(article.getMdContent()), Article::getMdContent, article.getMdContent())
                .like(StringUtils.isNotEmpty(article.getTags()), Article::getTags, article.getTags())
                .like(StringUtils.isNotEmpty(article.getCategory()), Article::getCategory, article.getCategory());
        // 前端传来的排序数据 example sortMap = {name=update, sort=desc})
        Map<String, String> sortMap = article.getSort();
        // 判断是否等于 name 是否等于 update 如果是则 order by updateTime 否者 order by createTime
        if (StringUtils.equals(SortMapConstant.UPDATE_TIME, sortMap.get(SortMapConstant.NAME_KEY))) {
            // 判断前端传来按 顺序/倒叙 排序
            wrapper.orderByAsc(StringUtils.equals(SortMapConstant.ASC, sortMap.get(SortMapConstant.SORT_KEY)), Article::getUpdateTime)
                    .orderByDesc(StringUtils.equals(SortMapConstant.DESC, sortMap.get(SortMapConstant.SORT_KEY)), Article::getUpdateTime);
        } else {
            // 同上
            wrapper.orderByAsc(StringUtils.equals(SortMapConstant.ASC, sortMap.get(SortMapConstant.SORT_KEY)), Article::getCreateTime)
                    .orderByDesc(StringUtils.equals(SortMapConstant.DESC, sortMap.get(SortMapConstant.SORT_KEY)), Article::getCreateTime);
        }
        return articleMapper.selectPage(page, wrapper);
    }

    /**
     * 获取所有的文章
     *
     * @return 文章集合
     */
    @Override
    public List<Article> selectArticleList() {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .select(Article::getId, Article::getTitle, Article::getSummaryContent)
                .eq(Article::getStatus, ArticleEnum.PUBLISH.getCode());
        return articleMapper.selectList(wrapper);
    }

    /**
     * 文章分页查询通过状态 (分页)
     *
     * @param current 当前页数
     * @param size    当前分页总页数
     * @return 分页 Page 对象接口 {@link IPage}
     */
    @Override
    public IPage<Article> selectArticlePageByStatus(Integer current, Integer size) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .select(Article.class, article -> !StringUtils.equals("html_content", article.getColumn()) &&
                        !StringUtils.equals("md_content", article.getColumn()) &&
                        !StringUtils.equals("status", article.getColumn()) &&
                        !StringUtils.equals("update_time", article.getColumn()))
                .eq(Article::getStatus, ArticleEnum.PUBLISH.getCode())
                .orderByDesc(Article::getCreateTime);
        return articleMapper.selectPage(page, wrapper);
    }

    /**
     * 获取文章归档列表
     * 归档还有一种思路，在数据库查询年的集合
     * 然后循环年的集合在数据库中查询数据
     *
     * @return 文章归档列表 {@link ArchiveVo}
     */
    @Override
    public List<ArchiveVo> selectArchiveList() {
        List<Article> articleList = selectArticleListByStatus();
        List<ArchiveVo> archiveVoList = new ArrayList<>();
        Integer currentYear = null;
        for (Article article : articleList) {
            Integer year = article.getCreateTime().getYear();
            if (year.equals(currentYear)) {
                ArchiveVo archiveVo = archiveVoList.get(archiveVoList.size() - 1);
                archiveVo.getArticleList().add(article);
            } else {
                currentYear = year;
                ArchiveVo archiveVo = new ArchiveVo();
                archiveVo.setYear(String.valueOf(year));
                archiveVo.setArticleList(Lists.newArrayList(article));
                archiveVoList.add(archiveVo);
            }
        }
        return archiveVoList;
    }

    private List<Article> selectArticleListByStatus() {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                .select(Article::getId, Article::getTitle, Article::getCreateTime)
                .eq(Article::getStatus, ArticleEnum.PUBLISH.getCode())
                .orderByDesc(Article::getCreateTime);
        return articleMapper.selectList(wrapper);
    }

}
