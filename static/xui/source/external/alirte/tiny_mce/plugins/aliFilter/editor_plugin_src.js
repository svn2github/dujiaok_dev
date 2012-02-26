/**
 *  @author     Aissa   
 *  @date       2009-09-03
 *  ifr = DOM.get(id);
 *  ide = ifr.contentWindow.document || ifr.contentDocument;
 *  字数统计
 */
(function() {
	tinymce.create("tinymce.plugins.aliPasteFilter", {
		init: function(ed, url) {
				var t = this, DOM = tinymce.DOM;
				t.url = url;

				/*
				ed.onKeyDown.add(function(ed, e) {
					t._clear(ed, e);
				});

				ed.onKeyUp.add(function(ed, e) {

				})

				ed.onPaste.add(function(ed, e, o) {
					t._clear(ed, e, true);
				});
				*/

				ed.onChange.add(function(ed, e) {
					ed.aliRTE_hasChanged = true;
				});
				
				var txt = DOM.get(ed.id);
				txt.onkeyup = function(){
					ed.aliRTE_hasChanged = true;
				};

				ed.addCommand('aliFilterHTML', function(){
					/*
					//remove tag
					var tag_remove = ['noscript','script','style','head','select','input','textarea','form','iframe','frame','frameset'];
					for(var i = 0; i < tag_remove.length; i++){
						var _c = tinymce.DOM.select(tag_remove[i], ed.getBody());
						tinymce.DOM.remove(_c);
					}
					//fliter links
					var all_anchor = tinymce.DOM.select('a', ed.getBody());
					var defSetting = {is_ali_only: true},
						aliURLs = [
							'*.alibaba.com',
							'*.alibaba.com.cn',
							'*.taobao.com',
							'*.alipay.com',
							'*.alisoft.com',
							'*.yahoo.com',
							'*.yahoo.com.cn',
							'*.koubei.com'
						];
					var c = ed.getParam('image_config', defSetting);

					if(!c.allow_all){
						tinymce.each(all_anchor, function(a){
							if(c.is_ali_only){
								c.white_list = [];
								if(c.white_list[c.white_list.length - 1] != '{end}'){
									for(var i = 0; i < aliURLs.length; i++){
										c.white_list.push(aliURLs[i]);
									}
									c.white_list.push('{end}');
								}
							}
							else{
								if(c.white_list[c.white_list.length - 1] != '{end}'){
									for(var i = 0; i < aliURLs.length; i++){
										c.white_list.push(aliURLs[i]);
									}
									c.white_list.push('{end}');
								}
							}

							for(var i = 0; i < c.white_list.length; i++){
								var u = c.white_list[i];
								var b = false;
								if(u.charAt(0) === '*'){
									u = u.substr(2);
									u = u.replace(/\./gi, '\\.');
									var r = new RegExp('^http([s]?):\/\/([a-z0-9\-_]+\.)*' + u + '(\/[^\/]*)*$', 'i');
									b = r.test(a.href);
								}
								else{
									u = u.replace(/\./gi, '\\.');
									var r = new RegExp('^http(s?):\/\/' + u + '(\/[^\/]*)*$', 'i');
									b = r.test(a.href);
								}
								
								if(!b){
									tinymce.DOM.remove(a);
								}
							}
						});
					}
					
					var replaced = false;
					var _html = ed.getContent();
					//remove redundant tag code
					var reg_redundany = /<[\/]?(script|noscript|style|head|select|form|iframe|frame|frameset|\!DOCTYPE|abbr|acronym|address|applet|base|basefont|bdo|body|button|code|del|dir|dfn|fieldset|html|input|ins|isindex|kbd|label|legend|link|menu|meta|noframes|object|optgroup|option|param|q|s|samp|textarea|title|tt|var|xmp|alitemp)([\s]?[^<>])*?>/gi;
					if(reg_redundany.test(_html)) {
						_html = _html.replace(reg_redundany, '');
						replaced = true;
					}
					//remove id, name, classname, events
					var reg_properties = /\s(id|name|class|(on[\w]+?))="?([^">=]+)"?([>|\s]+)/gi;
					if(reg_properties.test(_html)) {
						_html = _html.replace(reg_properties, function(m, m1, m2, m3, m4){return m4;});
						replaced = true;
					}
					if(replaced){
						ed.setContent(_html);
					}
					*/
					if(false){
						if(ed.aliRTE_hasChanged){
							var reg = [/<(((no)?script)|style|head|select|input|testarea|form|([i]?frame(set)?))([ ][^<>]*)?>(.|\n|\r)*?<\/\1([ ][^<>]*)?>/gi,
											/<[\/]?(script|noscript|style|head|select|form|iframe|frame|frameset|\!DOCTYPE|abbr|acronym|address|applet|base|basefont|bdo|body|button|code|del|dir|dfn|fieldset|html|input|ins|isindex|kbd|label|legend|link|menu|meta|noframes|object|optgroup|option|param|q|s|samp|textarea|title|tt|var|xmp|alitemp)([\s][^<>]*?)?>/gi,
											/[\s](id|name|class|(on[\w]+?))\="[^"\n\r]*?"/gi,
											/<a(.*)href="?(.*)((?!taobao|alipay|alibaba|yahoo|alisoft|alimama|koubei|aliimg)\w)+\.(.*)"?([^>]*)>([^<]*)<\/a>/gi],
								_html, replaced = false;

							_html = ed.getContent();

							// 去除XSS
							if (reg[0].test(_html)) {
								_html = _html.replace(reg[0], "");
								replaced = true;
							}
							/*
							// 去除冗余
							if (reg[1].test(_html)) {
								_html = _html.replace(reg[1], "");
								replaced = true;
							}
							// 去除冗余属性
							if (reg[2].test(_html)) {
								_html = _html.replace(reg[2], "");
								replaced = true;
							}
							// 去除外网链接
							if (reg[3].test(_html)) {
								_html = _html.replace(reg[3], "");
							}
							*/

							if (replaced) {
								ed.setContent(_html);
							}
							
							ed.aliRTE_hasChanged = false;
						}
					}
				});
        },

		/*
		// 火狐下按下ctrl使body禁止paste
		_fbpFF: function(ed, ev) {
		var DOM = tinymce.DOM, ifr = DOM.get(ed.id + '_ifr'), bd = DOM.getRoot();
		if (!tinymce.isIE && ev.keyCode == 17) {
		//alert(bd.tagName);
		DOM.setAttrib(bd, 'onpaste', 'return false');
		//alert(1)
		}
		},
		// 火狐下body允许paste 
		_fbpT: function(ed, ev) {
		},
		*/

		_clear: function(ed, ev, t) {
			if(false){
				var t = this;
				var DOM = tinymce.DOM, dom;
				//alert(DOM.getRoot().tagName);
				if (ev.keyCode == 17) {
					ed.execCommand('mceInsertContent', false, '<div id="dv_ali_v_v"></div>');
				}
				if ((ev.ctrlKey && ev.keyCode == 86) || arguments.length == 3) {
					if (!tinymce.isIE) {
						/*ed.windowManager.open({
						file: t.url + '/HTMLPage2.htm',
						width: parseInt(ed.getParam("paste_dialog_width", "450")),
						height: parseInt(ed.getParam("paste_dialog_height", "400")),
						inline: 1
						});*/
					}

					dom = DOM.get('dv_ali_v_v') || ed.selection.getEnd();
					//alert(dom.id);
					setTimeout(function() {
						var reg = [/<(((no)?script)|style|head|select|input|testarea|form|([i]?frame(set)?))([ ][^<>]*)?>(.|\n|\r)*?<\/\1([ ][^<>]*)?>/gi,
									/<[\/]?(script|noscript|style|head|select|form|iframe|frame|frameset|\!DOCTYPE|abbr|acronym|address|applet|base|basefont|bdo|body|button|code|del|dir|dfn|fieldset|html|input|ins|isindex|kbd|label|legend|link|menu|meta|noframes|object|optgroup|option|param|q|s|samp|textarea|title|tt|var|xmp|alitemp)([ ][^<>]*?)?>/gi,
									/[ ](id|name|class|(on[\w]+?))\="[^"\n\r]*?"/gi,
									/\"http[s]?:\/\/([a-z0-9\-_]+\.)*((?!taobao|alipay|alibaba|yahoo|alisoft|alimama|koubei|aliimg)\w)+\.(com|net|cn|com\.cn)(\/.*)*\"/gi],
							ctt, sr;

						sr = ctt = dom.innerHTML;

						// 去除XSS
						if (reg[0].test(ctt)) {
							ctt = ctt.replace(reg[0], "");
						}
						/*
						// 去除冗余
						if (reg[1].test(ctt)) {
							ctt = ctt.replace(reg[1], "");
						}
						// 去除冗余属性
						if (reg[2].test(ctt)) {
							ctt = ctt.replace(reg[2], "");
						}
						// 去除外网
						if (reg[3].test(ctt)) {
							ctt = ctt.replace(reg[3], "");
						}
						*/
						if (sr !== ctt) {
							dom.innerHTML = ctt;
						}
						//DOM.remove(DOM.get('dv_ali_v_v'), false);
					}, 1);
				}
			}
		},

		getInfo: function() {
			return {
				longname: 'aliPasteFilter',
				author: 'Aissa',
				authorurl: 'none',
				infourl: 'none',
				version: tinymce.majorVersion + "." + tinymce.minorVersion
			};
		}
	});

	tinymce.PluginManager.add('aliFilter', tinymce.plugins.aliPasteFilter);
})();