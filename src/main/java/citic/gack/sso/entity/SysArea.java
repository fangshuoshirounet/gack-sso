package citic.gack.sso.entity;

import javax.xml.bind.annotation.XmlRootElement;

import citic.gack.sso.base.BaseModel;

 
/** 
 * <br>类 名: Area 
 * <br>描 述: 系统区域实体类 
 * <br>作 者: 王金成
 * <br>创 建： 2015年7月31日 
 * <br>版 本：v1.0.0 
 * <br> 
 */
@XmlRootElement
@SuppressWarnings("serial")
public class SysArea extends BaseModel {

	protected String areaId; // 区域标识
	protected String parentId; // 上级区域
	protected String code; // 区域编码
	protected String name; // 区域名称
	protected String abbr; // 名称缩写
	protected String isCenter; // 是否中心区域
	protected Integer areaSpecId; // 区域规格
	protected Integer sortPosition; // 排序位置
	protected Double longitude; // 经度
	protected Double latitude; // 纬度
	protected Integer geoAreaId; // 对应物理区域标识
	protected String fullName; // 全名
	protected String remarks; // 备注

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getAreaSpecId() {
		return areaSpecId;
	}

	public void setAreaSpecId(Integer areaSpecId) {
		this.areaSpecId = areaSpecId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public Integer getSortPosition() {
		return sortPosition;
	}

	public void setSortPosition(Integer sortPosition) {
		this.sortPosition = sortPosition;
	}

	public String getIsCenter() {
		return isCenter;
	}

	public void setIsCenter(String isCenter) {
		this.isCenter = isCenter;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getGeoAreaId() {
		return geoAreaId;
	}

	public void setGeoAreaId(Integer geoAreaId) {
		this.geoAreaId = geoAreaId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
