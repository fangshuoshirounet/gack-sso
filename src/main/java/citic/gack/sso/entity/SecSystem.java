package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

/**
 * 系统（模块）
 * @author hc-3020-i3
 *
 */
public class SecSystem extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int systemId;
	private String code;
	private String name;
	private String description;
	public int getSystemId() {
		return systemId;
	}
	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
