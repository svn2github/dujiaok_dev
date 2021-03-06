package com.ssnn.dujiaok.web.velocity.toolbox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具
 * @author langben 2012-1-13
 *
 */
public class DateUtilToolbox {

	public String format(Date date , String format){
		if(date == null)
			return null ;
		return new SimpleDateFormat(format).format(date) ;
	}
	
	/**
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public Date addDays(Date date , int days){
		if(date == null)
			return null ;
		Calendar c = Calendar.getInstance() ;
		c.setTime(date) ;
		c.add(Calendar.DATE, days) ;
		return c.getTime() ;
	}
	
	public String addDays(Date date , int days , String format){
		if(date == null)
			return null ;
		Date d = addDays(date , days) ;
		DateFormat df = new SimpleDateFormat(format) ;
		return df.format(d) ;
	}
	
	public Date now(){
		return new Date() ;
	}
	
	public boolean before(Date t1 , Date base){
		if(t1 == null)
			return false ;
		return t1.before(base) ;
	}
	
	public boolean after(Date t1 , Date base){
		if(t1 == null)
			return false ;
		return t1.after(base) ;
	}
	
	public boolean beforeNow(Date t){
		if(t == null)
			return false ;
		return t.before(new Date()) ;
	}
	
	public boolean afterNow(Date t){
		if(t == null)
			return false ;
		return t.after(new Date()) ;
	}
	
	public boolean afterDays(Date t , int days){
		if(t == null){
			return false ;
		}
		Calendar c = Calendar.getInstance() ;
		c.add(Calendar.DATE, 7) ;
		if(t.after(c.getTime())){
			return true ;
		}
		return false ;
	}
}
