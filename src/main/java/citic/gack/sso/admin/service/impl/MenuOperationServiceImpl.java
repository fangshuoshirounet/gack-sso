package citic.gack.sso.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.MenuOperationDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.admin.service.MenuOperationService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenuOperation;
import citic.gack.sso.mapper.MenuOperationMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("menuOperationService")
public class MenuOperationServiceImpl implements MenuOperationService {
	@Autowired
	private MenuOperationMapper menuOperationMapper;

	@Override
	public SysMenuOperation insert(SysMenuOperation entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setMenuOperationId(UUIDGenerator.getUUID());
			menuOperationMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("MenuOperationService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysMenuOperation entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = menuOperationMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("MenuOperationService:update", e);

		}

	}

	@Override
	public void delete(SysMenuOperation entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			menuOperationMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("MenuOperationService:delete", e);

		}

	}

	@Override
	public List<SysMenuOperation> queryList(SysMenuOperation entity) throws SysException {
		List<SysMenuOperation> list = null;
		try {
			list = menuOperationMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("MenuOperationService:queryList", e);

		}
		return list;
	}

	@Override
	public SysMenuOperation queryBean(SysMenuOperation entity) throws SysException {
		try {
			entity = menuOperationMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("MenuOperationService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(SysMenuOperation menuOperation, PageInfo pageInfo) throws SysException {
		try {
			List<SysMenuOperation> list = menuOperationMapper.queryListByPage(menuOperation, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("MenuOperationService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysMenuOperation> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysMenuOperation menuOperation : delList) {
					this.delete(menuOperation);
				}
			}
		} catch (Exception e) {
			throw new SysException("MenuOperationService:deleteBatch", e);

		}

	}

	@Override
	public List<MenuOperationDTO> queryMenuCatalogOperations(RolePermissionDTO entity) throws SysException {
		List<MenuOperationDTO> list = null;
		try {
			list = menuOperationMapper.queryMenuCatalogOperations(entity);
		} catch (Exception e) {
			throw new SysException("MenuOperationService:queryMenuCatalogOperations", e);

		}
		return list;
	}

}
