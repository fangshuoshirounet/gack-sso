package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysOrganization;

/**
 * 组织机构(IOrganizationService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-6下午1:56:40
 */

public interface OrganizationService {
	/**
	 * 插入
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public SysOrganization insert(SysOrganization entity) throws SysException;

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public int update(SysOrganization entity) throws SysException;

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public void delete(SysOrganization entity) throws SysException;

	/**
	 * 查询 集合
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public List<SysOrganization> queryList(SysOrganization entity) throws SysException;

	/**
	 * 查询对象
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public SysOrganization queryBean(SysOrganization entity) throws SysException;

	/**
	 * 分页查询
	 * 
	 * @param Organization
	 * @param pageInfo
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public PageInfo queryListByPage(SysOrganization entity, PageInfo pageInfo) throws SysException;

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public void deleteBatch(List<SysOrganization> delList) throws SysException;

	public String treejson(boolean isChild, String orgId) throws SysException;

	public List<SysOrganization> searchOrgByAreaId(String areaId) throws SysException;

}
