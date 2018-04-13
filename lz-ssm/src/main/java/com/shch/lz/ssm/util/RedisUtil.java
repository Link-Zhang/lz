package com.shch.lz.ssm.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Link at 11:24 on 4/13/18.
 */
public class RedisUtil {
    protected static ReentrantLock lockPool = new ReentrantLock();

    protected static ReentrantLock lockJedis = new ReentrantLock();

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RedisUtil.class);

    private static String IP = PropertiesFileUtil.getInstance("redis").get("master.redis.ip");

    private static int PORT = PropertiesFileUtil.getInstance("redis").getInteger("master.redis.port");

    private static String PASSWORD = AesUtil.aesDecode(PropertiesFileUtil.getInstance("redis").get("master.redis.password"));

    private static int MAX_ACTIVE = PropertiesFileUtil.getInstance("redis").getInteger("master.redis.max_active");

    private static int MAX_IDLE = PropertiesFileUtil.getInstance("redis").getInteger("master.redis.max_idle");

    private static int MAX_WAIT = PropertiesFileUtil.getInstance("redis").getInteger("master.redis.max_wait");

    private static int TIMEOUT = PropertiesFileUtil.getInstance("redis").getInteger("master.redis.timeout");

    private static boolean TEST_ON_BORROW = false;

    private static JedisPool jedisPool = null;

    public final static int EXRP_HOUR = 60 * 60;

    public final static int EXRP_DAY = 60 * 60 * 24;

    public final static int EXRP_MONTH = 60 * 60 * 24 * 30;

    private static void initialPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(MAX_ACTIVE);
        jedisPoolConfig.setMaxIdle(MAX_IDLE);
        jedisPoolConfig.setMaxWaitMillis(MAX_WAIT);
        jedisPoolConfig.setTestOnBorrow(TEST_ON_BORROW);
        jedisPool = new JedisPool(jedisPoolConfig, IP, PORT, TIMEOUT);
    }

    private static synchronized void poolInit() {
        if (null == jedisPool) {
            initialPool();
        }
    }

    public synchronized static Jedis getJedis() {
        poolInit();
        Jedis jedis = null;
        if (null != jedisPool) {
            jedis = jedisPool.getResource();
            jedis.auth(PASSWORD);
        }
        return jedis;
    }

    public synchronized static void set(String key, String value) {
        value = StringUtils.isBlank(value) ? "" : value;
        Jedis jedis = getJedis();
        jedis.set(key, value);
        jedis.close();
    }

    public synchronized static void set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        jedis.close();
    }

    public synchronized static void set(String key, String value, int seconds) {
        value = StringUtils.isBlank(value) ? "" : value;
        Jedis jedis = getJedis();
        jedis.setex(key, seconds, value);
        jedis.close();
    }

    public synchronized static void set(byte[] key, byte[] value, int seconds) {
        Jedis jedis = getJedis();
        jedis.set(key, value);
        jedis.expire(key, seconds);
        jedis.close();
    }

    public synchronized static String get(String key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    public synchronized static byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        byte[] value = jedis.get(key);
        jedis.close();
        return value;
    }

    public synchronized static void remove(String key) {
        Jedis jedis = getJedis();
        jedis.del(key);
        jedis.close();
    }

    public synchronized static void remove(byte[] key) {
        Jedis jedis = getJedis();
        jedis.del(key);
        jedis.close();
    }

    public synchronized static void lpush(String key, String... strings) {
        Jedis jedis = RedisUtil.getJedis();
        jedis.lpush(key, strings);
        jedis.close();
    }

    public synchronized static void lrem(String key, long count, String value) {
        Jedis jedis = RedisUtil.getJedis();
        jedis.lrem(key, count, value);
        jedis.close();
    }

    public synchronized static void sadd(String key, String value, int seconds) {
        Jedis jedis = RedisUtil.getJedis();
        jedis.sadd(key, value);
        jedis.expire(key, seconds);
        jedis.close();
    }

    public synchronized static Long incr(String key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        long value = jedis.incr(key);
        jedis.close();
        return value;
    }

    public synchronized static Long decr(String key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        long value = jedis.decr(key);
        jedis.close();
        return value;
    }
}
