// JavaScript Document
/**
 * @project XUI: Widget Client Library
 * @copyright Copyright (c) 2009, alibaba.com All rights reserved.
 * @version 1.3
 * @author shengding
 * @date 2010-2-22
 * @requires core/xui-core.js
 * @requires util/util.js
 */
 
 
 
 
xui.widgets.FUploadProgress =function(file, ownerFUpload,errorMessage){
	var o= this;
	this._events = {};
	this.file = file;
	this.ownerFUpload = ownerFUpload;
	this.container = ownerFUpload.container;
	this.fileProgressID = file.id;	
	this.fileProgressWrapper = document.createElement("li");
	this.fileProgressWrapper.className = "xui-fupload-progress";
	if(errorMessage){
		xui.util.Dom.addClass(this.fileProgressWrapper,'xui-fupload-progressError');
	}
	this.fileProgressWrapper.id = this.fileProgressID;
	
	this.progressDetail= document.createElement("div");
	this.progressDetail.className = "xui-fupload-progressDetail";
	
	this.progressFile= document.createElement("span");
	this.progressFile.className = "xui-fupload-file";//xui-fupload-file-xlsS
	var cls = this.getFileClassByType(file.type);
	xui.util.Dom.addClass(this.progressFile,cls);
	
	this.progressFileName= document.createElement("span");
	this.progressFileName.className = "xui-fupload-progressFileName";
	this.progressUrl = document.createElement("a");
	this.progressUrl.target="_blank";
	this.progressUrl.innerHTML = file.name;
	this.progressUrl.title = file.name;
	this.progressFileName.appendChild(this.progressUrl);
	
	this.progressSize= document.createElement("span");
	var size = this.getSize(file.size)
	this.progressSize.innerHTML = '('+size+')';
	this.progressSize.className = "xui-fupload-progressSize";
	
	
	this.progressBtn= document.createElement("button");
	this.progressBtn.className = "xui-fupload-btn";
	this.progressBtn.innerHTML = '&nbsp;';
	
	this.progressBarInProgress= document.createElement("div");
	this.progressBarInProgress.className = "xui-fupload-progressBarInProgress";
	
	if(errorMessage){
		this.progressErrorMessage= document.createElement("div");
		this.progressErrorMessage.className = "xui-fupload-progressErrorMessage";
		this.progressErrorMessage.innerHTML = errorMessage;
	}

	this.progressDetail.appendChild(this.progressFile);
	this.progressDetail.appendChild(this.progressFileName);
	this.progressDetail.appendChild(this.progressSize);
	this.progressDetail.appendChild(this.progressBtn);
	
	this.fileProgressWrapper.appendChild(this.progressDetail);
	
	this.fileProgressWrapper.appendChild(this.progressBarInProgress);
	if(errorMessage){
		this.setComplete();
		this.fileProgressWrapper.appendChild(this.progressErrorMessage);
	}
	this.container.appendChild(this.fileProgressWrapper);
	
	
	this._initEnvents('removeProgress');
	xui.util.Event.on(this.progressBtn,'click',function(){
													o.removeProgress();
														})
	//console.log(file)
	if(file.url){
		this.setUrl(file.url)
	}
	
		
}

