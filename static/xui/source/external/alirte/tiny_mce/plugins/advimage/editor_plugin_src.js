/**
 * $Id: editor_plugin_src.js 677 2008-03-07 13:52:41Z spocke $
 *
 * @author Moxiecode
 * @copyright Copyright ?2004-2008, Moxiecode Systems AB, All rights reserved.
 */

(function() {
	tinymce.PluginManager.requireLangPack('advimage');

	tinymce.create('tinymce.plugins.AdvancedImagePlugin', {
		init : function(ed, url) {
			// Register commands
			ed.addCommand('mceAdvImage', function() {
				// Internal image object like a flash placeholder
				if (ed.dom.getAttrib(ed.selection.getNode(), 'class').indexOf('mceItem') != -1)
					return;

				ed.windowManager.open({
				file: url+'/aliimage-dialog.htm',
					width : 450 + parseInt(ed.getLang('advimage.delta_width', 0)),
					height : 385 + parseInt(ed.getLang('advimage.delta_height', 0)),
					inline : 1
				}, {
					plugin_url : url
				});
			});
			
			ed.addCommand('advImageResize', function() {
				var _img = ed.selection.getNode();
				var newImage = new Image();
				newImage.onload = function(){
					var _w = this.width,
						_h = this.height;
					
					_img.width = _w;
					_img.height = _h;
					
					delete newImage;
				}
				newImage.src = _img.src;
			});
			
			ed.addCommand('advImageDefault', function() {
				var _img = ed.selection.getNode();
				var _float = (tinymce.isIE) ? 'styleFloat' : 'cssFloat';
				_img.style[_float] = 'none';
				_img.style['display'] = '';
				_img.style['margin'] = '';
			});
			
			ed.addCommand('advImageLeft', function() {
				var _img = ed.selection.getNode();
				var _float = (tinymce.isIE) ? 'styleFloat' : 'cssFloat';
				_img.style[_float] = 'left';
			});
			
			ed.addCommand('advImageCenter', function() {
				var _img = ed.selection.getNode();
				var _float = (tinymce.isIE) ? 'styleFloat' : 'cssFloat';
				_img.style[_float] = 'none';
				_img.style['display'] = 'block';
				_img.style['margin'] = '0 auto';
			});
			
			ed.addCommand('advImageRight', function() {
				var _img = ed.selection.getNode();
				var _float = (tinymce.isIE) ? 'styleFloat' : 'cssFloat';
				_img.style[_float] = 'right';
			});

			// Register buttons
			ed.addButton('image', {
				title : 'advimage.image_desc',
				cmd : 'mceAdvImage'
			});
			
			ed.onInit.add(function() {
				if (ed && ed.plugins.contextmenu) {
					ed.plugins.contextmenu.onContextMenu.add(function(th, m, e) {
						var sm, se = ed.selection, el = se.getNode() || ed.getBody();
						if (e.tagName == 'IMG') {
							m.removeAll();

							m.add({title : 'advanced.cut_desc', icon : 'cut', cmd : 'Cut'});
							m.add({title : 'advanced.copy_desc', icon : 'copy', cmd : 'Copy'});
							m.add({title : 'advanced.paste_desc', icon : 'paste', cmd : 'Paste'});
							m.addSeparator();
							
							m.add({title : 'advimage.align_default', icon : 'advimagedefault', cmd : 'advImageDefault'});
							m.add({title : 'advimage.align_left', icon : 'advimageleft', cmd : 'advImageLeft'});
							m.add({title : 'advimage.align_center', icon : 'advimagecenter', cmd : 'advImageCenter'});
							m.add({title : 'advimage.align_right', icon : 'advimageright', cmd : 'advImageRight'});
							m.addSeparator();
							m.add({title : 'advimage.image_resize', icon : 'advimageresize', cmd : 'advImageResize'});
						}
					});
				}
			});
		},

		getInfo : function() {
			return {
				longname : 'Advanced image',
				author : 'Moxiecode Systems AB',
				authorurl : 'http://tinymce.moxiecode.com',
				infourl : 'http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/advimage',
				version : tinymce.majorVersion + "." + tinymce.minorVersion
			};
		}
	});

	// Register plugin
	tinymce.PluginManager.add('advimage', tinymce.plugins.AdvancedImagePlugin);
})();
