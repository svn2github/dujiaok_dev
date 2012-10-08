$(function(){
	
	if($("#adminPage").val() == 'orderlist'){
		////��ע��ʼ
	    
	    //��ʾ��ע
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
		
		//�������ӱ�ע������ʾ��ע����
		$(".add-memo-click").click(function(){
			var settleId = $(this).attr("data-settle-id") ;
			$("#add-memo-dialog").attr("data-settle-id" , settleId) ;
			$("#add-memo-dialog").css({left:$(this).offset().left - 150,top:$(this).offset().top + 19}) ;
			$("#add-memo-dialog").find(".content").val($(this).attr("data-memo"));
			$("#add-memo-dialog").find(".msg").html("") ;
			$("#add-memo-dialog").removeClass("dd-hide") ;
		}) ;
		
		//����ر�
		$("#add-memo-dialog .W_close_color").click(function(){
			$("#add-memo-dialog").addClass("dd-hide") ;
		});
		
		//�ύ��ע
		$("#add-memo-dialog .submit").click(function(){
			var url = ddzBopsRoot + "/bops/zhe/remote/add_settle_memo_ajax.htm";
			var msgSelector = $("#add-memo-dialog").find(".msg") ;
			var settleId = $("#add-memo-dialog").attr("data-settle-id") ;
			var content = $("#add-memo-dialog").find(".content").val() ;
			if(content == undefined || content == ''){
				msgSelector.html("�����뱸ע����") ;
				return ;
			}
			//�ύ��ע
			$.ajax({
				url : url ,
				type : "POST" ,
				data : { id: settleId , memo : encodeURI(content) },
				success : function(data){
					var json = data.json ;
					var code = json.code ;
					var data = json.data ;
					var detail = json.detail ;
					if(code == 'success') { 
						msgSelector.html("��ע���³ɹ���") ;
						$("#add-memo-dialog").delay(500).addClass("dd-hide") ;			
						
					} else {
						msgSelector.html("���±�עʧ�ܣ�" + detail) ;
					}
				} ,
				error : function(data) {
					msgSelector.html("���±�עʧ�ܣ�" + data) ;
				}
			}) ;
		}) ;
		
		////��ע����
	}
});