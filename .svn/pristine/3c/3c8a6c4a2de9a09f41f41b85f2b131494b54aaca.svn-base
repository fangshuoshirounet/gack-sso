package citic.gack.sso.admin.security;

import java.util.ArrayList;
import java.util.List;

import citic.gack.sso.entity.SysMenu;
import citic.gack.sso.entity.SysMenuCatalog;

public class MenuProcessor {
    /**
     * 菜单列表
     */
    private List<SysMenu> menuList = new ArrayList<SysMenu>();

    /**
     * 增加菜单
     *
     * @param menuId        菜单编号
     * @param menuName      菜单名称
     * @param menuUrl       菜单链接
     * @param menuCatalogId 菜单目录编号
     */
    public void addMenu(String menuId, String menuName, String menuUrl, String menuCatalogId) {
        SysMenu menu = new SysMenu();
        menu.setMenuId(menuId);
        menu.setMenuName(menuName);
        menu.setMenuUrl(menuUrl);
        menu.setMenuCatalogId(menuCatalogId);
        this.addMenu(menu);
    }

    /**
     * 增加菜单
     *
     * @param menu 菜单
     */
    public void addMenu(SysMenu menu) {
        if (!menuList.contains(menu)) {
            menuList.add(menu);
        }
    }

    /**
     * 去除菜单
     *
     * @param menuId 菜单编号
     */
    public void removeMenu(String menuId) {
        SysMenu menu = new SysMenu();
        menu.setMenuId(menuId);
        menuList.remove(menu);
    }

    /**
     * 增加菜单目录
     *
     * @param menuCatalogId   菜单目录编号
     * @param catalogName     菜单目录名称
     * @param parentCatalogId 上级目录编号
     */
    public void addMenuCatalog(String menuCatalogId, String catalogName, String parentCatalogId) {
        SysMenuCatalog menuCatalog = new SysMenuCatalog();
        menuCatalog.setMenuCatalogId(menuCatalogId);
        menuCatalog.setCatalogName(catalogName);
        menuCatalog.setParentCatalogId(parentCatalogId);
    }

    /**
     * 获取菜单列表
     *
     * @return 菜单列表
     */
    public List<SysMenu> getMenuList() {
        return menuList;
    }
}