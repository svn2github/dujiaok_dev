xui.widgets.Suggest = function(id,attributes){
	this.id = id;
	this._events = {};
	this.selectNode;
	this.ops = [];
	this.x = 0;
	this.y = 0;
	this.selectRowIndex = -1;
	if(attributes){
		this.attributes = attributes;	
		if(this.attributes.width)
			this.width = this.attributes.width;	
		if(this.attributes.height)
			this.height = this.attributes.height;
		if(this.attributes.x)
			this.x = this.attributes.x;	
		if(this.attributes.y)
			this.y = this.attributes.y;	
		if(this.attributes.renderer)
			this.renderer = this.attributes.renderer;
		if(this.attributes.emptyMeg)
			this.emptyMeg = this.attributes.emptyMeg;
		if(this.attributes.gbk)
			this.gbk = this.attributes.gbk;
	}
	/* @param {Boolean} anyMatch 是否数据里包含关键字就可以，默认为false，需要第一个字符匹配
	*/
	this.anyMatch =( this.attributes && this.attributes.anyMatch)?this.attributes.anyMatch:false;
	/* @param {Boolean} suffix 是否是加后缀suggest
	*/
	this.suffix =( this.attributes && this.attributes.suffix)?this.attributes.suffix:false;
	
}
xui.widgets.Suggest.prototype = {
	_formatUrl:function(url,v){
		 if(url.indexOf('?')>-1){
			return url+=  '&'+v;
		 }else{
			return url+=  '?'+v;
		 }
	},
	_getData:function(){
		var keyValue = xui.util.StringUtil.trim($(this.id).value);
		
		this.ops = [];
		if(keyValue.length>0){
			if(this.gbk){
				var str = xui.util.Ajax.syncGet(this._formatUrl(this.dataUrl,'keyValue='+encodeURIComponent(encodeURIComponent(keyValue))))
				//var str = xui.util.Ajax.syncGet(this.dataUrl+'?keyValue='+encodeURI(encodeURI(keyValue)));
			}else{
				var str = xui.util.Ajax.syncGet(this._formatUrl(this.dataUrl,'keyValue='+encodeURIComponent(keyValue)));
				//var str = xui.util.Ajax.syncGet(this.dataUrl+'?keyValue='+encodeURI(keyValue));
			}			//var str = '[{text:"张三张三张三张三张三张三张三张三张三",value:"1"},{text:"李四",value:"21"},{text:"王五",value:"3"}]';
			try{
				this.ops = eval("("+str+')');
			}catch(e){};
		}else{
			
			return;
		}
		return this.ops;
	},
	_hiddenContainer:function(){
		if(this.container)
			this.container.style.display = 'none';
	},
	_showContainer:function(){
		if(this.container)
			this.container.style.display = 'block';
	},
	_rendOps:function(){
		var h = [];
		var sH = [];
		var o = this;
		if(this.ops){					
			var l = this.ops.length;	
			for(var i =0;i<l;i++){		
				var oDiv = document.createElement('div');
				xui.util.Dom.addClass(oDiv,'xui-suggest-list-item');
				/**如果有自定义渲染*/
				if(this.renderer) {
					oDiv.innerHTML = this.renderer(this.ops[i]);	
				}else{
					oDiv.innerHTML =xui.util.StringUtil.htmlEncode(this.ops[i].text);		
				}
				
				oDiv.title = this.ops[i].text;;
				h[i]= oDiv;
				
				//h[i]='<div class="xui-suggest-ops">'+this.ops[i].text+'</div>';	
				xui.util.Event.on(oDiv,'mouseover',function(){																
							xui.util.Dom.addClass(arguments[1],'xui-suggest-ops');								
					},oDiv);
				xui.util.Event.on(oDiv,'mouseout',function(){													   
							xui.util.Dom.removeClass(arguments[1],'xui-suggest-ops');
					},oDiv);
				xui.util.Event.on(oDiv,'click',function(){
						/*$(o.id).value=arguments[1][1].text;
						o.selectNode = arguments[1][1];
						o._fire('nodeclick',o.selectNode.text);
						o._hiddenContainer();*/
						o._clickNode(arguments[1][1],arguments[1][2]);
					},[oDiv,o.ops[i],i]);
			}	
		}
		return h;
	},
	_clickNode:function(op,index){
		$(this.id).value= op.text;
		this.selectNode = op;
		
		this._fire('nodeclick',this.selectNode.text);
		this._hiddenContainer();
		this.selectRowIndex = -1;
	},
	_render:function(){
		var o =this;
		var _x = xui.util.Dom.getX(this.id);
		var _y = xui.util.Dom.getY(this.id);
		var _h = 22;
		if(!this.container){
			this.container =document.createElement('div');
			xui.util.Dom.addClass(this.container,'xui-suggest');
			this.container.style.width = this.width+'px' ;
			this.container.style.height = this.height +'px';
			this.container.style.left = _x+ this.x +'px' ;
			this.container.style.top= _y+_h + this.y +'px';
			//xui.util.Dom.insertAfter(this.container,this.id);
			document.body.appendChild(this.container);
			xui.util.Event.on(this.container,'click',function(){
						if(o.selectNode)									  
							o._fire('click',o.selectNode.text);
			});
		}
		
		this._showContainer();
		this.container.innerHTML = '';
		this.nodes = this._rendOps();
		var l =this.nodes.length;
		//如果有数据，插入
		
		if(l>0){
			for(var i =0;i<l;i++){	
				this.container.appendChild(this.nodes[i]);
			}
		}else{
			var keyValue = xui.util.StringUtil.trim($(this.id).value);
			if(keyValue && keyValue.length > 0){
				this.container.innerHTML = this.emptyMeg;
			}else{
				this._hiddenContainer();
			}
		}
	},
	/**
	*过滤数据
	*/
	filter:function(){
		var keyValue = xui.util.StringUtil.trim($(this.id).value);
		var l = this.ops.length;
		for(var i =0;i<l;i++){
			//如果是前台加后缀方式
			if(this.suffix){
				this.ops[i].text = keyValue +this.ops[i].text;
			//前台过滤方式
			}else{
				if(this.anyMatch){
					if(this.ops[i].text.indexOf(keyValue) < 0){
						this.ops.splice(i,1);
						i--;
						l--;
					}
				}else{
					//如果不匹配第一个字符
					
					if( ! (this.ops[i].text.indexOf(keyValue) == 0) ){
						this.ops.splice(i,1);
						//删除数据后，总的长度也减小了
						i--;
						l--;
					}
				}
			}
		}
		return this.ops;
	},
	init:function(url){	
		this.dataUrl = url;
		//需要复制出一个数组对象保存起来，用于过滤数据的时候
		if(this.dataUrl instanceof Array){
			//不能用slice，否则虽然数组是不是一个有用，但是数组里面的对象会是一个引用
			//this.datas = this.dataUrl.slice(0);
			this.datas = [];
			for(var i = 0;i<this.dataUrl.length;i++){
				this.datas[i]={text:this.dataUrl[i].text,value:this.dataUrl[i].value};
			}
		}
		this._initEnvents('click');
		this._initEnvents('nodeclick');
		var o =this;
		xui.util.Event.on(this.id,'keyup',function(evt){	
						if(evt.keyCode == 38 || evt.keyCode == 40 ||  evt.keyCode == 13)	
							return ;
						//如果传过来的是数组，则不需要往后台请求数据						   
						if(o.dataUrl instanceof Array){
							//这里也需要复制一下，如果写o.ops = o.datas，会指定到一个引用，过滤后数据就不对了
							//console.log(o.datas)
							//不能用slice，否则虽然数组是不是一个有用，但是数组里面的对象会是一个引用
							//o.ops = o.datas.slice(0);
							o.ops = [];
							for(var i = 0;i<o.datas.length;i++){
								o.ops[i]={text:o.datas[i].text,value:o.datas[i].value};
							}

							o.filter();
//console.log(o.datas)
						}else{
							o._getData();
						}
						o._render();
			});
		xui.util.Event.on(document,'click',function(evt){
			var element=evt.srcElement || evt.target;
			if(element.id != o.id){
				o._hiddenContainer();
			}
		});
		//up 38 down 40
		var kl =new  xui.util.KeyListener(this.id,{keys:40},{fn:o.selectNext,scope:o,correctScope:true });
		kl.enable();
		var k2 =new  xui.util.KeyListener(this.id,{keys:38},{fn:o.selectPre,scope:o,correctScope:true });
		k2.enable();
		var k3 =new  xui.util.KeyListener(this.id,{keys:13},{fn:function(){
																		
																		 arguments[1][1].cancelBubble = true;
																		 var index = o.selectRowIndex;
																		 var op = o.ops[index];
																		 o._clickNode(op,index);
																		  return false;
																		 },scope:o,correctScope:true });
		k3.enable();
	},
	selectNodeByIndex:function(rowIndex){
		if(this.ops[rowIndex]){
			xui.util.Dom.removeClass(this.nodes[this.selectRowIndex],'xui-suggest-ops');
			this.selectRowIndex = rowIndex;
			this.selectNode = this.ops[rowIndex];
			xui.util.Dom.addClass(this.nodes[rowIndex],'xui-suggest-ops');

		}
	},
	selectNext:function(e,e2){	
		var index =  ((this.selectRowIndex +1) >= this.ops.length )? (this.ops.length -1 ):(this.selectRowIndex +1) ;
		this.selectNodeByIndex(index);
	},
	selectPre:function(){
		var index =  ((this.selectRowIndex -1) >= 0 )? (this.selectRowIndex -1):0 ;
		this.selectNodeByIndex(index);
	},
	on:function(evemtName,func){
		this._events[evemtName].subscribe(func, this);
	},	
	_initEnvents:function(evemtName) {
		this._events[evemtName] = new YAHOO.util.CustomEvent(evemtName, this);
	},
	_fire:function(evemtName,arg){
		this._events[evemtName].fire(arg);
	}

}