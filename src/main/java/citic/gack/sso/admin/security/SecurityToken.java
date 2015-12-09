package citic.gack.sso.admin.security;

import org.apache.shiro.authc.UsernamePasswordToken;

public class SecurityToken extends UsernamePasswordToken {
    /**
     * 验证码
     */
    private String captcha;

    /**
     * 系统生成的验证码（从session获取）
     */
    private String captchaSession;

    /**
     * 构造函数
     */
    public SecurityToken() {
        super();
    }

    /**
     * 构造函数
     * @param username 用户名
     * @param password 用户密码
     * @param captcha 验证码
     */
    public SecurityToken(String username, String password, String captcha) {
        super(username, password);
        this.captcha = captcha;
    }

    /**
     * 构造函数
     * @param username 用户名
     * @param password 用户密码
     * @param captcha 验证码
     * @param host 主机
     */
    public SecurityToken(String username, String password, String captcha, String host) {
        super(username, password, host);
        this.captcha = captcha;
    }

    /**
     * 构造函数
     * @param username 用户名
     * @param password 用户密码
     * @param captcha 验证码
     * @param rememberMe 记住我
     */
    public SecurityToken(String username, String password, String captcha, boolean rememberMe) {
        super(username, password, rememberMe);
        this.captcha = captcha;
    }

    /**
     * 构造函数
     * @param username 用户名
     * @param password 用户密码
     * @param captcha 验证码
     * @param rememberMe 记住我
     * @param host 主机
     */
    public SecurityToken(String username, String password, String captcha, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }


    /**
     * 获得验证码
     * @return 验证码
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * 设置验证码
     * @param captcha 验证码
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * 获得系统生成的验证码
     * @return 系统生成的验证码
     */
    public String getCaptchaSession() {
        return captchaSession;
    }

    /**
     * 设置系统生成的验证码
     * @param captchaSession 系统生成的验证码
     */
    public void setCaptchaSession(String captchaSession) {
        this.captchaSession = captchaSession;
    }
}
