package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
public class SysUserOperPrivilege extends BaseModel {
	
	//操作特权标识
    protected String operPrivilegeId;

	//特权标识
    protected String privilegeId;

	//菜单操作编码
    protected String menuOperationId;

	//A 添加D 删除Q 查询R 只读U 更新
    protected String catalogOperateType;
 

 	
	public void setOperPrivilegeId(String operPrivilegeId) {
        this.operPrivilegeId = operPrivilegeId;
    }
    public String getOperPrivilegeId() {
        return operPrivilegeId;
    }
	public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }
    public String getPrivilegeId() {
        return privilegeId;
    }
	public void setMenuOperationId(String menuOperationId) {
        this.menuOperationId = menuOperationId;
    }
    public String getMenuOperationId() {
        return menuOperationId;
    }
	public void setCatalogOperateType(String catalogOperateType) {
        this.catalogOperateType = catalogOperateType;
    }
    public String getCatalogOperateType() {
        return catalogOperateType;
    }
 
}