xui.widgets.FUploadProgress.prototype = {
	getFileClassByType:function(sType){
		var s  = sType.toLowerCase( );
			switch(s){
				case '.eml' :
			       return 'eml';
			   case '.docx' :
			       return 'xui-fupload-file-docS';
			   case '.doc' :
			       return 'xui-fupload-file-docS';
			   case '.xlsx' :
			       return 'xui-fupload-file-xlsS';
				case '.xls' :
			       return 'xui-fupload-file-xlsS';
			   case '.txt' :
			       return 'xui-fupload-file-txtS';
			   case '.gif' :
			       return 'xui-fupload-file-gifS';
			   case '.png' :
			       return 'xui-fupload-file-pngS';
			   case '.jpg' :
			       return 'xui-fupload-file-jpgS';
			   case '.bmp' :
			       return 'xui-fupload-file-bmpS';
			   case '.pdf' :
			       return 'xui-fupload-file-pdfS';
			   case '.pptx' :
			       return 'xui-fupload-file-pptS';			
			   case '.ppt' :
			       return 'xui-fupload-file-pptS';
			   case '.zip' :
			       return 'xui-fupload-file-zipS';
			   case '.rar' :
			       return 'xui-fupload-file-zipS';
			   default :
			       return 'xui-fupload-file-defaultIconS';
			}
	},
	getSize:function(sSize){
		if(typeof sSize == 'string' ){
			var sSize = sSize.toUpperCase();
			if((sSize.indexOf('MB')>-1)){
				sSize = parseInt(sSize,10)*1048576;
			}else if((sSize.indexOf('KB')>-1)){
				sSize = parseInt(sSize,10)*1024;
			}else{
				sSize =parseInt(sSize,10);
			}
		}
		
		if(sSize>1073741824){
			return (sSize/1073741824).toFixed(2) + ' GB';
		}else if(sSize>1048576){
			return (sSize/1048576).toFixed(2) + ' MB';
		}else if(sSize>1024){
			return (sSize/1024).toFixed(2) + ' KB';			
		}else{
			return sSize + ' B';			
		}
	},
	setProgress:function(percentage){
		this.progressBarInProgress.style.width = percentage + "%";
	},
	setComplete:function(){
		this.progressBarInProgress.style.display = 'none';
	},
	setUrl:function(url){
		this.progressUrl.href =url;
		this.setComplete();
	},
	removeProgress:function(){
		var o = this;
		this.container.removeChild(this.fileProgressWrapper);
		this._fire('removeProgress',o.file);
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
	}

}
//设置总量超过的错误编码
SWFUpload.QUEUE_ERROR.QUEUE_TOTALLIMIT_EXCEEDED = 110;

