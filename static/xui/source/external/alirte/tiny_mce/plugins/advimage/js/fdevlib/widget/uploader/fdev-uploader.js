
/**
* FD.widget.Uploader
*
* Flash批量上传组件
* 
* 限制：
* 		1、依赖：js/fdevlib/widget/flash/fdev-flash.js
* 调用方法：
* 		……
*		<script type="text/javascript">
			var config = {
				url:	'a.swf',
				flashvars:	{}
			}
*			var flash = FD.widget.Flash.init( 'a.swf', config );
*			flash.addEventListener( 'myCustomEvent', function(evtObj){} );
*		</script>
*
* @author 	hua.qiuh <hua.qiuh@alibaba-inc.com>
*/


(function(){

	if(typeof(FD.widget.Uploader)!='undefined') return;

	FD.widget.Uploader = function(container, config){

		var time = new Date().valueOf();
		//默认配置
		var defConfig = {
			
			//uploader configs
			uploadUrl: '',							// 上传文件接收地址 
			simUploadLimit:		3,				// 同时上传的文件数目
			method:				"POST",			// 数据发送方式
			allowMultiple:		true,			// 允许多选
			buttonSkin:			null,			// 按钮皮肤
			vars:				{},				// 传输到服务器的额外变量
			headers:			{},				// 传输到服务器的额外头部变量，主要是cookie
			fieldName:			"imgFile",		// 传输到服务器时，文件内容在form中的字段名称
			sizeLimitEach:		0,				// 单个文件的大小限制（Bytes）
			sizeLimitTotal:		0,				// 上传的大小总和限制（Bytes）
			allowCompress:		true,			// 是否允许上传之前进行压缩
			compressSize:		0,				// 在此大小之下的文件，进行压缩处理
			fileNumLimit:		0,				// 单次上传的文件最大数量
			autoUpload:			false,			// 是否在选择文件后立刻自动上传
			autoAddJSCookie:	false,			// 自动从javascript读取cookie并添加到post数据的header中
			responseChecker:	"FD.widget.uploader.response_checker",		// 检查服务器端返回的数据，判断上传是否成功
			fileFilters:		null,

			//flash configs
			swfUrl: 'http://img.china.alibaba.com/images/cn/market/danai/flash/aliuploader-v2.swf',
			buttonSkin: '',
			width: '100%', 			/* 默认flash宽度 */
			height: '100%', 		/* 默认flash高度 */
			version: '9.0.0', 		/* Flash的版本 */
			id: 'uploaderswf' + time,
			menu: 'false',
			wmode: 'transparent',
			allowscriptaccess: 'always',
			expressinstall:'',
			debug: false
		};

		this._config	= FD.common.applyIf(config || {}, defConfig);

		var swfConfig = { flashvars:{} };
		for(var each in this._config){
			switch (each.toLowerCase())
			{
				case 'uploadurl':
				case 'eventhandler':
				case 'vars':
				case 'headers':
        case 'url':
					break;
				case 'simuploadlimit':
				case 'method':
				case 'allowmultiple':
				case 'buttonskin':
				case 'responsechecker':
				case 'autoupload':
				case 'compresssize':
				case 'allowcompress':
				case 'sizelimiteach':
				case 'sizelimittotal':
				case 'filenumlimit':
				case 'buttonskin':
        case 'uploadurl':
				case 'checkurl':
				case 'debug':
					swfConfig.flashvars[each] = this._config[each];
					break;
				case 'swfurl':
          swfConfig.url = this._config[each];
					break;
				default: swfConfig[each] = this._config[each];
			}
		}


		swfConfig.flashvars = FD.common.applyIf( swfConfig.flashvars, this._config.flashvars );

		
		this._eventHandler = function(evtObj){

			//alert(evtObj.type);
			if(typeof(this.swf)=='undefined')
			{
				this.swf = this.getFlash();
			}
			switch(evtObj.type)
			{
				case 'buttonReady':
					if(this._config.fileFilters) 
					this.setFileFilters(this._config.fileFilters);
					break;
				case 'uploadStart':
					this.disable();
					break;
				case 'finish':
					this.enable();
					this.clearFileList();
					break;
				case 'fileSelect': 
					if(this._config.autoUpload)
						this.uploadAll(); 
					break;
				case 'open':
					this.swf.blur();
					break;
			}
			if(console.debug && this._config.debug) console.debug("[%s] - %o", evtObj.type, evtObj);
		};

		/*
		fileSelect - fires when the user selects one or more files (after browse is called). Passes the array of currently selected files (if prior browse calls were made and clearFileList hasn't been called, all files the user has ever selected will be returned), along with all information available about them (name, size, type, creationDate, modificationDate, creator). 
		uploadStart - fires when a file starts uploading. Passes a file id for identifying the file.
		uploadProgress - fires when a file upload reports progress. Passes the file id, as well as bytesUploaded and bytesTotal for the given file.
		uploadComplete - fires when a file upload is completed successfully and passes the corresponding file id.
		uploadCompleteData - fires when data is received from the server after upload and passes the corresponding file id and the said data.
		uploadError - fires when an error occurs during download. Passes the id of the file that was being uploaded and an error type.
		*/
		//this.createEvent('swfReady');
		this.createEvent('buttonReady');
		this.createEvent('open');
		this.createEvent('fileSelect');
		this.createEvent('fileRefused');
		this.createEvent('cancel');
		this.createEvent('compressStart');
		this.createEvent('compressProgress');
		this.createEvent('compressComplete');
		this.createEvent('compressFail');
		this.createEvent('uploadStart');
		this.createEvent('uploadProgress');
		this.createEvent('uploadComplete');
		this.createEvent('uploadCompleteData');
		this.createEvent('uploadError');

		FD.widget.Uploader.superclass.constructor.call(this, container, swfConfig);

		//alert(this);
		this.addEventListener('ALLEVENT', this._eventHandler, this);
		
	};

	FD.widget.Uploader.init = function(container, config){
		return new FD.widget.Uploader(container, config);
	};

	//一些文件类型组合
	FD.widget.Uploader.FileFilter = {
		IMAGES: {
			description:	"图像 | images (*.jpg; *.jpeg; *.gif; *.png)", 
			extensions:		"*.jpg; *.jpeg; *.gif; *.png; "
		},
		IMAGES_NO_PNG: {
			description:	"图像 | images (*.jpg; *.jpeg; *.gif)", 
			extensions:		"*.jpg; *.jpeg; *.gif; " 
		}
	};

	YAHOO.extend(FD.widget.Uploader, FD.widget.Flash, {
		
		/*-------------------------
		* API 
		* -------------------------*/

		//
		setUploadVars: function(name, value){
			this._config.vars[name] = value;
		},

		setUploadHeaders: function(name, value){
			this._config.headers[name] = value;
		},

		// 设置是不是由flash发送测试
		setAllowLogging: function(b) { this.swf.setAllowLogging(b); },
		
		// 在上传队列中删除一个文件
		removeFile: function(fileId) { this.swf.removeFile(fileId); },

		// 清空上传队列
		clearFileList: function(){ this.swf.clearFileList(); },
		
		// 上传单个文件，可以设定各个上传参数
		upload: function(fileId){
			this.swf.upload(fileId, this._config.uploadUrl, this._config.method, this._config.vars, this._config.fieldName);
		},
		
		// 上传全部文件
		uploadAll: function(){
			this.swf.uploadAll(this._config.uploadUrl, this._config.method, this._config.vars, this._config.fieldName);
		},
		
		// 取消指定的上传文件
		cancel: function(fileId){
			this.swf.cancel(fileId);
		},
			
		// 设置同时进行上传的文件数
		setSimUploadLimit: function(simUpload){
			this.swf.setSimUploadLimit(simUpload);
		},
		
		// 设置选择文件的过滤器
		setFileFilters: function(filters){
			this.swf.setFileFilters(filters);
		},
		
		// enable()
		// 启用Flash选择文件UI
		enable: function(){
			this.swf.enable();
		},
		
		// 禁用Flash选择文件UI
		disable: function(){
			this.swf.disable();
		},
		
		toString: function(){
			return 'FD Uploader ['+this.config.id+']';
		}
		

	});

})();
