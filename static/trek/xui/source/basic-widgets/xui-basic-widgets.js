/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @version 1.1
 * @author shengding
 * @date 2009-3-16
 * @requires core/xui-core.js
 */

/**
 * Widget 基础组件的基类
 * @namespace xui.widgets
 * @class xui.widgets.Widget
 * @constructor 
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.Widget = function(attrs){
	this.attributes = attrs;
    if(typeof attrs == "string"){
        this.attributes = {id: attrs};
    }
	this.id = this.attributes.id;
	
}
xui.widgets.Widget.prototype ={
	/**
	 * 绑定事件
	 * @param {String} eName 事件名
	 * @param {Function} fun 事件回调函数
	 * @param {Object} args 参数 
	 */
	on: function(eventName, fun, args){
		xui.util.Event.on(this.id, eventName, fun, args);
	},
	/**
	 * 得到dom对象
	 * @return {Object}
	 */
	getDom:function(){
		return document.getElementById(this.id);
	}
}
/**
 * TextBox 文本输入框
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.TextBox
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.TextBox = function(attrs){ 
	xui.widgets.TextBox.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.TextBox, xui.widgets.Widget,{});
/**
 * Password 密码框
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.Password
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.Password = function(attrs){ 
	xui.widgets.Password.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.Password, xui.widgets.Widget,{});

/**
 * TextArea 
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.TextArea
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.TextArea = function(attrs){ 
	xui.widgets.TextArea.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.TextArea, xui.widgets.Widget,{});
/**
 * PictureBox
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.PictureBox
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.PictureBox = function(attrs){ 
	xui.widgets.PictureBox.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.PictureBox, xui.widgets.Widget,{});

/**
 * CheckBox 
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.CheckBox
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.CheckBox = function(attrs){ 
	xui.widgets.CheckBox.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.CheckBox, xui.widgets.Widget,{
	on: function(eventName, fun, args){
		xui.util.Event.on($N(this.id), eventName, fun, args);
	}
});

/**
 * Select 
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.Select
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.Select = function(attrs){ 
	xui.widgets.Select.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.Select, xui.widgets.Widget,{});
/**
 * Radio 
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.Radio
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.Radio = function(attrs){ 
	xui.widgets.Radio.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.Radio, xui.widgets.Widget,{
	on: function(eventName, fun, args){
		xui.util.Event.on($N(this.id), eventName, fun, args);
	}
});
/**
 * Button 
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.Button
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.Button = function(attrs){ 
	xui.widgets.Button.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.Button, xui.widgets.Widget,{});
/**
 * Link 链接类 
 * @extends xui.widgets.Widget
 * @namespace xui.widgets
 * @class xui.widgets.Link
 * @constructor
 * @param {Object} attrs  配置属性 eg：{}
 */
xui.widgets.Link = function(attrs){ 
	xui.widgets.Link.superclass.constructor.call(this, attrs);  
};   
xui.extend(xui.widgets.Link, xui.widgets.Widget,{});



