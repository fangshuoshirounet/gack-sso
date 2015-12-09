package citic.gack.sso.admin.dto;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.entity.SysApplyUser;
import citic.gack.sso.entity.SysMenuCatalog;
import citic.gack.sso.utils.ConstantsUtils;

@SuppressWarnings("serial")
public class ApplyUserDTO extends SysApplyUser {

	/**
	 * 组织机构名称
	 */
	private String orgName;
	/**
	 * 历史组织机构名称
	 */
	private String orgNameOld;

	/**
	 * 登录对象可访问的菜单目录列表
	 */
	private List<SysMenuCatalog> sysMenuCatalog;

	/**
	 * 登录对象可访问的菜单列表
	 */
	private List<Menu> menu;

	/**
	 * 用户角色名称
	 */
	private String roleName;

	/**
	 * 职责名称
	 */
	private String dutyName;
	/**
	 * 历史职责名称
	 */
	private String dutyNameOld;

	/**
	 * 历史手机
	 */
	protected String mobileOld;
	/**
	 * 历史家庭电话
	 */
	protected String homePhoneOld;

	/**
	 * 历史邮箱
	 */
	protected String emailOld;
	/**
	 * 历史生日日期
	 */
	protected String birthdayOld;

	/**
	 * 历史身份证号
	 */
	protected String idNumberOld;

	/**
	 * 用户角色Id
	 */
	private String roleId;

	private String initPwdFlagName;

	/**
	 * 性别
	 */
	private String sexName;
	/**
	 * 列表类型
	 */
	private String type;

	/**
	 * 变更类型名字
	 */

	private String changeTypeName;

	/**
	 * 申请类型名字
	 */

	private String applyTypeName;
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

	public String getOrgNameOld() {
		return orgNameOld;
	}

	public void setOrgNameOld(String orgNameOld) {
		this.orgNameOld = orgNameOld;
	}

	public String getDutyNameOld() {
		return dutyNameOld;
	}

	public void setDutyNameOld(String dutyNameOld) {
		this.dutyNameOld = dutyNameOld;
	}

	public String getMobileOld() {
		return mobileOld;
	}

	public void setMobileOld(String mobileOld) {
		this.mobileOld = mobileOld;
	}

	public String getHomePhoneOld() {
		return homePhoneOld;
	}

	public void setHomePhoneOld(String homePhoneOld) {
		this.homePhoneOld = homePhoneOld;
	}

	public String getEmailOld() {
		return emailOld;
	}

	public void setEmailOld(String emailOld) {
		this.emailOld = emailOld;
	}

	public String getBirthdayOld() {
		return birthdayOld;
	}

	public void setBirthdayOld(String birthdayOld) {
		this.birthdayOld = birthdayOld;
	}

	public String getIdNumberOld() {
		return idNumberOld;
	}

	public void setIdNumberOld(String idNumberOld) {
		this.idNumberOld = idNumberOld;
	}

	public String getChangeTypeName() {
		if (StringUtils.isBlank(changeTypeName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			changeTypeName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "APPLY_USER.CHANGE_TYPE." + this.getChangeType());
		}
		return changeTypeName;
	}

	public void setChangeTypeName(String changeTypeName) {
		this.changeTypeName = changeTypeName;
	}

	public String getApplyTypeName() {
		if (StringUtils.isBlank(applyTypeName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			applyTypeName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "APPLY_USER.APPLY_TYPE." + this.getApplyType());
		}
		return applyTypeName;

	}

	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getSexName() {
		if (StringUtils.isBlank(sexName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			sexName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "SYS_USER.SEX." + this.getSex());
		}
		return sexName;
	}

	public String getInitPwdFlagName() {
		if (StringUtils.isBlank(initPwdFlagName)) {
			CacheManager cacheManager = CacheManager.getInstance();
			initPwdFlagName = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.enumerate", "SYS_USER.INIT_PWD_FLAG" + initPwdFlag);
		}
		return initPwdFlagName;
	}

	public void setInitPwdFlagName(String initPwdFlagName) {
		this.initPwdFlagName = initPwdFlagName;
	}

	private final Map<String, OperatePermissionDTO> current_user_menu_maps = new HashMap<String, OperatePermissionDTO>();
	private final Map<String, List<String>> current_user_menu_operations = new HashMap<String, List<String>>();
	private final Map<String, OperatePermissionDTO> current_user_menucatalog_maps = new HashMap<String, OperatePermissionDTO>();
	private final Map<String, List<String>> current_user_menucatalog_operations = new HashMap<String, List<String>>();

	private final Set<String> setPerm = new HashSet<String>();
	private final Set<String> setCataPerm = new HashSet<String>();

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<SysMenuCatalog> getSysMenuCatalog() {
		return sysMenuCatalog;
	}

