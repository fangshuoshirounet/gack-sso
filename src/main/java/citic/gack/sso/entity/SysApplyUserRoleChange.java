package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
public class SysApplyUserRoleChange extends BaseModel {
	// 用户角色标识
	protected String applyUserId;
	protected String roleId;
	protected String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getFlagType() {
		return flagType;
	}

	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	protected String flagType;

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleId() {
		return roleId;
	}

}