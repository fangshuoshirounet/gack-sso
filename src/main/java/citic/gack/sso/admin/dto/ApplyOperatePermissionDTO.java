package citic.gack.sso.admin.dto;

import citic.gack.sso.entity.SysApplyOperatePermission;

@SuppressWarnings("serial")
public class ApplyOperatePermissionDTO extends SysApplyOperatePermission {
	private String menuId;
	private String menuName;
	private String menuCode;
	private String menuUrl;
	private String menuCatalogId;
	private String operationId;
	private String operationCode;
	private String operationName;
	private String catalogOperateType;
	private String operations;

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

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuCatalogId() {
		return menuCatalogId;
	}

	public void setMenuCatalogId(String menuCatalogId) {
		this.menuCatalogId = menuCatalogId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public String getCatalogOperateType() {
		return catalogOperateType;
	}

	public void setCatalogOperateType(String catalogOperateType) {
		this.catalogOperateType = catalogOperateType;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

}
