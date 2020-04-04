package com.merrycodes.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * 分页返回对象
 *
 * @author MerryCodes
 * @date 2020/4/4 23:39
 */
@Data
public class PaginationVo<T> {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 分页总页数
     */
    private Long size;

    /**
     * 当前页数
     */
    private Long current;

    /**
     * 分页数据列表
     */
    private List<T> list;

    public PaginationVo(IPage<T> iPage) {
        this.total = iPage.getTotal();
        this.size = iPage.getSize();
        this.current = iPage.getCurrent();
        this.list = iPage.getRecords();
    }
}
