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
