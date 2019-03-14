package com.cristi.redislock;

import com.cristi.util.RedisUtil;

//使用redis锁 防止重复提交
public class CheckRepeateUtil {
	
	public static final int intervals = 10;
	
	public static Boolean checkRepeateOrNot(String key){
		Long longOne = RedisUtil.incr(key);
		System.out.println(longOne);
		if (longOne > 1) {
			//TODO 防止多次提交 
			return false;
		}
		RedisUtil.expire(key, intervals);
		return true;
	}

	public static void main(String[] args) {
		String keyOne = "dingdan" + "test123456";
		Boolean torf = CheckRepeateUtil.checkRepeateOrNot(keyOne);
		System.out.println(torf);
	}

}
