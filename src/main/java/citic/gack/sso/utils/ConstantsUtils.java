package citic.gack.sso.utils;

/**
 * 短信平台常量类
 * 
 * @author jianglinzeng
 * 
 * @date 2015-4-9 10:40:25
 * 
 */
public class ConstantsUtils {

	public static final String CACHE_SYSTEM = "cprp";
	// 数据生效状态 A:生效 ，P:注销
	public static final String STS_A = "A";
	public static final String STS_P = "P";

	// 用户变更类型 U 修改 A 添加
	public static final String CHANGE_TYPE_U = "U";
	public static final String CHANGE_TYPE_A = "A";

	// 审批状态
	public static final String AUDIT_STATUS_A = "A";
	public static final String AUDIT_STATUS_R = "R";
	public static final String AUDIT_STATUS_I = "I";

	// 用户申请类型 U 添加 修改用户申请 R 用户添加修改角色申请
	public static final String APPLY_USER_TYPE_U = "U";
	public static final String APPLY_USER_TYPE_R = "R";
	// 角色申请类型 R 角色申请 P 角色授权申请
	public static final String APPLY_ROLE_TYPE_R = "R";
	public static final String APPLY_ROLE_TYPE_P = "P";
}
