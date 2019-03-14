package com.cristi.redislock;

import java.util.Collections;

import redis.clients.jedis.Jedis;

//可应用在秒杀争夺库存场景
public class RedisTool {
	 
    private static final String LOCK_SUCCESS = "OK";
    //即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
    private static final String SET_IF_NOT_EXIST = "NX";
    //我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    
    private static final Long RELEASE_SUCCESS = 1L;
 
    /**
     * 分布式锁四个条件：
     * 互斥性。在任意时刻，只有一个客户端能持有锁。
                   不会发生死锁。即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。
                   具有容错性。只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
                   解铃还须系铃人。加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了。
     */
    
    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识，识别哪个请求加的锁
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
 
    	//1. 当前没有锁（key不存在），那么就进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端。2. 已有锁存在，不做任何操作。
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
 
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
 
    }
    
    
    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        //lua脚本 ，并通过使用eval保证操作的原子性
        String script = "if redislock.call('get', KEYS[1]) == ARGV[1] then return redislock.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
 
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
 
    }
 
}
