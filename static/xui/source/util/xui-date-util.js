/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.0
 * @author fengchun
 * @date 2007-8-30
 * 
 * @version 1.1
 * @author shengding
 * @date 2009-11-5
 */

/**
 * 日期工具类
 * 
 * @namespace xui.util
 * @singleton DateUtil
 */
xui.util.DateUtil = {
	/**
	 * 日期对象转为"yyyy-mm-dd"格式文本
	 * @param {Date} oDate
	 * @return {String} yyyy-mm-dd
	 */
	toYYYYMMDD: function(oDate){
		  var sMonth = (oDate.getMonth() + 1).toString();
		  var sDay = (oDate.getDate()).toString();
		  if(sMonth.length < 2) sMonth = '0' + sMonth; 
		  if(sDay.length < 2) sDay = '0' + sDay; 
		  
		  return oDate.getFullYear() + '-' + sMonth + '-' + sDay;
	},
	/**
	 * 取得年份
	 * @param {Date|String} oDate，支持yyyy-mm-dd格式
	 * @return {Int} 四位年份
	 */
	getYear: function(oDate){
		if(xui.lang.isString(oDate)) return parseInt(oDate.split('-')[0],10);
		return oDate.getFullYear();
	},
	
	/**
	 * 取得月份值(0-11)
	 * @param {Date|String} oDate，支持yyyy-mm-dd格式
	 * @return {Int} 月份值
	 */
	getMonth: function(oDate){
		if(xui.lang.isString(oDate)) return parseInt(oDate.split('-')[1],10)-1;
		return oDate.getMonth();
	},
	/**
	 * 取得月的天数(1-31)
	 * @param {Date|String} oDate，支持yyyy-mm-dd格式
	 * @return {Int} 天数
	 */
	getMonthDay: function(oDate){
		if(xui.lang.isString(oDate)) return parseInt(oDate.split('-')[2],10);
		return oDate.getDate();
	},	
	/**
	 * 取得小时数(0-23)
	 * @param {Date} oDate
	 * @return {Int} 小时数
	 */
	getHours:function(oDate){
		return oDate.getHours();
	},
	/**
	 * 取得分钟数(0-59)
	 * @param {Date} oDate
	 * @return {Int} 分钟数
	 */
	getMinutes:function(oDate){
		return oDate.getMinutes();
	},
	/**
	 * 是否是“yyyy-mm-dd”日期字符串
	 * @param {String} sDate
	 * @return {Boolean} 是或否
	 */
	isYYYYMMDD: function(sDate){
		if(!sDate || typeof sDate != 'string') return false;
		if(!(new RegExp (/^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/)).test(sDate)) return false;
		
		var y = this.getYear(sDate);
		if(y < 1970) return false;
		var m = this.getMonth(sDate);
		if(m < 0 || m > 11) return false;
		var d = this.getMonthDay(sDate);
		if(d < 1 || d > 31) return false;
		
		return true;
	},
	/**
	 * "yyyy-mm-dd"转换为日期对象。
	 * @param {Object} sYMD "yyyy-mm-dd"格式日期
	 * @return {Date}
	 */
	toDate: function(sYMD){
		if(!this.isYYYYMMDD(sYMD)) return null;
		return new Date(this.getYear(sYMD), this.getMonth(sYMD), this.getMonthDay(sYMD));
	},
	/**
	 * 比较两个日期的数值差
	 * @param {Date|String} oDate1
	 * @param {Date|String} oDate2
	 * @return {Int} 两个日期的毫秒差值
	 */
	compare: function(oDate1, oDate2){
		if(!oDate1 || !oDate2) return null;
		var o1 = (typeof oDate1 == 'string')?this.toDate(oDate1):oDate1;
		var o2 = (typeof oDate2 == 'string')?this.toDate(oDate2):oDate2;
		
		return Date.parse(o1) - Date.parse(o2);
	}
}