package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
public class SysApplyOperatePermissionChange extends BaseModel {
	// 用户角色标识
	protected String applyRoleId;
	protected String menuId;
	protected String menuName;
	protected String permissionHtml;

	public String getApplyRoleId() {
		return applyRoleId;
	}

	public String getPermissionHtml() {
		return permissionHtml;
	}

	public void setPermissionHtml(String permissionHtml) {
		this.permissionHtml = permissionHtml;
	}

	public void setApplyRoleId(String applyRoleId) {
		this.applyRoleId = applyRoleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}