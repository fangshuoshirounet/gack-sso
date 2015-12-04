package citic.gack.web.sso.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
/**
 * 
 * 类 名: DateTools<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 创 建： 2014年12月15日<br/>
 * 版 本：<br/>
 *
 */
public class DateTools {
	
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");  
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
	/**格式化日期对象，只保留8位
	 * @param newDate
	 * @return
	 */
	public static String FormatDate8(Date newDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String date = dateFormat.format(newDate);
		return date;
	}

	public static String FormatDate10(Date newDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(newDate);
		return date;
	}
	public static String FormatDate14(Date newDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(newDate);
        return date;
    }
	public static String FormatDate19(java.util.Date curTime) {
		if (curTime == null) {
			return " ";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(curTime);
	}
	/**格式化日字符对象日期，只保留8位
	 * @param
	 * @return
	 */
	public static String StringDateTo8(String str_date) {
		if(null==str_date){
			return "";
		}
		str_date = StringDateReplace(str_date);
		if(str_date.length()>=8){
			return str_date.substring(0, 8);
		}
		return "";
	}
	/**
     * 14位转10位
     * @param date
     * @return
     */
    public static String StringDateTo10(String str_date) {
    	if(null==str_date){
			return "";
		}
		str_date = StringDateReplace(str_date);
		if(str_date.length()>=8){
			return str_date.substring(0,4)+"-"+str_date.substring(4,6)+"-"+str_date.substring(6,8);
		}
		return "";
    }

    /**
     * 19位转14位
     * @param date
     * @return
     */
    public static String StringDateTo14(String str_date) {
    	String date = StringDateReplace(str_date.trim());
    	if(null==date || "".equals(date)){
    	}else{
        	while(date.length()<14){
        		date+="0";
        	}
    	}
    	if (date.length()>14){
    		date = date.substring(0,14);
    	}
    	return date;
    }

    /**
     * 14转19
     * @param date
     * @return
     */
    public static String StringDateTo19(String date) {
    	String t_date = "";
    	if(null==date || "".equals(date)){

    	}else{
    		
    		date = StringDateReplace(date.trim());
    		
        	if(null==date || "".equals(date)){
        	}else{
            	while(date.length()<14){
            		date+="0";
            	}
        	}
        	t_date += date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
    	}
    	return t_date;
    }
    /**
     * 转ISO_DATETIME
     * @param date
     * @return
     */
    public static String StringDateToISO_DateTime(String date) {
    	String t_date = "";
    	if(null==date || "".equals(date)){
    	}else{
    		date = StringDateReplace(date.trim());
    		if(null==date || "".equals(date)){
    		}else{
    			while(date.length()<14){
    				date+="0";
    			}
    		}
    		t_date += date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+"T"+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
    	}
    	return t_date;
    }
    
    
    
	private static String StringDateReplace(String newDate) {
		newDate = newDate.replace("-", "").replace(":", "").replace(" ", "").replace(".", "").replace("T", "").replace("+", "");
		return StringEscapeUtils.escapeSql(newDate);
	}

	/**得到当前系统时间  date类型**/
	public static Date getSysDate() {
		Calendar ca = Calendar.getInstance();
		return ca.getTime();
	}
	public static String getSysDate10() {
		Calendar ca = Calendar.getInstance();
		return FormatDate10(ca.getTime());
	}
	public static String getSysDate14() {
		Calendar ca = Calendar.getInstance();
		return FormatDate14(ca.getTime());
	}
    public static String getSysDate19() {
    	return StringDateTo19(getSysDate14());
    }
    public static Date Date19ToDate(String date) {
    	if (19 != date.length()) {
    		return null;
    	}
//		int yea = Integer.parseInt(date.substring(0, 4));
//		int mon = Integer.parseInt(date.substring(5, 7))-1;
//		int dat = Integer.parseInt(date.substring(8, 10));
//    	int hrs = Integer.parseInt(date.substring(11, 13));
//    	int min = Integer.parseInt(date.substring(14, 16));
//    	int sec = Integer.parseInt(date.substring(17, 19));
//    	Calendar ca = Calendar.getInstance();
//    	ca.set(yea, mon, dat, hrs, min, sec);
//    	return ca.getTime();

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(date);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
    }
	public static Date Date14ToDate(String date) {
		if (14 != date.length()) {
			return null;
		}
//		int yea = Integer.parseInt(date.substring(0, 4));
//		int mon = Integer.parseInt(date.substring(4, 6))-1;
//		int dat = Integer.parseInt(date.substring(6, 8));
//		int hrs = Integer.parseInt(date.substring(8, 10));
//		int min = Integer.parseInt(date.substring(10, 12));
//		int sec = Integer.parseInt(date.substring(12, 14));
//		Calendar ca = Calendar.getInstance();
//		ca.set(yea, mon, dat, hrs, min, sec);
//		return ca.getTime();
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			return df.parse(date);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**字符串8位日期转换成Date类型**/
	public static Date Date8ToDate(String date) {
		date = StringDateTo8(date);
//		int yea = Integer.parseInt(date.substring(0, 4));
//		int mon = Integer.parseInt(date.substring(4, 6))-1;
//		int dat = Integer.parseInt(date.substring(6, 8));
//		Calendar ca = Calendar.getInstance();
//		ca.set(yea, mon, dat,0,0,0);
//		return ca.getTime();
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.parse(date);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
	}
	/**字符串10位日期转换成Date类型**/
	public static Date Date10ToDate(String date) {
		if (10 != date.length()) {
			return null;
		}
		/*
		int yea = Integer.parseInt(date.substring(0, 4));
		int mon = Integer.parseInt(date.substring(5, 7))-1;
		int dat = Integer.parseInt(date.substring(8, 10));
		Calendar ca = Calendar.getInstance();
		ca.set(yea, mon, dat,0,0,0);
		return ca.getTime();
		*/
		try {
			return dateFormat.parse(date);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**日期型比较相差天数**/
	public static int DateCompareDate(Date endDate, Date startDate) {
		long DAY = 24L * 60L * 60L * 1000L;
    	return (int) (( endDate.getTime() - startDate.getTime() ) / DAY) ;
	}
	/**日期型比较相差天数**/
	public static int DateCompareDate(String endDate, String startDate) {
		long DAY = 24L * 60L * 60L * 1000L; 
		Date d1 = Date14ToDate(StringDateTo14(startDate));    
		Date d2 = Date14ToDate(StringDateTo14(endDate));
		return (int) (( d2.getTime() - d1.getTime() ) / DAY) ;
	}
	/**
	 * 将YYYY-mm-dd的字符串转化为日期对象
	 * @param dateString YYYY-mm-dd的字符串
	 * @return 日期对象,格式不正确返回NULL
	 */
	public static java.sql.Date praseDate(String dateString){
		return praseDate(dateString,"yyyy-MM-dd");
	}
	
	public static java.sql.Date praseDate(String dateString,String format){
		SimpleDateFormat formatter=new SimpleDateFormat(format);
		Date d=null;
		if(dateString==null||dateString.equals("")||dateString.toLowerCase().equals("null")){ return null; }
		try{
			d=formatter.parse(dateString);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return new java.sql.Date(d.getTime());
	}

	/**根据日期和差的天数返回新的日期**/
	public static Date getDateByDateDay(Date date, int day) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_YEAR,day);
		return ca.getTime();
	}
	/**根据日期和差的年数返回新的日期**/
	public static Date getDateByDateYear(Date date, int year) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.YEAR,year);
		return ca.getTime();
	}
	/**根据日期和差的月数返回新的日期**/
	public static Date getDateByDateMonth(Date date, int month) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH,month);
		return ca.getTime();
	}
	/**根据日期和差的旬数返回新的日期
	 * @throws Exception **/
	public static Date getDateByDatePeriod(Date date, int period) throws Exception {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int day = ca.get(Calendar.DAY_OF_MONTH);
		if(-1==period){
			//向前移一旬
			if(1==day){
				ca.add(Calendar.MONTH,-1);
				ca.set(Calendar.DAY_OF_MONTH, 21);
			}else if(11==day){
				ca.set(Calendar.DAY_OF_MONTH, 1);
			}else if(21==day){
				ca.set(Calendar.DAY_OF_MONTH, 11);
			}else{
				throw new Exception("旬开始时间不合法");
			}
		}else if(1==period){
			//先后移一旬
			if(1==day){
				ca.set(Calendar.DAY_OF_MONTH, 11);
			}else if(11==day){
				ca.set(Calendar.DAY_OF_MONTH, 21);
			}else if(21==day){
				ca.add(Calendar.MONTH,1);
				ca.set(Calendar.DAY_OF_MONTH, 1);
			}else{
				throw new Exception("旬开始时间不合法");
			}
		}else{
			throw new Exception("旬转换周期不正确！");
		}

		return ca.getTime();
	}
	/**根据日期和差的周数返回新的日期**/
	public static Date getDateByDateWeek(Date date, int week) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.WEEK_OF_YEAR,week);
		return ca.getTime();
	}
	/////////////////////////////////////////////////
	/**根据日期和差的秒数返回新的日期**/
	public static Date getDateByNewSecond(Date date, int second) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int thisSecond = ca.get(Calendar.SECOND);
		thisSecond = (thisSecond)+second;
		ca.set(Calendar.SECOND, thisSecond);
		return ca.getTime();
	}
	/**根据日期和差的小时数返回新的日期**/
	public static Date getDateByNewHour(Date date, int hour) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.HOUR_OF_DAY,hour);
		return ca.getTime();
	}
	/**根据日期和差的天数返回新的日期**/
	public static Date getDateByNewDay(Date date, int day) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH,day);
		return ca.getTime();
	}
	/**根据日期和差的年数返回新的日期**/
	public static Date getDateByNewYear(Date date, int year) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.YEAR,year);
		return ca.getTime();
	}
	/**根据日期和差的月数返回新的日期**/
	public static Date getDateByNewMonth(Date date, int month) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.MONTH,month-1);
		return ca.getTime();
	}
	/**根据日期和差的周数返回新的日期**/
	public static Date getDateByNewWeek(Date date, int week) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.WEEK_OF_YEAR,week);
		return ca.getTime();
	}
	/////////////////////////////////////////////////////////////////
	/**根据日期计算两个日期相差天数**/
	public static int getContrastDay(Date startDate, Date endDdate) {
		long ca0 = startDate.getTime();
		long ca1 = endDdate.getTime();
		ca0 = ca0-ca1;
		int contrastDay = Integer.parseInt(Long.toString(ca0/1000/60/60/24));
		return contrastDay;
	}
	/**
	 * 获取to_date('date','yyyymmddhh24miss')语法的SQL语句
	 * @param date 日期字符串，为空返回null
	 * @return
	 */
	public static String getToDateSql(String date){
		return getToDateSql(date,null);
	}
	/**
	 * 获取to_date('date','yyyymmddhh24miss')语法的SQL语句
	 * @param date 日期字符串
	 * @param defDate 如果date为空,使用此默认值代替，多用于sysdate
	 * @return
	 */
	public static String getToDateSql(String date,String defDate){
		String sqlString="";
		if(StringUtils.isBlank(date)){
			if(StringUtils.isBlank(defDate)||defDate.equals("null")){
				sqlString="null";
			}else if(StringUtils.indexOfIgnoreCase(defDate, "sysdate")>-1){
				sqlString=defDate;
			}else{
				sqlString="to_date('" + StringDateTo14(defDate) + "','yyyymmddhh24miss')";
			}
		}else{
			if(StringUtils.indexOfIgnoreCase(date, "sysdate")>-1){
				sqlString=date;
			}else{
				sqlString="to_date('" + StringDateTo14(StringDateReplace(date))+ "','yyyymmddhh24miss')";
			}
		}
		return sqlString+" ";
	}	
	
	
    public static java.sql.Date strDate2SqlDate(String strDate) {
    	String date=DateTools.StringDateTo14(strDate);
    	return new java.sql.Date(Date14ToDate(date).getTime());
    }
    
    public static java.sql.Timestamp strDate2SqlTime(String strDate) {
    	String date=DateTools.StringDateTo14(strDate);
    	return new java.sql.Timestamp(Date14ToDate(date).getTime());
    }
    
    
	
	public static void main(String args[]) throws ParseException{
		System.out.println(DateTools.getToDateSql("sysdate+15", null));
		System.out.println(strDate2SqlDate("2012-01-01"));
		
		//日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        //当前系统日期
//		Date currentTime = formatter.parse("20140709");
//		Date endTime = DateTools.Date19ToDate("2014-08-14 20:00:03"); 
        Date currentTime = DateTools.Date10ToDate(DateTools.getSysDate10());
        Date endTime = DateTools.Date10ToDate("2014-10-14");
		for(int i=0;i<5000;i++){
			System.out.println("currentTime.getTime()="+currentTime.getTime()+"--DateTools.getSysDate10()="+DateTools.getSysDate10());
			currentTime = DateTools.getSysDate();
			System.out.println(endTime.getTime() + "-" + currentTime.getTime() + "=" + (endTime.getTime() - currentTime.getTime()) + ":" + DateCompareDate(endTime,currentTime));
		}
	}
}