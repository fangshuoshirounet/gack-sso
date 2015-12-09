package citic.gack.sso.admin.service;

import java.util.Map;

import citic.gack.sso.admin.dto.ApplyRolePermissionDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;

/**
 * 角色授权(IRolePermissionService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-14下午2:04:04
 */

public interface ApplyRolePermissionService {
	// 菜单操作权限申请添加
	public void saveApplyMenuOperatePermission(Map<String, Object> map) throws SysException;

	// 查看角色申请权限内容
	public PageInfo queryApplyRolePermission(ApplyRolePermissionDTO mvo, PageInfo pageInfo) throws SysException;

	// 查看角色申请权限历史内容
	public PageInfo queryApplyRolePermissionChangeByPage(ApplyRolePermissionDTO mvo, PageInfo pageInfo) throws SysException;

}
