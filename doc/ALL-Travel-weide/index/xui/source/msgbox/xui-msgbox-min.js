xui.widgets.MsgBox=function(){return{zIndex:99,width:300,height:150,id:null,positionTop:false,alpha:0,_successHTML:function(e,d,c){var a=(c&&c.success)?c.success:"确定";var b=[];b.push('<div class="xui-msgbox">');b.push('<div class="xui-msgbox-in">');b.push('<p class="xui-msg">'+d+"</p>");b.push('<div class="xui-msgbox-submit">');b.push('<button class="xui-msgbox-btn" id="'+e+'_success" onclick="xui.widgets.MsgBox.close()"><span class="xui-msgbox-icon-confirm">'+a+"</span></button>");b.push("</div></div></div>");return b.join("")},_confirmHTML:function(f,c,b){var e=(b&&b.yes)?b.yes:"确定";var d=(b&&b.no)?b.no:"取消";var a=[];a.push('<div class="xui-msgbox">');a.push('<div class="xui-msgbox-in">');a.push('<p class="xui-msg">'+c+"</p>");a.push('<div class="xui-msgbox-submit">');a.push('<button class="xui-msgbox-btn" id="'+f+'_yes" onclick="xui.widgets.MsgBox.close()"><span class="xui-msgbox-icon-confirm">'+e+"</span></button>");a.push('<button class="xui-msgbox-btn" id="'+f+'_no" onclick="xui.widgets.MsgBox.close()"><span class="xui-msgbox-icon-cancel">'+d+"</span></button>");a.push("</div></div></div>");return a.join("")},_failHTML:function(e,d,c){var a=(c&&c.error)?c.error:"失败";var b=[];b.push('<div class="xui-msgbox">');b.push('<div class="xui-msgbox-in">');b.push('<p class="xui-msg">'+d+"</p>");b.push('<div class="xui-msgbox-submit">');b.push('<button class="xui-msgbox-btn" id="'+e+'_error" onclick="xui.widgets.MsgBox.close()"><span class="xui-msgbox-icon-cancel">'+a+"</span></button>");b.push("</div></div></div>");return b.join("")},_customHTML:function(d,c,b){var a=[];return a.join("")},_init:function(){var b=document.createElement("span");b.setAttribute("id",this.id);b.style.position="absolute";b.style.zIndex=this.zIndex;b.style.width=this.width+"px";b.style.height=this.height+"px";if(this.positionTop){var a=$Canvas();b.style.left=parseInt((a.clientWidth-this.width)/2)+a.scrollLeft+"px";if(parent){b.style.top=parent.document.documentElement.scrollTop+this.positionTop+"px"}else{b.style.top=document.documentElement.scrollTop+this.positionTop+"px"}}else{$Center(b)}document.body.appendChild(b);xui.util.Event.on(window,"resize",function(){$Center($(xui.widgets.MsgBox.id))},null,this)},_createBGLayer:function(){if($(this.id+"_bglayer")){return}var a=document.createElement("div");a.setAttribute("id",this.id+"_bglayer");a.style.position="absolute";a.style.left="0";a.style.top="0";a.style.background="#cccccc";a.style.zIndex=8;if(xui.env.ua.ie>0){a.style.filter="alpha(opacity="+this.alpha*10+")"}else{a.style.opacity=this.alpha/10}if(xui.env.ua.ie>0&&xui.env.ua.ie<7){a.innerHTML=$Shim()}a.style.width=$Canvas().scrollWidth+"px";a.style.height=($Canvas().clientHeight>$Canvas().scrollHeight?$Canvas().clientHeight:$Canvas().scrollHeight)+"px";a.style.display="";document.body.appendChild(a)},_create:function(a){this.id=$UUID("xui_msgbox_");this._createBGLayer();this._events={};this._onEvent(a);this._init()},_onEvent:function(b){if(b){for(var a in b){this._events[this.id+"_"+a]=b[a];xui.util.Event.on(this.id+"_"+a,"click",function(){var c=arguments[1];this._fire(this.id+"_"+c[0])},[a],this)}}},isOpenning:false,submit:function(e,c){if(this.isOpenning){return}this.isOpenning=true;this._create(c);var a=$(this.id+"_bglayer");if(a){a.style.display=""}var b=$(this.id);b.style.display="";b.innerHTML=$Shim()+$Iframe({id:this.id+"_ifr",zIndex:this.zIndex});var d=$(e);d.target=this.id+"_ifr";d.method="post";d.submit()},show:function(f,d,h){if(this.isOpenning){return}this.isOpenning=true;this._create(d);var a=$(this.id+"_bglayer");var e=null;var g="";if(f){if((f.type)){e=f.type}if((f.msg)){g=f.msg}}var c="";if(e=="success"){c=this._successHTML(this.id,g,h)}else{if(e=="confirm"){c=this._confirmHTML(this.id,g,h)}else{if(e=="fail"){c=this._failHTML(this.id,g,h)}else{if(e=="custom"){c=this._customHTML(this.id,g,h)}}}}if(a){a.style.display=""}var b=$(this.id);b.style.display="";b.innerHTML=c},_events:null,_fire:function(b,c){var a=this._events[b];if(a){try{a.call(this,c?c:null)}catch(d){alert("[xui-widgets-MsgBox]事件<"+b+">回调出错："+d)}}},close:function(){var a=$(this.id+"_bglayer");if(a){a.style.display="none"}$Visibles("select",true);var b=$(this.id);b.style.display="none";this.isOpenning=false},setContent:function(a){var b=xui.util.Dom.getElementsByClassName("xui-msg","p")[0];b.innerHTML=a}}}();