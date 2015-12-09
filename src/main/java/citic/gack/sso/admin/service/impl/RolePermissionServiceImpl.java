package citic.gack.sso.admin.service.impl;

import static citic.gack.sso.admin.security.SecurityManager.ADMIN_ROLE_CODE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.MenuOperationDTO;
import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.security.SecurityManager;
import citic.gack.sso.admin.service.RolePermissionService;
import citic.gack.sso.admin.service.SecurityService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenu;
import citic.gack.sso.entity.SysMenuOperation;
import citic.gack.sso.entity.SysOperatePermission;
import citic.gack.sso.entity.SysPermission;
import citic.gack.sso.entity.SysRole;
import citic.gack.sso.mapper.MenuMapper;
import citic.gack.sso.mapper.MenuOperationMapper;
import citic.gack.sso.mapper.OperatePermissionMapper;
import citic.gack.sso.mapper.PermissionMapper;
import citic.gack.sso.mapper.RoleMapper;
import citic.gack.sso.mapper.SysUserMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

/**
 * 角色授权(RolePermissionService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-14下午2:04:04
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {
	@Autowired
	private OperatePermissionMapper operatePermissionMapper;
	@Autowired
	private MenuOperationMapper menuOperationMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private MenuMapper menuMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SecurityService securityService;

	/**
	 * 查询菜单操作权限这个方法暂时不用
	 */

	@Override
	public List<RolePermissionDTO> queryMenuOperatePermission(RolePermissionDTO entity) throws SysException {
		List<RolePermissionDTO> DTOList = new ArrayList<RolePermissionDTO>();
		try {
			// 查询菜单操作
			SysMenuOperation mo = new SysMenuOperation();
			mo.setMenuId(entity.getMenuId());
			List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
			if (moList != null && moList.size() > 0) {
				// 根据角色获取 -> 操作许可
				SysOperatePermission op = new SysOperatePermission();
				op.setSts("A");
				op.setRoleId(entity.getRoleId());
				List<SysOperatePermission> opList = operatePermissionMapper.queryMenuOperatePermission(entity);

				Map<String, String> opMap = new HashMap<String, String>();
				for (SysOperatePermission oper : opList) {
					opMap.put(oper.getMenuOperationId(), oper.getMenuOperationId());
				}
				RolePermissionDTO DTO = null;
				for (MenuOperationDTO mop : moList) {
					DTO = new RolePermissionDTO();
					DTO.setMenuId(mop.getMenuId());
					if (StringUtils.isNotBlank(opMap.get(mop.getMenuOperationId()))) {
						DTO.setSelected("SELECTED");
					}
					DTO.setMenuName(mop.getMenuName());
					DTO.setOperationName(mop.getOperationName());
					DTO.setMenuOperationId(mop.getMenuOperationId());
					DTOList.add(DTO);
				}
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:queryMenuOperatePermission", e);

		}

		return DTOList;
	}

	@Override
	public String rolePermissionView(RolePermissionDTO DTO) throws SysException {
		String htmlString = null;
		try {
			StringBuilder html = new StringBuilder();
			// 1.得到菜单列表
			List<RolePermissionDTO> calogList = menuMapper.queryMenu(DTO);
			if (calogList != null && calogList.size() > 0) {
				for (RolePermissionDTO catalog : calogList) {
					DTO.setMenuId(catalog.getMenuId());
					SysMenuOperation mo = new SysMenuOperation();
					mo.setMenuId(DTO.getMenuId());
					// 2.得到角色功能许可 ->根据角色 菜单
					List<SysPermission> permissionList = permissionMapper.queryMenuPermission(DTO);
					Map<String, String> opMap = new HashMap<String, String>();
					if (permissionList != null && permissionList.size() > 0) {
						// 3.根据角色功能许可得到角色操作许可，并放入map集合中
						List<SysOperatePermission> opList = operatePermissionMapper.queryMenuOperatePermission(DTO);
						for (SysOperatePermission op : opList) {
							opMap.put(op.getMenuOperationId(), op.getMenuOperationId());
						}
						// 4.根据菜单得到菜单操作
						List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
						// 5.循环遍历菜单操作
						for (MenuOperationDTO mop : moList) {
							if ("A".equals(mop.getDefaultSelected())) {
								html.append(mop.getMenuName());
								html.append(":").append(mop.getOperationName()).append("，");
							}
						}
					}
				}

			}
			htmlString = html.toString();
			if (StringUtils.isBlank(htmlString)) {
				return "";
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:rolePermissionView", e);

		}

		return htmlString.substring(0, htmlString.length() - 1);
	}

	private boolean removeMenuOrCatalogPermission(RolePermissionDTO DTO, String type) throws SysException {
		boolean result = false;
		try {
			List<SysPermission> permissionList = permissionMapper.queryMenuOrCatalogPermission(type, DTO.getRoleId());
			result = deleteMenuOperatePermission(DTO, permissionList);
		} catch (Exception e) {
			throw new SysException("RolePermissionService:removeMenuOrCatalogPermission", e);

		}

		return result;
	}

	// 菜单授权

	@Override
	public void saveMenuOperatePermission(Map<String, Object> map) throws SysException {
		try {
			RolePermissionDTO DTO = (RolePermissionDTO) map.get("model");
			// 1.删除角色操作许可2.删除角色功能许可
			if (removeMenuOrCatalogPermission(DTO, "menu")) {
				String[] menuIds = (String[]) map.get("menuIds");
				if (menuIds != null && menuIds.length > 0) {
					for (String menuId : menuIds) {
						DTO.setMenuId(menuId);
						String[] menuOperations = (String[]) map.get(menuId);
						if (menuOperations != null && menuOperations.length > 0) {
							// 3.添加角色功能许可
							SysPermission pmsion = savePermission(DTO, "menu");
							// 4.添加角色操作许可
							this.saveOperatePermission(menuOperations, DTO, pmsion, "menu");

						}
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:saveMenuOperatePermission", e);

		}
	}

	// 目录授权

	@Override
	public void saveMenuCatalogOperatePermission(Map<String, Object> map) throws SysException {
		try {
			RolePermissionDTO DTO = (RolePermissionDTO) map.get("model");
			// 1.删除角色操作许可 2.删除角色功能许可
			if (removeMenuOrCatalogPermission(DTO, "menucatalog")) {
				String[] menuCatalogIds = (String[]) map.get("menuCatalogIds");
				if (menuCatalogIds != null && menuCatalogIds.length > 0) {
					for (String catalogId : menuCatalogIds) {
						DTO.setMenuCatalogId(catalogId);
						String[] menuoPerations = (String[]) map.get(catalogId);
						if (menuoPerations != null && menuoPerations.length > 0) {
							// 3.添加角色功能许可
							SysPermission pmsion = savePermission(DTO, "menucatalog");
							// 4.添加角色操作许可
							this.saveOperatePermission(menuoPerations, DTO, pmsion, "menucatalog");
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:saveMenuCatalogOperatePermission", e);

		}
	}

	private boolean validateAdmin(RolePermissionDTO DTO) throws SysException {
		boolean isAdmin = false;
		try {

			RoleDTO role = new RoleDTO();
			role.setSts("A");
			role.setRoleId(DTO.getRoleId());
			role = roleMapper.queryById(role);
			if (ADMIN_ROLE_CODE.equals(role.getRoleCode())) {
				isAdmin = true;
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:validateAdmin", e);

		}

		return isAdmin;
	}

	// 查询菜单

	@Override
	public OperatePermissionDTO searchmenu(RolePermissionDTO DTO) throws SysException {
		OperatePermissionDTO opDTO = new OperatePermissionDTO();
		try {
			boolean isAdmin = validateAdmin(DTO);
			SysUserDTO sysUser = SecurityManager.getSessionUser();

			// 当前登录用户的权限菜单
			Map<String, OperatePermissionDTO> currentUserMenuMaps = sysUser.getCurrent_user_menu_maps();

			// 当前登录用户的菜单操作权限
			Map<String, List<String>> currentUserMenuOperations = sysUser.getCurrent_user_menu_operations();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可
			if (currentUserMenuMaps.get(DTO.getMenuId()) != null || isAdmin) {
				// 判断操作是否在权限之内
				List<String> currcodes = currentUserMenuOperations.get(DTO.getMenuId());
				if (isAdmin || currcodes != null) {
					StringBuilder html = new StringBuilder();
					html.append("<input type='hidden' value='").append(DTO.getMenuId());
					html.append("'  name='menuIds' />");

					SysMenuOperation mo = new SysMenuOperation();
					mo.setMenuId(DTO.getMenuId());
					// 2.得到角色功能许可 ->根据角色 菜单并放入map集合中
					Map<String, String> opMap = constMenuOperatePermissionMap(DTO, "menu");

					// 4.根据菜单得到菜单操作
					List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
					// 5.循环遍历菜单操作
					if (moList != null && moList.size() > 0) {
						for (MenuOperationDTO mop : moList) {
							if (isAdmin || currcodes.contains(mop.getOperationCode())) {
								if ("A".equals(mop.getDefaultSelected())) {
									html.append("<input type='checkbox' onclick='this.checked=!this.checked' checked='checked' value='").append(mop.getMenuOperationId());
									html.append("' name='").append(mop.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
								} else if (StringUtils.isNotBlank(opMap.get(mop.getMenuOperationId()))) {
									html.append("<input type='checkbox' checked='checked' value='").append(mop.getMenuOperationId());
									html.append("' name='").append(mop.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
								} else {
									html.append("<input type='checkbox'  value='").append(mop.getMenuOperationId());
									html.append("' name='").append(mop.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
								}
							}
						}
					}
					opDTO.setOperations(html.toString());
				}
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:searchmenu", e);

		}
		return opDTO;
	}

	// 查询菜单目录

	@Override
	public OperatePermissionDTO searchmenucatalog(RolePermissionDTO DTO) throws SysException {
		OperatePermissionDTO op = new OperatePermissionDTO();
		try {
			boolean isAdmin = validateAdmin(DTO);
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的菜单目录操作权限
			Map<String, List<String>> currentUserMenuCatalogOperations = sysUser.getCurrent_user_menucatalog_operations();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可

			op.setMenuCatalogId(DTO.getMenuCatalogId());
			// 判断目录操作是否在权限之内
			List<String> currcodes = currentUserMenuCatalogOperations.get(op.getMenuCatalogId());
			if (currcodes == null) {
				currcodes = new ArrayList<String>();
			}
			StringBuilder html = new StringBuilder();
			// 4.根据菜单得到
			html.append("<input type='hidden' value='").append(op.getMenuCatalogId());
			html.append("'  name='menuCatalogIds' />");
			// 2.得到角色功能许可
			// 3.根据角色功能许可得到角色操作许可，并放入map集合中
			Map<String, String> opMap = constMenuOperatePermissionMap(DTO, "menuCatalog");
			if (isAdmin || currcodes.contains("A")) {
				// 4.构造菜单显示html
				if (StringUtils.isNotBlank(opMap.get("A"))) {
					html.append("<input type='radio' checked='checked' value='A' name='").append(op.getMenuCatalogId()).append("'>所有操作</input>");
					html.append("<input type='radio' value='B' name='").append(op.getMenuCatalogId()).append("'>访问</input>");
					html.append("<input type='radio' value='' name='").append(op.getMenuCatalogId()).append("'>无</input>");
				} else if (StringUtils.isNotBlank(opMap.get("B"))) {
					html.append("<input type='radio' value='A' name='").append(op.getMenuCatalogId()).append("'>所有操作</input>");
					html.append("<input type='radio' checked='checked' value='B' name='").append(op.getMenuCatalogId()).append("'>访问</input>");
					html.append("<input type='radio' value='' name='").append(op.getMenuCatalogId()).append("'>无</input>");
				} else {
					html.append("<input type='radio' value='A' name='").append(op.getMenuCatalogId()).append("'>所有操作</input>");
					html.append("<input type='radio' value='B' name='").append(op.getMenuCatalogId()).append("'>访问</input>");
					html.append("<input type='radio' value='' checked='checked' name='").append(op.getMenuCatalogId()).append("'>无</input>");
				}
			} else if (currcodes.contains("B") || currcodes.contains("access")) {
				if (StringUtils.isNotBlank(opMap.get("B"))) {
					html.append("<input type='radio' checked='checked' value='B' name='").append(op.getMenuCatalogId()).append("'>访问</input>");
					html.append("<input type='radio' value='' name='").append(op.getMenuCatalogId()).append("'>无</input>");
				} else {
					html.append("<input type='radio' value='B' name='").append(op.getMenuCatalogId()).append("'>访问</input>");
					html.append("<input type='radio' value='' checked='checked' name='").append(op.getMenuCatalogId()).append("'>无</input>");
				}
			}
			op.setOperations(html.toString());
		} catch (Exception e) {
			throw new SysException("RolePermissionService:searchmenucatalog", e);

		}

		return op;
	}

	/**
	 * 添加角色操作许可
	 */
	private int saveOperatePermission(String[] values, RolePermissionDTO DTO, SysPermission pmsion, String type) {

		int result = 0;
		try {
			SysOperatePermission opm;
			String nowDate = DateTools.getSysDate19();
			for (String mpId : values) {
				opm = new SysOperatePermission();
				opm.setOperatePermissionId(UUIDGenerator.getUUID());
				opm.setRoleId(DTO.getRoleId());
				opm.setCreateDate(nowDate);
				opm.setOperateDate(nowDate);
				opm.setSts(ConstantsUtils.STS_A);
				opm.setPermissionId(pmsion.getPermissionId());
				opm.setCreator(DTO.getCreator());
				if ("menu".equals(type)) {
					opm.setMenuOperationId(mpId);
				} else {
					opm.setCatalogOperateType(mpId);
				}
				operatePermissionMapper.save(opm);
				result++;
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:saveOperatePermission", e);

		}
		return result;
	}

	/**
	 * 删除角色操作许可
	 * 
	 * @return
	 */
	private boolean deleteMenuOperatePermission(RolePermissionDTO DTO, List<SysPermission> permissionList) throws SysException {
		boolean istag = true;
		try {
			// 1.删除角色操作许可
			String nowDate = DateTools.getSysDate19();

			if (permissionList != null && permissionList.size() > 0) {
				RolePermissionDTO deDTO;
				for (SysPermission pm : permissionList) {
					deDTO = new RolePermissionDTO();
					deDTO.setPermissionId(pm.getPermissionId());
					// 1 假删除
					operatePermissionMapper.deleteMenuOperatePermission(deDTO);
					// 2.删除角色功能许可permissionMapper.deleteMenuPermission(DTO);
					pm.setOperateDate(nowDate);
					pm.setSts(ConstantsUtils.STS_A);
					permissionMapper.updateById(pm);
				}
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:deleteMenuOperatePermission", e);

		}

		return istag;
	}

	/**
	 * 添加角色功能许可
	 */
	// ***************
	private SysPermission savePermission(RolePermissionDTO DTO, String type) {

		SysPermission pmsion = new SysPermission();
		try {
			String nowDate = DateTools.getSysDate19();
			pmsion.setCreateDate(nowDate);
			pmsion.setOperateDate(nowDate);
			pmsion.setSts(ConstantsUtils.STS_A);
			pmsion.setPermissionId(UUIDGenerator.getUUID());
			pmsion.setCreator(DTO.getCreator());
			if ("menu".equals(type)) {
				pmsion.setMenuId(DTO.getMenuId());
			} else {
				pmsion.setMenuCatalogId(DTO.getMenuCatalogId());
			}
			pmsion.setRoleId(DTO.getRoleId());

			permissionMapper.save(pmsion);
		} catch (Exception e) {
			throw new SysException("RolePermissionService:savePermission", e);

		}
		return pmsion;
	}

	/**
	 * 构造菜单即目录 OperatePermission Map
	 * 
	 * @param opMap
	 * @param mo
	 * @return
	 */
	private Map<String, String> constMenuOperatePermissionMap(RolePermissionDTO DTO, String type) throws SysException {
		Map<String, String> opMap = new HashMap<String, String>();
		try {

			List<SysPermission> permissionList = null;
			List<SysOperatePermission> opList = null;
			if ("menu".equals(type))
				permissionList = permissionMapper.queryMenuPermission(DTO);
			else
				permissionList = permissionMapper.queryMenuCatalogPermission(DTO);

			if (permissionList != null && permissionList.size() > 0) {
				// 3.根据角色功能许可得到角色操作许可，并放入map集合中
				if ("menu".equals(type)) {
					opList = operatePermissionMapper.queryMenuOperatePermission(DTO);
				} else {
					opList = operatePermissionMapper.queryMenuCatalogOperatePermission(DTO);
				}
				for (SysOperatePermission op : opList) {
					opMap.put(op.getMenuOperationId(), op.getMenuOperationId());
					opMap.put(op.getCatalogOperateType(), op.getCatalogOperateType());
				}
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:constMenuOperatePermissionMap", e);

		}

		return opMap;
	}

	/**
	 * 构造菜单显示html
	 * 
	 * @param opMap
	 * @param mo
	 * @return
	 */
	@SuppressWarnings("unused")
	private String constMenuPermissionHtml(Map<String, String> opMap, SysMenuOperation mo, RolePermissionDTO catalog) throws SysException {
		StringBuffer html = null;
		try {
			// 4.根据菜单得到菜单操作
			List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
			// 5.循环遍历菜单操作
			if (moList != null && moList.size() > 0) {
				html = new StringBuffer();
				for (MenuOperationDTO mop : moList) {
					if ("A".equals(mop.getDefaultSelected())) {
						html.append("<input type='checkbox' onclick='this.checked=!this.checked' checked='checked' value='").append(mop.getMenuOperationId());
						html.append("' name='").append(catalog.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
					} else if (StringUtils.isNotBlank(opMap.get(mop.getMenuOperationId()))) {
						html.append("<input type='checkbox' checked='checked' value='").append(mop.getMenuOperationId());
						html.append("' name='").append(catalog.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
					} else {
						html.append("<input type='checkbox'  value='").append(mop.getMenuOperationId());
						html.append("' name='").append(catalog.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:constMenuPermissionHtml", e);

		}

		return html != null ? html.toString() : null;
	}

	/**
	 * 构造菜单目录显示html
	 * 
	 * @param opMap
	 * @param mo
	 * @return
	 */
	@SuppressWarnings("unused")
	private String constMenuCatalogPermissionHtml(Map<String, String> opMap, RolePermissionDTO catalog) throws SysException {
		StringBuffer html = new StringBuffer();
		try {
			if (StringUtils.isNotBlank(opMap.get("A"))) {
				html.append("<input type='radio' checked='checked' value='A' name='").append(catalog.getMenuCatalogId()).append("'>所有操作</input>");
				html.append("<input type='radio' value='B' name='").append(catalog.getMenuCatalogId()).append("'>访问</input>");
			} else if (StringUtils.isNotBlank(opMap.get("B"))) {
				html.append("<input type='radio' value='A' name='").append(catalog.getMenuCatalogId()).append("'>所有操作</input>");
				html.append("<input type='radio' checked='checked' value='B' name='").append(catalog.getMenuCatalogId()).append("'>访问</input>");
			} else {
				html.append("<input type='radio' value='A' name='").append(catalog.getMenuCatalogId()).append("'>所有操作</input>");
				html.append("<input type='radio' value='B' name='").append(catalog.getMenuCatalogId()).append("'>访问</input>");
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:constMenuCatalogPermissionHtml", e);

		}

		return html != null ? html.toString() : null;
	}

	public PageInfo searchRoleView(SysUserDTO sysUser, PageInfo pageInfo, SysRole role) throws SysException {
		boolean isAdmin = false;
		try {
			if (ADMIN_ROLE_CODE.equals(role.getRoleCode())) {
				isAdmin = true;
			}
			if (isAdmin)
				initAdminRolePermission(sysUser, pageInfo);
			else
				initGeneralRolePermission(sysUser, pageInfo);
		} catch (Exception e) {
			throw new SysException("RolePermissionService:searchRoleView", e);

		}

		return pageInfo;
	}

	@Override
	public List<OperatePermissionDTO> initRoleMenuPermission(RolePermissionDTO DTO) throws SysException {
		List<OperatePermissionDTO> recordList = new ArrayList<OperatePermissionDTO>();
		try {
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的权限菜单
			Map<String, OperatePermissionDTO> currentUserMenuMaps = sysUser.getCurrent_user_menu_maps();

			// 当前登录用户的菜单操作权限
			// Map<String, List<String>> currentUserMenuOperations =
			// sysUser.getCurrent_user_menu_operations();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可

			List<OperatePermissionDTO> roleMenuPermissionList = operatePermissionMapper.queryRoleMenuPermission(DTO.getRoleId());

			// /////------------------
			Map<String, OperatePermissionDTO> recordMap = new HashMap<String, OperatePermissionDTO>();

			for (OperatePermissionDTO op : roleMenuPermissionList) {
				// 判断菜单是否在权限之内
				if (currentUserMenuMaps.get(op.getMenuId()) != null) {
					if (recordMap.get(op.getMenuId()) == null) {
						recordList.add(op);
						recordMap.put(op.getMenuId(), op);
					}
				}
			}

		} catch (Exception e) {
			throw new SysException("RolePermissionService:initRoleMenuPermission", e);

		}

		return recordList;
	}

	@Override
	public List<OperatePermissionDTO> initRoleMenuCatalogPermission(RolePermissionDTO DTO) throws SysException {
		List<OperatePermissionDTO> recordList = new ArrayList<OperatePermissionDTO>();
		try {
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的权限菜单目录
			Map<String, OperatePermissionDTO> currentUserMenuCatalogMaps = sysUser.getCurrent_user_menucatalog_maps();

			// 当前登录用户的菜单目录操作权限
			// Map<String, List<String>> currentUserMenuCatalogOperations =
			// sysUser.getCurrent_user_menucatalog_operations();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可
			List<SysPermission> roleMenuPermissionList = permissionMapper.queryMenuCatalogPermission(DTO);

			// /////------------------
			Map<String, OperatePermissionDTO> recordMap = new HashMap<String, OperatePermissionDTO>();

			for (SysPermission op : roleMenuPermissionList) {
				// 判断菜单目录是否在权限之内
				if (currentUserMenuCatalogMaps.get(op.getMenuCatalogId()) != null) {
					if (recordMap.get(op.getMenuCatalogId()) == null) {
						recordList.add(currentUserMenuCatalogMaps.get(op.getMenuCatalogId()));
						recordMap.put(op.getMenuCatalogId(), currentUserMenuCatalogMaps.get(op.getMenuCatalogId()));
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("RolePermissionService:initRoleMenuCatalogPermission", e);

		}

		return recordList;
	}

	/**
	 * 加载admin的功能视图
	 * 
	 * @param sysUser
	 * @param PageInfo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initAdminRolePermission(SysUserDTO sysUser, PageInfo pageInfo) throws SysException {
		List<RolePermissionDTO> recordList = new ArrayList<RolePermissionDTO>();
		try {
			List<String> opName = new ArrayList<String>();
			List<SysMenu> menuList = sysUser.getMenu();
			RolePermissionDTO sysUserDTO;
			SysMenuOperation mo;
			for (SysMenu menu : menuList) {
				sysUserDTO = new RolePermissionDTO();
				mo = new SysMenuOperation();
				sysUserDTO.setMenuId(menu.getMenuId());
				mo.setMenuId(menu.getMenuId());
				sysUserDTO.setMenuName(menu.getMenuName());
				sysUserDTO.setMenuUrl(menu.getMenuUrl());
				List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
				for (MenuOperationDTO mop : moList) {
					opName.add(mop.getOperationName());
				}
				if (opName != null)
					sysUserDTO.setPermissionHtml(opName.toString());
				recordList.add(sysUserDTO);
			}
			Collections.sort(recordList, new Comparator() {
				@Override
				public int compare(Object a, Object b) {

					String one = ((RolePermissionDTO) a).getMenuId();
					String two = ((RolePermissionDTO) b).getMenuId();
					return one.compareTo(two);
				}
			});
		} catch (Exception e) {
			throw new SysException("RolePermissionService:initAdminRolePermission", e);

		}

		pageInfo.setRows(recordList);
	}

	/**
	 * 加载一般角色的功能视图
	 * 
	 * @param sysUser
	 * @param PageInfo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGeneralRolePermission(SysUserDTO sysUser, PageInfo pageInfo) {
		List<RolePermissionDTO> recordList = new ArrayList<RolePermissionDTO>();
		try {
			Set<String> set = sysUser.getSetPerm();
			Iterator<String> it = set.iterator();
			RolePermissionDTO roleDTO;
			Map<String, RolePermissionDTO> recordMap = new HashMap<String, RolePermissionDTO>();
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			while (it.hasNext()) {
				String messages = it.next();
				String[] message = messages.split("-");
				roleDTO = new RolePermissionDTO();
				roleDTO.setMenuId(message[0]);
				if (recordMap.get(roleDTO.getMenuId()) == null) {
					roleDTO.setMenuName(message[2]);
					roleDTO.setMenuUrl(message[3]);
					recordList.add(roleDTO);
					recordMap.put(roleDTO.getMenuId(), roleDTO);
				}
				List<String> list = map.get(message[0]);
				if (list == null) {
					list = new ArrayList<String>();
					list.add(message[4]);
					map.put(message[0], list);
				} else {
					list.add(message[4]);
					map.put(message[0], list);
				}
			}

			for (RolePermissionDTO user : recordList) {
				List<String> opName = map.get(user.getMenuId());
				// Collections.sort(opName);
				if (opName != null)
					user.setPermissionHtml(opName.toString());
			}
			Collections.sort(recordList, new Comparator() {
				@Override
				public int compare(Object a, Object b) {
					String one = ((RolePermissionDTO) a).getMenuId();
					String two = ((RolePermissionDTO) b).getMenuId();
					return one.compareTo(two);
				}
			});
		} catch (Exception e) {
			throw new SysException("RolePermissionService:initGeneralRolePermission", e);

		}

		pageInfo.setRows(recordList);
	}

	@Override
	public PageInfo searchRoleView(RolePermissionDTO mvo, PageInfo pageInfo) throws SysException {
		try {
			SysUserDTO sysUserMVO = new SysUserDTO();

			sysUserMVO.setRoleId(mvo.getRoleId());
			RoleDTO role = new RoleDTO();
			role.setRoleId(mvo.getRoleId());
			role = roleMapper.queryById(role);
			securityService.doGetAuthorizationInfoByRole(sysUserMVO, role);
			pageInfo = this.searchRoleView(sysUserMVO, pageInfo, role);

		} catch (Exception e) {
			throw new SysException("RolePermissionService:searchRoleView", e);

		}

		return pageInfo;
	}

}
