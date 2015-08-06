package com.adchina.api.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 令牌拦截器
 *
 * @author huangyong
 * @since 1.0.0
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    private TokenManager tokenManager;

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        logger.debug("token: {}", token);
        boolean isExist = tokenManager.checkToken(token);
        if (!isExist) {
            String message = String.format("token [%s] is invalid", token);
            throw new TokenException(message);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String token = tokenManager.createToken();
        response.setHeader("X-Token", token);
        System.out.println("afterCompletion");
    }
}
