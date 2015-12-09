package citic.gack.sso.web.dto;

import citic.gack.sso.base.BaseDTO;
import citic.gack.sso.entity.RegstUser;

public class UserRegistDTO extends BaseDTO<RegstUser>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String phoneNum;
	private String email;
	private String wechat;
	private String microblog;
	
	public UserRegistDTO(RegstUser user) {
		super(user);
		this.name = user.getName();
		this.phoneNum = user.getPhoneNum();
		this.email = user.getEmail();
		this.wechat = user.getWechat();
		this.microblog = user.getMicroblog();
	}
	
	public UserRegistDTO() {
		this.setEntity(new RegstUser());
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
}
