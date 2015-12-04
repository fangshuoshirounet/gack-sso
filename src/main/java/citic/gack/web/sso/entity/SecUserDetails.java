package citic.gack.web.sso.entity;

import citic.gack.web.sso.base.BaseModel;

public class SecUserDetails extends BaseModel{

	private int userId;
	private String name;
	private String phoneNum;
	private String email;
	private String password;
	private String wechat;
	private String microblog;
	private String regestDate;
	private String lastLoginDate;
	private String source;
	private String enterpriseId;
	private String pictureMp;
	private String pictureWeb;
	private int employeeId;
	private int status;
	private String accessToken;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getMicroblog() {
		return microblog;
	}
	public void setMicroblog(String microblog) {
		this.microblog = microblog;
	}
	public String getRegestDate() {
		return regestDate;
	}
	public void setRegestDate(String regestDate) {
		this.regestDate = regestDate;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getPictureMp() {
		return pictureMp;
	}
	public void setPictureMp(String pictureMp) {
		this.pictureMp = pictureMp;
	}
	public String getPictureWeb() {
		return pictureWeb;
	}
	public void setPictureWeb(String pictureWeb) {
		this.pictureWeb = pictureWeb;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
