package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.admin.dto.ApplyRoleDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;

public interface ApplyRoleService {
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
	public ApplyRoleDTO insert(ApplyRoleDTO entity) throws SysException;

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
	public int update(ApplyRoleDTO entity) throws SysException;

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
	public void delete(ApplyRoleDTO entity) throws SysException;

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
	public List<ApplyRoleDTO> queryList(ApplyRoleDTO entity) throws SysException;

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
	public ApplyRoleDTO queryBean(ApplyRoleDTO entity) throws SysException;

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
	public PageInfo queryListByPage(ApplyRoleDTO entity, PageInfo pageInfo) throws SysException;

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
	public void deleteBatch(List<ApplyRoleDTO> delList) throws SysException;

	/**
	 * 批量审批角色申请
	 * 
	 * @param ApplyRoleDTO
	 *            系统用户
	 * @return 系统用户
	 * @throws SysException
	 */
	public int updateBatch(List<ApplyRoleDTO> delList) throws SysException;

}