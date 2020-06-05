package com.merrycodes.service.intf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.merrycodes.model.entity.Tags;
import com.merrycodes.model.form.query.TagsQueryForm;
import com.merrycodes.model.vo.TagsVo;

import java.util.List;

/**
 * 文章标签service接口
 *
 * @author MerryCodes
 * @date 2020/4/11 16:42
 */
public interface TagsService extends IService<Tags> {

    /**
     * 文章标签分页查询 包括每个标签文章的数目
     *
     * @param current       当前页数
     * @param size          当前分页总页数
     * @param tagsQueryForm 文章标签查询表单类 （查询）{@link TagsQueryForm}
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<Tags> selectTagsPageWithCount(Integer current, Integer size, TagsQueryForm tagsQueryForm);

    /**
     * 获取文章标签名字的全部集合（用于文章列表查询的选项）
     *
     * @return 文章标签名字集合
     */
    List<String> selectTagsNameList();

    /**
     * 获取生效的文章标签名字的集合（用于新建文章的选项）
     *
     * @return 文章标签名字集合
     */
    List<String> selectTagsNameListByStatus();

    /**
     * 获取标签与发布的文章
     *
     * @return 文章标签对象模型
     */
    List<TagsVo> selectTagsWithArticle();

}
