xui.util.Key={backspace:8,tab:9,enter:13,shift:16,ctrl:17,alt:18,pause:19,caps:20,esc:27,pageup:33,pagedown:34,end:35,home:36,left:37,up:38,right:39,down:40,insert:45,"delete":46,f1:112,f2:113,f3:114,f4:115,f5:116,f6:117,f7:118,f8:119,f9:120,f10:121,f11:122,f12:123};xui.util.KeyBoard={initTabIndex:function(c){if(!c){return}for(var b=0,a=c.length;b<a;b++){var d=$(c[b]);if(!d){continue}d.setAttribute("tabIndex",b+1)}},initEnterTab:function(){xui.util.Event.on(document,"keyup",function(){var d=event.keyCode;if(d!=xui.util.Key.enter){return}var f=event.srcElement.tabIndex;if(!f||f<1){f=0}var c=document.getElementsByTagName("*");for(var b=0,a=c.length;b<a;b++){var e=c[b];if(e.tabIndex==(f+1)){e.focus();return}}})},_events0:{},_events1:{},onKey:function(b,a){if(b){this._events0["key_"+b]=a}},onKeyAndCtrl:function(b,a){if(b){this._events1["key_"+b]=a}},initHotKey:function(){xui.util.Event.on(document,"keydown",function(){var a=event.keyCode;if(event.ctrlKey&&(a==78||a==69)){event.returnValue=false}});xui.util.Event.on(document,"keyup",function(){var e=event.keyCode;var c=xui.util.KeyBoard;var d=c._events0["key_"+e];if(d){d.call()}var b=c._events1["key_"+e];if(b&&event.ctrlKey){b.call()}})}};