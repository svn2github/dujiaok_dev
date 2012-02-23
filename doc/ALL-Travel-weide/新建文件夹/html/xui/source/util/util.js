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
 * @author fengchun
 * @date 2009-11-5
 */

/**
 * Ajax异步提交工具类
 * 注意：提交的服务器必须与当前使用页面同域名（跨域问题）。
 * 
 * @namespace xui.util
 * @singleton Ajax
 */
xui.util.Ajax = {
	/**
	 * 异步GET请求
	 * @param {String} url 服务器地址
	 * @param {Function} successFun 成功后回调函数
	 * @param {Function} failureFun 失败后回调函数
	 */
	asyncGET: function(url,successFun,failureFun){
		var http = this._createHTTP();
		http.onreadystatechange = function(){					
			if(http.readyState==4){
				if(http.status==200) {
					xui.util.Ajax._callback(successFun, http.responseText);
				}else{
					xui.util.Ajax._callback(failureFun, http.responseText);				
				}
			}			
		};
		http.open('get',xui.util.Ajax._newURL(url),true);
		http.send(null);
	},
	_iframeCallback:{},
	_iframeOnload:function(id){
		try{
			xui.util.Ajax._callback(xui.util.Ajax._iframeCallback[id], window.frames[id].document.body.innerHTML);
		}catch(e){};
	},
	/**
	 * 异步POST请求
	 * 
	 * @param {String} url 服务器地址
	 * @param {String} d 数据
	 * @param {Function} successFun 成功后回调函数
	 * @param {Function} failureFun 失败后回调函数
	 */
	asyncPOST: function(url,d,successFun,failureFun){				
		var http = this._createHTTP();
		http.onreadystatechange = function(){					
			if(http.readyState==4){
				if(http.status==200) {
					xui.util.Ajax._callback(successFun, http.responseText);
				}else{
					xui.util.Ajax._callback(failureFun, http.responseText);				
				}
			}			
		};
		
		http.open("POST", url, true);
		http.setRequestHeader("cache-control","no-cache"); 
    	http.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded;");
    
    	var data = 'ts='+new Date().getTime()+"&"+d;	
    	http.setRequestHeader("Content-Length",data.length);		
		http.send(data);
	},
	/**
	 * 异步POST提交表单
	 * 
	 * @param {String} url 服务器地址
	 * @param {String|Object} f 表单ID或对象
	 * @param {Function} successFun 成功后回调函数
	 */
	asyncPOSTForm: function(url,f,successFun){				
		var form = typeof f=='string'?$(f):f;
		var id = $UUID('xaj_');
		xui.util.Ajax._iframeCallback[id] = successFun;
			
		var _layer = document.createElement('span');
		_layer.style.display = 'none';
		_layer.innerHTML = '<iframe id="'+id+'" name="'+id+'" src="javascript:false;" onload="xui.util.Ajax._iframeOnload(this.id);" style="width:0px;height:0px;"></iframe>';
	    document.body.appendChild(_layer);
			
		form.action=url;
		form.target=id;
		form.method='post';
		form.submit();
		
	},
	/**
	 * 同步GET请求
	 * @param {String} url 服务器地址
	 */
	syncGet:function(url){
		var http = this._createHTTP();
		http.open('get',xui.util.Ajax._newURL(url),false);
		http.setRequestHeader("cache-control","no-cache"); 
		//http.setRequestHeader("If-Modified-Since","0");
		
		http.send(null);			
		if(http.readyState==4){ 
			if(http.status==200){
				return xui.util.Ajax._trim(http.responseText);
			}
			else{
				return null;
			} 
		}
	},
	/**
	 * 异步GET请求（支持跨域代理）
	 * @param {Object} attrs 格式:{url:'',vName:'返回的JS变量名',charset:''} 
	 * @param {Function} callback 成功后回调函数
	 */
	asyncGETProxy: function(attrs, callback){
		var script = document.createElement('script');
		script.setAttribute('type','text/javascript');
		if (attrs['charset']) script.setAttribute('charset', attrs['charset']); 
		
		var url = attrs['url'];
		if(attrs['vName']){
			url += (url.indexOf('?')>-1?'':'?') + '&_xui_ajax_callback='+attrs['vName'];
		}
		script.src = xui.util.Ajax._newURL(url);

					
		if (document.all) {
			script.onreadystatechange = function(){
				var rs = this.readyState;
				if ("loaded" === rs || "complete" === rs) {								
					try{						
					xui.util.Ajax._callback(callback, eval(attrs['vName']));
					}catch(e){}
					this.onreadystatechange = null;
					this.parentNode.removeChild(this);
				}				
			}
		} else {
			script.async = true;
			script.onload = function() {				
				try{						
				xui.util.Ajax._callback(callback, eval(attrs['vName']));
				}catch(e){}
				this.onreadystatechange = null;
				this.parentNode.removeChild(this);
			};
		}
		
		document.body.appendChild(script);
	},
	/**
	 ,
	syncPost:function(url,data){
		var http = this._createHTTP();
		http.open('post',xui.util.Ajax._newURL(url),false);
		http.setRequestHeader("cache-control","no-cache"); 
		//http.setRequestHeader("If-Modified-Since","0");
		http.send(data);			
		if(http.readyState==4){ 
			if(http.status==200){
				return http.responseText;
			}
			else{
				return null;
			} 
		}
	}*/
	_createHTTP:function(){
		var http;
		try {
		     http = new XMLHttpRequest();
		} catch (e) {
			try {
		       http = new ActiveXObject("Msxml2.XMLHTTP");
		    } catch (e) {
		    	try {
		        	http = new ActiveXObject("Microsoft.XMLHTTP");
		        } catch (e) {
		        	http = null;
		        }  
		    }
		}
		return http;
	},
	_newURL: function(url){
		if(url.indexOf('?')>0){
			s = url+'&ts='+ new Date().getTime();
		}else{
			s = url+'?ts='+ new Date().getTime();
		}		
		return s;
	},
	_callback: function(oFunction, res){
	  	if(oFunction) oFunction.call(this,xui.util.Ajax._trim(res));
	},
	/**
	 * 去除字符串两边空格
	 */
	_trim: function(str){		
	    if(str && str.length > 0){
			return str.replace(/(^\s*)|(\s*$)/g, '');
		}		
		return '';		
	}
}/**
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
}();/**
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
}/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.0
 * @author shengding
 * @date 2007-8-30
 */

/**
 * JSON工具类
 * 
 * @namespace xui.util
 * @singleton JsonUtil
 */
