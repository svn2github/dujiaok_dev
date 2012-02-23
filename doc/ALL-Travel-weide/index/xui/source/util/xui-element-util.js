/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2010, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.0
 * @author tangq(ued)
 * @date 2010-09-09
 *
 * @update 2010-09-09 14:03:27 by tangq
 */

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