xui.widgets.FUpload = function(id,attrs){
	this.id = id;
	this.settings = {
		prevent_swf_caching:true,
		flash_url:'',
		
		upload_url:'',
		delete_url:'',
		
		button_placeholder_id : this.id,
		button_width: 61,
		button_height: 18,
		button_text_left_padding:2,
		button_text_top_padding: 0,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		button_text_style: ".button_text_style { text-decoration:underline;color:#006699 }",
		
		file_types_description : "All Files",
		file_types : "*.*",
		file_size_limit : "10 MB" ,
		total_size_limit : "10 MB",//总的文件大小限制
		file_upload_limit : 0,		//SWFUpload实例控制上传成功的文件总数
		file_queue_limit :0		//文件上传队列中（入队检测通过的文件会添加到上传队列等待上传）允许排队的文件总数,不包括上传成功的文件

	};
	this.container = $(attrs.container_id);
	if(attrs.button_text){
		attrs.button_text = '<span class="button_text_style">'+attrs.button_text+'</span>';
	}
	for(var p in attrs){
            this.settings[p] = attrs[p];
    }
	this._events = {};
	
	this.queFilesHash = {};//准备上传的文件集合
	this.uploadFilesHash = {}; ;//上传好的文件集合
	this.progressHash = {};//显示进度集合
	this.isQueueComplete = true;//需要上传的所有文件是否已经上传完毕
}
xui.widgets.FUpload.prototype = {
	/**
	 * 初始化
	 * 
	 * @param {Object} existFiles 存在的文件对象集合
	 */	
	init:function(existFiles){
		var o =this;
		this.existFiles = existFiles;
		this._initEnvents('file_queued_handler');
		this._initEnvents('upload_success_handler');
		this._initEnvents('file_queue_error_handler');
		this._initEnvents('swfupload_load_failed_handler');
		
		this.swfu = new SWFUpload(this.settings);
		this.swfu.settings['swfupload_load_failed_handler'] = function(){
			o._fire('swfupload_load_failed_handler');
		}		
		this.swfu.settings['swfupload_loaded_handler'] = function(){
			o._initFiles();
		}
		
		//当文件选择对话框关闭消失时，如果选择的文件成功加入上传队列，那么针对每个成功加入的文件都会触发一次该事件（N个文件成功加入队列，就触发N次此事件）
		this.swfu.settings['file_queued_handler'] = function(file){
			o._fire('file_queued_handler',file);
		}
		//文件选中框后事件
		this.swfu.settings['file_dialog_complete_handler'] =function(selected,queued){
			o.startUpload();
		}
		//出错时候，文件超出大小，类型不对，空文件
		this.swfu.settings['file_queue_error_handler'] = function(file, errorCode, message){
			o._fileQueueError(errorCode,file);
		}
		//在文件往服务端上传之前触发此事件，可以在这里完成上传前的最后验证以及其他你需要的操作，例如添加、修改、删除post数据等。在完成最后的操作以后，如果函数返回false，那么这个上传不会被启动，并且触发uploadError事件（code为ERROR_CODE_FILE_VALIDATION_FAILED），如果返回true或者无返回，那么将正式启动上传。
		this.swfu.settings['upload_start_handler']=function (file){
			if(o._checkTotalSize(file)){
				o.createProgress(file);
				o._registerQueFilesHash(file);
			}else{
				
				//超出总量需要取消上传，否则会不断的触发upload_error_handl
				o._fileQueueError(110,file);
				o.cancelUpload(file.id);
			}
			return o._checkTotalSize(file);
		}
		
		this.swfu.settings['upload_progress_handler']=function (file, bytesTotal, bytesLoaded){
			o.isQueueComplete = false;
			o.setProgress(file, bytesTotal, bytesLoaded);
		}
		this.swfu.settings['upload_success_handler'] = function(file, serverData){
			o.setComplete(file);
			o._uploadSuccess(file, serverData);
			o._fire('upload_success_handler',file);
			o.isQueueComplete = true;
		}
		//无论什么时候，只要上传被终止或者没有成功完成，那么该事件都
		//将被触发,注意：此时文件上传的周期还没有结束，不能在这里开始下一个文件的上传
		this.swfu.settings['upload_error_handler']=function (file, error, message){
			
		}
		//不管上传成功还是失败，都会触发
		this.swfu.settings['upload_complete_handler'] = function(file){
			o.startUpload();
		}
	},
	_initFiles:function(){
		//如果存在上传过的文件,需要加上传好的文件
		if(this.existFiles){
			var i = 0;
			var stats = this.swfu.getStats();
			for(key in this.existFiles){
				this.existFiles[key].id = this.existFiles[key].fileId;
				this.existFiles[key].size = this._initSize(this.existFiles[key].size);
				this.createProgress(this.existFiles[key]);
				this._registerUploadFile(this.existFiles[key]);
				stats.successful_uploads++;
			}
			this.swfu.setStats(stats);
		}
	},
	_initSize:function(sSize){	
		if(typeof sSize == 'string' ){
			var sSize = sSize.toUpperCase();
			if((sSize.indexOf('MB')>-1)){
				sSize = parseInt(sSize,10)*1048576;
			}else if((sSize.indexOf('KB')>-1)){
				sSize = parseInt(sSize,10)*1024;
			}else{
				sSize =parseInt(sSize,10);
			}
		}
		return sSize
	},
	on:function(evemtName,func){
		var o = this;
		this._events[evemtName].subscribe(func, o);
	},
	/**
	 * 获取上传好的文件集合对象
	 * 
	 * @return {Object} {}
	 */	
	getUploadFiles:function(){
		return this.uploadFilesHash;
	},
	/**是否所有文件上传完毕*/
	getQueueState :function(){
		return 	this.isQueueComplete;
	},

	getNumUploadFiles:function(){
		var i = 0;
		for(key in this.uploadFilesHash){
			i++;
		}
		return i;
	},
	_initEnvents:function(evemtName) {
		var o = this;
		this._events[evemtName] = new xui.util.CustomEvent(evemtName, o);
	},
	_fire:function(evemtName,arg){
		this._events[evemtName].fire(arg);
	},
	_uploadSuccess:function(file, serverData){
		//serverData = "{isSuccess:true,data:{fileId:'12',name:'xxx.txt',url:'http://taobao.com'}}"
		//成功队列加入，在排序队列中取消
		this._registerUploadFile(file);
		this._unRegisterQueFilesHash(file);
		if(serverData){
			var oServerData = eval('(' + serverData + ')');
			if(oServerData.isSuccess){
				var url = oServerData.data.url;
				//console.log(this.uploadFilesHash[file.id])
				//url = url + '/'+	encodeURIComponent(this.uploadFilesHash[file.id].name);
				
				this.uploadFilesHash[file.id].fileId= oServerData.data.fileId;

				this.uploadFilesHash[file.id].url = url;
				//console.log(this.progressHash[file.id])
				//需要在progress的fiel的fileId中加入
				this.progressHash[file.id].file.fileId = oServerData.data.fileId;
				this.progressHash[file.id].setUrl(url);
			}
		}

	},
	_fileQueueError:function(errorCode,file){
		this._fire('file_queue_error_handler',[file,errorCode]);
		try {	
			switch (errorCode) {
			case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
				var errorMessage ='对不起，您上传的文件太大了';
				this.createProgress(file,errorMessage);
				break;
			case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
				var errorMessage ='对不起，不能上传0KB大小的文件';
				this.createProgress(file,errorMessage);
				break;
			case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
				var errorMessage ='不支持的文件类型';
				this.createProgress(file,errorMessage);				
				break;
			case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
				var errorMessage ='对不起，文件数目过多';
				this.createProgress(file,errorMessage);	
			case SWFUpload.QUEUE_ERROR.QUEUE_TOTALLIMIT_EXCEEDED:
				var errorMessage ='对不起，超过上传总容量';
				this.createProgress(file,errorMessage);				
   		    break;
			default:
				break;
			}
		} catch (e) {	        
	    }
	},
	//检查上传总量
	_checkTotalSize:function(file){
		var l = (this.settings.total_size_limit).toUpperCase();
		if((l.indexOf('MB')>-1)){
			l = parseInt(l,10)*1048576;
		}else if((l.indexOf('KB')>-1)){
			l = parseInt(l,10)*1024;
		}else{
			l =parseInt(l,10);
		}
		return (( xui.util.NumberUtil.add(this._getUploadFilesSize(),file.size,'+') )>l)?false:true;
	},
	_getUploadFilesSize:function(){
		var size = 0 ;
		//console.log(this.uploadFilesHash)
		for(var p in this.uploadFilesHash){
		   	size +=  parseInt(this.uploadFilesHash[p].size,10) ;
		}
		return size;
	},
	/***加上传进度的显示
	*/
	createProgress:function(file,errorMessage){
		var o = this;
		var progress = new xui.widgets.FUploadProgress(file, this,errorMessage);
		this._registerProgressFile(file,progress);
		//删除时候
		progress.on('removeProgress',function(){
								var clickfile = arguments[1][0];
								//console.log(clickfile)
								o._unRegisterUploadFile(clickfile);
								o._unRegisterProgressFile(clickfile);
								//如果有fileId，要有删除请求
								if(clickfile.fileId)
									o._deleteFile(clickfile.fileId);
												  })
	},
	_deleteFile:function(id){
		if(this.settings.delete_url.indexOf('?')>0){
			var url = this.settings.delete_url+'&id='+id;
		}else{
			var url = this.settings.delete_url+'?id='+id;
		}
		try{
			/**删除时候改变可以上传的数目*/
			var stats = this.swfu.getStats();
			stats.successful_uploads--;
			this.swfu.setStats(stats);
			 xui.util.Ajax.asyncGET(url);
		}catch(e){}
	},
	setProgress:function(file, bytesTotal, bytesLoaded){
		var percent = 0;
	  if(bytesTotal !=0)
		   percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
	  this.progressHash[file.id].setProgress(percent);
	},
	setComplete:function(file){
		this.progressHash[file.id].setComplete();
	},
	/**
	*开始上传文件
	* @param {String} 文件id
	*/  
	startUpload:function(file_id){
  		this.swfu.startUpload(file_id);
  	},	
	cancelUpload:function(file_id){
		this.swfu.cancelUpload(file_id)
	},
	_registerQueFilesHash : function(file){
		this.queFilesHash[file.id] = file;
	},
	_unRegisterQueFilesHash:function(file){
		 delete this.queFilesHash[file.id];
	},
	_registerUploadFile : function(file){
		this.uploadFilesHash[file.id] = file;
	},
	_unRegisterUploadFile:function(file){
		 delete this.uploadFilesHash[file.id];
	},
	_registerProgressFile : function(file,progress){
		this.progressHash[file.id] = progress;
	},
	 _unRegisterProgressFile:function(file){
		 delete this.progressHash[file.id];
	}

}