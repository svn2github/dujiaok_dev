/**
 * $Id: editor_plugin_src.js 677 2008-03-07 13:52:41Z spocke $
 *
 * @author Moxiecode
 * @copyright Copyright ?2004-2008, Moxiecode Systems AB, All rights reserved.
 */

(function() {
    tinymce.PluginManager.requireLangPack('aliImage');

    tinymce.create('tinymce.plugins.AliImagePlugin', {
        init: function(ed, url) {
            // 注册命令
            ed.addCommand('aliimagePop', function() {

                // img delete bug
                ed.execCommand('mceInsertContent', false, '<span id="dvimg_ali_ali">&nbsp;</span>', { skip_undo: 1 });
            
                ed.windowManager.open({
                    file: url + '/aliimage-dialog.htm',
                    width: 500 + parseInt(ed.getLang('aliimage.delta_width', 0)),
                    height: 400 + parseInt(ed.getLang('aliimage.delta_height', 0)),
                    inline: 1
                }, {
                    plugin_url: url
                });
            });

            ed.addCommand('advImageResize', function() {
                var _img = ed.selection.getNode();
                var newImage = new Image();
                newImage.onload = function() {
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

					ed.dom.setAttrib(_img, 'mce_style', '');
            });

            ed.addCommand('advImageLeft', function() {
                var _img = ed.selection.getNode();
                var _float = (tinymce.isIE) ? 'styleFloat' : 'cssFloat';
                _img.style[_float] = 'left';
                _img.style['display'] = '';
                _img.style['margin'] = '';

					ed.dom.setAttrib(_img, 'mce_style', 'float:left;');
            });

            ed.addCommand('advImageCenter', function() {
                var _img = ed.selection.getNode();
                var _float = (tinymce.isIE) ? 'styleFloat' : 'cssFloat';
                _img.style[_float] = 'none';
                _img.style['display'] = 'block';
                _img.style['margin'] = '0 auto';
                _img.style['textAlign'] = 'center';

					ed.dom.setAttrib(_img, 'mce_style', 'display:block;margin:0 auto;text-align:center;');
            });

            ed.addCommand('advImageRight', function() {
                var _img = ed.selection.getNode();
                var _float = (tinymce.isIE) ? 'styleFloat' : 'cssFloat';
                _img.style[_float] = 'right';
                _img.style['display'] = '';
                _img.style['margin'] = '';

					ed.dom.setAttrib(_img, 'mce_style', 'float:right;');
            });

            // Register buttons

            ed.addButton('image', {
                title: 'advimage.image_desc',
                cmd: 'aliimagePop'
            });



            ed.onInit.add(function() {
                if (ed && ed.plugins.contextmenu) {
                    ed.plugins.contextmenu.onContextMenu.add(function(th, m, e) {
                        var sm, se = ed.selection, el = se.getNode() || ed.getBody();
                        if (e.tagName == 'IMG') {
                            m.removeAll();

							if(tinymce.isIE){
                            m.add({ title: 'advanced.cut_desc', icon: 'cut', cmd: 'Cut' });
                            m.add({ title: 'advanced.copy_desc', icon: 'copy', cmd: 'Copy' });
                            m.add({ title: 'advanced.paste_desc', icon: 'paste', cmd: 'Paste' });
                            m.addSeparator();
							}

                            m.add({ title: 'aliimage.align_default', icon: 'advimagedefault', cmd: 'advImageDefault' });
                            m.add({ title: 'aliimage.align_left', icon: 'advimageleft', cmd: 'advImageLeft' });
                            m.add({ title: 'aliimage.align_center', icon: 'advimagecenter', cmd: 'advImageCenter' });
                            m.add({ title: 'aliimage.align_right', icon: 'advimageright', cmd: 'advImageRight' });
                            m.addSeparator();
                            m.add({ title: 'aliimage.image_resize', icon: 'advimageresize', cmd: 'advImageResize' });
                        }
                    });
                }
            });
        },

        getInfo: function() {
            return {
                longname: 'Alibaba RTE Image Plugin',
                author: 'Alibaba UED Team',
                authorurl: 'http://www.aliued.cn',
                infourl: '',
                version: '1.0'
            };
        }
    });

    // Register plugin
    tinymce.PluginManager.add('aliImage', tinymce.plugins.AliImagePlugin);

})();
