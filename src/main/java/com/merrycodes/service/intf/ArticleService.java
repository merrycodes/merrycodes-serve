package com.merrycodes.service.intf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.model.entity.Article;
import com.merrycodes.model.vo.ArchiveVo;

import java.util.List;

/**
 * 文章service接口
 *
 * @author MerryCodes
 * @date 2020/3/30 15:56
 */
public interface ArticleService extends IService<Article> {

    /**
     * 根据文章id获取文章
     *
     * @param id 文章id
     * @return 文章实体类 {@link Article}
     */
    Article selectArticleInfo(Integer id);

    /**
     * 根据文章id获取文章
     *
     * @param id 文章id
     * @return 文章实体类 {@link Article}
     */
    Article selectEditArticleInfo(Integer id);

    /**
     * 文章分页查询 (分页)
     *
     * @param current 当前页数
     * @param size    当前分页总页数
     * @param article 文章实体类 {@link Article}
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Article> selectArticlePage(Integer current, Integer size, Article article);


    /**
     * 获取所有的文章
     *
     * @return 文章集合
     */
    List<Article> selectArticleList();

    /**
     * 文章分页查询通过状态 (分页)
     *
     * @param current 当前页数
     * @param size    当前分页总页数
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Article> selectArticlePageByStatus(Integer current, Integer size);

    /**
     * 获取文章归档列表
     *
     * @return 文章归档列表 {@link ArchiveVo}
     */
    List<ArchiveVo> selectArchiveList();

}
