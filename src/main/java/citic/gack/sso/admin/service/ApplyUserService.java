package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.admin.dto.ApplyUserDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;

public interface ApplyUserService {
	/**
	 * 插入
	 * 
	 * @param ApplyUserDTO
	 *            系统用户
	 * @return 记录数
	 * @throws SysException
	 */
	public ApplyUserDTO insert(ApplyUserDTO sysUser) throws SysException;

	/**
	 * 修改
	 * 
	 * @param ApplyUserDTO
	 *            系统用户
	 * @return 记录数
	 * @throws SysException
	 */
	public int update(ApplyUserDTO sysUser) throws SysException;

	/**
	 * 删除
	 * 
	 * @param ApplyUserDTO
	 *            系统用户
	 * @return 记录数
	 * @throws SysException
	 */
	public void delete(ApplyUserDTO sysUser) throws SysException;

	/**
	 * 查询集合
	 * 
	 * @param ApplyUserDTO
	 *            系统用户
	 * @return 记录数
	 * @throws SysException
	 */
	public List<ApplyUserDTO> queryList(ApplyUserDTO sysUser) throws SysException;

	/**
	 * 查询对象
	 * 
	 * @param ApplyUserDTO
	 *            系统用户
	 * @return 系统用户
	 * @throws SysException
	 */
	public ApplyUserDTO queryBean(ApplyUserDTO sysUser) throws SysException;

	/**
	 * 批量审批用户申请
	 * 
	 * @param ApplyUserDTO
	 *            系统用户
	 * @return 系统用户
	 * @throws SysException
	 */
	public int updateBatch(List<ApplyUserDTO> delList) throws SysException;

	/**
	 * 批量删除用户申请
	 * 
	 * @param ApplyUserDTO
	 *            系统用户
	 * @return 系统用户
	 * @throws SysException
	 */
	public void deleteBatch(List<ApplyUserDTO> delList) throws SysException;

	/**
	 * 分页查询申请数据
	 * 
	 * @param ApplyUserDTO
	 *            系统用户
	 * @return 系统用户
	 * @throws SysException
	 */
	public PageInfo queryListByPage(ApplyUserDTO sysUser, PageInfo pageInfo) throws SysException;

}