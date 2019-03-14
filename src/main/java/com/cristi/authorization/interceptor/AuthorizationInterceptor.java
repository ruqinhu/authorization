package com.cristi.authorization.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cristi.authorization.annotation.Authorization;
import com.cristi.authorization.manager.RedisTokenManager;
import com.cristi.authorization.model.JsonRequestWrapper;
import com.cristi.authorization.model.TokenModel;
import com.cristi.config.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	RedisTokenManager redisTokenManager;
	
	@Autowired
	ObjectMapper objectMapper;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle");
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// 判断是否有Authorization注解，如果没有则不进行拦截
		if (method.getAnnotation(Authorization.class) == null) {
			return true;
		}
		JsonRequestWrapper jsonRequestWrapper = new JsonRequestWrapper(request);
		String body = jsonRequestWrapper.getBody();
		System.out.println("body" + body);

		TokenModel tokenModel = objectMapper.readValue(body, TokenModel.class);
		
		String authorization = tokenModel.getToken();
		if (!redisTokenManager.checkToken(authorization)) {
			// 如果验证失败，则返回401
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		// 如果验证成功，则将token对应userId保存在request中，便于注入
		tokenModel = redisTokenManager.getToken(authorization);
		request.setAttribute(Constants.CURRENT_USER_ID, tokenModel.getUserId());
		return super.preHandle(request, response, handler);

	}
	
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//		System.out.println("preHandle");
//		HandlerMethod handlerMethod = (HandlerMethod) handler;
//		Method method = handlerMethod.getMethod();
//		// 判断是否有Authorization注解，如果没有则不进行拦截
//		if (method.getAnnotation(Authorization.class) == null) {
//			return true;
//		}
//		String authorization = request.getParameter("authorization");
//		if (!redisTokenManager.checkToken(authorization)) {
//			// 如果验证失败，则返回401
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			return false;
//		}
//		// 如果验证成功，则将token对应userId保存在request中，便于注入
//		TokenModel tokenModel = redisTokenManager.getToken(authorization);
//		request.setAttribute(Constants.CURRENT_USER_ID, tokenModel.getUserId());
//		return true;
//
//	}
}
