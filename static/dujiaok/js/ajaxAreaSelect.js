
var setupAreaSelects = function(provinceId , cityId , areaId){
	provinceId = "#" + provinceId ;
	cityId = "#" + cityId ;
	areaId = "#" + areaId ;
	var urlRoot = $("#urlRoot").val() + "/ajax/address.htm" ;
	var citySelect = $(cityId);
	var areaSelect = $(areaId) ;
	var provinceSelect = $(provinceId) ;
	$(provinceId).change(function(){
		var name = provinceSelect.val() ;
		var code = provinceSelect.find('option:selected').attr("code") ;
		citySelect.empty() ;
		areaSelect.empty() ;
		$.ajax({
			url: urlRoot ,
			type : "POST" ,
			data : { type: "city" , parentname : name , parentcode : code},
			success :function(data){
				__addAddrSelect(citySelect , data.result) ;
			} , 
			error : function(data){
				alert("获取数据失败");
			}
		});
	}) ;
	
	$(cityId).change(function(){
		var name = citySelect.val() ;
		var code = citySelect.find('option:selected').attr("code") ;
		areaSelect.empty() ;
		$.ajax({
			url: urlRoot ,
			type : "POST" ,
			data : { type: "area" , parentname : name , parentcode : code},
			success :function(data){
				__addAddrSelect(areaSelect , data.result) ;
			} , 
			error : function(data){
				alert("获取数据失败");
			}
		});
	}) ;
	
	$(areaId).change(function(){
		
	});
	
	//初始化省
	if(provinceSelect.length > 0){
		$.ajax({
			url: urlRoot ,
			type : "POST" ,
			data : { type: "province" },
			success :function(data){
				__addAddrSelect(provinceSelect , data.result , provinceSelect.attr("data")) ;
				//初始化市
				var name = provinceSelect.val() ;
				var code = provinceSelect.find('option:selected').attr("code") ;
				citySelect.empty() ;
				areaSelect.empty() ;
				$.ajax({
					url: urlRoot ,
					type : "POST" ,
					data : { type: "city" , parentname : name , parentcode : code},
					success :function(data){
						__addAddrSelect(citySelect , data.result , citySelect.attr("data")) ;
					} , 
					error : function(data){
						alert("获取数据失败");
					}
				});
			} , 
			error : function(data){
				alert("获取数据失败");
			}
		});
	}
		
}

var __newBlankOption = function() {
	var opStr = "<option></option>" ;
	return opStr ;
}

var __newAddrOption = function(opname , opcode , isSelected){
	var opStr = "" ;
	if(isSelected){
		opStr = "<option value='" + opname + "' code='" + opcode + "' selected >" + opname + "</option>" ;
	}else{
		opStr = "<option value='" + opname + "' code='" + opcode + "'>" + opname + "</option>" ;
	}
	
	return opStr ;
	
}

var __addAddrSelect = function(thisSelect ,addrAjaxResult , selectedVal){
	if(addrAjaxResult == null || addrAjaxResult.length == 0){
		return ;
	}
	thisSelect.append(__newBlankOption()) ;
	for(var i=0 ;i<addrAjaxResult.length;i++){
		var addr = addrAjaxResult[i] ;
		var opname = addr.name ;
		var opcode = addr.code ;
		//var o = new Option(opname,opname) ;
		if(selectedVal == opname){
			thisSelect.append(__newAddrOption(opname , opcode , true )) ;
		}else{
			thisSelect.append(__newAddrOption(opname , opcode )) ;
		}
		
	}
}
