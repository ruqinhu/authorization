package com.cristi.util;

//import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 1.1 字符串取值时进行延时操作 Redis 工具类 Created by zharounze
 */
public class RedisUtil {

	// protected static ReentrantLock lockPool = new ReentrantLock();
	// protected static ReentrantLock lockJedis = new ReentrantLock();

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

	// Redis服务器IP
	 private static String IP = "127.0.0.1";

	// Redis的端口号
	private static int PORT = 6379;

	 private static String PASSWORD = "";
	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 30;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 20;

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = -1;

	// 超时时间
	private static int TIMEOUT = 3000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = false;

	private static JedisPool jedisPool = null;

	/**
	 * redis过期时间,以秒为单位
	 */
	// 一小时
	public final static int EXRP_HOUR = 60 * 60;
	// 一天
	public final static int EXRP_DAY = 60 * 60 * 24;
	// 一个月
	public final static int EXRP_MONTH = 60 * 60 * 24 * 30;

	/**
	 * 初始化Redis连接池
	 */
	private static void initialPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, IP, PORT, TIMEOUT);
		} catch (Exception e) {
			LOGGER.error("First create JedisPool error : " + e);
		}
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (null == jedisPool) {
			initialPool();
		}
	}

	/**
	 * 同步获取Jedis实例
	 * 
	 * @return Jedis
	 */
	public synchronized static Jedis getJedis() {
		poolInit();
		Jedis jedis = null;
		try {
			if (null != jedisPool) {
				jedis = jedisPool.getResource();
				try {
					jedis.auth(PASSWORD);
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {
			LOGGER.error("Get jedis error : " + e);
		}
		return jedis;
	}

	/**
	 * 设置 String
	 * 
	 * @param key
	 * @param value
	 */
	public synchronized static void set(String key, String value) {
		try {
			value = "".equals(value) ? "" : value;
			Jedis jedis = getJedis();
			jedis.set(key, value);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("Set key error : " + e);
		}
	}

	/**
	 * 设置 byte[]
	 * 
	 * @param key
	 * @param value
	 */
	public synchronized static void set(byte[] key, byte[] value) {
		try {
			Jedis jedis = getJedis();
			jedis.set(key, value);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("Set key error : " + e);
		}
	}

	/**
	 * 设置 String 过期时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            以秒为单位
	 */
	public synchronized static void set(String key, String value, int seconds) {
		try {
			// value = !"".equals(value) ? "" : value;
			Jedis jedis = getJedis();
			jedis.setex(key, seconds, value);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("Set keyex error : " + e);
		}
	}

	/**
	 * 设置 byte[] 过期时间
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            以秒为单位
	 */
	public synchronized static void set(byte[] key, byte[] value, int seconds) {
		try {
			Jedis jedis = getJedis();
			jedis.set(key, value);
			jedis.expire(key, seconds);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("Set key error : " + e);
		}
	}

	/**
	 * 获取String值 ,如果不为null或者“”，就进行延时操作
	 *可在此处进行延时操作
	 * @param key
	 * @return value
	 */
	public synchronized static String get(String key) {
		Jedis jedis = getJedis();
		if (null == jedis) {
			return null;
		}
		String value = jedis.get(key);
//		if (null != value && !"".equals(value)) {
//			jedis.expire(key, FkGouConfig.TOKEN_OUTTIME);
//		}
		jedis.close();
		return value;
	}

	/**
	 * 获取byte[]值
	 * 
	 * @param key
	 * @return value
	 */
	public synchronized static byte[] get(byte[] key) {
		Jedis jedis = getJedis();
		if (null == jedis) {
			return null;
		}
		byte[] value = jedis.get(key);
		jedis.close();
		return value;
	}

	/**
	 * 删除值
	 * 
	 * @param key
	 */
	public synchronized static void remove(String key) {
		try {
			Jedis jedis = getJedis();
			jedis.del(key);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("Remove keyex error : " + e);
		}
	}

	/**
	 * 删除值
	 * 
	 * @param key
	 */
	public synchronized static void remove(byte[] key) {
		try {
			Jedis jedis = getJedis();
			jedis.del(key);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("Remove keyex error : " + e);
		}
	}

	/**
	 * lpush
	 * 
	 * @param key
	 * @param key
	 */
	public synchronized static void lpush(String key, String... strings) {
		try {
			Jedis jedis = RedisUtil.getJedis();
			jedis.lpush(key, strings);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("lpush error : " + e);
		}
	}

	/**
	 * lpush
	 * 
	 * @param key
	 * @param key
	 */
	public synchronized static void lpush(String key, Integer seconds, String... strings) {
		try {
			Jedis jedis = RedisUtil.getJedis();
			jedis.lpush(key, strings);
			jedis.expire(key, seconds);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("lpush error : " + e);
		}
	}

	/**
	 * lrem
	 * 
	 * @param key
	 * @param count
	 * @param value
	 */
	public synchronized static void lrem(String key, long count, String value) {
		try {
			Jedis jedis = RedisUtil.getJedis();
			jedis.lrem(key, count, value);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("lpush error : " + e);
		}
	}

	/**
	 * sadd
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public synchronized static void sadd(String key, String value, int seconds) {
		try {
			Jedis jedis = RedisUtil.getJedis();
			jedis.sadd(key, value);
			jedis.expire(key, seconds);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("sadd error : " + e);
		}
	}

	/**
	 * incr
	 * 
	 * @param key
	 * @return value
	 */
	public synchronized static Long incr(String key) {
		Jedis jedis = getJedis();
		if (null == jedis) {
			return null;
		}
		long value = jedis.incr(key);
		jedis.close();
		return value;
	}

	/**
	 * decr
	 * 
	 * @param key
	 * @return value
	 */
	public synchronized static Long decr(String key) {
		Jedis jedis = getJedis();
		if (null == jedis) {
			return null;
		}
		long value = jedis.decr(key);
		jedis.close();
		return value;
	}

	/**
	 * expire
	 * 
	 * @param key,seconds
	 * @return
	 */
	public synchronized static void expire(String key, int seconds) {
		Jedis jedis = getJedis();
		try {
			jedis.expire(key, seconds);
			jedis.close();
		} catch (Exception e) {
			LOGGER.error("Set keyex error : " + e);
		}
	}

}