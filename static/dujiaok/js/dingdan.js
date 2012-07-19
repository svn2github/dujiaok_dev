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
	if($("#dingdanP6").val() > v){
		$("#dingdanP6").val(v) ;
	}
	
	var scBxf=$("#dingdanP4").html()*$("#dingdanP6").val()*orderDays; //市场保险费用
	var bxf=$("#dingdanP5").html()*$("#dingdanP6").val()*orderDays;//保险费用
	var scj=(parseInt(v)+parseInt(cV))*marketPrice;//市场价
	var okj=parseInt(v/2)*$("#dingdanP2").html()+(v%2)*$("#dingdanP1").html()+cV*$("#dingdanP3").html();//OK价
	var js=(scj-okj)+(scBxf-bxf); //节省
	var zj=okj+bxf; //总价
	//$("#dingdanP6").val(v);
	
	$("#dingdanP7").html(bxf);
	$("#dingdanP8").html(scj);
	$("#dingdanP9").html(okj);
	$("#dingdanP10").html(bxf);
	$("#dingdanP11").html(js);
	$("#dingdanP12").html(zj);
	$("#baoxianzongjia").html(bxf) ;
}

function calcu(){
	var v=$("#roomNum").val();
	if(v == null || v == ''){
		v = $("#ticketNum").val() ;
	}
	var scj=v*$("#marketPrice").html();
	var okj=v*$("#price").html();
	var js=scj-okj;
	$("#orderMarketPrice").html(scj);
	$("#orderOkPrice").html(okj);
	$("#orderSavePrice").html(js);
	$("#orderPrice").html(okj);
}

$("#dingdanP6").change(function(){
	calculation();
	addContacts() ;
})

$("#ddIpt1").keyup(function(){
	zzsCheck($(this));
	calculation();
	addContacts() ;
})
$("#ddIpt2").keyup(function(){
	ffzsCheck($(this));
	calculation();
})
$("#roomNum").keyup(function(){
	zzsCheck($(this));
	calcu();
})
$("#ticketNum").keyup(function(){
	zzsCheck($(this));
	calcu();
})

$("[data-orderForm]").click(function(){
	$("#" + $(this).attr("data-orderForm")).submit() ;
});

//dom ready
$(function(){
	calculation();
	addContacts() ;
	calcu();
})

var addContacts = function (i){
	var v=$("#dingdanP6").val();
	$("#subCBox").html("");	
	if(v>1){
		s="";
		for(var i=1;i<v;i++){
			var contactStr='<div class="subContact"><table class="mgt10 contactInfo"><tr><th><i>*</i>游玩人姓名：</th><td><input type="text" class="w184 name" name="contacts[' + i + '].name"/><span class="error"></span></td></tr><tr><th><i>*</i>游玩人手机：</th><td><input type="text" class="w184 mobile" maxlength="11" name="contacts[' + i + '].mobile"/><span class="error"></span></td></tr><tr><th><i>*</i>证件类型：</th><td><select id="select" name="contacts[' + i + '].certificateType"><option value="身份证">身份证</option><option value="护照">护照</option><option value="军官证">军官证</option></select></td></tr><tr><th><i>*</i>证件号码：</th><td><input type="text" class="w184 certificateNumber" name="contacts[' + i + '].certificateNumber"/><span class="error"></span></td></tr><tr><th>E-mail：</th><td><input type="text" class="w184 email" name="contacts[' + i + '].email"/><span class="error"></span></td></tr><tr><th></th><td></td></tr></table></div>';
			s += contactStr;
		}
		$("#subCBox").html(s);	
	}
}
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

$("#orderForm").on("submit",function(e){
		e.preventDefault();
		var info=$("#orderForm .contactInfo");
		var L=info.size();
		var hasErr=0;
		for(var i=0;i<L;i++){
			var ipt=info.eq(i).find("input");
			var sel=info.eq(i).find("select");
			if(ipt.eq(0).val()==""){
				hasErr ++ ;
				ipt.eq(0).next().html("请填写姓名！");
			}
			else{
				ipt.eq(0).next().html("");
			}
			if(ipt.eq(1).val()==""){
				hasErr ++ ;
				ipt.eq(1).next().html("请填写手机号码！");
			}
			else{
				ipt.eq(1).next().html("");
			}
			
			var r=/^[0-9]*[1-9][0-9]*$/    //正整数正则表达式
			if(ipt.eq(1).val()!=""){
				if(ipt.eq(1).val().length!=11||ipt.eq(1).val().substring(0,1)!="1"||!r.test(ipt.eq(1).val())){
					hasErr ++ ;
					ipt.eq(1).next().html("手机号码有误！");
				}
				else{
					ipt.eq(1).next().html("");
				}
			}
			if(sel.val()=="身份证"){
//				if(ipt.eq(2).val()==""){
//					hasErr ++ ;
//					ipt.eq(2).next().html("身份证号码不能为空！");
//				}
//				else{
//					ipt.eq(2).next().html("");
//				}
				if(ipt.eq(2).val()!=""){
					if(ipt.eq(2).val().length!=15&&ipt.eq(2).val().length!=18){
						hasErr ++ ;
						ipt.eq(2).next().html("身份证号码应为15或18位！");
					}
					else{
						ipt.eq(2).next().html("");
					}
				}
			}
			else{
				if(ipt.eq(2).val()==""){
					hasErr ++ ;
					ipt.eq(2).next().html("证件号码不能为空！");
				}
				else{
					ipt.eq(2).next().html("");
				}
			}
			if(ipt.eq(3).val()!=""){
				if(ipt.eq(3).val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) ==-1){
					hasErr ++ ;
					ipt.eq(3).next().html("E-mail填写有误！");
				}
				else{
					ipt.eq(3).next().html("");
				}
			} else{
				ipt.eq(3).next().html("");
			}
			
		}
		if(hasErr >= 1){
			return false;
		}
		//$(this).closest("form").submit();
		//ajax相关数据	
		return true ;
  })