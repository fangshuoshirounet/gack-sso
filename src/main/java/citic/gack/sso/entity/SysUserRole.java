package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
public class SysUserRole extends BaseModel {
    protected String sysUserRoleId;
    protected String roleId;
    protected String sysUserId;
    protected String remarks; 
    public void setSysUserRoleId(String sysUserRoleId) {
        this.sysUserRoleId = sysUserRoleId;
    }

    public String getSysUserRoleId() {
        return sysUserRoleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    } 
}