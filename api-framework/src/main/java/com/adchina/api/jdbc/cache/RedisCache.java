package com.adchina.api.jdbc.cache;

import com.adchina.api.util.PropsUtil;
import com.adchina.api.util.SerializationUtil;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 基于 Redis 的缓存
 *
 * @author huangyong
 * @since 1.0.0
 */
public class RedisCache implements Cache {

    private static final String CONFIG = "config.properties";
    private static final String HOST = "redis.host";
    private static final String PORT = "redis.port";
    private static final String PASSWORD = "redis.password";
    private static final String TIMEOUT = "redis.timeout";
    private static final String MAX_TOTAL = "redis.max_total";
    private static final String MAX_IDLE = "redis.max_idle";
    private static final String MIN_IDLE = "redis.min_idle";
    private static final String TEST_ON_BORROW = "redis.test_on_borrow";
    private static final String TEST_ON_RETURN = "redis.test_on_return";

    private static Properties config = PropsUtil.loadProps(CONFIG);

    private String id;
    private JedisPool pool;

    public RedisCache(String id) {
        this.id = id;

        String host = PropsUtil.getString(config, HOST);
        int port = PropsUtil.getInt(config, PORT);
        int timeout = PropsUtil.getInt(config, TIMEOUT);
        String password = PropsUtil.getString(config, PASSWORD);

        JedisPoolConfig config = new JedisPoolConfig();
        int maxTotal = PropsUtil.getInt(RedisCache.config, MAX_TOTAL);
        if (maxTotal != 0) {
            config.setMaxTotal(maxTotal);
        }
        int maxIdle = PropsUtil.getInt(RedisCache.config, MAX_IDLE);
        if (maxIdle != 0) {
            config.setMaxIdle(maxIdle);
        }
        int minIdle = PropsUtil.getInt(RedisCache.config, MIN_IDLE);
        if (minIdle != 0) {
            config.setMinIdle(minIdle);
        }
        boolean testOnBorrow = PropsUtil.getBoolean(RedisCache.config, TEST_ON_BORROW);
        if (testOnBorrow) {
            config.setTestOnBorrow(true);
        }
        boolean testOnReturn = PropsUtil.getBoolean(RedisCache.config, TEST_ON_RETURN);
        if (testOnReturn) {
            config.setTestOnReturn(true);
        }

        pool = new JedisPool(config, host, port, timeout, password);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.hset(id.getBytes(), key.toString().getBytes(), SerializationUtil.serialize(value));
        }
    }

    @Override
    public Object getObject(Object key) {
        try (Jedis jedis = pool.getResource()) {
            return SerializationUtil.deserialize(jedis.hget(id.getBytes(), key.toString().getBytes()));
        }
    }

    @Override
    public Object removeObject(Object key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hdel(key.toString().getBytes());
        }
    }

    @Override
    public void clear() {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(id);
        }
    }

    @Override
    public int getSize() {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hgetAll(id).size();
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return new ReentrantReadWriteLock();
    }
}
