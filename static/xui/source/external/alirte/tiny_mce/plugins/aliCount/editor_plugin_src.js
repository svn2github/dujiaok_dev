/**
 *  @author     Aissa   
 *  @date       2009-09-06
 *  文本过滤
 */
(function() {
    tinymce.PluginManager.requireLangPack('aliCount');

    tinymce.create("tinymce.plugins.aliTextCount", {
        countre: null,
        cleanre: null,

        init: function(ed, url) {
            var t = this, row;
            t.id = ed.id + "-ali-foot-count";
            t.rid = ed.id + "-ali-foot-remaining";
            t.countre = ed.getParam('textcount_countregex', /./g);
            // 添加计数容器
            ed.onPostRender.add(function(ed, cm) {
                row = tinymce.DOM.get(ed.id + '_path_row');
                if (row)
                    tinymce.DOM.add(row.parentNode, 'div', { 'style': 'float: right' }, [ed.getLang('aliCount.words', 'Count: '), '<span id="', t.id, '">0</span> &nbsp;&nbsp;'/*, ed.getLang('aliCount.remaining', 'Remaining: '), '<span id="', t.rid, '">', ed.settings.textTotal, '</span>'*/].join('')); //剩余字数暂时不用
            });

            ed.onInit.add(function(ed) {
                ed.selection.onSetContent.add(function() {
                    t._count(ed);
                });

                t._count(ed);
            });

            ed.onSetContent.add(function(ed) {
                t._count(ed);
            });

            ed.onKeyUp.add(function(ed, e) {
                t._count(ed);
            });
        },

        createControl: function(n, cm) { },

        _count: function(ed) {
            var t = this, tc = 0;

            if (t.block)
                return;

            t.block = 1;

            setTimeout(function() {
                var tx = ed.getContent({ format: 'raw' });

                if (tx) {
                    tx = tx.replace(/<.[^>]*?>|[\t]|[\r]|[\n]/g, "").replace(/&nbsp;|&#160;|[\s]/gi, "").replace(/&lt;|&amp;|&gt;/gi, "2");
                    tx.replace(t.countre, function() { tc++; });
                }

                tinymce.DOM.setHTML(t.id, tc.toString());
                tinymce.DOM.setHTML(t.rid, ed.settings.textTotal - tc);

                setTimeout(function() { t.block = 0; }, 100);
            }, 100);
        },

        getInfo: function() {
            return {
                longname: 'aliTextCount',
                author: 'Aissa',
                authorurl: 'none',
                infourl: 'none',
                version: tinymce.majorVersion + "." + tinymce.minorVersion
            };
        }
    });

    tinymce.PluginManager.add('aliCount', tinymce.plugins.aliTextCount);
})();