package com.merrycodes.model.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页返回对象模型
 *
 * @author MerryCodes
 * @date 2020/4/4 23:39
 */
@ApiModel(description = "分页返回对象模型")
@Data
public class PaginationVo<T> {

    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private Long total;

    /**
     * 当前分页总页数
     */
    @ApiModelProperty(value = "当前分页总页数")
    private Long size;

    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数")
    private Long current;

    /**
     * 分页数据列表
     */
    @ApiModelProperty(value = "分页数据列表")
    private List<T> list;

    public PaginationVo(IPage<T> iPage) {
        this.total = iPage.getTotal();
        this.size = iPage.getSize();
        this.current = iPage.getCurrent();
        this.list = iPage.getRecords();
    }
}
