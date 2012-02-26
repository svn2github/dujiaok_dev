/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2010, alibaba.com All rights reserved.
 * @version 1.3
 * @author shengding
 * @date 2010-2-23
 * @requires core/xui-core.js
 * @requires util/util.js
 * update:2010-05-26 10:42:37
 */

/**
 * MsgBox����Ϣ��
 * 
 * @namespace xui.widgets
 * @singleton MsgBox
 */
xui.widgets.MsgBox = function(){
	return {
		zIndex:99,
		width:300,
		height:150,
		id: null,
		positionTop:false,
		alpha:0,
		
		_successHTML:function(id,msg,words){
			var v = (words && words.success)?words.success:'ȷ��';

			var html = [];
			html.push('<div class="xui-msgbox">');
			html.push('<div class="xui-msgbox-in">');
			html.push('<p class="xui-msg">'+msg+'</p>');
			html.push('<div class="xui-msgbox-submit">');
			html.push('<button class="xui-msgbox-btn" id="'+id+'_success" onclick="xui.widgets.MsgBox.close()"><span class="xui-msgbox-icon-confirm">'+v+'</span></button>');
         	html.push('</div></div></div>')
			return html.join('');	
		},
		_confirmHTML:function(id,msg,words){
			var y = (words &&words.yes)?words.yes:'ȷ��';
			var n = (words &&words.no)?words.no:'ȡ��';
			var html = [];
			html.push('<div class="xui-msgbox">');
			html.push('<div class="xui-msgbox-in">');
			html.push('<p class="xui-msg">'+msg+'</p>');
			html.push('<div class="xui-msgbox-submit">');
			html.push('<button class="xui-msgbox-btn" id="'+id+'_yes" onclick="xui.widgets.MsgBox.close()"><span class="xui-msgbox-icon-confirm">'+y+'</span></button>');
			html.push('<button class="xui-msgbox-btn" id="'+id+'_no" onclick="xui.widgets.MsgBox.close()"><span class="xui-msgbox-icon-cancel">'+n+'</span></button>');
         	html.push('</div></div></div>')
			return html.join('');	
		},
		_failHTML:function(id,msg,words){
			var v = (words && words.error)?words.error:'ʧ��';
			var html = [];
			html.push('<div class="xui-msgbox">');
			html.push('<div class="xui-msgbox-in">');
			html.push('<p class="xui-msg">'+msg+'</p>');
			html.push('<div class="xui-msgbox-submit">');
			html.push('<button class="xui-msgbox-btn" id="'+id+'_error" onclick="xui.widgets.MsgBox.close()"><span class="xui-msgbox-icon-cancel">'+v+'</span></button>');
         	html.push('</div></div></div>')
			return html.join('');	
		},
		_customHTML:function(id,msg,words){
			var html = [];
			return html.join('');		
		},
		_init: function(){
			var _layer = document.createElement('span');
			_layer.setAttribute('id',this.id);
			_layer.style.position = 'absolute';	            
			_layer.style.zIndex = this.zIndex;	
			_layer.style.width = this.width + 'px';
        	_layer.style.height = this.height + 'px';	
			if(this.positionTop){
				var canvas = $Canvas();
				_layer.style.left = parseInt((canvas.clientWidth-this.width)/2)+canvas.scrollLeft + 'px';
				if(parent){
					_layer.style.top = parent.document.documentElement.scrollTop+this.positionTop+'px';
				}else{
					_layer.style.top = document.documentElement.scrollTop+this.positionTop+'px';
				}
			}else{
				$Center(_layer);
			}
			//_layer.style.border = '1px solid red';
			document.body.appendChild(_layer);
			xui.util.Event.on(window,'resize',function(){
				$Center($(xui.widgets.MsgBox.id));					
			},null,this);
		},				
		_createBGLayer: function(){
			if($(this.id+'_bglayer')) return;
			var bgLayer = document.createElement('div');
			bgLayer.setAttribute('id',this.id+'_bglayer');
			bgLayer.style.position = 'absolute';
			bgLayer.style.left = '0';
			bgLayer.style.top = '0';
			bgLayer.style.background = '#cccccc';
			bgLayer.style.zIndex = 8;	
			if(xui.env.ua.ie>0){
				bgLayer.style.filter = 'alpha(opacity='+this.alpha*10+')';
			}else{
				bgLayer.style.opacity = this.alpha/10;
			}
			if(xui.env.ua.ie>0 && xui.env.ua.ie<7) bgLayer.innerHTML = $Shim();	//IE6����
			bgLayer.style.width = $Canvas().scrollWidth+'px';
			bgLayer.style.height = ($Canvas().clientHeight>$Canvas().scrollHeight?$Canvas().clientHeight:$Canvas().scrollHeight)+'px';	

			bgLayer.style.display = '';			
			document.body.appendChild(bgLayer);

		},	
		_create: function(events){
			this.id=$UUID('xui_msgbox_');
			this._createBGLayer();			
			
			this._events = {};
			this._onEvent(events);
			
			this._init();
		},
		_onEvent: function(events){
			if(events){
				for(var key in events){
					this._events[this.id+'_'+key]=events[key];
					xui.util.Event.on(this.id+'_'+key,'click',function(){
						var args = arguments[1];//�Ӻ����ĵ�2�������У���ȡ�¼��ص�����
     					this._fire(this.id+'_'+args[0]);
					},[key],this);
				}
			}			
		},
		/**
		 * @field {Boolean} �Ƿ��Ѵ�
		 */
		isOpenning: false,
		/**
		 * ���ύ
		 * @param {String} formId ��Id
		 * @param {Object} events �ص��¼����ϣ���������˷���ҳ֧�֣���{'success':xxx,'error':xxx,'close':xxx}
		 */
		submit: function(formId,events){
			if(this.isOpenning) return;//��ֹ�ظ���		
			this.isOpenning = true;
			this._create(events);			
			
			var bglayer = $(this.id+'_bglayer');
			if(bglayer) bglayer.style.display = '';
			var layer = $(this.id);
			layer.style.display = '';	
			layer.innerHTML = $Shim()+$Iframe({id:this.id+'_ifr',zIndex:this.zIndex});		
			
			var form = $(formId);
			form.target= this.id+'_ifr';
			form.method='post';
			form.submit();
		},
		/**
		 * ��ʾ
		 * @param {Object} style ���{type:'success|confirm|fail|custom',msg:'xxxxxxxxxx'}
		 * @param {Object} events �ص��¼����ϣ�{'yes':xxx,'no':xxx,'close':xxx}
		 * @param {Object}  words ��Ϊ�ļ��ϣ�{'yes':'ȷ��','no':'ȡ��','success':'�ر�','error':'ʧ��'}
		 */		
		show: function(style,events,words){
			if(this.isOpenning) return;//��ֹ�ظ���		
			this.isOpenning = true;				
			this._create(events);
			var bglayer = $(this.id+'_bglayer');

			var type = null;
			var msg = '';
			if(style){
				if((style.type)) type = style.type;
				if((style.msg)) msg = style.msg;
			}
			 
			var html = '';					
			if(type=='success'){
				html = this._successHTML(this.id,msg,words);
			}else if(type=='confirm'){
				html = this._confirmHTML(this.id,msg,words);
			}else if(type=='fail'){
				html = this._failHTML(this.id,msg,words);
			}else if(type=='custom') {
				html = this._customHTML(this.id,msg,words);
			}			
			
			
			if(bglayer) bglayer.style.display = '';
			var layer = $(this.id);
			layer.style.display = '';	
			layer.innerHTML = html;
		},
		/**
		 * @event yes �û������ȷ����
		 * @event no �û������ȡ����
		 * @event close �û�������رա�
		 * @event success ��ִ�гɹ�
		 * @event error ��ִ��ʧ��
		 */
		_events:null,
		_fire: function(eName,data){
			var fun = this._events[eName];			
			if(fun) {
				try{
					fun.call(this,data?data:null);
				}catch(e){alert('[xui-widgets-MsgBox]�¼�<'+eName+'>�ص�����'+e)}
			}			
		},
		/**
		 * �ر�
		 */
		close: function(){
			var bglayer = $(this.id+'_bglayer');
			if(bglayer) bglayer.style.display = 'none';
			$Visibles('select',true);
			
			var layer = $(this.id);
			layer.style.display = 'none';
			this.isOpenning = false;
		},
		/**
		 * ��������
		 * @param {String} ��Ҫ���õ�����
		 */
		 setContent:function(sContent){
		 	var content = xui.util.Dom.getElementsByClassName('xui-msg','p')[0];
			content.innerHTML = sContent;
		 }
	}
}();

