package citic.gack.sso.entity;

import javax.xml.bind.annotation.XmlRootElement;

import citic.gack.sso.base.BaseModel;

/**
 * 组别(Organization)
 * 
 * @author wangjincheng
 * 
 * @data 2013-3-6上午11:23:12
 */
@XmlRootElement
@SuppressWarnings("serial")
public class SysOrganization extends BaseModel {

	protected String orgId;// 组别标识
	protected String parentId;// 上级组别标识
	protected String orgTypeId;// 组别类型标识
	protected String name;// 组别名称
	protected String acronym;// 首字母缩写
	protected String remarks;// 备注
	protected Integer orderNo;// 排序位置
	protected String planId;// 排序方案Id
	protected String showFlag;// 跨组查看案件标志

	public String getOrgId() {
		return orgId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOrgTypeId() {
		return orgTypeId;
	}

	public void setOrgTypeId(String orgTypeId) {
		this.orgTypeId = orgTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
