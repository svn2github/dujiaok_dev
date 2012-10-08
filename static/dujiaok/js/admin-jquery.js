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
		$("#add-memo-dialog .W_close_color").click(function(){
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
					var isSuccess = data.isSuccess ;
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
});