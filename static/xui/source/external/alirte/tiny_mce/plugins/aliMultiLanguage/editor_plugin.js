
(function(){tinymce.create('tinymce.plugins.aliMultiLanguage',{init:function(ed,url){var t=this,row,DOM=tinymce.DOM,Event=tinymce.dom.Event,times=0;ed.onPostRender.add(function(ed,cm){if(!!this.settings.custom_i18n){tinymce.each(this.settings.custom_i18n,function(o,p){if(!tinymce.is(p,'string')){tinymce.each(p,function(o,lc){tinymce.each(o,function(o,g){tinymce.each(o,function(o,k){if(g==='common')
tinymce.EditorManager.i18n[lc+'.'+k]=o;else
tinymce.EditorManager.i18n[lc+'.'+g+'.'+k]=o;});});});}
else{tinymce.each(o,function(o,k){tinymce.EditorManager.i18n[p+'.'+k]=o;});}});}});}});tinymce.PluginManager.add('aliMultiLanguage',tinymce.plugins.aliMultiLanguage);})();
