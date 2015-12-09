package citic.gack.sso.mapper;

import java.util.List;
import java.util.Map;

import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysOperatePermission;

public interface OperatePermissionMapper extends BaseMapper<SysOperatePermission> {
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
	 * 查询角色拥有的菜单操作权限
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 操作权限集合
	 * @throws SysException
	 *             系统异常
	 */
	List<OperatePermissionDTO> queryRoleMenuPermission(String roleId);

	/**
	 * 查询角色拥有的菜单目录操作权限
	 * 
	 * @param roleId
	 *            角色编号
	 * @return 操作权限集合
	 * @throws SysException
	 *             系统异常
	 */
	List<OperatePermissionDTO> queryRoleMenuCatalogPermission(Map<String, Object> map);

	List<OperatePermissionDTO> queryRoleMenuCatalogPermissionDG(String roleId);

	/**
	 * 查询系统用户拥有的菜单操作权限
	 * 
	 * @param sysUserId
	 *            系统用户编号
	 * @return 操作权限集合
	 * @throws SysException
	 *             系统异常
	 */
	List<OperatePermissionDTO> querySysUserMenuPermission(String sysUserId);

	/**
	 * 查询系统用户拥有的菜单目录操作权限
	 * 
	 * @param sysUserId
	 *            系统用户编号
	 * @return 操作权限集合
	 * @throws SysException
	 *             系统异常
	 */
	List<OperatePermissionDTO> querySysUserMenuCatalogPermission(Map<String, Object> map);

	List<OperatePermissionDTO> querySysUserMenuCatalogPermissionDG(String sysUserId);

	/**
	 * 查询系统用户拥有的菜单操作限制
	 * 
	 * @param sysUserId
	 *            系统用户编号
	 * @return 操作限制集合
	 * @throws SysException
	 *             系统异常
	 */
	List<OperatePermissionDTO> querySysUserMenuConstraint(String sysUserId);

	/**
	 * 查询系统用户拥有的菜单目录操作限制
	 * 
	 * @param sysUserId
	 *            系统用户编号
	 * @return 操作限制集合
	 * @throws SysException
	 *             系统异常
	 */
	List<OperatePermissionDTO> querySysUserMenuCatalogConstraint(Map<String, Object> map);

	List<OperatePermissionDTO> querySysUserMenuCatalogConstraintDG(String sysUserId);

	/**
	 * 根据菜单和角色查询出角色操作许可(RolePermissionDOM在给角色菜单授权时候用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public List<SysOperatePermission> queryMenuOperatePermission(RolePermissionDTO mvo);

	/**
	 * 根据菜单目录和角色查询出角色操作许可(RolePermissionDOM在给角色菜单目录授权时候用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public List<SysOperatePermission> queryMenuCatalogOperatePermission(RolePermissionDTO mvo);

	/**
	 * 根据角色功能许可删除角色操作许可(RolePermissionDOM在给角色授权时候用到此方法)
	 * 
	 * @param
	 * @return 角色操作许可
	 * @throws SysException
	 *             系统异常
	 * 
	 */
	public int deleteMenuOperatePermission(RolePermissionDTO mvo);

}