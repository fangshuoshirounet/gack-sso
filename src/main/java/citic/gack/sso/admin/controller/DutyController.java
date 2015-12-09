package citic.gack.sso.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import citic.gack.sso.admin.service.DutyService;
import citic.gack.sso.base.BaseController;
import citic.gack.sso.base.PageInfo;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.SysUser;
import citic.gack.sso.admin.security.SecurityManager;

@Controller
@RequestMapping("/admin/organization/duty")
public class DutyController extends BaseController<DutyDTO> {

	private static final Logger logger = LoggerFactory.getLogger(DutyController.class);
	private static final String BUSINESS_PATH = "/admin/organization/duty";

	@Autowired
	private DutyService dutyService;

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
	 * 返回JSON信息 如果增加成功返回{message:MESSAGE_CREATE} 失败{message:异常信息}
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> createNew(HttpServletRequest request, DutyDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setCreator(userName);
			model.setCreatorId(userId);
			dutyService.insert(model);
			appInfo.put("message", MESSAGE_CREATE);
		} catch (SysException e) {
			appInfo.put("message", MESSAGE_CREATE_FAIL);
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
		List<DutyDTO> delList = new ArrayList<DutyDTO>();
		SysUser sysUser = SecurityManager.getSessionUser();
		String userId = sysUser.getSysUserId();
		String userName = sysUser.getName();
		if (StringUtils.isNotBlank(id)) {
			String[] uniKey = id.split("-");
			if (uniKey != null && uniKey.length > 0) {
				for (String string : uniKey) {
					DutyDTO duty = new DutyDTO();
					String[] ids = string.split(",");
					duty.setVersion(ids[1]);
					duty.setDutyId(ids[0]);
					duty.setOperatorId(userId);
					duty.setOperator(userName);
					delList.add(duty);
				}
				try {
					dutyService.deleteBatch(delList);
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
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isNotBlank(id)) {
			DutyDTO duty = new DutyDTO();
			duty.setDutyId(id);
			try {
				duty = dutyService.queryBean(duty);
				mv.addObject("model", duty);
				mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_SHOW));
			} catch (SysException e) {
				logger.error("", e);
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
		ModelAndView mv = new ModelAndView();
		if (StringUtils.isNotBlank(id)) {
			DutyDTO duty = new DutyDTO();
			duty.setDutyId(id);
			try {
				duty = dutyService.queryBean(duty);
				mv.addObject("model", duty);
				mv.setViewName(getMappingUrl(BUSINESS_PATH, PAGE_EDIT));
			} catch (SysException e) {
				logger.error("", e);
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
		return getMappingUrl(BUSINESS_PATH, PAGE_EDITNEW);
	}

	/**
	 * 分页查询返回pageInfo对象 model如果属性很多都从request中获取很麻烦，不如自己驱动模型
	 * 
	 * @return
	 * @throws SysException
	 */
	@RequestMapping(method = RequestMethod.POST, params = "search=true")
	public @ResponseBody
	PageInfo search(HttpServletRequest request, DutyDTO model) throws SysException {
		PageInfo pageInfo = getPageInfo(request);
		try {
			pageInfo = dutyService.queryListByPage(model, pageInfo);
		} catch (SysException e) {
		}

		return pageInfo;
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
	Map<String, Object> update(HttpServletRequest request, DutyDTO model) throws SysException {
		Map<String, Object> appInfo = new HashMap<String, Object>();
		try {
			SysUser sysUser = SecurityManager.getSessionUser();
			String userId = sysUser.getSysUserId();
			String userName = sysUser.getName();
			model.setOperatorId(userId);
			model.setOperator(userName);
			int flag = dutyService.update(model);
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
}
