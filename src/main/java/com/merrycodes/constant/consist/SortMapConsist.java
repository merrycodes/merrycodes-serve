package com.merrycodes.constant.consist;

/**
 * 排序常量
 *
 * @author MerryCodes
 * @date 2020/4/5 11:31
 */
public interface SortMapConsist {

    /**
     * 顺序
     */
    String ASC = "asc";

    /**
     * 倒叙
     */
    String DESC = "desc";

    /**
     * 前端传来的按什么字段排序
     * createTime
     */
    String CREATE_TIME = "create";

    /**
     * 前端传来的按什么字段排序
     * updateTime
     */
    String UPDATE_TIME = "update";

    /**
     * 前端传来数据映射成 Map 的 key
     * value create/update
     */
    String NAME_KEY = "name";

    /**
     * 前端传来数据映射成 Map 的 key
     * value asc/desc
     */
    String SORT_KEY = "sort";

}
