var _search = function(){
	var e = document.getElementById('schIpt');
	if(typeof(e) == 'undefined'){
		return false ;
	}
	var t = e.value ; 
//	if(t == '' || typeof(t)=='undefined'){
//		alert('�����������ؼ���');
//		return false ;
//	}
	document.getElementById("searchForm").submit() ;
}

/**
 * ����
 * product_type ��Ʒ����
 * days ��������
 * sellerPrice ��ʼ�۸�
 * order �����ֶ�
 * orderSeq ��������
 */
var G_search = function(product_type , days , sellPrice , order , orderSeq){
	var e = document.getElementById('schIpt');
	if(typeof(e) == 'undefined'){
		return false ;
	}
	var t = e.value ; 
//	if(t == '' || typeof(t)=='undefined'){
//		alert('�����������ؼ���');
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