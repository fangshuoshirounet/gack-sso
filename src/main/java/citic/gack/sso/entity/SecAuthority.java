package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

/**
 * 权限
 * @author hc-3020-i3
 *
 */
public class SecAuthority extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int authorityId;
	private int resourceId;
	private String name;
	private String code;
	private String description;
	public int getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(int authorityId) {
		this.authorityId = authorityId;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
