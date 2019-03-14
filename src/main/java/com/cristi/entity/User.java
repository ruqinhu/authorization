package com.cristi.entity;

public class User {
	private Integer userId;
	private String userName;
	private String password;
	private Integer userState;
	private Integer actionTime;
	private String actionIp;
	private String sessionId;
	private Integer userParentId;
	private Integer userParentShopId;
	private Integer inviteSubsiteId;
	private Integer isTjMember;
	private Integer userPlatform;
	private String clientToken;
	private String merchantToken;
	private String userMobile;
	private String userLastloginTime;// 最后登录时间
	private String token;

	private String userAvatar;

	private Integer userGender;

	private Integer sellerId;
	private String sellerName;
	private Integer shopId;
	private Integer sellerIsAdmin;
	private String limits;

	private String userIsNew;

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public Integer getUserGender() {
		return userGender;
	}

	public void setUserGender(Integer userGender) {
		this.userGender = userGender;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public Integer getUserPlatform() {
		return userPlatform;
	}

	public void setUserPlatform(Integer userPlatform) {
		this.userPlatform = userPlatform;
	}

	public String getClientToken() {
		return clientToken;
	}

	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}

	public String getMerchantToken() {
		return merchantToken;
	}

	public void setMerchantToken(String merchantToken) {
		this.merchantToken = merchantToken;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public Integer getActionTime() {
		return actionTime;
	}

	public void setActionTime(Integer actionTime) {
		this.actionTime = actionTime;
	}

	public String getActionIp() {
		return actionIp;
	}

	public void setActionIp(String actionIp) {
		this.actionIp = actionIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getUserParentId() {
		return userParentId;
	}

	public void setUserParentId(Integer userParentId) {
		this.userParentId = userParentId;
	}

	public Integer getUserParentShopId() {
		return userParentShopId;
	}

	public void setUserParentShopId(Integer userParentShopId) {
		this.userParentShopId = userParentShopId;
	}

	public Integer getInviteSubsiteId() {
		return inviteSubsiteId;
	}

	public void setInviteSubsiteId(Integer inviteSubsiteId) {
		this.inviteSubsiteId = inviteSubsiteId;
	}

	public Integer getIsTjMember() {
		return isTjMember;
	}

	public void setIsTjMember(Integer isTjMember) {
		this.isTjMember = isTjMember;
	}

	public String getUserLastloginTime() {
		return userLastloginTime;
	}

	public void setUserLastloginTime(String userLastloginTime) {
		this.userLastloginTime = userLastloginTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getSellerIsAdmin() {
		return sellerIsAdmin;
	}

	public void setSellerIsAdmin(Integer sellerIsAdmin) {
		this.sellerIsAdmin = sellerIsAdmin;
	}

	public String getLimits() {
		return limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}

	public String getUserIsNew() {
		return userIsNew;
	}

	public void setUserIsNew(String userIsNew) {
		this.userIsNew = userIsNew;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", password=" + password + ", userState="
				+ userState + ", actionTime=" + actionTime + ", actionIp=" + actionIp + ", sessionId=" + sessionId
				+ ", userParentId=" + userParentId + ", userParentShopId=" + userParentShopId + ", inviteSubsiteId="
				+ inviteSubsiteId + ", isTjMember=" + isTjMember + ", userPlatform=" + userPlatform + ", clientToken="
				+ clientToken + ", merchantToken=" + merchantToken + ", userMobile=" + userMobile
				+ ", userLastloginTime=" + userLastloginTime + ", token=" + token + ", userAvatar=" + userAvatar
				+ ", userGender=" + userGender + ", sellerId=" + sellerId + ", sellerName=" + sellerName + ", shopId="
				+ shopId + ", sellerIsAdmin=" + sellerIsAdmin + ", limits=" + limits + ", userIsNew=" + userIsNew + "]";
	}

}
