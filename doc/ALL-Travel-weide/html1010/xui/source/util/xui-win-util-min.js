xui.util.WinUtil={openWindow:function(b,c,k,j){var i=c?c:640,e=k?k:480;var d=0,g=0;if(j&&j.left){d=j.left}else{d=parseInt((window.screen.availWidth-i)/2)}if(j&&j.top){g=j.top}else{g=parseInt((window.screen.availHeight-e)/2)}var f=[];f.push("width="+i);f.push("height="+e);f.push("left="+d);f.push("top="+g);f.push("scrollbars="+((j&&j.scrollbars)?j.scrollbars:"yes"));f.push("resizable="+((j&&j.resizable)?j.resizable:"yes"));f.push("status="+((j&&j.status)?j.status:"no"));var a=(j&&j.name)?j.name:"_blank";var l=f.join(",");l=l+",toolbar=no,directories=no,menubar=no";return window.open(b,a,l)},openFullWindow:function(b,a){return this.openWindow(b,window.screen.availWidth,window.screen.availHeight,a)},selectOption:function(b,a){if(!b){return}if(!a||a<0){a=0}b.options[a].selected=true},loadIframe:function(c,a){var b=xui.lang.isString(c)?window.frames[c]:c;if(!b){return}b.location.href=a?a:b.location.href},getInputValue:function(e){var d=xui.lang.isString(e)?$(e):e;if(!d){return null}var c=d.tagName?d.tagName.toLowerCase():null;if(!c){return null}var a="";switch(c){case"input":if(d.type=="radio"){d=$N(d.name);for(var b=0;b<d.length;b++){if(d[b].checked){a=d[b].value}}}else{if(d.type=="checkbox"){d=$N(d.name);a=[];for(var b=0;b<d.length;b++){if(d[b].checked){a[a.length]=d[b].value}}}else{a=d.value}}break;case"select":if(d.type=="select-one"){if(d.selectedIndex!=-1){a=d.options[d.selectedIndex].value}}else{if(d.type=="select-multiple"){a=[];for(var b=0;b<d.length;b++){if(d[b].selected){a[a.length]=d[b].value}}}}break;case"textarea":a=d.value;break}return a},_onoffSwitch:function(b){var a=$(b).style.display;if(a=="none"){$(b).style.display=""}else{$(b).style.display="none"}},onoffSwitch:function(b){if(xui.lang.isArray(b)){for(var a=0;a<b.length;a++){this._onoffSwitch(b[a])}}else{this._onoffSwitch(b)}},isFormChanged:function(g){var c=$(g);for(var b=0;b<c.elements.length;b++){var e=c.elements[b];if(!e||!e.id){continue}var a=e.getAttribute("old-value");if(a==null||typeof a=="undefined"){continue}var d=xui.util.WinUtil.getInputValue(e.id)==a;if(!d){return true}}return false},isIE:function(){return xui.env.ua.ie>0?true:false},isIE6:function(){return xui.env.ua.ie==6?true:false},isIE7:function(){return xui.env.ua.ie==7?true:false},isIE8:function(){return xui.env.ua.ie==8?true:false},isFF:function(){return xui.env.ua.gecko>0?true:false}};