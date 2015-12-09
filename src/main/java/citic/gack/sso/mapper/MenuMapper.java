package citic.gack.sso.mapper;

import java.util.List;

import citic.gack.sso.admin.dto.MenuDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenu;

public interface MenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 查询系统的正常状态的菜单信息，用于系统初始化
	 * 
	 * @return 系统正常状态的菜单信息列表
	 * @throws SysException
	 *             系统异常
	 */
	public List<MenuDTO> queryActiveMenu();

	/**
	 * 查询系统的正常状态的菜单信息(RolePermissionDOM角色授权时候要用此方法)
	 * 
	 * @return RolePermissionMVO
	 * @throws SysException系统异常
	 * 
	 */
	public List<RolePermissionDTO> queryMenu(RolePermissionDTO entity);

	/**
	 * 查询系统的正常状态的菜单信息(SysUserPermissionDOM员工授权时候要用此方法)
	 * 
	 * @return 菜单
	 * @throws SysException系统异常
	 * 
	 */
	public List<SysMenu> querySysUserMenu(SysUserPermissionDTO entity);
}