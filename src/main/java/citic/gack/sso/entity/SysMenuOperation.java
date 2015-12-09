package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

/** 
 * <br>类 名: MenuOperation 
 * <br>描 述: 菜单操作实体类 
 * <br>作 者:  wangjincheng
 * <br>创 建： 2015年7月31日 
 * <br>版 本：v1.0.0 
 * <br> 
 */
@SuppressWarnings("serial")
public class SysMenuOperation extends BaseModel {
	
	//菜单操作编码
    protected String menuOperationId;

	//功能操作编号
    protected String operationId;

	//菜单编号
    protected String menuId;

	//默认选择取消标识
    protected String defaultSelected;
 

 	
	public void setMenuOperationId(String menuOperationId) {
        this.menuOperationId = menuOperationId;
    }
    public String getMenuOperationId() {
        return menuOperationId;
    }
	public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
    public String getOperationId() {
        return operationId;
    }
	public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    public String getMenuId() {
        return menuId;
    }
	public void setDefaultSelected(String defaultSelected) {
        this.defaultSelected = defaultSelected;
    }
    public String getDefaultSelected() {
        return defaultSelected;
    }
 
}