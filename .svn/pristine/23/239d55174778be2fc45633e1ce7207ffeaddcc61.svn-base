package citic.gack.sso.base.cache;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import citic.gack.sso.base.BaseModel;

public abstract class AbstractCacheManager implements ICacheManager {

	private static final Logger log = Logger.getLogger(AbstractCacheManager.class);

	/**
	 * 数据库操作对象
	 */
	protected JdbcTemplate jdbcTemplate;

	/**
	 * 数据库表缓存配置信息
	 */
	private CacheConfig cacheConfig;

	@Override
	public abstract Serializable get(String cacheName, String key);

	@Override
	public abstract void put(String cacheName, String key, Serializable value);

	@Override
	public abstract void addCache(String cacheName);

	@Override
	public abstract void removeCache(String cacheName);

	@Override
	public abstract void remove(String cacheName, String key);

	@Override
	public abstract Map<String, Serializable> getCacheMap(String cacheName);

	@Override
	public abstract boolean containsKey(String cacheName, String key);

	@Override
	public abstract <T extends Serializable> boolean containsValue(String cacheName, T value);

	@Override
	public void initialize() {
		log.info("......开始初始化数据库表缓存......");

		for (TableCacheConfig config : cacheConfig.getConfiguration()) {
			log.info("......重新初始化缓存：删除缓存【{config.getCacheName()}】开始执行。");
			this.removeCache(config.getCacheName());
			log.info("......重新初始化缓存：删除缓存【{config.getCacheName()}】完成。");
			doCacheInitializing(config);
		}
		log.info("......完成初始化数据库表缓存......");
	}

	@Override
	public void shutdown() {
		log.info("执行shutdown 刷新缓存内容");
	}

	/**
	 * 按照配置信息初始化缓存
	 * 
	 * @param config
	 *            缓存配置信息
	 */
	private void doCacheInitializing(TableCacheConfig config) {
		if (config.getCacheType().equals(TableCacheConfig.CACHE_TYPE.ID_VALUE)) {
			doIdValueCacheInit(config);
		} else if (config.getCacheType().equals(TableCacheConfig.CACHE_TYPE.ID_ENTITY)) {
			doIdEntityCacheInit(config);
		} else if (config.getCacheType().equals(TableCacheConfig.CACHE_TYPE.ID_LIST)) {
			doIdListCacheInit(config);
		}
	}

	/**
	 * 按照配置信息初始化缓存（id-value形式）
	 * 
	 * @param config
	 *            缓存配置信息
	 */
	private void doIdValueCacheInit(TableCacheConfig config) {
		log.info("......初始化缓存【{" + config.getCacheName() + "}】开始执行。");
		try {
			// Date date = new Date();
			// long ex_sql_startTime = date.getTime();
			List<LabelValueBean> rsList = jdbcTemplate.query(config.getSql(),
					new BeanPropertyRowMapper<LabelValueBean>(LabelValueBean.class));
			log.info("......初始化缓存【{" + config.getCacheName() + "}】，按照配置信息创建缓存成功。");

			// Date date2 = new Date();
			// long startTime = date2.getTime();
			long count = 0;
			for (LabelValueBean record : rsList) {
				this.put(config.getCacheName(), record.getValue(), record.getLabel());
				count++;
			}

			log.info("......初始化缓存：" + config.getCacheName() + "完成，共加入" + count + "个缓存内容。");
		} catch (DataAccessException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		}
	}

