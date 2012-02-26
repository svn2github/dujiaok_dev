/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.1
 * @author fengchun
 * @date 2009-11-12
 */


/**
 * Dialog  浮动窗口
 * @namespace xui.widgets
 * @class Dialog
 * @constructor
 * @param {String} id  唯一标识
 * @param {Object} attrs  配置属性
 */
xui.widgets.Dialog = function(id, attrs){
	/**
	 * @field {String} id
	 * @final
	 */
	this.id = id;
	/**
	 * @field {Object} attrs 
	 * @final
	 */
	this.attrs = attrs?attrs:null;		

	/**
	*@field {Array} 定位窗口的坐标
	*/
	this.attrs.reposition = ( this.attrs && this.attrs.reposition)?this.attrs.reposition:false;
	/**
	*@field {String} 要定位到的元素ID
	*/
	this.attrs.positionId = ( this.attrs && this.attrs.positionId)?this.attrs.positionId:false;
	/**
	*@field {Array}  要定位到元素的偏移量
	*/
	this.attrs.positionOffset = ( this.attrs && this.attrs.positionOffset)?this.attrs.positionOffset:[0,0];
	/**
	*@field {String} 设置边框的颜色
	*/
	this.attrs.borderStyle = ( this.attrs && this.attrs.borderStyle)?this.attrs.borderStyle:false;
	/**
	*@field {Number} 设置外框的宽度
	*/	
	this.attrs.borderMargin = ( this.attrs && this.attrs.borderMargin!==false)?this.attrs.borderMargin:false;
	/**
	*@field {String} 设置背景色
	*/		
	this.attrs.backgroundColor = ( this.attrs && this.attrs.backgroundColor)?this.attrs.backgroundColor:false;
	/**
	*@field {String} 设置关闭按钮的样式
	*/		
	this.attrs.closeBtnStyle = ( this.attrs && this.attrs.closeBtnStyle)?this.attrs.closeBtnStyle:false;
	
	/**
	*@field {String} 设置dialog距离文档最顶部的位置
	*/		
	this.attrs.positionTop = ( this.attrs && this.attrs.positionTop)?this.attrs.positionTop:false;
	/**
	*@field {Blean} 是否自动适应大小
	*/		
	this.attrs.isAutoSize = ( this.attrs && this.attrs.isAutoSize)?this.attrs.isAutoSize:false;
		/**
	*@field {Blean} 是否补全高度和宽度，只有autosize第一次的时候才补全
	*/		
	this.attrs.isAdd = ( this.attrs && !(typeof this.attrs.isAdd == "undefined"|| this.attrs.isAdd ==null) && !this.attrs.isAdd)?this.attrs.isAdd:true;
	/**
	 * @event beforeOpen 事件
	 * @event afterClose 事件
	 */
	this._events = {};
	
	if($(this.id)) return;
	var layer = document.createElement('div');
	layer.setAttribute('id',this.id);
	layer.style.position = 'absolute';
	layer.style.zIndex = '9';
	layer.style.display = 'none';	
	document.body.appendChild(layer);
	/**
	 * @field {Element} attrs 
	 * @final
	 */
	
	if($(this.id+'_bglayer')) return;
	var bgLayer = document.createElement('div');
    bgLayer.setAttribute('id',this.id+'_bglayer');
    bgLayer.style.position = 'absolute';
    bgLayer.style.left = '0';
    bgLayer.style.top = '0';
    bgLayer.style.background = '#cccccc';
	bgLayer.style.zIndex = 8;	
	if(xui.env.ua.ie>0){
     	bgLayer.style.filter = 'alpha(opacity=60)';
	}else{
		bgLayer.style.opacity = '0.6';
	}
	if(xui.env.ua.ie>0 && xui.env.ua.ie<7) bgLayer.innerHTML = $Shim();	//IE6补丁
	bgLayer.style.display = 'none';			
	document.body.appendChild(bgLayer);
}

