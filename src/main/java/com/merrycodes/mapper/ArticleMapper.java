package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.merrycodes.model.entity.Article;
import org.springframework.stereotype.Repository;

/**
 * 文章 CRUD操作
 *
 * @author MerryCodes
 * @date 2020/3/30 15:54
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}
