package citic.gack.sso.base.exception;

/**
 * 非运行时异常底层抽象类
 * 
 * @author jianglinzeng
 * 
 * @date 2015-4-9 10:19:23
 * 
 */
@SuppressWarnings("serial")
public abstract class AppCheckedException extends Exception {
	public AppCheckedException() {
		super();
	}

	public AppCheckedException(String msg) {
		super(msg);
	}

	public AppCheckedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AppCheckedException(Throwable cause) {
		super(cause);
	}

	@Override
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
