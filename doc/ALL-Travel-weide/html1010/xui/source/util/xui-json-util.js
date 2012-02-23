/**
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
}();