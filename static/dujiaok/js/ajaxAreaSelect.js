
var setupAreaSelects(provinceId , cityId , areaId){
	provinceId = "#" + provinceId ;
	cityId = "#" + cityId ;
	areaId = "#" + areaId ;
	var urlRoot = $("#urlRoot").val() + "/ajax/address.htm" ;
	var citySelect = $(cityId);
	var areaSelect = $(areaId) ;
	var provinceSelect = $(provinceId) ;
	$(provinceId).change(function(){
		var thisSelect = this ;
		var url = urlRoot ;
		var name = thisSelect.val() ;
		var code = thisSelect.find('option:selected').attr("code") ;
		citySelect.empty() ;
		areaSelect.empty() ;
		$.ajax({
			url: url ,
			type : "POST" ,
			data : { type: "city" , parentname : name , parentcode : code},
			success :function(data){
				__addAddrSelect(citySelect , data) ;
			} , 
			error : function(data){
				alert("��ȡ����ʧ��");
			}
		});
	}) ;
	
	$(cityId).change(function(){
		
	}) ;
	
	$(areaId).change(function(){
		
	});
	
	//��ʼ��ʡ
	if($(provinceId).length > 0){
		var thisSelect = $(provinceId) ;
		$.ajax({
			url: url ,
			type : "POST" ,
			data : { type: "province" , parentname : name , parentcode : code},
			success :function(data){
				__addAddrSelect(provinceSelect , data) ;
			} , 
			error : function(data){
				alert("��ȡ����ʧ��");
			}
		});
	}
	
}

var __newBlankOption = function() {
	var opStr = "<option></option>" ;
	return opStr ;
}

var __newAddrOption = function(opname , opcode){
	var opStr = "<option value='" + opname + "' code='" + opcode + "'>" + opname + "</option>" ;
	return opStr ;
	
}

var __addAddrSelect = function(thisSelect ,addrAjaxResult){
	__newBlankOption() ;
	for(var i=0 ;i<addrAjaxResult.length;i++){
		var addr = addrAjaxResult[i] ;
		var opname = addr.name ;
		var opcode = addr.code ;
		//var o = new Option(opname,opname) ;
		thisSelect.append(__newAddrOption(opname , opcode)) ;
	}
}
