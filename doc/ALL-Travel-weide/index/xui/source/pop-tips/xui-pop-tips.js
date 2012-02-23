xui.widgets.PopTips = function(id,attrs){
	this.id = id;
	this.attrs = attrs?attrs:null;	
	this.attrs.title = ( this.attrs && this.attrs.title)?this.attrs.title:'';	
	this.attrs.content = ( this.attrs && this.attrs.content)?this.attrs.content:'';
}
xui.widgets.PopTips.prototype = {
	init:function(){
		var layer = document.createElement('div');
		layer.id='xui-pop-tips-'+this.id;
		document.body.appendChild(layer) ;
		xui.util.Dom.addClass(layer,'xui-pop-tips');
		layer.innerHTML= this._html();
		var o =this;
		xui.util.Event.on('xui-pop-tips-'+this.id+'-close', 'click',function(){
			o.close();
			});
		if(xui.env.ua.ie == 6){
			
			xui.util.Event.on(window,'scroll',function(){
				o._rePositon();
			})
		}

		
		
	},

	_html:function(){
		var html = [];
		html.push('<div class="xui-pop-tips-title clearfix"><span class="xui-pop-tips-title-tip">'+this.attrs['title']+'</span><span class="xui-pop-tips-close" id="xui-pop-tips-'+this.id+'-close"></span></div>');
		html.push('<div class="xui-pop-tips-content">');
		html.push(this.attrs['content']);
/*		html.push('<div class="xui-pop-tips-content-title">温馨提示： </div>');
		html.push('<div class="xui-pop-tips-content-detail">您五月份的账单，应付金额500元，20日为扣款日,您当前账户为100元，为保证您的正常使用，请及时充值！</div>');
		html.push('<div class="xui-pop-tips-content-btn"></div>');
*/		html.push('</div></div>');
		html.push('</div></div>');
		return html.join('');
	},
	_rePositon:function(){
		var h =xui.util.Dom.getDocumentScrollTop()+xui.util.Dom.getViewportHeight();
		
		var tipH = xui.util.Dom.getStyle($('xui-pop-tips-'+this.id),'height');
		$('xui-pop-tips-'+this.id).style.top = h-parseInt(tipH,10);
	},
	close:function(){
		$('xui-pop-tips-'+this.id).style.display = 'none';	
	}
}