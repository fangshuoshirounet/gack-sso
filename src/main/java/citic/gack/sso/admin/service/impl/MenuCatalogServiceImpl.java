package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.MenuCatalogDTO;
import citic.gack.sso.admin.service.MenuCatalogService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.mapper.MenuCatalogMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("menuCatalogService")
public class MenuCatalogServiceImpl implements MenuCatalogService {

	@Autowired
	private MenuCatalogMapper menuCatalogMapper;

	/**
	 * 插入
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */

	@Override
	public MenuCatalogDTO insert(MenuCatalogDTO entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setMenuCatalogId(UUIDGenerator.getUUID());
			if (StringUtils.isEmpty(entity.getParentCatalogId()))
				entity.setParentCatalogId(null);
			menuCatalogMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:insert", e);

		}
		return entity;
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */

	@Override
	public int update(MenuCatalogDTO entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			if (StringUtils.isEmpty(entity.getParentCatalogId()))
				entity.setParentCatalogId(null);
			flag = menuCatalogMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:update", e);

		}

	}

	@Override
	public String queryCount(MenuCatalogDTO entity) throws SysException {
		String count = null;
		try {
			count = menuCatalogMapper.queryCount(entity);
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:queryCount", e);

		}
		return count;
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */

	@Override
	public void delete(MenuCatalogDTO entity) throws SysException {
		try {
			menuCatalogMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:delete", e);

		}

	}

	/**
	 * 查询 集合
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */

	@Override
	public List<MenuCatalogDTO> queryList(MenuCatalogDTO entity) throws SysException {
		List<MenuCatalogDTO> list = null;
		try {
			list = menuCatalogMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:queryList", e);

		}
		return list;
	}

	/**
	 * 查询对象
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */

	@Override
	public MenuCatalogDTO queryBean(MenuCatalogDTO entity) throws SysException {
		try {
			entity = menuCatalogMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:queryBean", e);

		}
		return entity;
	}

	/**
	 * 分页查询
	 * 
	 * @param manu
	 * @param pageInfo
	 * @return
	 * @throws SysException
	 * @throws AppException
	 */

	@Override
	public PageInfo queryListByPage(MenuCatalogDTO manu, PageInfo pageInfo) throws SysException {
		try {
			List<MenuCatalogDTO> list = menuCatalogMapper.queryListByPage(manu, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:queryListByPage", e);

		}

		return pageInfo;
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return
	 * @throws SysException
	 * 
	 */
	@Override
	public void deleteBatch(List<MenuCatalogDTO> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (MenuCatalogDTO man : delList) {
					this.delete(man);
				}
			}
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:deleteBatch", e);

		}

	}

	/**
	 * 删除
	 * 
	 * @param delList
	 * @return
	 * @throws SysException
	 * 
	 */

	@Override
	public String batchDelete(List<MenuCatalogDTO> delList) throws SysException {
		String name = "";
		try {
			// int count = 0;
			String result;
			if (delList != null && delList.size() > 0) {
				for (MenuCatalogDTO manu : delList) {
					result = this.queryCount(manu);
					if (StringUtils.isNotBlank(result)) {
						name += result + " ";
					} else {
						this.delete(manu);
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("MenuCatalogService:batchDelete", e);

		}

		return name;
	}

}