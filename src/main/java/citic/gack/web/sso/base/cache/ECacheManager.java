package citic.gack.web.sso.base.cache;

 

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import citic.gack.web.sso.base.BaseModel;
import citic.gack.web.sso.base.BeanUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
 


public class ECacheManager   implements ICacheManager {
    private static final Logger logger = Logger.getLogger(ECacheManager.class);
    
    public  net.sf.ehcache.CacheManager ehCacheManager;

    /**
     * 数据库操作对象
     */
    
    protected JdbcTemplate jdbcTemplate;

    /**
	 * 数据库表缓存配置信息
	 */
	private CacheConfig cacheConfig;

    private static final String BEAN_NAME = "cacheManager";

    private static ECacheManager instance;

    public  ECacheManager getInstances() {
        if(instance == null) {
            instance = BeanUtil.getBean("eCacheManager", ECacheManager.class);
        }
        return instance;
    }

    /**
     * 为缓存中增加缓存项
     *
     * @param cacheName 缓存的名称
     * @param key       缓存的关键字
     * @param value     缓存项（必须实现Serializable）
     * @param <T>       缓存项的定义
     */
    @Override
	public synchronized void put(String cacheName, String key, Serializable value) {
        if (!ehCacheManager.cacheExists(cacheName)) {
            addCache(cacheName);
        }
        Cache cache = ehCacheManager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }

    /**
     * 从local缓存中获得缓存项
     *
     * @param cacheName 缓存的名称
     * @param key       缓存的关键字
     * @return 缓存项
     */
    @SuppressWarnings("deprecation")
	@Override
	public Serializable get(String cacheName, String key) {
        if (!ehCacheManager.cacheExists(cacheName)) {
            return null;
        }

        Serializable value = null;

        Cache cache = ehCacheManager.getCache(cacheName);
        Element element = cache.get(key);
        if (element != null) {
            value = element.getValue();
        }

        return value;
    }
    
    
//    public Map get
    /**
     * 从缓存中删除缓存项
     *
     * @param cacheName 缓存的名称
     * @param key       缓存的关键字
     */
    @Override
	public synchronized void remove(String cacheName, String key) {
        if (!ehCacheManager.cacheExists(cacheName)) {
            return;
        }

        Cache cache = ehCacheManager.getCache(cacheName);
        cache.remove(key);
    }

    /**
     * 判断缓存中是否存在指定关键字
     *
     * @param cacheName 缓存的名称
     * @param key       缓存的关键字
     * @return true或者false
     */
    @Override
	public boolean containsKey(String cacheName, String key) {
        if (!ehCacheManager.cacheExists(cacheName)) {
            return false;
        }

        Cache cache = ehCacheManager.getCache(cacheName);
        return cache.isKeyInCache(key);
    }

    /**
     * 判断缓存中是否存在指定缓存项
     *
     * @param cacheName 缓存的名称
     * @param value     缓存项
     * @param <T>       缓存项的定义
     * @return true或者false
     */
    @Override
	public <T extends Serializable> boolean containsValue(String cacheName, T value) {
        if (!ehCacheManager.cacheExists(cacheName)) {
            return false;
        }
        Cache cache = ehCacheManager.getCache(cacheName);
        return cache.isValueInCache(value);
    }

    /**
     * 获取缓存的Map
     *
     * @param cacheName 缓存的名称
     * @return 缓存Map
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public Map<String, Serializable> getCacheMap(String cacheName) {
		Map cacheMap = new HashMap();

        if (ehCacheManager.cacheExists(cacheName)) {
            Cache cache = ehCacheManager.getCache(cacheName);
            List<String> keys = cache.getKeys();
            for (String key : keys) {
                cacheMap.put(key, cache.get(key).getValue());
            }
        }

        return cacheMap;
    }

    /**
     * 关闭EHCache的CacheManager
     */
    @Override
	public void shutdown() {
    	ehCacheManager.shutdown();
    }
    


    /**
     * 增加一个缓存，按照defaultCache的配置信息创建缓存
     *
     * @param cacheName 缓存名称
     */
    @Override
	public void addCache(String cacheName) {
    	ehCacheManager.addCache(cacheName);
    }

    /**
     * 增加一个缓存对象
     *
     * @param cache 缓存对象
     */
    public void addCache(Object cache) {
    	ehCacheManager.addCache((Cache) cache);
    }


    /**
     * 删除缓存
     *
     * @param cacheName 缓存的名称
     */
    @Override
	public void removeCache(String cacheName) {
    	ehCacheManager.removeCache(cacheName);
    }

    /**
     * 初始化缓存
     */
    @Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void initialize() {
        logger.info("......开始初始化数据库表缓存......");
        for (TableCacheConfig config : getConfiguration()) {
           doCacheInitializing(config);
        }
        logger.info("......完成初始化数据库表缓存......");
    }
    
//    public void initRemote() {
//        logger.info("......开始初始化数据库表缓存......");
//        if (configuration == null) {
//            configuration = new ArrayList<TableCacheConfig>();
//        }
//        for (TableCacheConfig config : configuration) {
//        	 redisCacheManager.doCacheInitializing(config);
//        }
//        logger.info("......完成初始化数据库表缓存......");
//    }

    /**
     * 重新初始化缓存
     */
    @Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void reInitialize() {
        logger.info("......开始重新初始化数据库表缓存......");
        for (TableCacheConfig config : getConfiguration()) {
            logger.info(String.format("......重新初始化缓存：删除缓存【%s】开始执行。", config.getCacheName()));
            this.removeCache(config.getCacheName());
            logger.info(String.format("......重新初始化缓存：删除缓存【%s】完成。", config.getCacheName()));
            doCacheInitializing(config);
        }
        logger.info("......完成重新初始化数据库表缓存......");
    }

