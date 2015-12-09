package citic.gack.sso.admin.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysMenu;
import citic.gack.sso.utils.ConstantsUtils;

@SuppressWarnings("serial")
public class MenuDTO extends SysMenu {
	private String catalogName;
	private String menuOperationId;
	private String operationId;
	private String operationName;
	private String operationCode;
	private List<String> operationIds;
	private String stsName;
	private String htmlOperation;
	private String menuCatalogName;

	public String getStsName() {
		if (StringUtils.isBlank(stsName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			stsName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "MENU.STS." + this.getSts());
		}
		return stsName;
	}

	public void setStsName(String stsName) {
		this.stsName = stsName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getMenuOperationId() {
		return menuOperationId;
	}

	public void setMenuOperationId(String menuOperationId) {
		this.menuOperationId = menuOperationId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}

	public List<String> getOperationIds() {
		return operationIds;
	}

	public void setOperationIds(List<String> operationIds) {
		this.operationIds = operationIds;
	}

	public String getHtmlOperation() {
		return htmlOperation;
	}

	public void setHtmlOperation(String htmlOperation) {
		this.htmlOperation = htmlOperation;
	}

	public String getMenuCatalogName() {
		return menuCatalogName;
	}

	public void setMenuCatalogName(String menuCatalogName) {
		this.menuCatalogName = menuCatalogName;
	}

	// @Override
	// public String toJSONString() {
	// StringBuilder json = new StringBuilder();
	// json.append("{");
	// json.append("menuId:'").append(menuId).append("',");
	// json.append("menuName:'").append(menuName).append("',");
	// json.append("menuCode:'").append(menuCode).append("',");
	// json.append("stsName:'").append(this.getStsName()).append("',");
	// json.append("stsDate:'").append(stsDate).append("',");
	// json.append("}");
	// return json.toString();
	// }

}
