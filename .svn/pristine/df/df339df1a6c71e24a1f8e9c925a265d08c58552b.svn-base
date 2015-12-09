package citic.gack.sso.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import citic.gack.sso.utils.Reflections;

/**
 * 
 * <p>
 * 判断如果参数中有 {@link PageController} 对象，那么执行分页查询。
 * </p>
 * <p>
 * 目前只支持把page放到HashMap中(或使用接口时，把page作为方法的参数),并且key为"page"
 * </p>
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
@SuppressWarnings("rawtypes")
public class PageInterceptor implements Interceptor {

	private static final ThreadLocal<PageInfo> PAGE_CONTEXT = new ThreadLocal<PageInfo>();

	public static final String PAGE_KEY = "page";

	/** mysql,oracle... */
	private String dialect = "";

	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
			BaseStatementHandler handler = (BaseStatementHandler) Reflections.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) Reflections.getValueByFieldName(handler, "mappedStatement");

			BoundSql boundSql = handler.getBoundSql();
			Object param = boundSql.getParameterObject();
			String originalSql = boundSql.getSql();
			// System.out.println(originalSql);
			// System.out.println(param);

			// Reflections.setValueByFieldName(boundSql, "parameterObject",
			// changeCode(param));
			if (param instanceof HashMap) {
				// 参数中含有page就进行分页处理
				HashMap map = (HashMap) param;
				if (map.containsKey(PAGE_KEY)) {
					PageInfo p = (PageInfo) map.get(PAGE_KEY);
					if (p != null) {
						p.setTotal(queryTotal(invocation, mappedStatement, boundSql, param, originalSql));
						Reflections.setValueByFieldName(boundSql, "sql", pageSql(originalSql, p));
					}
				}
			}
		}
		return invocation.proceed();
	}

	public Object changeCode(Object bean) {
		System.out.println(111);
		try {
			PropertyUtils.describe(bean);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return bean;

	}

	/**
	 * 为count语句设置参数.
	 * 
	 * @see org.apache.ibatis.executor.parameter.DefaultParameterHandler#setParameters(PreparedStatement)
	 * @see org.apache.ibatis.scripting.defaults.DefaultParameterHandler#setParameters(PreparedStatement)
	 * 
	 * @param preparedStatement
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private void setParameters(PreparedStatement preparedStatement, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject)
			throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> mappings = boundSql.getParameterMappings();
		if (mappings == null) {
			return;
		}
		Configuration configuration = mappedStatement.getConfiguration();
		TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
		MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
		for (int i = 0; i < mappings.size(); i++) {
			ParameterMapping parameterMapping = mappings.get(i);
			if (parameterMapping.getMode() != ParameterMode.OUT) {
				Object value;
				String propertyName = parameterMapping.getProperty();
				PropertyTokenizer prop = new PropertyTokenizer(propertyName);
				if (parameterObject == null) {
					value = null;
				} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
					value = parameterObject;
				} else if (boundSql.hasAdditionalParameter(propertyName)) {
					value = boundSql.getAdditionalParameter(propertyName);
				} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
					value = boundSql.getAdditionalParameter(prop.getName());
					if (value != null) {
						value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
					}
				} else {
					value = metaObject == null ? null : metaObject.getValue(propertyName);
				}
				TypeHandler typeHandler = parameterMapping.getTypeHandler();
				if (typeHandler == null) {
					throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
				}
				typeHandler.setParameter(preparedStatement, i + 1, value, parameterMapping.getJdbcType());
			}
		}
	}

	/**
	 * 生成特定数据库的分页语句
	 * 
	 * @param originalSql
	 * @param page
	 * @return
	 */
	private String pageSql(String originalSql, PageInfo page) {
		if (page == null || dialect == null || dialect.equals("")) {
			return originalSql;
		}

		StringBuilder sb = new StringBuilder();
		int pageSize = page.getRp(); // 每页显示记录数
		int pageNo = page.getPage(); // 第几页
		if ("mysql".equals(dialect)) {
			sb.append(originalSql);
			sb.append(" limit " + new Integer((pageNo - 1) * pageSize) + "," + new Integer(pageNo * pageSize));
		} else if ("oracle".equals(dialect)) {
			sb.append("select * from (select pagetable.*,ROWNUM AS rowcounter from (");
			sb.append(originalSql);
			sb.append(")  pagetable) subt where subt.rowcounter>");
			sb.append(new Integer((pageNo - 1) * pageSize));
			sb.append(" AND subt.rowcounter<=");
			sb.append(new Integer(pageNo * pageSize));
			sb.append(" and rownum <=" + pageSize);// 解决海量数据分页

			// System.out.println(sb.toString());
		} else {
			throw new IllegalArgumentException("分页插件不支持此数据库：" + dialect);
		}
		return sb.toString();
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
	}

	/**
	 * <p>
	 * 查询总数
	 * </p>
	 * 
	 * @param invocation
	 * @param mappedStatement
	 * @param boundSql
	 * @param param
	 * @param originalSql
	 * @return
	 * @throws SQLException
	 */
	private int queryTotal(Invocation invocation, MappedStatement mappedStatement, BoundSql boundSql, Object param, String originalSql) throws SQLException {
		Connection conn = (Connection) invocation.getArgs()[0];
		String countSql = "select count(*) from (" + originalSql + ") as a";
		// BoundSql countBoundSql = new
		// BoundSql(mappedStatement.getConfiguration(), countSql,
		// boundSql.getParameterMappings(), param);
		ResultSet rs = null;
		PreparedStatement stmt = null;

		int count = 0;
		try {
			stmt = conn.prepareStatement(countSql);
			setParameters(stmt, mappedStatement, boundSql, param);
			rs = stmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
		}
		return count;
	}

}