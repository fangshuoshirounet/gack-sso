package citic.gack.sso.entity;

import javax.xml.bind.annotation.XmlRootElement;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
@XmlRootElement
public class SysOperation extends BaseModel {
	protected String operationId;
	protected String operationName;
	protected String operationCode; 
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	 

}
