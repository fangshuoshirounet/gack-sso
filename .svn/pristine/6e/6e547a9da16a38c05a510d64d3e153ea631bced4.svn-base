package citic.gack.sso.admin.security;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import citic.gack.sso.admin.dto.SysUserDTO;
import citic.gack.sso.admin.security.exception.AccountNotFoundException;
import citic.gack.sso.admin.security.exception.AccountStatusException;
import citic.gack.sso.admin.security.exception.AccountUnknownException;
import citic.gack.sso.admin.security.exception.InactivePasswordException;
import citic.gack.sso.admin.security.exception.IncorrectCaptchaException;
import citic.gack.sso.admin.security.exception.IncorrectPasswordException;
import citic.gack.sso.admin.service.SecurityService;
import citic.gack.sso.base.cache.CacheManager;
import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.utils.ConstantsUtils;

/**
 * 安全管理认证及鉴权的Realm
 */
public class SecurityRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(SecurityRealm.class);

	// @Autowired
	// private ISecurityDelegate securityDelegate;
	// @Autowired
	// private ILoginLogDelegate loginLogDelegate;
	@Autowired
	private SecurityService securityService;
	/**
	 * 是否启用验证码
	 */
	private boolean isCaptchaEnable = false;

	/**
	 * 登录错误的异常信息：验证码错误异常
	 */
	public static final String INCORRECT_CAPTCHA_EXCEPTION_MSG = "验证码错误！";

	/**
	 * 登录错误的异常信息：密码错误异常
	 */
	public static final String INCORRECT_PASSWORD_USERNAME_EXCEPTION_MSG = "用户名或密码错误！";

	/**
	 * 登录错误的异常信息：密码过期异常
	 */
	public static final String INACTIVE_PASSWORD_EXCEPTION_MSG = "密码已过期，请联系管理员！";

	/**
	 * 登录错误的异常信息：未知异常
	 */
	public static final String ACCOUNT_UNKNOWN_EXCEPTION_MSG = "未知系统异常！";

	/**
	 * 用户、员工正常状态取值
	 */
	private static final String STATUS_NORMAL = "A";

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserDTO sysUser = (SysUserDTO) principals.fromRealm(getName()).iterator().next();
		if (sysUser == null) {
			logger.info("获取鉴权信息：无法获得登录用户信息");
			return null;
		}
		try {
			logger.info("获取鉴权信息：开始获取登录用户【{}】鉴权信息", sysUser.getLoginName());
			SimpleAuthorizationInfo info = securityService.doGetAuthorizationInfo(sysUser);
			logger.info("获取鉴权信息：成功获取登录用户【{}】鉴权信息", sysUser.getLoginName());
			return info;
		} catch (SysException e) {
			e.printStackTrace();
			logger.error("获取鉴权信息：获取登录用户【{}】鉴权信息时发生异常：{}", sysUser.getLoginName(), e.getErrMsg());
			throw new AccountUnknownException(e.getErrMsg());
		}
	}

	/**
	 * 获得用户认证信息
	 * 
	 * @param authenticationToken
	 *            用户登录的token
	 * @return 用户认证信息
	 * @throws AuthenticationException
	 *             用户认证异常
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		SecurityToken token = (SecurityToken) authenticationToken;
		Session session = SecurityUtils.getSubject().getSession();

		logger.debug("获取认证信息：开始获取登录用户【{}】认证信息", token.getUsername());
		/**
		 * 验证码校验
		 */
		if (isCaptchaEnable) {
			if (!StringUtils.equalsIgnoreCase(token.getCaptcha(), token.getCaptchaSession())) {
				throw new IncorrectCaptchaException(INCORRECT_CAPTCHA_EXCEPTION_MSG);
			}
			logger.debug("获取认证信息：登录用户【{}】通过验证码校验", token.getUsername());
		}

		/**
		 * 登录帐户信息校验（校验帐户是否存在，以及校验帐户状态）
		 */
		logger.debug("获取认证信息：开始数据库验证登录用户【{}】信息", token.getUsername());
		SysUserDTO sysUser = null;
		try {
			sysUser = securityService.doGetAuthenticationInfo(token.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取认证信息：获取登录用户【{}】鉴权信息时发生异常：{}", token.getUsername(), e.getMessage());
			throw new AccountUnknownException(ACCOUNT_UNKNOWN_EXCEPTION_MSG);
		}
		if (sysUser == null) {
			logger.debug("获取认证信息：登录用户【{}】不存在", token.getUsername());
			throw new AccountNotFoundException(INCORRECT_PASSWORD_USERNAME_EXCEPTION_MSG);
		}
		if (!StringUtils.equals(STATUS_NORMAL, sysUser.getSts()) || !StringUtils.equals(STATUS_NORMAL, sysUser.getSts())) {
			logger.debug("获取认证信息：登录用户【{}】状态异常", token.getUsername());
			throw new AccountStatusException(INCORRECT_PASSWORD_USERNAME_EXCEPTION_MSG);
		}
		if (!StringUtils.equals(String.copyValueOf(token.getPassword()), sysUser.getPassword())) {
			logger.debug("获取认证信息：登录用户【{}】密码错误", token.getUsername());
			throw new IncorrectPasswordException(INCORRECT_PASSWORD_USERNAME_EXCEPTION_MSG);
		}
		CacheManager cacheManager = CacheManager.getInstance();
		String inactiveFlag = (String) cacheManager.get(ConstantsUtils.CACHE_SYSTEM + ".table.cache.idvalue.sysconfig", "INACTIVEL_FLAG");
		if (StringUtils.isNotBlank(inactiveFlag) && inactiveFlag.equals("Y")) {
			if (sysUser.getPwdInactiveTime() != null) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				try {
					Date startDate = df.parse(sysUser.getPwdInactiveTime());
					Date endDate = df.parse(df.format(new Date()));
					long day = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);
					session.setAttribute("inactiveDays", day);

					if (df.parse(sysUser.getPwdInactiveTime()).getTime() < df.parse(df.format(new Date())).getTime()) {
						throw new InactivePasswordException(INACTIVE_PASSWORD_EXCEPTION_MSG);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		logger.debug("获取认证信息：完成数据库验证登录用户【{}】信息", token.getUsername());

		/*
		 * try { LoginLog loginLog = new LoginLog();
		 * loginLog.setSysUserId(sysUser.getSysUserId());
		 * loginLog.setIpAddr(token.getHost()); loginLog.setMacAddr(null);
		 * loginLog = loginLogService.insert(loginLog);
		 * session.setAttribute("loginLogId", loginLog.getLoginLogId()); } catch
		 * (SysException e) { e.printStackTrace(); } catch (AppException e) {
		 * e.printStackTrace(); }
		 */
		logger.debug("获取认证信息：记录登录用户【{}】日志", token.getUsername());
		System.out.println("user:"+getName()+",passwd:"+sysUser.getPassword());
		securityService.doGetAuthorizationInfo(sysUser);
		return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), getName());
	}

	/**
	 * 设置是否启用校验码
	 * 
	 * @param captchaEnable
	 *            是否启用校验码
	 */
	public void setCaptchaEnable(boolean captchaEnable) {
		isCaptchaEnable = captchaEnable;
	}
}
