package citic.gack.sso.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.RegstUser;
import citic.gack.sso.mapper.RegstUserMapper;
import citic.gack.sso.web.dto.UserDTO;
import citic.gack.sso.web.service.SecUserService;

@Service("secUserDetailsServiceImpl")
public class SecUserServiceImpl implements SecUserService {

	@Autowired
	private RegstUserMapper secUserMapper;
	
	@Override
	public UserDTO queryBean(UserDTO userDTO)  throws SysException{
		return new UserDTO(secUserMapper.queryById(userDTO.getEntity()));
	}

	@Override
	public UserDTO insert(UserDTO userDTO)  throws SysException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegstUser queryByEmailOrPhoneNum(String loginName) throws SysException {
		return secUserMapper.queryByEmailOrPhoneNum(loginName);
	}
}
