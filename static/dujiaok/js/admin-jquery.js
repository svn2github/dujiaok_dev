$(function(){
	var envRoot = $("#envRoot").val() ;
	
	if($("#adminPage").val() == 'orderlist'){
		////备注开始
	    
	    //显示备注
	    $("#orderlistTable .memo-icon").hover(function(){
	    	var memo = $(this).attr("data-memo") ;
	    	if(memo != ''){
		    	$("#memo-tips-container").css({left:$(this).offset().left - 20,top:$(this).offset().top + 25}) ;
		    	$("#memo-tips-container").find(".content").html($(this).attr("data-memo"));
		    	$("#memo-tips-container").removeClass("dd-hide") ;
	    	}
	    } , function(){
	    	$("#memo-tips-container").delay(300).addClass("dd-hide") ;
	    }) ;
		
		//点击【添加备注】，显示备注输入
		$(".add-memo-click").click(function(){
			var orderId = $(this).attr("data-order-id") ;
			$("#add-memo-dialog").attr("data-order-id" , orderId) ;
			$("#add-memo-dialog").css({left:$(this).offset().left - 150,top:$(this).offset().top + 19}) ;
			$("#add-memo-dialog").find(".content").val($(this).attr("data-memo"));
			$("#add-memo-dialog").find(".msg").html("") ;
			$("#add-memo-dialog").removeClass("dd-hide") ;
		}) ;
		
		//点击关闭
		$("#add-memo-dialog .w_close_color").click(function(){
			$("#add-memo-dialog").addClass("dd-hide") ;
		});
		
		//提交备注
		$("#add-memo-dialog .submit").click(function(){
			var url = envRoot + "/ajax/add_admin_order_memo_ajax.htm";
			var msgSelector = $("#add-memo-dialog").find(".msg") ;
			var orderId = $("#add-memo-dialog").attr("data-order-id") ;
			var content = $("#add-memo-dialog").find(".content").val() ;
			if(content == undefined || content == ''){
				msgSelector.html("请输入备注内容") ;
				return ;
			}
			//提交备注
			$.ajax({
				url : url ,
				type : "POST" ,
				data : { orderId: orderId , memo : encodeURI(content) },
				success : function(data){
					var isSuccess = data.success ;
					if(isSuccess == true) { 
						msgSelector.html("备注更新成功！") ;
						$("#add-memo-dialog").delay(500).addClass("dd-hide") ;			
					} else {
						msgSelector.html("更新备注失败！" + detail) ;
					}
				} ,
				error : function(data) {
					msgSelector.html("更新备注失败！" + data) ;
				}
			}) ;
		}) ;
		
		////备注结束
	}
	
	
	//
	//删除hot_city
	$(".hot-city-content .w_close_color").click(function(){
		var cityName = $(this).attr('data-city-name') ;
		var cityId = $(this).attr('data-id') ;
		if(confirm('删除热门城市[' + cityName + '] ？')){
			$.ajax({
				url:envRoot + "/ajax/delete_hot_city_ajax.htm" ,
				type:"POST" , 
				data:{cityId:cityId} ,
				success:function(data){
					var code = data.json.code ;
					if(code == 'success') {
						alert('操作成功！');
						window.location.reload() ;
					} else if(code == 'ill_args'){
						var detail = data.json.detail ;
						if(detail == 'dujiaok.hotCity.name.duplicate') {
							alert('该城市已经存在！');
						} else {
							alert('操作失败！');
						}
					} else {
						alert('操作失败！');
					}
				} , 
				error:function(data){
					alert('操作失败，请重试！');
				}
			}) ;
		}
	}) ;
	
	$(".hot-city-content .add-hot-city").click(function(){
		$(".hot-city-content .add-hot-city-dialog").removeClass('dd-hide') ;
		$(".hot-city-content .add-hot-city-dialog").find('input').val('') ;
	}) ;
	
	$(".hot-city-content .add-hot-city-dialog").find('.submit').click(function(){
		var cityName = $(".hot-city-content .add-hot-city-dialog").find('.city-name').val() ;
		if(cityName == ''){
			alert('请填写城市名称！') ;
			return ;
		}
		$.ajax({
			url:envRoot + "/ajax/add_hot_city_ajax.htm" ,
			type:"POST" , 
			data:{cityName:encodeURI(cityName)} ,
			success:function(data){
				var code = data.json.code ;
				if(code == 'success') {
					alert('操作成功！');
					window.location.reload() ;
				} else if(code == 'ill_args'){
					var detail = data.json.detail ;
					if(detail == 'dujiaok.hotCity.name.duplicate') {
						alert('该城市已经存在！');
					} else {
						alert('操作失败！');
					}
				} else {
					alert('操作失败！');
				}
			} , 
			error:function(data){
				alert('操作失败，请重试！');
			}
		}) ;
	}) ;
});