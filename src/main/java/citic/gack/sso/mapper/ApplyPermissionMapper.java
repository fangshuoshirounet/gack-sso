package citic.gack.sso.mapper;

import java.util.List;

import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyPermission;
import citic.gack.sso.entity.SysPermission;

public interface ApplyPermissionMapper extends BaseMapper<SysApplyPermission> {

	/**
	 * 根绝申请id获取菜单申请权限
	 * 
	 * @param roleId
	 *            * @param applyRoleId 角色编号
	 * @return 操作权限集合
	 * @throws SysException
	 *             系统异常
	 */
	List<SysPermission> queryApplyRoleMenuPermission(String applyRoleId);

}