package citic.gack.sso.base.exception;

/**
 * 异常底层工具类
 * 
 * @author jianglinzeng
 * 
 * @date 2015-4-9 10:19:23
 * 
 */
class ExceptionUtils {
	/**
	 * 封装异常消息
	 * 
	 * @param message异常信息
	 * 
	 * @param cause异常类
	 * 
	 * @return 异常信息
	 */

	static String buildMessage(String message, Throwable cause) {
		if (cause != null) {
			StringBuilder buf = new StringBuilder();
			if (message != null) {
				buf.append(message).append("; ");
			}
			buf.append("nested exception is ").append(cause);
			return buf.toString();
		} else {
			return message;
		}
	}

	/**
	 * 获取异常原因
	 * 
	 * @param ex异常类
	 * 
	 * @param nullable是否递归获取异常类
	 * 
	 * @return 异常类
	 */

	static Throwable getRootCause(Throwable ex, boolean nullable) {
		Throwable rootCause = nullable ? null : ex;
		Throwable cause = ex.getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}

	/**
	 * 判断是否运行异常还是非运行异常
	 * 
	 * @param ex异常类
	 * 
	 * @param exType比较异常类信息
	 * 
	 * @return 判断结果
	 */

	static boolean contains(Throwable ex, Class<? extends Throwable> exType) {
		if (exType == null) {
			return false;
		}
		if (exType.isInstance(ex)) {
			return true;
		}
		Throwable cause = ex.getCause();
		if (cause == ex) {
			return false;
		}
		if (cause instanceof AppRuntimeException) {
			return ((AppRuntimeException) cause).contains(exType);
		} else if (cause instanceof AppCheckedException) {
			return ((AppCheckedException) cause).contains(exType);
		} else {
			while (cause != null) {
				if (exType.isInstance(cause)) {
					return true;
				}
				if (cause.getCause() == cause) {
					break;
				}
				cause = cause.getCause();
			}
			return false;
		}
	}
}
