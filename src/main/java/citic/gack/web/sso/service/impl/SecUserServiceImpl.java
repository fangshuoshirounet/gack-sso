package citic.gack.web.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.web.sso.dto.UserDTO;
import citic.gack.web.sso.entity.SecUser;
import citic.gack.web.sso.mapper.SecUserMapper;
import citic.gack.web.sso.service.SecUserService;

@Service("secUserDetailsServiceImpl")
public class SecUserServiceImpl implements SecUserService {

	@Autowired
	private SecUserMapper secUserMapper;
	
	@Override
	public SecUser queryBean(UserDTO userDTO) {
		return secUserMapper.queryById(userDTO.getEntity());
	}

}
