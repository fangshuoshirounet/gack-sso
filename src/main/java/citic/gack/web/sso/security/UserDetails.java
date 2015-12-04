package citic.gack.web.sso.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import citic.gack.web.sso.entity.SecUserDetails;

public class UserDetails  implements org.springframework.security.core.userdetails.UserDetails {
	
	private SecUserDetails secUserDetail;
	
	public void setSecUserDetail(SecUserDetails secUserDetail) {
		this.secUserDetail = secUserDetail;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return secUserDetail.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return secUserDetail.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return secUserDetail.getStatus() <= 0?false:true;
	}

}
