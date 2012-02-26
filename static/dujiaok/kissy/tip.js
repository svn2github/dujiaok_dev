/*
 编辑器区域提示插件
 @author 龙啸,承玉<yiminghe@gmail.com>
 */
(function() {
    var S = KISSY,DOM = S.DOM,Node = S.Node,Event = S.Event;

    S.namespace('EditorPlugins.Tip');
    function EditorDecorate(editor, msg) {
        var self = this;
        self.editor = editor;
        self.msg = msg;
        self.shade = null;
        self.init();
    }

    var currentDecorate = null;
    var trimHTML = function(str) {
        return str.replace(/<[^>]+>/g, "");
    };
    EditorDecorate.prototype = {
        fix:function() {
            var self = this,shade = self.shade;
            var xy = DOM.offset(DOM.parent(self.editor.textarea[0], '.ke-textarea-wrap'));
            shade.css({
                left:xy.left + 10,
                top:xy.top + 10
            });
        },
        response:function() {
            var self = this;
            if (self.editor.getMode() == KISSY.Editor.WYSIWYG_MODE && trimHTML(self.editor.getData()) == "") {
                self.shade.show();
            }
        },
        init:function() {

            var self = this,
                editor = self.editor;

            function fixCall() {
                self.fix();
                change();
            }

            self.shade = new Node(self.msg).prependTo(document.body).on("click",
                function() {
                    editor.focus();
                }).css({
                    position:'absolute',
                    zIndex:999999,
                    left:-9999,
                    top:-9999
                });

            var shade = self.shade;

            editor.ready(fixCall);

            //偶尔会初始化对不齐
            setTimeout(fixCall, 1000);

            Event.on(window, 'resize', fixCall);

            editor.on("maximizeWindow restoreWindow wysiwygmode", fixCall);


            editor.on('focus sourcemode', function() {
                if (currentDecorate) {
                    currentDecorate.response();
                }
                currentDecorate = self;
                shade.hide();
            });


            editor.on('blur', function() {
                change();
            });


            var change = function() {
                if (editor.getMode() == KISSY.Editor.WYSIWYG_MODE && trimHTML(editor.getData()) == "") {
                    shade.show();
                } else {
                    shade.hide();
                }
            };
        }
    };
    //参数：消息，编辑器editor对象
    KISSY.EditorPlugins.Tip.bind = function(msg, editor) {
        return new EditorDecorate(editor, msg);
    };
})();