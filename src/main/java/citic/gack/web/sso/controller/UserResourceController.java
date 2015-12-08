package citic.gack.web.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import citic.gack.web.sso.base.BaseController;
import citic.gack.web.sso.base.ResponseResult;
import citic.gack.web.sso.dto.UserDTO;
import citic.gack.web.sso.entity.SecUser;
import citic.gack.web.sso.security.SecUserDetails;
import citic.gack.web.sso.service.SecUserService;

@Controller
@RequestMapping("/users")
public class UserResourceController extends BaseController<UserDTO>{

	@Autowired
	private SecUserService secUserDetailsService;
	
	@RequestMapping("/{id}")
	public @ResponseBody ResponseResult getUser(@PathVariable("id") int userId){
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userId);
		SecUser user = secUserDetailsService.queryBean(userDTO);
		if(null == user){
			return new ResponseResult(false,"用户不存在");
		}
		return new ResponseResult(true, "用户查询成功",user);
	}
	
	@RequestMapping("/me")
	public @ResponseBody ResponseResult me(){
		SecUserDetails user = (SecUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(user.getUserId());
		SecUser resultBean = secUserDetailsService.queryBean(userDTO);
		return new ResponseResult(true, "信息获取成功",resultBean);
	}
}
