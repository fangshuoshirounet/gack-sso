package citic.gack.sso.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.MenuDTO;
import citic.gack.sso.admin.dto.MenuOperationDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.admin.service.MenuService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenu;
import citic.gack.sso.entity.SysMenuOperation;
import citic.gack.sso.entity.SysPermission;
import citic.gack.sso.mapper.MenuMapper;
import citic.gack.sso.mapper.MenuOperationMapper;
import citic.gack.sso.mapper.OperatePermissionMapper;
import citic.gack.sso.mapper.PermissionMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private MenuOperationMapper menuOperationMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private OperatePermissionMapper operatePermissionMapper;

	@Override
	public SysMenu insert(SysMenu entity) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			entity.setCreateDate(nowDate);
			entity.setOperateDate(nowDate);
			entity.setSts(ConstantsUtils.STS_A);
			entity.setMenuId(UUIDGenerator.getUUID());
			menuMapper.save(entity);
		} catch (Exception e) {
			throw new SysException("MenuService:insert", e);

		}
		return entity;
	}

	@Override
	public int update(SysMenu entity) throws SysException {
		int flag = 0;
		try {
			entity.setOperateDate(DateTools.getSysDate19());
			flag = menuMapper.updateById(entity);
			return flag;
		} catch (Exception e) {
			throw new SysException("MenuService:update", e);

		}

	}

	@Override
	public void delete(SysMenu entity) throws SysException {
		try {
			entity.setSts(ConstantsUtils.STS_P);
			entity.setOperateDate(DateTools.getSysDate19());
			menuMapper.deleteById(entity);
		} catch (Exception e) {
			throw new SysException("MenuService:delete", e);

		}

	}

	@Override
	public List<SysMenu> queryList(SysMenu entity) throws SysException {
		List<SysMenu> list = null;
		try {
			list = menuMapper.queryList(entity);
		} catch (Exception e) {
			throw new SysException("MenuService:queryList", e);

		}
		return list;
	}

	@Override
	public SysMenu queryBean(SysMenu entity) throws SysException {
		try {
			entity = menuMapper.queryById(entity);
		} catch (Exception e) {
			throw new SysException("MenuService:queryBean", e);

		}
		return entity;
	}

	@Override
	public PageInfo queryListByPage(MenuDTO menu, PageInfo pageInfo) throws SysException {
		try {
			List<SysMenu> list = menuMapper.queryListByPage(menu, pageInfo);
			if (pageInfo != null) {
				pageInfo.setRows(list);
			}
		} catch (Exception e) {
			throw new SysException("MenuService:queryListByPage", e);

		}

		return pageInfo;
	}

	@Override
	public void deleteBatch(List<SysMenu> delList) throws SysException {
		try {
			if (delList != null && delList.size() > 0) {
				for (SysMenu menu : delList) {
					this.delete(menu);
				}
			}
		} catch (Exception e) {
			throw new SysException("MenuService:deleteBatch", e);

		}

	}

	@Override
	public MenuDTO insertMenuAndOperation(MenuDTO DTO) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();

			DTO.setMenuId(UUIDGenerator.getUUID());
			DTO.setCreateDate(nowDate);
			DTO.setSts(ConstantsUtils.STS_A);
			menuMapper.save(DTO);
			if (DTO.getOperationIds() != null && DTO.getOperationIds().size() > 0) {
				SysMenuOperation mo;
				for (String opId : DTO.getOperationIds()) {
					mo = new SysMenuOperation();
					mo.setCreateDate(nowDate);
					mo.setOperateDate(nowDate);
					mo.setSts(ConstantsUtils.STS_A);
					mo.setMenuId(DTO.getMenuId());
					mo.setCreator(DTO.getCreator());
					mo.setOperationId(opId);
					mo.setMenuOperationId(UUIDGenerator.getUUID());
					if ("1".equals(opId)) {
						mo.setDefaultSelected("A");
					} else {
						mo.setDefaultSelected("C");
					}
					menuOperationMapper.save(mo);
				}
			}
		} catch (Exception e) {
			throw new SysException("MenuService:insertMenuAndOperation", e);

		}

		return DTO;
	}

	@Override
	public MenuDTO updateMenuAndOperation(MenuDTO DTO) throws SysException {
		try {
			String nowDate = DateTools.getSysDate19();
			// 首先删除菜单操作
			{
				// 1.角色操作许可
				MenuDTO menu = new MenuDTO();
				menu.setSts(ConstantsUtils.STS_P);
				menu.setMenuId(DTO.getMenuId());
				List<SysPermission> pmList = permissionMapper.queryMenuPermissionByMenu(menu);
				if (pmList != null && pmList.size() > 0) {
					for (SysPermission pm : pmList) {
						RolePermissionDTO rDTO = new RolePermissionDTO();
						rDTO.setPermissionId(pm.getPermissionId());
						operatePermissionMapper.deleteMenuOperatePermission(rDTO);
					}
				}
				// 2.删除角色功能许可
				permissionMapper.deleteMenuPermissionByMenu(menu);
				// 3.删除菜单操作

				menu.setOperateDate(nowDate);
				menuOperationMapper.deleteMenuOperationByMenuId(menu);
			}
			DTO.setSts(ConstantsUtils.STS_A);
			DTO.setOperateDate(nowDate);
			menuMapper.updateById(DTO);
			if (DTO.getOperationIds() != null && DTO.getOperationIds().size() > 0) {
				SysMenuOperation mo;
				for (String opId : DTO.getOperationIds()) {
					mo = new SysMenuOperation();
					mo.setCreateDate(nowDate);
					mo.setOperateDate(nowDate);
					mo.setSts(ConstantsUtils.STS_A);
					mo.setMenuId(DTO.getMenuId());
					mo.setOperationId(opId);
					mo.setMenuOperationId(UUIDGenerator.getUUID());
					if ("1".equals(opId)) {
						mo.setDefaultSelected("A");
					} else {
						mo.setDefaultSelected("C");
					}
					menuOperationMapper.save(mo);
				}
			}
		} catch (Exception e) {
			throw new SysException("MenuService:updateMenuAndOperation", e);

		}

		return DTO;
	}

	@Override
	public void deleteMenuAndOperation(List<MenuDTO> DTOList) throws SysException {
		try {
			// 首先删除菜单操作
			String nowDate = DateTools.getSysDate19();
			for (MenuDTO DTO : DTOList) {
				// 1.角色操作许可
				SysMenu menu = new SysMenu();
				menu.setSts(ConstantsUtils.STS_P);
				menu.setMenuId(DTO.getMenuId());
				List<SysPermission> pmList = permissionMapper.queryMenuPermissionByMenu(menu);
				if (pmList != null && pmList.size() > 0) {
					for (SysPermission pm : pmList) {
						RolePermissionDTO rDTO = new RolePermissionDTO();
						rDTO.setPermissionId(pm.getPermissionId());
						rDTO.setSelected(nowDate);
						operatePermissionMapper.deleteMenuOperatePermission(rDTO);
					}
				}
				// 2.删除角色功能许可
				permissionMapper.deleteMenuPermissionByMenu(menu);
				// 3.删除菜单操作
				DTO.setSts(ConstantsUtils.STS_P);
				DTO.setOperateDate(nowDate);
				menuOperationMapper.deleteMenuOperationByMenuId(DTO);
				// 4.删除菜单
				menu.setSts(ConstantsUtils.STS_P);
				menu.setOperateDate(nowDate);
				menuMapper.deleteById(menu);
			}
		} catch (Exception e) {
			throw new SysException("MenuService:deleteMenuAndOperation", e);

		}

	}

	@Override
	public MenuDTO showMenuAndOperation(MenuDTO DTO) throws SysException {
		try {
			SysMenu menu = new SysMenu();
			menu.setMenuId(DTO.getMenuId());
			menu = menuMapper.queryById(menu);
			try {
				BeanUtils.copyProperties(DTO, menu);
			} catch (Exception e) {
				logger.error("", e);
			}
			RolePermissionDTO rDTO = new RolePermissionDTO();
			rDTO.setMenuId(menu.getMenuId());
			List<MenuOperationDTO> moList = menuOperationMapper.queryMenuCatalogOperations(rDTO);

			Map<String, String> operMap = new HashMap<String, String>();
			if (moList != null && moList.size() > 0) {
				for (SysMenuOperation mo : moList) {
					operMap.put(mo.getOperationId(), mo.getOperationId());
				}
			}
		} catch (Exception e) {
			throw new SysException("MenuService:showMenuAndOperation", e);

		}

		return DTO;
	}

}
