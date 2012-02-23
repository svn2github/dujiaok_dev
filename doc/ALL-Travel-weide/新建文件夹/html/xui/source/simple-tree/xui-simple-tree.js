/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2009, alibaba.com All rights reserved.
 * @version 1.1
 * @author shengding
 * @date 2009-11-9
 * @requires core/xui-core.js
 * @requires util/xui-ajax.js
 */


/**
 * SimpleTreeNode  树节点类
 * @namespace xui.widgets
 * @class SimpleTreeNode
 * @constructor
 * @param {Object} attrs  配置属性
 */
xui.widgets.SimpleTreeNode = function (attributes){
	this.attributes = attributes;
    if(typeof attributes == "string"){
        this.attributes = {text: attributes};
    }
	this.text = this.attributes.text;
	this.leaf = this.attributes.leaf;
	this.id = this.attributes.id;
	this.iconCls = '';
	if(this.attributes.iconCls)
		this.iconCls =  this.attributes.iconCls;
	
	this.childNodesHash = {};
	this.parentNode = null ;
	
	this.isSelected = false ;
	this.dataUrl;
	this.ownerTree;
	
	/**绑定该节点点击事件
	*/
	var obj = this;
	var id = this.id;
	xui.util.Event.on(id,'click',function(){		
							obj.clickNode();
				});

}
xui.widgets.SimpleTreeNode.prototype = {
	_hasClass : function(dom,className){
        return className && (' '+dom.className+' ').indexOf(' '+className+' ') != -1;
    },
	_addDomClass :function(dom,className){
		 if(className && !this._hasClass(dom,className))
			dom.className = dom.className + ' ' + className;
	},
	_removeDomClass:function(dom,className){
		if(className && this._hasClass(dom,className))
			dom.className = dom.className.replace(className, ' ');
	},
	/**
     * 设置结节的样式
	 * @param {String} className 样式类名
     * @return {void}
     */
	setStyle : function(className){
		var o = document.getElementById(this.id);
		this._addDomClass(o.childNodes[2],className);	
	},
	removeStyle:function(className){
		var o = document.getElementById(this.id);
		this._removeDomClass(o.childNodes[2],className);	
	},

	_getData:function(p){
		var dataUrl = this.getOwnerTree().dataUrl;
		if(dataUrl.indexOf('?')>-1){
			 dataUrl+=  '&node='+this.id;
		 }else{
			 dataUrl+=  '?node='+this.id;
		 }

		var s = xui.util.Ajax.syncGet(dataUrl);
		var cn =  eval("("+s+')');
		var n = [];
		if(cn){
			var l = cn.length;			
			for(var i =0;i<l;i++){				
				n[i]= new xui.widgets.SimpleTreeNode(cn[i]);				
			}
		}
		return n;
		/*var obj= this;
		Ajax.asyncPOST(this.dataUrl,p,function(o){
				var cn =  eval("("+o+')');
				var l = cn.length;
				var n = [];
				for(var i =0;i<l;i++){
					n[i]= new alisoft.xui.XTreeNode(cn[i]);
					
				}
				obj.setChildNodes(n);
				obj.renderChildren();
			});*/
	},
	
	/**
     * 设置结节的显示
	 * @param {String} str 显示值
     * @return {void}
     */
	setText	: function(str){
		this.text = str;
		xui.util.Dom.getElementsByClassName('xui-simpleTree-node-text','span',this.nodeDom)[0].innerHTML =xui.util.StringUtil.htmlEncode(str);
	},
	/**
     * 找到该节点所属的树
     * @return {Tree}
     */
	getOwnerTree : function(){
        // if it doesn't have one, look for one
        if(!this.ownerTree){
            var p = this;
            while(p){
                if(p.ownerTree){
                    this.ownerTree = p.ownerTree;
                    break;
                }
                p = p.parentNode;
            }
        }
        return this.ownerTree;
    },
	/**
	* 展现该节点
	*/
	render:function(){
		var n = this._getData();
		this._setChildNodes(n);
		this._renderChildren();
		this.hasExpanded = true;
	},
	_registerNode : function(node){
        this.childNodesHash[node.id] = node;
    },
    // private
    _unregisterNode : function(node){
        delete this.childNodesHash[node.id];
    },
	_setChildNodes:function(childNodes){
		for(var i =0;i<childNodes.length;i++){
			this._registerNode(childNodes[i]);
		}
		//this.childNodes = childNodes;
	},
	_renderChildren:function(){
		var obj = this;
		var	o =  document.getElementById(this.id);
		var doms = [];
		for(key in this.childNodesHash){
			this.childNodesHash[key].parentNode = this;
			this.getOwnerTree()._registerNode(this.childNodesHash[key]);
			o.nextSibling.appendChild(this.childNodesHash[key].setNodeDom());
		}
				
		//o.nextSibling.innerHTML = h.join('');
		//因为事件需要在dom对象产生之后才能绑定，虽然yui支持后绑定，但是不支持js中dom后产生的绑定
		/*for(var j=0;j<l;j++){
			.
			oc = obj.childNodes[j] 
			xui.util.Event.on(oc.id,'click',function(){
				var nodeId= arguments[1];
				obj.getChildNodeById(nodeId).clickNode();
			},oc.id);
		}*/	
	},
	getChildNodeById:function(id){
		return this.childNodesHash[id];
		
	},
	/**
     * 在该结节上加载子结节
	 * @param {XTreeNode} node 加载的结节
     * @return {void}
     */
	appendChild :function(node){
		var obj = this;
		node.parentNode = obj;
		node.parentNode.leaf = false;
		node.parentNode._registerNode(node);
		//node.ownerTree = this.getOwnerTree();
		//返回节点内容的dom，并不是节点的整体dom，整体dom还包括他的子节点dom
		var	o =  document.getElementById(this.id);
		var nodeDom = node.setNodeDom();
		this.getOwnerTree()._registerNode(node);
		//子节点的容器
		var childrenNodesContain = xui.util.Dom.getNextSibling(o);
		var firstChildNodeDom =  xui.util.Dom.getFirstChild(childrenNodesContain);
		//console.log(firstChildNodeDom)
		//如果存在子节点
		if(firstChildNodeDom){
			xui.util.Dom.insertBefore(nodeDom,firstChildNodeDom);
		}
		//如果展开过，加如一个节点，否则就什么都不做
		else if(this.hasExpanded){
			childrenNodesContain.appendChild(nodeDom);
		}
		
		//console.log(this.childNodesHash)
		this.setLeafStyle();
	},
	/**
     * 把删除掉该结节
     * @return {void}
     */
	remove :function(){
		this.getOwnerTree()._unregisterNode(this);
		this.parentNode.removeChild(this);
	},
	/**
     * 删除子结节
	 * @param {XTreeNode} node 要删除的结节
     * @return {void}
     */
	removeChild:function(node){
		 this.getOwnerTree().getSelectionModel().unselect(node);
		 node.parentNode._unregisterNode(node);
		 node.parentNode.setLeafStyle();
		 if(!this.hasChildren())
			node.parentNode.leaf = true;
		 var dom =node.getNodeDom();
		 if(dom)
		 	dom.parentNode.removeChild(dom);
		 

	},
	/**
	*点击节点
	*/
	clickNode:function(){
		var o = document.getElementById(this.id);
		this.select();
		if(!this.leaf){
			if(o.nextSibling.style.display =='none'){
				this.expand();
			}else{
				this.collapse();
			}
		}
	},
	/**
     * 插入结节
	 * @param {XTreeNode} node  要插入的结节的前个结节
	 * @param {XTreeNode} inserNode 要插入的结节
     * @return {void}
     */
	insertBefore:function(node,inserNode){
	
	},
	/**
     * 收拢该结节
     * @return {void}
     */
	collapse :function(){
		var o = document.getElementById(this.id);
		o.nextSibling.style.display ='none';
		
		this._removeDomClass(o,'xui-simpleTree-node-expanded');		
		this._addDomClass(o,'xui-simpleTree-node-collapsed');
		
		this._removeDomClass(o.childNodes[1],'xui-simpleTree-elbow-end-minus');		
		this._addDomClass(o.childNodes[1],'xui-simpleTree-elbow-end-plus');		
	},
	/**
     * 收拢该结节，包括所有子结点
     * @return {void}
     */
	collapseChildNodes:function(){
	
	},
	/**
     * 展开该节点
     * @return {void}
     */
	expand :function(){
		var o = document.getElementById(this.id);
		o.nextSibling.style.display ='';
		
		this._removeDomClass(o,'xui-simpleTree-node-collapsed');		
		this._addDomClass(o,'xui-simpleTree-node-expanded');
		
		this._removeDomClass(o.childNodes[1],'xui-simpleTree-elbow-end-plus');		
		this._addDomClass(o.childNodes[1],'xui-simpleTree-elbow-end-minus');
		//是否已经咱开过，可以扩展为是否需要缓存数据
		if(!this.hasExpanded)
			this.render();
		
	},
	/**
     * 展开该节点，包括所有子结点
     * @return {void}
     */
	expandChildNodes:function(){
		
	},
	
	/**
     * 是否是叶子结点
     * @return {Booean}
     */
	leaf:function(){
		return  this.leaf;
	},
	hasChildren:function(){
		var i = 0;
		for(key in this.childNodesHash){
			i++
		}
		return (i>0)?true:false;
	},
	//设置是否是子节点时候的样式
	setLeafStyle:function(){
		var o =  xui.util.Dom.getElementsByClassName('xui-simpleTree-node-el','div',this.nodeDom)[0];
		var o2 =  xui.util.Dom.getElementsByClassName('xui-simpleTree-ec-icon','img',this.nodeDom)[0];
		//如果没有子节点
		if(!this.hasChildren()){
			this._removeDomClass(o,'xui-simpleTree-node-collapsed');
			this._removeDomClass(o,'xui-simpleTree-node-expanded');
			this._addDomClass(o,'xui-simpleTree-node-leaf');
			
			this._removeDomClass(o2,'xui-simpleTree-elbow-end-plus');
			this._removeDomClass(o2,'xui-simpleTree-elbow-end-minus');
			this._addDomClass(o2,'xui-simpleTree-elbow');
		}else{
			this._removeDomClass(o,'xui-simpleTree-node-leaf');
			this._addDomClass(o,'xui-simpleTree-node-collapsed');
			
			this._removeDomClass(o2,'xui-simpleTree-elbow');
			this._addDomClass(o2,'xui-simpleTree-end-minus');
		}

	},
	/**
     * 选中该结点
     * @return {void}
     */
	select:function(){
		this.getOwnerTree().getSelectionModel().select(this);
		this.isSelected = true;
	},
    unselect : function(){
        this.getOwnerTree().getSelectionModel().unselect(this);
		this.isSelected = false;
    },
	
	uiSelect:function(){
		var o = document.getElementById(this.id);
		this._addDomClass(o,'xui-simpleTree-selected');	
	},
	uiUnSelect:function(){
		var o = document.getElementById(this.id);
		this._removeDomClass(o,'xui-simpleTree-selected');	
	},

	/**
     *返回该结点深度
     * @return {Number}
     */
    getDepth : function(){
        var depth = 0;
        var p = this;
        while(p.parentNode){
            ++depth;
            p = p.parentNode;
        }
        return depth;
    },
	getNodeDom:function(){
		return this.nodeDom;
	},
	/**返回节点的dom对象
	*/
	setNodeDom:function(){
		var imgH = '<IMG class=xui-simpleTree-icon src="'+xui.BLANK_IMAGE_URL+'"  aliNodeId='+this.id+'>' ;
		var imgEL = '<IMG class=xui-simpleTree-elbow-line src="'+xui.BLANK_IMAGE_URL+'"  aliNodeId='+this.id+'>' ;
		
		this.nodeDom = document.createElement('li');
		xui.util.Dom.addClass(this.nodeDom,'xui-simpleTree-node');

		var h = [];
		 h[0]='';
		 	//跟据是否是叶子节点，加载文件夹还是叶子图片
		 	if(this.leaf){
         		h[1]='<DIV id='+this.id+' aliNodeId='+this.id+' class="xui-simpleTree-node-el  xui-simpleTree-unselectable xui-simpleTree-node-leaf"  unselectable="on">';
			}else{
         		h[1]='<DIV id='+this.id+' aliNodeId='+this.id+' class="xui-simpleTree-node-el  xui-simpleTree-unselectable xui-simpleTree-node-collapsed"  unselectable="on">';
			}
			
		 		h[2]='<SPAN class=xui-simpleTree-node-indent aliNodeId='+this.id+'>';
				//根据层次定位往前空的位置	
					var depth =this.getDepth();
					h[3]='';
					if(depth){
						if(depth==1){
							h[3] = imgH;
						}else{						
							for(var i=0;i<(depth-1);i++){
								h[3]= h[3]+imgH;
							}
							h[3]=h[3]+imgEL;
						}										        				
					}else{
						h[3]='';
					}
					
				h[4]='</SPAN>';
				//跟据是否是叶子节点，加载前面加号，减号，或者没有，非叶子默认开始是加号
				if(this.leaf){
					h[5]='<IMG aliNodeId='+this.id+' class="xui-simpleTree-ec-icon xui-simpleTree-elbow" src="'+xui.BLANK_IMAGE_URL+'">';
				}else{
					h[5]='<IMG aliNodeId='+this.id+' class="xui-simpleTree-ec-icon xui-simpleTree-elbow-end-plus" src="'+xui.BLANK_IMAGE_URL+'">';
				}
				//自定义的样式
				h[6]='<IMG aliNodeId='+this.id+' class="xui-simpleTree-node-icon '+ this.iconCls+'" src="'+xui.BLANK_IMAGE_URL+'" unselectable="on">';
				if(this.getOwnerTree().renderer){
					var rendererHtml = this.getOwnerTree().renderer(this.id,this.text);
					h[7]= '<A aliNodeId='+this.id+' class=xui-simpleTree-node-anchor hideFocus tabIndex=1><SPAN class="xui-simpleTree-node-text" aliNodeId='+this.id+' unselectable="on">'+rendererHtml+'</SPAN></A>';
				}else{
					h[7]= '<A aliNodeId='+this.id+' class=xui-simpleTree-node-anchor hideFocus tabIndex=1><SPAN class="xui-simpleTree-node-text" aliNodeId='+this.id+' unselectable="on">'+this.text+'</SPAN></A>';
				}
				
             h[8]='</DIV>';
			 h[9]='<UL class=xui-simpleTree-node-ct  style="display:none">';
			 
			 h[10]='</UL>';
		h[11]='</LI>';
		this.nodeDom.innerHTML = h.join('');
		return this.nodeDom;
	},	/**
     *返回自定义的属性
     * @param {String} key  自定义属性的名称
     * @return {String}
     */
	getAttributesByKey:function(key){
		var v = eval('this.attributes.'+key+'');
		if(v)
			return v;
	}
	
}

