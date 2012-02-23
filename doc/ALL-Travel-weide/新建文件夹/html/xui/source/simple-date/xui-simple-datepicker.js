/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2010, Alibaba. All rights reserved.
 * @requires core/xui-core.js
 * 
 * @version 1.1
 * @author tangqiang
 * @date 2010-2-5
 *
 * @update time:2010-08-10 15:38:01
 */
 
/*
*class simpleDatePicker
*@param {string} id: thisElement's id
*@param {string} tmpl: this printout tmplate
*ep:'YYYY-MM-DD hh-mm-ss ms dd $zh'  this mean : year-month-date hour-minute-scond miilionscond weekday language
*@param {string|number|Object|array|Date} attr: a Initial value 
*object:{year:'2009',month:'2',date:'25',hour:'18',minute:'33',second:'54',ms:'158'}
*string:2009-2-25 18:33:54 158  or 2009 2 25 18 33 54 158   
*number:1005684555545
*array:[2009,2,25,18,33,54,158]
*Date :  var v = new Date();
*/
xui.widgets.simpleDatePicker = function(id,tmpl,attr,config){
	this.config  = config;
	this._disTime = new Array();
	this.tms = true;
	this.obj = {};
	if(id){
		this.id = 'xui-dp-'+id;
		this.user = $(id);
		
		/*
		* If there is no default template will setting default and  to parse it.
		*/
		if(tmpl){
			this.tmpl = tmpl;
		}else{
			this.tmpl = 'YYYY-MM-DD hh:mm:ss';
		}
		this.setTmpl();
		
		/*
		*Access to the user default parameters, and initialization
		*/
		if(attr){
			this.setAttr(attr);
		}else if($(id).value){
			this.setAttr($(id).value);
		}
		
		/*
		*get The final global object   type[Date] 
		*/
		this.initObj();
		
		/*
		* initialization htmlElements and node Event
		*/
		this.init();
		
		/*
		*Date object from the global data to generate the end-user interface
		*/
		this.getValue();
		
	}else{
		this.initObj();	
	}
}
xui.widgets.simpleDatePicker.prototype = {
	/*
	*You can define your HTML template 
	*/
	html:'<div class="xui-dp-ym">'
		+'<div class="xui-dp-month"><span id="#month"></span><a href="javascript:void(0)" id="#mbtn" class="xui-dp-btn-month"></a></div>'
		+'<div class="xui-dp-year"><span id="#year"></span><b class="xui-dp-btn"><a href="javascript:void(0)" class="xui-dp-btn-pre" id="#pre"></a><a href="javascript:void(0)" class="xui-dp-btn-next" id="#next"></a></b></div></div>'
		+'<div class="xui-dp-day" id="#day"></div>'
		+'<div class="xui-dp-hms"><div id="#tms"><a href="javascript:void(0)" id="#hh"></a><span>时</span><a href="javascript:void(0)" id="#mm"></a><span>分</span><a href="javascript:void(0)" id="#ss"></a><span>秒</span></div><a href="javascript:void(0)" id="#submit" class="xui-dp-sub">确定</a><a href="javascript:void(0)" id="#close" class="xui-dp-close">清除</a></div>',
	obj:{},
	setTmpl:function(){
		this.Tmpls = new Array();
		this.Tmpls.push(this.tmpl.replace(/[^Y+]/g,''));
		this.Tmpls.push(this.tmpl.replace(/[^M+]/g,''));
		this.Tmpls.push(this.tmpl.replace(/[^D+]/g,''));
		this.Tmpls.push(this.tmpl.replace(/[^h+]/g,''));
		this.Tmpls.push(this.tmpl.replace(/[^m+]/g,''));
		this.Tmpls.push(this.tmpl.replace(/[^s+]/g,''));
		var k=this.tmpl.indexOf('ms');
		if(k!=-1){
			this.Tmpls.push('ms');	
		}else{
			this.Tmpls.push('');	
		}
		this.Tmpls.push(this.tmpl.replace(/[^d+]/g,''));
		k = this.tmpl.indexOf('$');
		if(k!=-1){
			this.Tmpls.push(this.tmpl.substr(k+1));	
		}else{
			this.Tmpls.push('');	
		}
	},
	setOutInner:function(){
		var id = this.id.replace('xui-dp-','');
		var w = xui.util.Dom.getStyle(id,'width');
		try{w=='auto'?w='128':w=parseInt(w);}catch(e){w=128;}
		//this.user.style.visibility = 'hidden';
		this.user.style.display = 'none';
		var d = document.createElement('div');
		d.id = this.id+'-box';
		d.className = 'xui-dp-box';
		d.style.position='relative';
		d.style.width = w+'px';
		d.innerHTML = '<span id="'+this.id+'-call" class="xui-dp-call"></span><span class="xui-dp-value" id="'+this.id+'-value" style="width:'+(w-20)+'px"></span>';
		this.user.parentNode.insertBefore(d,this.user);
	},
	initObj:function(){
		if(!this.DATE){
			this.DATE = new Date();
		}
	},
	setInner:function(){
		var d = document.createElement('div');
		d.id = this.id;
		d.className = 'xui-dp';
		d.innerHTML = this.html.replace("#year",this.id+'-year').replace('#month',this.id+'-month').replace('#pre',this.id+'-pre').replace('#next',this.id+'-next').replace('#day',this.id+'-db').replace('#hh',this.id+'-hour').replace('#mm',this.id+'-minute').replace('#ss',this.id+'-second').replace('#mbtn',this.id+'-btn-m').replace('#close',this.id+'-close').replace('#submit',this.id+'-submit').replace('#tms',this.id+'-tms');
		document.body.appendChild(d);
		this.setInnerYear();
		this.setInnerMonth();
		this.setInnerDay();
		this.setInnerHMS();
	},
	setInnerYear:function(){
		this.obj._year = $(this.id+'-year');
		this.obj._year.innerHTML = this.getYear(this.DATE);
	},
	setInnerMonth:function(){
		this.obj._month = $(this.id+'-month');
		var str = '<div class="xui-dp-md" id="'+this.id+'-md">';
		for(var i=1; i<=12; i++){
			if(i==this.DATE.getMonth()+1){
				this.obj._month.innerHTML = i+'月';
			}
			str += '<a href="javascript:void(0)">'+i+'月</a>';
		}
		str += '</div>';
		$(this.id).innerHTML += str;
	},
	setInnerDay:function(){
		this.obj._day = $(this.id+'-db');
		var str = '<table><tr><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr>',
		day = this.DATE.getDate(),
		year = this.getYear(this.DATE),
		month = this.DATE.getMonth()+1,
		nextmonth = new Date(year,month),
		t = new Date(nextmonth.getTime()-1),
		premonth = new Date(year,month-1),
		wee = premonth.getDay(),
		premonth = new Date(premonth.getTime()-1),
		preDay = premonth.getDate(),
		maxday = t.getDate(),
		count = 0,
		num = 1,
		cnow = true,
		clname = 'xui-dp-a',nname='',
		nowmoth = 'pre';
		if(this.config && this.config.selectModel == 'week'){
		 	str += '<tr onmouseover="this.style.backgroundColor = \'#00b7e3\'" onmouseout="this.style.backgroundColor = \'transparent\'">';
		}else {
			str += '<tr>';
		}
		for(var i=0; i<42; i++){
			if(count==7){
				count=0;
				if(this.config && this.config.selectModel == 'week'){
				 str += '</tr><tr onmouseover="this.style.backgroundColor = \'#00b7e3\'" onmouseout="this.style.backgroundColor = \'transparent\'">';
				}
				else{
				 str += '</tr><tr>';
				}
			}
			if(num==day&&cnow){nname=' xui-dp-nowday';}else{nname='';}
			var tm = '<td><a href="javascript:void(0)" class="#class#">#d#</a></td>',className = 'xui-dp-noa',d='';
			
			if(i<7){
				if(i<wee){
					var f = preDay-(wee-i)+1;
					d = f;
				}else{
					nowmoth = 'now';
					className = 'xui-dp-a'+nname;
					d = num;
					num++;
				}
			}else{
				if(num>maxday){num=1;clname = 'xui-dp-noa';nname='';cnow=false;nowmoth = 'next';}
				className = clname+nname;
				d = num;
				num++;
			}



			var nowtmdate =  '';
			if(nowmoth=='pre'){
				nowtmdate = premonth;
			}else if(nowmoth=='next'){
				nowtmdate = nextmonth;
			}else{
				nowtmdate = t;
			}
			nowtmdate.setDate(d);


			if(this.isDisTime(nowtmdate)){
				tm = '<td><span class="#class#">#d#</span></td>';
			}





			str += tm.replace('#class#',className).replace('#d#',d);
			count++;
		}
		str +='</tr></table>';
		this.obj._day.innerHTML = str;
		this.initEventDd();
	},
	setInnerHMS:function(){
		this.obj._hour = $(this.id+'-hour');
		this.obj._minute = $(this.id+'-minute');
		this.obj._second = $(this.id+'-second');
		this.obj._hour.innerHTML = this.DATE.getHours();
		this.obj._minute.innerHTML = this.DATE.getMinutes();
		this.obj._second.innerHTML = this.DATE.getSeconds();
		var str1 = '<div class="xui-dp-dialog" id="'+this.id+'-h">',
			str2 = '<div class="xui-dp-dialog" id="'+this.id+'-m">',
			str3 = '<div class="xui-dp-dialog" id="'+this.id+'-s">';
		for(var i=0; i<60; i++){
			if(i<24){
				str1 +='<a href="javascript:void(0)">'+i+'</a>';	
			}
			str2 += '<a href="javascript:void(0)">'+i+'</a>';
			str3 += '<a href="javascript:void(0)">'+i+'</a>';
		}
		str1 += '</div>';
		str2 += '</div>';
		str3 += '</div>';
		$(this.id).innerHTML += str1+str2+str3;
	},
	clearDate:function(){
		var _f = this;
		$(_f.id+'-value').innerHTML = '';
		$(_f.id.replace('xui-dp-','')).value = '';
		$(_f.id).style.display = 'none';

	},
	init:function(){
		this.setOutInner();
		this.setInner();
		this.initEvent();
		var _f = this;
		xui.util.Event.on($(this.id+'-close'),'click',function(){
			_f.clearDate();
		});
		xui.util.Event.on($(this.id+'-submit'),'click',function(){
			_f.select();
			$(_f.id).style.display = 'none';
			});
		xui.util.Event.on(document,'click',function(e){
			if($(_f.id).style.display == 'none')return;
			var target = YAHOO.util.Event.getTarget(e),f=true; 
			while(target&&f){
				if(target.className&&target.className.toLowerCase().indexOf('xui-dp')!=-1){
					f=false;
				}
				for(var i=0; i<_f.documentExitId.length; i++){
					if($(_f.documentExitId[i])==target){
						f = false;
					}
				}
				target = target.parentNode;
			}
			if(f){
				$(_f.id).style.display = 'none';
			}
			});
	},
	documentExitId:[],
	setAttr:function(attr){	
		if(attr.constructor==Date){
			this.DATE = new Date(attr);
		}else if(attr.constructor == Array){
			this.setAttrByArray(attr);
		}else if(attr.constructor == Object){
			this.setAttrByObject(attr);	
		}else if(attr.constructor == String){
			this.setAttrByString(attr);	
		}else if(/^\d+$/.test(attr)){
			this.setAttrByNumber(attr);	
		}
	},
	close:function(){
		$(this.id).style.display = 'none';
	},
	initEvent:function(){
		this.initEventCall();
		this.initEventYd();
		this.initEventMd();
		this.initEventDd();
		this.initEventHmsd();
	},
	initEventCall:function(){
		var _f = this;
		xui.util.Event.on([$(this.id+'-call'),$(this.id+'-value')],'click',function(){_f.show();});
	},
	show:function(target){
		var x = xui.util.Dom.getX(this.id+'-box'),
				y = xui.util.Dom.getY(this.id+'-box'),
				h = $(this.id+'-box').offsetHeight;
			if(target&&target.nodeType){
				x = xui.util.Dom.getX(target);
				y = xui.util.Dom.getY(target);
				h = target.offsetHeight;
			}
			var w  = $Canvas().clientWidth;
			if(x+186>w){
				x = w-186;
			}
			$(this.id).style.left = x+'px';
			$(this.id).style.top = (y+h)+'px';
			$(this.id).style.display = 'block';
			//IE6 must display
			$(this.id+'-md').style.display = 'none';
	},
	initEventHmsd:function(){
		var _f = this;
		xui.util.Event.on($(this.id+'-hour'),'click',function(){$(_f.id+'-h').style.display = 'block';});
		xui.util.Event.on($(this.id+'-minute'),'click',function(){$(_f.id+'-m').style.display = 'block';});
		xui.util.Event.on($(this.id+'-second'),'click',function(){$(_f.id+'-s').style.display = 'block';});
		xui.util.Event.on($(this.id+'-hour'),'mouseout',function(){_f.obj.closeHhd=true;setTimeout(function(){_f.closeHhd();},2000);});
		xui.util.Event.on($(this.id+'-minute'),'mouseout',function(){_f.obj.closeMmd=true;setTimeout(function(){_f.closeMmd();},2000);});
		xui.util.Event.on($(this.id+'-second'),'mouseout',function(){_f.obj.closeSsd=true;setTimeout(function(){_f.closeSsd();},2000);});
		var hh = $(this.id+'-h').getElementsByTagName('a');
		var hm = $(this.id+'-m').getElementsByTagName('a');
		var hs = $(this.id+'-s').getElementsByTagName('a');
		xui.util.Event.on(hh,'click',function(){_f.setHours(this.innerHTML);});
		xui.util.Event.on(hm,'click',function(){_f.setMinutes(this.innerHTML);});
		xui.util.Event.on(hs,'click',function(){_f.setSeconds(this.innerHTML);});
		xui.util.Event.on(hh,'mouseout',function(){_f.obj.closeHhd=true;});
		xui.util.Event.on(hm,'mouseout',function(){_f.obj.closeMmd=true;});
		xui.util.Event.on(hs,'mouseout',function(){_f.obj.closeSsd=true;});
		xui.util.Event.on(hh,'mouseover',function(){_f.obj.closeHhd=false;});
		xui.util.Event.on(hm,'mouseover',function(){_f.obj.closeMmd=false;});
		xui.util.Event.on(hs,'mouseover',function(){_f.obj.closeSsd=false;});
	},
	initEventYd:function(){
		var _f = this;
		xui.util.Event.on($(this.id+'-pre'),'click',function(){_f.setYear(true);});
		xui.util.Event.on($(this.id+'-next'),'click',function(){_f.setYear(false);});
	},
	initEventMd:function(){
		var _f = this;
		var btn = $(this.id+'-btn-m');
		xui.util.Event.on(btn,'click',function(){$(_f.id+'-md').style.display = 'block';});
		xui.util.Event.on(btn,'mouseout',function(){_f.obj.closeMd = true;setTimeout(function(){_f.closeMd();},2000);});
		var a = $(this.id+'-md').getElementsByTagName('a');
		xui.util.Event.on(a,'click',function(){_f.setMonth(this.innerHTML.replace('月',''));	});
		xui.util.Event.on(a,'mouseover',function(){_f.obj.closeMd = false;});
		xui.util.Event.on(a,'mouseout',function(){_f.obj.closeMd = true;});
	},
	initEventDd:function(){
		var _f = this;
		var _da = $(this.id+'-db').getElementsByTagName('a');
		xui.util.Event.on(_da,'click',function(){
											   var cn = this.className;
											   var num = this.innerHTML.replace(/\s/g,'');
											   if(cn.indexOf('xui-dp-a')!=-1){
											   		_f.setDate(this.innerHTML);
											   }else{
												   var dm = _f.DATE.getMonth();
													if(num>20){
														_f.setMonth(dm,num);	
													}else{
														_f.setMonth(dm+2,num);		
													}
												}
												
												//add closeModel Event

												//if(_f.obj.closeModel&&_f.obj.closeModel=='true'){
													_f.select();
													$(_f.id).style.display = 'none';
												/*}else{
													_f.getValue();
												}*/

											   });
	},
	setAttrByArray:function(s){
		this.DATE = new Date("2000/0/1");
		if(s[0]){
			this.DATE.setFullYear(s[0]);
		}else{
			this.DATE.setFullYear(new Date().getFullYear());
		}
		if(s[1]){
			this.DATE.setMonth(parseInt(s[1],10)-1);
		}
		if(s[2]){
			this.DATE.setDate(s[2]);
		}
		if(s[3]){
			this.DATE.setHours(s[3]);
		}
		if(s[4]){
			this.DATE.setMinutes(s[4]);
		}
		if(s[5]){
			this.DATE.setSeconds(s[5]);
		}
	},
	setAttrByObject:function(s){
		this.DATE = new Date("2000/0/1");
		if(s['year']){
			this.DATE.setFullYear(s['year']);
		}
		if(s['month']){
			this.DATE.setMonth(s['month']-1);
		}
		if(s['date']){
			this.DATE.setDate(s['date']);
		}
		if(s['hour']){
			this.DATE.setHours(s['hour']);
		}
		if(s['minute']){
			this.DATE.setMinutes(s['minute']);
		}
		if(s['second']){
			this.DATE.setSeconds(s['second']);
		}
	},
	setAttrByString:function(s){
		var a = s.split(/[^\d]+/g);
		this.setAttrByArray(a);
	},
	setAttrByNumber:function(s){
		this.DATE = new Date(s);
	},
	closeSsd:function(){
		if(this.obj.closeSsd){
			$(this.id+'-s').style.display = 'none';
			this.obj.closeSsd = false;
		}else{
			var _f = this;
			setTimeout(function(){_f.closeSsd();},2000);
		}
	},
	closeMmd:function(){
		if(this.obj.closeMmd){
			$(this.id+'-m').style.display = 'none';
			this.obj.closeMmd = false;
		}else{
			var _f = this;
			setTimeout(function(){_f.closeMmd();},2000);
		}
	},
	closeHhd:function(){
		if(this.obj.closeHhd){
			$(this.id+'-h').style.display = 'none';
			this.obj.closeHhd = false;
		}else{
			var _f = this;
			setTimeout(function(){_f.closeHhd();},2000);
		}
	},
	closeMd:function(){
		if(this.obj.closeMd){
			$(this.id+'-md').style.display = 'none';
			this.obj.closeMd = false;
		}else{
			var _f = this;
			setTimeout(function(){_f.closeMd();},2000);
		}
	},
	setSeconds:function(s){
		this.DATE.setSeconds(s);
		$(this.id+'-second').innerHTML = s;
		$(this.id+'-s').style.display = 'none';
	},
	setMinutes:function(m){
		this.DATE.setMinutes(m);
		$(this.id+'-minute').innerHTML = m;
		$(this.id+'-m').style.display = 'none';
	},
	setHours:function(h){
		this.DATE.setHours(h);
		$(this.id+'-hour').innerHTML = h;
		$(this.id+'-h').style.display = 'none';
	},
	setYear:function(t){
		var y = this.getYear(this.DATE);
		if(t){y += 1;}else{y -= 1;}
		this.DATE.setFullYear(y);
		$(this.id+'-year').innerHTML = y;
		this.setInnerDay();
	},
	setDate:function(num){
		this.DATE.setDate(num);
		this.setInnerDay();
	},
	setMonth:function(num,d){
		if(num-1<0){
			this.setYear(false);
			num = 12-num;
		}else if(num>12){
			this.setYear(true);	
			num = num-12;
		}
		if(!d){
			d = this.DATE.getDate();
		}
		var maxs = new Date(new Date(this.getYear(this.DATE),num).getTime()-1).getDate();
		if(d>maxs){d=maxs;}
		this.DATE.setMonth(num-1,maxs);
		this.setDate(d);
		$(this.id+'-month').innerHTML = num+'月';
		$(this.id+'-md').style.display = 'none';
	},
	getValue:function(){
		var str = this._getStr();
		if(this.isDisTime()){
			return;
		} 
		$(this.id+'-value').innerHTML = str;
		$(this.id.replace('xui-dp-','')).value = str;
	},
	_getStr:function(){
		var str = this.tmpl;
		var t = this.getYear(this.DATE).toString();
		t = t.substr(4-this.Tmpls[0].toString().length);
		str = str.replace(this.Tmpls[0],t);
		t = this.DATE.getMonth()+1;
		str = this.getTm(str,t,1);
		t = this.DATE.getDate();
		str = this.getTm(str,t,2);
		t = this.DATE.getHours();
		str = this.getTm(str,t,3);
		t = this.DATE.getMinutes();
		str = this.getTm(str,t,4);
		t = this.DATE.getSeconds();
		str = this.getTm(str,t,5);
		t = this.DATE.getMilliseconds();
		str = this.getTm(str,t,6);
		return str;
	},
	select:function(){
		this.getValue();
		var str = this._getStr();
		if(this.config && this.config.selectModel == 'week'){
			str =  this.getWeek(xui.util.DateUtil.toDate(str));
		}
		if(this.obj.selectBefore){
			this.obj.selectBefore.call(this,str);
		}
	},
	getWeek:function(date){
		var d = new Date(date).getDay(),start,end;
	    start = new Date(new Date(date).setDate(new Date(date).getDate()-d));
	    end = new Date(new Date(date).setDate(new Date(date).getDate()+(6-d)));
	   return [start,end];   

	},
	getTm:function(str,t,num){
		if(this.Tmpls[num].toString().length>0){
			if(this.Tmpls[num].toString().length>1){
				if(t<10){
					t = '0'+t;	
				}
			}
			str = str.replace(this.Tmpls[num],t);
		}
		return str;
	},
	getYear:function(e){
		return e.getFullYear();
	},
	setModel:function(a){
		
		var ud = false;

		if(a.tms){
			if(a.tms=='false'){
				this.tms = false;
				$(this.id+'-tms').style.display = 'none';
			}else{
				$(this.id+'-tms').style.display = 'block';
				this.tms = true;
			}
		}
		if(a.closeModel){
			if(a.closeModel=='true'){
				this.obj.closeModel='true';
				//this.setInnerDay();
				$(this.id+'-close').style.display = 'none';
				$(this.id+'-submit').style.display = 'none';
				ud = true;
			}
		}
		if(a.initvalue){
			$(this.id+'-value').innerHTML = a.initvalue;
			$(this.id.replace('xui-dp-','')).value = a.initvalue;
		}
		if(a.clear){
			$(this.id+'-value').innerHTML = '';
			$(this.id.replace('xui-dp-','')).value = '';
		}
		if(a.distime){
			if(a.distime.length && a.distime.length>0){
				for(var i=0; i<a.distime.length; i++){
					this._disTime.push(this.format(a.distime[i]));
				}
				ud = true;
			}	
		}
		if(a.selectBefore){
			this.obj.selectBefore = a.selectBefore;
		}

		if(ud){
			this.setInnerDay();
		}

		if(a.inconver){
			if(a.inconver=='true'){
				$(this.id+'-box').style.display = 'none';
				var proid = this.id.replace('xui-dp-','');
				$(proid).style.display = 'block';
				var _f = this;
				xui.util.Event.on(proid,'click',function(){
					if($(_f.id).style.display != 'block'){
						_f.show($(proid));
					}else{
						$(_f.id).style.display = 'none';
					}
				});
				this.documentExitId.push(proid);
			}
		}
	},
	isDisTime:function(t){
		if(!t){t=this.DATE;}
		for(var j=0; j<this._disTime.length; j+=2){
			if(j+1<this._disTime.length){
				var t1 = t2 = t;
				if(!this.tms){
					t1.setMilliseconds(999);
					t1.setSeconds(59);
					t1.setMinutes(59);
					t1.setHours(23);
					t2.setMilliseconds(0);
					t2.setSeconds(0);
					t2.setMinutes(0);
					t2.setHours(0);
				}


				if(this.dateMath(t2,this._disTime[j]) && this.dateMath(this._disTime[j+1],t1)){
					return true;
				}

			}
		}
		return false;
	},
	dateMath:function(d1,d2){
		return d1.getTime() > d2.getTime();
	},
	format:function(s){
		var t =  new xui.widgets.simpleDatePicker();
		t.setAttr(s);
		return t.DATE;
	},
	reset:function(s){
		this.setAttr(s);
		this.setInnerYear();
		this.setInnerMonth();
		this.setInnerDay();
		this.setInnerHMS();
		this.initEvent();
		this.getValue();
	}
}
