package citic.gack.sso.entity;

import javax.xml.bind.annotation.XmlRootElement;

import citic.gack.sso.base.BaseModel;

@SuppressWarnings("serial")
@XmlRootElement
public class SysEnumerate extends BaseModel {
    private String tableName;
    private String columnName;
    private String stsId;
    private String stsWords;
    private String orderId;
	public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setStsId(String stsId) {
        this.stsId = stsId;
    }

    public String getStsId() {
        return stsId;
    }

    public void setStsWords(String stsWords) {
        this.stsWords = stsWords;
    }

    public String getStsWords() {
        return stsWords;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}