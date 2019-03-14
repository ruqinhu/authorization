package com.cristi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cristi.entity.User;
import org.springframework.stereotype.Repository;

@Mapper
public interface TokenDao {

	@Select("select * from udb_ucenter_user_info where user_id = #{userId}")
	public User getUserById(String userId);
	
}
