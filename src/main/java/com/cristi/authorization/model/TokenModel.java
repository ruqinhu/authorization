package com.cristi.authorization.model;

public class TokenModel {

	private String userId;
	
	private String token;
	
	public TokenModel(String token , String userId){
		this.userId = userId;
		this.token = token;
	}


	public TokenModel(){
		
	}

	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
