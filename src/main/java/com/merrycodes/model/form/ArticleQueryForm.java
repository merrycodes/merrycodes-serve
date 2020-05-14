package com.merrycodes.model.form;

import com.merrycodes.model.form.abstracts.AbstractSortForm;
import com.merrycodes.utils.ToStringStyleUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 文章查询表单
 *
 * @author MerryCodes
 * @date 2020/5/13 19:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章查询表当类")
public class ArticleQueryForm extends AbstractSortForm {

    /**
     * 文章标题
     */
    @ApiModelProperty("文章标题")
    private String title;

    /**
     * 默认为已发布
     * 0表示草稿，1表示已发布，2表示取消发布
     */
    @ApiModelProperty("文章状态")
    private Integer status;

    /**
     * 文章标签
     */
    @ApiModelProperty("文章标签")
    private String tags;

    /**
     * 文章分类
     */
    @ApiModelProperty("文章分类")
    private String category;

    /**
     * 文章内容-Markdown格式
     */
    @ApiModelProperty("文章内容-Markdown格式")
    private String mdContent;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyleUtils.NO_NULL_STYLE);
    }
}
