package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.admin.dto.ApplyUserDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyUserRole;

public interface ApplyUserRoleService {
	/**
	 * 插入
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 * @throws SysException
	 *             系统异常
	 * @throws AppException
	 *             应用异常
	 */
	public SysApplyUserRole insert(SysApplyUserRole entity) throws SysException;

	/**
	 * 修改
	 * 
	 * @param entity
	 *            实体对象
	 * @return 更新记录数
	 * @throws SysException
	 *             系统异常
	 * @throws AppException
	 *             应用异常
	 */
	public int update(SysApplyUserRole entity) throws SysException;

	/**
	 * 删除
	 * 
	 * @param entity
	 *            实体对象
	 * @return 删除记录数
	 * @throws SysException
	 *             系统异常
	 * @throws AppException
	 *             应用异常
	 */
	public void delete(SysApplyUserRole entity) throws SysException;

	/**
	 * 查询集合
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象列表
	 * @throws SysException
	 *             系统异常
	 * @throws AppException
	 *             应用异常
	 */
	public List<SysApplyUserRole> queryList(SysApplyUserRole entity) throws SysException;

	/**
	 * 查询对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 * @throws SysException
	 *             系统异常
	 * @throws AppException
	 *             应用异常
	 */
	public SysApplyUserRole queryBean(SysApplyUserRole entity) throws SysException;

	/**
	 * 分页查询
	 * 
	 * @param entity
	 *            实体对象
	 * @param pageInfo
	 *            分页对象
	 * @return 分页对象
	 * @throws SysException
	 *             系统异常
	 * @throws AppException
	 *             应用异常
	 */
	public PageInfo queryListByPage(SysApplyUserRole entity, PageInfo pageInfo) throws SysException;

	/**
	 * 删除
	 * 
	 * @param delList
	 *            实体对象集合
	 * @return 删除记录数
	 * @throws SysException
	 *             系统异常
	 * @throws AppException
	 *             应用异常
	 */
	public void deleteBatch(List<SysApplyUserRole> delList) throws SysException;

	/**
	 * <br>
	 * 描 述：实时查询角色信息 <br>
	 * 作 者：wangjincheng <br>
	 * 版 本：V1.0 <br>
	 * 时 间：2015年9月30日 上午10:51:57
	 * 
	 * @param sysUser
	 * @param pageInfo
	 * @return
	 * @throws SysException
	 */
	public PageInfo querySysUserRole(ApplyUserDTO sysUser, PageInfo pageInfo) throws SysException;

	/**
	 * <br>
	 * 描 述：查询历史申请角色信息 <br>
	 * 作 者：wangjincheng <br>
	 * 版 本：V1.0 <br>
	 * 时 间：2015年9月30日 上午11:05:50
	 * 
	 * @param sysUser
	 * @param pageInfo
	 * @return
	 * @throws SysException
	 */
	public PageInfo queryApplyUserRoleChangeByPage(ApplyUserDTO sysUser, PageInfo pageInfo) throws SysException;

}