package citic.gack.sso.admin.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysRole;
import citic.gack.sso.entity.SysUser;
import citic.gack.sso.utils.ConstantsUtils;

/**
 * 用户授权(SysUserPermissionMVO)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-18上午10:55:24
 */
@SuppressWarnings("serial")
public class SysUserPermissionDTO extends SysUser {

	private String sysUserId;// 系统用户id

	private String menuId;// 菜单id

	private String menuName;// 菜单名称

	private String menuCatalogId;// 菜单目录id

	private String catalogName;// 菜单目录名称

	private String privilegeId;// 特权标识

	private String constraintId;// 约束标识

	private String peOrConstraintHtml;// 特权与约束html

	private SysUserDTO sysUser;// 系统用户

	private String name;// 员工姓名

	private String menuUrl;// 菜单url

	private String roleId;

	private String initPwdFlag;// 密码状态解析

	@Override
	public String getInitPwdFlag() {
		if (StringUtils.isBlank(initPwdFlag)) {
			CacheManager cacheManager = CacheManager.getInstance();
			initPwdFlag = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "SYS_USER.INIT_PWD_FLAG" + initPwdFlag);
		}
		return initPwdFlag;
	}

	@Override
	public void setInitPwdFlag(String initPwdFlag) {
		this.initPwdFlag = initPwdFlag;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	private SysRole sysRole;// 用户角色

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	@SuppressWarnings("rawtypes")
	private List roleList;// 存储角色集合

	@SuppressWarnings("rawtypes")
	public List getRoleList() {
		return roleList;
	}

	@SuppressWarnings("rawtypes")
	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	@Override
	public String getSysUserId() {
		return sysUserId;
	}

	@Override
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuCatalogId() {
		return menuCatalogId;
	}

	public void setMenuCatalogId(String menuCatalogId) {
		this.menuCatalogId = menuCatalogId;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getConstraintId() {
		return constraintId;
	}

	public void setConstraintId(String constraintId) {
		this.constraintId = constraintId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getPeOrConstraintHtml() {
		return peOrConstraintHtml;
	}

	public void setPeOrConstraintHtml(String peOrConstraintHtml) {
		this.peOrConstraintHtml = peOrConstraintHtml;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public SysUserDTO getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUserDTO sysUser) {
		this.sysUser = sysUser;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

}
