		
/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2010, alibaba.com All rights reserved.
 * @version 1.3
 * @author shengding
 * @date 2010-2-5
 * @requires core/xui-core.js
 * @requires util/util.js
 * @resource/css/default/simple-grid/simple-grid.css
 */
 
 
xui.widgets.SimpleGridColModel = function(attrs){
	this.defaults = attrs.defaults;
	this.columns = attrs.columns;
	for(key in this.columns){
		if(!this.columns[key].width)
			this.columns[key].width = this.defaults.width;
		if( typeof this.columns[key].sortable == "undefined" || this.columns[key].sortable == null )
			this.columns[key].sortable = this.defaults.sortable;
	}
}
xui.widgets.SimpleGridColModel.prototype = {
	push:function(o){
		this.columns.push(o);
	},
	get:function(){
		return this.columns;
	},
	_getSchemaBykey:function(keyId){
		var o = [];
		var i = 0;
		while(this.columns[i]){
			o[i] = this.columns[i][keyId];
			i++;
		}
		/*for(i=0;i<this.columns.length; i++){
			o[i] = this.columns[i][keyId];
		} */
		return o;
	},
	/**
	*  return {Array} [sex,age,birthday,name]
	*/
	getIdSchema:function(){
		return this._getSchemaBykey('id');
	},
	/**
	*  return {Object} {sex:{id:'sex',header: '性别', dataIndex: 'sex',renderer:onRender},age:{id: 'age', header: '年龄', dataIndex: 'age'}}
	*/
	getIdColModelMap:function(){
		var o = {};
		for(key in this.columns){
			 o[this.columns[key].id] = this.columns[key];
		}
		return o;
	},
	/**
	*  return {Array} [sex,age,birthday,name]
	*/
	getDataIndexSchema:function(){
		return this._getSchemaBykey('dataIndex');
	}
}

xui.widgets.SimpleGridSelectionModel = function(){
	
}
xui.widgets.SimpleGridSelectionModel.prototype= {
	
}


/**
 * grid 
 * @class SimpleGrid
 * @namespace xui.widgets
 * @constructor
 * @param {String} id  grid的容器Id
 * @param {Object} attrs  配置属性 eg：{hasPagebar:true,limit:10}
 */

