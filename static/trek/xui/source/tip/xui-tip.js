/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008, Alisoft. All rights reserved.
 * @requires core/xui-core.js
 * @requires util/xui-string-util-min.js
 
 * 
 * @version 1.0
 * @author fengchun
 * @date 2008-12-12
 *
 * Update
 * Time: 2010-05-31 11:04:17
 * by: tangqiang
 */

/**
 * Tip����ͣʽ��ʾ��
 * 
 * @namespace xui.widgets
 * @singleton Tip
 */

xui.widgets.Tip = function() {
	var i=0;
	return {		
		/*
		*	Ĭ��ģ�� �Զ���ģ����Բμ���ģ���#uid��#msg��ʹ��
		*/
		htmlTmp:'<ul class="xui-tip-ul" id="#uid">'
				+'<li class="xui-tip-bg">#msg</li>'
				+'</ul>', 
		/**
		 * ����ĳ�������ϣ���ʾ��ʾ
		 * @param {String} id ����Id
		 * @param {String} msg ��ʾ�ı�
		 * @param {String} html �Զ���ģ�� *��ѡ���� *Ϊ��ʱʹ��Ĭ��ģ��
		 * ģ�����ò���˵����	
		 *	 #uid ��tip��ΨһID  (����)		
		 *	 #msg ��tip��ʾ��Ϣ  (����)
		 *
		 *
		 */
		show: function(id, msg,html,model){
			if(!html)html=this.htmlTmp;
			if(!id) {alert('[xui.widgets.Tip]����Id�����ڣ�');return}
			var obj =this.obj= (xui.lang.isString(id))?$(id):id;	
			
			if(obj.style.display=='none' || obj.style.visibility=='hidden') return;
			var xy = xui.util.Dom.getXY(obj);
			if(xy[0]==0 && xy[1]==0) return;//�˶���ܿ��ܲ��ɼ�����TIP����ʾ			
		
			var layer = document.createElement('div');
			layer.setAttribute('id','');
			layer.setAttribute('class','xui-tip');		
		
			layer.style.position = 'absolute';	            
			layer.style.zIndex = '80';	
			layer.style.overflow = 'hidden';
			layer.style.left = (xy[0]+obj.offsetWidth+20) + 'px';
			layer.style.top = (xy[1]) + 'px';
			//layer.style.height = '35px';
			//layer.style.border = '1px solid red';
			
			var uId = obj.id+'china';
			xui.widgets.Tip._SRC_ELEMENTS.push(uId);
			if(model&&model.safe){
				html = html.replace(/#uid/g,uId).replace('#msg',xui.util.StringUtil.escapeHTML(msg).replace(/&lt;/g, '<').replace(/&gt;/g, '>'));
			}else{
				html = html.replace(/#uid/g,uId).replace('#msg',xui.util.StringUtil.escapeHTML(msg).replace(/&lt;br&gt;/g, '<br/>'));
			}


	        layer.innerHTML = xui.util.WinUtil.isIE6()? ($Shim()+html) :html;
    		document.body.appendChild(layer);
			
			//xui.util.Event.on(obj,'mouseover',function(){
			//	xui.widgets.Tip._close(uId);
			//});
/*			xui.util.Event.on(obj,'propertychange',function(){
				xui.widgets.Tip._close(uId);
			});*/
			
			xui.util.Event.on(window,'scroll',function(){
				layer.innerHTML = $Shim()+html;
			});
			xui.util.Event.on(window,'resize',function(){
				if(obj.style.display=='none' || obj.style.visibility=='hidden') return;
				var xy = xui.util.Dom.getXY(obj);
				if(xy[0]==0 && xy[1]==0) return;//�˶���ܿ��ܲ��ɼ�����TIP����ʾ	
				if(obj.offsetWidth){
					layer.style.left = (xy[0]+obj.offsetWidth+20) + 'px';
					layer.style.top = (xy[1]) + 'px';			
				}
			});

			if(!-[1,]){
				var layw = xui.util.Dom.getStyle(layer,'width');
				var layh = xui.util.Dom.getStyle(layer,'height');
				var str = '<iframe style="filter:alpha(opacity=0); position:absolute; left:0px; top:0px; z-index:-1; width:'+layw+';height:'+layh+'"></iframe>';
				layer.innerHTML += str;
			}
			
			if(model&&model.autoClose){	
				setTimeout(function(){ 
				xui.widgets.Tip.clear();
					},model.autoClose)
			}
			return layer;
		},
		_close:function(id){
			var obj=$(id);
			if(!obj) return;
			try{obj.parentNode.parentNode.removeChild(obj.parentNode);}catch(e){} 
		},
		_SRC_ELEMENTS:[],
		/**
		 * �����ǰҳ������Tip��
		 */
		clear:function(){
			for(var i=0;i<this._SRC_ELEMENTS.length;i++){
				var obj = $(this._SRC_ELEMENTS[i]);
				if(obj) this._close(obj.id);
			}		
			this._SRC_ELEMENTS = [];	
		}
	}
}();

/*
* class QueryTip
* @import xui.widgets.Tip
* @autor:tangqiang
* @data:2010-03-04
*
*/
xui.widgets.QueryTip = {
	/*
	* config {object} �� ���ò��� 
	* maxWidth {integer} ������� (Ĭ��ֵΪ0��Ĭ��ʱ�������Ӧ)
	* maxHeight {integer} : ���߶� (Ĭ��ֵΪ0��Ĭ��ʱ�߶�����Ӧ)
	* width {integer} : ��� (Ĭ��ֵΪ0��Ĭ��ʱ�������Ӧ)
	* height {integer} �� ��� (Ĭ��ֵΪ0��Ĭ��ʱ�߶�����Ӧ)
	*/
	config:{
		maxWidth:0,
		maxHeight:0,
		width:0,
		height:0
	},
	/*
	* query the node attribute as querytip
	*/
	init:function(){
		this.config.docW = xui.util.Dom.getDocumentWidth();
		this.config.docH = xui.util.Dom.getDocumentHeight();
		
		
		var ts = $T('*');
		this.obj = [];
		this.htmlTmp = '<div class="xui-tip-qtip-bar"><span class="xui-tip-qtip-btn" onclick="xui.widgets.Tip._close(\'#uid\')"></span></div><div id="#uid" class="xui-tip-qtip-border">#msg</div>';
		var _t = this;
		for(var i=0; i<ts.length; i++){
			if(ts.item(i).getAttribute('querytip')){
				this.obj.push(ts[i]);
				(function(t,_t){
					_t.exec(t);
				})(ts[i],_t);
			}
		}
	},
	/*
	* addEventListener 
	*/
	exec:function(node){	
		if(node.id==''){
			node.id = $UUID('qtip');
		}
		var _t = this;
		xui.util.Event.on(node,'mouseover',function(){
			xui.widgets.Tip.clear();
			var layer = xui.widgets.Tip.show(node.id,node.getAttribute('querytip'),_t.htmlTmp,{safe:true});
			_t.debugTip(layer,node); 
		});
		/*xui.util.Event.on(node,'mouseout',function(){
			xui.widgets.Tip.clear();
		});*/
	},
	/*
	* set configuration
	*/
	debugTip:function(layer,parent){
		
		layer.style.color = '#616161';
		
		/*
		* set width and height
		*/
		var node = layer,w = node.offsetWidth,h=node.offsetHeight;
		
		if(this.config.width!=0){
			w  = this.config.width;
			node.style.width = w+'px';
			
			/*����ȸı�ʱ������Ӧ�߶Ȼᷢ���仯����Ҫ���»�ȡ*/
			h=node.offsetHeight;
		}
		
		if(this.config.height!=0){
			h = this.config.height;
			node.style.height = h+'px';
			
			/*���߶ȸı�ʱ������Ӧ��Ȼᷢ���仯����Ҫ���»�ȡ*/
			w = node.offsetWidth;
		}
		
		if(w>this.maxWidth&&this.maxWidth!=0){
			w  = this.config.width;
			node.style.width = w+'px';
			h=node.offsetHeight;
		}
		
		
		if(h>this.maxHeight&&this.maxHeight!=0){
			h = this.maxHeight;
			node.style.height = h+'px';
			w = node.offsetWidth;
			node.style.overflow="auto";
		}
		
		/*
		* Set the display position
		*/
		var ph=parent.offsetHeight,pw=parent.offsetWidth,xy = xui.util.Dom.getXY(parent);
		if(w-pw<xy[0]&&xy[0]+w>this.config.docW){
			layer.style.left = xy[0]-(w-pw)+'px';
		}
		
		if(xy[1]-h>0&&xy[1]+ph+h>this.config.docH){
			layer.style.top = xy[1]-h+'px';
		}else{
			layer.style.top = xy[1]+ph+'px';
		}
		/*
		* �Ƴ���ʧ
		*/
		xy = xui.util.Dom.getXY(layer);
		xui.util.Event.on(layer,'mouseout',function(e){
			var target = xui.util.Event.getTarget(e),f=true;
			var mx = e.pageX||e.clientX+(document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft);
			var my = e.pageY||e.clientY+(document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop);
			/*alert(mx+"::"+my+"::"+xy[0]+'::'+xy[1]+'::'+w+'::'+h);
			if(xui.env.ua.ie>0 && xui.env.ua.ie<7){
				my+=150;
				alert(mx+"::"+my+"::"+xy[0]+'::'+xy[1]+'::'+w+'::'+h);
			}*/
			if(mx<=xy[0]||mx>=xy[0]+w||my<=xy[1]||my>=xy[1]+h){
				xui.widgets.Tip.clear();
				return;
			}
			/*while(target.parentNode&&f){
				target = target.parentNode;
				if(target==this){
					alert(target.innerHTML);
					f=false;
				}
			}
			if(f){xui.widgets.Tip.clear()}
			*/
			//xui.widgets.Tip.clear();
		});
	}
}

xui.util.Event.on(window,"load", $PreloadClass, [["xui-tip-left","xui-tip-bg","xui-tip-right"]]);
xui.util.Event.onDOMReady(
	function(){xui.widgets.QueryTip.init();}
);
