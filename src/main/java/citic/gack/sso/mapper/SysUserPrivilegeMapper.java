package citic.gack.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.entity.SysUserPrivilege;

public interface SysUserPrivilegeMapper extends BaseMapper<SysUserPrivilege> {

	// 根据菜单和用户id查询用户特权(SysUserPermissionDOM在员工授权时候用到此方法)
	public List<SysUserPrivilege> queryMenuSysUserPrivilege(SysUserPermissionDTO mvo);

	// 根据菜单目录和用户id查询用户特权(SysUserPermissionDOM在员工授权时候用到此方法)
	public List<SysUserPrivilege> queryMenuCatalogSysUserPrivilege(SysUserPermissionDTO mvo);

	// 根据用户id查询用户特权(SysUserPermissionDOM在员工授权时候用到此方法)
	public List<SysUserPrivilege> queryMenuOrCatalogSysUserPrivilege(@Param("type") String type, @Param("sysUserId") String sysUserId);
}