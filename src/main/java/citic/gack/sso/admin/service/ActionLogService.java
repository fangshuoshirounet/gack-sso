package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysActionLog;

public interface ActionLogService  {
	/**
	 * 插入
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public SysActionLog insert(SysActionLog entity) throws SysException;
	
	/**
	 * 修改
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public int update(SysActionLog entity) throws SysException;
	
	/**
	 * 删除
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public void delete(SysActionLog entity) throws SysException;
	
	/**
	 * 查询集合
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public List<SysActionLog> queryList(SysActionLog entity) throws SysException;
	
	/**
	 * 查询对象
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public SysActionLog queryBean(SysActionLog entity) throws SysException;

	/**
	 * 分页查询
	 * @param entity
	 * @param PageInfo
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public PageInfo queryListByPage(SysActionLog entity, PageInfo pageInfo) throws SysException;
}
