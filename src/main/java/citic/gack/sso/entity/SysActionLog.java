package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

/**
 * 操作日志实体类
 * @author hc-3020-i3
 *
 */
@SuppressWarnings("serial")
public class SysActionLog extends BaseModel {
	protected String actionLogId;
	protected String actionText;
	protected String actionTime;
	protected String actionType; 
	protected String menuId;
	protected String menuUrl;
	protected String sysText;
	protected String sysUserId;
	public String getActionLogId() {
		return actionLogId;
	}
	public void setActionLogId(String actionLogId) {
		this.actionLogId = actionLogId;
	}
	public String getActionText() {
		return actionText;
	}
	public void setActionText(String actionText) {
		this.actionText = actionText;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
 
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getSysText() {
		return sysText;
	}
	public void setSysText(String sysText) {
		this.sysText = sysText;
	}
	public String getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	
}
