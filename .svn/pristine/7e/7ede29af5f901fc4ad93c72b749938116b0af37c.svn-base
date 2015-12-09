package citic.gack.sso.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import citic.gack.sso.entity.RegstUser;

public class SecUserDetails  implements org.springframework.security.core.userdetails.UserDetails {
	
	private RegstUser secUserDetail;
	private List<GrantedAuthority> authorities;
	
	public void setSecUserDetail(RegstUser secUserDetail) {
		this.secUserDetail = secUserDetail;
	}
	
	public SecUserDetails(RegstUser secUserDetail,List<GrantedAuthority> authorities) {
		this.secUserDetail = secUserDetail;
		this.authorities = authorities;
	}
	
	public SecUserDetails(RegstUser secUserDetail,String authorities) {
		this.secUserDetail = secUserDetail;
		if(null != authorities && authorities.trim().length() > 0){
			this.authorities = new ArrayList<GrantedAuthority>();
			for(String auth : authorities.split(",")){
				SimpleGrantedAuthority sga = new SimpleGrantedAuthority(auth);
				this.authorities.add(sga);
			}
		}
		
	}

	public SecUserDetails() {
	}
	public int getUserId() {
		return null == secUserDetail ? 0 : secUserDetail.getUserId();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return secUserDetail.getStatus() <= 0?false:true;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