xui.widgets.SimpleGrid = function(id,attrs) {
	this.id = id;
	this.attributes = attrs;
	
	/**
	*@field {Number} width  宽度
	*/
	this.width =( this.attributes && this.attributes.width)?this.attributes.width:'100%';
	/**
	*@field {Number} height  高度,默认高度为随着列表的条数自动拉升
	*/
	this.height =( this.attributes && this.attributes.height)?this.attributes.height:false;
	/**
	*@field {Number} limit  分页数 默认:10
	*/	
	this.limit = ( this.attributes && this.attributes.limit)?this.attributes.limit:10;
		/**
	*@field {Number} toPageNum  开始显示第几页，默认是第一页 默认:10
	*/	
	this.toPageNum = ( this.attributes && this.attributes.toPageNum)?this.attributes.toPageNum:1;
	/**
	*@field {Boolean} checkbox  是否有checkbox ,默认:false
	*/
	this.checkbox = ( this.attributes && this.attributes.checkbox)?this.attributes.checkbox:false;
	/**
	*@field {Boolean} hasPages  是否有总共多少页 ,默认:false
	*/
	this.hasPages = ( this.attributes && this.attributes.hasPages)?this.attributes.hasPages:false;
	/**
	*@field {Boolean} hasPagebar  是否有翻页条 ,默认:true
	*/
	this.hasPagebar =( this.attributes && !(typeof this.attributes.hasPagebar == "undefined"|| this.attributes.hasPagebar ==null) && !this.attributes.hasPagebar)?this.attributes.hasPagebar:true;
	/**
	*@field {Boolean} isRowAuto  是否根据数据的数目显示多少行 ,默认:true
	*/
	this.isRowAuto =( this.attributes && !(typeof this.attributes.isRowAuto == "undefined"|| this.attributes.isRowAuto ==null) && !this.attributes.isRowAuto)?this.attributes.isRowAuto:true;
	/**
	*@field {Boolean} hasTips  是否有tips ,默认:true
	*/
	//this.hasTips =( this.attributes && !(typeof this.attributes.hasTips == "undefined"|| this.attributes.hasTips ==null) && !this.attributes.hasTips)?this.attributes.hasTips:true;
	
	/**
	*@field {String} defaultSort  默认排序 ,默认:false
	*/
	this.defaultSort =( this.attributes && this.attributes.defaultSort)?this.attributes.defaultSort:'';
	/**
	*@field {String} dir  排序顺序 ,默认:desc,asc
	*/
	this.dir =( this.attributes && this.attributes.dir)?this.attributes.dir:'desc';
	/**
	*@field {Boolean} 	  是否可以多选 ,默认:false
	*/
	this.multiSelect = ( this.attributes && this.attributes.multiSelect)?this.attributes.multiSelect:false;

	this.store = null;
	this.schema = null;
	this.colModel=null;
	
	/**
	*@field {Number} start 往后台请求数据时参数，用于翻页  ,默认0
	*/
	this.start = 0 ;

	/**
	*@field {Number} currentPage 当前页，默认第一页
	*/
	this.currentPage= 1;
	/**
	*@field {Number} totalCount  全部数据总数
	*/
	this.totalCount = 0;
	
	this._events = {};
	this.selectDatas = {};
}
xui.widgets.SimpleGrid.prototype = {
	/**
	 * 初始化Grid
	 * 
	 * @param {String} sUrl 请求地址
	 * @param {Object} oColModel 列模型 ，如：[	{
							header: '邮件状态',
							width: 80,
							dataIndex:'type',
							sortable: true
						},{
							header:'customerAddress',
							dataIndex: 'customerAddress',
							width: 68,
							sortable: true
							
						}]
	 * @param {Object} oSchema 数据模型 ，如：['type','customerAddress']
	 */
	init:function(sUrl,oColModel,oSchema){
		var o = this;
		this.url = sUrl;
		this.schema = oSchema;
		if(this.checkbox){
			oColModel.push({id: 'checkbox',renderer:o._renderCheckBox});
			this.checkAll = xui.util.Dom.getElementsByClassName('xui-simpleGrid-hd-checkbox','input',this.id)[0];
			
			xui.util.Event.on(this.checkAll,'click',function(){
								if(o.checkAll.checked){
									o.selectAll();
								}else{
									o.cleanRow();
								}
																																	 })
		}
		//列模型
		this.colModel = oColModel.get();
		//列模型中的id集合
		this.idSchema = oColModel.getIdSchema();
		//列模型中的dataIndex集合
		this.dataIndexSchema = oColModel.getDataIndexSchema();
		
		this.colModelMap = oColModel.getIdColModelMap();
		
		//全部行的dom对象数组
		this.rowDoms = xui.util.Dom.getElementsByClassName('xui-simpleGrid-row','div',this.id);
		this._addRowDomsEvents();
		
		this.getHdDoms();
		this._addHdDomsEvents();
		this._renderHead();
		
		this.firstBtnDom = xui.util.Dom.getElementsByClassName('xui-pagebar-btn-first','button',this.id)[0];
		this.prevBtnDom = xui.util.Dom.getElementsByClassName('xui-pagebar-btn-prev','button',this.id)[0];
		this.lastBtnDom = xui.util.Dom.getElementsByClassName('xui-pagebar-btn-last','button',this.id)[0];
		this.nextBtnDom = xui.util.Dom.getElementsByClassName('xui-pagebar-btn-next','button',this.id)[0];

		this.numberBtnDom = xui.util.Dom.getElementsByClassName('xui-pagebar-btn-number','input',this.id)[0];
		this.numberStartDom = xui.util.Dom.getElementsByClassName('xui-pagebar-start','span',this.id)[0];
		this.numberEndDom = xui.util.Dom.getElementsByClassName('xui-pagebar-end','span',this.id)[0];
		this.numberTotalDom = xui.util.Dom.getElementsByClassName('xui-pagebar-total','span',this.id)[0];
		this.numberGoDom = xui.util.Dom.getElementsByClassName('xui-pagebar-btn-go','button',this.id)[0];
		
		this.numberTotalPages = xui.util.Dom.getElementsByClassName('xui-pagebar-pages','span',this.id)[0];
		
		if(this.hasPagebar)
			this._initPageBar();
		this.toPage(this.toPageNum);
		
		this._initEnvents('rowmouseover');
		this._initEnvents('rowmouseout');
		this._initEnvents('rowclick');
		this._initEnvents('rowdblclick');
		this._initEnvents('sort');
		this._initEnvents('rowselect');
		this._initEnvents('unrowselect');
		this._initEnvents('dsload');
		this._initEnvents('dserror');
		this._initEnvents('ajaxerror');
		this._initEnvents('checkboxclick');
	},
	_renderCheckBox:function(v,os,rowIndex,g){
		var checked = v?'checked':'';
		return '<input id="xui-simpleGrid-'+g.id+'-'+rowIndex+'" name="" class="xui-simpleGrid-row-checkbox" type="checkbox" value="" '+checked+' />';
	},
	/**
	* 	 @return {Object} {sex:	HTMLElement , age:HTMLElement} 
	*/
	getHdDoms:function(){
		if(! this.hdDoms){
			this.hdDoms = {};
			var hdBodyDom = xui.util.Dom.getElementsByClassName('xui-simpleGrid-header','div',this.id)[0];
			for(key in this.idSchema){
				this.hdDoms[this.idSchema[key]] = xui.util.Dom.getElementsByClassName('xui-simpleGrid-hd-'+this.idSchema[key],'td',hdBodyDom)[0];
			}
		}
		return this.hdDoms;
	},
	/**渲染头部
	*
	*
	*/
	_renderHead:function(){
		for(key in this.hdDoms){
			if(key != 'checkbox'&&key != 'undefined'){
				if(typeof this.hdDoms[key] != "undefined"){
					var hdDomDiv = xui.util.Dom.getElementsByClassName('xui-simpleGrid-hd-inner','div',this.hdDoms[key])[0];
					var hdInnerHTML = this.colModelMap[key].header ;
					hdDomDiv.innerHTML =hdInnerHTML +'<button  class="xui-simpleGrid-sort" ></button>';
				}
			}
		}
	
	},
	on:function(evemtName,func){
		var o = this;
		this._events[evemtName].subscribe(func, o);
	},	
	_initEnvents:function(evemtName) {
		var o = this;
		this._events[evemtName] = new xui.util.CustomEvent(evemtName, o);
	},
	_fire:function(evemtName,arg){
		this._events[evemtName].fire(arg);
	},
	//加默认头部事件
	_addHdDomsEvents:function(){
		var o =this;
		for(key in this.colModelMap){
			if(this.colModelMap[key].sortable  == true){
				
				xui.util.Dom.addClass(this.hdDoms[key],'xui-simpleGrid-sort-default');
				xui.util.Event.on(this.hdDoms[key] ,'click',function(){
								var hdId = 	arguments[1];	
								o.sort(hdId);
																	  },key);
			}
		}
	},
	_addCheckBoxEvent:function(){
		var o =this;
		for(key in  this.rowDoms){
			if($('xui-simpleGrid-'+this.id+'-'+key))
				xui.util.Event.on([$('xui-simpleGrid-'+this.id+'-'+key).parentNode,$('xui-simpleGrid-'+this.id+'-'+key).parentNode.parentNode],'click',function(){arguments[0].cancelBubble = true;})
			xui.util.Event.on($('xui-simpleGrid-'+this.id+'-'+key),'click',function(){
													//取消冒泡，防止触发click事件
													arguments[0].cancelBubble = true;
													
													var rowIndex = arguments[1];	
													o._fire('checkboxclick',o.rowDatas[rowIndex]);
													if($('xui-simpleGrid-'+o.id+'-'+rowIndex).checked){
														o.selectRowByIndex(rowIndex);
													}else{
														o.unselectRowByIndex(rowIndex);
													}
																		},key);
		}
	},

	//加默认行事件
	_addRowDomsEvents:function(){
		var o =this;
		for(key in  this.rowDoms){
			xui.util.Event.on(this.rowDoms[key] ,'click',function(){
											var rowIndex = arguments[1];
											if(o._isInDatasRow(rowIndex)){
												if(!o.multiSelect){
													o.cleanRow();
												}
												//多选模式开启的时候，单击先选中，再单击不选中
												if(o.multiSelect){
													if($('xui-simpleGrid-'+o.id+'-'+rowIndex).checked){
														o.unselectRowByIndex(rowIndex);
													}else{
														
														o.selectRowByIndex(rowIndex);
													}
												}else{
													o.selectRowByIndex(rowIndex);
												}
												if(o.rowDatas)
													o._fire('rowclick',o.rowDatas[rowIndex]);
											}

																	  },key);
			xui.util.Event.on(this.rowDoms[key] ,'dblclick',function(){
											var rowIndex = arguments[1];
											if(o._isInDatasRow(rowIndex)){
												if(o.rowDatas)
													o._fire('rowdblclick',o.rowDatas[rowIndex]);
											}

																	  },key);
			xui.util.Event.on(this.rowDoms[key] ,'mouseover',function(){
											var rowIndex = arguments[1];
											if(o._isInDatasRow(rowIndex)){
												o.hoverRowByIndex(rowIndex);
												if(o.rowDatas)
													o._fire('rowmouseover',o.rowDatas[rowIndex]);
											}
												
																	  },key);
			xui.util.Event.on(this.rowDoms[key] ,'mouseout',function(){
											var rowIndex = arguments[1];
											if(o._isInDatasRow(rowIndex)){
												o.unhoverRowByindex(rowIndex);
												if(o.rowDatas)
													o._fire('rowmouseout',o.rowDatas[rowIndex]);
											}
												
																	  },key);
		}
		
	},
	_initPageBar:function(){
		var o =this;
		xui.util.Event.on(this.firstBtnDom ,'click',o.toFirstPage,'',o);
		xui.util.Event.on(this.prevBtnDom ,'click',o.toPrevPage,'',o);
		xui.util.Event.on(this.lastBtnDom ,'click',o.toLastPage,'',o);
		xui.util.Event.on(this.nextBtnDom ,'click',o.toNextPage,'',o);
		xui.util.Event.on(this.numberGoDom ,'click',o.toNumberBtnPage,'',o);
		//回车事件
		var kl =new  xui.util.KeyListener(this.numberBtnDom,{keys:13},{fn:o.toNumberBtnPage,scope:o,correctScope:true });
		kl.enable();
	},
	//判断此row是否在数据域内
	_isInDatasRow:function(rowIndex){
		return (this.rowDatas && this.rowDatas[rowIndex])?true:false;
	},
	
	/**
	 * 排序切换
	 * 
	 * @param {String} hdId 列的ID
	 * @param {String} dir 排序方式 'desc'和'asc'
	 */	
	_dirSwitch:function(hdId,dir){
		
		if(this.currentHdId){
			xui.util.Dom.removeClass(this.hdDoms[this.currentHdId ],'xui-simpleGrid-sort-asc');
			xui.util.Dom.removeClass(this.hdDoms[this.currentHdId ],'xui-simpleGrid-sort-desc');
			xui.util.Dom.addClass(this.hdDoms[this.currentHdId],'xui-simpleGrid-sort-default');
		
		}
		//切换排序字段时初始排序顺序为降序（询盘管理三期引入）by eric.yangl
		if(this.currentHdId!=hdId){
			if(this.dir && this.dir == 'desc'){
				this.dir = 'asc';
			}
		}
		//强制制定排序方式
		if(dir && dir == 'desc'){
			this.dir = 'asc';
		}else if(dir && dir == 'asc'){
			this.dir = 'desc';
		}
		
		if(this.dir == 'desc'){
			this.dir = 'asc';
			xui.util.Dom.removeClass(this.hdDoms[hdId],'xui-simpleGrid-sort-default');
			xui.util.Dom.removeClass(this.hdDoms[hdId],'xui-simpleGrid-sort-asc');
			xui.util.Dom.addClass(this.hdDoms[hdId],'xui-simpleGrid-sort-asc');
		}
		else if(this.dir == 'asc'){
			this.dir = 'desc';
			xui.util.Dom.removeClass(this.hdDoms[hdId],'xui-simpleGrid-sort-default');
			xui.util.Dom.removeClass(this.hdDoms[hdId],'xui-simpleGrid-sort-asc');
			xui.util.Dom.addClass(this.hdDoms[hdId],'xui-simpleGrid-sort-desc');
		}
		//当前排序的hdId
		this.currentHdId = hdId;
	},
	/**
	 * 指定列排序	
	 * 
	 * @param {String} hdId 列的ID
	 * @param {String} dir 排序方式 'desc'和'asc',
	 * @param {String} beFirst 排序是否回到第一页，默认为false
	 */	
	sort:function(hdId,dir,beFirst){
		this.defaultSort = hdId;
		this._dirSwitch(hdId,dir);
		if(beFirst && beFirst == true){
			this.reload()
		}else{
			this.load();
		}
		
		this._fire('sort',hdId);
	},
	/**
	* * 设置dataSource的baseParams,并返回第一页
	*/
	reload:function(oParams){
		if(oParams)
			this.setParams(oParams);
		this.toFirstPage();
	},
	/**
	*
	*/
	load:function(){
		this.toPage(this.currentPage);
	},
	/**
	*选中全部行
	*/
	selectAll:function(){
		var l = this.rowDoms.length;
		for(var i = 0 ;i<l;i++){
			this.selectRowByIndex(i);
		}
/*		for(key in  this.rowDoms){
			this.selectRowByIndex(key);
		}*/
		if(this.checkbox)
			this.checkAll.checked = true;
	},
	/**
	 * 选择指定行	//如果显示的数据比limit少，那些行的事件需要特殊处理
	 * 
	 * @param {Number} rowIndex 行号
	 */	
	selectRowByIndex:function(rowIndex){
		if ( this.rowDatas && ! (rowIndex>(this.rowDatas.length-1))){
			xui.util.Dom.addClass(this.rowDoms[rowIndex],'xui-simpleGrid-row-selected');
			this.selectRowIndex = rowIndex;
			this._fire('rowselect',this.rowDatas[rowIndex]);
			//插入选中的数据
			this._registerSelectData(rowIndex);
			//如果与有checkbox需要勾上
			if(this.checkbox){
				if($('xui-simpleGrid-'+this.id+'-'+rowIndex)){
					$('xui-simpleGrid-'+this.id+'-'+rowIndex).checked = true;
				}
			}
		}
	},
	/**
	*得到选中后的行号
	*/
	getSelectRowIndex:function(){
		if(this.selectRowIndex)
			return this.selectRowIndex;
	},
	/**
	*得到鼠标移动上去的行号
	*/
	getMouseoverRowIndex:function(){
		if(this.mousoverRowIndex)
			return this.mousoverRowIndex;
	},
	/**
	*取消选择的行
	*/
	unselectRowByIndex:function(rowIndex){
		if ( this.rowDatas && ! (rowIndex>(this.rowDatas.length-1))){
			xui.util.Dom.removeClass(this.rowDoms[rowIndex],'xui-simpleGrid-row-selected');
			this._fire('unrowselect',this.rowDatas[rowIndex]);
			this._unRegisterSelectData(rowIndex);
			if(this.checkbox){
				if($('xui-simpleGrid-'+this.id+'-'+rowIndex)){
					$('xui-simpleGrid-'+this.id+'-'+rowIndex).checked = false;
				}
			}

		}
			
	},
	/**
	*清楚全部选中
	*/
	cleanRow:function(){
		for(key in  this.rowDoms){
			this.unselectRowByIndex(key);
			//xui.util.Dom.removeClass(this.rowDoms[key],'xui-simpleGrid-row-selected');
			
		}
		if(this.checkbox)
			this.checkAll.checked = false;
	},
	hoverRowByIndex:function(rowIndex){
		this.mousoverRowIndex = rowIndex;
		xui.util.Dom.addClass(this.rowDoms[rowIndex],'xui-simpleGrid-row-hover');
	},
	unhoverRowByindex:function(rowIndex){
		this.mousoverRowIndex = null;
		xui.util.Dom.removeClass(this.rowDoms[rowIndex],'xui-simpleGrid-row-hover');
	},
	/**
	 * 设置dataSource的参数
	 * @param {Object} oParames ，如：{stateType: 'abc',sType:1}会传这些参数和值给后台
	 */
	setParams:function(oParams){
		if(oParams){
			for(var k in oParams){
				oParams[k] = encodeURIComponent(oParams[k]);
			}
			this.params= oParams;
		}
	},
	_getParamsString:function(){
		var str = '';
		for(k in this.params){
			if(k == 'defaultSort'){
				this.defaultSort = this.params[k];
			}else if(k == 'dir'){
				this.dir = this.params[k];
			}else if(k == 'limit'){
				this.limit = this.params[k];
			}else{
				str +=  '&'+k +'='+this.params[k]+'';
			}
		}
		return str;
	},

	toPage:function(pageNumber){
		this.cleanRow();
		var start = (pageNumber-1)*this.limit;
		
		//如果排序的id存在，因为有可能排序的id是在scheme中有，而id中没有，那么就不需要现实排序的样式了
		if(this.colModelMap[this.defaultSort])
			this._dirSwitch(this.defaultSort,this.dir);
		
		this._getData(start);
		
		this.currentPage = pageNumber;
	},
	//是否隐藏上一个和第一个按钮
	_isHiddenPreButton:function(isHidden){
		if(this.firstBtnDom)
			this.firstBtnDom.disabled =isHidden;
		if(this.prevBtnDom)
			this.prevBtnDom.disabled =isHidden;
		if(isHidden){
			xui.util.Dom.addClass(this.firstBtnDom,'xui-pagebar-btn-first-disabled');
			xui.util.Dom.addClass(this.prevBtnDom,'xui-pagebar-btn-prev-disabled');
		}else{
			xui.util.Dom.removeClass(this.firstBtnDom,'xui-pagebar-btn-first-disabled');
			xui.util.Dom.addClass(this.firstBtnDom,'xui-pagebar-btn-first');
			xui.util.Dom.removeClass(this.prevBtnDom,'xui-pagebar-btn-prev-disabled');
			xui.util.Dom.addClass(this.prevBtnDom,'xui-pagebar-btn-prev');
		}
	},
	//是否隐藏下一个和最后一个按钮
	_isHiddenNextButton:function(isHidden){
		if(this.lastBtnDom)
			this.lastBtnDom.disabled =isHidden;
		if(this.nextBtnDom)
			this.nextBtnDom.disabled =isHidden;
		if(isHidden){
			xui.util.Dom.addClass(this.lastBtnDom,'xui-pagebar-btn-last-disabled');
			xui.util.Dom.addClass(this.nextBtnDom,'xui-pagebar-btn-next-disabled');
		}else{
			xui.util.Dom.removeClass(this.lastBtnDom,'xui-pagebar-btn-last-disabled');
			xui.util.Dom.addClass(this.lastBtnDom,'xui-pagebar-btn-last');
			xui.util.Dom.removeClass(this.nextBtnDom,'xui-pagebar-btn-next-disabled');
			xui.util.Dom.addClass(this.nextBtnDom,'xui-pagebar-btn-next');
		}
	},
	_rendPageBar:function(){
		var start = (this.currentPage-1)*this.limit;
		if( this.isFirstPage(this.currentPage) && this.isLastPage(this.currentPage)){
			this._isHiddenPreButton(true);
			this._isHiddenNextButton(true);
		}else if(this.isFirstPage(this.currentPage)){
			this._isHiddenPreButton(true);
			this._isHiddenNextButton(false);
			
		}else if(this.isLastPage(this.currentPage)){
			this._isHiddenNextButton(true);
			
			this._isHiddenPreButton(false);
		}else{
			this._isHiddenPreButton(false);
			this._isHiddenNextButton(false);
		}
		
		if(this.hasPagebar){
			if(this.numberBtnDom)
				this.numberBtnDom.value = this.currentPage;
			if(this.numberStartDom){
				if(this.totalCount == 0){
					this.numberStartDom.innerHTML = 0;
				}else{
					this.numberStartDom.innerHTML = start+1;
				}
			}
			if(this.numberEndDom){
				//如果每页数目小于总条数，或者最后一页
				if( this.limit>this.totalCount || (this.isLastPage()) )
					this.numberEndDom.innerHTML = this.totalCount;
				else {
					this.numberEndDom.innerHTML = start+this.limit;
				}
			}
			//是否有总页数显示
			if(this.hasPages && this.numberTotalPages){
				var lastPage = Math.ceil(this.totalCount/this.limit);
				this.numberTotalPages.innerHTML = lastPage;
				
			}

		}
	},
	_initSore:function(){
		
	},
	/**
	*
	*/
	_getData:function(start){
		var defaultParms= 'start='+start+'&sort='+(this.defaultSort?this.defaultSort:'')+'&dir='+(this.dir?this.dir:'')+'&limit='+(this.limit?this.limit:'');
		var parms = ''
		if(this.params){
			parms = this._getParamsString();
		}
		var o = this;try{
		xui.util.Ajax.asyncPOST(this.url,defaultParms+parms,function(str){
			
						var data = xui.util.JsonUtil.decode(str);	
						o.store = data;
						if(data.isSuccess === false){
							o._fire('dserror',data.message);
						}else{
						o._rendData(data);	
						}
				},function(){o._fire('ajaxerror');})
		}catch(e){};
	},
	/**
	*根据数据的数目显示多少行
	*/
	_cleanRow:function(l){
		var rowdomsL = this.rowDoms.length;
		for(var i = 0 ;i<rowdomsL;i++){
			this.rowDoms[i].style.display = (i>=l)? 'none':'block'; 
		}
	},
	_rendData:function(o){
		this.rowDatas = o.data;
		this.totalCount = o.totalCount;
		if(this.hasPagebar && this.numberTotalDom){
			this.numberTotalDom .innerHTML = this.totalCount;
		}
		//如果没有数据，回到前一页
		var l = this.rowDatas.length;

		this.dataRowDoms = this.rowDoms;
		
		//返回数据的条数大于this.limit的内容，只能渲染this.limit的条数
		l = (l>this.limit)? this.limit : l;
		
		//根据数据的数目显示多少行
		if(this.isRowAuto)
			this._cleanRow(l);
		
		for(var i = 0; i<l;i++){
			this.setRowData(i,this.rowDatas[i]);
		}
		//如果数据比每页的条数少，还需要删除掉前一页未被覆盖的数据
		if(this.limit>l){
			this.dataRowDoms = this.rowDoms.slice(0,l);
			for(var j = l;j<this.limit;j++){
				this.cleanRowData(j);
			}
		}
		//加checkbox事件
		if(this.checkbox){
			this._addCheckBoxEvent();
		}
		if(this.hasPagebar)
			this._rendPageBar();
		var o = this;
		this._fire('dsload',o);
		if(l==0){
			this.toPrevPage();
			return ;
		}
	},
	/**
	 * 设置一行里面的内容
	 * 
	 * @param {Number} rowIndex 行号
	 * @param {Object} {sex:"1",age:"2222123",birthday:"2008-10-11",name:"1111",status:"1"}
	 */	
	setRowData:function(rowIndex,rowData){
		//设置数据
		for(var p in rowData){
            this.rowDatas[rowIndex][p] = rowData[p];
    	}
		//var rowData = {sex:"1",age:"2222123",birthday:"2008-10-11",name:"1111",status:"1"}
		//for(key in this.idSchema){
			//防止外面array原生对象被改变，判断空值和和属性
			//if(this.idSchema.hasOwnProperty(key) && this.idSchema[key]){
				//根据id得到dataIndex，然后获取相应的值
				//var v = rowData[ this.colModelMap[this.idSchema[key]].dataIndex ];
				//this.rowDatas[rowIndex] = rowData;
				//有可能id存在，但是数据里面不存在，所以要判断
				//v = v ? v:'';
				//this.setCellData(rowIndex,this.idSchema[key],v);
			//}
		//}
		var l = this.idSchema.length;
		for(var i = 0 ;i<l ;i++){
			//根据id得到dataIndex，然后获取相应的值,有可能没有dataIndex,
			var v = '';
			if(this.colModelMap[this.idSchema[i]].dataIndex ){
				 v = this.rowDatas[rowIndex][ this.colModelMap[this.idSchema[i]].dataIndex ];
			}
			//处理当有checkbox选中的时候时候
			if(this.checkbox && this.idSchema[i] == 'checkbox'){
				if($('xui-simpleGrid-'+this.id+'-'+rowIndex)){
					v = $('xui-simpleGrid-'+this.id+'-'+rowIndex).checked;
				}
			}
			this.setCellData(rowIndex,this.idSchema[i],v);
		}
	},
	/**清除row里面的内容
	*
	* @param {Number} rowIndex 行号
	*/
	cleanRowData:function(rowIndex){
		if(this.idSchema.length){
			 for(var i=0; i<this.idSchema.length; i++){
				this.cleanCellData(rowIndex,this.idSchema[i]);
			 }
		}else{
			for(key in this.idSchema){
				this.cleanCellData(rowIndex,this.idSchema[key]);
			}
		}
	},
	/**
	 * 设置cell里面的内容
	 * 
	 * @param {Number} rowIndex 行号
	 * @param {String} colId 列的id
	 * @param {String} v 要设置的值
	 */	
	setCellData:function(rowIndex,colId,v){
		
		if(this.getCellTextDomByIndex(rowIndex,colId)){
			//有渲染函数德列，需要调用渲染函数来渲染单元格
			if(this.colModelMap[colId].renderer){
				this.getCellTextDomByIndex(rowIndex,colId).innerHTML = this.colModelMap[colId].renderer(v,this.rowDatas[rowIndex],rowIndex,this);
			}else{
				var fv = xui.util.StringUtil.htmlEncode(v)
				
				this.getCellTextDomByIndex(rowIndex,colId).innerHTML = (typeof fv == "undefined"|| fv ==null) ? '' : fv;
				this.getCellTextDomByIndex(rowIndex,colId).title = v;
			}
			if(this.colModelMap[colId].align)
				this.getCellTextDomByIndex(rowIndex,colId).style.textAlign = this.colModelMap[colId].align;
		}
		

	},
	/**清除cell里面的内容
	*
	* @param {Number} rowIndex 行号
	* @param {String} colId 列的id
	**/
	cleanCellData:function(rowIndex,colId){
		if(this.getCellTextDomByIndex(rowIndex,colId))
			this.getCellTextDomByIndex(rowIndex,colId).innerHTML = '';
	},
	/**
	 * 获取返回的数据对象
	 * 
	 * @return {Object}
	 */
	getStore:function(){
		return this.store;
	},
	/**
	 * 根据行号得到行的dom对象
	 * 
	 * @param {Number} rowIndex 行号
	 * @return {Object} HTMLElement 
	 */	
	getRowDomByIndex:function(rowIndex){
		return this.rowDoms[rowIndex];
	},
	/**
	 * 根据行号和列的id得到一格的dom对象
	 * 
	 * @param {Number} rowIndex 行号
	 * @param {String} colId 列的id
	 * @return {Object} HTMLElement 
	 */	
	getCellDomByIndex:function(rowIndex,colId){
		var rowDom = this.getRowDomByIndex(rowIndex);
		var cellDom =  xui.util.Dom.getElementsByClassName('xui-simpleGrid-col-'+colId,'td',rowDom)[0];
		return cellDom;
	},
	/**
	 * 根据行号和列的id得到一格的text dom对象
	 * 
	 * @param {Number} rowIndex 行号
	 * @param {String} colId 列的id
	 * @return {Object} HTMLElement 
	 */	
	getCellTextDomByIndex:function(rowIndex,colId){
		var cellDom =  this.getCellDomByIndex(rowIndex,colId)
		var cellTextDom = xui.util.Dom.getFirstChildBy(cellDom);
		return cellTextDom;
	},
	getSelect:function(){
		var o = [];
		var i = 0;
		for(key in this.selectDatas){
			o[i] = this.selectDatas[key];
			i++;
		}
		return o;
	},
	_registerSelectData : function(rowIndex){
        this.selectDatas['rowIndex'+rowIndex] = this.rowDatas[rowIndex];
  	},
	_unRegisterSelectData:function(rowIndex){
		 delete this.selectDatas['rowIndex'+rowIndex];
	},
	/**把行数据转换成列数据*/
	_tranData:function(){
		
	},
	toNextPage:function(o){
		var page = xui.util.NumberUtil.add(this.currentPage,1,'+') ;
		this.toPage(page);
	},
	toPrevPage:function(){
		var page =xui.util.NumberUtil.add(this.currentPage,1,'-') ;
		if(page < 1){
			return ;
		}
		this.toPage(page);
	},
	toFirstPage:function(){
		this.toPage(1);
	},
	toLastPage:function(){
		var lastPage = Math.ceil(this.totalCount/this.limit);
		this.toPage(lastPage);
	},
	toNumberBtnPage:function(){
		var num = xui.util.StringUtil.trim(this.numberBtnDom.value);
		var lastPage = Math.ceil(this.totalCount/this.limit);
		if(xui.util.Validator.isInt(num) && num>0 && num<=lastPage)
			this.toPage(num);
	},
	isFirstPage:function(){
		return (this.currentPage ==1)?true:false;
	},
	isLastPage:function(){
		//如果总共是0条数据，最后一页也算1
		var lastPage = Math.ceil(this.totalCount/this.limit);		
		lastPage = (lastPage == 0) ? 1: lastPage;
		return (this.currentPage ==lastPage)?true:false;
	},
	/**
	*获取当前页数
	*@return {Number}  
	*/
	getCurrentPage:function(){
		return this.currentPage;
	},
	/**
	*获取数据总数
	*@return {Number}  
	*/
	getTotalCount:function(){
		return this.totalCount;
	},
	/**
	*获取当前页的条数
	*@return {Number}  
	*/
	getPageCount:function(){
		return (this.rowDatas)? (this.rowDatas.length) :0;
	}
	
}

