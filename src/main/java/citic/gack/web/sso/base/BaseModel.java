package citic.gack.web.sso.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@SuppressWarnings("serial")
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 4794362684316466018L;

	/** 版本 **/
	private String version;
	/** 状态 **/
	private String sts;
	/** 创建人id **/
	private String creatorId;
	/** 创建人 **/
	private String creator;
	/** 创建时间 **/
	private String createDate;
	/** 操作人 **/
	private String operator;
	/** 操作人id **/
	private String operatorId;
	/** 操作时间 **/
	private String operateDate;

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 
	 * 采用Java反射机制（Reflection）绕过访问控制而直接存取对象的私有成员
	 * 
	 * 输出结果：com.credithc.framework.core.entity.Student@4774e78a[age=aa,mm=5]
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this);
	}

	/**
	 * 
	 * 采用Java反射机制（Reflection）绕过访问控制而直接存取对象的私有成员
	 * 
	 * 输出示列结果：aa,5
	 * 
	 * @return String
	 */
	public String toSimpleString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

	/**
	 * 用于辅助实现Object.equals()方法
	 * 
	 * @param o需要equals的对象
	 * 
	 * @return boolean(对象比较结果)
	 */

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	/**
	 * 用于辅助实现Object.hashCode()方法
	 * 
	 * @return int(对象的哈希码值)
	 * 
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
