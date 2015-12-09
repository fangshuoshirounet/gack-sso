package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
public class SysUser extends BaseModel {
	/**
	 * 系统用户标识
	 */
	protected String sysUserId;
	/**
	 * 电子邮箱
	 */

	protected String email;
	/**
	 * 生日日期
	 */
	protected String birthday;

	/**
	 * 身份证号
	 */
	protected String idNumber;

	/**
	 * 工号
	 */
	protected String employeeNo;

	/**
	 * 职责
	 */
	protected String dutyId;
	/**
	 * 用户登录名
	 */
	protected String loginName;

	/**
	 * 用户密码
	 */
	protected String password;

	/**
	 * 密码设置时间
	 */
	protected String pwdSetTime;

	/**
	 * 密码失效时间
	 */
	protected String pwdInactiveTime;

	/**
	 * 前次密码
	 */
	protected String lastPwd;

	/**
	 * 是否初始化密码
	 */
	protected String initPwdFlag;
	/**
	 * 备注
	 */
	protected String remarks;
	/**
	 * 手机
	 */
	protected String mobile;
	/**
	 * 家庭电话
	 */
	protected String homePhone;
	/**
	 * 机构标识
	 */
	protected String orgId;
	/**
	 * 姓名
	 */
	protected String name;
	/**
	 * 首字母缩写 名称的首字母缩写，大写
	 */
	protected String acronym;
	/**
	 * 性别
	 */
	protected String sex;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDutyId() {
		return dutyId;
	}

	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPwdSetTime() {
		return pwdSetTime;
	}

	public void setPwdSetTime(String pwdSetTime) {
		this.pwdSetTime = pwdSetTime;
	}

	public String getPwdInactiveTime() {
		return pwdInactiveTime;
	}

	public void setPwdInactiveTime(String pwdInactiveTime) {
		this.pwdInactiveTime = pwdInactiveTime;
	}

	public String getLastPwd() {
		return lastPwd;
	}

	public void setLastPwd(String lastPwd) {
		this.lastPwd = lastPwd;
	}

	public String getInitPwdFlag() {
		return initPwdFlag;
	}

	public void setInitPwdFlag(String initPwdFlag) {
		this.initPwdFlag = initPwdFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
