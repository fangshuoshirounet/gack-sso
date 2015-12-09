package citic.gack.sso.base.cache;

import citic.gack.sso.base.BaseModel;
import net.sf.ehcache.config.CacheConfiguration;

public class TableCacheConfig extends BaseModel {
    /**
     * 数据库表缓存名称（会覆盖configuration中的配置）
     */
    private String cacheName;

    /**
     * 数据库表缓存的查询语句
     */
    private String sql;

    /**
     * 缓存类型
     */
    private CACHE_TYPE cacheType;

    /**
     * 数据库表缓存的实体类，此字段用于id-entity和id-list形式的缓存，
     * 如果为id-value形式的此字段为空
     */
    private Class entityClass;

    /**
     * 主键属性字段，与entityClass配合使用
     */
    private String keyProperty;

    /**
     * EHCache的配置信息
     */
    private CacheConfiguration configuration;

    /**
     * 获取数据库表缓存名称
     *
     * @return 数据库表缓存名称
     */
    public String getCacheName() {
        return cacheName;
    }

    /**
     * 设置数据库表缓存名称
     *
     * @param cacheName 数据库表缓存名称
     */
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    /**
     * 获得数据库表缓存的查询语句
     *
     * @return 数据库表缓存的查询语句
     */
    public String getSql() {
        return sql;
    }

    /**
     * 设置数据库表缓存的查询语句
     *
     * @param sql 数据库表缓存的查询语句
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * 获得缓存类型
     *
     * @return 缓存类型
     */
    public CACHE_TYPE getCacheType() {
        return cacheType;
    }

    /**
     * 设置缓存类型
     *
     * @param cacheType 缓存类型
     */
    public void setCacheType(CACHE_TYPE cacheType) {
        this.cacheType = cacheType;
    }

    /**
     * 获得数据库表缓存的实体类
     *
     * @return 数据库表缓存的实体类
     */
    public Class getEntityClass() {
        return entityClass;
    }

    /**
     * 设置数据库表缓存的实体类
     *
     * @param entityClass 数据库表缓存的实体类
     */
    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 获取主键属性字段
     *
     * @return 主键属性字段
     */
    public String getKeyProperty() {
        return keyProperty;
    }

    /**
     * 设置主键属性字段
     *
     * @param keyProperty 主键属性字段
     */
    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    /**
     * 获得EHCache的配置信息
     *
     * @return EHCache的配置信息
     */
    public CacheConfiguration getConfiguration() {
        configuration.setName(cacheName);
        return configuration;
    }

    /**
     * 设置EHCache的配置信息
     *
     * @param configuration EHCache的配置信息
     */
    public void setConfiguration(CacheConfiguration configuration) {
        this.configuration = configuration;
    }

    public enum CACHE_TYPE {
        ID_VALUE, ID_ENTITY, ID_LIST,ID_MULTIPLE_VALUE
    }
}
