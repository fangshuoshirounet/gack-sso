package citic.gack.sso.entity;

import citic.gack.sso.base.BaseModel;

/**
 * <br>
 * 类 名: 角色申请 <br>
 * 描 述: 操作日志实体类 <br>
 * 作 者: wangjincheng <br>
 * 创 建： 2015年7月31日 <br>
 * 版 本：v1.0.0 <br>
 */
@SuppressWarnings("serial")
public class SysApplyRole extends BaseModel {
	/** 唯一标识 */
	private String id;
	/** 角色Id */
	private String roleId;
	/** 角色名称 */
	private String roleName;
	/** 角色code */
	private String roleCode;
	/** 角色定义 */
	private String roleDesc;
	/** 申请类型 */
	private String applyType;
	/** 申请者id */
	private String applyorId;
	/** 申请者 */
	private String applyor;
	/** 申请日期 */
	private String applyDate;
	/** 审批人id */
	private String auditorId;
	/** 审批人 */
	private String auditor;
	/** 审批状态 */
	private String auditStatus;
	/** 审批意见 */
	private String auditOpintion;
	/** 审批日期 */
	private String auditDate;
	/** 变更类型 */
	private String changeType;

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/** get */
	public String getId() {
		return id;
	}

	/** set */
	public void setId(String id) {
		this.id = id;
	}

	/** get */
	public String getRoleId() {
		return roleId;
	}

	/** set */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/** get */
	public String getRoleName() {
		return roleName;
	}

	/** set */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/** get */
	public String getRoleCode() {
		return roleCode;
	}

	/** set */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/** get */
	public String getRoleDesc() {
		return roleDesc;
	}

	/** set */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/** get */
	public String getApplyType() {
		return applyType;
	}

	/** set */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	/** get */
	public String getApplyorId() {
		return applyorId;
	}

	/** set */
	public void setApplyorId(String applyorId) {
		this.applyorId = applyorId;
	}

	/** get */
	public String getApplyor() {
		return applyor;
	}

	/** set */
	public void setApplyor(String applyor) {
		this.applyor = applyor;
	}

	/** get */
	public String getApplyDate() {
		return applyDate;
	}

	/** set */
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	/** get */
	public String getAuditorId() {
		return auditorId;
	}

	/** set */
	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	/** get */
	public String getAuditor() {
		return auditor;
	}

	/** set */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	/** get */
	public String getAuditOpintion() {
		return auditOpintion;
	}

	/** set */
	public void setAuditOpintion(String auditOpintion) {
		this.auditOpintion = auditOpintion;
	}

	/** get */
	public String getAuditDate() {
		return auditDate;
	}

	/** set */
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

}
