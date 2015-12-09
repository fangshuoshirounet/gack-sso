package citic.gack.sso.admin.controller;

import static citic.gack.sso.admin.security.SecurityManager.ADMIN_ROLE_CODE;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import citic.gack.sso.admin.dto.OperatePermissionDTO;
import citic.gack.sso.admin.dto.RoleDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.dto.SysUserPermissionDTO;
import citic.gack.sso.admin.security.SecurityManager;
import citic.gack.sso.admin.service.OrganizationService;
import citic.gack.sso.admin.service.RoleService;
import citic.gack.sso.admin.service.SysUserConstraintService;
import citic.gack.sso.admin.service.SysUserPermissionService;
import citic.gack.sso.admin.service.SysUserPrivilegeService;
import citic.gack.sso.admin.service.SysUserRoleService;
import citic.gack.sso.admin.service.SysUserService;
import citic.gack.sso.base.BaseController;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenu;
import citic.gack.sso.entity.SysMenuCatalog;
import citic.gack.sso.entity.SysRole;
import citic.gack.sso.entity.SysUser;
import citic.gack.sso.entity.SysUserConstraint;
import citic.gack.sso.entity.SysUserPrivilege;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/security/sysUserpermission")
public class SysUserPermissionController extends BaseController<SysUserPermissionDTO> {

	private static final Logger logger = LoggerFactory.getLogger(SysUserPermissionController.class);
	private static final String BUSINESS_PATH = "/admin/security/sysuserpermission";

	@Autowired
	private SysUserPermissionService sysUserPermissionService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private SysUserPrivilegeService sysUserPrivilegeService;
	@Autowired
	private SysUserConstraintService sysUserConstraintService;
	@Autowired
	private RoleService roleService;

