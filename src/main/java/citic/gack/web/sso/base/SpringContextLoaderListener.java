package citic.gack.web.sso.base;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 
 * <br>类 名: SpringContextLoaderListener 
 * <br>描 述: Spring环境加载监听器
 * <br>作 者: zj
 * <br>创 建： 2015年5月6日 
 * <br>版 本：v1.0.0 
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */
public class SpringContextLoaderListener extends ContextLoaderListener{

	private Logger log = Logger.getLogger(SpringContextLoaderListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		new ApplicationFactory().setApplicationContext(WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
		ApplicationContext applicationContext = ApplicationFactory.getContext();
	}
}
