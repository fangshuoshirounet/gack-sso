package citic.gack.sso.admin.dto;

import citic.gack.sso.base.BaseModel;

/**
 * 角色授权
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-18上午10:55:36
 */
@SuppressWarnings("serial")
public class RolePermissionDTO extends BaseModel {

	private String operationName;// 操作名称
	private String menuName;// 菜单名称
	private String menuOperationId;// 菜单操作编码
	private String selected;// 是否有该权限
	private String roleId;// 角色Id
	private String menuId;// 菜单Id
	private String roleName;// 角色名称
	private String roleCode;// 角色名称

	private String permissionId;// 功能操作编码
	private String permissionHtml;// 授权html
	private String menuCatalogId;// 菜单目录Id
	private String catalogName;// 菜单目录名称
	private String menuUrl;// 菜单url
	protected String stsDate;
	protected String applyRoleId;

	public String getApplyRoleId() {
		return applyRoleId;
	}

	public void setApplyRoleId(String applyRoleId) {
		this.applyRoleId = applyRoleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getStsDate() {
		return stsDate;
	}

	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuOperationId() {
		return menuOperationId;
	}

	public void setMenuOperationId(String menuOperationId) {
		this.menuOperationId = menuOperationId;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionHtml() {
		return permissionHtml;
	}

	public void setPermissionHtml(String permissionHtml) {
		this.permissionHtml = permissionHtml;
	}

	public String getMenuCatalogId() {
		return menuCatalogId;
	}

	public void setMenuCatalogId(String menuCatalogId) {
		this.menuCatalogId = menuCatalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

}
