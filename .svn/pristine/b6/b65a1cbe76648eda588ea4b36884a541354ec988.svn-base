package citic.gack.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.entity.SysUserConstraint;

public interface SysUserConstraintMapper extends BaseMapper<SysUserConstraint> {

	// 根据菜单和用户id查询用户约束(SysUserPermissionDOM在员工授权时候用到此方法)
	public List<SysUserConstraint> queryMenuSysUserConstraint(SysUserPermissionDTO mvo);

	// 根据菜单目录和用户id查询用户约束(SysUserPermissionDOM在员工授权时候用到此方法)
	public List<SysUserConstraint> queryMenuCatalogSysConstraint(SysUserPermissionDTO mvo);

	// 根据用户id查询用户约束(SysUserPermissionDOM在员工授权时候用到此方法)
	public List<SysUserConstraint> queryMenuOrCatalogSysConstraint(@Param("type") String type, @Param("sysUserId") String sysUserId);
}