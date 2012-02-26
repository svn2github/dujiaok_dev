 /**
 * $Id: editor_plugin_src.js 201 2007-02-12 15:56:56Z spocke $
 *
 * @author Moxiecode
 * @copyright Copyright ?2004-2008, Moxiecode Systems AB, All rights reserved.
 */

(function() {
    //加载语言包
    tinymce.PluginManager.requireLangPack('aliGroup');
    //简历插件类
    tinymce.create('tinymce.plugins.aliGroup', {
        //编辑器初始化后将初始化一个插件实例
        init: function(ed, url) {
            //在这个实例中我们保存一些编辑器的公用信息
            var _me = this;
            //初始化id编号
            _me.id = 0;
            //保留配置信息
            _me.settings = ed.settings;
            //保留命令列表
            _me.cmd = ed.theme.controls;
        },
        //这里是一个Hook 在创建按钮列表时被触发调用 我们可以在这里做我们需要进行的配置修改 和按钮创建
        createControl: function(n, cm) {
            //n为当前按钮的名字 当这个按钮是需要创建当前插件按钮的时候
            if (n === 'group') {
                //将刚才保留的信息获取
                var _set = this.settings;
                var _cmd = this.cmd;
                var _id = this.id;
                var _item = _set.group_set[_id]; //获取多组信息

                //创建一个可下拉的菜单按钮
                var _btn = cm.createSplitButton('aliGroup_' + _id, {
                    title: 'aliGroup.desc',
                    onclick: function() {
                        var _menu = cm.get('aliGroup_' + _id);
                        _menu.showMenu();
                    },                    
                    image: tinymce.baseURL + (_item.icon || '/plugins/aliGroup/img/group.gif')
                });

                //为按钮添加下拉菜单项
                _btn.onRenderMenu.add(function(c, m) {
                    //根据分组信息创建
							var _buttons = _item.buttons.split(',');
                    for (var i = 0, l = _buttons.length; i < l; i++) {
								if(_buttons[i] == 'pastetext'){
									m.add({
										//配置标题信息则需要考虑到语言和主题
										title: tinyMCE.i18n[_set.language + '.paste.paste_text_desc'],
										//图标类自己创建的话则需要注意格式
										icon: 'mceIcon mce_pastetext',
										//执行的命令 自己创建的话 也要注意格式
										cmd: 'mcePasteText'
									});
									continue;
								}
								if(_buttons[i] == 'pasteword'){
									m.add({
										//配置标题信息则需要考虑到语言和主题
										title: tinyMCE.i18n[_set.language + '.paste.paste_word_desc'],
										//图标类自己创建的话则需要注意格式
										icon: 'mceIcon mce_pasteword',
										//执行的命令 自己创建的话 也要注意格式
										cmd: 'mcePasteWord'
									});
									continue;
								}
								m.add({
									//配置标题信息则需要考虑到语言和主题
									title: tinyMCE.i18n[_set.language + '.' + _set.theme + '.' + _cmd[_buttons[i]][0]],
									//图标类自己创建的话则需要注意格式
									icon: 'mceIcon mce_' + _buttons[i],
									//执行的命令 自己创建的话 也要注意格式
									cmd: _cmd[_buttons[i]][1]
								});
                    }
                });
                //当前组配置完毕 则ID递增1 再遇到此插件的配置 则配置下一组
                this.id++
                //返回这个下拉按钮
                return _btn;
            }
            return null;
        },

        /**
        * Returns information about the plugin as a name/value array.
        * The current keys are longname, author, authorurl, infourl and version.
        * 插件信息
        * @return {Object} Name/value array containing information about the plugin.
        */
        getInfo: function() {
            return {
                longname: 'ToolGroup plugin',
                author: 'liubo',
                authorurl: 'http://www.likejs.com/',
                infourl: 'http://www.likejs.com/blog/',
                version: '1.0'
            };
        }
    });

    // Register plugin
    tinymce.PluginManager.add('aliGroup', tinymce.plugins.aliGroup);
})();