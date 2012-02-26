FD.widget.Tab = function(container,config){
	this.init(container,config);
}

FD.widget.Tab.defConfig = {
	isAutoPlay: true,			
	timeDelay:	3,				
	eventType:  'mouse',		
	showType:	'block',		
	currentClass:	'current',	
	tabTitleClass:	'f-tab-t',	
	tabBoxClass:	'f-tab-b',	
	startItem:	0				
}

FD.widget.Tab.prototype = {
	init: function(container,config){
		this.container = $(container);
		this.config = FD.common.applyIf(config||{}, FD.widget.Tab.defConfig);
		this.tabTitles = FD.common.toArray($D.getElementsByClassName(this.config.tabTitleClass,'*',this.container));
		this.tabBoxs =  FD.common.toArray($D.getElementsByClassName(this.config.tabBoxClass,'*',this.container));
		if(this.tabTitles.length == 0 || this.tabTitles.length != this.tabBoxs.length) return;
		
		this.pause = false;
		this.delayTimeId = null;
		this.autoPlayTimeId = null;
		
		$D.setStyle(this.tabBoxs,'display','none');
		$D.removeClass(this.tabTitles,this.config.currentClass);
		this.setTab(this.config.startItem,false);
		
		$E.on(this.tabBoxs, 'mouseover', function(){ this.pause = true; }, this, true);
		$E.on(this.tabBoxs, 'mouseout', function(){ this.pause = false; }, this, true);
		if (this.config.eventType == 'mouse') {
			$E.on(this.tabTitles, 'mouseover' ,this.mouseHandler, this, true);
			$E.on(this.tabTitles, 'mouseout' ,function(){
				clearTimeout(this.delayTimeId);
				this.pause = false;
			}, this, true);		
		}else{
			$E.on(this.tabTitles, 'click' ,this.clickHandler, this, true);
		}
		
		if(this.config.isAutoPlay) this.autoPlay();
		
	},

	clickHandler: function(e){
		var t = $E.getTarget(e);
		var idx = this.tabTitles.indexOf(t);
		this.setTab(idx,'true');
	},

	 mouseHandler: function(e){
	 	var t = $E.getTarget(e);
	 	var idx = this.tabTitles.indexOf(t);
	 	var self = this;
	 	this.delayTimeId = setTimeout(function(){
	 		self.pause = true;
	 		self.setTab(idx,'true');
	 	},.1*1000)
	 },

	 setTab: function(idx, flag){
		if (idx == this.curId) return;	
		var curId = this.curId >= 0 ? this.curId : 0;
		if (flag && this.autoPlayTimeId) clearTimeout(this.autoPlayTimeId);
		$D.removeClass(this.tabTitles[curId],this.config.currentClass);
		$D.setStyle(this.tabBoxs[curId],'display','none');
		$D.addClass(this.tabTitles[idx],this.config.currentClass);
		$D.setStyle(this.tabBoxs[idx],'display',this.config.showType);	
		this.curId = idx;
		if (flag && this.config.isAutoPlay)	this.autoPlay();
	},

	autoPlay: function(){
		var curId = this.curId >= 0 ? this.curId : 0;
		var self = this;
		this.autoPlayTimeId = setTimeout(function(){
			if ( !self.pause ) {
				var n = curId + 1;
				if( n == self.tabTitles.length ) n=0;		
				self.setTab(n, false);
			}
			self.autoPlay();		
		}, this.config.timeDelay * 1000);
	}
}

FD.widget.Tab.init = function(container, config) {
	return new FD.widget.Tab(container, config);
};
