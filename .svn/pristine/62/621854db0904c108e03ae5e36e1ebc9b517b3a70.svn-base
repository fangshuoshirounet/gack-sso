package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysArea;

/**
 * 系统区域(IAreaService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-2-20下午2:02:48
 */
public interface AreaService {
	/**
	 * 插入
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public SysArea insert(SysArea entity) throws SysException;

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public int update(SysArea entity) throws SysException;

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public void delete(SysArea entity) throws SysException;

	/**
	 * 查询 集合
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public List<SysArea> queryList(SysArea entity) throws SysException;

	/**
	 * 查询对象
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public SysArea queryBean(SysArea entity) throws SysException;

	/**
	 * 分页查询
	 * 
	 * @param Area
	 * @param pageInfo
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public PageInfo queryListByPage(SysArea entity, PageInfo pageInfo) throws SysException;

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             , AppException
	 */
	public void deleteBatch(List<SysArea> delList) throws SysException;

	public String treejson(boolean isChild, String id) throws SysException;
}
