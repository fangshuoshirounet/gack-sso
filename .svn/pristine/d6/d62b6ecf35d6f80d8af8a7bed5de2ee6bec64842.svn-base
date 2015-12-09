package citic.gack.sso.admin.security;
 
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import citic.gack.sso.admin.security.exception.AccountNotFoundException;
import citic.gack.sso.admin.security.exception.AccountStatusException;
import citic.gack.sso.admin.security.exception.AccountUnknownException;
import citic.gack.sso.admin.security.exception.InactivePasswordException;
import citic.gack.sso.admin.security.exception.IncorrectCaptchaException;
import citic.gack.sso.admin.security.exception.IncorrectPasswordException;

/**
 * 安全管理的用户认证过滤器
 */
public class SecurityAuthcFilter extends FormAuthenticationFilter {
    private static Logger logger = LoggerFactory.getLogger(SecurityAuthcFilter.class);

	/**
	 * 默认的验证码输入框标识
	 */
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    /**
	 * 默认登录后的转向逻辑（是否跳转到登录前的界面）
	 */
	public static final boolean DEFAULT_REDIRECT_TO_SAVED_REQUEST = false;

	/**
	 * 默认的编码方式
	 */
	public static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";

	/**
	 * 验证码输入框标识
	 */
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	/**
	 * 登录后的转向逻辑（是否跳转到登录前的界面）
	 */
	private boolean redirectToSavedRequest = DEFAULT_REDIRECT_TO_SAVED_REQUEST;

	/**
	 * 编码方式
	 */
	private String characterEncoding = DEFAULT_CHARACTER_ENCODING;

    /**
     * 验证码的属性Key（用于从session中获取系统生成的验证码）
     */
    public static final String CAPTCHA_SESSION = "SECURITY.LOGIN.CAPTCHA";

    /**
     * 登录错误输入框元素名称
     */
    public static final String LOGIN_FAILED_ELEMENT = "SECURITY.LOGIN.FAILED.ELEMENT";

    /**
     * 登录错误提示信息
     */
    public static final String LOGIN_FAILED_MESSAGE = "SECURITY.LOGIN.FAILED.MESSAGE";

	/**
	 * 根据登录信息创建登录用户的token
	 * @param request ServletRequest对象
	 * @param response ServletResponse对象
	 * @return 登录用户的token
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		try {
			request.setCharacterEncoding(characterEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
        String captchaSession = getCaptchaSession(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);

        SecurityToken token = new SecurityToken(username, password, captcha, rememberMe, host);
        token.setCaptchaSession(captchaSession);

		return token;
	}

	/**
	 * 获取验证码输入框标识
	 * @return 验证码输入框标识
	 */
	public String getCaptchaParam() {
		return captchaParam;
	}

	/**
	 * 设置验证码输入框标识
	 * @param captchaParam 验证码输入框标识
	 */
	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	/**
	 * 获取验证码输入框标识
	 * @param request ServletRequest对象
	 * @return 验证码输入框标识
	 */
	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

    /**
     * 获取验证码输入框标识
     * @param request ServletRequest对象
     * @return 验证码输入框标识
     */
    protected String getCaptchaSession(ServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return (String)session.getAttribute(CAPTCHA_SESSION);
    }

	/**
	 * 判断是否跳转到登录前的界面
	 * @return 是否跳转到登录前的界面
	 */
	public boolean isRedirectToSavedRequest() {
		return redirectToSavedRequest;
	}

	/**
	 * 设置是否跳转到登录前的界面
	 * @param redirectToSavedRequest 是否跳转到登录前的界面
	 */
	public void setRedirectToSavedRequest(boolean redirectToSavedRequest) {
		this.redirectToSavedRequest = redirectToSavedRequest;
	}

	/**
	 * 获取编码方式
	 * @return 编码方式
	 */
	public String getCharacterEncoding() {
		return characterEncoding;
	}

	/**
	 * 设置编码方式
	 * @param characterEncoding 编码方式
	 */
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	/**
	 * 登录失败后的处理
	 * @param request ServletRequest对象
	 * @param ae 用户认证错误异常信息
	 */
	@Override
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        String element = getUsernameParam();
        if(ae instanceof AccountNotFoundException ||
                ae instanceof AccountStatusException ||
                ae instanceof AccountUnknownException) {
            element = getUsernameParam();
        }
        if(ae instanceof IncorrectPasswordException) {
            element = getPasswordParam();
        }
        if(ae instanceof IncorrectCaptchaException) {
            element = getCaptchaParam();
        }
        if(ae instanceof InactivePasswordException) {
        	element = getPasswordParam();
        }
		request.setAttribute(LOGIN_FAILED_ELEMENT, element);
		request.setAttribute(LOGIN_FAILED_MESSAGE, ae.getMessage());
	}

	/**
	 * 登录成功后的处理
	 * @param token 登录用户的token
	 * @param subject 登录用户的Shiro信息
	 * @param request ServletRequest对象
	 * @param response ServletResponse对象
	 * @return 是否登录成功
	 * @throws Exception 异常信息
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //登录成功后，从session中去除校验码
        Session session = subject.getSession();
        session.removeAttribute(CAPTCHA_SESSION);

        //触发执行鉴权方法
        SecurityManager.isSessionUserAdminRole();
		if (redirectToSavedRequest) {
			return super.onLoginSuccess(token, subject, request, response);
		} else {
			WebUtils.issueRedirect(request, response, getSuccessUrl());
			return false;
		}
	}
}