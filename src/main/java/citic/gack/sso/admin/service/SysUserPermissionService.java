package citic.gack.sso.admin.service;

import java.util.List;
import java.util.Map;

import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;

/**
 * 员工授权(ISysUserPermissionService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-18上午11:02:24
 */

public interface SysUserPermissionService {
	// 菜单操作用户特权添加
	public void saveMenuSysUserPrivilege(Map<String, Object> map) throws SysException;

	// 菜单目录用户特权添加
	public void saveMenucatalogSysUserPrivilege(Map<String, Object> map) throws SysException;

	// 菜单操作用户约束添加
	public void saveMenuSysUserConstraint(Map<String, Object> map) throws SysException;

	// 菜单目录约束用户添加
	public void saveMenucatalogSysUserConstraint(Map<String, Object> map) throws SysException;

	// 查询菜单操作用户特权
	public OperatePermissionDTO searchMenuSysUserPrivilege(SysUserPermissionDTO mvo) throws SysException;

	// 查询菜单目录用户特权
	public OperatePermissionDTO searchMenucatalogSysUserPrivilege(SysUserPermissionDTO mvo) throws SysException;

	// 查询菜单操作用户约束
	public OperatePermissionDTO searchMenuSysUserConstraint(SysUserPermissionDTO mvo) throws SysException;

	// 查询菜单目录用户约束
	public OperatePermissionDTO searchMenucatalogSysUserConstraint(SysUserPermissionDTO mvo) throws SysException;

	/**
	 * <br>
	 * 描 述： 查看员工功能视图<br>
	 * 作 者：wangjincheng <br>
	 * 历 史: (版本) 作者 时间 注释
	 * 
	 * @param mvo
	 * @param pageInfo
	 * @return
	 * @throws SysException
	 */
	public PageInfo searchSysUserView(SysUserPermissionDTO mvo, PageInfo pageInfo) throws SysException;

	public List<OperatePermissionDTO> initMenuSysUserPrivilege(SysUserPermissionDTO mvo) throws SysException;

	public List<OperatePermissionDTO> initMenuSysUserConstraint(SysUserPermissionDTO mvo) throws SysException;

	public List<OperatePermissionDTO> initMenuCatalogSysUserConstraint(SysUserPermissionDTO mvo) throws SysException;

	public List<OperatePermissionDTO> initMenuCatalogSysUserPrivilege(SysUserPermissionDTO mvo) throws SysException;

	/**
	 * <br>
	 * 描 述：用户添加角色 <br>
	 * 作 者：wangjincheng <br>
	 * 历 史: (版本) 作者 时间 注释
	 * 
	 * @param SysUserPermissionDTO
	 * @return boolean
	 * @throws SysException
	 */
	public boolean addSysUserRole(SysUserPermissionDTO mvo) throws SysException;

	/**
	 * <br>
	 * 描 述：用户角色申请 <br>
	 * 作 者：wangjincheng <br>
	 * 历 史: (版本) 作者 时间 注释
	 * 
	 * @param SysUserPermissionDTO
	 * @return boolean
	 * @throws SysException
	 */
	public boolean addApplyUserRole(SysUserPermissionDTO mvo) throws SysException;
}
