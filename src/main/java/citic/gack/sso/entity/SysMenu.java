package citic.gack.sso.entity;

import java.awt.Menu;

import org.apache.commons.lang.builder.EqualsBuilder;

import citic.gack.sso.base.BaseModel;

/**
 * <br>
 * 类 名: Menu <br>
 * 描 述: 系统菜单实体类 <br>
 * 作 者: wangjincheng <br>
 * 创 建： 2015年7月31日 <br>
 * 版 本：v1.0.0 <br>
 */
@SuppressWarnings("serial")
public class SysMenu extends BaseModel {
	protected String menuId;
	protected String menuCatalogId;
	protected String menuName;
	protected String menuCode;
	protected String menuDesc;
	protected String menuUrl;
	protected String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuCatalogId(String menuCatalogId) {
		this.menuCatalogId = menuCatalogId;
	}

	public String getMenuCatalogId() {
		return menuCatalogId;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj != null && obj instanceof Menu) {
			SysMenu another = (SysMenu) obj;
			equals = new EqualsBuilder().append(menuId, another.getMenuId()).isEquals();
		}
		return equals;
	}
}