package com.adchina.api.security;

/**
 * 令牌管理器
 *
 * @author huangyong
 * @since 1.0.0
 */
public interface TokenManager {

    String createToken();

    boolean checkToken(String token);

    void removeToken(String token);
}