package citic.gack.web.sso.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PageInfo implements Serializable {
	private static final long serialVersionUID = -6333015871784412687L;

	public PageInfo() {
        //默认为第一页，每页显示15条
        page = 1;
        rp = 15;
	}

	/**
	 * 当前页
	 */
	private int page = -1; 
	/**
	 *  总记录数
	 */
	private int total;
	/**
	 *  每页显示几条数据
	 */
	private int rp; 
	/**
	 * 是否为可用的页--
	 */
	private boolean usePager;
	/**
	 *  查询返回记录数
	 */
	private List rows;
	/**
	 *  最大查询记录数
	 */
	private int rowLimit = -1; 
	/**
	 *  排序字段名
	 */
	private String sortName; 
	/**
	 *  排序方式，升序/降序
	 */
	private String sortOrder; 
	boolean rowsAmountSet;
	public boolean isRowsAmountSet() {
		return rowsAmountSet;
	}

	public void setRowsAmountSet(boolean rowsAmountSet) {
		this.rowsAmountSet = rowsAmountSet;
	}
	public boolean isUsePager() {
		return usePager;
	}

	public void setUsePager(boolean usePager) {
		this.usePager = usePager;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getRowLimit() {
		return rowLimit;
	}

	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}

	public String getSortName() {
		return convertString(sortName);
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

    public static String convertString(String str)
    {
    	if(StringUtils.isNotBlank(str)){
    		String upStr = str.toUpperCase();  
            StringBuffer buf = new StringBuffer(str.length()); 
            for(int i=0;i<str.length();i++)
            { 
               if(str.charAt(i)==upStr.charAt(i))
               {   buf.append("_"); 
                   buf.append(str.charAt(i)); 
               }
              else
              {
                  buf.append(str.charAt(i)); 
               } 
         } return   buf.toString(); 
         } 
    	return str;
      }
    @SuppressWarnings("rawtypes")
	public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page", page);
        jsonObject.put("total", total);
        jsonObject.put("rp", rp);
        jsonObject.put("usePager", usePager);
        jsonObject.put("rowLimit", rowLimit);
        jsonObject.put("sortname", convertString(sortName));
        jsonObject.put("sortorder", sortOrder);
        if(rows != null && rows.size() > 0) {
            String jsonString = JSONArray.fromObject(rows).toString();
            if (StringUtils.isNotBlank(jsonString)) {
                jsonObject.element("rows", jsonString);
            }
        }else{
        	jsonObject.element("rows", new ArrayList());
        }
        return jsonObject.toString();
    }
}
