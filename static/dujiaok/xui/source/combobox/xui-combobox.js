/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2009, Alisoft. All rights reserved.
 * @version 1.1
 * @author shengding
 * @date 2009-3-16
 * @requires core/xui-core.js
 * @requires external/ext-2.2/adapter/ext-base.js
 * @requires external/ext-2.2/ext-all.js
 */
 
 
 
 
/**
 * ComboBox  ComboBox类
 * @namespace xui.widgets
 * @class ComboBox
 * @constructor
 * @param {String} id  容器的id
 * @param {Object} attrs  配置属性 eg：{}
 * @param {Object} oSchema  ajax请求数据的schema
 */ 
xui.widgets.ComboBox = function(id,attrs,oSchema) {
	this.id = id;
	this.attrs = attrs;
	this._schema = oSchema;
	/**
	*@field {String} emptyText  显示 默认:''
	*/	
	this.emptyText =( this.attrs && this.attrs.emptyText)?this.attrs.emptyText:'';
	/**
	*@field {Boolean} editable  能否编辑 默认:true
	*/		
	this.editable =( this.attrs && this.attrs.editable)?this.attrs.editable:false;
	/**
	*@field {Number} width  宽度 默认:130
	*/	
	this.width =( this.attrs && this.attrs.width)?this.attrs.width:130;
	this.minChars =( this.attrs && this.attrs.minChars)?this.attrs.minChars:1;
	
	this.displayTrigger =( this.attrs && !(typeof this.attrs.displayTrigger == "undefined"|| this.attrs.displayTrigger ==null) && !this.attrs.displayTrigger)?this.attrs.displayTrigger:true;
	
	this.triggerClass = this.displayTrigger?'x-form-arrow-trigger':'x-hide-visibility';
	/**
	*@field {Boolean} cross  是否跨域请求数据 默认:false
	*/		
	this.cross = ( this.attrs && this.attrs.cross)?this.attrs.cross:false;
	
	this._displayField =( this.attrs && this.attrs.label)?this.attrs.label:null;
	this._valueField = ( this.attrs && this.attrs.value)?this.attrs.value:null;
	this.lazyRender = ( this.attrs && this.attrs.lazyRender)?this.attrs.lazyRender:false;
	this.forceSelection = ( this.attrs && this.attrs.forceSelection)?this.attrs.forceSelection:false;
	
	//默认为渲染模式
	this._transform = this.id;
	if(this.attrs && this.attrs.transform==false){
		this._transform =null;
		this._applyTo = this.id;
	}
};
xui.widgets.ComboBox.prototype = {
	_store:null,
	
	_url:null,
	_schema:null,
	_initSore:function(){
		var p= this.cross? new Ext.data.ScriptTagProxy({url: this._url}):new Ext.data.HttpProxy({url: this._url});
		this._store =  new Ext.data.JsonStore({
					root: 'data',
					totalProperty: 'totalCount',
					idProperty: 'id',
					remoteSort: true,					
					fields:	this._schema,
					proxy:  p
				});
		this._store.load();		
	},
	_initCombox:function(){
		// Ext.QuickTips.init();
		this.combox = new Ext.form.ComboBox({
			store: this._store,
			//tpl: '<tpl for="."><div ext:qtip="{'+this._displayField+'}. {'+this._valueField+'}" class="x-combo-list-item">{'+this._displayField+'}</div></tpl>',
			displayField: this._displayField,
			valueField: this._valueField,
			mode: 'local',
			typeAhead: false,
			forceSelection: this.forceSelection,
			triggerAction: 'all',
			emptyText:this.emptyText,
			width:this.width,
			resizable:true ,
			selectOnFocus:true,
			triggerClass :this.triggerClass,
			minChars:this.minChars,
			minHeight  :250,
			editable: this.editable,
			lazyRender:this.lazyRender,
			applyTo: this._applyTo,
			transform: this._transform
		});	
		//fix ie6 bug
		if(xui.env.ua.ie == 6){
			this.combox.on('expand',function(){
											xui.util.Dom.getElementsByClassName('x-combo-list-inner')[0].style.overflowX = 'hidden';
											 })
		}
	},
	/**
	 * 初始化
	 * @param {String} [sUrl] 请求地址
	 */
	init:function(sUrl){
		if(sUrl){
			this._url = sUrl;
			this._initSore();			
		}
		this._initCombox();
	},
	/** 
	 * 绑定事件
	 * @param {String} eventName
	 * @param {Function } handler
	 */
	on:function(eventName,handler,scope,options){
		this.combox.on(eventName,handler,scope,options);
	},
	/** 
	 * 得到值
	 * @return {String} 
	 */	
	getValue:function(){
		return this.combox.getValue();
	},
	/** 
	 * 得到ext的combox对象
	 * @return {Object} 
	 */	
	get:function(){
		return this.combox;
	},
	/** 
	 * 设置值
	 * @param {String} 
	 */	
	setValue:function(v){
		this.combox.setValue(v);
	}
	
}

