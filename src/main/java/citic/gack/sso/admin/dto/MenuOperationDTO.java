package citic.gack.sso.admin.dto;

import citic.gack.sso.entity.SysMenuOperation;

@SuppressWarnings("serial")
public class MenuOperationDTO extends SysMenuOperation {
	private String operationName;

	private String menuName;
	
	private String operationCode;

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

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

}
