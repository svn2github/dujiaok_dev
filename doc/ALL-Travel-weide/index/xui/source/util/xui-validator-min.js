xui.util.Validator=function(){var REG_EXPRESS={FULLNUMBER:/^\d+$/,EMAIL:/^\w+(((-|&)\w*)|(\.\w+))*\@[A-Za-z0-9]+((\-)[A-Za-z0-9]*|(\.)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,EMAIL_DOMAIN:/^@[A-Za-z0-9]+((\-)[A-Za-z0-9]*|(\.)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,YYYY_MM_DD:/^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/,HALFANGLE:/^[\u0000-\u00FF]+$/,ENTIREANGLE_CHARS:/^[\uFF00-\uFFFF]+$/,ENTIREANGLE_FULL:/^[\u0391-\uFFE5]+$/,ENGLISH:/^[A-Za-z]+$/,NUMORENG:/^[A-Za-z\d]+$/,IP:/^(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5])$/};var _validateByReg=function(sStr,sRegexp){sStr=_trim(sStr);if(sStr&&(new RegExp(sRegexp))&&(new RegExp(sRegexp)).test(sStr)){return true}return false};var _trim=function(str){if(str&&str.length>0){return str.replace(/(^\s*)|(\s*$)/g,"")}return""};var _isArray=function(obj){return(typeof obj=="object")&&obj.constructor==Array};var _validateInput=function(ipt,onerror,emptyPassed){var sId,oInput;if(xui.lang.isString(ipt)){sId=ipt;oInput=$(sId)}else{oInput=ipt;sId=ipt.id}var value=xui.util.WinUtil.getInputValue(sId);if(emptyPassed&&(!value||value=="")){return true}var eps=oInput.getAttribute("validate");if(!eps){return true}var rules;try{rules=eval("("+eps+")")}catch(e){alert("[xui-util-Validator]\u7035\u7845\u8584<"+sId+">\u9428\u5249alidate\u705e\u70b4\ufffd\u0446\u0412\u93cb\u6130\u654a\u7487\ue224\u7d1d\u7487\u950b\ue5c5\u93cc\u30ef\u7d12");return}if(!rules){return true}if(!_isArray(rules)){alert("[xui-util-Validator]\u7035\u7845\u8584<"+sId+">\u9428\u5249alidate\u705e\u70b4\ufffd\u0444\u7278\u5bee\u5fdb\u654a\u7487\ue224\u7d1d\u7487\u950b\ue5c5\u93cc\u30ef\u7d12");return}for(i in rules){var rule=rules[i];if(rule&&rule.check){var is=true;var callback=eval("xui.util.Validator."+rule.check);if(callback&&(typeof callback=="function")){try{is=(rule.format)?callback.call(xui.util.Validator,value,rule.format):callback.call(xui.util.Validator,value)}catch(e){}}if(!is){if(!onerror){return is}try{onerror.call(null,sId,rule,value)}catch(e){alert("[xui-util-Validator]\u59ab\ufffd\u93cc\u30e5\ue1ee\u749e\ufffd"+sId+">\u95bf\u6b12\ue1e4\u935a\u5eaf\u7d1d\u9365\u70b6\u769f\u9351\u82a5\u669f\u93b5\u0446\ue511\u5bee\u509a\u7236\u951b\u5c83\ue1ec\u59ab\ufffd\u93cc\u30ef\u7d12")}return is}}}return true};return{setExpress:function(input,rule){var oInput=(typeof input=="string")?$(input):input;if(oInput){oInput.setAttribute("validate",rule)}},isNotEmpty:function(sStr){if(!sStr||!sStr instanceof String){return false}return sStr.length>0},isEmpty:function(sStr){return !this.isNotEmpty(sStr)},isDate:function(sStr){if(!sStr){return false}return _validateByReg(_trim(sStr),REG_EXPRESS.YYYY_MM_DD)},isEmail:function(sStr){if(!sStr){return false}return _validateByReg(_trim(sStr),REG_EXPRESS.EMAIL)},isEmailDomain:function(sStr){if(!sStr){return false}return _validateByReg(_trim(sStr),REG_EXPRESS.EMAIL_DOMAIN)},isFullNumber:function(sStr){if(!sStr){return false}return _validateByReg(_trim(sStr),REG_EXPRESS.FULLNUMBER)},isNumber:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isNumber(_trim(sStr))},isPlusNumber:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isPlus(_trim(sStr))},isNegativeNumber:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isNegative(_trim(sStr))},isInt:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isInt(_trim(sStr))},isFloat:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isFloat(_trim(sStr))},isHalfAngle:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.HALFANGLE)},isFullEntireAngle:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.ENTIREANGLE_FULL)},isFullEntireAngleAndNoChinese:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.ENTIREANGLE_CHARS)},hasEntireAngle:function(sStr){if(!sStr){return false}sStr=_trim(sStr);for(var i=0;i<sStr.length;i++){if(this.isFullEntireAngle(sStr.charAt(i))){return true}}return false},isFormatNumber:function(sStr,sFormat){sStr=_trim(sStr);if(!sStr){return false}if(!sFormat||!this.isNumber(sStr)){return false}var f=sFormat.split(".");var iLength=xui.util.NumberUtil.getIntegersLength(sStr);var dLength=xui.util.NumberUtil.getDigitsLength(sStr);f[0]=f[0]?f[0]:0;f[1]=f[1]?f[1]:0;if(f[0]>0&&iLength>f[0]){return false}if(f[1]>0&&dLength>f[1]){return false}return true},isTextLength:function(str,len){if(!str||len<1){return false}return str.length>len?false:true},isTextByteLength:function(str,len){if(!str||len<1){return false}return str.replace(/[^\x00-\xff]/g,"aa").length>len?false:true},isEnglish:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.ENGLISH)},isIP:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.IP)},isNumOrEng:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.NUMORENG)},checkInput:function(input,onerror,emptyPassed){return _validateInput(input,onerror,emptyPassed)},checkForm:function(formId,onError,emptyPassed){var oForm=$(formId);var els=oForm.elements;var rst=true;for(var i=0,len=els.length;i<len;i++){if(!_validateInput(els[i],onError,emptyPassed)){rst=false}}return rst}}}();xui.util.Vobot=function(){return{onError:null,_configs:{},addTemplate:function(b,a){this._configs[b]=a},setExpress:function(d,c,a){var b=$(d);xui.util.Validator.setExpress(b,c);if(a){b.setAttribute("label",a)}},_onError:function(h,f,d){var b=this;if(b.onError){var g=b._configs[f.check];if(!g){return}if(g.indexOf("?")>-1){var c=$(h).getAttribute("label");g=g.replace("?",c?c:"");if("isFormatNumber"==f.check){var e=f.format.split(".");g=g.replace("?",e[0]?e[0]:"*");g=g.replace("?",e[1]?e[1]:"*")}else{g=g.replace("?",f.format)}}b.onError.call(null,h,g)}},checkForm:function(d,c){var b=this;return xui.util.Validator.checkForm(d,function(f,e,a){b._onError(f,e,a)},c)},checkInput:function(d,c){var b=this;return xui.util.Validator.checkInput(d,function(f,e,a){b._onError(f,e,a)},c)}}}();