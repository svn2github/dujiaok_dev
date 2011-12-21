/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.0
 * @author fengchun
 * @date 2009-1-5
 */

/**
 * 浏览器键码表
 * 
 * @namespace xui.util
 * @singleton Key
 */
xui.util.Key={
	"backspace": 8, 
	"tab": 9, 
	"enter": 13, 
	"shift": 16, 
	"ctrl": 17, 
	"alt": 18, 
	"pause": 19, 
	"caps": 20, 
	"esc": 27,
	"pageup": 33, 
	"pagedown": 34, 
	"end": 35, 
	"home": 36, 
	"left": 37, 
	"up": 38, 
	"right": 39, 
	"down": 40,
	"insert": 45, 
	"delete": 46,
	"f1": 112, 
	"f2": 113, 
	"f3": 114, 
	"f4": 115, 
	"f5": 116, 
	"f6": 117, 
	"f7": 118, 
	"f8": 119, 
	"f9": 120, 
	"f10": 121, 
	"f11": 122, 
	"f12": 123  
}

/**
 * 键盘工具类
 * 
 * @namespace xui.util
 * @singleton KeyBoard
 */
xui.util.KeyBoard={
	/**
	 * 初始化（按数组顺序）设置一组对象的TabIndex属性。
	 * @param {Array} ids
	 */
	initTabIndex:function(ids){
		if(!ids) return;
		for(var i=0,len=ids.length;i<len;i++){
			var obj = $(ids[i]);
			if(!obj) continue;
			obj.setAttribute('tabIndex',i+1);
		}
	},
	/**
	 * 初始化监听Enter键当作Tab键。
	 * 注意：执行本函数前，保证当前页面的TabIndex已经初始化设置完毕
	 */
	initEnterTab:function(){
		xui.util.Event.on(document,'keyup',function(){
			var key=event.keyCode;
			if(key!=xui.util.Key['enter']) return;
			
			var curTabIndex=event.srcElement.tabIndex;
			if(!curTabIndex || curTabIndex<1) curTabIndex=0;
			var els = document.getElementsByTagName("*");
			for(var i=0,len=els.length;i<len;i++){   
		        var obj = els[i];	
				if(obj.tabIndex==(curTabIndex+1)){
					obj.focus();return;
				}
			}
		});
	},
	_events0:{},
	_events1:{},
	/**
	 * 监听某个键被按下
	 * @param {Int} key 按键值
	 * @param {Function} fun 监听函数
	 */
	onKey:function(key,fun){
		if(key) this._events0["key_"+key]=fun;
	},
	/**
	 * 监听某个键+Ctrl键一起被按下
	 * @param {Int} key 按键值
	 * @param {Function} fun 监听函数
	 */
	onKeyAndCtrl:function(key,fun){
		if(key) this._events1["key_"+key]=fun;
	},
	/**
	 * 初始化设置所有热键监听函数
	 */
	initHotKey:function(){
		xui.util.Event.on(document,'keydown',function(){
			var key=event.keyCode;
			if (event.ctrlKey&&(key==78 || key==69)){//屏蔽原Ctrl+N,Ctrl+E 
			     event.returnValue=false;  
			}
		})
			
		xui.util.Event.on(document,'keyup',function(){
			var key=event.keyCode;
			var a = xui.util.KeyBoard;
			
			var fun0 = a._events0["key_"+key];
			if(fun0) fun0.call();
			
			var fun1 = a._events1["key_"+key];
			if(fun1 && event.ctrlKey) fun1.call();
		})
	}
}