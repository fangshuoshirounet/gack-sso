package citic.gack.sso.admin.controller;

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

import citic.gack.sso.admin.dto.MenuCatalogDTO;
import citic.gack.sso.admin.dto.MenuDTO;
import citic.gack.sso.admin.dto.MenuOperationDTO;
import citic.gack.sso.admin.dto.OperationDTO;
import citic.gack.sso.admin.dto.RolePermissionDTO;
import citic.gack.sso.admin.security.SecurityManager;
import citic.gack.sso.admin.service.MenuCatalogService;
import citic.gack.sso.admin.service.MenuOperationService;
import citic.gack.sso.admin.service.MenuService;
import citic.gack.sso.admin.service.OperationService;
import citic.gack.sso.base.BaseController;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysMenuCatalog;
import citic.gack.sso.entity.SysUser;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/security/menu")
public class MenuController extends BaseController<MenuDTO> {
	private static Logger logger = LoggerFactory.getLogger(MenuController.class);
	private static final String BUSINESS_PATH = "/admin/security/menu";

	@Autowired
	private MenuService menuService;
	@Autowired
	private MenuCatalogService menuCatalogService;
	@Autowired
	private OperationService operationService;
	@Autowired
	private MenuOperationService menuOperationService;

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
		mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_INDEX));
		return mv;
	}

	/**
	 * 分页查询返回pageInfo对象 model如果属性很多都从request中获取很麻烦，不如自己驱动模型
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(method = RequestMethod.POST, params = "search=true")
	public @ResponseBody
	PageInfo search(HttpServletRequest request, MenuDTO model) throws SysException {

		PageInfo pageInfo = getPageInfo(request);
		try {
			pageInfo = menuService.queryListByPage(model, pageInfo);
		} catch (SysException e) {
		}

		return pageInfo;
	}

	/**
	 * 返回JSON信息 如果增加成功返回{message:MESSAGE_CREATE} 失败{message:异常信息}
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> createNew(HttpServletRequest request, MenuDTO model) throws SysException {
		// 1.拿到前台传来的菜单数据
		// 2.插入菜单数据
		// 3.拿到前台传过来的操作id集合
		// 2.依次遍历集合，插入操蛋操作
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setCreator(userName);
			model.setCreatorId(userId);
			menuService.insertMenuAndOperation(model);
			appInfo.put("message", MESSAGE_CREATE);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}

		return appInfo;
	}

	/**
	 * 确定修改操作 如果修改成功返回{message:MESSAGE_UPDATE} 失败{message:异常信息}
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	Map<String, Object> update(HttpServletRequest request, MenuDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setOperatorId(userId);
			model.setOperator(userName);
			menuService.updateMenuAndOperation(model);
			appInfo.put("message", MESSAGE_UPDATE);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_UPDATE_FAIL);
		}

		return appInfo;
	}

	/**
	 * 确定删除操作 如果删除成功返回{message:MESSAGE_DESTROY} 失败{message:异常信息}
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	Map<String, Object> destroy(HttpServletRequest request, @PathVariable("id") String id) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		SysUser sysUser = SecurityManager.getSessionUser();
		String userId = sysUser.getSysUserId();
		String userName = sysUser.getName();
		List<MenuDTO> delList = new ArrayList<MenuDTO>();
		if (StringUtils.isNotBlank(id)) {
			String[] uniKey = id.split("-");
			if (uniKey != null && uniKey.length > 0) {
				for (String string : uniKey) {
					MenuDTO menu = new MenuDTO();
					menu.setMenuId(string);
					menu.setOperatorId(userId);
					menu.setOperator(userName);
					delList.add(menu);
				}
				try {
					menuService.deleteMenuAndOperation(delList);
					appInfo.put("message", MESSAGE_DESTROY);
				} catch (SysException e) {
					appInfo.put("message", MESSAGE_DESTROY_FAIL);
				}
			}
		}
		return appInfo;
	}

	/**
	 * 一开始使用@PathVariable("id") String
	 * id只注入id，但很多业务还需要获取更多参数，所以作为共用方法注入HttpServletRequest 去查看界面
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request, @PathVariable("id") String id) throws SysException {
		initOperationList(request, id);
		ModelAndView mv = new ModelAndView();
		MenuDTO model = new MenuDTO();
		if (StringUtils.isNotBlank(id)) {
			model.setMenuId(id);
			try {
				model = menuService.showMenuAndOperation(model);
				mv.addObject("model", model);
				mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_SHOW));
			} catch (SysException e) {

			}
		}
		return mv;
	}

	/**
	 * 去编辑界面
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) throws SysException {
		initOperationList(request, id);
		ModelAndView mv = new ModelAndView();
		MenuDTO model = new MenuDTO();
		if (StringUtils.isNotBlank(id)) {
			model.setMenuId(id);
			try {
				model = menuService.showMenuAndOperation(model);
				mv.addObject("model", model);
				mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_EDIT));
			} catch (SysException e) {

			}
		}
		return mv;
	}

	/**
	 * 去新增界面
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew(HttpServletRequest request) throws SysException {
		initOperationList(request, null);
		return getMappingUrl(BUSINESS_PATH, PAGE_EDITNEW);
	}

	public void initOperationList(HttpServletRequest request, String id) {
		try {
			OperationDTO entity = new OperationDTO();
			entity.setSts("A");
			List<OperationDTO> operations = operationService.queryList(entity);
			RolePermissionDTO rDTO = new RolePermissionDTO();
			List<String> opIds = new ArrayList<String>();
			if (StringUtils.isNotBlank(id)) {
				rDTO.setMenuId(id);
				List<MenuOperationDTO> moList = menuOperationService.queryMenuCatalogOperations(rDTO);
				for (MenuOperationDTO mop : moList) {
					opIds.add(mop.getOperationId());
				}
			}

			List<OperationDTO> operationList = new ArrayList<OperationDTO>();
			for (OperationDTO op : operations) {
				if (opIds.contains(op.getOperationId())) {
					op.setChecked(true);
				}
				operationList.add(op);
			}
			request.setAttribute("operationList", operationList);
		} catch (SysException e) {
			logger.error("获取操作列列表数据时发生异常:" + e.getMessage());
		}
	}

	/**
	 * 菜单目录树
	 * 
	 */
	@RequestMapping("/treejson")
	public void treejson(HttpServletRequest request, HttpServletResponse response, boolean isChild) throws SysException {
		response.setContentType("text/plain;charset=UTF-8");
		String id = request.getParameter("id");
		MenuCatalogDTO catalog = new MenuCatalogDTO();
		if (!isChild && StringUtils.isNotBlank(id)) {
			catalog.setMenuCatalogId(id);
		}
		{
			List<MenuCatalogDTO> list;
			try {
				list = menuCatalogService.queryList(catalog);
				List<MenuCatalogDTO> rootMenuCatalog = new ArrayList<MenuCatalogDTO>();
				if (StringUtils.isNotBlank(id)) {
					catalog.setMenuCatalogId(id);
				}
				rootMenuCatalog.addAll(list);
				// 构造所有权限
				List<Object> jsonList = new ArrayList<Object>();
				buildJsonList(jsonList, rootMenuCatalog, 1, isChild, catalog);
				JSONArray jo = JSONArray.fromObject(jsonList);
				String rs = jo.toString();
				try {
					PrintWriter writer = response.getWriter();
					writer.write(rs);
					writer.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			} catch (SysException e) {
				logger.error("", e);
			}
		}
	}

	private void buildJsonList(List<Object> jsonList, List<MenuCatalogDTO> datas, int level, boolean isChild, SysMenuCatalog model) throws SysException {
		if (datas == null) {
			return;
		}
		try {
			for (MenuCatalogDTO obj : datas) {
				String id = obj.getMenuCatalogId();
				if (isChild && id.equals(model.getMenuCatalogId())) {
					continue;
				}
				String text = obj.getCatalogName();
				Map<String, Object> amap = new HashMap<String, Object>();
				List<MenuCatalogDTO> children = menuCatalogService.queryList(obj);
				if (children != null && children.size() > 0) {
					amap.put("state", "closed");
					amap.put("attributes", "true");
					if (isChild) {
						buildJsonChildList(amap, children, level++, isChild, model);
					}
				} else {
					amap.put("state", "open");
					amap.put("attributes", "false");
				}
				amap.put("text", text);
				amap.put("id", id);
				jsonList.add(amap);
			}
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	private void buildJsonChildList(Map<String, Object> parentMap, List<MenuCatalogDTO> datas, int level, boolean isChild, SysMenuCatalog model) throws SysException {
		if (datas == null) {
			return;
		}
		List<Object> list = new ArrayList<Object>();
		try {
			for (MenuCatalogDTO obj : datas) {
				String id = obj.getMenuCatalogId();
				String text = obj.getCatalogName();
				if (isChild && id.equals(model.getMenuCatalogId())) {
					parentMap.put("state", "open");
					continue;
				}
				Map<String, Object> amap = new HashMap<String, Object>();
				List<MenuCatalogDTO> children = menuCatalogService.queryList(obj);
				if (children != null && children.size() > 0) {
					amap.put("state", "closed");
					amap.put("attributes", "true");
					if (isChild) {
						buildJsonChildList(amap, children, level + 1, isChild, model);
					}
				} else {
					amap.put("state", "open");
					amap.put("attributes", "false");
				}
				amap.put("text", text);
				amap.put("id", String.valueOf(id));
				list.add(amap);
			}
		} catch (SysException e) {
			logger.error("", e);
		}
		parentMap.put("children", list);
	}

}
