package citic.gack.sso.base.exception;

/**
 * 运行时异常底层抽象类
 * 
 * @author jianglinzeng
 * 
 * @date 2015-4-9 10:23:33
 * 
 */
@SuppressWarnings("serial")
public abstract class AppRuntimeException extends RuntimeException {
	public AppRuntimeException() {
		super();
	}

	public AppRuntimeException(String msg) {
		super(msg);
	}

	public AppRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
	}

	public String getMessage() {
		return ExceptionUtils.buildMessage(super.getMessage(), getCause());
	}

	public Throwable getRootCause() {
		return ExceptionUtils.getRootCause(this, true);
	}

	public Throwable getMostSpecificCause() {
		return ExceptionUtils.getRootCause(this, false);
	}

	public boolean contains(Class<? extends Throwable> exType) {
		return ExceptionUtils.contains(this, exType);
	}
}
