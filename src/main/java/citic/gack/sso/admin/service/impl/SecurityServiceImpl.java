package citic.gack.sso.admin.service.impl;

import static citic.gack.sso.admin.security.SecurityManager.ACCESS_OPERATION_CODE;
import static citic.gack.sso.admin.security.SecurityManager.ACCESS_OPERATION_ID;
import static citic.gack.sso.admin.security.SecurityManager.ADMIN_ROLE_CODE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.admin.dto.MenuDTO;
import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.OperationDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.security.MenuProcessor;
import citic.gack.sso.admin.security.PermissionProcessor;
import citic.gack.sso.admin.service.SecurityService;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenu;
import citic.gack.sso.entity.SysMenuCatalog;
import citic.gack.sso.entity.SysOperation;
import citic.gack.sso.entity.SysRole;
import citic.gack.sso.mapper.MenuCatalogMapper;
import citic.gack.sso.mapper.MenuMapper;
import citic.gack.sso.mapper.OperatePermissionMapper;
import citic.gack.sso.mapper.OperationMapper;
import citic.gack.sso.mapper.SysUserMapper;
import citic.gack.sso.mapper.SysUserRoleMapper;
import citic.gack.sso.utils.ConstantsUtils;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {
	private static Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private MenuCatalogMapper menuCatalogMapper;

	@Autowired
	private OperationMapper operationMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	private OperatePermissionMapper operatePermissionMapper;

	private List<OperationDTO> operationsList;

	@Override
	public SysUserDTO doGetAuthenticationInfo(String username) throws SysException {
		SysUserDTO sysUserDTO = null;
		try {
			sysUserDTO = sysUserMapper.doGetAuthenticationInfo(username);
		} catch (Exception e) {
			throw new SysException("SecurityService:", e);

		}
		return sysUserDTO;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SimpleAuthorizationInfo doGetAuthorizationInfo(SysUserDTO sysUser) throws SysException {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		try {
			PermissionProcessor permissionProcessor = new PermissionProcessor();
			MenuProcessor menuProcessor = new MenuProcessor();
			if (operationsList == null) {
				logger.debug("开始获取菜单操作类型列表");
				OperationDTO operation = new OperationDTO();
				operation.setSts(ConstantsUtils.STS_A);
				operationsList = operationMapper.queryList(operation);
				if (operationsList == null || operationsList.size() == 0) {
					throw new SysException("40000", "操作类型表（operation）中无有效数据");
				}
				logger.debug("结束获取菜单操作类型列表，共获得{}条数据", operationsList.size());
			}

			logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色信息", sysUser.getLoginName());
			List<SysRole> roles = sysUserRoleMapper.querySysUserRole(sysUser.getSysUserId());
			logger.debug("获取鉴权信息：成功获取登录用户【{}】角色信息共{}条", sysUser.getLoginName(), roles == null ? 0 : roles.size());

			/** 管理员标志,默认否 */
			boolean isAdmin = false;

			/** 查询角色操作权限 */
			logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色权限信息", sysUser.getLoginName());
			for (SysRole role : roles) {
				info.addRole(role.getRoleCode());
				if (ADMIN_ROLE_CODE.equals(role.getRoleCode())) {
					/** 管理员角色直接添加所有权限 */
					logger.debug("获取鉴权信息：登录用户【{}】有管理员角色，默认设置拥有所有权限", sysUser.getLoginName());
					permissionProcessor.addPermission("*");
					isAdmin = true;
				} else {
					/** 设置普通角色权限 */

					/** 1.查询当前角色的菜单操作权限 */
					logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色菜单操作权限信息", sysUser.getLoginName());
					List<OperatePermissionDTO> roleMenuPermissionList = operatePermissionMapper.queryRoleMenuPermission(role.getRoleId());
					for (OperatePermissionDTO opVO : roleMenuPermissionList) {
						// 添加权限
						permissionProcessor.addPermission(opVO.getMenuCode() + ":" + opVO.getOperationCode());
						// 添加历史
						permissionProcessor.addPermissionLog(opVO.getMenuCode(), opVO.getOperationCode());
						// 添加视图权限
						sysUser.addSetPerm(opVO);

						// 添加菜单（只有“操作”为“access”的链接才加入到菜单中）
						if (ACCESS_OPERATION_ID.equals(opVO.getOperationId())) {
							menuProcessor.addMenu(opVO.getMenuId(), opVO.getMenuName(), opVO.getMenuUrl(), opVO.getMenuCatalogId());
						}
					}
					logger.debug("获取鉴权信息：成功获取登录用户【{}】的角色菜单操作权限信息", sysUser.getLoginName());

					/** 2.查询当前角色的菜单目录操作权限 */
					logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色的菜单目录操作权限信息", sysUser.getLoginName());
					List<OperatePermissionDTO> operatePermissiondDTOlist = operatePermissionMapper.queryRoleMenuCatalogPermissionDG(role.getRoleId());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("list", operatePermissiondDTOlist);
					map.put("roleId", role.getRoleId());
					roleMenuPermissionList = operatePermissionMapper.queryRoleMenuCatalogPermission(map);
					for (OperatePermissionDTO opVO : roleMenuPermissionList) {
						// 添加权限
						if ("*".equals(opVO.getOperationCode())) {
							for (SysOperation operation : operationsList) {
								permissionProcessor.addPermission(opVO.getMenuCode() + ":" + operation.getOperationCode());
								// 添加历史
								permissionProcessor.addPermissionLog(opVO.getMenuCode(), operation.getOperationCode());
								// 添加视图权限
								opVO.setOperationName(operation.getOperationName());
								opVO.setOperationCode(operation.getOperationCode());
								sysUser.addSetPerm(opVO);
							}
							List<SysMenuCatalog> catalogs = menuCatalogMapper.queryMenuCatalogByMenu(opVO.getMenuCatalogId().split(","));
							for (SysMenuCatalog catalog : catalogs) {
								opVO.setMenuCatalogId(catalog.getMenuCatalogId());
								opVO.setOperationCode("*");
								sysUser.addSetCataPerm(opVO);
							}
						} else {
							permissionProcessor.addPermission(opVO.getMenuCode() + ":" + opVO.getOperationCode());
							// 添加历史
							permissionProcessor.addPermissionLog(opVO.getMenuCode(), opVO.getOperationCode());
							// 添加视图权限
							opVO.setOperationName("访问操作");
							sysUser.addSetPerm(opVO);
							List<SysMenuCatalog> catalogs = menuCatalogMapper.queryMenuCatalogByMenu(opVO.getMenuCatalogId().split(","));
							for (SysMenuCatalog catalog : catalogs) {
								opVO.setMenuCatalogId(catalog.getMenuCatalogId());
								opVO.setOperationCode("B");
								sysUser.addSetCataPerm(opVO);
							}
						}
						// 添加菜单
						menuProcessor.addMenu(opVO.getMenuId(), opVO.getMenuName(), opVO.getMenuUrl(), opVO.getMenuCatalogId());
					}
					logger.debug("获取鉴权信息：成功获取登录用户【{}】的角色的菜单目录操作权限信息", sysUser.getLoginName());

					/** 3.查询继承的操作权限，包括角色继承限制 */
					logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色的继承操作权限", sysUser.getLoginName());
					// todo 下一阶段实现
					logger.debug("获取鉴权信息：成功获取登录用户【{}】的角色的继承操作权限", sysUser.getLoginName());
				}
			}
			logger.debug("获取鉴权信息：成功获取登录用户【{}】的角色权限信息", sysUser.getLoginName());

			logger.debug("获取鉴权信息：开始获取登录用户【{}】的个人权限信息", sysUser.getLoginName());
			if (!isAdmin) {
				/* 读取个人操作权限,管理员无需读取 */
				/** 1.查询当前作用对象类型菜单操作权限 */
				logger.debug("获取鉴权信息：开始获取登录用户【{}】的菜单操作权限", sysUser.getLoginName());
				List<OperatePermissionDTO> sysUserMenuPermissionList = operatePermissionMapper.querySysUserMenuPermission(sysUser.getSysUserId());
				for (OperatePermissionDTO opVO : sysUserMenuPermissionList) {
					// 添加权限
					permissionProcessor.addPermission(opVO.getMenuCode() + ":" + opVO.getOperationCode());
					// 添加历史
					permissionProcessor.addPermissionLog(opVO.getMenuCode(), opVO.getOperationCode());
					// 添加视图权限
					sysUser.addSetPerm(opVO);
					// 添加菜单（只有“操作”为“access”的链接才加入到菜单中）
					if (ACCESS_OPERATION_ID.equals(opVO.getOperationId())) {
						menuProcessor.addMenu(opVO.getMenuId(), opVO.getMenuName(), opVO.getMenuUrl(), opVO.getMenuCatalogId());
					}
				}
				logger.debug("获取鉴权信息：成功获取登录用户【{}】的菜单操作权限", sysUser.getLoginName());

				/** 2.查询当前作用对象类型目录操作权限 */
				logger.debug("获取鉴权信息：开始获取登录用户【{}】的菜单目录操作权限", sysUser.getLoginName());

				List<OperatePermissionDTO> list = operatePermissionMapper.querySysUserMenuCatalogPermissionDG(sysUser.getSysUserId());

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("sysUserId", sysUser.getSysUserId());
				sysUserMenuPermissionList = operatePermissionMapper.querySysUserMenuCatalogPermission(map);
				for (OperatePermissionDTO opVO : sysUserMenuPermissionList) {
					// 添加权限
					if ("*".equals(opVO.getOperationCode())) {
						for (SysOperation operation : operationsList) {
							permissionProcessor.addPermission(opVO.getMenuCode() + ":" + operation.getOperationCode());
							// 添加历史
							permissionProcessor.addPermissionLog(opVO.getMenuCode(), operation.getOperationCode());
							// 添加视图权限
							opVO.setOperationName(operation.getOperationName());
							opVO.setOperationCode(operation.getOperationCode());
							sysUser.addSetPerm(opVO);
						}
						List<SysMenuCatalog> catalogs = menuCatalogMapper.queryMenuCatalogByMenu(opVO.getMenuCatalogId().split(","));
						for (SysMenuCatalog catalog : catalogs) {
							opVO.setMenuCatalogId(catalog.getMenuCatalogId());
							opVO.setOperationCode("*");
							sysUser.addSetCataPerm(opVO);
						}
					} else {
						permissionProcessor.addPermission(opVO.getMenuCode() + ":" + opVO.getOperationCode());
						// 添加历史
						permissionProcessor.addPermissionLog(opVO.getMenuCode(), opVO.getOperationCode());
						// 添加视图权限
						opVO.setOperationName("访问操作");
						sysUser.addSetPerm(opVO);
						List<SysMenuCatalog> catalogs = menuCatalogMapper.queryMenuCatalogByMenu(opVO.getMenuCatalogId().split(","));
						for (SysMenuCatalog catalog : catalogs) {
							opVO.setMenuCatalogId(catalog.getMenuCatalogId());
							opVO.setOperationCode("B");
							sysUser.addSetCataPerm(opVO);
						}
					}
					// 添加菜单
					menuProcessor.addMenu(opVO.getMenuId(), opVO.getMenuName(), opVO.getMenuUrl(), opVO.getMenuCatalogId());
				}
				logger.debug("获取鉴权信息：成功获取登录用户【{}】的菜单目录操作权限", sysUser.getLoginName());

				/** 3.查询当前作用对象操作权限菜单限制 */
				logger.debug("获取鉴权信息：开始获取登录用户【{}】的操作权限限制", sysUser.getLoginName());
				List<OperatePermissionDTO> sysUserMenuConstraintList = operatePermissionMapper.querySysUserMenuConstraint(sysUser.getSysUserId());
				for (OperatePermissionDTO opVO : sysUserMenuConstraintList) {
					// 删除权限
					permissionProcessor.removePermission(opVO.getMenuCode() + ":" + opVO.getOperationCode());
					// 删除历史
					permissionProcessor.removePermissionLog(opVO.getMenuCode(), opVO.getOperationCode());
					// 删除视图权限
					sysUser.removeSetPerm(opVO);
					// 如果菜单不涵盖任何操作权限，删除菜单
					if (!permissionProcessor.getPerm().containsKey(opVO.getMenuCode()) || !permissionProcessor.getPerm().get(opVO.getMenuCode()).contains(ACCESS_OPERATION_CODE)) {
						menuProcessor.removeMenu(opVO.getMenuId());
					}
				}
				logger.debug("获取鉴权信息：成功获取登录用户【{}】的操作权限限制", sysUser.getLoginName());
				/** 4.查询当前作用对象操作权限目录限制 */
				logger.debug("获取鉴权信息：开始获取登录用户【{}】的操作权限限制", sysUser.getLoginName());
				list = operatePermissionMapper.querySysUserMenuCatalogPermissionDG(sysUser.getSysUserId());
				map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("sysUserId", sysUser.getSysUserId());
				sysUserMenuConstraintList = operatePermissionMapper.querySysUserMenuCatalogConstraint(map);
				for (OperatePermissionDTO opVO : sysUserMenuConstraintList) {
					if ("*".equals(opVO.getOperationCode())) {
						for (SysOperation operation : operationsList) {
							// 删除权限
							permissionProcessor.removePermission(opVO.getMenuCode() + ":" + operation.getOperationCode());
							// 添加历史
							permissionProcessor.removePermissionLog(opVO.getMenuCode(), operation.getOperationCode());
							// 删除视图权限
							opVO.setOperationName(operation.getOperationName());
							opVO.setOperationCode(operation.getOperationCode());
							sysUser.removeSetPerm(opVO);
						}
						List<SysMenuCatalog> catalogs = menuCatalogMapper.queryMenuCatalogByMenu(opVO.getMenuCatalogId().split(","));
						for (SysMenuCatalog catalog : catalogs) {
							opVO.setMenuCatalogId(catalog.getMenuCatalogId());
							opVO.setOperationCode("*");
							sysUser.addSetCataPerm(opVO);
						}
					} else {
						// 删除权限
						permissionProcessor.removePermission(opVO.getMenuCode() + ":" + opVO.getOperationCode());
						// 删除历史
						permissionProcessor.removePermissionLog(opVO.getMenuCode(), opVO.getOperationCode());
						// 删除视图权限
						opVO.setOperationName("访问操作");
						sysUser.removeSetPerm(opVO);
						List<SysMenuCatalog> catalogs = menuCatalogMapper.queryMenuCatalogByMenu(opVO.getMenuCatalogId().split(","));
						for (SysMenuCatalog catalog : catalogs) {
							opVO.setMenuCatalogId(catalog.getMenuCatalogId());
							opVO.setOperationCode("B");
							sysUser.addSetCataPerm(opVO);
						}
					}
					// 如果菜单不涵盖任何操作权限，删除菜单
					if (!permissionProcessor.getPerm().containsKey(opVO.getMenuCode()) || !permissionProcessor.getPerm().get(opVO.getMenuCode()).contains(ACCESS_OPERATION_CODE)) {
						menuProcessor.removeMenu(opVO.getMenuId());
					}
				}
				logger.debug("获取鉴权信息：成功获取登录用户【{}】的操作权限限制", sysUser.getLoginName());
			}
			logger.debug("获取鉴权信息：成功获取登录用户【{}】的个人权限信息", sysUser.getLoginName());

			// 将处理好的权限增加到Shiro的SimpleAuthorizationInfo对象中
			info.addObjectPermissions(((Collection) permissionProcessor.getPermissions()));

			/** 设置用户可访问的菜单 **/
			if (isAdmin) {
				OperatePermissionDTO opVo;
				List<SysMenuCatalog> menuCatalogList = menuCatalogMapper.queryActioeMenuCatalog();
				for (SysMenuCatalog menuCatalog : menuCatalogList) {
					opVo = new OperatePermissionDTO();
					opVo.setMenuCatalogId(menuCatalog.getMenuCatalogId());
					opVo.setOperationCode("*");
					sysUser.addSetCataPerm(opVo);
				}
				SysMenu qryMenu = new SysMenu();
				qryMenu.setSts("A");
				List<SysMenu> menuList = menuMapper.queryList(qryMenu);
				for (SysMenu menu : menuList) {
					opVo = new OperatePermissionDTO();
					opVo.setMenuId(menu.getMenuId());
					opVo.setMenuName(menu.getMenuName());
					opVo.setMenuUrl(menu.getMenuUrl());
					for (SysOperation operation : operationsList) {
						opVo.setMenuOperationId(operation.getOperationName());
						opVo.setOperationCode(operation.getOperationCode());
						sysUser.addSetPerm(opVo);
					}
				}
				sysUser.setMenuCatalog(menuCatalogList);
				sysUser.setMenu(menuList);

			} else {
				// 通过用户可访问的菜单，查询相应的菜单目录
				List menuCatalogList;
				if (menuProcessor.getMenuList() != null && menuProcessor.getMenuList().size() > 0) {
					StringBuilder menuCatalogIds = new StringBuilder();
					for (SysMenu menu : menuProcessor.getMenuList()) {
						menuCatalogIds.append(menu.getMenuCatalogId()).append(",");
					}
					menuCatalogIds.deleteCharAt(menuCatalogIds.length() - 1);

					menuCatalogList = menuCatalogMapper.queryMenuCatalogByMenu(menuCatalogIds.toString().split(","));
				} else {
					menuCatalogList = new ArrayList<SysMenuCatalog>();
				}

				// 设置用户可访问的菜单和菜单目录
				sysUser.setMenuCatalog(menuCatalogList);
				// Collections.sort(menuProcessor.getMenuList(),new
				// MenuComparator());
				sysUser.setMenu(menuProcessor.getMenuList());
			}
		} catch (Exception e) {
			throw new SysException("SecurityService:doGetAuthorizationInfo", e);

		}

		return info;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SimpleAuthorizationInfo doGetAuthorizationInfoByRole(SysUserDTO sysUser, SysRole role) throws SysException {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		try {
			PermissionProcessor permissionProcessor = new PermissionProcessor();
			MenuProcessor menuProcessor = new MenuProcessor();
			if (operationsList == null) {
				logger.debug("开始获取菜单操作类型列表");
				OperationDTO operation = new OperationDTO();
				operation.setSts(ConstantsUtils.STS_A);
				operationsList = operationMapper.queryList(operation);
				if (operationsList == null || operationsList.size() == 0) {
					throw new SysException("40000", "操作类型表（operation）中无有效数据");
				}
				logger.debug("结束获取菜单操作类型列表，共获得{}条数据", operationsList.size());
			}
			/** 管理员标志,默认否 */
			boolean isAdmin = false;

			/** 查询角色操作权限 */
			logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色权限信息", sysUser.getLoginName());
			info.addRole(role.getRoleCode());
			if (ADMIN_ROLE_CODE.equals(role.getRoleCode())) {
				/** 管理员角色直接添加所有权限 */
				logger.debug("获取鉴权信息：登录用户【{}】有管理员角色，默认设置拥有所有权限", sysUser.getLoginName());
				permissionProcessor.addPermission("*");
				isAdmin = true;
			} else {
				/** 设置普通角色权限 */

				/** 1.查询当前角色的菜单操作权限 */
				logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色菜单操作权限信息", sysUser.getLoginName());
				List<OperatePermissionDTO> roleMenuPermissionList = operatePermissionMapper.queryRoleMenuPermission(role.getRoleId());
				for (OperatePermissionDTO opVO : roleMenuPermissionList) {
					// 添加权限
					permissionProcessor.addPermission(opVO.getMenuCode() + ":" + opVO.getOperationCode());
					// 添加历史
					permissionProcessor.addPermissionLog(opVO.getMenuCode(), opVO.getOperationCode());
					// 添加视图权限
					sysUser.addSetPerm(opVO);

					// 添加菜单（只有“操作”为“access”的链接才加入到菜单中）
					if (ACCESS_OPERATION_ID.equals(opVO.getOperationId())) {
						menuProcessor.addMenu(opVO.getMenuId(), opVO.getMenuName(), opVO.getMenuUrl(), opVO.getMenuCatalogId());
					}
				}
				logger.debug("获取鉴权信息：成功获取登录用户【{}】的角色菜单操作权限信息", sysUser.getLoginName());

				/** 2.查询当前角色的菜单目录操作权限 */
				logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色的菜单目录操作权限信息", sysUser.getLoginName());
				List<OperatePermissionDTO> operatePermissiondDTOlist = operatePermissionMapper.queryRoleMenuCatalogPermissionDG(role.getRoleId());

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", operatePermissiondDTOlist);
				map.put("roleId", role.getRoleId());
				roleMenuPermissionList = operatePermissionMapper.queryRoleMenuCatalogPermission(map);
				for (OperatePermissionDTO opVO : roleMenuPermissionList) {
					// 添加权限
					if ("*".equals(opVO.getOperationCode())) {
						for (SysOperation operation : operationsList) {
							permissionProcessor.addPermission(opVO.getMenuCode() + ":" + operation.getOperationCode());
							// 添加历史
							permissionProcessor.addPermissionLog(opVO.getMenuCode(), operation.getOperationCode());
							// 添加视图权限
							opVO.setOperationName(operation.getOperationName());
							opVO.setOperationCode(operation.getOperationCode());
							sysUser.addSetPerm(opVO);
						}
					} else {
						permissionProcessor.addPermission(opVO.getMenuCode() + ":" + opVO.getOperationCode());
						// 添加历史
						permissionProcessor.addPermissionLog(opVO.getMenuCode(), opVO.getOperationCode());
						// 添加视图权限
						opVO.setOperationName("访问操作");
						sysUser.addSetPerm(opVO);
					}
					// 添加菜单
					menuProcessor.addMenu(opVO.getMenuId(), opVO.getMenuName(), opVO.getMenuUrl(), opVO.getMenuCatalogId());
				}
				logger.debug("获取鉴权信息：成功获取登录用户【{}】的角色的菜单目录操作权限信息", sysUser.getLoginName());

				/** 3.查询继承的操作权限，包括角色继承限制 */
				logger.debug("获取鉴权信息：开始获取登录用户【{}】的角色的继承操作权限", sysUser.getLoginName());
				// todo 下一阶段实现
				logger.debug("获取鉴权信息：成功获取登录用户【{}】的角色的继承操作权限", sysUser.getLoginName());
			}

			// 将处理好的权限增加到Shiro的SimpleAuthorizationInfo对象中
			info.addObjectPermissions(((Collection) permissionProcessor.getPermissions()));

			/** 设置用户可访问的菜单 **/
			if (isAdmin) {
				List menuCatalogList = menuCatalogMapper.queryActioeMenuCatalog();

				SysMenu qryMenu = new SysMenu();
				qryMenu.setSts("A");
				List menuList = menuMapper.queryList(qryMenu);

				sysUser.setMenuCatalog(menuCatalogList);
				sysUser.setMenu(menuList);
			} else {
				// 通过用户可访问的菜单，查询相应的菜单目录
				List menuCatalogList;
				if (menuProcessor.getMenuList() != null && menuProcessor.getMenuList().size() > 0) {
					StringBuilder menuCatalogIds = new StringBuilder();
					for (SysMenu menu : menuProcessor.getMenuList()) {
						menuCatalogIds.append(menu.getMenuCatalogId()).append(",");
					}
					menuCatalogIds.deleteCharAt(menuCatalogIds.length() - 1);

					menuCatalogList = menuCatalogMapper.queryMenuCatalogByMenu(menuCatalogIds.toString().split(","));
				} else {
					menuCatalogList = new ArrayList<SysMenuCatalog>();
				}

				// 设置用户可访问的菜单和菜单目录
				sysUser.setMenuCatalog(menuCatalogList);
				// Collections.sort(menuProcessor.getMenuList(),new
				// MenuComparator());
				sysUser.setMenu(menuProcessor.getMenuList());
			}

		} catch (Exception e) {
			throw new SysException("SecurityService:doGetAuthorizationInfoByRole", e);

		}

		return info;
	}

	@Override
	public List<MenuDTO> queryActiveMenu() throws SysException {
		List<MenuDTO> list = null;
		try {
			list = menuMapper.queryActiveMenu();
		} catch (Exception e) {
			throw new SysException("SecurityService:queryActiveMenu", e);

		}
		return list;
	}
}
