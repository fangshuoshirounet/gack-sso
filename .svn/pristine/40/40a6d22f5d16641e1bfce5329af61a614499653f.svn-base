package citic.gack.sso.admin.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 用于处理用户的权限清单
 */
public class PermissionProcessor {
	/**
	 * 用户拥有的权限
	 */
	private final Set<WildcardPermission> permissions = new HashSet<WildcardPermission>();

	private final Map<String, List<String>> perm = new HashMap<String, List<String>>();

	public void addPermissionLog(String menu, String operation) {
		if (!perm.containsKey(menu)) {
			List list = new ArrayList();
			list.add(operation);
			perm.put(menu, list);
		} else {
			perm.get(menu).add(operation);
		}
	}

	public void removePermissionLog(String menu, String operation) {
		if (perm.containsKey(menu)) {
			perm.get(menu).remove(operation);
			if (perm.get(menu).size() == 0) {
				perm.remove(menu);
			}
		}
	}

	public Map<String, List<String>> getPerm() {
		return perm;
	}

	/**
	 * 增加权限
	 * 
	 * @param stringPermission
	 *            权限字符串
	 */
	public void addPermission(String stringPermission) {
		WildcardPermission wp = new WildcardPermission(stringPermission);
		this.addPermission(wp);
	}

	/**
	 * 增加一组权限
	 * 
	 * @param permissionsSet
	 */
	public void addPermissions(Set<WildcardPermission> permissionsSet) {
		for (WildcardPermission permission : permissionsSet) {
			this.addPermission(permission);
		}
	}

	/**
	 * 增加权限
	 * 
	 * @param wildcardPermission
	 *            权限
	 */
	public void addPermission(WildcardPermission wildcardPermission) {
		boolean hasImplied = false;
		Set<WildcardPermission> set = new HashSet<WildcardPermission>();
		for (WildcardPermission permission : permissions) {
			if (permission.implies(wildcardPermission)) { // 新加入的权限已经被已有权限包含
				hasImplied = true;
				break;
			} else if (wildcardPermission.implies(permission)) { // 新加入的权限包含了已有权限
				set.add(permission);
			}
		}
		if (!hasImplied) {
			permissions.add(wildcardPermission);
			permissions.removeAll(set);// 删除已有权限
		}
	}

	/**
	 * 去除权限
	 * 
	 * @param stringPermission
	 *            权限字符串
	 */
	public void removePermission(String stringPermission) {
		WildcardPermission wp = new WildcardPermission(stringPermission);
		this.removePermission(wp);
	}

	/**
	 * 去除权限
	 * 
	 * @param wildcardPermission
	 *            权限
	 */
	public void removePermission(WildcardPermission wildcardPermission) {
		Set<WildcardPermission> _permissions = new HashSet<WildcardPermission>();
		for (WildcardPermission permission : permissions) {
			if (wildcardPermission.implies(permission)) {
				_permissions.add(permission);
			}
		}
		permissions.removeAll(_permissions);
	}

	/**
	 * 获得用户拥有的权限
	 * 
	 * @return 用户拥有的权限
	 */
	public Set<WildcardPermission> getPermissions() {
		return permissions;
	}
}
