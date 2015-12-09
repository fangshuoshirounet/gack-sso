package citic.gack.sso.admin.security.exception;

import org.apache.shiro.authc.AuthenticationException;

public class AccountNotFoundException extends AuthenticationException {
    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
