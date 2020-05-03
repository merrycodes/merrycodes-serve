package com.merrycodes.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.merrycodes.model.entity.Article;
import com.merrycodes.utils.ToStringStyleUtils;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author MerryCodes
 * @date 2020/4/23 21:40
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveVo implements Serializable {

    public String year;

    public List<Article> articleList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