	/**
	 * 返回首页
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) throws SysException {
		ModelAndView mv = new ModelAndView();
		String permission = request.getParameter("permission");
		mv.addObject("permission", permission);
		mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_INDEX));
		return mv;
	}

	private void initMenuSysUserPrivilege(HttpServletRequest request, SysUserPermissionDTO model) {
		try {
			String catalog = JSONArray.fromObject(SecurityManager.getSessionUser().getMenuCatalog()).toString();
			List<SysMenu> menus = SecurityManager.getSessionUser().getMenu();
			String menu = JSONArray.fromObject(menus).toString();
			List<OperatePermissionDTO> roleMenus = sysUserPermissionService.initMenuSysUserPrivilege(model);
			String roleMenuJson = JSONArray.fromObject(roleMenus).toString();
			request.setAttribute("catalog", catalog);
			request.setAttribute("menu", menu);
			request.setAttribute("roleMenuJson", roleMenuJson);
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	private void initMenuSysUserConstraint(HttpServletRequest request, SysUserPermissionDTO model) {
		try {
			String catalog = JSONArray.fromObject(SecurityManager.getSessionUser().getMenuCatalog()).toString();
			List<SysMenu> menus = SecurityManager.getSessionUser().getMenu();
			String menu = JSONArray.fromObject(menus).toString();
			List<OperatePermissionDTO> roleMenus = sysUserPermissionService.initMenuSysUserConstraint(model);
			String roleMenuJson = JSONArray.fromObject(roleMenus).toString();
			request.setAttribute("catalog", catalog);
			request.setAttribute("menu", menu);
			request.setAttribute("roleMenuJson", roleMenuJson);
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	private void initMenuCatalogSysUserPrivilege(HttpServletRequest request, SysUserPermissionDTO model) {
		try {
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			List<SysMenuCatalog> menuCatalogsList = sysUser.getMenuCatalog();
			Map<String, OperatePermissionDTO> currentUserMenuMaps = sysUser.getCurrent_user_menucatalog_maps();
			List<SysMenuCatalog> catalogs = new ArrayList<SysMenuCatalog>();
			for (SysMenuCatalog menuCatalog : menuCatalogsList) {
				if (currentUserMenuMaps.containsKey(menuCatalog.getMenuCatalogId())) {
					catalogs.add(menuCatalog);
				}
			}
			List<OperatePermissionDTO> roleMenuCatalogs = sysUserPermissionService.initMenuCatalogSysUserPrivilege(model);
			String catalog = JSONArray.fromObject(catalogs).toString();
			String roleMenuCatalogJson = JSONArray.fromObject(roleMenuCatalogs).toString();

			request.setAttribute("catalog", catalog);
			request.setAttribute("roleMenuCatalogJson", roleMenuCatalogJson);
		} catch (SysException e) {
			logger.error("", e);
		}

	}

	private void initMenuCatalogSysUserConstraint(HttpServletRequest request, SysUserPermissionDTO model) {
		try {
			SysUserDTO sysUser = SecurityManager.getSessionUser();
			List<SysMenuCatalog> menuCatalogsList = sysUser.getMenuCatalog();
			Map<String, OperatePermissionDTO> currentUserMenuMaps = sysUser.getCurrent_user_menucatalog_maps();
			List<SysMenuCatalog> catalogs = new ArrayList<SysMenuCatalog>();
			for (SysMenuCatalog menuCatalog : menuCatalogsList) {
				if (currentUserMenuMaps.containsKey(menuCatalog.getMenuCatalogId())) {
					catalogs.add(menuCatalog);
				}
			}
			List<OperatePermissionDTO> roleMenuCatalogs = sysUserPermissionService.initMenuCatalogSysUserConstraint(model);
			String catalog = JSONArray.fromObject(catalogs).toString();
			String roleMenuCatalogJson = JSONArray.fromObject(roleMenuCatalogs).toString();
			request.setAttribute("catalog", catalog);
			request.setAttribute("roleMenuCatalogJson", roleMenuCatalogJson);
		} catch (SysException e) {
			logger.error("", e);
		}

	}

	@RequestMapping("/permission")
	public ModelAndView permission(HttpServletRequest request, SysUserPermissionDTO model, String permission) {
		String result = "index";
		boolean flag = true;

		flag = StringUtils.isNotBlank(permission) ? true : false;
		flag = StringUtils.isBlank(model.getSysUserId()) ? false : flag;

		result = flag ? permission : result;

		if (flag) {
			if ("menuprivilege".equals(permission)) {
				initMenuSysUserPrivilege(request, model);
			} else if ("menuconstraint".equals(permission)) {
				initMenuSysUserConstraint(request, model);
			} else if ("menucatalogprivilege".equals(permission)) {
				initMenuCatalogSysUserPrivilege(request, model);
			} else if ("menucatalogconstraint".equals(permission)) {
				initMenuCatalogSysUserConstraint(request, model);
			}
		}
		ModelAndView mv = new ModelAndView(getMappingUrl(BUSINESS_PATH, result));
		mv.addObject("model", model);
		mv.addObject("permission", permission);
		return mv;
	}

	/**
	 * 保存菜单用户特权
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/saveMenuPrivilege")
	public @ResponseBody
	Map<String, Object> saveMenuPrivilege(HttpServletRequest request, SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("model", model);
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setCreator(userName);
			model.setCreatorId(userId);
			String[] menuIds = request.getParameterValues("menuIds");
			map.put("menuIds", menuIds);
			if (menuIds != null && menuIds.length > 0) {
				String[] menuoCatalogPerations;
				for (String catalogId : menuIds) {
					menuoCatalogPerations = request.getParameterValues(catalogId);
					map.put(catalogId, menuoCatalogPerations);
				}
			}
			sysUserPermissionService.saveMenuSysUserPrivilege(map);
			appInfo.put("message", MESSAGE_CREATE);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}
		return appInfo;
	}

	/**
	 * 保存菜单目录用户特权
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/saveMenuCatalogPrivilege")
	public @ResponseBody
	Map<String, Object> saveMenuCatalogPrivilege(HttpServletRequest request, SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setCreator(userName);
			model.setCreatorId(userId);
			map.put("model", model);
			String[] menucatalogIds = request.getParameterValues("menucatalogIds");
			map.put("menucatalogIds", menucatalogIds);
			if (menucatalogIds != null && menucatalogIds.length > 0) {
				String[] menuoCatalogPerations;
				for (String catalogId : menucatalogIds) {
					menuoCatalogPerations = request.getParameterValues(catalogId);
					if (StringUtils.isNotBlank(menuoCatalogPerations[0])) {
						map.put(catalogId, menuoCatalogPerations);
					}
				}
			}
			sysUserPermissionService.saveMenucatalogSysUserPrivilege(map);
			appInfo.put("message", MESSAGE_CREATE);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}
		return appInfo;
	}

	/**
	 * 保存菜单用户约束
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/saveMenuConstraint")
	public @ResponseBody
	Map<String, Object> saveMenuConstraint(HttpServletRequest request, SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setCreator(userName);
			model.setCreatorId(userId);
			map.put("model", model);
			String[] menuIds = request.getParameterValues("menuIds");
			map.put("menuIds", menuIds);
			if (menuIds != null && menuIds.length > 0) {
				String[] menuoCatalogPerations;
				for (String catalogId : menuIds) {
					menuoCatalogPerations = request.getParameterValues(catalogId);
					map.put(catalogId, menuoCatalogPerations);
				}
			}
			sysUserPermissionService.saveMenuSysUserConstraint(map);
			appInfo.put("message", MESSAGE_CREATE);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}
		return appInfo;
	}

	/**
	 * 保存菜单目录用户约束
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/saveMenuCatalogConstraint")
	public @ResponseBody
	Map<String, Object> saveMenuCatalogConstraint(HttpServletRequest request, SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setCreator(userName);
			model.setCreatorId(userId);
			map.put("model", model);
			String[] menucatalogIds = request.getParameterValues("menucatalogIds");
			map.put("menucatalogIds", menucatalogIds);
			if (menucatalogIds != null && menucatalogIds.length > 0) {
				String[] menuoCatalogPerations;
				for (String catalogId : menucatalogIds) {
					menuoCatalogPerations = request.getParameterValues(catalogId);
					if (StringUtils.isNotBlank(menuoCatalogPerations[0])) {
						map.put(catalogId, menuoCatalogPerations);
					}
				}
			}
			sysUserPermissionService.saveMenucatalogSysUserConstraint(map);
			appInfo.put("message", MESSAGE_CREATE);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}
		return appInfo;
	}

	private void initModelParams(SysUserPermissionDTO model) {
		if (StringUtils.isNotBlank(model.getMenuCatalogId())) {
			String[] catalogIds = model.getMenuCatalogId().split(",");
			model.setMenuCatalogId(catalogIds[0]);
		}
		if (StringUtils.isNotBlank(model.getSysUserId())) {
			String[] sysUserIds = model.getSysUserId().split(",");
			model.setSysUserId(sysUserIds[0]);
		}
	}

	/**
	 * 查询菜单用户特权
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/searchMenuPrivilege")
	public void searchMenuPrivilege(HttpServletResponse response, SysUserPermissionDTO model) throws SysException {
		try {
			initModelParams(model);
			OperatePermissionDTO operatePermissionDTO = sysUserPermissionService.searchMenuSysUserPrivilege(model);
			String operatePermissionDTOJson = JSONArray.fromObject(operatePermissionDTO).toString();
			try {
				response.setContentType("text/plain;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(operatePermissionDTOJson);
				writer.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	/**
	 * 查询菜单目录用户特权
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/searchMenuCatalogPrivilege")
	public void searchMenuCatalogPrivilege(HttpServletResponse response, SysUserPermissionDTO model) throws SysException {
		try {
			initModelParams(model);
			OperatePermissionDTO operatePermissionDTO = sysUserPermissionService.searchMenucatalogSysUserPrivilege(model);
			String operatePermissionDTOJson = JSONArray.fromObject(operatePermissionDTO).toString();
			try {
				response.setContentType("text/plain;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(operatePermissionDTOJson);
				writer.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	/**
	 * 查询菜单用户约束
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/searchMenuConstraint")
	public void searchMenuConstraint(HttpServletResponse response, SysUserPermissionDTO model) throws SysException {
		try {
			initModelParams(model);
			OperatePermissionDTO operatePermissionDTO = sysUserPermissionService.searchMenuSysUserConstraint(model);
			String operatePermissionDTOJson = JSONArray.fromObject(operatePermissionDTO).toString();
			try {
				response.setContentType("text/plain;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(operatePermissionDTOJson);
				writer.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	/**
	 * 查询菜单目录用户约束
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/searchMenuCatalogConstraint")
	public void searchMenuCatalogConstraint(HttpServletResponse response, SysUserPermissionDTO model) throws SysException {
		try {
			initModelParams(model);
			OperatePermissionDTO operatePermissionDTO = sysUserPermissionService.searchMenucatalogSysUserConstraint(model);
			String operatePermissionDTOJson = JSONArray.fromObject(operatePermissionDTO).toString();
			try {
				response.setContentType("text/plain;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(operatePermissionDTOJson);
				writer.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	/**
	 * 添加系统角色
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/searchRole")
	public ModelAndView searchRole(HttpServletRequest request, SysUserPermissionDTO model) throws SysException {
		String result = "index";
		String permission = request.getParameter("permission");

		// 1.判断是否有系统用户和员工
		if (StringUtils.isNotBlank(model.getSysUserId())) {
			result = "addrole";
		}
		ModelAndView mv = new ModelAndView(getMappingUrl(BUSINESS_PATH, result));
		mv.addObject("permission", permission);
		mv.addObject("model", model);
		return mv;
	}

	/**
	 * 授权之前验证是否是admin
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/validateadmin")
	public @ResponseBody
	Map<String, Object> validateadmin(SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			String errorMsg = "";
			if (StringUtils.isBlank(model.getSysUserId())) {
				errorMsg = "您选择选项不存在，可能已经删除，请选择其它选项。";
				appInfo.put("errorMsg", errorMsg);
				appInfo.put("errorResult", "1");
			} else {
				List<SysRole> roles = sysUserRoleService.querySysUserRole(model.getSysUserId());
				if (roles == null || roles.size() == 0) {
					errorMsg = "您选择角色还没有保存，是否进行下一步授权?";
					appInfo.put("errorMsg", errorMsg);
					appInfo.put("errorResult", "2");
				} else {
					for (SysRole role : roles) {
						if (ADMIN_ROLE_CODE.equals(role.getRoleCode())) {
							errorMsg = "您选择" + role.getRoleName() + "选项是admin，已经拥有所有权限，不能授权，请选择其它选项。";
							appInfo.put("errorMsg", errorMsg);
							appInfo.put("errorResult", "1");
						}
					}
				}
			}
		} catch (SysException e) {
			logger.error("", e);
		}
		return appInfo;
	}

	/**
	 * 查看员工功能视图
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/SysUserView")
	public @ResponseBody
	PageInfo SysUserView(HttpServletRequest request, SysUserPermissionDTO model) throws SysException {
		PageInfo pageInfo = null;
		try {
			pageInfo = sysUserPermissionService.searchSysUserView(model, getPageInfo(request));
		} catch (SysException e) {
			logger.error("", e);
		}
		return pageInfo;
	}

	/**
	 * 构建系统组织机构tree(第一步)
	 * 
	 */
	@RequestMapping("/orgtreejson")
	public void orgtreejson(HttpServletRequest request, HttpServletResponse response, boolean isChild) throws SysException {
		response.setContentType("text/plain;charset=UTF-8");
		String id = request.getParameter("id");

		try {
			String rs = organizationService.treejson(isChild, id);
			PrintWriter writer = response.getWriter();
			writer.write(rs);
			writer.close();
		} catch (SysException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 保存添加系统用户(第二步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/saveSysUser")
	public @ResponseBody
	Map<String, Object> saveSysUser(SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		SysUser sysUser = SecurityManager.getSessionUser();
		String userId = sysUser.getSysUserId();
		String userName = sysUser.getName();
		model.setCreator(userName);
		model.setCreatorId(userId);
		model.getSysUser().setCreator(userId);
		try {
			sysUserService.insert(model.getSysUser());
		} catch (SysException e) {
			logger.error("", e);
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}
		appInfo.put("message", MESSAGE_CREATE);
		return appInfo;
	}

	/**
	 * 修改系统用户(第二步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/editSysUser")
	public ModelAndView editSysUser(SysUserPermissionDTO model) throws SysException {
		String result = "addsysuser";
		try {
			SysUserDTO sysUser = new SysUserDTO();
			if (StringUtils.isNotBlank(model.getSysUserId())) {
				sysUser.setSysUserId(model.getSysUserId());
				sysUser = sysUserService.queryBean(sysUser);
				model.setSysUser(sysUser);
				result = "editsysuser";
			}
		} catch (SysException e) {
			logger.error("", e);
		}
		ModelAndView mv = new ModelAndView(getMappingUrl(BUSINESS_PATH, "SysUserpermission-" + result));
		mv.addObject("model", model);
		return mv;
	}

	/**
	 * 保存修改系统用户(第二步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/updateSysUser")
	public @ResponseBody
	Map<String, Object> updateSysUser(SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setOperatorId(userId);
			model.setOperator(userName);
			int flag = sysUserService.update(model.getSysUser());
			if (flag > 0) {
				appInfo.put("message", MESSAGE_UPDATE);
			} else {
				appInfo.put("message", MESSAGE_UPDATE_FAIL);
			}
		} catch (SysException e) {
			logger.error("", e);
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}

		return appInfo;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request, @PathVariable("id") String id) throws SysException {
		ModelAndView mv = new ModelAndView();
		SysUserDTO model = new SysUserDTO();
		model.setSysUserId(id);
		model = sysUserService.queryBean(model);
		mv.addObject("model", model);
		mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_SHOW));
		return mv;
	}

	/**
	 * 删除验证
	 * 
	 * @return
	 */
	@RequestMapping("/validateremove")
	public @ResponseBody
	Map<String, Object> validateremove(HttpServletRequest request, SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			String keys = request.getParameter("keys");
			if (StringUtils.isNotBlank(keys)) {
				String[] uniKey = keys.split("-");
				if (uniKey != null && uniKey.length > 0) {
					for (String string : uniKey) {
						String[] ids = string.split(",");
						model.setSysUserId(ids[0]);
						SysUserDTO su = new SysUserDTO();
						su.setSysUserId(ids[0]);
						su = sysUserService.queryBean(su);
						if (su == null) {
							String errorMsg = "您选择的选项可能已经删除，请刷新页面在进行此操作！";
							appInfo.put("message", errorMsg);
							break;
						}
						List<SysUserPrivilege> suList = sysUserPrivilegeService.queryMenuSysUserPrivilege(model);
						if (suList != null && suList.size() > 0) {
							String errorMsg = "您选择的" + su.getLoginName() + "有菜单或者目录关联的特权数据，请先删除关联数据。";
							appInfo.put("message", errorMsg);
							break;
						} else {
							List<SysUserConstraint> sdList = sysUserConstraintService.queryMenuSysUserConstraint(model);
							if (sdList != null && sdList.size() > 0) {
								String errorMsg = "您选择的" + su.getLoginName() + "有菜单或者目录关联的约束数据，请先删除关联数据。";
								appInfo.put("message", errorMsg);
								break;
							}
						}
					}
				}
			}
		} catch (SysException e) {
			logger.error("", e);
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}
		return appInfo;
	}

	/**
	 * 删除系统用户(第二步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/removeSysUser")
	public @ResponseBody
	Map<String, Object> removeSysUser(HttpServletRequest request) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		SysUser sysUser = SecurityManager.getSessionUser();
		String userId = sysUser.getSysUserId();
		String userName = sysUser.getName();
		List<SysUserDTO> delList = new ArrayList<SysUserDTO>();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			String[] uniKey = id.split("-");
			if (uniKey != null && uniKey.length > 0) {
				for (String string : uniKey) {
					SysUserDTO sta = new SysUserDTO();
					sta.setOperatorId(userId);
					sta.setOperator(userName);
					sta.setSysUserId(string);
					delList.add(sta);
				}
			}
			try {
				sysUserService.deleteBatch(delList);
				appInfo.put("message", MESSAGE_DESTROY);
			} catch (SysException e) {
				appInfo.put("message", MESSAGE_CREATE_FAIL);
				logger.error("", e);
			}
		}
		return appInfo;
	}

	/**
	 * 查看系统用户(第二步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/showSysUser")
	public ModelAndView showSysUser(SysUserPermissionDTO model) throws SysException {
		SysUserDTO sysUser = new SysUserDTO();
		sysUser.setSysUserId(model.getSysUserId());
		try {
			sysUser = sysUserService.queryBean(sysUser);
			model.setSysUser(sysUser);
		} catch (SysException e) {
			logger.error("", e);
		}
		ModelAndView mv = new ModelAndView(getMappingUrl(BUSINESS_PATH, "SysUserpermission-showsysuser"));
		mv.addObject("model", model);
		return mv;
	}

	/**
	 * 重置密码(第二步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/sysUserPwdReset")
	public ModelAndView sysUserPwdReset(SysUserPermissionDTO model) throws SysException {
		SysUserDTO sysUser = new SysUserDTO();
		sysUser.setSysUserId(model.getSysUserId());
		try {
			sysUser = sysUserService.queryBean(sysUser);
			model.setSysUser(sysUser);
		} catch (SysException e) {
			logger.error("", e);
		}
		ModelAndView mv = new ModelAndView(getMappingUrl(BUSINESS_PATH, "SysUserpermission-sysuserpwdreset"));
		mv.addObject("model", model);
		return mv;
	}

	/**
	 * 重置密码保存(第二步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/savePwdReset")
	public @ResponseBody
	Map<String, Object> savePwdReset(SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			if (!sysUserService.pwdreset(model.getSysUser())) {
				appInfo.put("message", "重置密码失败！");
			}
		} catch (SysException e) {
			logger.error("", e);
			appInfo.put("message", "重置密码失败！");
		}
		return appInfo;
	}

	/**
	 * 查询用户的系统用户角色(第三步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/queryRoleList")
	public @ResponseBody
	PageInfo queryRoleList(HttpServletRequest request) throws SysException {
		PageInfo pageInfo = null;
		try {
			pageInfo = roleService.queryListByPage(new RoleDTO(), getPageInfo(request));
		} catch (SysException e) {
			logger.error("", e);
		}
		return pageInfo;
	}

	/**
	 * 查询用户的系统用户角色(第三步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/querySysUserRole")
	public @ResponseBody
	Map<String, Object> querySysUserRole(SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(model.getSysUserId())) {
				List<SysRole> roles = sysUserRoleService.querySysUserRole(model.getSysUserId());
				List<String> ids = new ArrayList<String>();
				for (SysRole role : roles) {
					ids.add(role.getRoleId());
				}
				appInfo.put("ids", ids.toString());
			}
		} catch (SysException e) {
			logger.error("", e);
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}
		return appInfo;
	}

	/**
	 * 添加系统用户角色(第三步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/saveSysUserRole")
	public @ResponseBody
	Map<String, Object> saveSysUserRole(SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(model.getSysUserId())) {
				sysUserPermissionService.addSysUserRole(model);
			}
			appInfo.put("message", MESSAGE_CREATE);
		} catch (SysException e) {
			logger.error("", e);
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}

		return appInfo;
	}

	/**
	 * 添加系统用户角色(第三步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/applySysUserRole")
	public @ResponseBody
	Map<String, Object> applySysUserRole(SysUserPermissionDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotBlank(model.getSysUserId())) {
				SysUser sysUser = SecurityManager.getSessionUser();
				String userId = sysUser.getSysUserId();
				String userName = sysUser.getName();
				model.setOperatorId(userId);
				model.setOperator(userName);
				model.setCreator(userName);
				model.setCreatorId(userId);
				sysUserPermissionService.addApplyUserRole(model);
			}
			appInfo.put("message", MESSAGE_APPLY);
		} catch (SysException e) {
			logger.error("", e);
			appInfo.put("message", MESSAGE_APPLY_FAIL);
		}

		return appInfo;
	}

}
