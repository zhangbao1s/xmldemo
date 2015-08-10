package com.adchina.api.security.impl;

import com.adchina.api.security.TokenManager;
import com.adchina.api.util.CodecUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 基于 Redis 的令牌管理器
 *
 * @author huangyong
 * @since 1.0.0
 */
public class RedisTokenManager implements TokenManager {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 6379;
    private static final int DEFAULT_TIMEOUT = 2000;
    private static final int DEFAULT_DATABASE = 0;
    private static final int DEFAULT_SECONDS = 3600;
    private static final int DEFAULT_MAX_TOTAL = 100;
    private static final int DEFAULT_MAX_IDLE = 20;
    private static final int DEFAULT_MIN_IDLE = 5;
    private static final boolean DEFAULT_TEST_ON_BORROW = true;
    private static final boolean DEFAULT_TEST_ON_RETURN = true;

    private static JedisPool pool;

    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private int timeout = DEFAULT_TIMEOUT;
    private String password;
    private int database = DEFAULT_DATABASE;
    private int seconds = DEFAULT_SECONDS;
    private int maxTotal = DEFAULT_MAX_TOTAL;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;
    private boolean testOnBorrow = DEFAULT_TEST_ON_BORROW;
    private boolean testOnReturn = DEFAULT_TEST_ON_RETURN;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public void init() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        pool = new JedisPool(config, host, port, timeout, password, database);
    }

    @Override
    public String createToken(String username) {
        String token = CodecUtil.createUUID();
        try (Jedis jedis = pool.getResource()) {
            jedis.setex(token, seconds, username);
        }
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        boolean result;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.exists(token);
            jedis.expire(token, seconds);
        }
        return result;
    }
}