	public void setSysMenuCatalog(List<SysMenuCatalog> sysMenuCatalog) {
		this.sysMenuCatalog = sysMenuCatalog;
	}

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		if (StringUtils.isNotBlank(name)) {
			return name;
		} else {
			return loginName;
		}
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public Set<String> getSetPerm() {
		return setPerm;
	}

	public Set<String> getSetCataPerm() {
		return setCataPerm;
	}

	public void addSetPerm(OperatePermissionDTO mvo) {
		StringBuilder str = new StringBuilder();
		str.append(mvo.getMenuId()).append("-").append(mvo.getOperationCode()).append("-");
		str.append(mvo.getMenuName()).append("-").append(mvo.getMenuUrl()).append("-");
		str.append(mvo.getOperationName());
		if (current_user_menu_maps.get(mvo.getMenuId()) == null) {
			current_user_menu_maps.put(mvo.getMenuId(), mvo);
		}
		List<String> list = current_user_menu_operations.get(mvo.getMenuId());
		if (list == null) {
			list = new ArrayList<String>();
			list.add(mvo.getOperationCode());
			current_user_menu_operations.put(mvo.getMenuId(), list);
		} else {
			if (!list.contains(mvo.getOperationCode())) {
				list.add(mvo.getOperationCode());
				current_user_menu_operations.put(mvo.getMenuId(), list);
			}
		}
		setPerm.add(str.toString());
	}

	public void removeSetPerm(OperatePermissionDTO mvo) {
		StringBuilder str = new StringBuilder();
		str.append(mvo.getMenuId()).append("-").append(mvo.getOperationCode()).append("-");
		str.append(mvo.getMenuName()).append("-").append(mvo.getMenuUrl()).append("-");
		str.append(mvo.getOperationName());

		if (current_user_menu_maps.get(mvo.getMenuId()) != null) {
			current_user_menu_maps.remove(mvo.getMenuId());
		}
		List<String> list = current_user_menu_operations.get(mvo.getMenuId());
		if (list != null) {
			list.remove(mvo.getOperationCode());
			current_user_menu_operations.put(mvo.getMenuId(), list);
		}
		setPerm.remove(str.toString());
	}

	public void addSetCataPerm(OperatePermissionDTO mvo) {
		StringBuilder str = new StringBuilder();
		if ("*".equals(mvo.getOperationCode())) {
			str.append(mvo.getMenuCatalogId()).append("-A");
			mvo.setOperationCode("A");
		} else {
			str.append(mvo.getMenuCatalogId()).append("-").append(mvo.getOperationCode());
		}
		if (current_user_menucatalog_maps.get(mvo.getMenuCatalogId()) == null) {
			current_user_menucatalog_maps.put(mvo.getMenuCatalogId(), mvo);
		}
		List<String> list = current_user_menucatalog_operations.get(mvo.getMenuCatalogId());
		if (list == null) {
			list = new ArrayList<String>();
			list.add(mvo.getOperationCode());
			current_user_menucatalog_operations.put(mvo.getMenuCatalogId(), list);
		} else {
			if (!list.contains(mvo.getOperationCode())) {
				list.add(mvo.getOperationCode());
				current_user_menucatalog_operations.put(mvo.getMenuCatalogId(), list);
			}
		}
		setCataPerm.add(str.toString());
	}

	public void removeSetCataPerm(OperatePermissionDTO mvo) {
		StringBuilder str = new StringBuilder();
		if ("*".equals(mvo.getOperationCode())) {
			str.append(mvo.getMenuCatalogId()).append("-A");
			mvo.setOperationCode("A");
		} else {
			str.append(mvo.getMenuCatalogId()).append("-").append(mvo.getOperationCode());
		}
		if (current_user_menucatalog_maps.get(mvo.getMenuCatalogId()) != null) {
			current_user_menucatalog_maps.remove(mvo.getMenuCatalogId());
		}
		List<String> list = current_user_menucatalog_operations.get(mvo.getMenuCatalogId());
		if (list != null) {
			list.remove(mvo.getOperationCode());
			current_user_menucatalog_operations.put(mvo.getMenuCatalogId(), list);
		}
		setCataPerm.remove(str.toString());
	}

	public Map<String, OperatePermissionDTO> getCurrent_user_menu_maps() {
		return current_user_menu_maps;
	}

	public Map<String, List<String>> getCurrent_user_menu_operations() {
		return current_user_menu_operations;
	}

	public Map<String, OperatePermissionDTO> getCurrent_user_menucatalog_maps() {
		return current_user_menucatalog_maps;
	}

	public Map<String, List<String>> getCurrent_user_menucatalog_operations() {
		return current_user_menucatalog_operations;
	}

}
