package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.admin.dto.OrgTypeDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;

public interface OrgTypeService {
	/**
	 * 插入数据
	 * 
	 * @param orgType
	 * @return MVO
	 * @throws SysException
	 * @throws AppException
	 */
	public OrgTypeDTO insert(OrgTypeDTO orgType) throws SysException;

	/**
	 * 删除数据
	 * 
	 * @param orgType
	 * @return int
	 * @throws SysException
	 * @throws AppException
	 */
	public void delete(OrgTypeDTO orgType) throws SysException;

	/**
	 * 修改数据
	 * 
	 * @param orgType
	 * @return int
	 * @throws SysException
	 * @throws AppException
	 */
	public int update(OrgTypeDTO orgType) throws SysException;

	/**
	 * 查询数据
	 * 
	 * @param orgType
	 * @return list
	 * @throws SysException
	 * @throws AppException
	 */
	public List<OrgTypeDTO> queryList(OrgTypeDTO orgType) throws SysException;

	/**
	 * 分页查询
	 * 
	 * @param orgType
	 * @param pageInfo
	 * @return PageInfo
	 * @throws SysException
	 * @throws AppException
	 */
	public PageInfo queryListByPage(OrgTypeDTO orgType, PageInfo pageInfo) throws SysException;

	public void deleteBatch(List<OrgTypeDTO> delList) throws SysException;

	public OrgTypeDTO queryBean(OrgTypeDTO org) throws SysException;
}
