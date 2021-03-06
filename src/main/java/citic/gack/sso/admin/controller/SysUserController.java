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

import citic.gack.sso.admin.dto.DutyDTO;
import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.security.SecurityManager;
import citic.gack.sso.admin.service.DutyService;
import citic.gack.sso.admin.service.OrganizationService;
import citic.gack.sso.admin.service.SysUserService;
import citic.gack.sso.base.BaseController;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysOrganization;
import citic.gack.sso.entity.SysUser;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/admin/organization/sysuser")
public class SysUserController extends BaseController<SysUserDTO> {
	private static Logger logger = LoggerFactory.getLogger(SysUserController.class);
	private static final String BUSINESS_PATH = "/admin/organization/sysuser";

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private DutyService dutyService;
	private boolean isChild;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request, @PathVariable("id") String id) throws SysException {
		ModelAndView mv = new ModelAndView();
		SysUserDTO model = new SysUserDTO();
		model.setSysUserId(id);
		try {
			model = sysUserService.queryBean(model);
			mv.addObject("model", model);
			mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_SHOW));
		} catch (SysException e) {
			logger.error("", e);
		}
		return mv;
	}

	/**
	 * <br>
	 * 描 述：修改密码 <br>
	 * 作 者：wangjincheng <br>
	 * 版 本：V1.0 <br>
	 * 时 间：2015年10月23日 上午11:59:20
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/updateuserpwd")
	public ModelAndView updateuserpwd(HttpServletRequest request) throws SysException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(getMappingUrl(BUSINESS_PATH, "update_user_pwd"));
		return mv;
	}

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
	PageInfo search(HttpServletRequest request, SysUserDTO model) throws SysException {

		PageInfo pageInfo = getPageInfo(request);
		try {
			pageInfo = sysUserService.queryListByPage(model, pageInfo);
		} catch (SysException e) {
		}

		return pageInfo;
	}

	/**
	 * 验证名字是否存在(第二步)
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping("/validatename")
	public void validatename(HttpServletResponse response, SysUserDTO model) throws SysException {
		response.setContentType("text/plain;charset=UTF-8");
		String errorMsg = "";
		try {

			if (StringUtils.isBlank(model.getLoginName())) {
				errorMsg = "请输入用户的登录名。";
			} else {
				SysUserDTO sysUser = sysUserService.searchSysUserByName(model.getLoginName());
				if (sysUser != null) {
					if (StringUtils.isNotBlank(model.getSysUserId())) {
						if (!model.getSysUserId().equals(sysUser.getSysUserId())) {
							errorMsg = "您输入用户登录名已经存在，请输入其它登录名。";
						}
					} else {
						errorMsg = "您输入用户登录名已经存在，请输入其它登录名。";
					}
				}
			}
			PrintWriter writer = response.getWriter();
			writer.write(errorMsg);
			writer.close();
		} catch (IOException e) {
			logger.error("", e);
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	/**
	 * 返回JSON信息 如果增加成功返回{message:MESSAGE_CREATE} 失败{message:异常信息}
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> createNew(HttpServletRequest request, SysUserDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setCreator(userName);
			model.setCreatorId(userId);
			sysUserService.insert(model);
			appInfo.put("message", MESSAGE_CREATE);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_CREATE_FAIL);
		}
		return appInfo;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	public Map<String, Object> pwdreset(HttpServletRequest request, SysUserDTO model) {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			if (!sysUserService.pwdreset(model)) {
				appInfo.put("message", "重置密码失败！");
			}
		} catch (SysException e) {
			logger.error("", e);
			appInfo.put("message", "重置密码失败！");
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
	Map<String, Object> update(HttpServletRequest request, SysUserDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setOperatorId(userId);
			model.setOperator(userName);
			int flag = sysUserService.update(model);
			if (flag > 0) {
				appInfo.put("message", MESSAGE_UPDATE);
			} else {
				appInfo.put("message", MESSAGE_UPDATE_FAIL);
			}
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
		List<SysUserDTO> delList = new ArrayList<SysUserDTO>();
		SysUser sysUsers = SecurityManager.getSessionUser();
		String userId = sysUsers.getSysUserId();
		String userName = sysUsers.getName();
		if (StringUtils.isNotBlank(id)) {
			String[] uniKey = id.split("-");
			if (uniKey != null && uniKey.length > 0) {
				for (String string : uniKey) {
					String[] ids = string.split(",");
					SysUserDTO sysUser = new SysUserDTO();
					sysUser.setSysUserId(ids[0]);
					sysUser.setOperatorId(userId);
					sysUser.setOperator(userName);
					delList.add(sysUser);
				}
			}
			try {
				sysUserService.deleteBatch(delList);
				appInfo.put("message", MESSAGE_DESTROY);
			} catch (SysException e) {
				appInfo.put("message", MESSAGE_DESTROY_FAIL);
			}

		}
		return appInfo;
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
		try {
			List<DutyDTO> dutyList = dutyService.queryList(new DutyDTO());
			request.setAttribute("dutyList", dutyList);
		} catch (Exception e) {
			logger.error("", e);
		}

		return getMappingUrl(BUSINESS_PATH, PAGE_EDITNEW);
	}

	/**
	 * 构建机构组织tree
	 * 
	 */
	@RequestMapping("/treejson")
	public void treejson(HttpServletRequest request, HttpServletResponse response) throws SysException {
		response.setContentType("text/plain;charset=UTF-8");
		String id = request.getParameter("id");
		SysOrganization organization = new SysOrganization();
		if (!isChild && StringUtils.isNotBlank(id)) {
			organization.setOrgId(id);
		}
		{
			List<SysOrganization> list;
			try {
				SysOrganization model = new SysOrganization();
				list = organizationService.queryList(organization);
				List<SysOrganization> rootstaffs = new ArrayList<SysOrganization>();
				if (StringUtils.isNotBlank(id)) {
					model.setOrgId(id);
				}
				rootstaffs.addAll(list);
				List<Object> jsonList = new ArrayList<Object>();
				buildJsonList(jsonList, rootstaffs, 1);
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

	/**
	 * 去编辑界面
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) throws SysException {
		ModelAndView mv = new ModelAndView();
		SysUserDTO model = new SysUserDTO();
		model.setSysUserId(id);

		try {
			model = sysUserService.queryBean(model);
			List<DutyDTO> dutyList = dutyService.queryList(new DutyDTO());
			mv.addObject("model", model);
			mv.addObject("dutyList", dutyList);
			mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_EDIT));
		} catch (SysException e) {
			logger.error("", e);
		}
		return mv;
	}

	private void buildJsonList(List<Object> jsonList, List<SysOrganization> datas, int level) throws SysException {
		if (datas == null) {
			return;
		}
		try {
			SysOrganization model = new SysOrganization();
			for (SysOrganization obj : datas) {
				String id = obj.getOrgId();
				if (isChild && id.equals(model.getOrgId())) {
					continue;
				}
				String text = obj.getName();
				Map<String, Object> amap = new HashMap<String, Object>();
				List<SysOrganization> children = organizationService.queryList(obj);
				if (children != null && children.size() > 0) {
					amap.put("state", "closed");
					amap.put("attributes", "true");
					if (isChild) {
						buildJsonChildList(amap, children, level++);
					}
				} else {
					amap.put("state", "open");
					amap.put("attributes", "false");
				}
				amap.put("text", text);
				amap.put("id", String.valueOf(id));
				jsonList.add(amap);
			}
		} catch (SysException e) {
			logger.error("", e);
		}
	}

	private void buildJsonChildList(Map<String, Object> parentMap, List<SysOrganization> datas, int level) throws SysException {
		if (datas == null) {
			return;
		}
		List<Object> list = new ArrayList<Object>();
		try {
			SysOrganization model = new SysOrganization();
			for (SysOrganization obj : datas) {
				String id = obj.getOrgId();
				if (isChild && id.equals(model.getOrgId())) {
					parentMap.put("state", "open");
					continue;
				}
				String text = obj.getName();
				Map<String, Object> amap = new HashMap<String, Object>();
				List<SysOrganization> children = organizationService.queryList(obj);
				if (children != null && children.size() > 0) {
					amap.put("state", "closed");
					amap.put("attributes", "true");
					if (isChild) {
						buildJsonChildList(amap, children, level + 1);
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
