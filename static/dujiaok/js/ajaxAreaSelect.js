
var element = function(id){
	return document.getElementById(id) ;
}

var addBlankOption = function(id){
	var o = new Option('','') ;
	document.getElementById(id).options.add( o , 0) ;	
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
		var select = document.getElementById(param.id) ;
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
	var select = document.getElementById(p.id) ;
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
	$.ajax({
		url: url ,
		type : "POST" ,
		data : { type: type , name: parentName==null?'':encodeURI(parentName)},
		success : function(data){
			try{
				
				//var result = eval('('+data+')') ;
				__setupAddressSelect(p,data.result) ;
			}catch(e){
				alert('exception :' + e.message) ;
			}
		} ,
		error : function(data){
			alert("get data error!") ;
		}
	});
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
	var select = document.getElementById(id) ;
	var options = select.options ;
	for(var i=0 ;i<options.length;i++){
		if(options[i].value == selectValue){
			options[i].selected = true ;
			break ;
		}
		
	}
}