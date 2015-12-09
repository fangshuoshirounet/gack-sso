package citic.gack.sso.admin.security;

import java.util.Comparator;

import citic.gack.sso.entity.SysMenu;

public class MenuComparator implements Comparator<SysMenu> {

	public int compare(SysMenu o1, SysMenu o2) {
		return o1.getMenuId().compareTo(o2.getMenuId());
	}

}