    /**
     * 重新初始化指定缓存
     *
     * @param cacheName 缓存的名称
     */
    @Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void reInitialize(String cacheName) {
        logger.info(String.format("......开始重新初始化缓存【%s】......", cacheName));
        for (TableCacheConfig config : getConfiguration()) {
            if (StringUtils.equals(cacheName, config.getCacheName())) {
                logger.info(String.format("......重新初始化缓存：删除缓存【%s】开始执行。", config.getCacheName()));
                this.removeCache(config.getCacheName());
                logger.info(String.format("......重新初始化缓存：删除缓存【%s】完成。", config.getCacheName()));
                doCacheInitializing(config);
                break;
            }
        }
        logger.info(String.format("......完成重新初始化缓存【%s】......", cacheName));
    }

    /**
     * 按照配置信息初始化缓存
     *
     * @param config 缓存配置信息
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
     * @param config 缓存配置信息
     */
    private void doIdValueCacheInit(TableCacheConfig config) {
        logger.info(String.format("......初始化缓存【%s】开始执行。", config.getCacheName()));
        try {
            List<LabelValueBean> rsList = jdbcTemplate.query(config.getSql(),
                    new BeanPropertyRowMapper<LabelValueBean>(LabelValueBean.class));
            if (config.getConfiguration() != null) {
                Cache cache = new Cache(config.getConfiguration());
                this.addCache(cache);
            }
            logger.info(String.format("......初始化缓存【%s】，按照配置信息创建缓存成功。", config.getCacheName()));

            long count = 0;
            for (LabelValueBean record : rsList) {
                this.put(config.getCacheName(), record.getValue(), record.getLabel());
                count++;
            }
            logger.info("......初始化缓存：" + config.getCacheName() + "完成，共加入" + count + "个缓存内容。");
        } catch (DataAccessException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        }
    }

    /**
     * 按照配置信息初始化缓存（id-entity形式）
     *
     * @param config 缓存配置信息
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void doIdEntityCacheInit(TableCacheConfig config) {
        logger.info(String.format("......初始化缓存【%s】开始执行。", config.getCacheName()));

        try {
            Class clazz = config.getEntityClass();

            String[] keyPropertys = StringUtils.split(config.getKeyProperty());
            Method[] getters = new Method[keyPropertys.length];
            for (int i = 0; i < keyPropertys.length; i++) {
                getters[i] = clazz.getDeclaredMethod("get" + StringUtils.capitalize(keyPropertys[i]));
            }

            List<BaseModel> rsList = jdbcTemplate.query(config.getSql(),
                    new BeanPropertyRowMapper<BaseModel>(clazz));
            if (config.getConfiguration() != null) {
                Cache cache = new Cache(config.getConfiguration());
                this.addCache(cache);
            }
            logger.info(String.format("......初始化缓存【%s】，按照配置信息创建缓存成功。", config.getCacheName()));

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
            logger.info(String.format("......初始化缓存【%s】完成，共加入%d个缓存内容。", config.getCacheName(), count));
        } catch (DataAccessException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        } catch (NoSuchMethodException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        } catch (InvocationTargetException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        } catch (IllegalAccessException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        }
    }

    /**
     * 按照配置信息初始化缓存（id-list形式）
     *
     * @param config 缓存配置信息
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void doIdListCacheInit(TableCacheConfig config) {
        logger.info(String.format("......初始化缓存【%s】开始执行。", config.getCacheName()));

        try {
            Class clazz = config.getEntityClass();

            String[] keyPropertys = StringUtils.split(config.getKeyProperty());
            Method[] getters = new Method[keyPropertys.length];
            for (int i = 0; i < keyPropertys.length; i++) {
                getters[i] = clazz.getDeclaredMethod("get" + StringUtils.capitalize(keyPropertys[i]));
            }

            List<BaseModel> rsList = jdbcTemplate.query(config.getSql(),
                    new BeanPropertyRowMapper<BaseModel>(clazz));
            if (config.getConfiguration() != null) {
                Cache cache = new Cache(config.getConfiguration());
                this.addCache(cache);
            }
            logger.info(String.format("......初始化缓存【%s】，按照配置信息创建缓存成功。", config.getCacheName()));

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
            logger.info(String.format("......初始化缓存【%s】完成，共加入%d个缓存内容。", config.getCacheName(), count));
        } catch (DataAccessException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        } catch (NoSuchMethodException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        } catch (InvocationTargetException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        } catch (IllegalAccessException e) {
            logger.error(String.format("......初始化缓存【%s】失败：%s", config.getCacheName(), e.getMessage()));
        }
    }

    

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
	public net.sf.ehcache.CacheManager getEhCacheManager() {
		return ehCacheManager;
	}

	public void setEhCacheManager(net.sf.ehcache.CacheManager ehCacheManager) {
		this.ehCacheManager = ehCacheManager;
	}

	public CacheConfig getCacheConfig() {
		return cacheConfig;
	}

	public void setCacheConfig(CacheConfig cacheConfig) {
		this.cacheConfig = cacheConfig;
	}

	@Override
	public List<TableCacheConfig> getConfiguration() {
		return null == cacheConfig || null == cacheConfig.getConfiguration() ? new ArrayList<TableCacheConfig>() : cacheConfig.getConfiguration();
	}
}
