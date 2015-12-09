package citic.gack.sso.admin.dto;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysArea;
import citic.gack.sso.utils.ConstantsUtils;

/**
 * 系统区域(AreaDTO)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-6上午9:45:01
 */
@SuppressWarnings("serial")
public class AreaDTO extends SysArea {
	private String parentName;// 上级区域名称，不操作数据库，用来页面显示显示
	private String stsName; // 状态解释
	private String iscenterName; // 是否中心区域解释

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getStsName() {
		if (StringUtils.isBlank(stsName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			stsName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "AREA.STS." + this.getSts());
		}
		return stsName;
	}

	public void setStsName(String stsName) {
		this.stsName = stsName;
	}

	public String getIscenterName() {
		if (StringUtils.isBlank(iscenterName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			iscenterName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "AREA.IS_CENTER." + isCenter);
		}
		return iscenterName;
	}

	public void setIscenterName(String iscenterName) {
		this.iscenterName = iscenterName;
	}

}
