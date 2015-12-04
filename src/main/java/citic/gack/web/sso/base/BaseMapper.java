package citic.gack.web.sso.base;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * 类 名: CsmBaseMapper<br/>
 * 描 述: mybatis 公用方法基类<br/>
 * 版 本：<br/>
 * 
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public interface BaseMapper<T> {
	final static String BEAN_KEY = "bean";
	final static String CONDITION_BEAN_KEY = "condition";

	/** 保存实体 */
	void save(T bean);

	/** 删除实体 */
	void deleteById(T bean);

	/** 更新实体 */
	int updateById(T bean);

	/** 通过主键进行查询 */
	T queryById(T condition);

	/** 通过条件进行查询-(PO) */
	List<T> queryList(T condition);

	/** 通过条件与Page进行分页排序查询-(PO) */
	List<T> queryListByPage(@Param(CONDITION_BEAN_KEY) T condition, @Param(PageInterceptor.PAGE_KEY) PageInfo page);
}