xui.widgets.Dialog.prototype = {
	/**
 	 * 获取属性值
 	 * 
 	 * @function get 
 	 * @return {Object} value
 	 */
	get:function(key){
		return this.attrs?this.attrs[key]:null;
	},
	/**
 	 * 设置属性值
 	 * 
 	 * @function set 
 	 * @param {String} k
 	 * @param {Object} v
	 * @param {Blean} isPs 是否设置_position，默认为true
 	 */
	set:function(k,v,isPs){
		if(!k) return;
		if(!this.attrs) this.attrs = {};
		this.attrs[k] = v;		
		
		if(k=='width'){
			var obj = $(this.id+'_bar');
			if (obj) obj.style.width = (v?v:0) + 'px';
		}else if(k=='height'){
			var obj = $(this.id+'_content');			
			if (obj) obj.style.height = (v-50) + 'px';
			obj = $(this.id+'_ifr');			
			if (obj) obj.style.height = (v-50) + 'px';
			
			if(xui.env.ua.ie>0 && xui.env.ua.ie<7){//IE6补丁
				obj = $(this.id+'_shim');			
				if (obj) obj.style.height = (v-13) + 'px';
			}
		}else if(k=='title'){
			var obj = $(this.id+'_title');
			if(obj) obj.innerHTML = v?v:'';
		} else if(k=='lable'){
			var obj = $(this.id+'_lable');
			if(obj) obj.innerHTML = v?v:'';
		} 
		var isPs =( !(typeof isPs == "undefined"|| isPs ==null) && !isPs)?isPs:true;
		if(isPs)
			this._position($(this.id));
	},
	_html:function(){
		var html = [],style='';
		var lable = this.attrs['lable']?this.attrs['lable']:'';
		html.push('<div id="'+this.id+'_bar" class="xui-dialog-bg" style="width:'+this.attrs['width']+'px;">');
		html.push('<div class="xui-dialog-border" style="'+((this.attrs.borderMargin!==false)?'margin:'+this.attrs.borderMargin+'px;':'')+' '+(this.attrs.borderStyle?'border:'+this.attrs.borderStyle+';':' ')+'">');
		
		html.push('<div class="xui-dialog-titlebar" style="'+(this.attrs.backgroundColor?'background-color:'+this.attrs.backgroundColor:' ')+'">');
		html.push('<span class="xui-dialog-titlebar-button"><span id="'+this.id+'_close" href="javascript:void(0)" class="xui-dialog-titlebar-close" style="'+(this.attrs.closeBtnStyle?this.attrs.closeBtnStyle:'')+'"></span></span>');
		if(this.attrs['title']){
			html.push('<label id="'+this.id+'_title" class="xui-dialog-title">'+this.attrs['title']+'</label><span class="xui-dialog-title2" ></span>');
		}
		html.push('<span class="xui-dialog-title-lable" id="'+this.id+'_lable">'+lable+'</span></div>');
		
		var height = this.attrs['height']-60;
		html.push('<div id="'+this.id+'_content" class="xui-dialog-content" style="height:'+height+'px;'+(this.attrs.backgroundColor?'background-color:'+this.attrs.backgroundColor:' ')+'">');		
		if(this.attrs['content']['url']){
			
			var isAutoSize = this.attrs['isAutoSize']?'no':'auto';
			var url =  this.attrs['content']['url'];
			if(url.indexOf('?')!=-1){
				url += '&t='+new Date().getTime();
			}else{
				url += '?t='+new Date().getTime();
			}
			html.push('<iframe id="'+this.id+'_ifr" src="'+url+'" style="overflow:hidden;width:100%;height:'+height+'px;" frameborder="0" scrolling="'+isAutoSize+'"/>');
		
		}else if(this.attrs['content']['txt']){
			html.push('<div id="'+this.id+'_txt">'+this.attrs['content']['txt']+'</div>');
		}
		
		if(xui.env.ua.ie>0 && xui.env.ua.ie<7) html.push($Shim(this.id+'_shim','',this.attrs['width'],this.attrs['height']-13));//IE6补丁
		html.push('</div></div></div>');
		return html.join('');
	},
	_position:function(layer){
		var canvas = $Canvas();
		layer.style.left = !this.attrs['width']?'0px':parseInt((canvas.clientWidth-this.attrs['width'])/2)+canvas.scrollLeft + 'px';
		if(this.attrs.positionTop){
			if(parent){
				layer.style.top = parent.document.documentElement.scrollTop+this.attrs.positionTop+'px';
			}else{
				layer.style.top = document.documentElement.scrollTop+this.attrs.positionTop+'px';
			}
		}
		else{
			var h = parseInt((canvas.clientHeight-this.attrs['height'])/2)+canvas.scrollTop;
			layer.style.top = !this.attrs['height']?'0px':(h<0?0:h) + 'px';
		}
		
	},
	_positionBGLayer:function(bgLayer){
		var canvas = $Canvas();
		bgLayer.style.width = canvas.scrollWidth+'px';
		bgLayer.style.height = (canvas.clientHeight>canvas.scrollHeight?canvas.clientHeight:canvas.scrollHeight)+'px';	
	},
	/**
 	 * 打开窗口
 	 * 
 	 * @function open 
 	 */
	open:function(){
		this._fire('beforeOpen');
		var self = this;
		if(this.attrs['modal']){//模态窗口
			var bgLayer = $(this.id+'_bglayer');
			this._positionBGLayer(bgLayer);
			bgLayer.style.display = '';
		}
		
		var layer = $(this.id);		
		layer.innerHTML = this._html();

		
		if(this.attrs['reposition']){
			this.reposition(this.attrs['reposition']);
		}else if(this.attrs['positionId']){
			this.positionToElement();
		}else{
			this._position(layer);
		}

			
		layer.style.display = '';
				
		//add event handle
		xui.util.Event.on(this.id+'_close', 'click', function(e){
			this.cancelClose();	
			xui.util.Event.stopPropagation(e);//阻止事件传播
		},null,this);
		if(this.attrs['content']['url']&&$Listen){
				xui.util.Event.on(this.id+ '_ifr', 'load', function(e){
					if(this.contentWindow&&this.contentWindow.$Listen){
						this.contentWindow.$Listen('close', function(){self.close();})
						this.contentWindow.$Listen('cancel', function(){self.cancelClose();})
					}

				},null);
		}
		if(this.attrs.isAutoSize){
			var o = this;
			window.setInterval(function(){o._autoSize()}, 400);
		}
			
		return this;
	},
	/**
 	 * 关闭窗口
 	 * 
 	 * @function close 
 	 */
	close:function(){
		$(this.id).style.display = 'none';	
		
		var bgLayer = $(this.id+'_bglayer');
		if(bgLayer) bgLayer.style.display = 'none';	
		
		this._fire('afterClose');		
	},	
	cancelClose:function(){
		$(this.id).style.display = 'none';	
		
		var bgLayer = $(this.id+'_bglayer');
		if(bgLayer) bgLayer.style.display = 'none';	
		
		this._fire('cancelClose');		
		this._fire('afterClose');	
	},
	/**
 	 * 重定位窗口位置
 	 * @function reposition 
	 * @param {Array.<number>} 坐标
 	 */
	reposition:function(pos){
		$(this.id).style.left = pos[0]+'px';
		$(this.id).style.top = pos[1]+'px';
		return this;
	},
	/**
 	 * 定位到指定元素
 	 * @function positionToElement 
	 * @param {String} 要定位到的元素ID
	 * @param {Array.<number>} 偏移量
 	 */
	positionToElement:function(id,offset){
		var pos = id?xui.util.Dom.getXY($(id)):xui.util.Dom.getXY($(this.attrs['positionId']));
		var offset = offset?offset:this.attrs.positionOffset;
		this.reposition([pos[0]+offset[0],pos[1]+offset[1]]);
		return this;
	},
	_fire: function(eName,d){
		var fun = this._events[eName];			
		if(fun) {
			try{fun.call(this,d);}catch(e){alert(e.message);}
		}
	},
	reload:function(url){
		if(this.attrs['content']['url'])$(this.id+'_ifr').src = url;
	},
	/**
	 * 事件监听
	 * @param {String} eName 事件名
	 * @param {Function} fun 事件回调函数
	 */
	on: function(eName, fun){
		this._events[eName]=fun;
	},
	getInnerIframe:function(){
		return $(this.id+'_ifr').contentWindow;
	},
	/**
	 * 自动根据内容调整大小
	 */
	_autoSize:function(){
		var o =this;
		if(o.attrs['content']['url']){
			//xui.util.Event.on(o.id+ '_ifr', 'load',function(){	  
//											if(xui.env.ua.gecko > 0){	
////												//var w = ($(o.id+'_ifr').contentWindow.document.body.clientWidth )	;
////												//var h = ($(o.id+'_ifr').contentWindow.document.body.clientHeight)	;
////												
//											var w = ($(o.id+'_ifr').contentDocument.documentElement.scrollWidth)	;
////												var h = ($(o.id+'_ifr').contentDocument.documentElement.scrollHeight)	;
////												
////													
//											}else{
//											var w = ($(o.id+'_ifr').Document.documentElement.scrollWidth)	;
////												var h = ($(o.id+'_ifr').Document.documentElement.scrollHeight)	;
////												
//											}
//解决ie8和ff下，内容变大后缩小回来外面还是大的，需要先将iframe所小
											if(xui.env.ua.gecko > 0 || xui.env.ua.ie == 8)
												$(o.id+'_ifr').style.height = 100+'px'
											try{
											var bHeight = $(o.id+'_ifr').contentWindow.document.body.scrollHeight;}catch(e){var bHeight = 520}
											try{
											var dHeight = $(o.id+'_ifr').contentWindow.document.documentElement.scrollHeight;}catch(e){var dHeight = 520}
											try{
											var bWidth = $(o.id+'_ifr').contentWindow.document.body.scrollWidth;}catch(e){var bWidth = 660}
											try{
											var dWidth = $(o.id+'_ifr').contentWindow.document.documentElement.scrollWidth;}catch(e){var dWidth = 660}
											
												
											
											var h = Math.max(bHeight, dHeight);
											var w = Math.max(bWidth, dWidth);
											if(xui.env.ua.gecko > 0 || xui.env.ua.ie == 8){
												//console.log(w)
												if(o.attrs['isAdd']){
													w =w +18;
													o.set('width',w,false);													
												}
											}else{
												w =w +18;
												if(o.attrs['isAdd'])
													o.set('width',w,false);
											}
											h= h+50;
											
											
											o.set('height',h,false);
											//解决变高后，蒙层没有变高
											if(o.attrs['modal']){
												var bgLayer = $(o.id+'_bglayer');
												o._positionBGLayer(bgLayer);
											}
											o.attrs['isAdd'] =false;
										
											 //$(o.id+'_ifr').scrolling = 'no';
															 //  });
			//xui.util.Event.onContentReady(this.id+'_ifr',function(){
															//	  alert($(o.id+'_ifr').contentWindow.document.body.scrollHeight)
															//	  })	
		}else{
			xui.util.Event.onContentReady(o.id+'_txt',function(){														  
								var w = ($(o.id+'_txt').clientWidth);
								var h = ($(o.id+'_txt').clientHeight);	
																  })	
		}
		
	}
	
}
