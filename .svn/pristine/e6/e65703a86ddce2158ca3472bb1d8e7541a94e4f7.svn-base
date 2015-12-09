package citic.gack.sso.mapper;

import java.util.List;

import citic.gack.sso.admin.dto.MenuDTO;
import citic.gack.sso.admin.dto.MenuOperationDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.entity.SysMenuOperation;

public interface MenuOperationMapper extends BaseMapper<SysMenuOperation> {

	/**
	 * 根据菜单删查询菜单操作(RolePermissionDOM用此方法)
	 * 
	 * @return 菜单操作
	 * @throws SysException系统异常
	 * 
	 */
	public List<MenuOperationDTO> queryMenuOperations(SysMenuOperation entity);

	/**
	 * 根据菜单删查询菜单操作(MenuDOM用此方法)
	 * 
	 * @return 菜单操作
	 * @throws SysException系统异常
	 * 
	 */
	public List<MenuOperationDTO> queryMenuCatalogOperations(RolePermissionDTO entity);

	/**
	 * 根据菜单删除菜单操作(MenuDOM用此方法)
	 * 
	 * @return 菜单目录
	 * @throws SysException系统异常
	 * 
	 */
	public int deleteMenuOperationByMenuId(MenuDTO mvo);
}