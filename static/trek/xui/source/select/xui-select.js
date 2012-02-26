/*
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2010, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.2
 * @author tangqiang
 * @date 2010-02-03
 *
 * @update time:2010-11-01 10:01:09
*/
/*
 * select   
 * @namespace xui.widgets
 * @class Select
 * @constructor
 * @param {String} id  this html Element select's  id
 * @param {String} |{umber}  width  this Ojbect in HTML node's width
 * @model {Boolean} |{ALLTYPE}  model : if model, can input some value
 */
xui.widgets.Select = function(id,width,model){
	/*
	*this Object's HTMLElement's ID
	*/
	this.id = 'xui-select-'+id;
	this.obj = [];
	this.width = width;
	if(model){
		this.model = true;
	}else{
		this.model = false;
	}
	this.init(id,width,model);
}
xui.widgets.Select.prototype = {
	isclose : false,
	model:false,
	init:function(id){
		this.porobj = $(id);
		var x = xui.util.Dom.getX(id);
		var y = xui.util.Dom.getY(id);
		var w = this.width;
		this.porobj.style.display = 'none';

		var htmlstr = '';
		if(this.model) htmlstr += '<span class="xui-select-value" id="'+this.id+'-value"></span>';
		else htmlstr += '<input class="xui-select-value" readonly="readonly" id="'+this.id+'-value"/>';
		htmlstr += '<span class="xui-select-btn" id="'+this.id+'-btn"></span>';
		
		var tmpd = document.createElement('div');
		tmpd.className = 'xui-select';
		tmpd.id = this.id;
		tmpd.style.width = w+'px';
		tmpd.innerHTML = htmlstr;
		this.porobj.parentNode.insertBefore(tmpd,this.porobj);
		
		this.opsnode = document.createElement('div');
		this.opsnode.id = this.id+'-option';
		this.opsnode.className = 'xui-select-option';
		this.opsnode.style.width = w+'px';
		this.opsnode.style.zIndex = 9999;
		document.body.appendChild(this.opsnode);

		this.node = $(this.id);
		this.porobj = $(id);
		this.shownode = $(this.id+'-value');
		var margins = 0;
		
		try{
			margins += parseInt(xui.util.Dom.getStyle(xui.util.Dom.get(this.id+'-btn'),'margin-left'));
			margins += parseInt(xui.util.Dom.getStyle(xui.util.Dom.get(this.id+'-btn'),'margin-right'));
			margins += parseInt(xui.util.Dom.getStyle(xui.util.Dom.get(this.id+'-value'),'margin-left'));
			margins += parseInt(xui.util.Dom.getStyle(xui.util.Dom.get(this.id+'-value'),'margin-right'));
		}catch(e){}
		var btnw =parseInt(xui.util.Dom.getStyle($(this.id+'-btn'),'width'))+(isNaN(margins)?0:margins);
		
		this.shownode.style.width = w-btnw-3+'px';
		
		
		this.button = $(this.id+'-btn');
		this.opsnode.style.width = w+'px';
		this.opsnode.style.left = x+'px';
		var ph = this.node.offsetHeight;
		this.opsnode.style.top = (y+ph)+'px';
		this.opsnode.style.position = 'absolute';
		var h = this.opsnode.offetHeight;
		
		if(this.porobj.options){
			for(var i=0; i<this.porobj.options.length; i++){
				if(this.porobj.options[i].selected){
					this.index = i;
				}

				this._inertChild(this.porobj.options[i]);
			}
			this.value(this.porobj.options[this.index]);
		}else{
			if(this.porobj.childNodes){
				var len = this.porobj.childNodes.length;
				for(var i=0; i<len; i++){
					if(this.porobj.childNodes[i].typeNode!=1){
						var v =  this.porobj.childNodes[i].innerHTML.replace(/<.*?>/ig,'');
						this._inertChild({text:v,value:v});
					}
				}
				this.index = 0;
				if(len>0){
					this.value(this.obj[0].o);
				}
			}
		}
		this.opsnode.style.display = 'none';
		(function(t){
				  xui.util.Event.on([t.button,t.shownode],'click',function(){
											
											if(!t.click())return;



											var x = xui.util.Dom.getX(t.id),
												y = xui.util.Dom.getY(t.id)+t.node.offsetHeight;
												/*if(xui.env.ua.ie>0 && xui.env.ua.ie<7){
													x-=0;
													y-=2;
												}else if(xui.env.ua.ie==7){
													x+=2;
												}else */if(xui.env.ua.gecko>0){
													x+=1;
												}
											t.opsnode.style.left = x+'px';
											t.opsnode.style.top = y+'px';
											if(t.opsnode.style.display == 'block'){
												t.opsnode.style.display = 'none'
												return;
											}
											t.opsnode.style.display = 'block';
											
											if(xui.widgets.SelectUUopsnode!=t.opsnode){
												if(xui.widgets.SelectUUopsnode.style){
													xui.widgets.SelectUUopsnode.style.display = 'none';
												}
												xui.widgets.SelectUUopsnode = t.opsnode;
											}
											var ns  = t.opsnode.childNodes;
											for(var i=0; i<ns.length; i++){
												ns[i].style.display = 'block';
											}
											/*IE6&7debug*/
											 if(xui.env.ua.ie>0 && xui.env.ua.ie<8){
												 var w=t.opsnode.offsetWidth;
												 if(t.opsnode.offsetHeight<t.opsnode.scrollHeight){
													for(var i=0; i<ns.length; i++){
														ns[i].style.width = w-20+'px';
													}
												 }
											 }
									});
				  xui.util.Event.on([t.button,t.opsnode],'mouseout',function(){
											t.isclose = true;
											 setTimeout(function(){t.close()},2000);
											   });
				  xui.util.Event.on([t.button,t.opsnode],'mouseover',function(){
											t.isclose = false;
											   });
				  
		})(this);
		/*
		*click this document and close this
		*/
		var _f = this,f=true;
		xui.util.Event.on(document,'click',function(e){
			if(_f.opsnode.style.display == 'none')return;
			var target = xui.util.Event.getTarget(e),f=true; 
			while(target.parentNode&&f){
				target = target.parentNode;
				if(target.className&&target.className.toLowerCase().indexOf('xui-select')!=-1){
					f=false;
				}
			}
			if(f){_f.opsnode.style.display = 'none';}
			});
		return this;
	},
	security:false,
	value:function(ov){
		//this.shownode.innerHTML = ov.text;
		if(this.security){
			if(this.model){
				this.shownode.innerHTML = xui.util.StringUtil.unescapeHTML(ov.text);
			}else{
				this.shownode.value = xui.util.StringUtil.unescapeHTML(ov.text);
			}
			this.porobj.value = xui.util.StringUtil.unescapeHTML(ov.value);
			this.proValue = xui.util.StringUtil.unescapeHTML(ov.value);
		}else{
			if(this.model){
				this.shownode.innerHTML = ov.text;
			}else{
				this.shownode.value = ov.text;
			}
			this.porobj.value = ov.value;
			this.proValue = ov.value;
		}
	},
	/**
		 * @param {Boolean} beClose 强制关闭
	*/
	close:function(beClose){
		var t = this;
		if(this.opsnode.style.display == 'none')return;
		if(beClose){
			this.opsnode.style.display = 'none';
			try{this.porobj.onchange();}catch(e){}
			return;
		}
		if(this.isclose&&this.autoClose){
			this.opsnode.style.display = 'none';
			return;
		}else{setTimeout(function(){t.close()},2000);}
	},
	createSuggest:function(){
		var t = this;
		xui.util.Event.on(t.shownode,'click',function(){
			if(t.initValue&&t.shownode.value == t.initValue){
				t.shownode.value = '';
			}
		});
		xui.util.Event.on(t.shownode,'keyup',function(){t.queryText();});
	},
	setOnlyValue:function(v){
		this.proValue = v;
	},
	proValue:'',
	getValue:function(){
		return this.proValue;
	},
	queryText:function(){
		var v= this.shownode.value;
		//v = v.replace('&lt;','<').replace('&gt;','>').replace('&nbsp;',' ');
		this.setOnlyValue(v);
		if(v.replace(/\s/,'').length<1){
			this.close(true);
			return ;
		}
		/*
		*which need to display 
		*/
		var ns  = this.opsnode.childNodes,count=0,num=0;
		for(var i=0; i<this.obj.length; i++){
			if(this.obj[i].o.text.indexOf(v)==-1){
				ns[i].style.display = 'none';
			}else{
				count++;
				num=i;
				ns[i].style.display = 'block';
			}
		}
		this.opsnode.style.display = 'block';
	},
	autoClose:false,
	setModel:function(obj){
		/*
		*@param {string} autoClose 
		*/
		if(obj.autoClose){
			if(obj.autoClose=='true'){
				this.autoClose = true;
			}else{
				this.autoClose = false;
			}
		}
		if(obj.msg){
			this.value({text:obj.msg,value:''});
		}
		/*
		*@param {string} suggest : active the Static suggest function
		*/
		if(obj.suggest=='true'){
			this.autoClose = false;
			this.shownode.readOnly = false;
			this.createSuggest();
		}
		/*
		* @param {string} selectModel : close or open this select function
		*/
		if(obj.selectModel=='false'){
			this.button.style.display = 'none';
		}else{
			this.button.style.display = 'block';
		}
		/*
		* @param {string} selectModel : close or open this select function
		*/
		if(obj.initValue){
			if(obj.initValue.constructor==String){
				obj.initValue = {
						text:obj.initValue,
						value:obj.initValue
					}
			}
			this.value(obj.initValue);
		}
	},
	select:function(v,txt){
		for(var i in this.obj){
			if(txt){
				if(this.obj[i].o["text"]==v){
					this.setModel({initValue:this.obj[i].o});
					this.index = i;
					return;
				}
			}else if(this.obj[i].o["value"]==v){
				this.setModel({initValue:this.obj[i].o});
				this.index = i;
				return;
			}
		}
	},
	selectByIndex:function(index){
		if(index.constructor!=Number)return;
		if(index>this.obj.length-1||index<0)return;
		this.setModel({initValue:this.obj[index].o});
		this.index = index;
	},
	remove:function(txt,v){
		var index = null;
		if(txt.constructor==Number){
			index = txt;	
		}else{
			for(var i=0; i<this.obj.length; i++){
				if(v){
					if(this.obj[i].o.value==v){
						index = i;
						break;
					}
				}else if(this.obj[i].o.text==txt){
					index = i;
					break;
				}
			}	
		}
		if(index==null)return;
		this.opsnode.removeChild(this.obj[index].node);
		for(var i=index; i<this.obj.length-1; i++){
			this.obj[i] = this.obj[i+1];
		}
		this.obj.length = this.obj.length -1;
		if(index==this.index){
			this.value(this.obj[0].o);
			this.index = 0;
		}
		
	},
	index:0,
	add:function(t,v){
		var o;
		if(t.constructor == Object){
			o=t;
		}else{o={text:t,value:v};}
		this._inertChild(o);
		
	},
	_inertChild:function(o){
		var d = document.createElement('div');
		d.innerHTML = o.text;
		this.opsnode.appendChild(d);
		this.obj.push({"o":o,"node":d});
		(function(obj,t){
				xui.util.Event.on(obj.node,'mouseover',function(){
														t.isclose = false;
														obj.node.className = 'xui-select-hover';
														 });
				xui.util.Event.on(obj.node,'mouseout',function(){
														t.isclose = true;
														obj.node.className = ''; 
														   });
				xui.util.Event.on(obj.node,'click',function(){
														
														if(!t.onselect())return;
														 
														 t.select(obj.o.value);
														 t.close(true);
														
														  t.change();

														   });
		})({"o":o,"node":d},this);
	},
	/*
	* 用户自定义事件流
	* @M change:选中了某个值后触发的事件
	* @M onselect:选择某项值的时候触发的事件
	*		return : true ; 有效选择，将触发change事件或原节点的onchange事件
	*		return : false ; 无效选择，不触发change事件或原节点的onchange事件
	* @M click: 点击此select进行选择的时候
	*		return : true ; 继续后续操作
	*		return : false ; 阻止后续操作
	*/
	change:function(){},
	onselect:function(){return true;},
	click:function(){return true;},
	/*
	* reLoad : 重渲染 
	*/
	reLoad:function(){
		this.init(this.porobj.id);
	},
	/*
	* load : 删除当前的所有选项渲染一个新的数据列表
	*/
	load:function(list){
		this.opsnode.innerHTML = '';
		this.value({text:'',value:''});
		this.obj = [];
		this.addList(list);
	},
	/*
	* addList : 增加一个新的数据列表
	*/
	addList:function(list){
		for(var i=0,len=list.length;i<len;i++){
			this._inertChild(list[i]);
		}
	},
	/*
	* show ：显示此控件界面
	*/
	show:function(){
		$(this.id).style.display = 'block';
	},
	/*
	* hide ：隐藏此控件界面
	*/
	hide:function(){
		$(this.id).style.display = 'none';
	}
}
/*
* 保证只有一个显示的opsnode选择界面
*/
xui.widgets.SelectUUopsnode = '';