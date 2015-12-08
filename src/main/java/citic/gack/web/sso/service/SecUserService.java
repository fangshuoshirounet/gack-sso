package citic.gack.web.sso.service;

import citic.gack.web.sso.dto.UserDTO;
import citic.gack.web.sso.entity.SecUser;

public interface SecUserService{

	public SecUser queryBean(UserDTO userDTO);
}
