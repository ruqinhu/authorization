package com.cristi.authorization.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.cristi.authorization.annotation.CurrentUser;
import com.cristi.config.Constants;
import com.cristi.dao.TokenDao;
import com.cristi.entity.User;

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	TokenDao tokenDao;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//如果被注解的参数是User并且注解为CurrentUser则进行参数注入
		if (parameter.getParameterType().isAssignableFrom(User.class)
				&& parameter.hasParameterAnnotation(CurrentUser.class)) {
			return true;
		}
		return false;
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndView, NativeWebRequest webRequest,
			WebDataBinderFactory webDataBinder) throws Exception {
		System.out.println("HandlerMethodArgumentResolver");
		String userId = (String) webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
		if (null != userId) {
			return tokenDao.getUserById(userId);
		}
		throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
	}

	

}
