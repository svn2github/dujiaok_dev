xui.widgets.SimpleGridColModel=function(a){this.defaults=a.defaults;this.columns=a.columns;for(key in this.columns){if(!this.columns[key].width){this.columns[key].width=this.defaults.width}if(typeof this.columns[key].sortable=="undefined"||this.columns[key].sortable==null){this.columns[key].sortable=this.defaults.sortable}}};xui.widgets.SimpleGridColModel.prototype={push:function(a){this.columns.push(a)},get:function(){return this.columns},_getSchemaBykey:function(c){var b=[];var a=0;while(this.columns[a]){b[a]=this.columns[a][c];a++}return b},getIdSchema:function(){return this._getSchemaBykey("id")},getIdColModelMap:function(){var a={};for(key in this.columns){a[this.columns[key].id]=this.columns[key]}return a},getDataIndexSchema:function(){return this._getSchemaBykey("dataIndex")}};xui.widgets.SimpleGridSelectionModel=function(){};xui.widgets.SimpleGridSelectionModel.prototype={};xui.widgets.SimpleGrid=function(b,a){this.id=b;this.attributes=a;this.width=(this.attributes&&this.attributes.width)?this.attributes.width:"100%";this.height=(this.attributes&&this.attributes.height)?this.attributes.height:false;this.limit=(this.attributes&&this.attributes.limit)?this.attributes.limit:10;this.toPageNum=(this.attributes&&this.attributes.toPageNum)?this.attributes.toPageNum:1;this.checkbox=(this.attributes&&this.attributes.checkbox)?this.attributes.checkbox:false;this.hasPages=(this.attributes&&this.attributes.hasPages)?this.attributes.hasPages:false;this.hasPagebar=(this.attributes&&!(typeof this.attributes.hasPagebar=="undefined"||this.attributes.hasPagebar==null)&&!this.attributes.hasPagebar)?this.attributes.hasPagebar:true;this.isRowAuto=(this.attributes&&!(typeof this.attributes.isRowAuto=="undefined"||this.attributes.isRowAuto==null)&&!this.attributes.isRowAuto)?this.attributes.isRowAuto:true;this.defaultSort=(this.attributes&&this.attributes.defaultSort)?this.attributes.defaultSort:"";this.dir=(this.attributes&&this.attributes.dir)?this.attributes.dir:"desc";this.multiSelect=(this.attributes&&this.attributes.multiSelect)?this.attributes.multiSelect:false;this.store=null;this.schema=null;this.colModel=null;this.start=0;this.currentPage=1;this.totalCount=0;this._events={};this.selectDatas={}};xui.widgets.SimpleGrid.prototype={init:function(c,b,a){var d=this;this.url=c;this.schema=a;if(this.checkbox){b.push({id:"checkbox",renderer:d._renderCheckBox});this.checkAll=xui.util.Dom.getElementsByClassName("xui-simpleGrid-hd-checkbox","input",this.id)[0];xui.util.Event.on(this.checkAll,"click",function(){if(d.checkAll.checked){d.selectAll()}else{d.cleanRow()}})}this.colModel=b.get();this.idSchema=b.getIdSchema();this.dataIndexSchema=b.getDataIndexSchema();this.colModelMap=b.getIdColModelMap();this.rowDoms=xui.util.Dom.getElementsByClassName("xui-simpleGrid-row","div",this.id);this._addRowDomsEvents();this.getHdDoms();this._addHdDomsEvents();this._renderHead();this.firstBtnDom=xui.util.Dom.getElementsByClassName("xui-pagebar-btn-first","button",this.id)[0];this.prevBtnDom=xui.util.Dom.getElementsByClassName("xui-pagebar-btn-prev","button",this.id)[0];this.lastBtnDom=xui.util.Dom.getElementsByClassName("xui-pagebar-btn-last","button",this.id)[0];this.nextBtnDom=xui.util.Dom.getElementsByClassName("xui-pagebar-btn-next","button",this.id)[0];this.numberBtnDom=xui.util.Dom.getElementsByClassName("xui-pagebar-btn-number","input",this.id)[0];this.numberStartDom=xui.util.Dom.getElementsByClassName("xui-pagebar-start","span",this.id)[0];this.numberEndDom=xui.util.Dom.getElementsByClassName("xui-pagebar-end","span",this.id)[0];this.numberTotalDom=xui.util.Dom.getElementsByClassName("xui-pagebar-total","span",this.id)[0];this.numberGoDom=xui.util.Dom.getElementsByClassName("xui-pagebar-btn-go","button",this.id)[0];this.numberTotalPages=xui.util.Dom.getElementsByClassName("xui-pagebar-pages","span",this.id)[0];if(this.hasPagebar){this._initPageBar()}this.toPage(this.toPageNum);this._initEnvents("rowmouseover");this._initEnvents("rowmouseout");this._initEnvents("rowclick");this._initEnvents("rowdblclick");this._initEnvents("sort");this._initEnvents("rowselect");this._initEnvents("unrowselect");this._initEnvents("dsload");this._initEnvents("dserror");this._initEnvents("ajaxerror");this._initEnvents("checkboxclick")},_renderCheckBox:function(a,d,e,c){var b=a?"checked":"";return'<input id="xui-simpleGrid-'+c.id+"-"+e+'" name="" class="xui-simpleGrid-row-checkbox" type="checkbox" value="" '+b+" />"},getHdDoms:function(){if(!this.hdDoms){this.hdDoms={};var a=xui.util.Dom.getElementsByClassName("xui-simpleGrid-header","div",this.id)[0];for(key in this.idSchema){this.hdDoms[this.idSchema[key]]=xui.util.Dom.getElementsByClassName("xui-simpleGrid-hd-"+this.idSchema[key],"td",a)[0]}}return this.hdDoms},_renderHead:function(){for(key in this.hdDoms){if(key!="checkbox"&&key!="undefined"){if(typeof this.hdDoms[key]!="undefined"){var a=xui.util.Dom.getElementsByClassName("xui-simpleGrid-hd-inner","div",this.hdDoms[key])[0];var b=this.colModelMap[key].header;a.innerHTML=b+'<button  class="xui-simpleGrid-sort" ></button>'}}}},on:function(a,b){var c=this;this._events[a].subscribe(b,c)},_initEnvents:function(a){var b=this;this._events[a]=new xui.util.CustomEvent(a,b)},_fire:function(b,a){this._events[b].fire(a)},_addHdDomsEvents:function(){var a=this;for(key in this.colModelMap){if(this.colModelMap[key].sortable==true){xui.util.Dom.addClass(this.hdDoms[key],"xui-simpleGrid-sort-default");xui.util.Event.on(this.hdDoms[key],"click",function(){var b=arguments[1];a.sort(b)},key)}}},_addCheckBoxEvent:function(){var a=this;for(key in this.rowDoms){if($("xui-simpleGrid-"+this.id+"-"+key)){xui.util.Event.on([$("xui-simpleGrid-"+this.id+"-"+key).parentNode,$("xui-simpleGrid-"+this.id+"-"+key).parentNode.parentNode],"click",function(){arguments[0].cancelBubble=true})}xui.util.Event.on($("xui-simpleGrid-"+this.id+"-"+key),"click",function(){arguments[0].cancelBubble=true;var b=arguments[1];a._fire("checkboxclick",a.rowDatas[b]);if($("xui-simpleGrid-"+a.id+"-"+b).checked){a.selectRowByIndex(b)}else{a.unselectRowByIndex(b)}},key)}},_addRowDomsEvents:function(){var a=this;for(key in this.rowDoms){xui.util.Event.on(this.rowDoms[key],"click",function(){var b=arguments[1];if(a._isInDatasRow(b)){if(!a.multiSelect){a.cleanRow()}if(a.multiSelect){if($("xui-simpleGrid-"+a.id+"-"+b).checked){a.unselectRowByIndex(b)}else{a.selectRowByIndex(b)}}else{a.selectRowByIndex(b)}if(a.rowDatas){a._fire("rowclick",a.rowDatas[b])}}},key);xui.util.Event.on(this.rowDoms[key],"dblclick",function(){var b=arguments[1];if(a._isInDatasRow(b)){if(a.rowDatas){a._fire("rowdblclick",a.rowDatas[b])}}},key);xui.util.Event.on(this.rowDoms[key],"mouseover",function(){var b=arguments[1];if(a._isInDatasRow(b)){a.hoverRowByIndex(b);if(a.rowDatas){a._fire("rowmouseover",a.rowDatas[b])}}},key);xui.util.Event.on(this.rowDoms[key],"mouseout",function(){var b=arguments[1];if(a._isInDatasRow(b)){a.unhoverRowByindex(b);if(a.rowDatas){a._fire("rowmouseout",a.rowDatas[b])}}},key)}},_initPageBar:function(){var b=this;xui.util.Event.on(this.firstBtnDom,"click",b.toFirstPage,"",b);xui.util.Event.on(this.prevBtnDom,"click",b.toPrevPage,"",b);xui.util.Event.on(this.lastBtnDom,"click",b.toLastPage,"",b);xui.util.Event.on(this.nextBtnDom,"click",b.toNextPage,"",b);xui.util.Event.on(this.numberGoDom,"click",b.toNumberBtnPage,"",b);var a=new xui.util.KeyListener(this.numberBtnDom,{keys:13},{fn:b.toNumberBtnPage,scope:b,correctScope:true});a.enable()},_isInDatasRow:function(a){return(this.rowDatas&&this.rowDatas[a])?true:false},_dirSwitch:function(b,a){if(this.currentHdId){xui.util.Dom.removeClass(this.hdDoms[this.currentHdId],"xui-simpleGrid-sort-asc");xui.util.Dom.removeClass(this.hdDoms[this.currentHdId],"xui-simpleGrid-sort-desc");xui.util.Dom.addClass(this.hdDoms[this.currentHdId],"xui-simpleGrid-sort-default")}if(this.currentHdId!=b){if(this.dir&&this.dir=="desc"){this.dir="asc"}}if(a&&a=="desc"){this.dir="asc"}else{if(a&&a=="asc"){this.dir="desc"}}if(this.dir=="desc"){this.dir="asc";xui.util.Dom.removeClass(this.hdDoms[b],"xui-simpleGrid-sort-default");xui.util.Dom.removeClass(this.hdDoms[b],"xui-simpleGrid-sort-asc");xui.util.Dom.addClass(this.hdDoms[b],"xui-simpleGrid-sort-asc")}else{if(this.dir=="asc"){this.dir="desc";xui.util.Dom.removeClass(this.hdDoms[b],"xui-simpleGrid-sort-default");xui.util.Dom.removeClass(this.hdDoms[b],"xui-simpleGrid-sort-asc");xui.util.Dom.addClass(this.hdDoms[b],"xui-simpleGrid-sort-desc")}}this.currentHdId=b},sort:function(b,a,c){this.defaultSort=b;this._dirSwitch(b,a);if(c&&c==true){this.reload()}else{this.load()}this._fire("sort",b)},reload:function(a){if(a){this.setParams(a)}this.toFirstPage()},load:function(){this.toPage(this.currentPage)},selectAll:function(){var a=this.rowDoms.length;for(var b=0;b<a;b++){this.selectRowByIndex(b)}if(this.checkbox){this.checkAll.checked=true}},selectRowByIndex:function(a){if(this.rowDatas&&!(a>(this.rowDatas.length-1))){xui.util.Dom.addClass(this.rowDoms[a],"xui-simpleGrid-row-selected");this.selectRowIndex=a;this._fire("rowselect",this.rowDatas[a]);this._registerSelectData(a);if(this.checkbox){if($("xui-simpleGrid-"+this.id+"-"+a)){$("xui-simpleGrid-"+this.id+"-"+a).checked=true}}}},getSelectRowIndex:function(){if(this.selectRowIndex){return this.selectRowIndex}},getMouseoverRowIndex:function(){if(this.mousoverRowIndex){return this.mousoverRowIndex}},unselectRowByIndex:function(a){if(this.rowDatas&&!(a>(this.rowDatas.length-1))){xui.util.Dom.removeClass(this.rowDoms[a],"xui-simpleGrid-row-selected");this._fire("unrowselect",this.rowDatas[a]);this._unRegisterSelectData(a);if(this.checkbox){if($("xui-simpleGrid-"+this.id+"-"+a)){$("xui-simpleGrid-"+this.id+"-"+a).checked=false}}}},cleanRow:function(){for(key in this.rowDoms){this.unselectRowByIndex(key)}if(this.checkbox){this.checkAll.checked=false}},hoverRowByIndex:function(a){this.mousoverRowIndex=a;xui.util.Dom.addClass(this.rowDoms[a],"xui-simpleGrid-row-hover")},unhoverRowByindex:function(a){this.mousoverRowIndex=null;xui.util.Dom.removeClass(this.rowDoms[a],"xui-simpleGrid-row-hover")},setParams:function(a){if(a){for(var b in a){a[b]=encodeURIComponent(a[b])}this.params=a}},_getParamsString:function(){var a="";for(k in this.params){if(k=="defaultSort"){this.defaultSort=this.params[k]}else{if(k=="dir"){this.dir=this.params[k]}else{if(k=="limit"){this.limit=this.params[k]}else{a+="&"+k+"="+this.params[k]+""}}}}return a},toPage:function(a){this.cleanRow();var b=(a-1)*this.limit;if(this.colModelMap[this.defaultSort]){this._dirSwitch(this.defaultSort,this.dir)}this._getData(b);this.currentPage=a},_isHiddenPreButton:function(a){if(this.firstBtnDom){this.firstBtnDom.disabled=a}if(this.prevBtnDom){this.prevBtnDom.disabled=a}if(a){xui.util.Dom.addClass(this.firstBtnDom,"xui-pagebar-btn-first-disabled");xui.util.Dom.addClass(this.prevBtnDom,"xui-pagebar-btn-prev-disabled")}else{xui.util.Dom.removeClass(this.firstBtnDom,"xui-pagebar-btn-first-disabled");xui.util.Dom.addClass(this.firstBtnDom,"xui-pagebar-btn-first");xui.util.Dom.removeClass(this.prevBtnDom,"xui-pagebar-btn-prev-disabled");xui.util.Dom.addClass(this.prevBtnDom,"xui-pagebar-btn-prev")}},_isHiddenNextButton:function(a){if(this.lastBtnDom){this.lastBtnDom.disabled=a}if(this.nextBtnDom){this.nextBtnDom.disabled=a}if(a){xui.util.Dom.addClass(this.lastBtnDom,"xui-pagebar-btn-last-disabled");xui.util.Dom.addClass(this.nextBtnDom,"xui-pagebar-btn-next-disabled")}else{xui.util.Dom.removeClass(this.lastBtnDom,"xui-pagebar-btn-last-disabled");xui.util.Dom.addClass(this.lastBtnDom,"xui-pagebar-btn-last");xui.util.Dom.removeClass(this.nextBtnDom,"xui-pagebar-btn-next-disabled");xui.util.Dom.addClass(this.nextBtnDom,"xui-pagebar-btn-next")}},_rendPageBar:function(){var b=(this.currentPage-1)*this.limit;if(this.isFirstPage(this.currentPage)&&this.isLastPage(this.currentPage)){this._isHiddenPreButton(true);this._isHiddenNextButton(true)}else{if(this.isFirstPage(this.currentPage)){this._isHiddenPreButton(true);this._isHiddenNextButton(false)}else{if(this.isLastPage(this.currentPage)){this._isHiddenNextButton(true);this._isHiddenPreButton(false)}else{this._isHiddenPreButton(false);this._isHiddenNextButton(false)}}}if(this.hasPagebar){if(this.numberBtnDom){this.numberBtnDom.value=this.currentPage}if(this.numberStartDom){if(this.totalCount==0){this.numberStartDom.innerHTML=0}else{this.numberStartDom.innerHTML=b+1}}if(this.numberEndDom){if(this.limit>this.totalCount||(this.isLastPage())){this.numberEndDom.innerHTML=this.totalCount}else{this.numberEndDom.innerHTML=b+this.limit}}if(this.hasPages&&this.numberTotalPages){var a=Math.ceil(this.totalCount/this.limit);this.numberTotalPages.innerHTML=a}}},_initSore:function(){},_getData:function(f){var d="start="+f+"&sort="+(this.defaultSort?this.defaultSort:"")+"&dir="+(this.dir?this.dir:"")+"&limit="+(this.limit?this.limit:"");var a="";if(this.params){a=this._getParamsString()}var c=this;try{xui.util.Ajax.asyncPOST(this.url,d+a,function(g){var e=xui.util.JsonUtil.decode(g);c.store=e;if(e.isSuccess===false){c._fire("dserror",e.message)}else{c._rendData(e)}},function(){c._fire("ajaxerror")})}catch(b){}},_cleanRow:function(a){var c=this.rowDoms.length;for(var b=0;b<c;b++){this.rowDoms[b].style.display=(b>=a)?"none":"block"}},_rendData:function(d){this.rowDatas=d.data;this.totalCount=d.totalCount;if(this.hasPagebar&&this.numberTotalDom){this.numberTotalDom.innerHTML=this.totalCount}var a=this.rowDatas.length;this.dataRowDoms=this.rowDoms;a=(a>this.limit)?this.limit:a;if(this.isRowAuto){this._cleanRow(a)}for(var c=0;c<a;c++){this.setRowData(c,this.rowDatas[c])}if(this.limit>a){this.dataRowDoms=this.rowDoms.slice(0,a);for(var b=a;b<this.limit;b++){this.cleanRowData(b)}}if(this.checkbox){this._addCheckBoxEvent()}if(this.hasPagebar){this._rendPageBar()}var d=this;this._fire("dsload",d);if(a==0){this.toPrevPage();return}},setRowData:function(f,d){for(var e in d){this.rowDatas[f][e]=d[e]}var a=this.idSchema.length;for(var c=0;c<a;c++){var b="";if(this.colModelMap[this.idSchema[c]].dataIndex){b=this.rowDatas[f][this.colModelMap[this.idSchema[c]].dataIndex]}if(this.checkbox&&this.idSchema[c]=="checkbox"){if($("xui-simpleGrid-"+this.id+"-"+f)){b=$("xui-simpleGrid-"+this.id+"-"+f).checked}}this.setCellData(f,this.idSchema[c],b)}},cleanRowData:function(b){if(this.idSchema.length){for(var a=0;a<this.idSchema.length;a++){this.cleanCellData(b,this.idSchema[a])}}else{for(key in this.idSchema){this.cleanCellData(b,this.idSchema[key])}}},setCellData:function(d,b,a){if(this.getCellTextDomByIndex(d,b)){if(this.colModelMap[b].renderer){this.getCellTextDomByIndex(d,b).innerHTML=this.colModelMap[b].renderer(a,this.rowDatas[d],d,this)}else{var c=xui.util.StringUtil.htmlEncode(a);this.getCellTextDomByIndex(d,b).innerHTML=(typeof c=="undefined"||c==null)?"":c;this.getCellTextDomByIndex(d,b).title=a}if(this.colModelMap[b].align){this.getCellTextDomByIndex(d,b).style.textAlign=this.colModelMap[b].align}}},cleanCellData:function(b,a){if(this.getCellTextDomByIndex(b,a)){this.getCellTextDomByIndex(b,a).innerHTML=""}},getStore:function(){return this.store},getRowDomByIndex:function(a){return this.rowDoms[a]},getCellDomByIndex:function(d,c){var b=this.getRowDomByIndex(d);var a=xui.util.Dom.getElementsByClassName("xui-simpleGrid-col-"+c,"td",b)[0];return a},getCellTextDomByIndex:function(d,b){var a=this.getCellDomByIndex(d,b);var c=xui.util.Dom.getFirstChildBy(a);return c},getSelect:function(){var b=[];var a=0;for(key in this.selectDatas){b[a]=this.selectDatas[key];a++}return b},_registerSelectData:function(a){this.selectDatas["rowIndex"+a]=this.rowDatas[a]},_unRegisterSelectData:function(a){delete this.selectDatas["rowIndex"+a]},_tranData:function(){},toNextPage:function(b){var a=xui.util.NumberUtil.add(this.currentPage,1,"+");this.toPage(a)},toPrevPage:function(){var a=xui.util.NumberUtil.add(this.currentPage,1,"-");if(a<1){return}this.toPage(a)},toFirstPage:function(){this.toPage(1)},toLastPage:function(){var a=Math.ceil(this.totalCount/this.limit);this.toPage(a)},toNumberBtnPage:function(){var a=xui.util.StringUtil.trim(this.numberBtnDom.value);var b=Math.ceil(this.totalCount/this.limit);if(xui.util.Validator.isInt(a)&&a>0&&a<=b){this.toPage(a)}},isFirstPage:function(){return(this.currentPage==1)?true:false},isLastPage:function(){var a=Math.ceil(this.totalCount/this.limit);a=(a==0)?1:a;return(this.currentPage==a)?true:false},getCurrentPage:function(){return this.currentPage},getTotalCount:function(){return this.totalCount},getPageCount:function(){return(this.rowDatas)?(this.rowDatas.length):0}};