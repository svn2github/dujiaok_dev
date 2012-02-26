
(function() {

    if (typeof (ImageFactory) != 'undefined') return;

    ImageFactory = function(config) {

        //default config
        var defConfig = {
            tabContainer: 'tab1',
            allow_all: false,
            is_ali_only: false,
            white_list: ['*.aliui.com', '*.aliued.com'],
            //local image size limit - before compress
            compressSize: 3 * 1024 * 1024,
            //local image size limit - after compress
            sizeLimitEach: 200 * 1024,
            //local image type limit
            fileTypes: '',
            //local image count limit, 0 means no limit
            fileCountLimit: 0,
            uploadCountInDialog: 10,
            upload_response_checker: null,
            //upload address
            uploadUrl: 'http://10.20.131.8:8080/upload/blog/upload.html',
            //valid address
            checkUrl: 'http://blog.china.alibaba.com/misc/arandaHttpCheck.htm',
            //image prefix
            imgPrefix: 'http://10.20.131.8:8080/aranda.file/blog/',
            uploadBtnSkin: 'http://img.china.alibaba.com/images/cn/market/danai/090210/uploadBtn.png',
            waterMark: '',
            supportFileTypes: ["jpg", "jpeg", "gif", "png"],
            fileFieldName: 'file',
            uploadVars_flash: {},
            uploadVars_html: {},
            uploadHeaders: {},
            //convert server response to result object
            response_to_result_object: function(data) {
                data = typeof (data) == 'string' ? YAHOO.lang.JSON.parse(data) : data;
                //return data;
                return {
                    success: data.success,
                    errorCode: data.errorCode,
                    filePath: data.filePath
                };
            },
            allowUpload: true,
            allowAblum: false,
            allowWeb: true
        };

        var config = $Y.Lang.merge(defConfig, config || {});
        this.tab = FD.widget.Tab.init(config.tabContainer, {
            eventType: 'click',
            isAutoPlay: false
        });

        $E.on(this.tab.tabTitles, 'click', function() {
            if (this.tab.curId == 2) {
                //switch to web
                if (typeof (this.imagePool['up']) != 'undefined' && this.imagePool['up'].length) {
                    this.warn(__("aliimage_dlg.warn_unsaved_images",
						{ back_link: '<a href="#back-to-uploader" id="link-sw2up" onclick="return false;">' + __("aliimage_dlg.back2upload") + '</a>' }));
                    tinymce.dom.Event.add($D.get('link-sw2up'), 'click', function() { this.tab.setTab(0); }, this, true);
                } else {
                    this.info(__("aliimage_dlg.info_white_list", {
                        size_limit: toHumenReadSize(this.config.sizeLimitEach),
                        support_types: this.config.supportFileTypes
                    }));
                }
            }
        }, null, this);

        this.config = config;
        this.failFiles = [];
        ImageFactory.instance = this;

        //
        if (swfobject.hasFlashPlayerVersion("10")) {
            this.initFlashUploader();
        } else {
            this.initHTMLUploader();
        }
        this.initWeb();

        if (!this.config.allowUpload) {
            $D.getElementsByClassName('up', null, 'tab1', function(el) { el.style.display = 'none'; });
            return;
        }
        //TODO:add album tab
        if (!this.config.allowAlbum)
            $D.getElementsByClassName('album', '*', 'tab1', function(o) { o.style.display = 'none'; })
    }

    ImageFactory.instance = this;

    ImageFactory.init = function(config) {
        return new ImageFactory(config);
    }

    // check response data from file server
    ImageFactory.uploadSuccessCheck = function(swfId, data) {
        if (ImageFactory.instance.config.upload_response_checker) {
            return ImageFactory.instance.config.upload_response_checker(data);
        }
        try { var obj = YAHOO.lang.JSON.parse(data); }
        catch (e) { return false }
        return obj.success == true;
    };
    //
    //--------------- static methods end

    //
    ImageFactory.prototype = {

        imagePool: { up: [], web: [] },

        // add image to image pool
        addImage: function(url, type, filename) {
            //log('add image:'+url+' from '+type);

            var pool = this.imagePool[type];
            if (typeof (pool) == 'undefined') pool = this.imagePool[type] = [];
            pool.push({ url: url, filename: filename });

            var el = $D.get(type + '-imgcontainer');
            if (!el) return null;

            var count = (el.children || el.childNodes).length;
            //create a new image
            var image = new Image();
            var thumbw = 100, id = pool.length - 1;
            var _type = type;
            tinymce.dom.Event.add(image, 'load', function(evt, obj) {
                var w = image.width || image.offsetWidth;
                var h = image.height || image.offsetHeight;
                var id = pool.length - 1;
                try {
                    this.imagePool[_type][id]['width'] = w;
                    this.imagePool[_type][id]['height'] = h;
                }
                catch (e) { }

                setImgSize(image, thumbw - 10, thumbw - 10);
                setTimeout(function() { image.style.visibility = 'visible'; }, 100);
                image.style.marginTop = (thumbw - (image.height)) / 2 + 'px';

            }, this);

            image.src = url;
            image.title = image.alt = filename || ' ';
            image.style.visibility = 'hidden';

            var link = mkEl('a', {
                innerHTML: __('cancel'),
                href: '#cancel-image',
                id: 'rmlink-' + type + '-' + count,
                onclick: function() { return false; }
            });
            tinymce.dom.Event.add(link, 'click', function() {
                this.removeImage(this.getSourceType(), Math.round((link.parentNode.offsetLeft - 10) / (10 + link.parentNode.offsetWidth)), link);
                return false;
            }, this);

            el.style.width = ((count + 1) * (thumbw + 10) + (tinymce.isIE ? 25 : 5)) + 'px';

            var pEl = mkEl('li');
            //TODO:add drag'n'drop feature
            //pEl.onmousedown = function(event) { dragIt(this, event, 10, 'li'); }
            var divEl = mkEl('div', { className: 'img-box' });
            divEl.onmousedown = function(event) { dragIt(this.parentNode, event, 10, 'li'); }
            divEl.appendChild(image);
            pEl.appendChild(divEl);
            pEl.appendChild(link);
            el.appendChild(pEl);
            el.parentNode.scrollLeft = 9999999;
        },

        swapImage: function(fm, to) {
            var type = this.getSourceType();
            var pool = this.imagePool[type];
            var img = pool.splice(fm, 1)[0];
            pool.splice(to, 0, img);
        },

        //remove image
        removeImage: function(type, id, linkNode) {
            //alert('remove:' + type + ',' + id);
            log("remove image:" + this + ", " + type + ", " + id);
            var thumbw = 100;
            var pool = this.imagePool[type];
            if (pool) {
                pool.splice(id, 1);
            }
            if (type == 'up') {
                this._updateUploaderCountLimit();
            }
            var el = $D.get(type + '-imgcontainer');
            var count = (el.children || el.childNodes).length;
            if (!el || !count) return null;
            el.style.width = ((count - 1) * (thumbw + 10) + 5) + 'px';
            el.removeChild(linkNode.parentNode);
        },

        insertIntoEditor: function(type) {
            this.removeOverlay();
            ImageDialog.aliInsert(this.imagePool[type]);
            //tinyMCEPopup.close();
        },

        initFlashUploader: function() {

            this._usingFlash = true;

            //init flash uploader ui
            try {
                var el = $D.get('up-control');
                var btn = mkEl('div', { id: 'swf-sel-btn' });
                el.innerHTML = __("aliimage_dlg.upload");
                el.appendChild(btn);
            }
            catch (e) { }
            /*
            var swLink = mkEl('a', {className:'swlink', href:'#',innerHTML:__("aliimage_dlg.use_html_uploader")});
            tinymce.dom.Event.add(swLink,'click',function(){this.initHTMLUploader(); return false;},this);
            el.appendChild(swLink);
            */
            $E.on('up-submit-btn', 'click', function() { this.insertIntoEditor('up'); }, null, this);

            this.info(__("aliimage_dlg.info_upload_flash", {
                count_limit: this.config.fileCountLimit,
                size_limit: toHumenReadSize(this.config.compressSize),
                support_types: this.config.supportFileTypes
            }));

            try {
                //init uploader
                var imgTypes = "";
                for (var i = 0, len = this.config.supportFileTypes.length; i < len; i++) {
                    imgTypes += "*." + this.config.supportFileTypes[i] + ";";
                }
                var uploader = FD.widget.Uploader.init(btn, {
                    swfUrl: 'img/aliuploader-v2.swf',
                    //upload url
                    uploadUrl: this.config.uploadUrl,
                    width: '147',
                    height: '32',
                    id: 'uploader-swf-' + (new Date().valueOf()),
                    buttonSkin: encodeURIComponent(this.config.uploadBtnSkin),
                    debug: true,
                    responseChecker: 'ImageFactory.uploadSuccessCheck',
                    fieldName: this.config.fileFieldName,
                    wmode: 'transparent',
                    compressSize: this.config.compressSize,
                    sizeLimitEach: this.config.sizeLimitEach,
                    fileNumLimit: this.config.fileCountLimit,
                    simUploadLimit: 1,
                    vars: this.config.uploadVars_flash || {},
                    headers: this.config.headers || {},
                    fileFilters: [{
                        description: __("aliimage_dlg.image_files", { support_types: imgTypes }),
                        extensions: imgTypes
}],
                        elmentAlign: 'absmiddle'
                    });
                }
                catch (e) {  }

                uploader.on('swfReady', function(evtObj) {
                    this.uploader.setAllowLogging(true);
                    this.uploader.setUploadVars('waterMark', this.config.waterMark);
                    this._updateUploaderCountLimit();
                }, this);

                uploader.on('open', function(evtObj) {
                    this.clearMsg();
                }, this);

                uploader.on('fileSelect', function(evtObj) {
                    this.failFiles = [];
                    var len = evtObj.filesRefused.length;
                    if (len) {
                        for (var i = 0; i < len; i++) {
                            var file = evtObj.filesRefused[i];
                            this.failFiles.push({ 'reason': file.reason, 'file': file.name });
                        }
                    }
                    if (evtObj.numFiles) {
                        this._uploadTotalCount = evtObj.numFiles + len;
                        this._uploadSuccessCount = 0;
                        if (this.config.checkUrl) this.fetchToken();
                        else this.uploader.uploadAll();
                    } else if (len) {
                        this.listfailFiles();
                    }
                }, this);

                uploader.on('compressFail', function(evtObj) {
                    this.failFiles.push({ 'reason': 'COMPRESS_FAIL', 'file': evtObj.fileName });
                }, this);

                uploader.on('uploadError', function(evtObj) {
                    this.failFiles.push({ 'reason': 'UNKNOWN', 'file': evtObj.fileName });
                }, this);

                uploader.on('uploadCompleteData', function(evtObj) {
                    //log(evtObj.id+' Upload '+(evtObj.success ? ' succeed ':' failed ')+'\n'+evtObj.data);
                    if (evtObj.success) {
                        this._uploadSuccessCount++;
                        this.onUploadDone(evtObj.data, evtObj.fileName);
                    } else {
                        //record reason by errorCode
                        var data = YAHOO.lang.JSON.parse(evtObj.data);
                        var reason = {
                            '1000': 'SIZE_OVERFLOW',
                            '1100': 'INVALID_TYPE',
                            '1200': 'UNKNOWN',
                            '1300': 'UNKNOWN',
                            '1400': 'UNKNOWN',
                            '1500': 'UNKNOWN'
                        };
                        this.failFiles.push({
                            reason: reason[data.errorCode] || 'UNKNOWN',
                            file: evtObj.fileName
                        });
                    }
                }, this);

                uploader.on('finish', function(evtObj) {
                    evtObj.preventDefault = true;
                    this.uploader.clearFileList();
                    this.removeOverlay();
                    this._updateUploaderCountLimit();
                    this.listfailFiles();
                    //alert('upload finished : succeed'+this._uploadSuccessCount+'/'+this._uploadTotalCount);
                    var s = this._uploadSuccessCount, t = this._uploadTotalCount
                    if (s >= t) {
                        this.info(__('aliimage_dlg.info_upload_done_ok', { count: s }), true);
                        //all success
                    } else if (s > 0) {
                        this.info(__('aliimage_dlg.info_upload_done', { success_count: s, fail_count: t - s }), true);
                    }
                    this._uploadSuccessCount = 0;
                    this._uploadTotalCount = 0;
                }, this);

                this.uploader = uploader;
            },

            _updateUploaderCountLimit: function() {
                var count = this.getUploadLimit();
                var t = this.config.uploadCountInDialog;
                if (count <= 0) {
                    if (this._usingFlash)
                        this.uploader.disable();
                    else {
                        $D.get('html-up-input').disabled = true;
                    }
                } else {
                    if (this._usingFlash) {
                        this.uploader.enable();
                        this.uploader.setFileCountLimit(count);
                    } else {
                        $D.get('html-up-input').disabled = false;
                    }
                }
                $D.get('up-insert-tip').innerHTML = __('aliimage_dlg.image_pool') + ' (' + (t - count) + '/' + t + ')';
            },

            //get security token
            fetchToken: function() {
                this._fetchingToken = true;
                this.uploadToken = null;
                var url = this.config.checkUrl;
                url = url + (url.indexOf('?') == -1 ? '?' : '&') + 't=' + Math.floor(Math.random() * 10000);
                YAHOO.util.Get.script(url, {
                    onSuccess: function(oData) {
                        if (typeof (uploadToken) != 'undefined') {
                            this._fetchingToken = false;
                            this.uploadToken = uploadToken;
                            uploadToken = null;
                            //if has token, upload automatically
                            if (this._usingFlash) {
                                this.uploader.setUploadVars('authorToken', this.uploadToken);
                                this.uploader.uploadAll();
                                this.addOverlay();
                            }
                            else {
                                this.uploadFromHTML();
                                this.addOverlay();
                            }
                        }
                        else {
                            //alert("token fetch fail:"+ );
                            //fectch token fail, upload aborted, show warnning
                            this.warn(__((typeof (errorMsg) != 'undefined' && errorMsg == 'NOT_LOGIN' ? "aliimage_dlg.warn_fail_notlogin" : "aliimage_dlg.warn_fail_unknown")));
                        }
                    },
                    scope: this
                });
            },

            //upload complete 
            onUploadDone: function(data, fileName) {
                //data = typeof (data) == 'string' ? YAHOO.lang.JSON.parse(data) : data;
                data = this.config.response_to_result_object(data);
                if (data) {
                    if (data.success && data.success != 'false') {
                        var imgUrl = this.config.imgPrefix + data.filePath;
                        this.addImage(imgUrl, 'up', fileName);
                        this._updateUploaderCountLimit();
                        if (!this._usingFlash) {
                            this.info(__('aliimage_dlg.info_upload_done_ok', { count: 1 }));
                        }
                    } else if (!this._usingFlash) {
                        //1000 fail on file size check
                        //1100 fail on file type check
                        //1200 fail on token validation
                        //1300 fail on adding wartermark
                        //1400 fail on gererating thumbnails
                        //1500 unknown
                        var code = data.errorCode;
                        var msg;
                        switch (data.errorCode) {
                            case '1000':
                                msg = __('aliimage_dlg.warn_htmlfail_size', {
                                    size_limit: toHumenReadSize(this.config.sizeLimitEach)
                                });
                                break;
                            case '1100':
                                msg = __('aliimage_dlg.warn_htmlfail_type', {
                                    support_types: this.config.supportFileTypes
                                });
                                break;
                            default:
                                msg = __('aliimage_dlg.warn_htmlfail_unknown');
                        }
                        this.warn(msg);
                    } else {
                        // upload fail via flash uploader
                    }
                }
                else {
                    //uploaded, but analyze data error
                }
            },

            //JS uploader
            initHTMLUploader: function() {
                //alert(YAHOO.lang.JSON.parse('{"success":true}'));
                this._usingFlash = false;
                this.info(__("aliimage_dlg.info_upload_html", {
                    count_limit: this.config.fileCountLimit,
                    size_limit: toHumenReadSize(this.config.sizeLimitEach),
                    support_types: this.config.supportFileTypes
                }));

                var el = $D.get('up-control');
                if (el) {
                    var url = window.location.href;
                    var path = url.substring(0, Math.max(url.lastIndexOf("\\"), url.lastIndexOf("/")) + 1);
                    el.innerHTML = "";

                    var code = '<form id="html-up-form" name="html-up-form" method="POST" enctype="multipart/form-data"'
                    + '	 action="' + this.config.uploadUrl + '" target="ifup">'
				    + '	<input type="file" id="html-up-input" name="file" />'
				    + '	<input type="hidden" name="authorToken" value="" />'
				    + '	<input type="hidden" name="jsupload" value="true" />'
				    + '	<input type="hidden" name="directUrl" value="' + path + 'html-uploaded.htm" />';
                    for (var each in this.config.uploadVars_html) {
                        code += ' <input type="hidden" name="' + each
                        + '" value="' + this.config.uploadVars_html[each] + '" />';
                    }
                    code += '</form> \
                    <iframe id="ifup" name="ifup" height="0" width="0" class="hidden"></iframe>';

                    // use "innerHTML" to fix bug in IE
                    // for "name" attribute is READ ONLY in IE
                    var form = mkEl('div', { innerHTML: code });
                    el.appendChild(form);

                    tinymce.dom.Event.add($D.get('ifup'), 'load', function() {
                        if (this._uploading) {
                            try {
                                var obj = $D.get('ifup');
                                this.onUploadDone(obj.contentWindow.location.search.substr(1).toQueryParams());
                                this._uploading = false;
                                this.removeOverlay();
                                //$D.get('up-tmp-form').reset();
                            }
                            catch (e) { }
                        }
                    }, this);

                    /*
                    var swLink = mkEl('a', {href:'#', className:'swlink',innerHTML:__("aliimage_dlg.use_flash_uploader")});
                    tinymce.dom.Event.add(swLink,'click',function(){this.initFlashUploader();return false;},this);
                    form.appendChild(swLink);
                    */

                    // auto submit when select a file
                    tinymce.dom.Event.add($D.get('html-up-input'), 'change', function(evt) {
                        //return;
                        var obj = $D.get('html-up-input');
                        var v = obj.value;
                        if (!v.match(/.*?\.(jpg|jpeg|gif|png)$/i)) {
                            this.warn(__("aliimage_dlg.warn_htmlfail_type", {
                                support_types: this.config.supportFileTypes
                            }));
                            return false;
                        }
                        //check file size
                        try {
                            //works in FF3.0+
                            if (obj.files[0].fileSize > 200 * 1024) {
                                this.warn(__("aliimage_dlg.warn_htmlfail_size", {
                                    size_limit: toHumenReadSize(this.config.sizeLimitEach), files: v
                                }));
                                return false;
                            }
                        }
                        catch (e) { }
                        //upload
                        this.clearMsg();
                        if (this.config.checkUrl) this.fetchToken();
                        else this.uploadFromHTML();

                    }, this);

                    this._updateUploaderCountLimit();
                }
                tinymce.dom.Event.add($D.get('up-submit-btn'), 'click', function() { this.insertIntoEditor('up'); }, this);
            },

            listfailFiles: function() {

                var msgs = [];
                var addMsg = function(msg) {
                    //say sorry only once ... -__-!
                    msgs.push('<p>' + (msgs.length > 0 ? msg.replace(__('aliimage_dlg.sorry'), '') : msg) + '</p>');
                };

                var obj = {
                    'SIZE_OVERFLOW': [],
                    'INVALID_TYPE': [],
                    'REACH_MAX_FILE_COUNT': [],
                    'COMPRESS_FAIL': [],
                    'UNKNOWN': []
                };
                for (var i = 0, len = this.failFiles.length; i < len; i++) {
                    var record = this.failFiles[i];
                    var arr = obj[record.reason];
                    if (typeof (arr) == 'undefined') arr = obj['UNKNOWN'];
                    arr.push(record.file);
                }
                //msg += __("aliimage_dlg.sorry");
                if (obj['SIZE_OVERFLOW'].length)
                    addMsg(__("aliimage_dlg.warn_fail_size", {
                        files: '<span class="file">' + obj['SIZE_OVERFLOW'].join('/') + '</span>',
                        size_limit: toHumenReadSize(this.config.compressSize)
                    }));
                if (obj['INVALID_TYPE'].length)
                    addMsg(__("aliimage_dlg.warn_fail_type", { files: obj['INVALID_TYPE'].join('/') }));
                if (obj['REACH_MAX_FILE_COUNT'].length)
                    addMsg(__("aliimage_dlg.warn_fail_count", {
                        files: '<span class="file">' + obj['REACH_MAX_FILE_COUNT'].join(' / ') + '</span>',
                        count: this.config.fileCountLimit
                    }));
                if (obj['COMPRESS_FAIL'].length)
                    addMsg(__("aliimage_dlg.warn_fail_compress", {
                        files: '<span class="file">' + obj['COMPRESS_FAIL'].join(' / ') + '</span>',
                        size_limit: toHumenReadSize(this.config.sizeLimitEach)
                    }));
                if (obj['UNKNOWN'].length)
                    addMsg(__("aliimage_dlg.warn_fail_busy", { files: obj['UNKNOWN'].join('/') }));
                if (msgs.length) this.warn(msgs.join('<p class="warnbreak" />'));
            },

            uploadFromHTML: function() {
                var form = $D.get('html-up-form');
                if (form) {
                    this._uploading = true;
                    form.authorToken.value = this.uploadToken;
                    form.submit();
                    form.reset();
                }
            },

            initWeb: function() {
                if (!this.config.allowWeb) {
                    $D.getElementsByClassName('web', null, 'tab1', function(el) { el.style.display = 'none'; });
                    return;
                }
                //insert from internet
                tinymce.dom.Event.add($D.get('web-add-btn'), 'click', function() {
                    var url = $D.get('web-img-input').value;
                    if (url) {
                        if ((this.config.allow_all && url.match(/^http[s]{0,1}:\/\/.*\/.*/)) || url.match(/^http[s]{0,1}:\/\/([a-z0-9\-_]+\.)*(taobao|alipay|alibaba|1688|alibaba-inc|yahoo|alisoft|alimama|koubei|aliimg|alibado|alixueyuan|huanqiu|globaltimes|kitco|export-entreprises)\.(com|net|cn|com\.cn)\//)) {
                            this.addImage(url, 'web', '');
                            $D.get('web-img-input').value = "";
                            this.clearMsg();
                        }
                        else {
                            this.warn(__("aliimage_dlg.warn_invalid_url"));
                            //$D.get('web-img-input').value = "";
                        }
                    }
                    else {
                        this.warn(__("aliimage_dlg.warn_empty_url"));
                        //$D.get('web-img-input').value = "";
                    }
                }, this);

                tinymce.dom.Event.add($D.get('web-submit-btn'), 'click', function() {
                    this.insertIntoEditor('web');
                }, this);
            },

            getUploadLimit: function() {
                // unlimit
                if (!this.config.uploadCountInDialog) return 9999999;
                return this.config.uploadCountInDialog - this.imagePool.up.length;
            },

            //loading icon
            addOverlay: function() {
                var el;
                el = $D.get('overlay-div');
                if (el) el.parentNode.removeChild(el);

                el = mkEl('div', { id: 'overlay-div', className: 'overlay', onMouseDown: function() { },
                    innerHTML: ''
                });
                el.style.position = "absolute";
                el.style.top = el.style.left = "0";
                //el.style.width = el.style.height = "100%";
                el.style.width = "100%";
                el.style.height = document.body.offsetHeight + "px";
                el.style.padding = "0";

                var bg = mkEl('div');
                bg.style.width = bg.style.height = "100%";
                bg.style.opacity = ".3";
                bg.style.background = "#fff";
                try { bg.style.filter = "alpha(opacity=50)"; } catch (e) { }
                el.appendChild(bg);

                var img = mkEl('div');
                img.style.width = img.style.height = "100%";
                img.style.position = "absolute";
                img.style.top = img.style.left = 0;
                img.style.background = "url(img/ajax-loader.gif) no-repeat 50%";
                el.appendChild(img);

                document.body.appendChild(el);
            },

            //return to normal form
            removeOverlay: function() {
                var el = $D.get('overlay-div');
                if (el) document.body.removeChild(el);
            },

            info: function(msg, toAdd) {
                var type = this.getSourceType();
                var el = $D.get(type + '-msg');
                var sep = el.innerHTML.length ? '<p class="infobreak" />' : '';
                el.innerHTML = toAdd ? msg + sep + el.innerHTML : msg;
                if (!toAdd) $D.removeClass(el, 'warn');
                if (!toAdd || !$D.hasClass(el, 'warn')) $D.addClass(el, 'info');
            },

            warn: function(msg, toAdd) {
                var type = this.getSourceType();
                var el = $D.get(type + '-msg');
                var sep = el.innerHTML.length ? '<p class="warnbreak" />' : '';
                el.innerHTML = toAdd ? msg + sep + el.innerHTML : msg;
                $D.removeClass(el, 'info');
                $D.addClass(el, 'warn');
            },

            clearMsg: function() {
                var type = this.getSourceType();
                var el = $D.get(type + '-msg');
                el.innerHTML = "";
                $D.removeClass(el, 'info');
                $D.removeClass(el, 'warn');
            },

            getSourceType: function() {
                return ['up', 'album', 'web'][this.tab.curId];
            },

            toString: function() {
                return '[ImageFactory Instance]';
            }
        };

        try {
            tinyMCE.activeEditor.ImageFactory = ImageFactory;
        }
        catch (e) { }


        //--- start from here
        if (typeof (DoNotAutoStart) == 'undefined') {
            if (typeof (initINTV) != 'undefined') clearInterval(initINTV);
            initINTV = setInterval(function() {
                if (typeof (FYE) == 'undefined') return;
                clearInterval(initINTV);
                initINTV = null;
                $E.onDOMReady(function() {
                    var config;
                    try {
                        config = tinyMCE.settings.aliimage_config;
                    }
                    catch (e) { }
                    ImageFactory.init(config);
                });
            }, 50);
        }
    })();



/**
* Helper Functions
*/

function __(msg, map) {
    var txt = msg;
    try {
        txt = tinyMCE.activeEditor.translate(msg);
    }
    catch (e) { }
    for (var each in map) txt = txt.split("{#" + each + "}").join(map[each]);
    return txt;
};

function toHumenReadSize(bytes) {
    if (bytes > 1024 * 1024) return Math.floor(bytes / 1024 / 1024 * 100) / 100 + "MB";
    if (bytes > 1024) return Math.floor(bytes / 1024 * 100) / 100 + "KB";
    return bytes + "B";
};

function log(sth) { try { console.log(sth); } catch (e) { } }

function setImgSize(B, D, A) { var w = (B.width || B.offsetWidth), h = (B.height || B.offsetHeight); var G = D; var E = A; var F = h / w; var C = w / h; if (w >= h) { if (w > D) { B.width = G; B.height = G * F } } else if (h >= w) { if (h > E) { B.height = E; B.width = E * C } } }

function mkEl(tag, map, children) {
    var el = document.createElement(tag);
    for (var each in map) {
        if (map[each] != Object.prototype[each]) {
            el[each] = map[each];
        }
    }
    if (children) for (var i = 0, len = children.length; i < len; i++) {
        el.appendChild(children[i]);
    }
    return el;
}


function dragIt(item, event, right, type) {
    var sx, sy, ix, iy, vx, vy, iw, ih, dP, dL, len, tmp = null, tmp2 = null, tmp3 = null, tmp4 = null, tmp5 = null, f = 0, t = 0, fm, to;
    event = event || window.event;
    sx = event.clientX;
    sy = event.clientY;
    ix = item.offsetLeft;
    iy = item.offsetTop;
    iw = item.offsetWidth;
    ih = item.offsetHeight;
    vx = sx - ix;
    vy = sy - iy;
    dP = item.parentNode;
    dL = dP.getElementsByTagName(type);
    len = dL.length;
    fm = Math.round((ix - 10) / (right + iw));
    $D.addClass(item, 'dragging');

    if (document.addEventListener) {
        document.addEventListener("mousemove", moveHandler, true);
        document.addEventListener("mouseup", upHandler, true);
    }
    else if (document.attachEvent) {
        item.setCapture();
        item.attachEvent("onmousemove", moveHandler);
        item.attachEvent("onmouseup", upHandler);
        item.attachEvent("onlosecapture", upHandler);
    }

    if (event.stopPropagation) event.stopPropagation();
    else event.cancelBubble = true;

    if (event.preventDefault) event.preventDefault();
    else event.returnValue = false;

    var a, r;



        function moveHandler(e) {
        var d, l, t, a, r;
        if (!e) e = window.event;

        if (tmp == null) {
            tmp = document.createElement(type == 'div' ? 'span' : 'div');
            tmp.className = "drag_unit";
            tmp.style.width = iw - 2 + "px";
            tmp.style.height = ih - 2 + "px";
            tmp.style.border = "dashed 1px #666";
            dP.insertBefore(tmp, item);
            /*tmp3 = document.createElement("li");
            tmp3.className = "drag_unit";
            tmp3.style.width = 1 + "px";
            tmp3.style.height = ih - 2 + "px";
            dP.appendChild(tmp3);
            tmp4 = document.createElement("li");
            tmp4.className = "drag_unit";
            tmp4.style.width = 1 + "px";
            tmp4.style.height = ih - 2 + "px";
            dP.appendChild(tmp4);
            tmp5 = document.createElement("li");
            tmp5.className = "drag_unit";
            tmp5.style.width = 1 + "px";
            tmp5.style.height = ih - 2 + "px";
            dP.appendChild(tmp5);*/
        }

        if (item.style.position != "absolute") {
            item.style.position = "absolute";
        }

        l = e.clientX - vx - dP.parentNode.scrollLeft
        t = e.clientY - vy;

        l = l > 342 ? 342 : (l < 2 ? 2 : l);
        //t = t > 100 ? 100 : (t < 0 ? 0 : t);
        t = 4;

        item.style.left = l + "px";
        item.style.top = t + "px";


        d = Math.floor((e.clientX - vx) / (iw + right));
        d = d < 0 ? 0 : d;

        d = d > fm ? d + 1 : d;

        if (tmp) {
            if (d < len - 2) {
                dP.insertBefore(tmp, dL[d]);
            }
            else if (d == len - 2) {
                //document.getElementById('dsewa').innerHTML = (d + "#" + len);
                dP.insertBefore(tmp, dL[len - 2]);
            }
            else if (d == len - 1) {
                //document.getElementById('dsewa').innerHTML = (d + "#" + len);
                dP.insertBefore(tmp, dL[len - 1]);
            }
            else if (d == len) {
                $D.insertAfter(tmp, dL[len - 1])
                //document.getElementById('dsewa').innerHTML = (d + "#" + len);
            }

        }

        if (e.stopPropagation) e.stopPropagation();
        else e.cancelBubble = true;
    }

    function upHandler(e) {
        if (!e) e = window.event;

        $D.removeClass(item, 'dragging');
        if (tmp) {
            to = Math.round((tmp.offsetLeft - 10) / (right + iw));
            dP.insertBefore(item, tmp);
            dP.removeChild(tmp);
            tmp = null;
            ImageFactory.instance.swapImage(fm, to);
        }
        //move down to fix the bug whith wrong 'to' value
        item.style.position = "";

        if (document.removeEventListener) {
            document.removeEventListener("mouseup", upHandler, true);
            document.removeEventListener("mousemove", moveHandler, true);
        }
        else if (document.detachEvent) {
            item.detachEvent("onlosecapture", upHandler);
            item.detachEvent("onmouseup", upHandler);
            item.detachEvent("onmousemove", moveHandler);
            item.releaseCapture();
        }

        if (e.stopPropagation) e.stopPropagation();
        else e.cancelBubble = true;
    }
}