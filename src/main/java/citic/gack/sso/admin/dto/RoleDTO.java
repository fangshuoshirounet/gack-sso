package citic.gack.sso.admin.dto;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysRole;
import citic.gack.sso.utils.ConstantsUtils;

@SuppressWarnings("serial")
public class RoleDTO extends SysRole {
	private String stsName; // 状态解释
	private String flagType; // 变更类型
	private String applyUserId; // 申请id

	public String getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getFlagType() {
		return flagType;
	}

	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	public String getStsName() {
		if (StringUtils.isBlank(stsName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			stsName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "ROLE.STS." + this.getSts());
		}
		return stsName;
	}

	public void setStsName(String stsName) {
		this.stsName = stsName;
	}
}
