package citic.gack.sso.admin.service;

import java.util.List;
import java.util.Map;

import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;

/**
 * 角色授权(IRolePermissionService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-14下午2:04:04
 */

public interface RolePermissionService {
	// 菜单操作权限查询
	public List<RolePermissionDTO> queryMenuOperatePermission(RolePermissionDTO entity) throws SysException;

	// 菜单操作权限添加
	public void saveMenuOperatePermission(Map<String, Object> map) throws SysException;

	// 菜单目录权限添加
	public void saveMenuCatalogOperatePermission(Map<String, Object> map) throws SysException;

	// 查询菜单目录
	public OperatePermissionDTO searchmenucatalog(RolePermissionDTO mvo) throws SysException;

	// 查询菜单
	public OperatePermissionDTO searchmenu(RolePermissionDTO mvo) throws SysException;

	// 角色权限视图
	public String rolePermissionView(RolePermissionDTO mvo) throws SysException;

	// 查看角色功能视图
	public PageInfo searchRoleView(RolePermissionDTO mvo, PageInfo pageInfo) throws SysException;

	public List<OperatePermissionDTO> initRoleMenuPermission(RolePermissionDTO mvo) throws SysException;

	public List<OperatePermissionDTO> initRoleMenuCatalogPermission(RolePermissionDTO mvo) throws SysException;
}
