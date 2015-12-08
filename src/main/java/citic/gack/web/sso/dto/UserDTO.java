package citic.gack.web.sso.dto;

import citic.gack.web.sso.base.BaseDTO;
import citic.gack.web.sso.entity.SecUser;

public class UserDTO extends BaseDTO<SecUser>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String name;
	private String phoneNum;
	private String email;
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
	
	public UserDTO(SecUser user) {
		super(user);
		this.userId = user.getUserId();
		this.name = user.getName();
		this.phoneNum = user.getPhoneNum();
		this.email = user.getEmail();
		this.wechat = user.getWechat();
		this.microblog = user.getMicroblog();
		this.regestDate = user.getRegestDate();
		this.lastLoginDate = user.getLastLoginDate();
		this.source = user.getSource();
		this.enterpriseId = user.getEnterpriseId();
		this.pictureMp = user.getPictureMp();
		this.pictureWeb = user.getPictureWeb();
		this.employeeId = user.getEmployeeId();
		this.status = user.getStatus();
	}
	
	public UserDTO() {
		this.setEntity(new SecUser());
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
		this.getEntity().setUserId(userId);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.getEntity().setName(name);
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
		this.getEntity().setPhoneNum(phoneNum);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
		this.getEntity().setEmail(email);
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
		this.getEntity().setWechat(wechat);
	}
	public String getMicroblog() {
		return microblog;
	}
	public void setMicroblog(String microblog) {
		this.microblog = microblog;
		this.getEntity().setMicroblog(microblog);
	}
	public String getRegestDate() {
		return regestDate;
	}
	public void setRegestDate(String regestDate) {
		this.regestDate = regestDate;
		this.getEntity().setRegestDate(regestDate);
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
		this.getEntity().setLastLoginDate(lastLoginDate);
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
		this.getEntity().setSource(source);
	}
	public String getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
		this.getEntity().setEnterpriseId(enterpriseId);
	}
	public String getPictureMp() {
		return pictureMp;
	}
	public void setPictureMp(String pictureMp) {
		this.pictureMp = pictureMp;
		this.getEntity().setPictureMp(pictureMp);
	}
	public String getPictureWeb() {
		return pictureWeb;
	}
	public void setPictureWeb(String pictureWeb) {
		this.pictureWeb = pictureWeb;
		this.getEntity().setPictureWeb(pictureWeb);
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
		this.getEntity().setEmployeeId(employeeId);
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
		this.getEntity().setStatus(status);
	}
}
