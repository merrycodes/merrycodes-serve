package com.merrycodes.model.vo;

import com.merrycodes.model.entity.Article;
import lombok.Data;

import java.util.List;

/**
 * @author MerryCodes
 * @date 2020/4/23 21:40
 */
@Data
public class ArchiveVo {

    public String year;

    public List<Article> articleList;
}
