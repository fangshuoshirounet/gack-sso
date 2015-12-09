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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.ApplyUserDTO;
import citic.gack.sso.admin.dto.MenuOperationDTO;
import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.admin.security.SecurityManager;
import citic.gack.sso.admin.service.SecurityService;
import citic.gack.sso.admin.service.SysUserPermissionService;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.UUIDGenerator;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysApplyUserRole;
import citic.gack.sso.entity.SysMenu;
import citic.gack.sso.entity.SysMenuOperation;
import citic.gack.sso.entity.SysRole;
import citic.gack.sso.entity.SysUserConstraint;
import citic.gack.sso.entity.SysUserOperConstraint;
import citic.gack.sso.entity.SysUserOperPrivilege;
import citic.gack.sso.entity.SysUserPrivilege;
import citic.gack.sso.entity.SysUserRole;
import citic.gack.sso.mapper.ApplyUserMapper;
import citic.gack.sso.mapper.ApplyUserRoleMapper;
import citic.gack.sso.mapper.MenuOperationMapper;
import citic.gack.sso.mapper.OperatePermissionMapper;
import citic.gack.sso.mapper.SysUserConstraintMapper;
import citic.gack.sso.mapper.SysUserMapper;
import citic.gack.sso.mapper.SysUserOperConstraintMapper;
import citic.gack.sso.mapper.SysUserOperPrivilegeMapper;
import citic.gack.sso.mapper.SysUserPrivilegeMapper;
import citic.gack.sso.mapper.SysUserRoleMapper;
import citic.gack.sso.utils.ConstantsUtils;
import citic.gack.sso.utils.DateTools;

/**
 * 员工授权(SysUserPermissionService)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-18上午11:03:27
 */
@Service("SysUserPermissionService")
public class SysUserPermissionServiceImpl implements SysUserPermissionService {
	@Autowired
	private SysUserPrivilegeMapper sysUserPrivilegeMapper;
	@Autowired
	private SysUserOperPrivilegeMapper sysUserOperPrivilegeMapper;
	@Autowired
	private SysUserConstraintMapper sysUserConstraintMapper;
	@Autowired
	private SysUserOperConstraintMapper sysUserOperConstraintMapper;
	@Autowired
	private MenuOperationMapper menuOperationMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private ApplyUserRoleMapper applyUserRoleMapper;
	@Autowired
	private ApplyUserMapper applyUserMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private OperatePermissionMapper operatePermissionMapper;
	@Autowired
	private SecurityService securityService;

	private boolean removeMenuOrCatalogSysUserPrivilege(SysUserPermissionDTO DTO, String type) throws SysException {
		boolean result = false;
		try {
			List<SysUserPrivilege> suList = sysUserPrivilegeMapper.queryMenuOrCatalogSysUserPrivilege(type, DTO.getSysUserId());
			// 删除用户操作特权和用户特权
			result = deleteMenuOrCatelogSysUserPrivilege(DTO, suList);

		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:removeMenuOrCatalogSysUserPrivilege", e);

		}

		return result;
	}

	private boolean removeMenuOrCatalogSysUserConstraint(SysUserPermissionDTO DTO, String type) throws SysException {
		boolean result = false;
		try {
			List<SysUserConstraint> suList = sysUserConstraintMapper.queryMenuOrCatalogSysConstraint(type, DTO.getSysUserId());
			// 删除用户操作特权和用户特权
			result = deleteMenuOrCatelogSysUserConstraint(DTO, suList);

		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:removeMenuOrCatalogSysUserConstraint", e);

		}

		return result;
	}

	/**
	 * 菜单用户特权添加
	 */

