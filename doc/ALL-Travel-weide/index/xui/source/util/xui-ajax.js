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
}