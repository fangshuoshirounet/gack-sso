package citic.gack.web.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import citic.gack.web.sso.base.BaseController;
import citic.gack.web.sso.base.ResponseResult;
import citic.gack.web.sso.entity.SecUserDetails;
import citic.gack.web.sso.service.SecUserDetailService;

@Controller
@RequestMapping("/users")
public class UserResourceController extends BaseController<SecUserDetails>{

	@Autowired
	private SecUserDetailService secUserDetailsService;
	
	@RequestMapping("/{id}")
	public @ResponseBody ResponseResult getUser(@PathVariable("id") int userId){
		SecUserDetails secUserDetails = new SecUserDetails();
		secUserDetails.setUserId(userId);
		SecUserDetails user = secUserDetailsService.queryBean(secUserDetails);
		if(null == user){
			return new ResponseResult(false,"用户不存在");
		}
		return new ResponseResult(true, "用户查询成功",user);
	}
}
