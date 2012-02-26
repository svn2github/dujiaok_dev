/**
 *  @author     Lucars   
 *  @date       2009-09-17
 *  添加Tracelog
 */
(function() {
	//tinymce.PluginManager.requireLangPack('aliTracelog');

    tinymce.create('tinymce.plugins.aliTracelog', {
        init: function(ed, url) {
            var t = this, row, DOM = tinymce.DOM, Event = tinymce.dom.Event, times = 0;
			ed.onPostRender.add(function(ed, cm){
				if (document.images) {
					var _tlog = escape(this.settings.tracelog) + '_init';
					setTimeout(function(){
						var _random = (new Date).getTime();
						(new Image).src = 'http://tracelog.www.alibaba.com/null.gif?fresh=' + _random + '&tracelog=' + _tlog;
					}, 200);
				}
				return true;
			});
		}
    });

    tinymce.PluginManager.add('aliTracelog', tinymce.plugins.aliTracelog);
})();