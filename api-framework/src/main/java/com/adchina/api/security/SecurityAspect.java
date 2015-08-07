package com.adchina.api.security;

import com.adchina.api.util.StringUtil;
import com.adchina.api.web.WebContext;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于检查并生成 token 的切面
 *
 * @author huangyong
 * @since 1.0.0
 */
@Aspect
public class SecurityAspect {

    private static Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

    private static final String DEFAULT_TOKEN_NAME = "X-Token";

    private TokenManager tokenManager;
    private String tokenName;

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public void setTokenName(String tokenName) {
        if (StringUtil.isEmpty(tokenName)) {
            tokenName = DEFAULT_TOKEN_NAME;
        }
        this.tokenName = tokenName;
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 从 request header 中获取当前 token
        String token = WebContext.getRequest().getHeader(tokenName);
        // 若忽略了安全性检查，则无需检查 token，只需生成 token
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            createToken(token);
            return pjp.proceed();
        }
        // 首先检查 token，然后生成 token
        Object result;
        String oldToken = checkToken(token);
        logger.debug("old token: {}", oldToken);
        try {
            result = pjp.proceed();
        } finally {
            createToken(oldToken);
        }
        return result;
    }

    private String checkToken(String token) {
        // 从 request header 中获取 token，并检查 token 的有效性
        if (!tokenManager.checkToken(token)) {
            String message = String.format("token [%s] is invalid", token);
            throw new TokenException(message);
        }
        return token;
    }

    private void createToken(String oldToken) {
        // 首先移除 token，然后生成 token，并将其写入 response header 中
        tokenManager.removeToken(oldToken);
        String newToken = tokenManager.createToken();
        logger.debug("new token: {}", newToken);
        WebContext.getResponse().setHeader(tokenName, newToken);
    }
}
