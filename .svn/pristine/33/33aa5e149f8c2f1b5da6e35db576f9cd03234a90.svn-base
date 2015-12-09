package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.admin.dto.OperationDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;


public interface OperationService  {
	/**
	 * 插入
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public OperationDTO insert(OperationDTO entity) throws SysException;
	
	/**
	 * 修改
	 * @param entity
	 * @return
	 * @throws SysExceptionO
	 * @throws AppException
	 */
	public int update(OperationDTO entity) throws SysException;
	
	/**
	 * 删除
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public void delete(OperationDTO entity) throws SysException;
	
	/**
	 * 查询集合
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public List<OperationDTO> queryList(OperationDTO entity) throws SysException;
	
	/**
	 * 查询对象
	 * @param entity
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public OperationDTO queryBean(OperationDTO entity) throws SysException;

	/**
	 * 分页查询
	 * @param entity
	 * @param PageInfo
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public PageInfo queryListByPage(OperationDTO entity, PageInfo pageInfo) throws SysException;
	
    /**
     * 删除
     *
     * @param entity
     * @return
     * @throws SysException , AppException
     */
    public void batchDelete(List<OperationDTO> delList) throws SysException;
}
