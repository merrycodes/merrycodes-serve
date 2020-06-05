package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.merrycodes.constant.enums.ArticleEnum;
import com.merrycodes.mapper.ArticleMapper;
import com.merrycodes.model.entity.Article;
import com.merrycodes.model.form.query.ArticleQueryForm;
import com.merrycodes.model.vo.ArchiveVo;
import com.merrycodes.service.intf.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.merrycodes.constant.consist.SortMapConsist.*;
import static com.merrycodes.constant.consist.CacheValueConsist.CACHE_VALUE_ARTICLE;

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
    @Cacheable(cacheNames = CACHE_VALUE_ARTICLE, key = "'front::articleInfo['+#id+']'")
    public Article selectArticleInfo(Integer id) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                // 查询出来的结果不包括下面的字段
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
    @Cacheable(cacheNames = CACHE_VALUE_ARTICLE, key = "'admin::articleInfo['+#id+']'")
    public Article selectEditArticleInfo(Integer id) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                // 查询出来的结果不包括下面的字段
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
     * @param current          当前页数
     * @param size             当前分页总页数
     * @param articleQueryForm 文章查询表单类 {@link Article}
     * @return 分页 Page 对象接口 {@link IPage}
     * @see <a href="https://github.com/baomidou/mybatis-plus/issues/467">参考链接</a>
     */
    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(cacheNames = CACHE_VALUE_ARTICLE, key = "'admin::ArticleList['+#current+':'+#size+':'+#articleQueryForm+']'")
    public IPage<Article> selectArticlePage(Integer current, Integer size, ArticleQueryForm articleQueryForm) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                // 查询出来的结果不包括下面的字段
                .select(Article.class, articleColumn -> !StringUtils.equals("html_content", articleColumn.getColumn()) &&
                        !StringUtils.equals("md_content", articleColumn.getColumn()) &&
                        !StringUtils.equals("summary_content", articleColumn.getColumn()) &&
                        !StringUtils.equals("allow_comment", articleColumn.getColumn()))
                .eq(articleQueryForm.getStatus() != null, Article::getStatus, articleQueryForm.getStatus())
                .like(StringUtils.isNotEmpty(articleQueryForm.getTitle()), Article::getTitle, articleQueryForm.getTitle())
                .like(StringUtils.isNotEmpty(articleQueryForm.getMdContent()), Article::getMdContent, articleQueryForm.getMdContent())
                .like(StringUtils.isNotEmpty(articleQueryForm.getTags()), Article::getTags, articleQueryForm.getTags())
                .like(StringUtils.isNotEmpty(articleQueryForm.getCategory()), Article::getCategory, articleQueryForm.getCategory());
        // 前端传来的排序数据 example sortMap = {name=update, sort=desc})
        Map<String, String> sortMap = articleQueryForm.getSort();
        if (sortMap != null) {
            // 判断是否等于 name 是否等于 update 如果是则 order by updateTime 否者 order by createTime
            if (StringUtils.equals(UPDATE_TIME, sortMap.get(NAME_KEY))) {
                // 判断前端传来按 顺序/倒叙 排序
                wrapper.orderByAsc(StringUtils.equals(ASC, sortMap.get(SORT_KEY)), Article::getUpdateTime)
                        .orderByDesc(StringUtils.equals(DESC, sortMap.get(SORT_KEY)), Article::getUpdateTime);
            } else {
                // 同上
                wrapper.orderByAsc(StringUtils.equals(ASC, sortMap.get(SORT_KEY)), Article::getCreateTime)
                        .orderByDesc(StringUtils.equals(DESC, sortMap.get(SORT_KEY)), Article::getCreateTime);
            }
        }
        return articleMapper.selectPage(page, wrapper);
    }

    /**
     * 获取所有的文章
     *
     * @return 文章集合
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_ARTICLE, key = "'front::ArticleListForSEO'")
    public List<Article> selectArticleList() {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                // 查询出来的结果仅包括下面的字段
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
    @Cacheable(cacheNames = CACHE_VALUE_ARTICLE, key = "'front::ArticleList['+#current+':'+#size+']'")
    public IPage<Article> selectArticlePageByStatus(Integer current, Integer size) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                // 查询出来的结果仅包括下面的字段
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
     *
     * @return 文章归档列表 {@link ArchiveVo}
     */
    @Override
    @Cacheable(cacheNames = CACHE_VALUE_ARTICLE, key = "'front::ArchiveList'")
    public List<ArchiveVo> selectArchiveList() {
        // 查询所有发布的文章集合
        List<Article> articleList = selectArticleListByStatus();
        // 返回对象
        List<ArchiveVo> archiveVoList = new ArrayList<>();
        Integer currentYear = null;
        for (Article article : articleList) {
            // 获取当前文章的年份
            Integer year = article.getCreateTime().getYear();
            if (year.equals(currentYear)) {
                // 相等，则获取最后一个ArchiveVo对象，把文章插入文章集合中
                ArchiveVo archiveVo = archiveVoList.get(archiveVoList.size() - 1);
                archiveVo.getArticleList().add(article);
            } else {
                // 不相等，则改变当前文章年份，新建一个ArchiveVo对象，并把文章插入文章集合中
                currentYear = year;
                ArchiveVo archiveVo = new ArchiveVo();
                archiveVo.setYear(String.valueOf(year));
                archiveVo.setArticleList(Lists.newArrayList(article));
                archiveVoList.add(archiveVo);
            }
        }
        return archiveVoList;
    }

    /**
     * 获取也发布的文章列表，按文章创建时间倒叙排序
     *
     * @return 文章集合
     */
    @Cacheable(cacheNames = CACHE_VALUE_ARTICLE, key = "'front::ArticleListForArchive'")
    public List<Article> selectArticleListByStatus() {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery()
                // 查询出来的结果仅包括下面的字段
                .select(Article::getId, Article::getTitle, Article::getCreateTime)
                .eq(Article::getStatus, ArticleEnum.PUBLISH.getCode())
                .orderByDesc(Article::getCreateTime);
        return articleMapper.selectList(wrapper);
    }

}
