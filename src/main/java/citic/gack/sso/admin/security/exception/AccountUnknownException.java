package citic.gack.sso.admin.security.exception;

import org.apache.shiro.authc.AuthenticationException;

public class AccountUnknownException extends AuthenticationException {
    public AccountUnknownException() {
        super();
    }

    public AccountUnknownException(String message) {
        super(message);
    }

    public AccountUnknownException(Throwable cause) {
        super(cause);
    }

    public AccountUnknownException(String message, Throwable cause) {
        super(message, cause);
    }
}
