package citic.gack.sso.admin.dto;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysConfig;
import citic.gack.sso.utils.ConstantsUtils;

@SuppressWarnings("serial")
public class SysConfigDTO extends SysConfig {

	private String type;

	public String getType() {
		if (StringUtils.isBlank(type)) {
			CacheManager cacheManager = CacheManager.getInstance();
			type = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "SYS_CONFIG.CONFIG_TYPE." + configType);
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}