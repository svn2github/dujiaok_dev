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
 * Cookie工具类
 * 
 * @namespace xui.util
 * @singleton Cookie
 */
xui.util.Cookie = function(){
	return {
		/**
		 * @constant {String} Cookie的KEY前缀
		 */
		NAME_SPACE: 'XUI_COOKIE_',
		/**
		 * 写Cookie键值
		 * @param {String} key 键
		 * @param {String} value 值
		 * @param {Date} oExpires 过期日期
		 * @param {Date} path 路径，缺省为根路径
		 */
		write: function(key, value, oExpires, path){
			if(!key) return;
			var p =  path? path : '/' ; 
			var exp = oExpires?oExpires.toGMTString():'Tus, 2 Sep 2049 11:11:11 GMT';
			document.cookie = this.NAME_SPACE + key + '=' + escape(''+value) + '; path=' +p+ '; expires=' + exp;
		},
		/**
		 * 读取Cookie键值
		 * @param {String} key 键
		 * @return {String} 值
		 */
		read: function(key){
			var reg = new RegExp("(^| )" + this.NAME_SPACE + key+"=([^;]*)(;|$)","gi");
			
			var v = document.cookie.replace(/(^\s*)|(\s*$)/g, '');
 			var data = reg.exec(v);	
			return data? (data[2]==''?null:unescape(data[2])):null;
		},
		/**
		 * 清空Cookie键值
		 * @param {String} key
		 */
		clear: function(key){
			this.write(key, '');
		}
	}
}();