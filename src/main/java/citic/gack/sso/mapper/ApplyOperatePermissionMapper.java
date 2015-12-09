package citic.gack.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyOperatePermission;
import citic.gack.sso.entity.SysOperatePermission;

public interface ApplyOperatePermissionMapper extends BaseMapper<SysApplyOperatePermission> {
	/**
	 * 查询角色拥有的菜单操作权限
	 * 
	 * @param roleId
	 *            * @param applyRoleId 角色编号
	 * @return 操作权限集合
	 * @throws SysException
	 *             系统异常
	 */
	List<OperatePermissionDTO> queryApplyRoleMenuPermission(@Param("applyRoleId") String applyRoleId, @Param("roleId") String roleId);

	/**
	 * 根绝申请id获取菜单操作申请权限
	 * 
	 * @param roleId
	 *            * @param applyRoleId 角色编号
	 * @return 操作权限集合
	 * @throws SysException
	 *             系统异常
	 */
	List<SysOperatePermission> queryApplyRoleOperatePermission(String applyRoleId);

}