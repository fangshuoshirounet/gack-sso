package citic.gack.sso.base.cache;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import citic.gack.sso.base.exception.SysException;

 
/**
 * 缓存管理统一基类
 * 1.缓存初始化、关闭、重新加载
 * 2.添加、删除、获取缓存值
 * @author zhangweic
 */
public interface ICacheManager {
//  
//	/**
//	 * 得到实现类单例对象
//	 * @return
//	 */
//	public ICacheManager getInstances();
    /**
     * 向指定缓存中添加缓存值
     * @param 缓存key值
     * @return 缓存中的值
     * @throws SysException
     */
    public  Serializable get(String cacheName,String key);
    
    /**
     * 往缓存添加值
     * @param cacheName 缓存名
     * @param key 
     * @param value
     * @throws SysException
     */
    public  void put(String cacheName,String key,Serializable value);
    
    /**
     * 增加一个缓存，按照defaultCache的配置信息创建缓存
     * @param cacheName 缓存名称
     */
    public  void addCache(String cacheName);
    
    /**
     * 删除对应名称缓存
     * @param key 
     * @throws SysException
     */
    public  void removeCache(String cacheName);
    
    /**
     * 删除缓存对应key项
     * @param cacheName
     * @param key
     * @throws SysException
     */
    public  void remove(String cacheName,String key);
    
    /**
     * 初始化方法
     * @throws SysException
     */
    public  void initialize();
    
    
    /**
     * 关闭方法
     * @throws SysException
     */
    public  void shutdown();
    
    /**
     * 刷新缓存
     * @throws SysException
     */
    public  void reInitialize()throws SysException;
    
    /**
     * 刷新指定cacheName缓存
     * @throws SysException
     */
    public  void reInitialize(String cacheName);
    
    /**
     * 获取配置参数列表
     */
    public List<TableCacheConfig> getConfiguration();
    
    /**
     * 获取缓存的Map
     *
     * @param cacheName 缓存的名称
     * @return 缓存Map
     */
    public  Map<String, Serializable> getCacheMap(String cacheName) ;
    /**
     * 判断缓存中是否存在指定关键字
     *
     * @param cacheName 缓存的名称
     * @param key       缓存的关键字
     * @return true或者false
     */
    public boolean containsKey(String cacheName,String key);
    /**
     * 判断缓存中是否存在指定缓存项
     *
     * @param cacheName 缓存的名称
     * @param value     缓存项
     * @param <T>       缓存项的定义
     * @return true或者false
     */
    public <T extends Serializable> boolean containsValue(String cacheName,T value);
}
