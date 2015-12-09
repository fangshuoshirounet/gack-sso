package citic.gack.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysRole;
import citic.gack.sso.entity.SysUserRole;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	/**
	 * 查询系统用户拥有的角色
	 * 
	 * @param sysUserId
	 *            系统用户编号
	 * @return 角色列表
	 * @throws SysException
	 *             系统异常
	 */
	public List<SysRole> querySysUserRole(String sysUserId);

	/**
	 * 查询系统用户拥有的角色
	 * 
	 * @param page
	 *            分页
	 * @param ApplyUserDTO
	 *            参数
	 * @return 角色列表
	 * @throws SysException
	 *             系统异常
	 */

	public List<RoleDTO> queryUserRoleByPage(@Param("condition") SysUserRole condition, @Param("page") PageInfo page);

	public int deleteUserRoleByUserId(String sysUserId);
}