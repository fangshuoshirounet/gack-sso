package citic.gack.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenu;
import citic.gack.sso.entity.SysPermission;

public interface PermissionMapper extends BaseMapper<SysPermission> {

	/**
	 * 根据 角色删除角色功能许可
	 * 
	 * @param roleId
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public int deleteByRoleId(String roleId);

	/**
	 * 根据菜单和角色删除角色功能许可(RolePermissionDOM在给角色授权时候用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public void deleteMenuPermission(RolePermissionDTO entity);

	/**
	 * 根据菜单和角色查询角色功能许可(RolePermissionDOM在给角色菜单授权时候用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public List<SysPermission> queryMenuPermission(RolePermissionDTO entity);

	/**
	 * 根据菜单目录和角色删除角色功能许可(RolePermissionDOM在给角色授权时候用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public void deleteMenuCatalogPermission(RolePermissionDTO entity);

	/**
	 * 根据菜单目录和角色查询角色功能许可(RolePermissionDOM在给角色菜单授权时候用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public List<SysPermission> queryMenuCatalogPermission(RolePermissionDTO entity);

	/**
	 * 根据菜单查询角色功能许可(MenuDOM在删除菜单操作用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public List<SysPermission> queryMenuPermissionByMenu(SysMenu entity);

	/**
	 * 根据菜单删除角色功能许可(MenuDOM在删除菜单操作用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public void deleteMenuPermissionByMenu(SysMenu entity);

	public List<SysPermission> queryMenuOrCatalogPermission(@Param("type") String type, @Param("roleId") String roleId);

}