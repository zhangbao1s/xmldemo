package com.adchina.api.cors;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 支持 CORS 跨域
 *
 * @author huangyong
 * @since 1.0.0
 */
public class CorsFilter implements Filter {

    private String allowOrigin;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowOrigin = filterConfig.getInitParameter("allowOrigin");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}
