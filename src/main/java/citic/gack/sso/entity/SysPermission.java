package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
public class SysPermission extends BaseModel {

	// 功能许可编码
	protected String permissionId;

	// 角色编号
	protected String roleId;

	// 菜单目录编号目录权限优先菜单权限，MENU_CATALOG_ID与MENU_ID不能同时为空
	protected String menuCatalogId;

	// 菜单编号目录权限优先菜单权限，MENU_CATALOG_ID与MENU_ID不能同时为空
	protected String menuId;
 

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setMenuCatalogId(String menuCatalogId) {
		this.menuCatalogId = menuCatalogId;
	}

	public String getMenuCatalogId() {
		return menuCatalogId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuId() {
		return menuId;
	}
 
}