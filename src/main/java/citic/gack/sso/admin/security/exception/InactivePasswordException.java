package citic.gack.sso.admin.security.exception;

import org.apache.shiro.authc.AuthenticationException;

public class InactivePasswordException extends AuthenticationException {

	public InactivePasswordException() {
		super();
	}

	public InactivePasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public InactivePasswordException(String message) {
		super(message);
	}

	public InactivePasswordException(Throwable cause) {
		super(cause);
	}

}
