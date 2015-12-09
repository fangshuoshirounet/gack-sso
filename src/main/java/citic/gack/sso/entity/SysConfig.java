package citic.gack.sso.entity;

import javax.xml.bind.annotation.XmlRootElement;

import citic.gack.sso.base.BaseModel;

@XmlRootElement
@SuppressWarnings("serial")
public class SysConfig extends BaseModel {

	// 系统配置标识 主键，定义系统全局参数，修改为字符类型，以便在进行参数定义时通过英文缩写+数字方式可以直观看出该参数含义；
	protected String sysConfigId;

	// 名称
	protected String name;

	// 配置类型 G 全省统一L 按本地网统一A 按服务区统一W 按工区统一E 按局向统一非G的配置，关联SYS_AREA_CONFIG取值
	protected String configType;

	// 默认值
	// CONFIG_TYPE=G，直接取该默认值；CONFIG_TYPE=L/A/W/E，且SYS_AREA_CONFIG无对应配置，取该默认值。
	protected String curValue;

	// 描述
	protected String valueDesc;

	public void setSysConfigId(String sysConfigId) {
		this.sysConfigId = sysConfigId;
	}

	public String getSysConfigId() {
		return sysConfigId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getConfigType() {
		return configType;
	}

	public void setCurValue(String curValue) {
		this.curValue = curValue;
	}

	public String getCurValue() {
		return curValue;
	}

	public void setValueDesc(String valueDesc) {
		this.valueDesc = valueDesc;
	}

	public String getValueDesc() {
		return valueDesc;
	}
}