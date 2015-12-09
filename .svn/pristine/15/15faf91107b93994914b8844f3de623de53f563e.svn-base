package citic.gack.sso.admin.service;

import java.util.List;

import citic.gack.sso.admin.dto.MenuCatalogDTO;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;

public interface MenuCatalogService {

	public String queryCount(MenuCatalogDTO entity) throws SysException;

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */

	public int update(MenuCatalogDTO entity) throws SysException;

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public void delete(MenuCatalogDTO entity) throws SysException;

	/**
	 * 查询 集合
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public List<MenuCatalogDTO> queryList(MenuCatalogDTO entity) throws SysException;

	/**
	 * 查询对象
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public MenuCatalogDTO queryBean(MenuCatalogDTO entity) throws SysException;

	/**
	 * 删除
	 * 
	 * @param delList
	 * @return
	 * @throws SysException
	 *             , AppException
	 */
	public String batchDelete(List<MenuCatalogDTO> delList) throws SysException;

	/**
	 * 插入
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             ,AppException
	 */
	public MenuCatalogDTO insert(MenuCatalogDTO entity) throws SysException;

	/**
	 * 批量删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 *             , AppException
	 */
	public void deleteBatch(List<MenuCatalogDTO> delList) throws SysException;

	/**
	 * 分页查询
	 * 
	 * @param manu
	 * @param pageInfo
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */
	public PageInfo queryListByPage(MenuCatalogDTO manu, PageInfo pageInfo) throws SysException;
}
