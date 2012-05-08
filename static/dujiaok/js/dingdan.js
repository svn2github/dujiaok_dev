function zzsCheck(obj){//成人必须是正整数
	var v=obj.val();
	obj.val(v.replace(/\D/g,''));
	v=obj.val();
	var L=v.length;
	if(v.substr(0,1)=="0"){
		obj.val(v.substr(1,L));
	}
	if(v==""){
		obj.val(1);
	}
}
function ffzsCheck(obj){//儿童可以是非负整数
	var v=obj.val();
	obj.val(v.replace(/\D/g,''));
	v=obj.val();
	var L=v.length;
	if(v.substr(0,1)=="0"&&L>1){
		obj.val(v.substr(1,L));
	}
	if(v==""){
		obj.val(0);
	}
}
function calculation(){
	var v=$("#ddIpt1").val(); //成人数
	var cV=$("#ddIpt2").val(); //儿童数
	var orderDays = $("#orderDays").html() ; //订单天数
	var single = $("#dingdanP1").html() ;
	var double = $("#dingdanP2").html() ;
	var marketPrice = $("#marketPrice").val() ;
	
	var scBxf=$("#dingdanP4").html()*v*orderDays; //市场保险费用
	var bxf=$("#dingdanP5").html()*v*orderDays;//保险费用
	var scj=(v+cV)*marketPrice;//市场价
	var okj=parseInt(v/2)*$("#dingdanP2").html()+(v%2)*$("#dingdanP1").html()+cV*$("#dingdanP3").html();//OK价
	var js=(scj-okj)+(scBxf-bxf); //节省
	var zj=okj+bxf; //总价
	$("#dingdanP6").val(v);
	$("#dingdanP7").html(bxf);
	$("#dingdanP8").html(scj);
	$("#dingdanP9").html(okj);
	$("#dingdanP10").html(bxf);
	$("#dingdanP11").html(js);
	$("#dingdanP12").html(zj);
}
function calcu(obj){
	var v=obj.val();	
	var scj=v*$("#marketPrice").html();
	var okj=v*$("#price").html();
	var js=scj-okj;
	$("#orderMarketPrice").html(scj);
	$("#orderOkPrice").html(okj);
	$("#orderSavePrice").html(js);
	$("#orderPrice").html(okj);
}

$("#ddIpt1").keyup(function(){
	zzsCheck($(this));
	calculation();
	var v=$(this).val();
	$("#subCBox").html("");	
	if(v>1){
		s="";
		for(var i=1;i<v;i++){
			var contactStr='<div class="subContact"><table class="mgt10 contactInfo"><tr><th><i>*</i>游玩人姓名：</th><td><input type="text" class="w184 name" name="contacts[' + i + '].name"/></td></tr><tr><th><i>*</i>游玩人手机：</th><td><input type="text" class="w184 mobile" maxlength="11" name="contacts[' + i + '].mobile"/></td></tr><tr><th><i>*</i>证件类型：</th><td><select id="select" name="contacts[' + i + '].certificateType"><option value="身份证">身份证</option><option value="护照">护照</option><option value="军官证">军官证</option></select></td></tr><tr><th><i>*</i>证件号码：</th><td><input type="text" class="w184 certificateNumber" name="contacts[' + i + '].certificateNumber"/></td></tr><tr><th>E-mail：</th><td><input type="text" class="w184 email" name="contacts[' + i + '].email"/></td></tr><tr><th></th><td></td></tr></table></div>';
			s += contactStr;
		}
		$("#subCBox").html(s);	
	}
})
$("#ddIpt2").keyup(function(){
	ffzsCheck($(this));
	calculation();
})
$("#roomNum").keyup(function(){
	zzsCheck($(this));
	calcu($(this));
})
$("#ticketNum").keyup(function(){
	zzsCheck($(this));
	calcu($(this));
})

//dom ready
$(function(){
	ffzsCheck($(this));
	calculation();
})
/**
$("#operateInfo .add").click(function(e){
	e.preventDefault();
	$("#subCBox").append(contactStr);
})
**/
$("#orderForm .del").live("click",function(e){
	e.preventDefault();
	var obj=$(this).closest(".subContact");
	obj.remove();
})

$("#orderForm :submit").click(function(e){
		e.preventDefault();
		var infos=$("#orderForm .contactInfo");
		var L=infos.size();
		for(var i=0;i<L;i++){
			var info = $(infos[i]);
			var nameIpt = info.find(".name");
			var mobileIpt = info.find(".mobile");
			var certificateNumberIpt = info.find(".certificateNumber");
			var email = info.find(".email"); 
			var sel=info.find("select");
			if(nameIpt.val()==""){
				alert("请填写姓名！")
				nameIpt.focus()
				return false
			}
			if(mobileIpt.val()==""){
				alert("请填写手机号码！")
				mobileIpt.focus()
				return false
			}
			var r=/^[0-9]*[1-9][0-9]*$/    //正整数正则表达式
			if(mobileIpt.val().length!=11||mobileIpt.val().substring(0,1)!="1"||!r.test(mobileIpt.val())){
				alert("手机号码有误！")
				mobileIpt.select()
				return false
			}
			if(sel.val()=="身份证"){
				if(certificateNumberIpt.val()==""){
					alert("身份证号码不能为空！")
					certificateNumberIpt.focus()
					return false
				}	
				if(certificateNumberIpt.val().length!=15&&certificateNumberIpt.val().length!=18){
					alert("身份证号码应为15或18位！")
					certificateNumberIpt.select()
					return false
				}	
			}
			if(email.val()!=""){
				if(email.val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) ==-1){
					alert("E-mail填写有误！")
					email.select()
					return false
				}
			}
		}
		//ajax相关数据	
  })