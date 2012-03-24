var _search = function(){
	var e = document.getElementById('schIpt');
	if(typeof(e) == 'undefined'){
		return false ;
	}
	var t = e.value ; 
	if(t == '' || typeof(t)=='undefined'){
		alert('ÇëÊäÈëËÑË÷¹Ø¼ü×Ö');
		return false ;
	}
	document.getElementById("searchForm").submit() ;
}

var _search_p = function(product){
	if(product!=null && product!='' && typeof(product)!='undefined'){
		document.getElementById('s_product').value = product ;
	}else{
		document.getElementById('s_product').value = '' ;
	}
	document.getElementById("searchForm").submit() ;
}

var _search_d = function(days){
	if(days!=null && typeof(days)!='undefined'){
		document.getElementById('s_days').value = days ;
	}else{
		document.getElementById('s_days').value = '' ;
	}
	document.getElementById("searchForm").submit() ;
}