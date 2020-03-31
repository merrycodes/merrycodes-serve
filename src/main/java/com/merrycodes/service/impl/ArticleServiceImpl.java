package com.merrycodes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.merrycodes.entity.Article;
import com.merrycodes.mapper.ArticleMapper;
import com.merrycodes.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MerryCodes
 * @date 2020/3/30 15:56
 */
@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
