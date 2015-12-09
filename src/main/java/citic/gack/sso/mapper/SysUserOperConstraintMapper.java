package citic.gack.sso.mapper;

import java.util.List;

import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.base.BaseMapper;
import citic.gack.sso.entity.SysUserOperConstraint;

public interface SysUserOperConstraintMapper extends BaseMapper<SysUserOperConstraint> {

	// 根据用户特权id删除用户操作约束(SysUserPermissionDOM在员工授权时候用到此方法)
	public int deleteMenuOrCatelogSysUserOperConstraint(SysUserPermissionDTO mvo);

	// 根据用户特权id得到用户操作约束(SysUserPermissionDOM在员工授权时候用到此方法)
	public List<SysUserOperConstraint> searchMenuOrCatelogSysUserOperConstraint(SysUserPermissionDTO mvo);
}