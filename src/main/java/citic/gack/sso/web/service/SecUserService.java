package citic.gack.sso.web.service;

import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.entity.RegstUser;
import citic.gack.sso.web.dto.UserDTO;

public interface SecUserService{

	public UserDTO queryBean(UserDTO userDTO) throws SysException;
	
	public UserDTO insert(UserDTO userDTO) throws SysException;
	
	public RegstUser queryByEmailOrPhoneNum(String loginName) throws SysException;
}