xui.util.JsonUtil = function(){
	 var useHasOwn = {}.hasOwnProperty ? true : false;
    
    // crashes Safari in some instances
    //var validRE = /^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/;
    
    var pad = function(n) {
        return n < 10 ? "0" + n : n;
    };
    
    var m = {
        "\b": '\\b',
        "\t": '\\t',
        "\n": '\\n',
        "\f": '\\f',
        "\r": '\\r',
        '"' : '\\"',
        "\\": '\\\\'
    };

    var encodeString = function(s){
        if (/["\\\x00-\x1f]/.test(s)) {
            return '"' + s.replace(/([\x00-\x1f\\"])/g, function(a, b) {
                var c = m[b];
                if(c){
                    return c;
                }
                c = b.charCodeAt();
                return "\\u00" +
                    Math.floor(c / 16).toString(16) +
                    (c % 16).toString(16);
            }) + '"';
        }
        return '"' + s + '"';
    };
    var encodeDate = function(o){
        return '"' + o.getFullYear() + "-" +
                pad(o.getMonth() + 1) + "-" +
                pad(o.getDate()) + "T" +
                pad(o.getHours()) + ":" +
                pad(o.getMinutes()) + ":" +
                pad(o.getSeconds()) + '"';
    };
    

    var encodeArray = function(o){
    	var a = ["["], b, i, l = o.length, v;
        for (i = 0; i < l; i += 1) {
            v = o[i];
            switch (typeof v) {
                case "undefined":
                case "function":
                case "unknown":
                    break;
                default:
                    if (b) {
                        a.push(',');
                    }
                    a.push(v === null ? "null" : xui.util.JsonUtil.encode(v));
                    b = true;
            }
        }
        a.push("]");
        return a.join("");
    };

	return {
		/**
		 * 解码：JSON文本转为JSON对象
	     * @param {String} sJson The JSON string
	     * @return {Object} The resulting object
		 */
		decode :function(sJson){
			return eval("(" + sJson + ')');
		},
		/**
		 * 编码：JS对象转为JSON文本
		 * @param {Object} o JS对象
		 * @return {String} The JSON string
		 */
		encode : function(o){
			if(typeof o == "undefined" || o === null){
			            return "null";
			        }else if(o instanceof Array){
			            return encodeArray(o);
			        }else if(o instanceof Date){
			            return encodeDate(o);
			        }else if(typeof o == "string"){
			            return encodeString(o);
			        }else if(typeof o == "number"){
			            return isFinite(o) ? String(o) : "null";
			        }else if(typeof o == "boolean"){
			            return String(o);
			        }else {
			            var a = ["{"], b, i, v;
			            for (i in o) {
			                if(!useHasOwn || o.hasOwnProperty(i)) {
			                    v = o[i];
			                    switch (typeof v) {
			                    case "undefined":
			                    case "function":
			                    case "unknown":
			                        break;
			                    default:
			                        if(b){
			                            a.push(',');
			                        }
			                        a.push(this.encode(i), ":",
			                                v === null ? "null" : this.encode(v));
			                        b = true;
			                    }
			                }
			            }
			            a.push("}");
			            return a.join("");
			        }
		}
	}
}();/**
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
}/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.0
 * @author fengchun
 * @date 2007-7-7
 * 
 * @version 1.1
 * @author shengding
 * @date 2009-11-5
 
 * @version 1.3
 * @author shengding
 * @date 2010-2-22 
 */

/**
 * 精确浮点运算工具类：完全解决JS的浮点运算问题，包括科学计数法格式的浮点数的加减乘除运算。
 * 注意：
 * 1）请注意运算时数字过大或过小（超过当前JS引擎允许的最大/最小数），JS引擎可能会返回异常
 * 2）若使用0或Null作为除数，会报“除数不能为零”错误。
 * 3）请注意：过大或过小的数字（比如：一个去掉小数点的数字的文本以6个零开头）可能被JS自动标记为科学计数法格式。
 * 4）NumberUtil类的加减乘除运算方法直接支持输入：科学计数法格式的数字。
 * 5）如需要将科学计数法格式的数字转换成正常格式，请使用toString方法。
 * 
 * @namespace xui.util
 * @singleton NumberUtil
 */
xui.util.NumberUtil = {
	/**
	 * 精确的加法运算
	 * @param {Number|String} s1 被加数
	 * @param {Number|String} s2 加数
	 * @param {String} opt 加号或减号:'+'或'-'
	 * @return {Number} 
	 */
	add: function(s1, s2, opt){
		if(!opt) opt = '+';
		s1 = this.toNumber(s1);
		s2 = this.toNumber(s2);
		
		if(this.isInt(s1) && this.isInt(s2)) return eval('s1'+opt+'s2');
		s1=this.toString(s1);s2=this.toString(s2);
		
		var m = Math.pow(10,Math.max(this.getDigitsLength(s1),this.getDigitsLength(s2)));
		var n1=this.multiply(s1,m);
		var n2=this.multiply(s2,m);		
		
		return (!opt || opt=='-')?this.divide(n1-n2,m):this.divide(n1+n2,m);
	},
	/**
	 * 精确的减法运算
	 * @param {Number|String} s1 被减数
	 * @param {Number|String} s2 减数
	 * @return {Number} 
	 */
	subtract: function(s1, s2){
		return this.add(s1, s2, '-');
	},
	_insertString: function(s,ch,pos){
		return s.substring(0,pos)+ch+s.slice(pos);
	},
	/**
	 * 将数字转换为String：支持科学计数法格式的数字
	 * @param {Number|String} s
	 * @return {String} 
	 */
	toString: function(s){
		if(s==null || typeof s == 'undefined') return '';		
		var n = this.toNumber(s);
		var t = n.toString().toLowerCase();
		if(t.indexOf('e')<=0) return t;	
		
		var sNum = Math.abs(n).toString();
		var dotP = sNum.indexOf('.');
		var eP = sNum.indexOf('e');
		
		var rNum = sNum.substring(0,eP);
		var power = sNum.slice(eP+1); 
		if(power=='' || power=='+' || power=='-' || this.isZero(power)){//如果是零则直接返回
			return (Math.abs(n)!=n)?'-'+rNum:rNum;
		};		
		
		var tempNum = rNum.replace('.','').replace(/0/g,'');
		var digitLen = dotP<0?0:eP-dotP-1;
		
		var zero=[];pLen=Math.abs(power);
		if(this.isPlus(power)){
			for(var i=0;i<pLen;i++){
				zero.push('0');
			}			
			tempNum = tempNum+zero.join('');
			if(digitLen>0) tempNum = this._insertString(tempNum,'.',tempNum.length-digitLen);
		}else{
			for(var i=0,len=pLen+digitLen;i<len;i++){
				zero.push('0');				
			}
			tempNum = zero.join('')+tempNum;
			tempNum = this._insertString(tempNum,'.',tempNum.length-digitLen-pLen);
		}
		//去掉两头多余的零
		tempNum=tempNum.replace(/^0+\./g,'0.');
		tempNum=tempNum.replace(/0+$/g,'');
		return (Math.abs(n)!=n)?'-'+tempNum:tempNum;
	},
	/**
	 * 精确的乘法运算
	 * @param {Number|String} s1 被乘数
	 * @param {Number|String} s2 乘数
	 * @return {Number} 
	 */
	multiply: function(s1, s2){
		s1 = this.toNumber(s1);
		s2 = this.toNumber(s2);
		if(s2 == 0 || s1 == 0) return 0;
		if(this.isInt(s1) && this.isInt(s2)) return s1*s2;
		if(s1===1) return s2; if(s2===1) return s1;
		
		var m=0;s1=this.toString(s1);s2=this.toString(s2);
		try{m+=s1.split(".")[1].length}catch(e){};    
		try{m+=s2.split(".")[1].length}catch(e){};    
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);		
	},
	/**
	 * 精确的除法运算
	 * @param {Number|String} s1 被除数
	 * @param {Number|String} s2 除数：不能为0或NULL
	 * @return {Number} 
	 */
	divide: function(s1, s2){
		s1 = this.toNumber(s1);
		s2 = this.toNumber(s2);		
		if(s1==0) return 0;
		if(s2==0) {
			alert('除数不能为零');
			return null;
		}
		s1=this.toString(s1);s2=this.toString(s2);
		
		var m1 = this.getDigitsLength(s1);
		var m2 = this.getDigitsLength(s2);
		var n1 = Number(s1.replace('.',''));
		var n2 = Number(s2.replace('.',''));
		
		return this.multiply(n1/n2,Math.pow(10,m2-m1));		
	},
	/**
	 * 转换为数字
	 * @param {Object} s
	 * @return {Number} 
	 */
	toNumber: function(s){
		if(!this.isNumber(s)) return 0;
		if(typeof s == 'string') {
			var f = parseFloat(s.replace(/,/g, ""));
			return isNaN(f)?0:f;
		}else{
			return Number(s);
		}		
	},
	/**
	 * 转换为整数。
	 * 注意：浮点数字的舍入依据参数mode的值。
	 *
	 * @param {Number|String} s
	 * @param {Int} mode 浮点数的舍入模式（缺省=0），取值范围：0=四舍五入；1=全入；2=全舍
	 * @return {Int} 整数
	 */
	toInt: function(s, mode){
		if(this.isInt(s)) return s;
		if(!mode) mode = 0;
		
		switch(mode){
			case 0: return Math.round(this.toNumber(s)); break;
			case 1: return Math.ceil(this.toNumber(s));break;
			case 2: return Math.floor(this.toNumber(s));break;
		} 		
	},
	/**
	 * 是否是数字
	 * @param {Number|String} s
	 * @return {Boolean} 
	 */
	isNumber: function(s){		
		if(s==null || typeof s == 'undefined') return false;
		if(typeof s == 'number'){
			return true;
		}else if(typeof s == 'string'){
			if(s.toLowerCase().indexOf('e')>=0) return !isNaN(s);
			if(s.indexOf(',')>0){
				var p0 = s.lastIndexOf(',');
				var p1 = s.lastIndexOf('.');
				if(p0 > p1 && p1 > -1) return false;
			}
			var n = s.replace(/,/g, ""); 
			return (!n || isNaN(n))?false:true;
		}
		return false;
	},
	/**
	 * 是否是浮点数
	 * @param {Number|String} s
	 * @return {Boolean}
	 */
	isFloat: function(s){
		if(!this.isNumber(s)) return false;
		
		s = s.toString();		
		var f = s.replace(/,/g, "");
		if(f.indexOf('.') >= 0) return true;
		return false;
	},
	/**
	 * 是否是整数
	 * @param {Number|String} s
	 * @return {Boolean}
	 */
	isInt: function(s){
		if(!this.isNumber(s)) return false;
		return !this.isFloat(s);
	},
	/**
	 * 是否是零
	 * @param {Number|String} s
	 * @return {Boolean}
	 */
	isZero: function(s){
		if(!this.isNumber(s)) return false;
		return parseFloat(s)==0;
	},
	/**
	 * 是否是负数（零不是负数）
	 * @param {Number|String} s
	 * @return {Boolean}
	 */
	isNegative: function(s){
		if(!this.isNumber(s)) return false;
		var n = this.toNumber(s);
		return !this.isZero(s) && Math.abs(n)!=n;
	},
	/**
	 * 是否是正数（零不是正数）
	 * @param {Number|String} s
	 * @return {Boolean}
	 */
	isPlus: function(s){
		if(!this.isNumber(s)) return false;
		var n = this.toNumber(s);
		return !this.isZero(s) && Math.abs(n)==n;
	},
	/**
	 * 取反数
	 * @param {Number|String} s
	 * @return {Number} 
	 */
	reverseValue: function(s){
		if(!this.isNumber(s)) return 0;
		
		if(this.isNegative(s)){
			return this.toNumber(s.toStirng().slice(1));
		};
		
		return this.toNumber('-'+s);
	},
	/**
	 * 取绝对值
	 * @param {Number|String} s
	 * @return {Number} 
	 */ 
	absValue: function(s){
		if(!this.isNumber(s)) return 0;
		return Math.abs(s);
	},
	/**
	 * 取小数长度
	 * @param {Number|String} s
	 * @return {Int} 
	 */
	getDigitsLength: function(s){
		if(!this.isNumber(s)) return -1;
		if(this.isInt(s)) return 0;
		
		s = s.toString();
		var d = s.split('.')[1];
		return d?d.toString().length:0;
	},
	/**
	 * 取整数位长度
	 * @param {Number|String} s
	 * @return {Int} 
	 */
	getIntegersLength: function(s){
		if(!this.isNumber(s)) return -1;
		return this.toInt(s).toString().length;
	},	
	/**
	 * 四舍五入
	 * @param {Number|String} s
	 * @param {Int} digit 保留小数位数
	 * @return {Number} 
	 */
	round: function(s, digit){
		if(!this.isNumber(s)) return 0;
		var n = this.toNumber(s);
		if(this.isInt(n)) return n;
	
		if(!digit){
			digit = 0;
		}else if(!this.isInt(digit) || digit < 0) {
			digit = 3;
		}
		return xui.util.NumberUtil.toNumber(n.toFixed(digit));
	},
	/**
	 * 格式化金额数字（为文本）
	 * @param {Number|String} s
	 * @param {String} pattern 格式:"#,###.###"
	 * @param {Boolean} forceZero 如果为整数的时候，强制在小数位补0
	 * @return {String}
	 */
	formatMoney: function(s, pattern,forceZero){ 
		if(!this.isNumber(s)) return '0';
		if(!pattern) pattern = '#,###.###';
		var p = pattern.split('.');
		var digit = parseInt(p[1].length);
		if(forceZero){
			var sNum = this.round(s, digit).toFixed(digit).toString();
		}else{
			var sNum = this.round(s, digit).toString();
		}
		if(p[0].indexOf(',') > 0){
			var isNeg = false;
			if(this.isNegative(sNum)){
				isNeg = true;
				sNum = sNum.slice(1);
			}
			
			var iLen = (this.isFloat(sNum))?sNum.indexOf('.'):sNum.length;
			if(iLen <= digit) return sNum;
			var arr = [];
			var pos1 = 0;
			var pos2 = iLen % 3;
			while(pos2 <= sNum.length){
				var sSlice = sNum.slice(pos1,pos2);
				if(sSlice) arr[arr.length] = sSlice;
				
				pos1 = pos2;
				if(pos2 == (iLen-3)){
					pos2 = sNum.length;
				}else{
					pos2+=3;
				}					
			}
			return ((isNeg)?'-':'')+arr.join(',');
		}		
		return sNum;
	},
	/**
	 * 使用"+-*\"操作符，作两数运算
	 * @param {Number|String} v1
	 * @param {String} opt 操作符："+-*\"
	 * @param {Number|String} v2
	 * @return {Number}
	 */
	calc:function(v1,opt,v2){
		var rst = null;
		switch(opt){
			case '+': rst = this.add(v1, v2);break;
			case '-': rst = this.subtract(v1, v2);break;
			case '*': rst = this.multiply(v1, v2);break;
			case '\\': rst = this.divide(v1, v2);break;
		}
		return rst;
	},
	/**
	 * 按照输入顺序，简单作"+-*\"四则运算
	 * 例如：xui.util.NumberUtil.arth(v1, "+", v2, "-", v1, "-", v2);
	 * @return {Number}
	 */
	arth:function(){
		if(arguments==null || arguments.length<=0) return null;
		
		var result = null;
		for (var i = 1; i < arguments.length; i=i+2){
			if(i==1){
				result = this.calc(arguments[i-1],arguments[i],arguments[i+1]);
			}else{
				result = this.calc(result,arguments[i],arguments[i+1]);
			}			
		}
		
		return result;
	}
	
}
/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.0
 * @author fengchun
 * @author shengding
 * @date 2007-8-30
 * 
 * @version 1.1
 * @author shengding
 * @date 2009-11-5
 */

/**
 * String工具类
 * 
 * @namespace xui.util
 * @singleton StringUtil
 */
xui.util.StringUtil = {
	/**
	 * 去除字符串两边空格
	 * @param {String} str
	 * @return {String}
	 */
	trim: function(str){		
	    if(str && str.length > 0){
			return str.replace(/(^\s*)|(\s*$)/g, '');
		}		
		return '';		
	},
	/**
	 * 字符串是否为空
	 * @param {String} str
	 * @return {Boolean}
	 */
	isEmpty: function(str){	    
		return (str || str.length > 0)?false:true;		
	},
	/**
	 * 字符串开头判断
	 * @param {String} str
	 * @param {String} startText
	 * @return {Boolean}
	 */
	startsWith: function(str,startText){
		return str && str.indexOf(startText) == 0;
	},
	/**
	 * 取字符串第n位的字符
	 * @param {String} str 字符串
	 * @param {Int} n 第N位
	 * @return {String} 一个字符
	 */
	getChar: function(str, n){
		return str.substring(n, n+1);
	},
	/**
	 * 替换首字符
	 * @param {String} str 原字符串
	 * @param {String} rChar 替换字符
	 * @return {String} 替换后的字符串
	 */
	replaceFirstChar: function(str, rChar){
		return rChar + str.slice(1);
	},
	/**
	 * 截取两个字符串之间的文本
	 * @param {String} str 原字符串
	 * @param {String} start 开始文本
	 * @param {String} end 结束文本
	 * @return {String} 截取后的字符串
	 */
	getBetweenText: function(str,start,end){
		return str.substring(str.indexOf(start)+start.length,str.indexOf(end));
	},
	/**
	 * 截取开头至某文本之间的文本
	 * @param {String} str 原字符串
	 * @param {String} text 某文本
	 * @return {String} 截取后的字符串
	 */
	getBeforeText: function(str, text){
		if(str.indexOf(text)<0) return str;
		return str.substring(0,str.indexOf(text));
	},
	/**
	 * 字符串转化成字符数组
	 * @param {String} str
	 * @return {Array} 
	 */
	toChars: function(str){
		if(!str || str.length <= 0) return null;
		var _chars = [];
		for(var i=0; i < str.length; i++){
			_chars[i] = xui.util.StringUtil.getChar(str,i);
		};
		return _chars;
	},
	/**
	 * 转换字符串为整数。字符串为空，则返回0。
	 * @param {String} str
	 * @return {Int}
	 */
	parseInt: function(str){
		str = '' + str;
		return (str && str.length > 0)?parseInt(str,10):0;
	}	,
	/**
	 * 替换字符
	 * @param {String} str 原字符串
	 * @param {String} s1 目标字符
	 * @param {String} s2 替换字符
	 * @return {String} 替换后的字符串
	 */
	replaceAll:function(str,s1,s2){
		return str.replace(new RegExp(s1,"g"),s2);
	},
	/**
	 * 算出字符串的长度。注意：中文字符算2个长度
	 * @param {String} str
	 * @return {Int}
	 */
	byteLength: function(str){
  		return str.replace(/[^\x00-\xff]/g,'aa').length;
 	},
	/**
	 * 替换位置n上的字符
	 * @param {String} str 原字符串
	 * @param {Char} rChar 替换字符
	 * @param {Int} n 位置
	 * @return {String} 替换后的字符串
	 */	
	replaceCharByPostion:function(str,rChar,n){
		return str.substring(0, n-1)+rChar+str.substring(n);
	},
	/**
	 * &，<，",>转义
	 * @param {String} value
	 * @return {String}
	 */
  htmlEncode : function(value){
      return !value ? value : String(value).replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/"/g, "&quot;");
  },
	/**
	 * &gt;，&quot;&quo，&amp;t>转义
	 * @param {String} value
	 * @return {String}
	 */
  htmlDecode : function(value){
      return !value ? value : String(value).replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, '"').replace(/&amp;/g, "&");
  },
  /**
	 * 过滤HTML特殊字符
	 * @param {String} str
	 * @return {String}
	 */	
	stripTags: function(str) {
		if(!str || typeof str!='string') return '';
		return str.replace(/<\/?[^>]+>/gi, '');
	},
	/**
	 * HTML特殊字符转义
	 * @param {String} str
	 * @return {String}
	 */
	escapeHTML: function(str) {
		if(!str || typeof str!='string') return '';
		var div = document.createElement('div');
		var text = document.createTextNode(str);
		div.appendChild(text);
		return div.innerHTML;
	},
	/**
	 * HTML特殊字符反转义
	 * @param {String} str
	 * @return {String}
	 */
    unescapeHTML: function(str) {
		if(!str || typeof str!='string') return '';
		var div = document.createElement('div');
		div.innerHTML = this.stripTags(str);
		return div.childNodes[0] ? div.childNodes[0].nodeValue : '';
	},	
	/**
	 * 截取字符串长度 兼容双字节
	 * @param {String} pstr
	 * @param {Number} num
	 * @param {String} str
	 * @return {String}
	 */
	btSub : function(pstr,num,str){
		if(pstr===null||pstr==='')return '';
		var s = pstr.match(/(.{1})/g),k=[],laststr='',count=0;
		if(str)laststr = str;
		for(var i=0; i<num; i++){
			k.push(s[count]);
			count++;
			if(!/^[\u0000-\u00ff]$/.test(s[count])){i++;}
		}
		return k.join('')+laststr;
	}

};
/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * @requires util/xui-win-util.js
 * @requires core/xui-number-util.js
 * 
 * @version 1.0
 * @author fengchun
 * @author shengding
 * @date 2007-8-30
 * 
 * @version 1.1
 * @author fengchun
 * @date 2009-11-4
 
 * @version 1.3
 * @author shengding
 * @date 2010-2-21
 */