	@Override
	public void saveMenuSysUserPrivilege(Map<String, Object> map) throws SysException {
		try {// 1.得到页面传过来的数据(SysUserPermissionDTO)
			SysUserPermissionDTO DTO = (SysUserPermissionDTO) map.get("model");
			// 2.删除用户操作特权
			if (removeMenuOrCatalogSysUserPrivilege(DTO, "menu")) {
				String[] menuIds = (String[]) map.get("menuIds");
				if (menuIds != null && menuIds.length > 0) {
					for (String menuId : menuIds) {
						DTO.setMenuId(menuId);
						String[] sysUserOperPrivilegeIds = (String[]) map.get(menuId);
						if (sysUserOperPrivilegeIds != null && sysUserOperPrivilegeIds.length > 0) {
							// 4.添加用户特权
							SysUserPrivilege sysUserPrivilege = this.saveSysUserPrivilege(DTO, "menu");
							// 5.添加用户操作特权
							saveSysUserOperPrivilege(sysUserOperPrivilegeIds, DTO, sysUserPrivilege, "menu");
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:saveMenuSysUserPrivilege", e);

		}
	}

	/**
	 * 菜单目录用户特权添加
	 */

	@Override
	public void saveMenucatalogSysUserPrivilege(Map<String, Object> map) throws SysException {
		try {// 1.得到页面传过来的数据(SysUserPermissionDTO)
			SysUserPermissionDTO DTO = (SysUserPermissionDTO) map.get("model");
			// 2.删除用户操作特权
			if (removeMenuOrCatalogSysUserPrivilege(DTO, "menucatalog")) {
				String[] menucatalogIds = (String[]) map.get("menucatalogIds");
				if (menucatalogIds != null && menucatalogIds.length > 0) {
					for (String menucatalogId : menucatalogIds) {
						DTO.setMenuCatalogId(menucatalogId);
						String[] sysUserOperPrivilegeIds = (String[]) map.get(menucatalogId);
						if (sysUserOperPrivilegeIds != null && sysUserOperPrivilegeIds.length > 0) {
							// 4.添加用户特权
							SysUserPrivilege sysUserPrivilege = this.saveSysUserPrivilege(DTO, "menucatalog");
							// 5.添加用户操作特权
							saveSysUserOperPrivilege(sysUserOperPrivilegeIds, DTO, sysUserPrivilege, "menucatalog");
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:saveMenucatalogSysUserPrivilege", e);

		}
	}

	/**
	 * 菜单用户约束添加
	 */

	@Override
	public void saveMenuSysUserConstraint(Map<String, Object> map) throws SysException {
		try {// 1.得到页面传过来的数据(SysUserPermissionDTO)
			SysUserPermissionDTO DTO = (SysUserPermissionDTO) map.get("model");
			// 2.删除用户操作约束
			if (removeMenuOrCatalogSysUserConstraint(DTO, "menu")) {
				String[] menuIds = (String[]) map.get("menuIds");
				if (menuIds != null && menuIds.length > 0) {
					for (String menuId : menuIds) {
						DTO.setMenuId(menuId);
						String[] sysUserConstraintIds = (String[]) map.get(menuId);
						if (sysUserConstraintIds != null && sysUserConstraintIds.length > 0) {
							// 4.添加用户约束
							SysUserConstraint sysUserConstraint = this.saveSysUserConstraint(DTO, "menu");
							// 5.添加用户操作约束
							this.saveSysUserOperConstraint(DTO, sysUserConstraint, sysUserConstraintIds, "menu");
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:saveMenuSysUserConstraint", e);

		}
	}

	/**
	 * 菜单目录用户约束添加
	 */

	@Override
	public void saveMenucatalogSysUserConstraint(Map<String, Object> map) throws SysException {
		try {// 1.得到页面传过来的数据(SysUserPermissionDTO)
			SysUserPermissionDTO DTO = (SysUserPermissionDTO) map.get("model");
			// 2.删除用户操作约束
			if (removeMenuOrCatalogSysUserConstraint(DTO, "menucatalog")) {
				String[] menucatalogIds = (String[]) map.get("menucatalogIds");
				if (menucatalogIds != null && menucatalogIds.length > 0) {
					for (String menucatalogId : menucatalogIds) {
						DTO.setMenuCatalogId(menucatalogId);
						String[] sysUserConstraintIds = (String[]) map.get(menucatalogId);
						if (sysUserConstraintIds != null && sysUserConstraintIds.length > 0) {
							// 4.添加用户约束
							SysUserConstraint sysUserConstraint = this.saveSysUserConstraint(DTO, "menucatalog");
							// 5.添加用户操作约束
							this.saveSysUserOperConstraint(DTO, sysUserConstraint, sysUserConstraintIds, "menucatalog");
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:saveMenucatalogSysUserConstraint", e);

		}
	}

	private boolean validateAdmin(SysUserPermissionDTO DTO, List<SysRole> roles) {
		boolean isAdmin = false;
		for (SysRole role : roles) {
			if (ADMIN_ROLE_CODE.equals(role.getRoleCode())) {
				isAdmin = true;
				break;
			}
		}
		try {

		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:validateAdmin", e);

		}
		return isAdmin;
	}

	@Override
	public List<OperatePermissionDTO> initMenuCatalogSysUserConstraint(SysUserPermissionDTO DTO) throws SysException {
		List<OperatePermissionDTO> recordList = new ArrayList<OperatePermissionDTO>();
		try {
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的权限菜单目录
			Map<String, OperatePermissionDTO> currentUserMenuCatalogMaps = sysUser.getCurrent_user_menucatalog_maps();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可

			List<SysUserConstraint> roleMenuPermissionList = sysUserConstraintMapper.queryMenuCatalogSysConstraint(DTO);

			Map<String, OperatePermissionDTO> recordMap = new HashMap<String, OperatePermissionDTO>();

			for (SysUserConstraint op : roleMenuPermissionList) {
				// 判断菜单目录是否在权限之内
				if (currentUserMenuCatalogMaps.get(op.getMenuCatalogId()) != null) {
					if (recordMap.get(op.getMenuCatalogId()) == null) {
						recordList.add(currentUserMenuCatalogMaps.get(op.getMenuCatalogId()));
						recordMap.put(op.getMenuCatalogId(), currentUserMenuCatalogMaps.get(op.getMenuCatalogId()));
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:initMenuCatalogSysUserConstraint", e);

		}

		return recordList;
	}

	@Override
	public List<OperatePermissionDTO> initMenuCatalogSysUserPrivilege(SysUserPermissionDTO DTO) throws SysException {
		List<OperatePermissionDTO> recordList = new ArrayList<OperatePermissionDTO>();
		try {
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的权限菜单目录
			Map<String, OperatePermissionDTO> currentUserMenuCatalogMaps = sysUser.getCurrent_user_menucatalog_maps();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可

			List<SysUserPrivilege> roleMenuPermissionList = sysUserPrivilegeMapper.queryMenuCatalogSysUserPrivilege(DTO);

			Map<String, OperatePermissionDTO> recordMap = new HashMap<String, OperatePermissionDTO>();

			for (SysUserPrivilege op : roleMenuPermissionList) {
				// 判断菜单目录是否在权限之内
				if (currentUserMenuCatalogMaps.get(op.getMenuCatalogId()) != null) {
					if (recordMap.get(op.getMenuCatalogId()) == null) {
						recordList.add(currentUserMenuCatalogMaps.get(op.getMenuCatalogId()));
						recordMap.put(op.getMenuCatalogId(), currentUserMenuCatalogMaps.get(op.getMenuCatalogId()));
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:initMenuCatalogSysUserPrivilege", e);

		}

		return recordList;
	}

	@Override
	public List<OperatePermissionDTO> initMenuSysUserConstraint(SysUserPermissionDTO DTO) throws SysException {
		List<OperatePermissionDTO> recordList = new ArrayList<OperatePermissionDTO>();
		try {
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的权限菜单
			Map<String, OperatePermissionDTO> currentUserMenuMaps = sysUser.getCurrent_user_menu_maps();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可
			Map<String, OperatePermissionDTO> recordMap = new HashMap<String, OperatePermissionDTO>();

			List<OperatePermissionDTO> sysUserMenuPermissionList = operatePermissionMapper.querySysUserMenuConstraint(DTO.getSysUserId());

			for (OperatePermissionDTO op : sysUserMenuPermissionList) {
				// 判断菜单是否在权限之内
				if (currentUserMenuMaps.get(op.getMenuId()) != null) {
					if (recordMap.get(op.getMenuId()) == null) {
						recordList.add(op);
						recordMap.put(op.getMenuId(), op);
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:initMenuSysUserConstraint", e);

		}

		return recordList;
	}

	@Override
	public List<OperatePermissionDTO> initMenuSysUserPrivilege(SysUserPermissionDTO DTO) throws SysException {
		List<OperatePermissionDTO> recordList = new ArrayList<OperatePermissionDTO>();
		try {
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的权限菜单
			Map<String, OperatePermissionDTO> currentUserMenuMaps = sysUser.getCurrent_user_menu_maps();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可
			Map<String, OperatePermissionDTO> recordMap = new HashMap<String, OperatePermissionDTO>();

			List<OperatePermissionDTO> sysUserMenuPermissionList = operatePermissionMapper.querySysUserMenuPermission(DTO.getSysUserId());

			for (OperatePermissionDTO op : sysUserMenuPermissionList) {
				// 判断菜单是否在权限之内
				if (currentUserMenuMaps.get(op.getMenuId()) != null) {
					if (recordMap.get(op.getMenuId()) == null) {
						recordList.add(op);
						recordMap.put(op.getMenuId(), op);
					}
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:initMenuSysUserPrivilege", e);

		}

		return recordList;

	}

	/**
	 * 查询菜单操作用户特权
	 */

	@Override
	public OperatePermissionDTO searchMenuSysUserPrivilege(SysUserPermissionDTO DTO) throws SysException {
		OperatePermissionDTO recordList = new OperatePermissionDTO();
		try {
			List<SysRole> roles = sysUserRoleMapper.querySysUserRole(DTO.getSysUserId());
			boolean isAdmin = validateAdmin(DTO, roles);
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的权限菜单
			// 当前登录用户的菜单操作权限
			Map<String, List<String>> currentUserMenuOperations = sysUser.getCurrent_user_menu_operations();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可

			recordList.setMenuId(DTO.getMenuId());
			// 开始循环菜单
			// 判断操作是否在权限之内
			List<String> currcodes = currentUserMenuOperations.get(recordList.getMenuId());
			if (isAdmin || currcodes != null) {
				StringBuilder html = new StringBuilder();
				html.append("<input type='hidden' value='").append(recordList.getMenuId());
				html.append("'  name='menuIds' />");

				SysMenuOperation mo = new SysMenuOperation();
				mo.setMenuId(recordList.getMenuId());
				DTO.setMenuId(recordList.getMenuId());
				// 2.得到角色功能许可 ->根据角色 菜单并放入map集合中
				List<SysUserPrivilege> suList = sysUserPrivilegeMapper.queryMenuSysUserPrivilege(DTO);

				Map<String, String> opMap = new HashMap<String, String>();
				if (suList != null && suList.size() > 0) {
					for (SysUserPrivilege su : suList) {
						// 3.根据用户特权得到用户操作特权，并放入map集合中
						DTO.setPrivilegeId(su.getPrivilegeId());
						List<SysUserOperPrivilege> opList = sysUserOperPrivilegeMapper.searchMenuOrCatelogSysUserPrivilege(DTO);
						for (SysUserOperPrivilege sop : opList) {
							opMap.put(sop.getMenuOperationId(), sop.getMenuOperationId());
						}
					}
				}
				// 4.根据菜单得到菜单操作
				List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
				// 5.循环遍历菜单操作
				if (moList != null && moList.size() > 0) {
					for (MenuOperationDTO mop : moList) {
						if (isAdmin || currcodes.contains(mop.getOperationCode())) {
							if ("A".equals(mop.getDefaultSelected())) {
								html.append("<input type='checkbox' onclick='this.checked=!this.checked' checked='checked' value='").append(mop.getMenuOperationId());
								html.append("' name='").append(recordList.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
							} else if (StringUtils.isNotBlank(opMap.get(mop.getMenuOperationId()))) {
								html.append("<input type='checkbox' checked='checked' value='").append(mop.getMenuOperationId());
								html.append("' name='").append(recordList.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
							} else {
								html.append("<input type='checkbox'  value='").append(mop.getMenuOperationId());
								html.append("' name='").append(recordList.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
							}
						}
					}
				}
				recordList.setOperations(html.toString());
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:searchMenuSysUserPrivilege", e);

		}

		return recordList;
	}

	/**
	 * 查询菜单目录用户特权
	 */

	@Override
	public OperatePermissionDTO searchMenucatalogSysUserPrivilege(SysUserPermissionDTO DTO) throws SysException {
		OperatePermissionDTO op = new OperatePermissionDTO();
		try {
			List<SysRole> roles = sysUserRoleMapper.querySysUserRole(DTO.getSysUserId());
			boolean isAdmin = validateAdmin(DTO, roles);
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
			html.append("'  name='menucatalogIds' />");
			// 2.得到角色功能许可
			// 3.根据角色功能许可得到角色操作许可，并放入map集合中
			List<SysUserPrivilege> suList = sysUserPrivilegeMapper.queryMenuCatalogSysUserPrivilege(DTO);
			Map<String, String> opMap = new HashMap<String, String>();
			if (suList != null && suList.size() > 0) {
				// 3.根据菜单目录用户特权得到用户操作特权，并放入map集合中
				for (SysUserPrivilege su : suList) {
					DTO.setPrivilegeId(su.getPrivilegeId());
					List<SysUserOperPrivilege> opList = sysUserOperPrivilegeMapper.searchMenuOrCatelogSysUserPrivilege(DTO);
					for (SysUserOperPrivilege sop : opList) {
						opMap.put(sop.getCatalogOperateType(), sop.getCatalogOperateType());
					}
				}
			}
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
			throw new SysException("SysUserPermissionService:searchMenucatalogSysUserPrivilege", e);

		}

		return op;
	}

	/**
	 * 查询菜单操作用户约束
	 */

	@Override
	public OperatePermissionDTO searchMenuSysUserConstraint(SysUserPermissionDTO DTO) throws SysException {
		OperatePermissionDTO recordList = new OperatePermissionDTO();
		try {
			List<SysRole> roles = sysUserRoleMapper.querySysUserRole(DTO.getSysUserId());
			boolean isAdmin = validateAdmin(DTO, roles);
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的权限菜单
			// 当前登录用户的菜单操作权限
			Map<String, List<String>> currentUserMenuOperations = sysUser.getCurrent_user_menu_operations();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可

			recordList.setMenuId(DTO.getMenuId());
			// 开始循环菜单
			// 判断操作是否在权限之内
			List<String> currcodes = currentUserMenuOperations.get(recordList.getMenuId());
			if (isAdmin || currcodes != null) {
				StringBuilder html = new StringBuilder();
				html.append("<input type='hidden' value='").append(recordList.getMenuId());
				html.append("'  name='menuIds' />");

				SysMenuOperation mo = new SysMenuOperation();
				mo.setMenuId(recordList.getMenuId());
				DTO.setMenuId(recordList.getMenuId());
				// 2.得到角色功能许可 ->根据角色 菜单并放入map集合中
				List<SysUserConstraint> suList = sysUserConstraintMapper.queryMenuSysUserConstraint(DTO);

				Map<String, String> opMap = new HashMap<String, String>();
				if (suList != null && suList.size() > 0) {
					for (SysUserConstraint su : suList) {
						// 3.根据用户约束得到用户操作约束，并放入map集合中
						DTO.setConstraintId(su.getConstraintId());
						List<SysUserOperConstraint> opList = sysUserOperConstraintMapper.searchMenuOrCatelogSysUserOperConstraint(DTO);
						for (SysUserOperConstraint op : opList) {
							opMap.put(op.getMenuOperationId(), op.getMenuOperationId());
						}
					}
				}
				// 4.根据菜单得到菜单操作
				List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
				// 5.循环遍历菜单操作
				if (moList != null && moList.size() > 0) {
					for (MenuOperationDTO mop : moList) {
						if (isAdmin || currcodes.contains(mop.getOperationCode())) {
							if (StringUtils.isNotBlank(opMap.get(mop.getMenuOperationId()))) {
								html.append("<input type='checkbox' checked='checked' value='").append(mop.getMenuOperationId());
								html.append("' name='").append(recordList.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
							} else {
								html.append("<input type='checkbox'  value='").append(mop.getMenuOperationId());
								html.append("' name='").append(recordList.getMenuId()).append("'>").append(mop.getOperationName()).append("</input>");
							}
						}
					}
				}
				recordList.setOperations(html.toString());
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:searchMenuSysUserConstraint", e);

		}

		return recordList;
	}

	/**
	 * 查询菜单目录用户约束
	 */

	@Override
	public OperatePermissionDTO searchMenucatalogSysUserConstraint(SysUserPermissionDTO DTO) throws SysException {
		OperatePermissionDTO op = new OperatePermissionDTO();
		try {
			List<SysRole> roles = sysUserRoleMapper.querySysUserRole(DTO.getSysUserId());
			boolean isAdmin = validateAdmin(DTO, roles);
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			// 当前登录用户的菜单目录操作权限
			Map<String, List<String>> currentUserMenuCatalogOperations = sysUser.getCurrent_user_menucatalog_operations();

			// 1得到用户角色//2.根据角色id得到角色功能许可// 3.根据角色id得到角色操作许可

			// /////------------------

			op.setMenuCatalogId(DTO.getMenuCatalogId());
			// 判断目录操作是否在权限之内
			List<String> currcodes = currentUserMenuCatalogOperations.get(op.getMenuCatalogId());
			if (currcodes == null) {
				currcodes = new ArrayList<String>();
			}
			StringBuilder html = new StringBuilder();
			// 4.根据菜单得到
			html.append("<input type='hidden' value='").append(op.getMenuCatalogId());
			html.append("'  name='menucatalogIds' />");
			// 2.得到角色功能许可
			// 3.根据角色功能许可得到角色操作许可，并放入map集合中
			List<SysUserConstraint> suList = sysUserConstraintMapper.queryMenuCatalogSysConstraint(DTO);
			Map<String, String> opMap = new HashMap<String, String>();
			if (suList != null && suList.size() > 0) {
				// 3.根据菜单目录用户特权得到用户操作特权，并放入map集合中
				for (SysUserConstraint su : suList) {
					DTO.setConstraintId(su.getConstraintId());
					List<SysUserOperConstraint> opList = sysUserOperConstraintMapper.searchMenuOrCatelogSysUserOperConstraint(DTO);
					for (SysUserOperConstraint sop : opList) {
						opMap.put(sop.getCatalogOperateType(), sop.getCatalogOperateType());
					}
				}
			}
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
			throw new SysException("SysUserPermissionService:searchMenucatalogSysUserConstraint", e);

		}

		return op;
	}

	/**
	 * 删除用户操作特权和用户特权
	 * 
	 * @return
	 */
	private boolean deleteMenuOrCatelogSysUserPrivilege(SysUserPermissionDTO DTO, List<SysUserPrivilege> suList) throws SysException {
		boolean istag = true;
		try {
			String nowDate = DateTools.getSysDate19();
			if (suList != null && suList.size() > 0) {
				// 1.删除用户操作特权
				for (SysUserPrivilege su : suList) {
					DTO.setPrivilegeId(su.getPrivilegeId());
					DTO.setOperateDate(nowDate);
					sysUserOperPrivilegeMapper.deleteMenuOrCatelogSysUserPrivilege(DTO);
					su.setSts(ConstantsUtils.STS_P);
					su.setOperateDate(nowDate);
					// 2.删除用户特权
					sysUserPrivilegeMapper.deleteById(su);
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:deleteMenuOrCatelogSysUserPrivilege", e);

		}

		return istag;
	}

	/**
	 * 添加用户特权
	 */
	// ****************
	private SysUserPrivilege saveSysUserPrivilege(SysUserPermissionDTO DTO, String type) {

		SysUserPrivilege sysUserPrivilege = new SysUserPrivilege();
		try {
			String nowDate = DateTools.getSysDate19();
			sysUserPrivilege.setSysUserId(DTO.getSysUserId());
			sysUserPrivilege.setSts(ConstantsUtils.STS_A);
			sysUserPrivilege.setOperateDate(nowDate);
			sysUserPrivilege.setCreator(DTO.getCreator());
			sysUserPrivilege.setOperator(DTO.getCreator());
			sysUserPrivilege.setVersion("1");
			sysUserPrivilege.setCreateDate(nowDate);
			sysUserPrivilege.setPrivilegeId(UUIDGenerator.getUUID());
			if ("menu".equals(type))
				sysUserPrivilege.setMenuId(DTO.getMenuId());
			else
				sysUserPrivilege.setMenuCatalogId(DTO.getMenuCatalogId());

			sysUserPrivilegeMapper.save(sysUserPrivilege);
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:saveSysUserPrivilege", e);

		}

		return sysUserPrivilege;
	}

	/**
	 * 添加用户操作特权
	 */
	private void saveSysUserOperPrivilege(String[] values, SysUserPermissionDTO DTO, SysUserPrivilege sysUserPrivilege, String type) {
		try {
			SysUserOperPrivilege sysUserOperPrivilege;
			String nowDate = DateTools.getSysDate19();
			for (String opId : values) {
				sysUserOperPrivilege = new SysUserOperPrivilege();

				sysUserOperPrivilege.setPrivilegeId(sysUserPrivilege.getPrivilegeId());
				sysUserOperPrivilege.setSts(ConstantsUtils.STS_A);
				sysUserOperPrivilege.setOperateDate(nowDate);
				sysUserOperPrivilege.setCreateDate(nowDate);
				sysUserOperPrivilege.setOperator(DTO.getCreator());
				sysUserOperPrivilege.setCreator(DTO.getCreator());
				sysUserOperPrivilege.setOperPrivilegeId(UUIDGenerator.getUUID());
				if ("menu".equals(type))
					sysUserOperPrivilege.setMenuOperationId(opId);
				else
					sysUserOperPrivilege.setCatalogOperateType(opId);

				sysUserOperPrivilegeMapper.save(sysUserOperPrivilege);

			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:saveSysUserOperPrivilege", e);

		}

	}

	/**
	 * 构造用户特权(约束)菜单目录显示html
	 * 
	 * @param opMap
	 * @param mo
	 * @return
	 */
	@SuppressWarnings("unused")
	private String constMenuCatalogSysUserPrivilegeHtml(Map<String, String> opMap, SysUserPermissionDTO catalog) {
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
			throw new SysException("SysUserPermissionService:constMenuCatalogSysUserPrivilegeHtml", e);

		}

		return html != null ? html.toString() : null;
	}

	/**
	 * 构造用户特权(约束)菜单显示html
	 * 
	 * @param opMap
	 * @param mo
	 * @return
	 */
	@SuppressWarnings("unused")
	private String constMenuSysUserPrivilegeHtml(Map<String, String> opMap, SysMenuOperation mo, SysUserPermissionDTO catalog, String type) throws SysException {
		StringBuffer html = null;
		try { // 4.根据菜单得到菜单操作
			List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
			// 5.循环遍历菜单操作
			if (moList != null && moList.size() > 0) {
				html = new StringBuffer();
				for (MenuOperationDTO mop : moList) {
					if ("A".equals(mop.getDefaultSelected()) && "privilege".equals(type)) {
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
			throw new SysException("SysUserPermissionService:constMenuSysUserPrivilegeHtml", e);

		}

		return html != null ? html.toString() : null;
	}

	/**
	 * 删除用户操作约束和用户约束
	 * 
	 * @return
	 */
	private boolean deleteMenuOrCatelogSysUserConstraint(SysUserPermissionDTO DTO, List<SysUserConstraint> suList) throws SysException {
		boolean istag = true;
		try {
			String nowDate = DateTools.getSysDate19();
			if (suList != null && suList.size() > 0) {
				// 1.删除用户操作特权
				for (SysUserConstraint su : suList) {
					DTO.setConstraintId(su.getConstraintId());
					DTO.setOperateDate(nowDate);
					sysUserOperConstraintMapper.deleteMenuOrCatelogSysUserOperConstraint(DTO);
					su.setSts(ConstantsUtils.STS_P);
					su.setOperateDate(nowDate);
					// 2.删除用户约束
					sysUserConstraintMapper.deleteById(su);
				}
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:deleteMenuOrCatelogSysUserConstraint", e);

		}

		return istag;
	}

	/**
	 * 添加用户约束
	 */
	// *******************
	private SysUserConstraint saveSysUserConstraint(SysUserPermissionDTO DTO, String type) {

		SysUserConstraint sysUserConstraint = new SysUserConstraint();
		try {
			String nowDate = DateTools.getSysDate19();
			sysUserConstraint.setSysUserId(DTO.getSysUserId());

			sysUserConstraint.setSts(ConstantsUtils.STS_A);
			sysUserConstraint.setOperateDate(nowDate);
			sysUserConstraint.setCreateDate(DateTools.getSysDate19());
			sysUserConstraint.setConstraintId(nowDate);
			if ("menu".equals(type))
				sysUserConstraint.setMenuId(DTO.getMenuId());
			else
				sysUserConstraint.setMenuCatalogId(DTO.getMenuCatalogId());

			sysUserConstraintMapper.save(sysUserConstraint);

		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:saveSysUserConstraint", e);

		}

		return sysUserConstraint;
	}

	/**
	 * 添加用户操作约束
	 */
	private void saveSysUserOperConstraint(SysUserPermissionDTO DTO, SysUserConstraint sysUserConstraint, String[] values, String type) {
		try {
			SysUserOperConstraint sysUserOperConstraint;
			String nowDate = DateTools.getSysDate19();
			for (String opId : values) {
				sysUserOperConstraint = new SysUserOperConstraint();
				sysUserOperConstraint.setOperConstraintId(UUIDGenerator.getUUID());
				sysUserOperConstraint.setConstraintId(sysUserConstraint.getConstraintId());
				sysUserOperConstraint.setSts(ConstantsUtils.STS_A);
				sysUserOperConstraint.setOperateDate(nowDate);
				sysUserOperConstraint.setCreateDate(DateTools.getSysDate19());
				if ("menu".equals(type))
					sysUserOperConstraint.setMenuOperationId(opId);
				else
					sysUserOperConstraint.setCatalogOperateType(opId);

				sysUserOperConstraintMapper.save(sysUserOperConstraint);
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:saveSysUserOperConstraint", e);

		}
	}

	@Override
	public PageInfo searchSysUserView(SysUserPermissionDTO mvo, PageInfo pageInfo) throws SysException {
		try {
			SysUserDTO sysUser = new SysUserDTO();
			sysUser.setSysUserId(mvo.getSysUserId());
			securityService.doGetAuthorizationInfo(sysUser);

			Set<String> set = sysUser.getSetPerm();
			boolean isAdmin = false;
			if (set.size() == 0) {
				List<SysRole> roles = sysUserRoleMapper.querySysUserRole(sysUser.getSysUserId());
				for (SysRole role : roles) {
					if (ADMIN_ROLE_CODE.equals(role.getRoleCode())) {
						isAdmin = true;
						break;
					}
				}
			}
			if (isAdmin)
				initAdminRolePermission(sysUser, pageInfo);
			else
				initGeneralRolePermission(sysUser, pageInfo);
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:searchSysUserView", e);

		}

		return pageInfo;
	}

	/**
	 * 加载admin的功能视图
	 * 
	 * @param sysUser
	 * @param PageInfo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initAdminRolePermission(SysUserDTO sysUser, PageInfo pageInfo) throws SysException {
		try {
			List<String> opName = new ArrayList<String>();
			List<SysUserPermissionDTO> recordList = new ArrayList<SysUserPermissionDTO>();
			List<SysMenu> menuList = sysUser.getMenu();
			SysUserPermissionDTO SysUserDTO;
			SysMenuOperation mo;
			for (SysMenu menu : menuList) {
				SysUserDTO = new SysUserPermissionDTO();
				mo = new SysMenuOperation();
				SysUserDTO.setMenuId(menu.getMenuId());
				mo.setMenuId(menu.getMenuId());
				SysUserDTO.setMenuName(menu.getMenuName());
				SysUserDTO.setMenuUrl(menu.getMenuUrl());
				List<MenuOperationDTO> moList = menuOperationMapper.queryMenuOperations(mo);
				for (MenuOperationDTO mop : moList) {
					opName.add(mop.getOperationName());
				}
				if (opName != null)
					SysUserDTO.setPeOrConstraintHtml(opName.toString());
				recordList.add(SysUserDTO);
			}
			Collections.sort(recordList, new Comparator() {
				@Override
				public int compare(Object a, Object b) {
					int one = Integer.valueOf((((SysUserPermissionDTO) a).getMenuId()));
					int two = Integer.valueOf(((SysUserPermissionDTO) b).getMenuId());
					return one - two;
				}
			});
			pageInfo.setRows(recordList);
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:initAdminRolePermission", e);

		}

	}

	/**
	 * 加载一般角色的功能视图
	 * 
	 * @param sysUser
	 * @param PageInfo
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initGeneralRolePermission(SysUserDTO sysUser, PageInfo pageInfo) {
		try {
			Set<String> set = sysUser.getSetPerm();
			Iterator<String> it = set.iterator();
			SysUserPermissionDTO SysUserDTO;
			List<SysUserPermissionDTO> recordList = new ArrayList<SysUserPermissionDTO>();
			Map<String, SysUserPermissionDTO> recordMap = new HashMap<String, SysUserPermissionDTO>();
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			while (it.hasNext()) {
				String messages = it.next();
				String[] message = messages.split("-");
				SysUserDTO = new SysUserPermissionDTO();
				SysUserDTO.setMenuId(message[0]);
				if (recordMap.get(SysUserDTO.getMenuId()) == null) {
					SysUserDTO.setMenuName(message[2]);
					SysUserDTO.setMenuUrl(message[3]);
					recordList.add(SysUserDTO);
					recordMap.put(SysUserDTO.getMenuId(), SysUserDTO);
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
			for (SysUserPermissionDTO SysUser : recordList) {
				List<String> opName = map.get(SysUser.getMenuId());
				Collections.sort(opName);
				if (opName != null)
					SysUser.setPeOrConstraintHtml(opName.toString());
			}
			pageInfo.setRows(recordList);
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:initGeneralRolePermission", e);

		}

	}

	@Override
	public boolean addSysUserRole(SysUserPermissionDTO DTO) throws SysException {
		int result = -1;
		try {
			String sysUserId = DTO.getSysUserId();
			// 删除系统员工的员工角色
			result = sysUserRoleMapper.deleteUserRoleByUserId(sysUserId);
			String nowDate = DateTools.getSysDate19();
			if (result >= 0) {
				// 添加员工角色
				String[] roleIds = DTO.getRoleId().split(",");
				SysUserRole userRole = null;
				for (String roleId : roleIds) {
					userRole = new SysUserRole();
					userRole.setSts(ConstantsUtils.STS_A);
					userRole.setCreateDate(nowDate);
					userRole.setOperator(nowDate);
					userRole.setSysUserId(sysUserId);
					userRole.setRoleId(roleId);
					userRole.setSysUserRoleId(UUIDGenerator.getUUID());
					sysUserRoleMapper.save(userRole);
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new SysException("SysUserPermissionService:addSysUserRole", e);

		}

		return true;
	}

	@Override
	public boolean addApplyUserRole(SysUserPermissionDTO DTO) throws SysException {
		boolean flag = false;
		try {
			String sysUserId = DTO.getSysUserId();
			String nowDate = DateTools.getSysDate19();
			SysUserDTO sysuser = new SysUserDTO();
			sysuser.setSysUserId(sysUserId);

			sysuser = sysUserMapper.queryById(sysuser);

			ApplyUserDTO applyuser = new ApplyUserDTO();
			BeanUtils.copyProperties(sysuser, applyuser);

			String applyUserId = UUIDGenerator.getUUID();
			applyuser.setCreateDate(nowDate);
			applyuser.setOperateDate(nowDate);
			applyuser.setApplyDate(nowDate);
			applyuser.setSysUserId(sysUserId);
			applyuser.setSts(ConstantsUtils.STS_A);
			applyuser.setId(applyUserId);
			applyuser.setPwdSetTime(nowDate);
			applyuser.setCreateDate(nowDate);
			applyuser.setCreator(DTO.getCreator());
			applyuser.setOperator(DTO.getCreator());
			applyuser.setApplyor(DTO.getCreator());
			applyuser.setCreatorId(DTO.getCreatorId());
			applyuser.setOperatorId(DTO.getCreatorId());
			applyuser.setApplyorId(DTO.getCreatorId());
			applyuser.setAuditStatus(ConstantsUtils.AUDIT_STATUS_I);
			applyuser.setApplyType(ConstantsUtils.APPLY_USER_TYPE_R);
			applyUserMapper.save(applyuser);

			// 添加员工角色
			String[] roleIds = DTO.getRoleId().split(",");
			SysApplyUserRole userRole = null;
			for (String roleId : roleIds) {
				userRole = new SysApplyUserRole();
				userRole.setSts(ConstantsUtils.STS_A);
				userRole.setCreator(DTO.getCreator());
				userRole.setOperator(DTO.getCreator());
				userRole.setCreatorId(DTO.getCreatorId());
				userRole.setOperatorId(DTO.getCreatorId());
				userRole.setCreateDate(nowDate);
				userRole.setOperateDate(nowDate);
				userRole.setSysUserId(sysUserId);
				userRole.setRoleId(roleId);
				userRole.setApplyUserId(applyUserId);
				userRole.setId(UUIDGenerator.getUUID());
				applyUserRoleMapper.save(userRole);
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			throw new SysException("SysUserPermissionService:applySysUserRole", e);

		}
		return flag;

	}
}
