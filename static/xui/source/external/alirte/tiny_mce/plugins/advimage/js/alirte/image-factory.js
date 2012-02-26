(function(){

	if(typeof(ImageFactory)!='undefined') return;

	ImageFactory = function(config){

		var defConfig = {
			tabContainer: 'tab1',
			allow_all: false,
			is_ali_only: false,
			white_list: ['*.aliui.com', '*.aliued.com'],
			compressSize:	3*1024*1024,	//插入本地图片-需要进行压缩的图片大小
			sizeLimitEach:	200*1024,		//插入本地图片-压缩后运行上传的图片大小
			fileTypes:		'',				//插入本地图片-上传图片的类型
			fileCountLimit:	10,				//插入本地图片-一次上传图片的数量
			//上传地址
			uploadUrl:		'http://10.20.131.8:8080/aranda.upload/web/fileUpload',
			//验证地址
			checkUrl:		'http://blog-dev.china.alibaba.com/misc/arandaHttpCheck.htm',
			//图片前缀
			imgPrefix:		'http://10.20.131.8:8080/aranda.file/blog/',
			uploadBtnSkin:	'http://img.china.alibaba.com/images/cn/market/danai/090210/uploadBtn.png',
			waterMark:		''
		};

		var config = FD.common.applyIf(config || {}, defConfig);

		this.tab = FD.widget.Tab.init(config.tabContainer,{
			eventType:	'click',
			isAutoPlay: false
		});

		this.config = config;
		this.id = ImageFactory.instances.length;
		ImageFactory.instances[this.id] = this;

		//
		if(swfobject.hasFlashPlayerVersion("10"))
		{
			this.initFlashUploader();
		} else {
			//js方式上传
      this.initHTMLUploader();
		}
		this.initWeb();
	}

	ImageFactory.instances = [];

	ImageFactory.init = function(config)
	{
		return new ImageFactory(config);
	}

	ImageFactory.uploadSuccessCheck = function(swfId, data)
	{
		try{ var obj = YAHOO.lang.JSON.parse(data); }
		catch(e){}
		return obj.success == true;
	}

	ImageFactory.cancelImageLinkHandler = function(linkEl)
	{
		var linkId = linkEl.id;
		var arr = linkId.split("-");
		//alert(arr);
		var imgFct = ImageFactory.instances[arr[1]];
		if(imgFct && arr.length)
		{
			imgFct.removeImage(arr[2],arr[3],linkEl);
		}
		return false;
	}

	ImageFactory.prototype = {

		imagePool: {},

		addImage: function(url, type, filename)
		{
			//log('add image:'+url+' from '+type);

			var pool = this.imagePool[type];
			if(typeof(pool)=='undefined')
			{
				pool = this.imagePool[type] = [];
			}
			pool.push({url:url, filename:filename});

			var el = FYD.get(type+'-imgcontainer');
			if(!el) return null;

      var count = (el.children || el.childNodes).length;
			//添加一个图片，包括上传的和网络的
			var image = new Image();
      var thumbw = 100, id=pool.length-1;
			FYE.on(image, 'load', function(evt, obj){
				var w = image.width || image.offsetWidth;
				var h = image.height || image.offsetHeight;
				this.imagePool[obj.type][obj.id]['width'] = w;
				this.imagePool[obj.type][obj.id]['height'] = h;
				setImgSize(image,thumbw-10,thumbw-10); 
				setTimeout(function(){image.style.visibility='visible';}, 100);
				image.style.marginTop = (thumbw-(image.height))/2+'px';

			}, {id:pool.length-1, type:type}, this);
			image.src = url;
			if(filename) image.title = image.alt = filename;
			image.style.visibility = 'hidden';

      var link = mkEl('a', {
        innerHTML : '取消',
        href: '#cancel-image',
        id: 'rmlink-'+this.id+'-'+type+'-'+count,
			  onclick : function(){
				  ImageFactory.cancelImageLinkHandler(this);
				  return false;
			  }
      });
      el.style.width = ((count+1)*(thumbw+10)+5)+'px';

      var pEl = mkEl('li');

			var divEl = mkEl('div',{ className : 'img-box'  });
			divEl.appendChild(image);
			pEl.appendChild(divEl);
			pEl.appendChild(link);
			el.appendChild(pEl);
			el.parentNode.scrollLeft = 9999999;
		},

		//取消图片
		removeImage: function(type, id, linkNode){
      var thumbw = 100;
			var pool = this.imagePool[type];
			if(pool){
				pool.splice(id, 1);
			}
			log("remove image:"+ this+", "+type+", "+ id);
			var el = FYD.get(type+'-imgcontainer');
      var count = (el.children || el.childNodes).length;
			if(!el || !count) return null;
			el.style.width = ((count-1)*(thumbw+10)+5)+'px';
			el.removeChild(linkNode.parentNode);
		},

		insertIntoEditor:function(type)
		{
			ImageDialog.aliInsert( this.imagePool[type] );			
			//tinyMCEPopup.close();
		},
		
		initFlashUploader: function(){

      this._usingFlash = true;
			//初始化Flash上传界面
      var el = FYG('up-control');
	  el.innerHTML = "";
      var btn = mkEl('div');
      el.insertBefore(btn, el.firstChild);
			FYE.on('up-submit-btn', 'click', function(){ this.insertIntoEditor('up'); }, null, this);

			var swLink = mkEl('a', {href:'#html-uploader',innerHTML:'切换到HTML上传'});
			el.appendChild(swLink);
			FYE.on(swLink,'click',function(){this.initHTMLUploader(); return false;},null,this);

      this.info("最大3M，支持.jpg、jpeg、gif、png格式");

      try{
			//初始化上传组件
			var uploader = FD.widget.Uploader.init(btn, {
        swfUrl: 'img/aliuploader-v2.swf',
				//上传地址
				uploadUrl:			this.config.uploadUrl,
				width:				'147', 
				height:				'32',
				id:					'uploader-swf-'+(new Date().valueOf()),
				buttonSkin:			encodeURIComponent(this.config.uploadBtnSkin),
				debug:				true,
				responseChecker:	'ImageFactory.uploadSuccessCheck',
				fieldName:			'file',
				wmode:				'transparent',
				compressSize:		this.config.compressSize,
				sizeLimitEach:		this.config.sizeLimitEach,
				fileNumLimit:		this.config.fileCountLimit,
				simUploadLimit:		1,
				fileFilters:		[FD.widget.Uploader.FileFilter.IMAGES]
			});
      }
      catch(e){}

			uploader.addEventListener('buttonReady', function(evtObj){
				this.uploader.setAllowLogging(true);
				this.uploader.setUploadVars('waterMark', this.config.waterMark);
			}, this);

			uploader.addEventListener('fileSelect', function(evtObj){
				if(this.config.checkUrl) this.fetchToken();
				else this.uploadAll();
			}, this);
			uploader.addEventListener('uploadCompleteData', function(evtObj){
				log(evtObj.id+'上传'+(evtObj.success ? '成功':'失败')+'\n'+evtObj.data);
				if(evtObj.success)
				{
          this.onUploadDone(evtObj.data, evtObj.fileName);
				}
			}, this);

			this.uploader = uploader;
		},

		//获取token
    fetchToken: function(){
				this._fetchingToken = true;
        this.uploadToken = null;
				YAHOO.util.Get.script(this.config.checkUrl, { 
					onSuccess: function(oData) {
						if(typeof(uploadToken)!='undefined')
						{
							this._fetchingToken = false;
              this.uploadToken = uploadToken;
							uploadToken = null;
							//如果token已经取得，自动上传
							if(this._usingFlash){
                this.uploader.setUploadVars('authorToken',this.uploadToken);
                this.uploader.uploadAll();
              }
              else this.uploadFromHTML();
              //TODO:转圈效果
						} else {
							alert("token fetch fail:"+ (typeof(errorMsg)!='undefined' ? errorMsg : 'unknown reason'));
						}
					}, 							
					scope:this					
				});

    },

    //上传成功
    onUploadDone: function(dataStr, fileName){
					var data = typeof(dataStr)=='string' ? YAHOO.lang.JSON.parse(dataStr) : dataStr;
					if(data) 
					{
						var imgUrl = this.config.imgPrefix + data.filePath;
						this.addImage(imgUrl, 'up', fileName);
					} else {
						//上传成功，但是数据解析出错？
					}
    },

    //JS 方式上传组件
    initHTMLUploader: function(){

      //alert(YAHOO.lang.JSON.parse('{"success":true}'));
      this._usingFlash = false;
	  this.info('最大200KB，支持jpg、gif、png格式');
      var el = FYG('up-control');
      if(el)
      {
		el.innerHTML = "";

        var form = mkEl('form', {
          id:       'html-up-form',
          name:     'html-up-form',
          action:   this.config.uploadUrl,
          onsubmit: function(){ return true;},
          target:   'ifUp'
        });

        var input = mkEl('input', {
          type: 'file',
          name: 'file',
          id:   'html-up-input'
        });
        FYE.on(input, 'change', function(evt, obj){
          var v = obj.value;
          if (!v.match(/.*?\.(jpg|jpeg|gif|png)$/i)) {
            this.warn('只能上传图片文件');
            return false;
          }
          //check file size
          try {
            //works in FF3.0
            if (obj.files[0].fileSize > 200 * 1024) {
              this.warn('文件太大');
              return;
            }
          } catch (e) { }
          //upload
          this.fetchToken();
        }, input, this);
        form.appendChild(input);

		var url = window.location.href;
		var path = url.substring(0, Math.max(url.lastIndexOf("\\"),url.lastIndexOf("/"))+1);
        
        var tmpForm = mkEl('form', {
          id:   'up-tmp-form',
          name: 'up-tmp-form',
          action: this.config.uploadUrl,
          target: 'ifup',
          method: 'post',
			enctype: 'multipart/form-data',
          className:  'hidden'
        },[
          mkEl('input', {type:'hidden', name:'file'}),
          mkEl('input', {type:'hidden', name:'authorToken'}),
			mkEl('input', {type:'hidden', name:'jsupload', value:'true'}),
			mkEl('input', {type:'hidden', name:'directUrl', value:path+'html-uploaded.htm'}),
			mkEl('input', {type:'hidden', name:'waterMark', value:this.config.waterMark})
        ]);

        var iframe = mkEl('iframe', {
          id:     'ifup',
          name:   'ifup',
          width:  0,
          height: 0,
          className: 'hidden'
        });
        FYE.on(iframe, 'load', function(evt, obj){
          //TODO:replace textContent
          if(this._uploading) {
            //this.onUploadDone(obj.contentDocument.documentElement.textContent, '');
			//alert('iframe loaded');
			//alert(obj.contentWindow.location.search.substr(0).toQueryParams());
			try{
				this.onUploadDone(obj.contentWindow.location.search.substr(0).toQueryParams());
				this._uploading = false;
				FYG('up-tmp-form').reset();
			}
			catch(e){}
          }
        }, iframe, this);

        el.appendChild(form);

			var swLink = mkEl('a', {href:'#flash-uploader',innerHTML:'切换到flash上传'});
			el.appendChild(swLink);
			FYE.on(swLink,'click',function(){this.initFlashUploader();return false;},null,this);

        el.appendChild(tmpForm);
        el.appendChild(iframe);
      }
    },

    uploadFromHTML: function(){
      var tmpForm = FYG('up-tmp-form');
      if(tmpForm)
      {
        //clone input node
        var input = FYG('html-up-input');
        tmpForm.replaceChild(input.cloneNode(true), tmpForm.childNodes[0]);
        tmpForm.authorToken.value = this.uploadToken;
        //upload to server
        tmpForm.submit();
        this._uploading = true;
        input.form.reset();
      }
    },

		initWeb: function(){
			//从网络插入
			FYE.on('web-add-btn', 'click', function(){
				var url = FYD.get('web-img-input').value;
				if(url){
					if(url.match(/^http[s]{0,1}:\/\/([a-z0-9\-_]+\.)*(taobao|alipay|alibaba|yahoo|alisoft|alimama|koubei|aliimg)\.(com|net|cn|com\.cn)\//))
						this.addImage(url, 'web');
					else
						this.warn('图片地址不合法');
				}else{
					//地址为空
					this.warn('请输入图片地址');
				}
			}, null, this);

			FYE.on('web-submit-btn', 'click', function(){
				this.insertIntoEditor('web');
			}, null, this);
		},

		//转圈图标
		addOverlay: function(){
		},
		
		//回到正常状态
		removeOverlay: function(){
		},

    info: function(msg){
      var type = this.getSourceType();
      var el = FYG(type+'-msg');
      el.innerHTML = msg;
      FYD.removeClass(el, 'warn');
      FYD.addClass(el, 'info');
    },

    warn: function(msg){
      var type = this.getSourceType();
      var el = FYG(type+'-msg');
      el.innerHTML = msg;
      FYD.removeClass(el, 'info');
      FYD.addClass(el, 'warn');
    },

    getSourceType: function(){
      return ['up','album','web'][this.tab.curId];
    },

		toString: function(){
			return 'ImageFactory-'+ this.id;
		}

	};
})();

function log(sth){ try{ console.log(sth); } catch(e){} }
function setImgSize(B,D,A){var w=(B.width||B.offsetWidth),h=(B.height||B.offsetHeight);var G=D;var E=A;var F=h/w;var C=w/h;if(w>G){B.width=G;B.height=G*F}if(h>E){B.height=E;B.width=E*C}}
function mkEl(tag, map, children){ 
  var el=document.createElement(tag); 
  for(var each in map){ 
    if(map[each]!=Object.prototype[each]) 
      el[each]=map[each]; 
  }
  if(children) for(var i=0,len=children.length;i<len;i++){
    el.appendChild(children[i]);
  } 
  return el;
}


if(typeof(DoNotAutoStart)=='undefined')
{
	if(typeof(initINTV)!='undefined') clearInterval(initINTV);
	initINTV = setInterval(function(){
		if(typeof(FYE)=='undefined') return;
		clearInterval(initINTV);
		initINTV = null;
		FYE.onDOMReady(function(){
			var config = {};
			try
			{
				config = tinyMCE.settings.aliimage_config;
			}
			catch (e){}
			ImageFactory.init(config);
		});
	}, 50);
}
