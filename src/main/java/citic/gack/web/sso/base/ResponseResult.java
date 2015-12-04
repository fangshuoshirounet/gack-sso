package citic.gack.web.sso.base;

public class ResponseResult {

	private boolean status;
	private String message;
	private Object data;
	
	public ResponseResult(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public ResponseResult(boolean status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
