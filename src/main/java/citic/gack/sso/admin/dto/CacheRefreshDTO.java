package citic.gack.sso.admin.dto;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
public class CacheRefreshDTO extends BaseModel {
	private String cacheName;// 缓存名字
	private String operation;// 操作

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
