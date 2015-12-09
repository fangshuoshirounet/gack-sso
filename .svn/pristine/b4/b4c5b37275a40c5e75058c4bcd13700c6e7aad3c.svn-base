package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;

public interface SysUserService {
	/**
	 * 插入
	 * 
	 * @param sysUser
	 *            系统用户
	 * @return 记录数
	 * @throws SysException
	 */
	public SysUserDTO insert(SysUserDTO sysUser) throws SysException;

	/**
	 * 修改
	 * 
	 * @param sysUser
	 *            系统用户
	 * @return 记录数
	 * @throws SysException
	 */
	public int update(SysUserDTO sysUser) throws SysException;

	/**
	 * 删除
	 * 
	 * @param sysUser
	 *            系统用户
	 * @return 记录数
	 * @throws SysException
	 */
	public void delete(SysUserDTO sysUser) throws SysException;

	/**
	 * 查询集合
	 * 
	 * @param sysUser
	 *            系统用户
	 * @return 记录数
	 * @throws SysException
	 */
	public List<SysUserDTO> queryList(SysUserDTO sysUser) throws SysException;

	/**
	 * 查询对象
	 * 
	 * @param sysUser
	 *            系统用户
	 * @return 系统用户
	 * @throws SysException
	 */
	public SysUserDTO queryBean(SysUserDTO sysUser) throws SysException;

	public SysUserDTO searchSysUserByName(String username) throws SysException;

	public boolean pwdreset(SysUserDTO sysUser) throws SysException;

	public void deleteBatch(List<SysUserDTO> delList) throws SysException;

	public PageInfo queryListByPage(SysUserDTO sysUser, PageInfo pageInfo) throws SysException;

	/**
	 * 根据员工查询系统用户(在员工授权时候用到此方法)
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 */
	public List<SysUserDTO> sarchSysUserByOrgId(String orgId) throws SysException;

	/**
	 * 根据组查询组下的人员信息
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 */
	public List<SysUserDTO> selectUsersByOrgIds(String[] orgIds) throws SysException;
}