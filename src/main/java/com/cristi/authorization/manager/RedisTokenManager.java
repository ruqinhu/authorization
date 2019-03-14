package com.cristi.authorization.manager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.cristi.authorization.model.TokenModel;
import com.cristi.config.Constants;

@Component
public class RedisTokenManager {
	
	@Autowired
	private StringRedisTemplate redis;
    
    public TokenModel createToken(String userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(token, userId);
        //存储到redis并设置过期时间
        redis.boundValueOps(token).set(userId, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);;
        return model;
    }
    
    public TokenModel getToken(String authorization) {
        if(null == authorization || authorization.length() == 0) {
			return null;
		}
        String userId = redis.boundValueOps(authorization).get();
        if (null == userId) {
			return null;
		}
        return new TokenModel(authorization, userId);
    }
    
    public Boolean checkToken(String authorization) {
        if(null == authorization || authorization.length() == 0) {
			return false;
		}
        String userId = redis.boundValueOps(authorization).get();
        if (null == userId) {
			return false;
		}
        //登陆之后则延长过期时间
        redis.boundValueOps(authorization).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return true;
    }
    
    public void deleteToken(String authorization){
    	redis.delete(authorization);
    }
}
