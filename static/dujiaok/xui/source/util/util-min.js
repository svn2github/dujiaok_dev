xui.util.Ajax={asyncGET:function(b,c,d){var a=this._createHTTP();a.onreadystatechange=function(){if(a.readyState==4){if(a.status==200){xui.util.Ajax._callback(c,a.responseText)}else{xui.util.Ajax._callback(d,a.responseText)}}};a.open("get",xui.util.Ajax._newURL(b),true);a.send(null)},_iframeCallback:{},_iframeOnload:function(b){try{xui.util.Ajax._callback(xui.util.Ajax._iframeCallback[b],window.frames[b].document.body.innerHTML)}catch(a){}},asyncPOST:function(b,g,c,f){var a=this._createHTTP();a.onreadystatechange=function(){if(a.readyState==4){if(a.status==200){xui.util.Ajax._callback(c,a.responseText)}else{xui.util.Ajax._callback(f,a.responseText)}}};a.open("POST",b,true);a.setRequestHeader("cache-control","no-cache");a.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded;");var e="ts="+new Date().getTime()+"&"+g;a.setRequestHeader("Content-Length",e.length);a.send(e)},asyncPOSTForm:function(a,d,b){var c=typeof d=="string"?$(d):d;var g=$UUID("xaj_");xui.util.Ajax._iframeCallback[g]=b;var e=document.createElement("span");e.style.display="none";e.innerHTML='<iframe id="'+g+'" name="'+g+'" src="javascript:false;" onload="xui.util.Ajax._iframeOnload(this.id);" style="width:0px;height:0px;"></iframe>';document.body.appendChild(e);c.action=a;c.target=g;c.method="post";c.submit()},syncGet:function(b){var a=this._createHTTP();a.open("get",xui.util.Ajax._newURL(b),false);a.setRequestHeader("cache-control","no-cache");a.send(null);if(a.readyState==4){if(a.status==200){return xui.util.Ajax._trim(a.responseText)}else{return null}}},asyncGETProxy:function(attrs,callback){var script=document.createElement("script");script.setAttribute("type","text/javascript");if(attrs.charset){script.setAttribute("charset",attrs.charset)}var url=attrs.url;if(attrs.vName){url+=(url.indexOf("?")>-1?"":"?")+"&_xui_ajax_callback="+attrs.vName}script.src=xui.util.Ajax._newURL(url);if(document.all){script.onreadystatechange=function(){var rs=this.readyState;if("loaded"===rs||"complete"===rs){try{xui.util.Ajax._callback(callback,eval(attrs.vName))}catch(e){}this.onreadystatechange=null;this.parentNode.removeChild(this)}}}else{script.async=true;script.onload=function(){try{xui.util.Ajax._callback(callback,eval(attrs.vName))}catch(e){}this.onreadystatechange=null;this.parentNode.removeChild(this)}}document.body.appendChild(script)},_createHTTP:function(){var a;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){a=null}}}return a},_newURL:function(a){if(a.indexOf("?")>0){s=a+"&ts="+new Date().getTime()}else{s=a+"?ts="+new Date().getTime()}return s},_callback:function(a,b){if(a){a.call(this,xui.util.Ajax._trim(b))}},_trim:function(a){if(a&&a.length>0){return a.replace(/(^\s*)|(\s*$)/g,"")}return""}};xui.util.Cookie=function(){return{NAME_SPACE:"XUI_COOKIE_",write:function(b,c,a,e){if(!b){return}var d=e?e:"/";var f=a?a.toGMTString():"Tus, 2 Sep 2049 11:11:11 GMT";document.cookie=this.NAME_SPACE+b+"="+escape(""+c)+"; path="+d+"; expires="+f},read:function(b){var c=new RegExp("(^| )"+this.NAME_SPACE+b+"=([^;]*)(;|$)","gi");var a=document.cookie.replace(/(^\s*)|(\s*$)/g,"");var d=c.exec(a);return d?(d[2]==""?null:unescape(d[2])):null},clear:function(a){this.write(a,"")}}}();xui.util.DateUtil={toYYYYMMDD:function(c){var a=(c.getMonth()+1).toString();var b=(c.getDate()).toString();if(a.length<2){a="0"+a}if(b.length<2){b="0"+b}return c.getFullYear()+"-"+a+"-"+b},getYear:function(a){if(xui.lang.isString(a)){return parseInt(a.split("-")[0],10)}return a.getFullYear()},getMonth:function(a){if(xui.lang.isString(a)){return parseInt(a.split("-")[1],10)-1}return a.getMonth()},getMonthDay:function(a){if(xui.lang.isString(a)){return parseInt(a.split("-")[2],10)}return a.getDate()},getHours:function(a){return a.getHours()},getMinutes:function(a){return a.getMinutes()},isYYYYMMDD:function(b){if(!b||typeof b!="string"){return false}if(!(new RegExp(/^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/)).test(b)){return false}var e=this.getYear(b);if(e<1970){return false}var a=this.getMonth(b);if(a<0||a>11){return false}var c=this.getMonthDay(b);if(c<1||c>31){return false}return true},toDate:function(a){if(!this.isYYYYMMDD(a)){return null}return new Date(this.getYear(a),this.getMonth(a),this.getMonthDay(a))},compare:function(b,a){if(!b||!a){return null}var d=(typeof b=="string")?this.toDate(b):b;var c=(typeof a=="string")?this.toDate(a):a;return Date.parse(d)-Date.parse(c)}};xui.util.JsonUtil=function(){var useHasOwn={}.hasOwnProperty?true:false;var pad=function(n){return n<10?"0"+n:n};var m={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r",'"':'\\"',"\\":"\\\\"};var encodeString=function(s){if(/["\\\x00-\x1f]/.test(s)){return'"'+s.replace(/([\x00-\x1f\\"])/g,function(a,b){var c=m[b];if(c){return c}c=b.charCodeAt();return"\\u00"+Math.floor(c/16).toString(16)+(c%16).toString(16)})+'"'}return'"'+s+'"'};var encodeDate=function(o){return'"'+o.getFullYear()+"-"+pad(o.getMonth()+1)+"-"+pad(o.getDate())+"T"+pad(o.getHours())+":"+pad(o.getMinutes())+":"+pad(o.getSeconds())+'"'};var encodeArray=function(o){var a=["["],b,i,l=o.length,v;for(i=0;i<l;i+=1){v=o[i];switch(typeof v){case"undefined":case"function":case"unknown":break;default:if(b){a.push(",")}a.push(v===null?"null":xui.util.JsonUtil.encode(v));b=true}}a.push("]");return a.join("")};return{decode:function(sJson){return eval("("+sJson+")")},encode:function(o){if(typeof o=="undefined"||o===null){return"null"}else{if(o instanceof Array){return encodeArray(o)}else{if(o instanceof Date){return encodeDate(o)}else{if(typeof o=="string"){return encodeString(o)}else{if(typeof o=="number"){return isFinite(o)?String(o):"null"}else{if(typeof o=="boolean"){return String(o)}else{var a=["{"],b,i,v;for(i in o){if(!useHasOwn||o.hasOwnProperty(i)){v=o[i];switch(typeof v){case"undefined":case"function":case"unknown":break;default:if(b){a.push(",")}a.push(this.encode(i),":",v===null?"null":this.encode(v));b=true}}}a.push("}");return a.join("")}}}}}}}}}();xui.util.Key={backspace:8,tab:9,enter:13,shift:16,ctrl:17,alt:18,pause:19,caps:20,esc:27,pageup:33,pagedown:34,end:35,home:36,left:37,up:38,right:39,down:40,insert:45,"delete":46,f1:112,f2:113,f3:114,f4:115,f5:116,f6:117,f7:118,f8:119,f9:120,f10:121,f11:122,f12:123};xui.util.KeyBoard={initTabIndex:function(c){if(!c){return}for(var b=0,a=c.length;b<a;b++){var d=$(c[b]);if(!d){continue}d.setAttribute("tabIndex",b+1)}},initEnterTab:function(){xui.util.Event.on(document,"keyup",function(){var d=event.keyCode;if(d!=xui.util.Key.enter){return}var f=event.srcElement.tabIndex;if(!f||f<1){f=0}var c=document.getElementsByTagName("*");for(var b=0,a=c.length;b<a;b++){var e=c[b];if(e.tabIndex==(f+1)){e.focus();return}}})},_events0:{},_events1:{},onKey:function(b,a){if(b){this._events0["key_"+b]=a}},onKeyAndCtrl:function(b,a){if(b){this._events1["key_"+b]=a}},initHotKey:function(){xui.util.Event.on(document,"keydown",function(){var a=event.keyCode;if(event.ctrlKey&&(a==78||a==69)){event.returnValue=false}});xui.util.Event.on(document,"keyup",function(){var e=event.keyCode;var c=xui.util.KeyBoard;var d=c._events0["key_"+e];if(d){d.call()}var b=c._events1["key_"+e];if(b&&event.ctrlKey){b.call()}})}};xui.util.NumberUtil={add:function(s1,s2,opt){if(!opt){opt="+"}s1=this.toNumber(s1);s2=this.toNumber(s2);if(this.isInt(s1)&&this.isInt(s2)){return eval("s1"+opt+"s2")}s1=this.toString(s1);s2=this.toString(s2);var m=Math.pow(10,Math.max(this.getDigitsLength(s1),this.getDigitsLength(s2)));var n1=this.multiply(s1,m);var n2=this.multiply(s2,m);return(!opt||opt=="-")?this.divide(n1-n2,m):this.divide(n1+n2,m)},subtract:function(b,a){return this.add(b,a,"-")},_insertString:function(b,a,c){return b.substring(0,c)+a+b.slice(c)},toString:function(o){if(o==null||typeof o=="undefined"){return""}var b=this.toNumber(o);var m=b.toString().toLowerCase();if(m.indexOf("e")<=0){return m}var f=Math.abs(b).toString();var c=f.indexOf(".");var d=f.indexOf("e");var l=f.substring(0,d);var a=f.slice(d+1);if(a==""||a=="+"||a=="-"||this.isZero(a)){return(Math.abs(b)!=b)?"-"+l:l}var k=l.replace(".","").replace(/0/g,"");var j=c<0?0:d-c-1;var h=[];pLen=Math.abs(a);if(this.isPlus(a)){for(var e=0;e<pLen;e++){h.push("0")}k=k+h.join("");if(j>0){k=this._insertString(k,".",k.length-j)}}else{for(var e=0,g=pLen+j;e<g;e++){h.push("0")}k=h.join("")+k;k=this._insertString(k,".",k.length-j-pLen)}k=k.replace(/^0+\./g,"0.");k=k.replace(/0+$/g,"");return(Math.abs(b)!=b)?"-"+k:k},multiply:function(c,b){c=this.toNumber(c);b=this.toNumber(b);if(b==0||c==0){return 0}if(this.isInt(c)&&this.isInt(b)){return c*b}if(c===1){return b}if(b===1){return c}var a=0;c=this.toString(c);b=this.toString(b);try{a+=c.split(".")[1].length}catch(d){}try{a+=b.split(".")[1].length}catch(d){}return Number(c.replace(".",""))*Number(b.replace(".",""))/Math.pow(10,a)},divide:function(e,c){e=this.toNumber(e);c=this.toNumber(c);if(e==0){return 0}if(c==0){alert("\u95c4\u3086\u669f\u6d93\u5d88\u5158\u6d93\u6d2a\u6d42");return null}e=this.toString(e);c=this.toString(c);var b=this.getDigitsLength(e);var a=this.getDigitsLength(c);var f=Number(e.replace(".",""));var d=Number(c.replace(".",""));return this.multiply(f/d,Math.pow(10,a-b))},toNumber:function(a){if(!this.isNumber(a)){return 0}if(typeof a=="string"){var b=parseFloat(a.replace(/,/g,""));return isNaN(b)?0:b}else{return Number(a)}},toInt:function(a,b){if(this.isInt(a)){return a}if(!b){b=0}switch(b){case 0:return Math.round(this.toNumber(a));break;case 1:return Math.ceil(this.toNumber(a));break;case 2:return Math.floor(this.toNumber(a));break}},isNumber:function(a){if(a==null||typeof a=="undefined"){return false}if(typeof a=="number"){return true}else{if(typeof a=="string"){if(a.toLowerCase().indexOf("e")>=0){return !isNaN(a)}if(a.indexOf(",")>0){var d=a.lastIndexOf(",");var b=a.lastIndexOf(".");if(d>b&&b>-1){return false}}var c=a.replace(/,/g,"");return(!c||isNaN(c))?false:true}}return false},isFloat:function(a){if(!this.isNumber(a)){return false}a=a.toString();var b=a.replace(/,/g,"");if(b.indexOf(".")>=0){return true}return false},isInt:function(a){if(!this.isNumber(a)){return false}return !this.isFloat(a)},isZero:function(a){if(!this.isNumber(a)){return false}return parseFloat(a)==0},isNegative:function(a){if(!this.isNumber(a)){return false}var b=this.toNumber(a);return !this.isZero(a)&&Math.abs(b)!=b},isPlus:function(a){if(!this.isNumber(a)){return false}var b=this.toNumber(a);return !this.isZero(a)&&Math.abs(b)==b},reverseValue:function(a){if(!this.isNumber(a)){return 0}if(this.isNegative(a)){return this.toNumber(a.toStirng().slice(1))}return this.toNumber("-"+a)},absValue:function(a){if(!this.isNumber(a)){return 0}return Math.abs(a)},getDigitsLength:function(a){if(!this.isNumber(a)){return -1}if(this.isInt(a)){return 0}a=a.toString();var b=a.split(".")[1];return b?b.toString().length:0},getIntegersLength:function(a){if(!this.isNumber(a)){return -1}return this.toInt(a).toString().length},round:function(a,c){if(!this.isNumber(a)){return 0}var b=this.toNumber(a);if(this.isInt(b)){return b}if(!c){c=0}else{if(!this.isInt(c)||c<0){c=3}}return xui.util.NumberUtil.toNumber(b.toFixed(c))},formatMoney:function(m,g,f){if(!this.isNumber(m)){return"0"}if(!g){g="#,###.###"}var a=g.split(".");var j=parseInt(a[1].length);if(f){var e=this.round(m,j).toFixed(j).toString()}else{var e=this.round(m,j).toString()}if(a[0].indexOf(",")>0){var b=false;if(this.isNegative(e)){b=true;e=e.slice(1)}var c=(this.isFloat(e))?e.indexOf("."):e.length;if(c<=j){return e}var d=[];var k=0;var h=c%3;while(h<=e.length){var l=e.slice(k,h);if(l){d[d.length]=l}k=h;if(h==(c-3)){h=e.length}else{h+=3}}return((b)?"-":"")+d.join(",")}return e},calc:function(d,a,c){var b=null;switch(a){case"+":b=this.add(d,c);break;case"-":b=this.subtract(d,c);break;case"*":b=this.multiply(d,c);break;case"\\":b=this.divide(d,c);break}return b},arth:function(){if(arguments==null||arguments.length<=0){return null}var a=null;for(var b=1;b<arguments.length;b=b+2){if(b==1){a=this.calc(arguments[b-1],arguments[b],arguments[b+1])}else{a=this.calc(a,arguments[b],arguments[b+1])}}return a}};xui.util.StringUtil={trim:function(a){if(a&&a.length>0){return a.replace(/(^\s*)|(\s*$)/g,"")}return""},isEmpty:function(a){return(a||a.length>0)?false:true},startsWith:function(b,a){return b&&b.indexOf(a)==0},getChar:function(a,b){return a.substring(b,b+1)},replaceFirstChar:function(b,a){return a+b.slice(1)},getBetweenText:function(b,c,a){return b.substring(b.indexOf(c)+c.length,b.indexOf(a))},getBeforeText:function(b,a){if(b.indexOf(a)<0){return b}return b.substring(0,b.indexOf(a))},toChars:function(c){if(!c||c.length<=0){return null}var a=[];for(var b=0;b<c.length;b++){a[b]=xui.util.StringUtil.getChar(c,b)}return a},parseInt:function(a){a=""+a;return(a&&a.length>0)?parseInt(a,10):0},replaceAll:function(c,b,a){return c.replace(new RegExp(b,"g"),a)},byteLength:function(a){return a.replace(/[^\x00-\xff]/g,"aa").length},replaceCharByPostion:function(b,a,c){return b.substring(0,c-1)+a+b.substring(c)},htmlEncode:function(a){return !a?a:String(a).replace(/&/g,"&amp;").replace(/>/g,"&gt;").replace(/</g,"&lt;").replace(/"/g,"&quot;")},htmlDecode:function(a){return !a?a:String(a).replace(/&gt;/g,">").replace(/&lt;/g,"<").replace(/&quot;/g,'"').replace(/&amp;/g,"&")},stripTags:function(a){if(!a||typeof a!="string"){return""}return a.replace(/<\/?[^>]+>/gi,"")},escapeHTML:function(b){if(!b||typeof b!="string"){return""}var c=document.createElement("div");var a=document.createTextNode(b);c.appendChild(a);return c.innerHTML},unescapeHTML:function(a){if(!a||typeof a!="string"){return""}var b=document.createElement("div");b.innerHTML=this.stripTags(a);return b.childNodes[0]?b.childNodes[0].nodeValue:""},btSub:function(c,b,g){if(c===null||c===""){return""}var e=c.match(/(.{1})/g),a=[],h="",f=0;if(g){h=g}for(var d=0;d<b;d++){a.push(e[f]);f++;if(!/^[\u0000-\u00ff]$/.test(e[f])){d++}}return a.join("")+h}};xui.util.Validator=function(){var REG_EXPRESS={FULLNUMBER:/^\d+$/,EMAIL:/^\w+(((-|&)\w*)|(\.\w+))*\@[A-Za-z0-9]+((\-)[A-Za-z0-9]*|(\.)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,EMAIL_DOMAIN:/^@[A-Za-z0-9]+((\-)[A-Za-z0-9]*|(\.)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,YYYY_MM_DD:/^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/,HALFANGLE:/^[\u0000-\u00FF]+$/,ENTIREANGLE_CHARS:/^[\uFF00-\uFFFF]+$/,ENTIREANGLE_FULL:/^[\u0391-\uFFE5]+$/,ENGLISH:/^[A-Za-z]+$/,NUMORENG:/^[A-Za-z\d]+$/,IP:/^(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5])$/};var _validateByReg=function(sStr,sRegexp){sStr=_trim(sStr);if(sStr&&(new RegExp(sRegexp))&&(new RegExp(sRegexp)).test(sStr)){return true}return false};var _trim=function(str){if(str&&str.length>0){return str.replace(/(^\s*)|(\s*$)/g,"")}return""};var _isArray=function(obj){return(typeof obj=="object")&&obj.constructor==Array};var _validateInput=function(ipt,onerror,emptyPassed){var sId,oInput;if(xui.lang.isString(ipt)){sId=ipt;oInput=$(sId)}else{oInput=ipt;sId=ipt.id}var value=xui.util.WinUtil.getInputValue(sId);if(emptyPassed&&(!value||value=="")){return true}var eps=oInput.getAttribute("validate");if(!eps){return true}var rules;try{rules=eval("("+eps+")")}catch(e){alert("[xui-util-Validator]<"+sId+"> error");return}if(!rules){return true}if(!_isArray(rules)){alert("[xui-util-Validator]<"+sId+"> error");return}for(i in rules){var rule=rules[i];if(rule&&rule.check){var is=true;var callback=eval("xui.util.Validator."+rule.check);if(callback&&(typeof callback=="function")){try{is=(rule.format)?callback.call(xui.util.Validator,value,rule.format):callback.call(xui.util.Validator,value)}catch(e){}}if(!is){if(!onerror){return is}try{onerror.call(null,sId,rule,value)}catch(e){alert("[xui-util-Validator]<"+sId+"> error")}return is}}}return true};return{setExpress:function(input,rule){var oInput=(typeof input=="string")?$(input):input;if(oInput){oInput.setAttribute("validate",rule)}},isNotEmpty:function(sStr){if(!sStr||!sStr instanceof String){return false}return sStr.length>0},isEmpty:function(sStr){return !this.isNotEmpty(sStr)},isDate:function(sStr){if(!sStr){return false}return _validateByReg(_trim(sStr),REG_EXPRESS.YYYY_MM_DD)},isEmail:function(sStr){if(!sStr){return false}return _validateByReg(_trim(sStr),REG_EXPRESS.EMAIL)},isEmailDomain:function(sStr){if(!sStr){return false}return _validateByReg(_trim(sStr),REG_EXPRESS.EMAIL_DOMAIN)},isFullNumber:function(sStr){if(!sStr){return false}return _validateByReg(_trim(sStr),REG_EXPRESS.FULLNUMBER)},isNumber:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isNumber(_trim(sStr))},isPlusNumber:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isPlus(_trim(sStr))},isNegativeNumber:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isNegative(_trim(sStr))},isInt:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isInt(_trim(sStr))},isFloat:function(sStr){if(!sStr){return false}return xui.util.NumberUtil.isFloat(_trim(sStr))},isHalfAngle:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.HALFANGLE)},isFullEntireAngle:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.ENTIREANGLE_FULL)},isFullEntireAngleAndNoChinese:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.ENTIREANGLE_CHARS)},hasEntireAngle:function(sStr){if(!sStr){return false}sStr=_trim(sStr);for(var i=0;i<sStr.length;i++){if(this.isFullEntireAngle(sStr.charAt(i))){return true}}return false},isFormatNumber:function(sStr,sFormat){sStr=_trim(sStr);if(!sStr){return false}if(!sFormat||!this.isNumber(sStr)){return false}var f=sFormat.split(".");var iLength=xui.util.NumberUtil.getIntegersLength(sStr);var dLength=xui.util.NumberUtil.getDigitsLength(sStr);f[0]=f[0]?f[0]:0;f[1]=f[1]?f[1]:0;if(f[0]>0&&iLength>f[0]){return false}if(f[1]>0&&dLength>f[1]){return false}return true},isTextLength:function(str,len){if(!str||len<1){return false}return str.length>len?false:true},isTextByteLength:function(str,len){if(!str||len<1){return false}return str.replace(/[^\x00-\xff]/g,"aa").length>len?false:true},isEnglish:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.ENGLISH)},isIP:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.IP)},isNumOrEng:function(sStr){if(!sStr){return false}return _validateByReg(sStr,REG_EXPRESS.NUMORENG)},checkInput:function(input,onerror,emptyPassed){return _validateInput(input,onerror,emptyPassed)},checkForm:function(formId,onError,emptyPassed){var oForm=$(formId);var els=oForm.elements;var rst=true;for(var i=0,len=els.length;i<len;i++){if(!_validateInput(els[i],onError,emptyPassed)){rst=false}}return rst}}}();xui.util.Vobot=function(){return{onError:null,_configs:{},addTemplate:function(b,a){this._configs[b]=a},setExpress:function(d,c,a){var b=$(d);xui.util.Validator.setExpress(b,c);if(a){b.setAttribute("label",a)}},_onError:function(h,f,d){var b=this;if(b.onError){var g=b._configs[f.check];if(!g){return}if(g.indexOf("?")>-1){var c=$(h).getAttribute("label");g=g.replace("?",c?c:"");if("isFormatNumber"==f.check){var e=f.format.split(".");g=g.replace("?",e[0]?e[0]:"*");g=g.replace("?",e[1]?e[1]:"*")}else{g=g.replace("?",f.format)}}b.onError.call(null,h,g)}},checkForm:function(d,c){var b=this;return xui.util.Validator.checkForm(d,function(f,e,a){b._onError(f,e,a)},c)},checkInput:function(d,c){var b=this;return xui.util.Validator.checkInput(d,function(f,e,a){b._onError(f,e,a)},c)}}}();xui.util.WinUtil={openWindow:function(b,c,l,k){var j=c?c:640,e=l?l:480;var d=0,g=0;if(k&&k.left){d=k.left}else{d=parseInt((window.screen.availWidth-j)/2)}if(k&&k.top){g=k.top}else{g=parseInt((window.screen.availHeight-e)/2)}var f=[];f.push("width="+j);f.push("height="+e);f.push("left="+d);f.push("top="+g);f.push("scrollbars="+((k&&k.scrollbars)?k.scrollbars:"yes"));f.push("resizable="+((k&&k.resizable)?k.resizable:"yes"));f.push("status="+((k&&k.status)?k.status:"no"));var a=(k&&k.name)?k.name:"_blank";var m=f.join(",");m=m+",toolbar=no,directories=no,menubar=no";return window.open(b,a,m)},openFullWindow:function(b,a){return this.openWindow(b,window.screen.availWidth,window.screen.availHeight,a)},selectOption:function(b,a){if(!b){return}if(!a||a<0){a=0}b.options[a].selected=true},loadIframe:function(c,a){var b=xui.lang.isString(c)?window.frames[c]:c;if(!b){return}b.location.href=a?a:b.location.href},getInputValue:function(e){var d=xui.lang.isString(e)?$(e):e;if(!d){return null}var c=d.tagName?d.tagName.toLowerCase():null;if(!c){return null}var a="";switch(c){case"input":if(d.type=="radio"){d=$N(d.name);for(var b=0;b<d.length;b++){if(d[b].checked){a=d[b].value}}}else{if(d.type=="checkbox"){d=$N(d.name);a=[];for(var b=0;b<d.length;b++){if(d[b].checked){a[a.length]=d[b].value}}}else{a=d.value}}break;case"select":if(d.type=="select-one"){if(d.selectedIndex!=-1){a=d.options[d.selectedIndex].value}}else{if(d.type=="select-multiple"){a=[];for(var b=0;b<d.length;b++){if(d[b].selected){a[a.length]=d[b].value}}}}break;case"textarea":a=d.value;break}return a},_onoffSwitch:function(b){var a=$(b).style.display;if(a=="none"){$(b).style.display=""}else{$(b).style.display="none"}},onoffSwitch:function(b){if(xui.lang.isArray(b)){for(var a=0;a<b.length;a++){this._onoffSwitch(b[a])}}else{this._onoffSwitch(b)}},isFormChanged:function(g){var c=$(g);for(var b=0;b<c.elements.length;b++){var e=c.elements[b];if(!e||!e.id){continue}var a=e.getAttribute("old-value");if(a==null||typeof a=="undefined"){continue}var d=xui.util.WinUtil.getInputValue(e.id)==a;if(!d){return true}}return false},isIE:function(){return xui.env.ua.ie>0?true:false},isIE6:function(){return xui.env.ua.ie==6?true:false},isIE7:function(){return xui.env.ua.ie==7?true:false},isIE8:function(){return xui.env.ua.ie==8?true:false},isFF:function(){return xui.env.ua.gecko>0?true:false}};xui.util.ElementUtil=function(c,b,a){if(a){this.attr=a}if(!b){b=document}this.length=0;this.dom=$$(c,b)};xui.util.ElementUtil.prototype={attr:{emptyColor:"#999",loop:true},initEmptyText:function(){for(var b=0,a=this.dom.length;b<a;b++){var d=xui.util.Dom.getAttribute(this.dom[b],"emptytext");if(d!=null){var c=xui.util.Dom.getStyle(this.dom[b],"color");xui.util.Dom.setAttribute(this.dom[b],"valueColor",c);xui.util.Dom.addClass(this.dom[b],"xui-emptytext");xui.util.ElementUtil.emptyBlur(this.dom[b]);(function(f,e){xui.util.Event.on(f,"focus",xui.util.ElementUtil.emptyFoucs);xui.util.Event.on(f,"blur",xui.util.ElementUtil.emptyBlur);if(!e){xui.util.Event.removeListener(f,"blur",xui.util.ElementUtil.emptyBlur);xui.util.Event.on(f,"focus",function(){xui.util.Event.removeListener(f,"focus",xui.util.ElementUtil.emptyFoucs)})}})(this.dom[b],this.attr.loop)}}},setAttr:function(a){for(var b in a){this.attr[b]=a[b]}}};xui.util.ElementUtil.emptyBlur=function(f,c){var b="#999";if(c){b=c}var e=this;if(f&&f.nodeType){e=f}var d=xui.util.Dom.getAttribute(e,"emptytext");var a=xui.util.StringUtil.trim(e.value);if(a==""){e.value=d;e.style.color=b}};xui.util.ElementUtil.emptyFoucs=function(e){var d=this;if(e&&e.nodeType){d=e}var c=xui.util.Dom.getAttribute(d,"emptytext");var b=xui.util.StringUtil.trim(d.value);var a=xui.util.Dom.getAttribute(d,"valueColor");xui.util.Dom.removeClass(d,"xui-emptytext");if(b==c){d.value="";d.style.color=a}};xui.util.ElementUtil.getValue=function(b){var c=xui.util.Dom.getAttribute(b,"emptytext");var a=xui.util.StringUtil.trim(b.value);if(a==c){return""}return a};