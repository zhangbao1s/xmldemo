package com.adchina.api.security.impl;

import com.adchina.api.security.TokenManager;
import com.adchina.api.util.CodecUtil;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 默认令牌管理器
 *
 * @author huangyong
 * @since 1.0.0
 */
public class DefaultTokenManager implements TokenManager {

    private static Set<String> tokenSet = new ConcurrentSkipListSet<>();

    @Override
    public String createToken() {
        String token = CodecUtil.createUUID();
        tokenSet.add(token);
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        return tokenSet.contains(token);
    }

    @Override
    public void removeToken(String token) {
        tokenSet.remove(token);
    }
}
