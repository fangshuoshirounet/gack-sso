package citic.gack.sso.admin.dto;

import citic.gack.sso.entity.SysShortcutMenu;

@SuppressWarnings("serial")
public class ShortcutMenuDTO extends SysShortcutMenu {
	
	private String menuName;
	private String menuUrl;
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	
}