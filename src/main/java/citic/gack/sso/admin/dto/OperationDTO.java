package citic.gack.sso.admin.dto;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysOperation;
import citic.gack.sso.utils.ConstantsUtils;

@SuppressWarnings("serial")
public class OperationDTO extends SysOperation {

	private String stsName; // 状态解释
	private boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getStsName() {
		if (StringUtils.isBlank(stsName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			stsName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "OPERATION.STS." + this.getSts());
		}
		return stsName;
	}

	public void setStsName(String stsName) {
		this.stsName = stsName;
	}
}
