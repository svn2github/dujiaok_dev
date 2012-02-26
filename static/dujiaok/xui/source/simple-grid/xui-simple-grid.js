		
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
	*  return {Object} {sex:{id:'sex',header: '�Ա�', dataIndex: 'sex',renderer:onRender},age:{id: 'age', header: '����', dataIndex: 'age'}}
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
 * @param {String} id  grid������Id
 * @param {Object} attrs  �������� eg��{hasPagebar:true,limit:10}
 */

xui.widgets.SimpleGrid = function(id,attrs) {
	this.id = id;
	this.attributes = attrs;
	
	/**
	*@field {Number} width  ���
	*/
	this.width =( this.attributes && this.attributes.width)?this.attributes.width:'100%';
	/**
	*@field {Number} height  �߶�,Ĭ�ϸ߶�Ϊ�����б�������Զ�����
	*/
	this.height =( this.attributes && this.attributes.height)?this.attributes.height:false;
	/**
	*@field {Number} limit  ��ҳ�� Ĭ��:10
	*/	
	this.limit = ( this.attributes && this.attributes.limit)?this.attributes.limit:10;
		/**
	*@field {Number} toPageNum  ��ʼ��ʾ�ڼ�ҳ��Ĭ���ǵ�һҳ Ĭ��:10
	*/	
	this.toPageNum = ( this.attributes && this.attributes.toPageNum)?this.attributes.toPageNum:1;
	/**
	*@field {Boolean} checkbox  �Ƿ���checkbox ,Ĭ��:false
	*/
	this.checkbox = ( this.attributes && this.attributes.checkbox)?this.attributes.checkbox:false;
	/**
	*@field {Boolean} hasPages  �Ƿ����ܹ�����ҳ ,Ĭ��:false
	*/
	this.hasPages = ( this.attributes && this.attributes.hasPages)?this.attributes.hasPages:false;
	/**
	*@field {Boolean} hasPagebar  �Ƿ��з�ҳ�� ,Ĭ��:true
	*/
	this.hasPagebar =( this.attributes && !(typeof this.attributes.hasPagebar == "undefined"|| this.attributes.hasPagebar ==null) && !this.attributes.hasPagebar)?this.attributes.hasPagebar:true;
	/**
	*@field {Boolean} isRowAuto  �Ƿ�������ݵ���Ŀ��ʾ������ ,Ĭ��:true
	*/
	this.isRowAuto =( this.attributes && !(typeof this.attributes.isRowAuto == "undefined"|| this.attributes.isRowAuto ==null) && !this.attributes.isRowAuto)?this.attributes.isRowAuto:true;
	/**
	*@field {Boolean} hasTips  �Ƿ���tips ,Ĭ��:true
	*/
	//this.hasTips =( this.attributes && !(typeof this.attributes.hasTips == "undefined"|| this.attributes.hasTips ==null) && !this.attributes.hasTips)?this.attributes.hasTips:true;
	
	/**
	*@field {String} defaultSort  Ĭ������ ,Ĭ��:false
	*/
	this.defaultSort =( this.attributes && this.attributes.defaultSort)?this.attributes.defaultSort:'';
	/**
	*@field {String} dir  ����˳�� ,Ĭ��:desc,asc
	*/
	this.dir =( this.attributes && this.attributes.dir)?this.attributes.dir:'desc';
	/**
	*@field {Boolean} 	  �Ƿ���Զ�ѡ ,Ĭ��:false
	*/
	this.multiSelect = ( this.attributes && this.attributes.multiSelect)?this.attributes.multiSelect:false;

	this.store = null;
	this.schema = null;
	this.colModel=null;
	
	/**
	*@field {Number} start ����̨��������ʱ���������ڷ�ҳ  ,Ĭ��0
	*/
	this.start = 0 ;

	/**
	*@field {Number} currentPage ��ǰҳ��Ĭ�ϵ�һҳ
	*/
	this.currentPage= 1;
	/**
	*@field {Number} totalCount  ȫ����������
	*/
	this.totalCount = 0;
	
	this._events = {};
	this.selectDatas = {};
}
xui.widgets.SimpleGrid.prototype = {
	/**
	 * ��ʼ��Grid
	 * 
	 * @param {String} sUrl �����ַ
	 * @param {Object} oColModel ��ģ�� ���磺[	{
							header: '�ʼ�״̬',
							width: 80,
							dataIndex:'type',
							sortable: true
						},{
							header:'customerAddress',
							dataIndex: 'customerAddress',
							width: 68,
							sortable: true
							
						}]
	 * @param {Object} oSchema ����ģ�� ���磺['type','customerAddress']
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
		//��ģ��
		this.colModel = oColModel.get();
		//��ģ���е�id����
		this.idSchema = oColModel.getIdSchema();
		//��ģ���е�dataIndex����
		this.dataIndexSchema = oColModel.getDataIndexSchema();
		
		this.colModelMap = oColModel.getIdColModelMap();
		
		//ȫ���е�dom��������
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
	/**��Ⱦͷ��
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
	//��Ĭ��ͷ���¼�
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
													//ȡ��ð�ݣ���ֹ����click�¼�
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

	//��Ĭ�����¼�
	_addRowDomsEvents:function(){
		var o =this;
		for(key in  this.rowDoms){
			xui.util.Event.on(this.rowDoms[key] ,'click',function(){
											var rowIndex = arguments[1];
											if(o._isInDatasRow(rowIndex)){
												if(!o.multiSelect){
													o.cleanRow();
												}
												//��ѡģʽ������ʱ�򣬵�����ѡ�У��ٵ�����ѡ��
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
		//�س��¼�
		var kl =new  xui.util.KeyListener(this.numberBtnDom,{keys:13},{fn:o.toNumberBtnPage,scope:o,correctScope:true });
		kl.enable();
	},
	//�жϴ�row�Ƿ�����������
	_isInDatasRow:function(rowIndex){
		return (this.rowDatas && this.rowDatas[rowIndex])?true:false;
	},
	
	/**
	 * �����л�
	 * 
	 * @param {String} hdId �е�ID
	 * @param {String} dir ����ʽ 'desc'��'asc'
	 */	
	_dirSwitch:function(hdId,dir){
		
		if(this.currentHdId){
			xui.util.Dom.removeClass(this.hdDoms[this.currentHdId ],'xui-simpleGrid-sort-asc');
			xui.util.Dom.removeClass(this.hdDoms[this.currentHdId ],'xui-simpleGrid-sort-desc');
			xui.util.Dom.addClass(this.hdDoms[this.currentHdId],'xui-simpleGrid-sort-default');
		
		}
		//�л������ֶ�ʱ��ʼ����˳��Ϊ����ѯ�̹����������룩by eric.yangl
		if(this.currentHdId!=hdId){
			if(this.dir && this.dir == 'desc'){
				this.dir = 'asc';
			}
		}
		//ǿ���ƶ�����ʽ
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
		//��ǰ�����hdId
		this.currentHdId = hdId;
	},
	/**
	 * ָ��������	
	 * 
	 * @param {String} hdId �е�ID
	 * @param {String} dir ����ʽ 'desc'��'asc',
	 * @param {String} beFirst �����Ƿ�ص���һҳ��Ĭ��Ϊfalse
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
	* * ����dataSource��baseParams,�����ص�һҳ
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
	*ѡ��ȫ����
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
	 * ѡ��ָ����	//�����ʾ�����ݱ�limit�٣���Щ�е��¼���Ҫ���⴦��
	 * 
	 * @param {Number} rowIndex �к�
	 */	
	selectRowByIndex:function(rowIndex){
		if ( this.rowDatas && ! (rowIndex>(this.rowDatas.length-1))){
			xui.util.Dom.addClass(this.rowDoms[rowIndex],'xui-simpleGrid-row-selected');
			this.selectRowIndex = rowIndex;
			this._fire('rowselect',this.rowDatas[rowIndex]);
			//����ѡ�е�����
			this._registerSelectData(rowIndex);
			//�������checkbox��Ҫ����
			if(this.checkbox){
				if($('xui-simpleGrid-'+this.id+'-'+rowIndex)){
					$('xui-simpleGrid-'+this.id+'-'+rowIndex).checked = true;
				}
			}
		}
	},
	/**
	*�õ�ѡ�к���к�
	*/
	getSelectRowIndex:function(){
		if(this.selectRowIndex)
			return this.selectRowIndex;
	},
	/**
	*�õ�����ƶ���ȥ���к�
	*/
	getMouseoverRowIndex:function(){
		if(this.mousoverRowIndex)
			return this.mousoverRowIndex;
	},
	/**
	*ȡ��ѡ�����
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
	*���ȫ��ѡ��
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
	 * ����dataSource�Ĳ���
	 * @param {Object} oParames ���磺{stateType: 'abc',sType:1}�ᴫ��Щ������ֵ����̨
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
		
		//��������id���ڣ���Ϊ�п��������id����scheme���У���id��û�У���ô�Ͳ���Ҫ��ʵ�������ʽ��
		if(this.colModelMap[this.defaultSort])
			this._dirSwitch(this.defaultSort,this.dir);
		
		this._getData(start);
		
		this.currentPage = pageNumber;
	},
	//�Ƿ�������һ���͵�һ����ť
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
	//�Ƿ�������һ�������һ����ť
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
				//���ÿҳ��ĿС�����������������һҳ
				if( this.limit>this.totalCount || (this.isLastPage()) )
					this.numberEndDom.innerHTML = this.totalCount;
				else {
					this.numberEndDom.innerHTML = start+this.limit;
				}
			}
			//�Ƿ�����ҳ����ʾ
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
	*�������ݵ���Ŀ��ʾ������
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
		//���û�����ݣ��ص�ǰһҳ
		var l = this.rowDatas.length;

		this.dataRowDoms = this.rowDoms;
		
		//�������ݵ���������this.limit�����ݣ�ֻ����Ⱦthis.limit������
		l = (l>this.limit)? this.limit : l;
		
		//�������ݵ���Ŀ��ʾ������
		if(this.isRowAuto)
			this._cleanRow(l);
		
		for(var i = 0; i<l;i++){
			this.setRowData(i,this.rowDatas[i]);
		}
		//������ݱ�ÿҳ�������٣�����Ҫɾ����ǰһҳδ�����ǵ�����
		if(this.limit>l){
			this.dataRowDoms = this.rowDoms.slice(0,l);
			for(var j = l;j<this.limit;j++){
				this.cleanRowData(j);
			}
		}
		//��checkbox�¼�
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
	 * ����һ�����������
	 * 
	 * @param {Number} rowIndex �к�
	 * @param {Object} {sex:"1",age:"2222123",birthday:"2008-10-11",name:"1111",status:"1"}
	 */	
	setRowData:function(rowIndex,rowData){
		//��������
		for(var p in rowData){
            this.rowDatas[rowIndex][p] = rowData[p];
    	}
		//var rowData = {sex:"1",age:"2222123",birthday:"2008-10-11",name:"1111",status:"1"}
		//for(key in this.idSchema){
			//��ֹ����arrayԭ�����󱻸ı䣬�жϿ�ֵ�ͺ�����
			//if(this.idSchema.hasOwnProperty(key) && this.idSchema[key]){
				//����id�õ�dataIndex��Ȼ���ȡ��Ӧ��ֵ
				//var v = rowData[ this.colModelMap[this.idSchema[key]].dataIndex ];
				//this.rowDatas[rowIndex] = rowData;
				//�п���id���ڣ������������治���ڣ�����Ҫ�ж�
				//v = v ? v:'';
				//this.setCellData(rowIndex,this.idSchema[key],v);
			//}
		//}
		var l = this.idSchema.length;
		for(var i = 0 ;i<l ;i++){
			//����id�õ�dataIndex��Ȼ���ȡ��Ӧ��ֵ,�п���û��dataIndex,
			var v = '';
			if(this.colModelMap[this.idSchema[i]].dataIndex ){
				 v = this.rowDatas[rowIndex][ this.colModelMap[this.idSchema[i]].dataIndex ];
			}
			//������checkboxѡ�е�ʱ��ʱ��
			if(this.checkbox && this.idSchema[i] == 'checkbox'){
				if($('xui-simpleGrid-'+this.id+'-'+rowIndex)){
					v = $('xui-simpleGrid-'+this.id+'-'+rowIndex).checked;
				}
			}
			this.setCellData(rowIndex,this.idSchema[i],v);
		}
	},
	/**���row���������
	*
	* @param {Number} rowIndex �к�
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
	 * ����cell���������
	 * 
	 * @param {Number} rowIndex �к�
	 * @param {String} colId �е�id
	 * @param {String} v Ҫ���õ�ֵ
	 */	
	setCellData:function(rowIndex,colId,v){
		
		if(this.getCellTextDomByIndex(rowIndex,colId)){
			//����Ⱦ�������У���Ҫ������Ⱦ��������Ⱦ��Ԫ��
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
	/**���cell���������
	*
	* @param {Number} rowIndex �к�
	* @param {String} colId �е�id
	**/
	cleanCellData:function(rowIndex,colId){
		if(this.getCellTextDomByIndex(rowIndex,colId))
			this.getCellTextDomByIndex(rowIndex,colId).innerHTML = '';
	},
	/**
	 * ��ȡ���ص����ݶ���
	 * 
	 * @return {Object}
	 */
	getStore:function(){
		return this.store;
	},
	/**
	 * �����кŵõ��е�dom����
	 * 
	 * @param {Number} rowIndex �к�
	 * @return {Object} HTMLElement 
	 */	
	getRowDomByIndex:function(rowIndex){
		return this.rowDoms[rowIndex];
	},
	/**
	 * �����кź��е�id�õ�һ���dom����
	 * 
	 * @param {Number} rowIndex �к�
	 * @param {String} colId �е�id
	 * @return {Object} HTMLElement 
	 */	
	getCellDomByIndex:function(rowIndex,colId){
		var rowDom = this.getRowDomByIndex(rowIndex);
		var cellDom =  xui.util.Dom.getElementsByClassName('xui-simpleGrid-col-'+colId,'td',rowDom)[0];
		return cellDom;
	},
	/**
	 * �����кź��е�id�õ�һ���text dom����
	 * 
	 * @param {Number} rowIndex �к�
	 * @param {String} colId �е�id
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
	/**��������ת����������*/
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
		//����ܹ���0�����ݣ����һҳҲ��1
		var lastPage = Math.ceil(this.totalCount/this.limit);		
		lastPage = (lastPage == 0) ? 1: lastPage;
		return (this.currentPage ==lastPage)?true:false;
	},
	/**
	*��ȡ��ǰҳ��
	*@return {Number}  
	*/
	getCurrentPage:function(){
		return this.currentPage;
	},
	/**
	*��ȡ��������
	*@return {Number}  
	*/
	getTotalCount:function(){
		return this.totalCount;
	},
	/**
	*��ȡ��ǰҳ������
	*@return {Number}  
	*/
	getPageCount:function(){
		return (this.rowDatas)? (this.rowDatas.length) :0;
	}
	
}

