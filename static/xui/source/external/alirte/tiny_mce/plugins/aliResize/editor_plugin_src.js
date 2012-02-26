/**
 *  @author     Lucars   
 *  @date       2009-09-17
 *  重置编辑器放大缩小功能
 */
(function() {
	tinymce.PluginManager.requireLangPack('aliResize');

	tinymce.create('tinymce.plugins.aliResize', {
		init: function(ed, url) {
			var t = this, row, DOM = tinymce.DOM, Event = tinymce.dom.Event, times = 0;
			t.lid = ed.id + '-ali-foot-btnLarger';
			t.sid = ed.id + '-ali-foot-btnSmaller';
			t.editor = ed.id + '_tbl';
			t.iframe = ed.id + '_ifr';
			t.resize = ed.id + '_resize';
			ed.onPostRender.add(function(ed, cm){
				//row 即为statusBar
				row = tinymce.DOM.get(ed.id + '_path_row');
				if (row){
					tinymce.DOM.add(row.parentNode, 'div', {'style': 'float: right'},
						'<span id="' + t.lid + '" class="aliRTE-btn-Larger" title="' + ed.translate('aliResize.btnLarger') + '">' + ed.translate('aliResize.btnLarger') + '</span><span id="' + t.sid + '" class="aliRTE-btn-Smaller" title="' + ed.translate('aliResize.btnSmaller') + '">' + ed.translate('aliResize.btnSmaller') + '</span>');
				}
				//隐藏原来的放大缩小按钮
				Event.remove(t.resize, 'mousedown');
				Event.remove(t.resize, 'mouseup');
				Event.remove(t.resize, 'click');
				DOM.remove(t.resize, false);
				var _step = (!!this.settings.resize_set)?this.settings.resize_set.stepHeight || 80:80;
				var _minh = (!!this.settings.resize_set)?this.settings.resize_set.minHeight || 0:0;
				var _maxh = (!!this.settings.resize_set)?this.settings.resize_set.maxHeight || 0:0;

				Event.add(t.lid, 'click', function(e){
					var _tbls = DOM.getSize(t.editor);
					if(_maxh > 0 && _tbls.h < _maxh){
						var _h = Math.min(_maxh, _tbls.h + _step);
						DOM.setStyle(t.editor, 'height', _h + 'px');
						var _ifrs = DOM.getSize(t.iframe);
						_h = _h - _tbls.h + _ifrs.h;
						DOM.setStyle(t.iframe, 'height', _h + 'px');
						//times++;
						DOM.setStyle(t.sid, 'backgroundPosition', '-28px -87px');
						if(_h == _maxh - _tbls.h + _ifrs.h){
							DOM.setStyle(t.lid, 'backgroundPosition', '-52px -68px');
						}
					}
					else{
						DOM.setStyle(t.lid, 'backgroundPosition', '-52px -68px');
					}
				});

				Event.add(t.sid, 'click', function(e){
					var _tbls = DOM.getSize(t.editor);
					if(_minh > 0 && _tbls.h > _minh){
						var _h = Math.max(_minh, _tbls.h - _step);
						DOM.setStyle(t.editor, 'height', _h + 'px');
						var _ifrs = DOM.getSize(t.iframe);
						_h = _ifrs.h - (_tbls.h - _h);
						DOM.setStyle(t.iframe, 'height', _h + 'px');
						//times--;
						DOM.setStyle(t.lid, 'backgroundPosition', '-28px -68px');
						if(_h == _ifrs.h - (_tbls.h - _minh)){
							DOM.setStyle(t.sid, 'backgroundPosition', '-52px -87px');
						}
					}
					else{
						DOM.setStyle(t.sid, 'backgroundPosition', '-52px -87px');
					}
				});

				var _tbls = DOM.getSize(t.editor);
				if(_minh > 0 && _tbls.h > _minh){
					DOM.setStyle(t.sid, 'backgroundPosition', '-28px -87px');
				}
			});
		}
    });

    tinymce.PluginManager.add('aliResize', tinymce.plugins.aliResize);
})();