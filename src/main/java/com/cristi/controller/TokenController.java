package com.cristi.controller;

import java.util.Map;

import com.cristi.customlog.annnotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cristi.authorization.annotation.Authorization;
import com.cristi.authorization.annotation.CurrentUser;
import com.cristi.authorization.manager.RedisTokenManager;
import com.cristi.authorization.model.TokenModel;
import com.cristi.entity.User;
import com.cristi.result.ResultResp;
import com.cristi.service.TokenService;

@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	TokenService tokenService;
	
	@Autowired
	RedisTokenManager redisTokenManager;
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<ResultResp> login(@RequestParam String userId, @RequestParam String password) {
        Assert.notNull(userId, "userId can not be empty");
        Assert.notNull(password, "password can not be empty");

        User user = tokenService.getUserById(userId);
        if (user == null ) {
            //提示用户名或密码错误
            return new ResponseEntity<>(new ResultResp(2,null,"未知用户"), HttpStatus.NOT_FOUND);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = redisTokenManager.createToken(user.getUserId().toString());
        return new ResponseEntity<>(new ResultResp(1,model,""), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/getuser")
	@ResponseBody
	@Authorization
	public ResponseEntity<ResultResp> getUser(@CurrentUser User user,@RequestBody Map<String, String> paraMap){
		System.out.println(paraMap.toString());
		return new ResponseEntity<>(new ResultResp(1,user,""),HttpStatus.OK);
	}

	@RequestMapping(value = "/log")
	@ResponseBody
	@Authorization
	@Log(value = "此处记录日志")
	public ResponseEntity<ResultResp> log(@RequestBody User user){
		System.out.println(user.toString());
		return new ResponseEntity<>(new ResultResp(1,user,""),HttpStatus.OK);
	}

}
