package citic.gack.sso.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import citic.gack.sso.entity.RegstUser;
import citic.gack.sso.web.service.SecUserService;

public class SecUserDetailsService implements UserDetailsService {

	@Autowired
	private SecUserService secUserService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RegstUser user =  secUserService.queryByEmailOrPhoneNum(username);
		if(null != user){
			return new SecUserDetails(user,"USERMANAGE:USER:ME");
		}
		return null;
	}

}