	/**
	 * 按照配置信息初始化缓存（id-entity形式）
	 * 
	 * @param config
	 *            缓存配置信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doIdEntityCacheInit(TableCacheConfig config) {
		log.info("......初始化缓存【{" + config.getCacheName() + "}】开始执行。");
		try {
			Class clazz = config.getEntityClass();

			String[] keyPropertys = StringUtils.split(config.getKeyProperty());
			Method[] getters = new Method[keyPropertys.length];
			for (int i = 0; i < keyPropertys.length; i++) {
				getters[i] = clazz.getDeclaredMethod("get" + StringUtils.capitalize(keyPropertys[i]));
			}
			List<BaseModel> rsList = jdbcTemplate.query(config.getSql(), new BeanPropertyRowMapper<BaseModel>(clazz));
			log.info("......初始化缓存【{" + config.getCacheName() + "}】，按照配置信息创建缓存成功。");

			// Date date2 = new Date();
			// long startTime = date2.getTime();
			long count = 0;
			String keyProperty = null;
			for (BaseModel record : rsList) {
				keyProperty = "";
				for (Method getter : getters) {
					keyProperty += getter.invoke(record).toString();
					keyProperty += ".";
				}
				keyProperty = StringUtils.removeEnd(keyProperty, ".");

				this.put(config.getCacheName(), keyProperty, record);
				count++;
			}
			log.info("......初始化缓存【{" + config.getCacheName() + "}】完成，共加入{" + count + "}个缓存内容。");
		} catch (DataAccessException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		} catch (NoSuchMethodException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		} catch (InvocationTargetException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		} catch (IllegalAccessException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		}
	}

	/**
	 * 按照配置信息初始化缓存（id-list形式）
	 * 
	 * @param config
	 *            缓存配置信息
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doIdListCacheInit(TableCacheConfig config) {
		log.info("......初始化缓存【{" + config.getCacheName() + "}】开始执行。");
		try {
			Class clazz = config.getEntityClass();

			String[] keyPropertys = StringUtils.split(config.getKeyProperty());
			Method[] getters = new Method[keyPropertys.length];
			for (int i = 0; i < keyPropertys.length; i++) {
				getters[i] = clazz.getDeclaredMethod("get" + StringUtils.capitalize(keyPropertys[i]));
			}
			List<BaseModel> rsList = jdbcTemplate.query(config.getSql(), new BeanPropertyRowMapper<BaseModel>(clazz));
			log.info("......初始化缓存【{" + config.getCacheName() + "}】，按照配置信息创建缓存成功。");

			// Date date2 = new Date();
			// long startTime = date2.getTime();
			long count = 0;
			List valueList = null;
			String keyProperty = null;

			for (BaseModel record : rsList) {
				keyProperty = "";
				for (Method getter : getters) {
					keyProperty += getter.invoke(record).toString();
					keyProperty += ".";
				}
				keyProperty = StringUtils.removeEnd(keyProperty, ".");

				valueList = (List) this.get(config.getCacheName(), keyProperty);

				if (valueList == null) {
					valueList = new ArrayList();
				}
				valueList.add(record);
				this.put(config.getCacheName(), keyProperty, (Serializable) valueList);
				count++;
			}
			log.info("......初始化缓存【{" + config.getCacheName() + "}】完成，共加入{" + count + "}个缓存内容。");
		} catch (DataAccessException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		} catch (NoSuchMethodException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		} catch (InvocationTargetException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		} catch (IllegalAccessException e) {
			log.error("......初始化缓存【{" + config.getCacheName() + "}】失败：{}", e);
		}
	}

	@Override
	public void reInitialize() {
		log.info("......开始重新初始化数据库表缓存......");
		for (TableCacheConfig config : cacheConfig.getConfiguration()) {
			log.info("......重新初始化缓存：删除缓存【{config.getCacheName()}】开始执行。");
			this.removeCache(config.getCacheName());
			log.info("......重新初始化缓存：删除缓存【{config.getCacheName()}】完成。");
			doCacheInitializing(config);
		}
		log.info("......完成重新初始化数据库表缓存......");
	}
	
	@Override
	public void reInitialize(String cacheName) {
		log.info("......开始重新初始化缓存【{cacheName}】......");
		for (TableCacheConfig config : cacheConfig.getConfiguration()) {
			if (StringUtils.equals(cacheName, config.getCacheName())) {
				log.info("......重新初始化缓存：删除缓存【{config.getCacheName()}】开始执行。");
				this.removeCache(config.getCacheName());
				log.info("......重新初始化缓存：删除缓存【{config.getCacheName()}】完成。");
				doCacheInitializing(config);
				break;
			}
		}
		log.info(String.format("......完成重新初始化缓存【%s】......", cacheName));
	}

	@Override
	public List<TableCacheConfig> getConfiguration() {
		return cacheConfig.getConfiguration();
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public CacheConfig getCacheConfig() {
		return cacheConfig;
	}

	public void setCacheConfig(CacheConfig cacheConfig) {
		this.cacheConfig = cacheConfig;
	}

}
