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
			alert('[xui-util-Validator]对象<'+sId+'>的validate属性解析错误，请检查！');
			return;
		}
		if(!rules) return true;
		if(!_isArray(rules)){
			alert('[xui-util-Validator]对象<'+sId+'>的validate属性格式错误，请检查！');
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
						alert('[xui-util-Validator]检查对象<'+sId+'>错误后，回调函数执行异常，请检查！');
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