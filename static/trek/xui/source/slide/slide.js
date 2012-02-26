/*
 * Slide   
 * @namespace xui.widgets
 * @class Slide
 * @constructor
 * @param {Object} 配置参数如下
 * 	container 		容器的element
 * 	content   		内容的element
 *	panelCl			需要切换的内容的classname
 *	triggerCl		切换内容触点的classname
 *	currentTriggerCl 当前触点的classname
 *	autoplay		是否自动轮播
 *	interval		轮播内容时间间隔
 *	duration		轮播动画的持续时间
 *	currentIndex 	当前的内容的index
 *	onSwitchTriggerBefores 切换触点前需要触发的事件
 */
xui.widgets.Slide = (function(){

var DOM  = xui.util.Dom,
    EV   = xui.util.Event,
	CUSTOMEV = xui.util.CustomEvent,
    ANIM = xui.util.Anim;
	
	

var EMPTY = '',
    SELF = null;

var isBackward = false,
    isCritical= false;

var FN = function(attrs){
    this.attrs ={
        container: $('Focus'),
        content: $('Focus_content'),
		panelCl:'circularPanel',
		triggerCl :'circularTrigger',
		currentTriggerCl:'on',
        autoplay:true,
        interval:5,
        duration :0.5,
        currentIndex:0,
		onSwitchTriggerBefore:function(){}
    };
    this.attrs = $merge(this.attrs,attrs);

    SELF = this;

    this._events = [];
    this._initEnvents('onSwitched');
    this._initEnvents('onSwitchTriggerBefore');


    this.container = this.attrs.container;
    this.content = this.attrs.content;



    this.panels = DOM.getElementsByClassName(this.attrs.panelCl);
    this.triggers = DOM.getElementsByClassName(this.attrs.triggerCl);

    this.currentIndex = this.attrs.currentIndex;

    this.length = this.panels.length;
    this.viewDiff = this.panels[0].offsetWidth;

	this._timer = function(){};

    this.paused = false;
	this._bind();
	this._switchTo(this.currentIndex);
    this.run();
	
};

FN.prototype = {
    _circularScroll:function(index){
        var  from = this.currentIndex,
             to = index,
             isBackward = false;
		
        this.diff = -this.viewDiff*to;
        isCritical =  (isBackward && from === 0 && to === this.length - 1)  ||
                      (isBackward === false && from  === this.length - 1 && to === 0);

        if(isCritical){
            this.diff = this._adjustPosition(from,to,isBackward);
        }

        if(SELF.anim)SELF.anim.stop();
	
        SELF.anim = new ANIM(this.content, { left: {to: this.diff } },this.attrs.duration);
        SELF.anim.onComplete.subscribe(function(){
            if(isCritical)SELF._restPosition();
            SELF._fire('onSwitched');
        });

        SELF.anim.animate();
    },
    _adjustPosition:function(from,to,isBackward){
        var direction = isBackward?this.length-1:0,
            from = direction,
            to = direction +1,
            i;
        for(i = from;i<to;i++){
            DOM.setStyle(this.panels[i],'position','relative');
            DOM.setStyle(this.panels[i],'left',((isBackward ? -1 : 1) * this.viewDiff * this.length)+'px');
        }
        return isBackward ? this.viewDiff : -this.viewDiff * this.length;
    },
    _restPosition:function(){
        var direction = isBackward?this.length-1:0,
            from = direction,
            to = direction +1,
            i;
        for(i = from;i<to;i++){
            DOM.setStyle(this.panels[i],'position',EMPTY);
            DOM.setStyle(this.panels[i],'left',EMPTY);
        }
        DOM.setStyle(this.content, 'left', isBackward ? -this.viewDiff * (this.length - 1) : EMPTY);
    },
	/* 运行自动轮播 */
    run:function(){
        if(!this.attrs.autoplay)return;
        if(this.panels.length===1)return;
        this._timer = setInterval(function(){
            if(SELF.paused)return;
            SELF._switchTo(SELF.currentIndex < SELF.length - 1 ? SELF.currentIndex + 1 : 0);
        },this.attrs.interval*1000);
    },
	/* 停止自动轮播 */
	stop:function(){
		clearInterval(this._timer);
	},
    _switchTo:function(index){        
        if(index !== this.currentIndex){
            if(this.currentIndex<this.length)this.currentIndex = this.currentIndex+1;
            if(this.currentIndex===this.length)this.currentIndex = 0;
        }
		this._switchTrigger(index);
		this._circularScroll(index);
    },
	/* 切换到某页 */
    go:function(index){
        this.stop();
        if(isCritical)SELF._restPosition();
        this._switchTo(this.currentIndex=index);
        var paused = function(){
            SELF.paused = false;
            this._removeEvent('onSwitched',paused);
        }
        this.on('onSwitched',paused);
    },
	/* 切换到上一页 */
    prev:function(){
        this.stop();
        if(isCritical)SELF._restPosition();
        this._switchTo(this.currentIndex=(this.currentIndex > 0 ? this.currentIndex - 1 : this.length - 1));
        var paused = function(){
            SELF.paused = false;
            this._removeEvent('onSwitched',paused);
        }
        this.on('onSwitched',paused);
    },
	/* 切换到下一页 */
    next:function(){
        this.stop();
        if(isCritical)SELF._restPosition();
        this._switchTo(this.currentIndex=(this.currentIndex < this.length - 1 ? this.currentIndex + 1 : 0));
        var paused = function(){
            SELF.paused = false;
            this._removeEvent('onSwitched',paused)
        }
        this.on('onSwitched',paused);
    },
	/* 播放 */
	play:function(){
		this.paused = false;
	},
	/* 暂停播放 */
    suspend:function(){
        this.paused = true;
    },
    _switchTrigger:function(){
        this._fire('onSwitchTriggerBefore',[this.currentIndex,this.triggers[this.currentIndex]]);
		var el = this.triggers[this.currentIndex];
		$each(this.triggers,function(item){
			if(item == el){
				DOM.addClass(item,SELF.attrs.currentTriggerCl);
			}else{
				DOM.removeClass(item,SELF.attrs.currentTriggerCl);
			}			
		})	
    },
    _bind:function(){
        var self = this;
        var check = function(event,el,type){
            var related = event.relatedTarget || {mouseover:event.fromElement,mouseout:event.toElement}[type];
            if (related == undefined) return true;
            if (related === false) return false;
            return ($type(el) != 'document' && related != el && related.prefix != 'xul' && (DOM.getElementsBy(function(e){if(e==related)return true;},'*',el).length == 0) );
        };
        EV.on(this.container, 'mouseover', function(e) {
            e = EV.getEvent(e);
            if(check(e,this,'mouseover')){
                self.paused = true;
            }
        });
        EV.on(this.container, 'mouseout', function(e) {
            e = EV.getEvent(e);
            if(check(e,this,'mouseout')){
             	SELF.play();   
            }
        });
        EV.on(this.triggers,'mouseover',function(){
            for(var i = 0 ;i<SELF.triggers.length;i++){
                if(SELF.triggers[i]===this)SELF.currentIndex = i;
            }
            SELF._switchTo(SELF.currentIndex);
            SELF.paused = true;
        });
        EV.on(this.triggers,'mouseout',function(){
            SELF.paused = false;
        });
		this.on('onSwitchTriggerBefore',this.attrs.onSwitchTriggerBefore);
		
    },
    on:function(evemtName,func){
        var o = this;
        this._events[evemtName].subscribe(func, o);
    },
    _initEnvents:function(evemtName) {
        var o = this;
        this._events[evemtName] = new CUSTOMEV(evemtName, o);
    },
    _fire:function(evemtName,arg){
        this._events[evemtName].fire.apply(this._events[evemtName],arg);	
    },
    _removeEvent:function(evemtName,func){
        var o = this;
        this._events[evemtName].unsubscribe(func,o)
    }

};
return FN;
})();