/**
 * @class xui.widgets.SimpleTree
 * id 存放树的DIV 的id
 * attributes 属性类，可加自定义属性
 * leafStyle 所有叶子结点的样式，有默认的样式和图标，这里可设置，结点也可设置，级别依次提高
 * nonLeafCloStyleClo 所有非叶子结点的关闭时的样式
 * nonLeafOpenStyle 所有非叶子结点的打开的样式
 * dataUrl 数据的URL
 * root 根结节
*/
xui.widgets.SimpleTree = function(id,attributes){
	if(attributes){
		this.attributes = attributes;	
		if(this.attributes.dataUrl)
			this.dataUrl = this.attributes.dataUrl;	
		if(this.attributes.leafStyle)
			this.leafStyle = this.attributes.leafStyle;
		if(this.attributes.nonLeafOpenStyle)
			this.nonLeafOpenStyle = this.attributes.nonLeafOpenStyle;
		if(this.attributes.nonLeafCloStyleClo)
			this.nonLeafCloStyleClo = this.attributes.nonLeafCloStyleClo;
	}
	
	this.root;
	this.id = id;
	this.container = $(id);

	this.dataSource;
	this.selModel;
	this.nodeHash = {};
	this._events = {};
};
xui.widgets.SimpleTree.prototype = {

	/**
     *设置根结点
	 * @param {XTreeNode} node  根结点
     * @return {void}
     */
	setRootNode:function(node){
		this.root = node;
		node.ownerTree = this;
		this._registerNode(node);
	},
	/**
     *获取根结点
     * @return {XTreeNode}
     */
    getRootNode : function(){
        return this.root;
    },
	/**
     *设置叶子结点的样式，
	 * @param {String} className  样式
     * @return {void}
     */
	setLeafStyle:function(className){
		this.leafStyle = className;
	},
	/**
     *设置非叶子结点的打开的样式，
	 * @param {String} className  样式
     * @return {void}
     */
	/*setNonLeafOpenStyle:function(className){
		this.nonLeafOpenStyle = className;
	},*/
	/**
     *设置非叶子结点的关闭的样式，
	 * @param {String} className  样式
     * @return {void}
     */
	/*setNonLeafCloStyle:function(className){
		this.nonLeafCloStyleClo = className;
	},*/
	/**
     *返回选中的结点
     * @return {Array}
     */
	getSelectedNode:function(){
		return this.getSelectionModel().getSelectedNode();
	},
    getSelectionModel : function(){
        if(!this.selModel){
           this.selModel = new xui.widgets.SimpleTreeSelectionModel();
        }
        return this.selModel;
    },
	
	_getContainerWidth:function(){
		if(this.container.style.width){
			return this.container.style.width;
		}else{
			var theStyleObj =this.container.currentStyle?this.container.currentStyle:document.defaultView.getComputedStyle(this.container, null);
			if(theStyleObj.width){
				return theStyleObj.width;
			}else{
				return '';
			}
		}

	},
	_getContainerHeight:function(){
		if(this.container.style.height){
			return this.container.style.height;
		}else{
			var theStyleObj =this.container.currentStyle?this.container.currentStyle:document.defaultView.getComputedStyle(this.container, null);
			if(theStyleObj.height){
				return theStyleObj.height;
			}else{
				return '';
			}
		}
	},
	_renderHeader:function(){
		var h = [];
		h[0] = '<DIV class=x-panel-bwrap>';
			h[1]= '<DIV class="xui-panel-body xui-panel-body-noheader"  style="OVERFLOW: auto;WIDTH: '+this._getContainerWidth()+'; HEIGHT:'+this._getContainerHeight()+'; ">';
				h[2]='<UL class="xui-simpleTree-root-ct xui-simpleTree-lines">';	
					h[3]='<DIV class=xui-simpleTree-root-node id="ali-root-node">';
					
					h[4]='</DIV>';
				h[5]='</UL>';
			h[6]='</DIV>';	
		h[7]='</DIV>';
		this.container.innerHTML = h.join('');	

	},
	/**
	 * 初始化tree
	 * 
	 * @param {String} url 请求数据地址
	 * @param {String} rootText 跟节点显示的内容
	 * @param {Function} renderer 回调自定义显示
	 */
	init:function(url,rootText,renderer){
		this.renderer = renderer;
		this._initEnvents('click');
		this._initEnvents('dblclick');
		var obj = this;
		xui.util.Event.on(this.container,'click',function(){										  
					obj._fire('click',obj.selModel.getSelectedNode());
		});
		xui.util.Event.on(this.container,'dblclick',function(){										  
					obj._fire('dblclick',obj.selModel.getSelectedNode());
		});
		this._initEnvents();
		this.dataUrl = url;
		this._renderHeader();
		this.root = new xui.widgets.SimpleTreeNode({"text":rootText,"leaf":false,"id":"-1"});
		this.setRootNode(this.root);
		//var h = this.root.getNodeHTML();
		var nodeDom = this.root.setNodeDom();
		xui.util.Dom.getElementsByClassName('xui-simpleTree-root-node','div',this.id)[0].appendChild(nodeDom);
		
		//$('ali-root-node').innerHTML = h;
		this.root.expand();
		var rootEcicon = xui.util.Dom.getElementsByClassName('xui-simpleTree-ec-icon','img',this.id)[0];
		xui.util.Dom.addClass(rootEcicon,'xui-simpleTree-ec-icon-root');
		this.getSelectionModel().init(this);
				
	},
	_registerNode : function(node){
        this.nodeHash[node.id] = node;
    },

    // private
    _unregisterNode : function(node){
        delete this.nodeHash[node.id];
    },    
	/**
     * 根据ID，得到这个树的节点
     * @param {String} id
     * @return {Node}
     */
    getNodeById : function(id){
        return this.nodeHash[id];
    },
	/**
	 * tree绑定事件
	 * @param {String} eventName
	 * @param {Function } func
	 */
	on:function(evemtName,func){
		this._events[evemtName].subscribe(func, this);
	},	
	_initEnvents:function(evemtName) {
		this._events[evemtName] = new YAHOO.util.CustomEvent(evemtName, this);
	},
	_fire:function(evemtName,arg){
		this._events[evemtName].fire(arg);
	}
}

xui.widgets.SimpleTreeSelectionModel = function(){
	this.selNode;
};
xui.widgets.SimpleTreeSelectionModel.prototype = {
	init:function(tree){
		this.tree = tree;
	},
	select : function(node){
		var last = this.selNode;
		if(last != node ){
            if(last){
                last.uiUnSelect();
            }
			node.uiSelect();
			this.selNode = node;
		}
		return node;
	},
	unselect:function(node){
        if(this.selNode == node){
            this.clearSelections();
        }    
	},
	clearSelections : function(){
        var n = this.selNode;
        if(n){
            n.uiUnSelect();
            this.selNode = null;
        }
        return n;
    },
	getSelectedNode : function(){
        return this.selNode;    
    }


}