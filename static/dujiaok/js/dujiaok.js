
var addBlankOption = function(id){
	var o = new Option('','') ;
	$(id).options.add( o , 0) ;	
	//o.selected = true ;
}

/**
 *
 * @param param 
 * {id:id,url:url,type:type,parentName:parentName,name:name} 
 * @param result 
 * @returns
 */
var __setupAddressSelect = function(param,result){
		var select = $(param.id) ;
		select.options.length = 0  ;
		for(var i=0 ;i<result.length;i++){
			var p = result[i] ;
			var o = new Option(p.name,p.name) ;
    		select.options.add(o) ;
		}
		var options = select.options ;
		addBlankOption(param.id) ;
		options[0].selected = true ;
		if(typeof(param.name)!='undefined' && param.name!=''){
    	for(var i=0 ;i<options.length;i++){
    		if(options[i].value == param.name){
    			options[i].selected = true ;
    			break ;
    		}
    	}
	}
	
}

/**
 * 
 * {id:id,url:url,type:type,parentName:parentName,name:name} 
 */
var setupAddressSelect = function(p){
	var select = $(p.id) ;
	select.options.length = 0  ;
	var id = p.id;
	var url = p.url ;
	var type = p.type ;
	var parentName = p.parentName ;
	var name = p.name ;
	var params = 'type='+type+'&name='+encodeURI(parentName)+'&t='+new Date() ;
	var request = url + '?' + params ;
	if(type!='province' && parentName==''){
		return ;
	}
	xui.util.Ajax.asyncGET(request, 
			function(data){
				
				try{
					var result = eval('('+data+')') ;
					__setupAddressSelect(p,result.result) ;
				}catch(e){
					alert('exception :' + e.message) ;
				}
			} , 
			function(data){
				alert("get data error!") ;
			}
	)
}

/**
 * 
 * @param url ${env.root}
 * @param id documentId
 * @param type 'province' | 'city' | 'area'
 * @param name
 * @param parentName
 * @returns
 */
var initAddressSelect = function(url,id,type,name,parentName){
	var p = {id:id,url:url,type:type,parentName:parentName,name:name} ;
	setupAddressSelect(p) ;
}

var setupSelect = function(id,selectValue){
	if(typeof(selectValue)=='undefined'){
		return ;
	}
	var select = $(id) ;
	var options = select.options ;
	for(var i=0 ;i<options.length;i++){
		if(options[i].value == selectValue){
			options[i].selected = true ;
			break ;
		}
		
	}
}


/**
 * set clipboard
 */
function setClipboard(maintext) 
{
    if (window.clipboardData) 
    {
        return (window.clipboardData.setData("Text", maintext));
    } 
    else 
    {
        if (window.netscape) 
        {
            try{
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            var clip = Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(Components.interfaces.nsIClipboard);
            if (!clip) 
            {
                return;
            }
            var trans = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);
            if (!trans) 
            {
                return;
            }
            trans.addDataFlavor("text/unicode");
            var str = new Object();
            var len = new Object();
            var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
            var copytext = maintext;
            str.data = copytext;
            trans.setTransferData("text/unicode", str, copytext.length * 2);
            var clipid = Components.interfaces.nsIClipboard;
            if (!clip) 
            {
                return false;
            }
            clip.setData(trans, null, clipid.kGlobalClipboard);
            return true;
            }
            catch(e)
            {
                alert("you firefox not support clipboard operations . please change your settings .");
                return false;
            }
        }
    }
    return false;
}


/**
 * get Clipboard
 */
function getClipboard() 
{
    if (window.clipboardData) 
    {
        return (window.clipboardData.getData('text'));
    } 
    else 
    {
        if (window.netscape) 
        {
            try 
            {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                var clip = Components.classes["@mozilla.org/widget/clipboard;1"].createInstance(Components.interfaces.nsIClipboard);
                if (!clip) 
                {
                    return;
                }
                var trans = Components.classes["@mozilla.org/widget/transferable;1"].createInstance(Components.interfaces.nsITransferable);
                if (!trans) 
                {
                    return;
                }
                trans.addDataFlavor("text/unicode");
                clip.getData(trans, clip.kGlobalClipboard);
                var str = new Object();
                var len = new Object();
                trans.getTransferData("text/unicode", str, len);
            }
            catch (e) 
            {
            	alert("you firefox not support clipboard operations . please change your settings .");
                return null;
            }
            if (str) 
            {
                if (Components.interfaces.nsISupportsWString) 
                {
                    str = str.value.QueryInterface(Components.interfaces.nsISupportsWString);
                } 
                else 
                {
                    if (Components.interfaces.nsISupportsString) 
                    {
                        str = str.value.QueryInterface(Components.interfaces.nsISupportsString);
                    } 
                    else 
                    {
                        str = null;
                    }
                }
            }
            if (str) 
            {
                return (str.data.substring(0, len.value / 2));
            }
        }
    }
    return null;
}


var delProduct = function(val , rootUrl){
	var url = rootUrl + '/ajax/delProduct.htm?productId=' + val ;
	xui.util.Ajax.asyncGET(url , 
			function(data){
				try{
					var result = eval('('+data+')') ;
					var result = result.result ;
					if('success' == result){
						window.location.reload() ;
					}else{
						alert('删除产品出错，请稍后再试！') ;
					}
				}catch(e){
					alert('删除产品出错，请稍后再试！') ;
				}
			} , 
			function(data){
				alert("get data error!") ;
			}
	)
	
}