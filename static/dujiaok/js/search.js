var _search = function(){
	var e = document.getElementById('schIpt');
	if(typeof(e) == 'undefined'){
		return false ;
	}
	var t = e.value ; 
//	if(t == '' || typeof(t)=='undefined'){
//		alert('请输入搜索关键字');
//		return false ;
//	}
	document.getElementById("searchForm").submit() ;
}

/**
 * 搜索
 * product_type 产品类型
 * days 游玩天数
 * sellerPrice 起始价格
 * order 排序字段
 * orderSeq 排序依据
 */
var G_search = function(product_type , days , sellPrice , order , orderSeq){
	var e = document.getElementById('schIpt');
	if(typeof(e) == 'undefined'){
		return false ;
	}
	var t = e.value ; 
//	if(t == '' || typeof(t)=='undefined'){
//		alert('请输入搜索关键字');
//		return false ;
//	}
	
	if(product_type!=null && product_type!='' && typeof(product_type)!='undefined'){
		document.getElementById('s_product').value = product_type ;
	}else{
		document.getElementById('s_product').value = '' ;
	}
	
	if(days!=null && typeof(days)!='undefined'){
		document.getElementById('s_days').value = days ;
	}else{
		document.getElementById('s_days').value = '' ;
	}
	
	if(sellPrice!=null && typeof(sellPrice)!='undefined'){
		document.getElementById('s_sellPrice').value = sellPrice ;
	}else{
		document.getElementById('s_sellPrice').value = '' ;
	}
		
	if(order != null && typeof(order)!='undefined'){
		document.getElementById('s_order').value = order ;
	}else{
		document.getElementById('s_order').value = '' ;
	}
	
	if(orderSeq != null && typeof(orderSeq)!='undefined'){
		document.getElementById('s_orderSeq').value = orderSeq ;
	}else{
		document.getElementById('s_orderSeq').value = '' ;
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