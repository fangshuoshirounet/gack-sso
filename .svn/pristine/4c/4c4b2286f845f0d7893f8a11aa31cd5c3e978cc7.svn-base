package citic.gack.sso.base.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import citic.gack.sso.base.BeanUtil;
import citic.gack.sso.base.exception.SysException;
 

public class CacheManager {

	 private static Logger logger = Logger.getLogger(ECacheManager.class);

	    /**
	     * 缓存管理类
	     */
	    private static ICacheManager cacheManager;
	    
	    /**
	     * 单例对象
	     */
	    private static CacheManager instance;
	    
	    
	   public static CacheManager getInstance() {
	        if(instance == null) {
	            instance =  BeanUtil.getBean("cacheManager", CacheManager.class);
	        }
	        return instance;
	    }
	    
	    public void initialize() {
	    	cacheManager.initialize();
	    }
	    
	    public void shutdown(){
	    	cacheManager.shutdown();
	    }

		public ICacheManager getCacheManager() {
			return cacheManager;
		}

		public void setCacheManager(ICacheManager cacheManager) {
			this.cacheManager = cacheManager;
		}

		public Serializable get(String cacheName, String key) {
			return cacheManager.get(cacheName, key);
		}

		public void put(String cacheName, String key, Serializable value) {
			cacheManager.put(cacheName, key, value);
		}

		public void addCache(String cacheName) {
			cacheManager.addCache(cacheName);
		}

		public void removeCache(String cacheName) {
			cacheManager.removeCache(cacheName);
		}

		public void remove(String cacheName, String key) {
			cacheManager.remove(cacheName, key);			
		}

		public void reInitialize() throws SysException {
			cacheManager.reInitialize();			
		}
		  /**
	     * 刷新指定cacheName缓存
	     * @throws SysException
	     */
	    public  void reInitialize(String cacheName){
	    	cacheManager.reInitialize(cacheName);
	    }
	    
	    /**
	     * 获取配置参数列表
	     */
	    public List<TableCacheConfig> getConfiguration(){
	    	return cacheManager.getConfiguration();
	    }
	    
	    /**
	     * 获取缓存的Map
	     *
	     * @param cacheName 缓存的名称
	     * @return 缓存Map
	     */
	    public  Map<String, Serializable> getCacheMap(String cacheName) {
	    	return cacheManager.getCacheMap(cacheName);
	    }
	    
	    public boolean containsKey(String cacheName,String key){
	    	return cacheManager.containsKey(cacheName, key);
	    }
	    
	    public <T extends Serializable>  boolean containsValue(String cacheName,T value){
	    	return cacheManager.containsValue(cacheName, value);
	    }
}
