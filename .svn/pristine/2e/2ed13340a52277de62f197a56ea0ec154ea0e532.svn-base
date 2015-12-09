package citic.gack.sso.entity;

import java.awt.Menu;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;

import citic.gack.sso.base.BaseModel;

/**
 * <br>
 * 类 名: MenuCatalog <br>
 * 描 述: 系统菜单目录实体类 <br>
 * 作 者: wangjincheng <br>
 * 创 建： 2015年7月31日 <br>
 * 版 本：v1.0.0 <br>
 */
@SuppressWarnings("serial")
@XmlRootElement
public class SysMenuCatalog extends BaseModel {
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	protected String menuCatalogId;
	protected String parentCatalogId;
	protected String catalogName;
	protected String orderNo;

	public String getMenuCatalogId() {
		return menuCatalogId;
	}

	public void setMenuCatalogId(String menuCatalogId) {
		this.menuCatalogId = menuCatalogId;
	}

	public String getParentCatalogId() {
		return parentCatalogId;
	}

	public void setParentCatalogId(String parentCatalogId) {
		this.parentCatalogId = parentCatalogId;
	}

	public String getCatalogName() {
		return this.catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj != null && obj instanceof Menu) {
			SysMenuCatalog another = (SysMenuCatalog) obj;
			equals = new EqualsBuilder().append(menuCatalogId, another.getMenuCatalogId()).isEquals();
		}
		return equals;
	}
}
