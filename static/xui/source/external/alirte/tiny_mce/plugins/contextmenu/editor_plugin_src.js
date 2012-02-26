/**
 * $Id: editor_plugin_src.js 848 2008-05-15 11:54:40Z spocke $
 *
 * @author Moxiecode
 * @copyright Copyright ?2004-2008, Moxiecode Systems AB, All rights reserved.
 */

(function() {
	var Event = tinymce.dom.Event, each = tinymce.each, DOM = tinymce.DOM;

	tinymce.create('tinymce.plugins.ContextMenu', {
		init : function(ed) {
			var t = this;

			t.editor = ed;
			t.onContextMenu = new tinymce.util.Dispatcher(this);

			ed.onContextMenu.add(function(ed, e) {
				if (!e.ctrlKey) {
					t._getMenu(ed).showMenu(e.clientX, e.clientY);
					Event.add(ed.getDoc(), 'click', hide);
					Event.cancel(e);
				}
			});

			ed.addCommand('hideContextMenu', function(){
				if (t._menu) {
					t._menu.removeAll();
					t._menu.destroy();
					Event.remove(ed.getDoc(), 'click', hide);
				}
			});
			
			function hide(){
				ed.execCommand('hideContextMenu');
			}

			ed.onMouseDown.add(hide);
			ed.onKeyDown.add(hide);
		},

		getInfo : function() {
			return {
				longname : 'Contextmenu',
				author : 'Moxiecode Systems AB',
				authorurl : 'http://tinymce.moxiecode.com',
				infourl : 'http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/contextmenu',
				version : tinymce.majorVersion + "." + tinymce.minorVersion
			};
		},

		_getMenu : function(ed) {
			var t = this, m = t._menu, se = ed.selection, col = se.isCollapsed(), el = se.getNode() || ed.getBody(), am, p1, p2;

			if (m) {
				m.removeAll();
				m.destroy();
			}

			p1 = DOM.getPos(ed.getContentAreaContainer());
			//p2 = DOM.getPos(ed.getContainer());

			m = ed.controlManager.createDropMenu('contextmenu', {
				offset_x : p1.x + ed.getParam('contextmenu_offset_x', 0),
				offset_y : p1.y + ed.getParam('contextmenu_offset_y', 0),
				constrain : 1
			});

			t._menu = m;

			if(tinymce.isIE){
				m.add({title : 'advanced.cut_desc', icon : 'cut', cmd : 'Cut'}).setDisabled(col);
				m.add({title : 'advanced.copy_desc', icon : 'copy', cmd : 'Copy'}).setDisabled(col);
				m.add({title : 'advanced.paste_desc', icon : 'paste', cmd : 'Paste'});
			}
			
			m.add({title : 'paste.paste_text_desc', icon : 'pastetext', cmd: 'mcePasteText' });
			m.add({title : 'paste.paste_word_desc', icon : 'pasteword', cmd: 'mcePasteWord' });

			if ((el.nodeName == 'A' && !ed.dom.getAttrib(el, 'name')) || !col) {
				m.addSeparator();
				m.add({title : 'advanced.link_desc', icon : 'link', cmd : ed.plugins.advlink ? 'mceAdvLink' : 'mceLink', ui : true});
				m.add({title : 'advanced.unlink_desc', icon : 'unlink', cmd : 'UnLink'});
			}

			m.addSeparator();

			m.add({title: 'contextmenu.left', icon: 'justifyleft', cmd: 'JustifyLeft' });
			m.add({title: 'contextmenu.center', icon: 'justifycenter', cmd: 'JustifyCenter' });
			m.add({title: 'contextmenu.right', icon: 'justifyright', cmd: 'JustifyRight' });
			m.add({title: 'contextmenu.full', icon: 'justifyfull', cmd: 'JustifyFull' });
			m.addSeparator();
			
			m.add({title : 'advanced.image_desc', icon : 'image', cmd : ed.plugins.aliImage ? 'aliimagePop' : 'mceImage', ui : true});
			/*am = m.addMenu({title : 'contextmenu.align'});
			am.add({title : 'contextmenu.left', icon : 'justifyleft', cmd : 'JustifyLeft'});
			am.add({title : 'contextmenu.center', icon : 'justifycenter', cmd : 'JustifyCenter'});
			am.add({title : 'contextmenu.right', icon : 'justifyright', cmd : 'JustifyRight'});
			am.add({title : 'contextmenu.full', icon : 'justifyfull', cmd : 'JustifyFull'});*/

			t.onContextMenu.dispatch(t, m, el, col);

			return m;
		}
	});

	// Register plugin
	tinymce.PluginManager.add('contextmenu', tinymce.plugins.ContextMenu);
})();