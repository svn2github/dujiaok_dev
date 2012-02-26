/**
 *  @author     Aissa   
 *  @date       2009-09-11
 *  编辑器源码所见即所得编辑
 */
(function() {
	tinymce.PluginManager.requireLangPack('aliViewAndCode');

	tinymce.create('tinymce.plugins.aliViewAndCode', {
		init: function(ed, url) {
			var t = this, row, txt, DOM = tinymce.DOM, Event = tinymce.dom.Event, type = 0;
			
			ed.addCommand('beforeSubmit', function(){
				if(ed.isHidden()){
					ed.load();
				}
			});
			
			t.vid = ed.id + '-ali-foot-btnView';
			t.cid = ed.id + '-ali-foot-btnCode';
			ed.onPostRender.add(function(ed, cm) {
				var tbl = DOM.get(ed.id + '_tbl');
				var _w = tbl.offsetWidth;

				row = DOM.get(ed.id + '_path_row');
				//初始化可视化脚页按钮
				if (row) { //aliRTE-btn-view btn-pressed  aliRTE-btn-code
				    tinymce.DOM.add(row.parentNode, 'div', { 'style': 'float: left; position:relative;' },
						'<span class="aliRTE-btn-viewandcode aliRTE-btn-onview" title="' + ed.translate('aliViewAndCode.btnView') + '">' + ed.translate('aliViewAndCode.btnView') + '</span><span id="' + t.cid + '" class="aliRTE-btn-viewandcode aliRTE-btn-offcode" title="' + ed.translate('aliViewAndCode.btnCode') + '">' + ed.translate('aliViewAndCode.btnCode') + '</span>');
				}
				row.style.display = 'none';
				//初始化代码模式脚页按钮
				txt = DOM.get(ed.id);
				if (txt) {
					var codeStatusBar = document.createElement('div');
					codeStatusBar.id = ed.id + '-ali-code-status';
					codeStatusBar.className = 'alirte_statusbar';
					codeStatusBar.style.width = (_w - 4) + 'px';
					codeStatusBar.style.height = '22px';
					codeStatusBar.style.border = '1px solid #cfcfcf';
					codeStatusBar.style.marginTop = '-2px';
					codeStatusBar.style.paddingTop = '2px';
					codeStatusBar.style.paddingLeft = '2px';
					codeStatusBar.style.backgroundColor = '#f0f0ee';
					codeStatusBar.style.position = 'relative';
					codeStatusBar.style.display = 'none';
					codeStatusBar.innerHTML = '<span id="' + t.vid + '" class="aliRTE-btn-viewandcode aliRTE-btn-offview" title="' + ed.translate('aliViewAndCode.btnView') + '">' + ed.translate('aliViewAndCode.btnView') + '</span><span class="aliRTE-btn-viewandcode btn-pressed aliRTE-btn-oncode" title="' + ed.translate('aliViewAndCode.btnCode') + '">' + ed.translate('aliViewAndCode.btnCode') + '</span>';

					tinymce.DOM.insertAfter(codeStatusBar, ed.id + '_parent');
				}
					
				var txtP, txtN, txtF = null;
				Event.add(DOM.get(t.cid), 'click', function(e) {
					ed.execCommand('aliFilterHTML');
					ed.execCommand('hideContextMenu');
					
					//获取span大小
					var _w = tbl.offsetWidth;
					var _h = tbl.offsetHeight;
					ed.hide();

					txt.style.display = '';
					if(tinymce.isIE){
						txt.style.width = (_w - 4) + 'px';
					}
					else{
						txt.style.width = (_w - 2) + 'px';
					}
					if(tinymce.isIE6 || /MSIE [7]/.test(navigator.userAgent)){
						txt.style.height = (_h - 30) + 'px';
					}
					else{
						txt.style.height = (_h - 28) + 'px';
					}
					txt.className = 'alirte_textarea';

					DOM.setStyle(ed.id + '-ali-code-status', 'display', 'block');
					DOM.setStyle(ed.id + '-ali-code-status', 'width', (_w - 4) + 'px');
				});

				Event.add(DOM.get(t.vid), 'click', function(ev) {
					ed.execCommand('aliFilterHTML');
					ed.execCommand('hideContextMenu');

					DOM.setStyle(ed.id + '-ali-code-status', 'display', 'none');
					txt.style.display = 'none';
					ed.show();
				});
			});
		}
	});

	tinymce.PluginManager.add('aliViewAndCode', tinymce.plugins.aliViewAndCode);
})();