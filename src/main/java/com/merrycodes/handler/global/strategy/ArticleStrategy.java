package com.merrycodes.handler.global.strategy;

/**
 * @author MerryCodes
 * @date 2020/6/11 9:14
 */
public class ArticleStrategy implements  GlobalExceptionStrategy{
    /**
     * 获取异常信息
     *
     * @return 异常信息
     */
    @Override
    public String getMessage() {
        return "文章标题已经存在，新建失败";
    }
}
