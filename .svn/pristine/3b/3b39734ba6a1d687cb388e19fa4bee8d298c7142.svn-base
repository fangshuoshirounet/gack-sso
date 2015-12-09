package citic.gack.sso.mapper;

import java.util.List;

import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.entity.SysUserOperPrivilege;

public interface SysUserOperPrivilegeMapper extends BaseMapper<SysUserOperPrivilege> {

	// 根据用户特权id删除用户操作特权(SysUserPermissionDOM在员工授权时候用到此方法)
	public int deleteMenuOrCatelogSysUserPrivilege(SysUserPermissionDTO mvo);

	// 根据用户特权id和菜单得到用户操作特权(SysUserPermissionDOM在员工授权时候用到此方法)
	public List<SysUserOperPrivilege> searchMenuOrCatelogSysUserPrivilege(SysUserPermissionDTO mvo);
}