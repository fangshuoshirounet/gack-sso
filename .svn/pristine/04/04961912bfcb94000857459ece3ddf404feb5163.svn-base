package citic.gack.sso.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import citic.gack.sso.admin.dto.ApplyRoleDTO;
import citic.gack.sso.admin.security.SecurityManager;
import citic.gack.sso.admin.service.ApplyRoleService;
import citic.gack.sso.base.BaseController;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysUser;
import citic.gack.sso.utils.ConstantsUtils;

@Controller
@RequestMapping("/admin/security/applyrole")
public class ApplyRoleController extends BaseController<ApplyRoleDTO> {
	private static final String BUSINESS_PATH = "/admin/security/applyrole";

	@Autowired
	private ApplyRoleService applyRoleService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request, @PathVariable("id") String id) throws SysException {
		ModelAndView mv = new ModelAndView();
		ApplyRoleDTO model = new ApplyRoleDTO();
		model.setId(id);
		model = applyRoleService.queryBean(model);
		mv.addObject("model", model);
		mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_SHOW));
		return mv;
	}

	/**
	 * 列表首页
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) throws SysException {
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		mv.addObject("type", type);
		if (type.equals("pend")) {
			mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_INDEX));
		} else if (type.equals("pending")) {
			mv.setViewName(getMappingUrl(BUSINESS_PATH, "pending_index"));
		} else if (type.equals("pended")) {
			mv.setViewName(getMappingUrl(BUSINESS_PATH, "pended_index"));
		}
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
	PageInfo search(HttpServletRequest request, ApplyRoleDTO model) throws SysException {
		SysUser sysUser = SecurityManager.getSessionUser();
		String userId = sysUser.getSysUserId();
		model.setApplyorId(userId);
		model.setAuditorId(userId);
		model.setApplyType(ConstantsUtils.APPLY_ROLE_TYPE_R);
		PageInfo pageInfo = getPageInfo(request);
		pageInfo = applyRoleService.queryListByPage(model, pageInfo);
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
	Map<String, Object> createNew(HttpServletRequest request, ApplyRoleDTO model) {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setApplyorId(userId);
			model.setApplyor(userName);
			model.setCreator(userName);
			model.setCreatorId(userId);
			model.setAuditStatus(ConstantsUtils.AUDIT_STATUS_I);
			model.setApplyType(ConstantsUtils.APPLY_ROLE_TYPE_R);
			model.setChangeType(ConstantsUtils.CHANGE_TYPE_A);
			applyRoleService.insert(model);
			appInfo.put("message", MESSAGE_APPLY);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_APPLY_FAIL);
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
	Map<String, Object> update(HttpServletRequest request, ApplyRoleDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setApplyorId(userId);
			model.setApplyor(userName);
			model.setCreator(userName);
			model.setCreatorId(userId);
			model.setAuditStatus(ConstantsUtils.AUDIT_STATUS_I);
			model.setApplyType(ConstantsUtils.APPLY_ROLE_TYPE_R);
			model.setChangeType(ConstantsUtils.CHANGE_TYPE_U);
			applyRoleService.insert(model);
			appInfo.put("message", MESSAGE_APPLY);

		} catch (SysException e) {
			appInfo.put("message", MESSAGE_APPLY_FAIL);
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
		List<ApplyRoleDTO> delList = new ArrayList<ApplyRoleDTO>();
		SysUser sysUsers = SecurityManager.getSessionUser();
		String userId = sysUsers.getSysUserId();
		String userName = sysUsers.getName();
		if (StringUtils.isNotBlank(id)) {
			String[] uniKey = id.split("-");
			if (uniKey != null && uniKey.length > 0) {
				for (String string : uniKey) {
					String[] ids = string.split(",");
					ApplyRoleDTO sysUser = new ApplyRoleDTO();
					sysUser.setId(ids[0]);
					sysUser.setVersion(ids[1]);
					sysUser.setOperatorId(userId);
					sysUser.setOperator(userName);
					sysUser.setApplyType(ConstantsUtils.APPLY_ROLE_TYPE_R);
					delList.add(sysUser);
				}
			}
			try {
				applyRoleService.deleteBatch(delList);
				appInfo.put("message", MESSAGE_DESTROY);
			} catch (SysException e) {
				appInfo.put("message", MESSAGE_DESTROY_FAIL);
			}

		}
		return appInfo;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isNotBlank(id)) {
			ApplyRoleDTO dto = new ApplyRoleDTO();
			dto.setId(id);
			dto = applyRoleService.queryBean(dto);
			mv.addObject("model", dto);
			mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_EDIT));
		}
		return mv;
	}

	/**
	 * 确定审批操作 如果审批成功返回{message:MESSAGE_DESTROY} 失败{message:异常信息}
	 * 
	 * @param request
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(value = "/review/{id}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> review(HttpServletRequest request, @PathVariable("id") String id) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		List<ApplyRoleDTO> delList = new ArrayList<ApplyRoleDTO>();
		SysUser sysUsers = SecurityManager.getSessionUser();
		String auditOpintion = request.getParameter("auditOpintion");
		String auditStatus = request.getParameter("auditStatus");
		String userId = sysUsers.getSysUserId();
		String userName = sysUsers.getName();
		if (StringUtils.isNotBlank(id)) {
			String[] uniKey = id.split("-");
			if (uniKey != null && uniKey.length > 0) {
				for (String string : uniKey) {
					String[] ids = string.split(",");
					ApplyRoleDTO applyRole = new ApplyRoleDTO();
					applyRole.setId(ids[0]);
					applyRole.setVersion(ids[1]);
					applyRole.setOperatorId(userId);
					applyRole.setAuditor(userName);
					applyRole.setAuditorId(userId);
					applyRole.setOperator(userName);
					applyRole.setAuditStatus(auditStatus);
					applyRole.setAuditOpintion(auditOpintion);
					delList.add(applyRole);
				}
			}
			try {
				int flag = applyRoleService.updateBatch(delList);
				if (flag == delList.size()) {
					appInfo.put("message", MESSAGE_OPINTION);
				} else {
					appInfo.put("message", MESSAGE_OPINTION_FAIL);
				}
			} catch (SysException e) {
				appInfo.put("message", MESSAGE_OPINTION_FAIL);
			}

		}
		return appInfo;
	}

}
