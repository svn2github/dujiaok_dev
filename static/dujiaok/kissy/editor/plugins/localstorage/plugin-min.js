KISSY.Editor.add("localstorage",function(){var d=KISSY,b=d.Editor;b.localStorage=null;if(b.storeReady)d.log("localstorage attach more","warn");else{b.storeReady=function(a){b.on("storeReady",a)};b.on("storeReady",function(){b.storeReady=function(a){a()};b.detach("storeReady")});if(!d.UA.ie&&window.localStorage){b.localStorage=window.localStorage;b.fire("storeReady")}else{var f=b.Utils.debugUrl("localstorage/swfstore.swf?t="+ +new Date),c=new b.FlashBridge({movie:f,flashVars:{useCompression:true},
methods:["setItem","removeItem","getItem","setMinDiskSpace","getValueOf"]});d.use("overlay",function(){c.swf.height=138;var a=new d.Overlay({headerContent:"\u8bf7\u70b9\u5141\u8bb8",width:"0px",elStyle:{overflow:"hidden"},content:"<h1 style='border:1px solid black;border-bottom:none;background:white;text-align:center;'>\u8bf7\u70b9\u51fb\u5141\u8bb8</h1>",zIndex:b.baseZIndex(b.zIndexManager.STORE_FLASH_SHOW)});a.render();a.get("contentEl").append(c.swf);a.center();a.show();c.on("pending",function(){a.set("width",215);a.center();a.show();setTimeout(function(){c.retrySave()},
1E3)});c.on("save",function(){a.set("width",0)})});var g=c.setItem;d.mix(c,{_ke:1,getItem:function(a){return this.getValueOf(a)},retrySave:function(){this.setItem(this.lastSave.k,this.lastSave.v)},setItem:function(a,e){this.lastSave={k:a,v:e};g.call(this,a,e)}});c.on("contentReady",function(){b.localStorage=c;b.fire("storeReady")})}}},{attach:false,requires:["flashutils","flashbridge"]});