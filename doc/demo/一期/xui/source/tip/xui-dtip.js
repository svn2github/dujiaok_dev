/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2008-2010, Alibaba-ITU UED. All rights reserved.
 * @import xui.*
 
 * 
 * @version 1.0
 * @author tangqiang
 * @date 2010-07-15
 *
 * Update
 * Time: 2010-08-18 17:13:38
 * by: tangqiang
 */

/**
 * dTip：悬停式提示框
 * @namespace xui.widgets
 * @singleton Dtip
 */
 xui.widgets.Dtip = function(arrt,id){
	this._config = {closeBar:true};
	if(arrt&&arrt.constructor == String){
		this.show(arrt,id);
	}else{
		this.setArrt(arrt);
		this.show();
	}
 }
 xui.widgets.Dtip.prototype = {
	 
	/*
	* @Method setArrt:初始化设置参数
	* @param o[Object]:设置参数的对象
	*/
	setArrt:function(o){
		for(var k in o){
			if(k=='id'){
				this["_config"]["pro"] = $(o[k]);
			}
			this["_config"][k]=o[k];
		}
	},
	/*
	* @Method show:Dtip显示函数
	* @param msg[String]:显示的信息 可省略
	* @param id[String]:显示的信息所在位置的节点 可省略
	*/
	show:function(msg,id){
		if(msg){this.setArrt({'msg':msg})}
		if(id){
			if(id.constructor == String){
				this.setArrt({'id':id});
			}
			if(id.nodeType){
				this.setArrt({'pro':id});
			}

		}
		if(!this.nodid){
			this.nodid = $UUID('dtip-'); 
			var d = document.createElement('div');
			d.id = this.nodid;
			d.className = 'xui-dtip';
			d.style.display = 'none';
			var ds = document.createElement('div');
			ds.style.position = 'absolute';
			ds.style.width = '100%';
			ds.style.height = '0';
			ds.style.top = '0';
			ds.style.left = '0';
			document.body.appendChild(ds);
			ds.appendChild(d);
		}
		$(this.nodid).innerHTML = '<div class="xui-dtip-con">'+this._config["msg"]+'</div>'//+'<a href="javascript:;" class="xui-dtip-closebtn"></a><span class="xui-dtip-arr1"></span>';
		$(this.nodid).style.display = 'block';
		
		if(this._config.closeBar){
			this._getCloseinner();
		}
		
		this._getXY();
		try{(this._config["before"])();}catch(e){}
		if(this._config["timeout"]){
			var $f = this;
			setTimeout(function(){$f.hide();},$f._config["timeout"]);
		}
	},
	/*
	* @Method _getXY:判断Dtip显示的位置 
	*/
	_getXY:function(){
		var xy=[0,0],pwh=[0,0],wh=[0,0],className = 'xui-dtip-arr1';
		if(this._config["pro"]){
			xy = xui.util.Dom.getXY(this._config["pro"]);
			xy[0] = isNaN(xy[0])?0:xy[0];
			xy[1] = isNaN(xy[1])?0:xy[1];
			pwh = [parseInt(this._config["pro"].offsetWidth),parseInt(this._config["pro"].offsetHeight)];
		}
		if(this._config["width"]){
			var w =  parseInt(this._config["width"]);
			if(!isNaN(w)){
				$(this.nodid).style.width = w+'px';
			}
		}
		if(this._config["height"]){
			var h =  parseInt(this._config["height"]);
			if(!isNaN(h)){
				$(this.nodid).style.height = h+'px';
			}
		}
		wh = [parseInt($(this.nodid).offsetWidth),parseInt($(this.nodid).offsetHeight)];

		if(this._config["classname"]){
			if(this._config["classname"]=='topright'||this._config["classname"]==2){
				xy[0] = xy[0]+pwh[0]-wh[0];
				xy[1] = xy[1]-wh[1]-8;
				className = 'xui-dtip-arr2';
			}
			if(this._config["classname"]=='righttop'||this._config["classname"]==3){
				xy[0] = xy[0]+pwh[0]+13;
				xy[1] = xy[1]-wh[1]+pwh[1];
				className = 'xui-dtip-arr3';
			}
			if(this._config["classname"]=='rightbottom'||this._config["classname"]==4){
				xy[0] = xy[0]+pwh[0]+13;
				className = 'xui-dtip-arr4';
			}
			if(this._config["classname"]=='bottomright'||this._config["classname"]==5){
				xy[1] = xy[1]+pwh[1]+8;
				className = 'xui-dtip-arr5';
			}
			if(this._config["class"]=='bottomleft'||this._config["classname"]==6){
				xy[0] = xy[0]+pwh[0]-wh[0];
				xy[1] = xy[1]+pwh[1]+8;
				className = 'xui-dtip-arr6';
			}
			if(this._config["classname"]=='leftbottom'||this._config["classname"]==7){
				xy[0] = xy[0]-wh[0]-13;
				className = 'xui-dtip-arr7';
			}
			if(this._config["classname"]=='lefttop'||this._config["classname"]==8){
				xxy[0] = xy[0]-wh[0]-13;
				xy[1] = xy[1]-wh[1]+pwh[1];
				className = 'xui-dtip-arr8';
			}

		}else{
			xy[1] = xy[1]-wh[1]-8;
		}
		if(this._config["left"]){
			xy[0] =  parseInt(this._config["left"]);
			xy[0] = isNaN(xy[0])?0:xy[0];
		}
		if(this._config["top"]){
			xy[1] =  parseInt(this._config["top"]);
			xy[1] = isNaN(xy[1])?0:xy[1];
		}
		if(this._config["releft"]){
			xy[0] =  xy[0]+parseInt(this._config["releft"]);
			xy[0] = isNaN(xy[0])?0:xy[0];
		}
		if(this._config["retop"]){
			xy[1] =  xy[1]+parseInt(this._config["retop"]);
			xy[1] = isNaN(xy[1])?0:xy[1];
		}
		xy[0] = xy[0]<0?0:xy[0];
		xy[1] = xy[1]<0?0:xy[1];
		$(this.nodid).style.top = xy[1]+'px';
		$(this.nodid).style.left = xy[0]+'px';
		var d = document.createElement('span');
		d.className = className;
		$(this.nodid).appendChild(d);
	},
	/*
	* @Method _getCloseinner:产生关闭按钮 绑定关闭事件     
	*/
	_getCloseinner:function(){
		 var a = document.createElement('a');
		 a.id = this.nodid+'-closebtn';
		 a.className = 'xui-dtip-closebtn';
		 $(this.nodid).appendChild(a);
		 var $t = this;
		 xui.util.Event.on(a,'click',function(){
			  $t.close();
		 });
	},
	hide:function(){
		 $(this.nodid).style.display = 'none';
	},
	close:function(){
		this.hide();
		try{
			(this._config["close"])();
		}catch(e){}
	}
 }

