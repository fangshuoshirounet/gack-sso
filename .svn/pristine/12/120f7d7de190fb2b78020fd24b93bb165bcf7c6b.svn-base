package citic.gack.sso.admin.dto;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysOrganization;
import citic.gack.sso.utils.ConstantsUtils;

/**
 * 组织机构(OrganizationMVO)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-7上午9:21:25
 */
@SuppressWarnings("serial")
public class OrganizationDTO extends SysOrganization {
	private String parentName;// 上级组织机构名称
	private String orgTypeName;// 组织结构类型名称
	private String planName;// 方案名称

	private String stsName;// 组织结构类型名称
	private String showFlagName;// 组织结构类型名称

	public String getParentName() {
		return parentName;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getOrgTypeName() {
		return orgTypeName;
	}

	public String getShowFlagName() {
		if (StringUtils.isBlank(showFlagName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			showFlagName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "ORGANIZATION.SHOW_FLAG." + this.getShowFlag());
		}
		return showFlagName;
	}

	public void setShowFlagName(String showFlagName) {
		this.showFlagName = showFlagName;
	}

	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}

	public String getStsName() {
		if (StringUtils.isBlank(stsName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			stsName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "ORGANIZATION.STS." + this.getSts());
		}
		return stsName;
	}

	public void setStsName(String stsName) {
		this.stsName = stsName;
	}
}
