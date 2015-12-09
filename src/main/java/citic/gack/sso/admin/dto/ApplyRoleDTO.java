package citic.gack.sso.admin.dto;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysApplyRole;
import citic.gack.sso.utils.ConstantsUtils;

@SuppressWarnings("serial")
public class ApplyRoleDTO extends SysApplyRole {

	/**
	 * 申请类型名字
	 */

	private String applyTypeName;

	/**
	 * 变更类型名字
	 */

	private String changeTypeName;

	/** 历史角色名称 */
	private String roleNameOld;
	/** 历史角色code */
	private String roleCodeOld;
	/** 历史角色定义 */
	private String roleDescOld;
	/**
	 * 列表类型
	 */
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRoleNameOld() {
		return roleNameOld;
	}

	public void setRoleNameOld(String roleNameOld) {
		this.roleNameOld = roleNameOld;
	}

	public String getRoleCodeOld() {
		return roleCodeOld;
	}

	public void setRoleCodeOld(String roleCodeOld) {
		this.roleCodeOld = roleCodeOld;
	}

	public String getRoleDescOld() {
		return roleDescOld;
	}

	public void setRoleDescOld(String roleDescOld) {
		this.roleDescOld = roleDescOld;
	}

	public String getChangeTypeName() {

		if (StringUtils.isBlank(changeTypeName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			changeTypeName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "APPLY_ROLE.CHANGE_TYPE." + this.getChangeType());
		}
		return changeTypeName;

	}

	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}

	/**
	 * 复核状态名字
	 */
	private String auditStatusName;

	public String getAuditStatusName() {
		if (StringUtils.isBlank(auditStatusName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			auditStatusName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "PUBLIC_CI_ASSIST_APPLY.AUDIT_STATUS." + this.getAuditStatus());
		}
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public String getApplyTypeName() {
		if (StringUtils.isBlank(applyTypeName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			applyTypeName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "APPLY_ROLE.APPLY_TYPE." + this.getApplyType());
		}
		return applyTypeName;

	}

	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}

}
