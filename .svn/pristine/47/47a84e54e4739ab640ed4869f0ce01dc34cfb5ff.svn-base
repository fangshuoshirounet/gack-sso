package citic.gack.sso.admin.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import citic.gack.sso.admin.dto.SysUserDTO;

public class SecurityManager {
    public static final String ADMIN_ROLE_CODE = "administrators";
    public static final String ACCESS_OPERATION_ID = "1";
    public static final String ACCESS_OPERATION_CODE = "access";

    /**
     * 获取session中的登录用户对象
     * @return 登录用户对象
     */
    public static SysUserDTO getSessionUser() {
    	SysUserDTO sysUser = (SysUserDTO)SecurityUtils.getSubject().getPrincipal();
        return sysUser;
    }

    /**
     * 获取session中的信息
     * @param sessionKey 信息的Key
     * @return session中的信息
     */
    public static Object getSessionAttribute(String sessionKey) {
    	Session session = SecurityUtils.getSubject().getSession();
        return session.getAttribute(sessionKey);
    }

    /**
     * 设置session中的信息
     * @param sessionKey 信息的Key
     * @param sessionObject 信息对象
     */
    public static void setSessionAttribute(String sessionKey, Object sessionObject) {
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(sessionKey, sessionObject);
    }

    /**
     * 删除session中的信息
     * @param sessionKey 信息的Key
     */
    public static void removeSessionAttribute(String sessionKey) {
        Session session = SecurityUtils.getSubject().getSession();
        session.removeAttribute(sessionKey);
    }

    /**
     * 当前登录用户注销登录
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 判断当前用户是否是管理员角色
     * @return 是否是管理员
     */
    public static boolean isSessionUserAdminRole() {
        return SecurityUtils.getSubject().hasRole(ADMIN_ROLE_CODE);
    }
}