/**
 * 校验工具类
 * 
 * @namespace xui.util
 * @singleton Validator
 */
xui.util.Validator = function (){
	var REG_EXPRESS = {
		FULLNUMBER :  /^\d+$/,
		EMAIL :/^\w+(((-|&)\w*)|(\.\w+))*\@[A-Za-z0-9]+((\-)[A-Za-z0-9]*|(\.)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
		EMAIL_DOMAIN:/^@[A-Za-z0-9]+((\-)[A-Za-z0-9]*|(\.)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
		YYYY_MM_DD :/^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/,
		HALFANGLE: /^[\u0000-\u00FF]+$/,
		ENTIREANGLE_CHARS: /^[\uFF00-\uFFFF]+$/,
		ENTIREANGLE_FULL : /^[\u0391-\uFFE5]+$/,
		ENGLISH : /^[A-Za-z]+$/,
		NUMORENG: /^[A-Za-z\d]+$/,
		IP : /^(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5])$/	
	};	
	
	/**
	 * 判断正则表达式
	 * @param {String} sStr 判断字符串
	 * @param {String} sRegexp 正则表达式
	 */ 
	var _validateByReg = function (sStr,sRegexp){
		sStr = _trim(sStr);			
		if(sStr && ( new RegExp (sRegexp) ) && (new RegExp (sRegexp)).test(sStr)) return true ; 
		return false;
	};
	/**
	 * 去掉字符串两边空格
	 * @param {Object} str
	 */
	var _trim = function(str){		
	    if(str && str.length > 0){
			return str.replace(/(^\s*)|(\s*$)/g, '');
		}		
		return '';		
	};
	var _isArray = function(obj){
		return (typeof obj=='object') && obj.constructor==Array;
	}
	/**
	 * 校验INPUT
	 * @param {Object|String} ipt
	 * @param {Function} onerror
  	 * @param {Boolean} emptyPassed	空值是否被允许，默认false。	 如果设置了true，空值就会通过验证
	 */
	var _validateInput = function (ipt, onerror, emptyPassed){
		var sId, oInput;
		if(xui.lang.isString(ipt)) {
			sId = ipt;
			oInput = $(sId);
		}else{
			oInput = ipt;
			sId = ipt.id;
		}
		
		var value = xui.util.WinUtil.getInputValue(sId);
		//如果允许空值通过
		if(emptyPassed && (!value || value=='')) {//@since 1.2
			return true;
		} 
		var eps = oInput.getAttribute('validate');
		if(!eps) return true;
		
		var rules;
		try{
			rules = eval('(' + eps + ')');
		}catch(e){
			alert('[xui-util-Validator]<'+sId+'> error');
			return;
		}
		if(!rules) return true;
		if(!_isArray(rules)){
			alert('[xui-util-Validator]<'+sId+'> error');
			return;
		}
		
		for(i in rules){
			var rule = rules[i];
			if(rule && rule.check){
				var is = true;
				var callback = eval('xui.util.Validator.'+rule.check);
				if(callback && (typeof callback=='function')){
					try{
						is = (rule.format)?callback.call(xui.util.Validator, value, rule.format):callback.call(xui.util.Validator, value);
					}catch(e){}
				}
				if(!is){
					if(!onerror) return is;
					try{onerror.call(null,sId,rule,value);}catch(e){
						alert('[xui-util-Validator]<'+sId+'> error');
					}
					return is;
				}
			}
		}
		return true;
	};
	
	
	return {
		/**
		 * 设置HTML标签控件的校验规则表达式。
		 * @param {String|Object} input 控件Id或对象
		 * @param {String} rule 校验规则
		 */
		setExpress: function(input, rule){
			var oInput = (typeof input == 'string')?$(input):input;
			if(oInput) oInput.setAttribute('validate',rule);
		},
			
		/**
		 * 字符串是否是非空
		 * @param {String} sStr
		 * @return {Boolean}
		 */
		isNotEmpty :function (sStr){
			if(!sStr || !sStr instanceof String) return false;
			return sStr.length > 0;
		},
		/**
		 * 字符串是否是空
		 * @param {String} sStr
		 * @return {Boolean}
		 */
		isEmpty : function (sStr){
			return !this.isNotEmpty(sStr);
		},
		/**
		 * 判断日期格式
		 * @param {String} sStr 格式：'yyyy-mm-dd'
		 * @return {Boolean}
		 */
		isDate : function (sStr){
			if(!sStr) return false;
			return  _validateByReg(_trim(sStr),REG_EXPRESS.YYYY_MM_DD);			
		},
		/**
		 * 判断邮件地址
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isEmail : function (sStr){
			if(!sStr) return false;
			return _validateByReg(_trim(sStr),REG_EXPRESS.EMAIL);						
		},
		/**
		 * 判断是否邮件后缀
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isEmailDomain : function (sStr){
			if(!sStr) return false;
			return _validateByReg(_trim(sStr),REG_EXPRESS.EMAIL_DOMAIN);						
		},
		/**
		 * 判断全数字组成
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isFullNumber : function (sStr) {
			if(!sStr) return false;
			return _validateByReg(_trim(sStr),REG_EXPRESS.FULLNUMBER);				
		},
		/**
		 * 判断数字。以下格式都是数字：'-21.33','+6,700.','0.0'。
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isNumber : function (sStr) {
			if(!sStr) return false;
			return xui.util.NumberUtil.isNumber(_trim(sStr));				
		},
		/**
		 * 判断正数字（零不是正数）。以下格式都是正数：'+21.33','+6,700.'。
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isPlusNumber : function (sStr) {
			if(!sStr) return false;
			return xui.util.NumberUtil.isPlus(_trim(sStr));				
		},
		/**
		 * 判断负数字（零不是负数）。以下格式都是负数：'-21.33','-6,700.'。
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isNegativeNumber : function (sStr) {
			if(!sStr) return false;
			return xui.util.NumberUtil.isNegative(_trim(sStr));				
		},
		/**
		 * 判断整数。以下格式都是整数：'-21','+6,700'。
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isInt : function (sStr) { 
			if(!sStr) return false;
			return xui.util.NumberUtil.isInt(_trim(sStr));				
		},		
		/**
		 * 判断是否实数
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isFloat  : function (sStr){
			if(!sStr) return false;
			return xui.util.NumberUtil.isFloat(_trim(sStr));				
		},
		/**
		 * 所有字符都是：半角字符
		 * @param {String} sStr
		 * @return {Boolean}
		 */
		isHalfAngle:function(sStr){
			if(!sStr) return false;
			return _validateByReg(sStr,REG_EXPRESS.HALFANGLE);		
		},
		/**
		 * 所有字符都是：全角字符(包括：中文或非中文全角字符)
		 * @param {String} sStr
		 * @return {Boolean}
		 */
		isFullEntireAngle:function(sStr){
			if(!sStr) return false;
			return _validateByReg(sStr,REG_EXPRESS.ENTIREANGLE_FULL);		
		},
		/**
		 * 所有字符都是：全角非中文字符
		 * @param {String} sStr
		 * @return {Boolean}
		 */
		isFullEntireAngleAndNoChinese:function(sStr){
			if(!sStr) return false;
			return _validateByReg(sStr,REG_EXPRESS.ENTIREANGLE_CHARS);		
		},
		/**
		 * 所有字符都是：中文字符
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */ 
//		isFullChinese : function (sStr) {
//			if(!sStr) return false;
//			return this.isFullEntireAngle(sStr) && !this.isEntireAngleOfChars(sStr);						
//		},
		/**
		 * 包含中文字符：只要包含一个中文字符就返回TRUE
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */ 
//		hasChinese : function (sStr) {
//			if(!sStr) return false;
//			sStr = _trim(sStr);
//			
//			for(var i=0;i<sStr.length;i++){
//				if(this.isFullChinese(sStr.charAt(i))) return true;
//			}
//			return false;						
//		},
		/**
		 * 包含全角字符：只要包含一个全角字符（全角标点+中文字符）就返回TRUE。
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */ 
		hasEntireAngle : function (sStr) {
			if(!sStr) return false;
			sStr = _trim(sStr);
			
			for(var i=0;i<sStr.length;i++){
				if(this.isFullEntireAngle(sStr.charAt(i))) return true;
			}
			return false;						
		},		
		/**
		 * 检查数字的格式（长度）
		 * @param {String} sStr
		 * @param {String} sFormat 格式："{整数位长度}.{小数位长度}"。例如：".2","5.3"
		 * @return {Boolean} 
		 */
		isFormatNumber: function(sStr, sFormat){
			sStr = _trim(sStr);
			if(!sStr) return false;
			if(!sFormat || !this.isNumber(sStr)) return false;
			var f = sFormat.split('.');
			
			var iLength = xui.util.NumberUtil.getIntegersLength(sStr);
			var dLength = xui.util.NumberUtil.getDigitsLength(sStr);
			
			f[0]=f[0]?f[0]:0;
			f[1]=f[1]?f[1]:0;
			
			//检查整数位
			if(f[0]>0 && iLength>f[0]) return false;
			//检查小数位
			if(f[1]>0 && dLength>f[1]) return false;
			return true;
		},
		/**
		 * 检查文本（非字节）长度范围：[0,len]
		 * @param {String} str
		 * @param {Int} len 最大长度
		 * @return {Boolean}
		 */
		isTextLength:function(str,len){
			if(!str || len<1) return false;
			return str.length>len?false:true;
		},
		/**
		 * 检查文本字节长度范围：[0,len]
		 * @param {String} str
		 * @param {Int} len 最大字节长度
		 * @return {Boolean}
		 */
		isTextByteLength:function(str,len){
			if(!str || len<1) return false;
			return str.replace(/[^\x00-\xff]/g,'aa').length>len?false:true;
		},
		/**
		 * 判断英文字符串
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */ 
		isEnglish : function (sStr) {
			if(!sStr) return false;
			return  _validateByReg(sStr,REG_EXPRESS.ENGLISH);						
		},
		/**
		 * 判断IP地址
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isIP : function (sStr){
			if(!sStr) return false;			
			return  _validateByReg(sStr,REG_EXPRESS.IP);				
		},		
		/**
		 * 判断数字或者英文字母
		 * @param {String} sStr 判断字符串
		 * @return {Boolean}
		 */
		isNumOrEng :function (sStr){
			if(!sStr) return false;
			return _validateByReg(sStr,REG_EXPRESS.NUMORENG);		
		},
		/**
		 * 校验单个HTML标签控件
		 * @param {Object|String} input 控件对象或Id
		 * @param {Function} onError 校验失败的监听函数
  	 * @param {Boolean} emptyPassed	空值是否被允许，默认false。	 如果设置了true，空值就会通过验证
		 * @return {Boolean} 校验成功或失败
		 */
		checkInput :function(input, onerror, emptyPassed){
			return _validateInput(input, onerror, emptyPassed);
		},
		/**
		 * 校验表单中的以下元素：<text>,<hidden>,<radio>,<checkbox>,<select>,<textarea>,<password>,<file>
		 * @param {Object|String} formId 表单Id
		 * @param {Object} onError 校验失败的监听函数
  	 * @param {Boolean} emptyPassed	空值是否被允许，默认false。	 如果设置了true，空值就会通过验证
		 * @return {Boolean} 全部校验成功或某一次失败
		 */
		checkForm : function (formId, onError, emptyPassed){
			var oForm = $(formId);
			var els = oForm.elements;
			var rst = true;

			for (var i=0,len=els.length;i<len;i++){
				if(!_validateInput(els[i], onError, emptyPassed)) rst = false;
			}			
			return rst;
		}
	}
	
}();

/**
 * 校验机器人类：能（按照规则配置）自动完成校验以及错误提示功能
 * 
 * @namespace xui.util
 * @singleton Vobot
 */
xui.util.Vobot=function(){
	return {
		/**
		 * @field {Function} 校验提示的监听函数
		 */
		onError:null,
		_configs:{},
		/**
		 * 设置Validator类的某个方法的提示文案
		 * @param {String} name 方法名
		 * @param {String} template 提示文案模板
		 */
		addTemplate:function(type,template){
			this._configs[type]=template;
		},
		/**
		 * 设置HTML标签控件的校验规则
		 * @param {String} id HTML标签控件Id
		 * @param {String} rule 校验规则
		 * @param {Object} label HTML标签控件的名称（可选项）
		 */
		setExpress:function(id,rule,label){
			var obj = $(id);
			xui.util.Validator.setExpress(obj,rule);
			if(label) obj.setAttribute('label',label);
		},
		_onError:function(id,rule,value){
			var a = this;
			if(a.onError){
				var express = a._configs[rule.check];
				if(!express) return;						
				if(express.indexOf('?')>-1){
					var label = $(id).getAttribute('label');
					express = express.replace('?',label?label:'');
					if('isFormatNumber'==rule.check){
						var format=rule.format.split('.');
						express = express.replace('?',format[0]?format[0]:'*');
						express = express.replace('?',format[1]?format[1]:'*');						
					}else {
						express = express.replace('?',rule.format);					
					}
				}
				a.onError.call(null,id,express);
			}
		},
		/**
		 * 自动校验表单
		 * @param {String} formId 表单Id
  	 * @param {Boolean} emptyPassed	空值是否被允许，默认false。	 如果设置了true，空值就会通过验证
		 * @return {Boolean} 全部校验成功或某一次失败
		 */
		checkForm: function(formId, emptyPassed){
			var a = this;
			return xui.util.Validator.checkForm(formId,function(id,rule,value){
				a._onError(id,rule,value);
			},emptyPassed)
		},
		/**
		 * 校验单个HTML标签控件
		 * @param {Object|String} input 控件对象或Id
  	 * @param {Boolean} emptyPassed	空值是否被允许，默认false。	 如果设置了true，空值就会通过验证
		 * @return {Boolean} 校验成功或失败
		 */
		checkInput:function(ipt, emptyPassed){
			var a = this;
			return xui.util.Validator.checkInput(ipt,function(id,rule,value){
				a._onError(id,rule,value);
			},emptyPassed)
		}
		
	}
}();
/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.0
 * @author fengchun
 * @author shengding
 * @date 2007-8-30
 * 
 * @version 1.1
 * @author shengding
 * @date 2009-11-5
 */

/**
 * 浏览器工具类
 * 
 * @namespace xui.util
 * @singleton WinUtil
 */
xui.util.WinUtil = {  
	/**
	 * 打开定制窗口
	 * @param {String} url
	 * @param {int} width
	 * @param {int} height
	 * @param {Object} oStyle
	 */
	openWindow: function(url,width,height,oStyle) {
		var w=width?width:640, h=height?height:480;
		
		var left=0,top=0;
		if(oStyle && oStyle.left){
			left = oStyle.left;
		}else{
			left = parseInt((window.screen.availWidth-w)/2);
		}
		if(oStyle && oStyle.top){
			top = oStyle.top;
		}else{
			top = parseInt((window.screen.availHeight-h)/2);
		}
		
		var str = [];
		str.push('width=' + w);
		str.push('height=' + h);
		str.push('left=' + left);
		str.push('top=' + top);
		
		str.push('scrollbars=' + ((oStyle && oStyle.scrollbars) ? oStyle.scrollbars : 'yes'));
		str.push('resizable=' + ((oStyle && oStyle.resizable) ? oStyle.resizable : 'yes'));
		str.push('status=' + ((oStyle && oStyle.status) ? oStyle.status : 'no'));
		
		var name = (oStyle && oStyle.name) ? oStyle.name : '_blank';
		var sArg = str.join(',');
		sArg = sArg + ',toolbar=no,directories=no,menubar=no';
		return window.open(url,name,sArg);
	},	
	/**
	 * 打开全屏窗口
	 * @param {String} url
	 * @param {Object} oStyle
	 */
	openFullWindow: function(url,oStyle) {		
		return this.openWindow(url,window.screen.availWidth,window.screen.availHeight,oStyle);
	},
	/**
	 * 选中第N个Option
	 * @param {Object} oSelect
	 * @param {Int} n
	 */
	selectOption: function(oSelect, index){     
	    if(!oSelect) return; 
		if(!index || index < 0) index = 0;
    	oSelect.options[index].selected = true;         
    },
	/**
	 * 加载Iframe内容。注意：参数Url不传入，则重载Iframe
	 * 
	 * @param {Object|String} oIframe iframe索引对象（如:window.frames['name']）或iframe的ID
	 * @param {String} url 
	 */
	loadIframe: function (oIframe,url){
		var obj = xui.lang.isString(oIframe)?window.frames[oIframe]:oIframe;
		if(!obj) return;
		obj.location.href = url?url:obj.location.href ;	
	},
	/**
	 * 取得各类HTML标签控件的值。
	 * 注意：
	 * 1）所有对象必须有id
	 * 2）支持的HTML标签控件:<text>,<hidden>,<radio>,<checkbox>,<select>,<textarea>,<password>,<file>
	 * 3）当type=radio|checkbox，对象必须有name属性
	 * 
	 * @param {String} id  对象ID或对象
	 * @return (String|Array) 如为单选控件，返回String；如为多选控件，返回Array[]；
	 */
	getInputValue: function(id){
		var oElement = xui.lang.isString(id)?$(id):id;
		if(!oElement) return null;
		
		var tagName = oElement.tagName?oElement.tagName.toLowerCase():null; 
		if(!tagName) return null;
		
		var v = '';		
		switch(tagName){
			case 'input':if(oElement.type=='radio'){
					oElement = $N(oElement.name);
					for (var i=0;i<oElement.length ;i++){
						if(oElement[i].checked) v = oElement[i].value;
					}
				}else if(oElement.type=='checkbox'){
					oElement = $N(oElement.name);
					v = [];
					for (var i=0;i<oElement.length ;i++){
						if(oElement[i].checked) v[v.length] = oElement[i].value;
					}
				}else{v = oElement.value;};break;
			case 'select':if(oElement.type=='select-one'){
					if (oElement.selectedIndex!=-1) v = oElement.options[oElement.selectedIndex].value;
				}else if(oElement.type=='select-multiple'){
					v = [];
					for (var i=0;i<oElement.length ;i++){
						if(oElement[i].selected) v[v.length] = oElement[i].value;
					}
				};break;
			case 'textarea':v = oElement.value;break;
		}
		return v;
	},
	_onoffSwitch:function(id){
		var display = $(id).style.display;
		if(display == 'none'){
			$(id).style.display='';
		}else{
			$(id).style.display='none';
		}
	},
	/**
	 * 显示/隐藏对象
	 * @param {String} id
	 */
	onoffSwitch:function(id){
		if(xui.lang.isArray(id)){
			for(var i=0;i<id.length;i++){
				this._onoffSwitch(id[i]);
			}
		}else{
			this._onoffSwitch(id);
		}
	},
	
	/**
	 * 检查Form是否被修改。
	 * 注意：表单中的HTML标签控件含有old-value属性
	 * 
	 * @param {String} formId 表单Id
	 * @return {Boolean}
	 */
	isFormChanged:function(formId){
		var form = $(formId);
		for(var i=0;i<form.elements.length;i++){   
	        var obj = form.elements[i];
			if(!obj || !obj.id) continue;
			var oldValue = obj.getAttribute('old-value');
			if(oldValue==null || typeof oldValue=='undefined') continue;
			var f = xui.util.WinUtil.getInputValue(obj.id)==oldValue;
			//alert(obj.id+':'+xui.util.WinUtil.getInputValue(obj.id)+','+oldValue+'='+f)
			if(!f){return true;}
	  	}
		return false;
	},
	isIE:function(){
		return xui.env.ua.ie > 0 ? true :false;
	},
	isIE6:function(){
		return xui.env.ua.ie == 6? true :false;
	},
	isIE7:function(){
		return xui.env.ua.ie == 7? true :false;
	},
	isIE8:function(){
		return xui.env.ua.ie == 8? true :false;
	},
	isFF:function(){
		return xui.env.ua.gecko > 0 ? true :false;  
	}
}


/**
 * 基本HTMLElement元素类
 * @namespace xui.util
 * @singleton WinUtil
 * @param ele [String] Selector 选择符号
 * @param root 根节点
 * @param attr [Object] 设置参数对象
 *
 *
 *
 *
 */
xui.util.ElementUtil = function(ele,root,attr){
	 if(attr)this.attr = attr;
	 if(!root)root = document;
	 this.length = 0;
	 this.dom = $$(ele,root);
}
xui.util.ElementUtil.prototype = {
	attr:{
	    emptyColor:'#999',
		loop:true
	},
	/*
	*初始化空文本提示	
	*/
	initEmptyText:function(){
		
		for(var i=0, len=this.dom.length;i<len; i++){
			
			var et = xui.util.Dom.getAttribute(this.dom[i],'emptytext');
		
			if(et!=null){
				var valueColor =  xui.util.Dom.getStyle(this.dom[i],'color');
				xui.util.Dom.setAttribute(this.dom[i],'valueColor',valueColor);
				xui.util.Dom.addClass(this.dom[i],'xui-emptytext');
				xui.util.ElementUtil.emptyBlur(this.dom[i]);

				(function(obj,loop){
					
					xui.util.Event.on(obj,'focus',xui.util.ElementUtil.emptyFoucs);
					xui.util.Event.on(obj,'blur',xui.util.ElementUtil.emptyBlur);
					
					if(!loop){
						xui.util.Event.removeListener(obj,'blur',xui.util.ElementUtil.emptyBlur);
						xui.util.Event.on(obj,'focus',function(){
							xui.util.Event.removeListener(obj,'focus',xui.util.ElementUtil.emptyFoucs);
						});
					}

				})(this.dom[i],this.attr.loop);
			}
		}
		
	},
	/*
	* 更新设置参数对象
	*/
	setAttr:function(k){
		for(var i in k){
			this.attr[i] = k[i];
		}
	}
}
/*
* 空文本提示节点Blur事件
* @param es [Element] (可选) 需要执行此操作的节点对象
* @param ec [String]  (可选) 为空提示的文本颜色
*/
xui.util.ElementUtil.emptyBlur = function(es,ec){
		var color = '#999';
		if(ec)color = ec;
		var obj= this;
		if(es&&es.nodeType){obj=es;}

		var et = xui.util.Dom.getAttribute(obj,'emptytext');
		var v = xui.util.StringUtil.trim(obj.value);
		if(v==''){
			obj.value = et;
			obj.style.color = color;
		}	
	}
/*
* 空文本提示节点Foucs事件
* @param es [Element] (可选) 需要执行此操作的节点对象
* 
*/
xui.util.ElementUtil.emptyFoucs = function(es){
		var obj= this;
		if(es&&es.nodeType){obj=es;}
		var et = xui.util.Dom.getAttribute(obj,'emptytext');
		var v = xui.util.StringUtil.trim(obj.value);
		var color = xui.util.Dom.getAttribute(obj,'valueColor');
		xui.util.Dom.removeClass(obj,'xui-emptytext');
		if(v==et){
			obj.value = '';
			obj.style.color = color;
		}
	}
/*
* 获取被初始化过的节点的VALUE值
* @param ele [Element] 需要执行获取值的某个节点对象
*/
xui.util.ElementUtil.getValue = function(ele){
		var et = xui.util.Dom.getAttribute(ele,'emptytext');
		var v = xui.util.StringUtil.trim(ele.value);
		if(v==et){
			return '';
		}
		return v;
	}

