package com.merrycodes.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 删除 Druid的广告过滤器
 *
 * @author MerryCodes
 * @date 2020/4/30 21:38
 */
public class RemoveAdvertisementFilter implements Filter {

    private final String commonJs;

    public RemoveAdvertisementFilter(String commonJs) {
        this.commonJs = commonJs;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        // 重置缓冲区，响应头不会被重置
        servletResponse.flushBuffer();
        servletResponse.getWriter().write(commonJs);
    }
}
