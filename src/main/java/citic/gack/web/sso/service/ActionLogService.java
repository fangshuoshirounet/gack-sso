package citic.gack.web.sso.service;

import java.util.List;

import citic.gack.web.sso.base.PageInfo;
import citic.gack.web.sso.base.exception.SysException;
import citic.gack.web.sso.entity.ActionLog;

public interface ActionLogService  {
	/**
	 * 插入
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public ActionLog insert(ActionLog entity) throws SysException;
	
	/**
	 * 修改
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public int update(ActionLog entity) throws SysException;
	
	/**
	 * 删除
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public void delete(ActionLog entity) throws SysException;
	
	/**
	 * 查询集合
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public List<ActionLog> queryList(ActionLog entity) throws SysException;
	
	/**
	 * 查询对象
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public ActionLog queryBean(ActionLog entity) throws SysException;

	/**
	 * 分页查询
	 * @param entity
	 * @param PageInfo
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public PageInfo queryListByPage(ActionLog entity, PageInfo pageInfo) throws SysException;
}
