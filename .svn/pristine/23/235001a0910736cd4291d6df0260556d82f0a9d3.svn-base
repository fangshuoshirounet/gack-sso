package citic.gack.sso.admin.security;

import java.text.MessageFormat;
import java.util.List;

import org.apache.shiro.config.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import citic.gack.sso.admin.dto.MenuDTO;
import citic.gack.sso.admin.service.SecurityService;
import citic.gack.sso.base.exception.SysException;

public class MenuFilterChainDefinition implements FactoryBean<Ini.Section> {
	private static Logger logger = LoggerFactory.getLogger(MenuFilterChainDefinition.class);

	// @Autowired
	// private ISecurityDelegate securityDelegate;
	@Autowired
	private SecurityService securityService;

	/**
	 * 优先加载的filterChainDefinitions
	 */
	private String filterChainDefinitions;

	/**
	 * 最后加载的filterChainDefinitions
	 */
	private String finalFilterChainDefinitions = "/** = authc";

	/**
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "authc, perms[\"{0}:{1}\"]";

	@Override
	public Ini.Section getObject() throws BeansException {
		Ini preIni = new Ini();
		preIni.load(filterChainDefinitions);
		Ini.Section section = preIni.getSection(Ini.DEFAULT_SECTION_NAME);
		logger.info("完成加载：优先加载的filterChainDefinitions,共 {} 条", section.size());

		try {
			List<MenuDTO> menus = securityService.queryActiveMenu();
			for (MenuDTO menu : menus) {
				if (menu.getMenuCode() == null) {
					logger.warn("菜单【{}，URL={}】的操作编码未配置，请配置菜单表", menu.getMenuName(), menu.getMenuUrl());
				}
				section.put(menu.getMenuUrl(), MessageFormat.format(PREMISSION_STRING, menu.getMenuCode(), menu.getOperationCode()));
				logger.debug("加载菜单URL={},权限={}", menu.getMenuUrl(), section.get(menu.getMenuUrl()));
			}
			logger.info("完成加载：菜单定义的filterChainDefinitions，共 {} 条", menus.size());
		} catch (SysException e) {
			e.printStackTrace();
			logger.error("加载系统菜单时发生错误：{}", e.getErrMsg());
		}

		Ini postIni = new Ini();
		postIni.load(finalFilterChainDefinitions);// 添加一种验证规则
		Ini.Section postSection = postIni.getSection(Ini.DEFAULT_SECTION_NAME);
		section.putAll(postSection);
		logger.info("完成加载：最后加载的filterChainDefinitions，共 {} 条", postSection.size());

		return section;
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	/**
	 * 设置优先加载的filterChainDefinitions
	 * 
	 * @param filterChainDefinitions
	 *            优先加载的filterChainDefinitions
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	/**
	 * 设置最后加载的filterChainDefinitions
	 * 
	 * @param finalFilterChainDefinitions
	 *            最后加载的filterChainDefinitions
	 */
	public void setFinalFilterChainDefinitions(String finalFilterChainDefinitions) {
		this.finalFilterChainDefinitions = finalFilterChainDefinitions;
	}
}