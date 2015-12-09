package citic.gack.sso.base.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * 运行时异常底层实现类
 * 
 * @author jianglinzeng
 * 
 * @date 2015-4-9 10:24:23
 * 
 */
public class SysException extends AppRuntimeException {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SysException.class);

	/**
	 * 错误号
	 */
	String errId;

	/**
	 * 错误信息，当前错误的信息。
	 */
	String errMsg;

	/**
	 * 原错误信息。捕捉到原发错误时候，记录下原发错误的信息。
	 */
	String errMsgOri;

	/**
	 * 原发错误对象。
	 */
	Exception errOri;

	public String getErrId() {
		return this.errId;
	}

	public void setErrId(String errId) {
		this.errId = errId;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrMsgOri() {
		return this.errMsgOri;
	}

	public void setErrMsgOri(String errMsgOri) {
		this.errMsgOri = errMsgOri;
	}

	public Exception getErrOri() {
		return this.errOri;
	}

	public void setErrOri(Exception errOri) {
		this.errOri = errOri;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	/**
	 * SysException的跟踪调试信息，一般包括类名和方法名。
	 */
	private String trace = "";

	public SysException() {
	}

	public SysException(String errId, String errOwnMsg, Exception oriEx) {

		super(oriEx);
		this.errId = errId;
		this.errMsg = errOwnMsg;
		this.errOri = oriEx;

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(os);
		this.errOri.printStackTrace(p);
		this.errMsgOri = os.toString().trim();
		this.writeSysException();
	}

	/**
	 * @param message
	 */
	public SysException(String message) {
		super(message);
	}
	
	public SysException(String errId, String errOwnMsg) {

		this.errId = errId;
		this.errMsg = errOwnMsg;

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(os);
		this.errOri.printStackTrace(p);
		this.errMsgOri = os.toString().trim();
		this.writeSysException();
	}

	public SysException(String errId, Exception oriEx) {
		super(oriEx);

		this.errId = errId;
		this.errOri = oriEx;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(os);
		this.errOri.printStackTrace(p);
		this.errMsgOri = os.toString().trim();
		this.writeSysException();
	}

	/**
	 * 添加跟踪信息，只有捕捉到SysException的时候才调用该函数。
	 * 
	 * @param msg
	 *            跟踪信息
	 */
	public void appendMsg(String msg) {
		trace += msg;
	}

	/**
	 * 返回跟踪信息
	 * 
	 * @return String
	 */
	public String getTrace() {
		return trace;
	}

	@Override
	public String getMessage() {
		String message = "";
		if (errMsgOri.length() > 100) {
			message = errMsg + "[" + errMsgOri.substring(0, 100) + "]";
		} else {
			message = errMsg + "[" + errMsgOri + "]";
		}
		return message;
	}

	/**
	 * 打印原发错误信息
	 */
	public void printDebug() {
		this.errOri.printStackTrace();
	}

	private void writeSysException() {
		logger.error("id:" + this.errId);
		logger.error("msg:" + this.errMsg);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream p = new PrintStream(os);
		this.errOri.printStackTrace(p);
		logger.error(os.toString());
	}
}