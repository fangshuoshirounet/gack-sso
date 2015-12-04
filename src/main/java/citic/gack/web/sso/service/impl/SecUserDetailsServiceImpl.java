package citic.gack.web.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.web.sso.entity.SecUserDetails;
import citic.gack.web.sso.mapper.SecUserDetailsMapper;
import citic.gack.web.sso.service.SecUserDetailService;

@Service("secUserDetailsServiceImpl")
public class SecUserDetailsServiceImpl implements SecUserDetailService {

	@Autowired
	private SecUserDetailsMapper secUserDetailsMapper;
	
	@Override
	public SecUserDetails queryBean(SecUserDetails userDetails) {
		return secUserDetailsMapper.queryById(userDetails);
	}

}
