package citic.gack.sso.web.oauth2;

import org.springframework.security.oauth2.provider.code.AuthorizationRequestHolder;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

public class AuthorizationCodeServices extends RandomValueAuthorizationCodeServices{

	@Override
	protected void store(String code, AuthorizationRequestHolder authentication) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AuthorizationRequestHolder remove(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
