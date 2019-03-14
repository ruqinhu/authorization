package com.cristi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristi.dao.TokenDao;
import com.cristi.entity.User;

@Service
public class TokenService {

	@Autowired
	TokenDao tokenDao;
	
	public User getUserById(String userId){
		return tokenDao.getUserById(userId);
	}
}
