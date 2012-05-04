function zzsCheck(obj){//成人必须是正整数
	var v=obj.val();
	obj.val(v.replace(/\D/g,''));
	v=obj.val();
	var L=v.length;
	if(v.substr(0,1)=="0"){
		obj.val(v.substr(1,L));
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
}
function calculation(){
	var v=$("#ddIpt1").val();
	var cV=$("#ddIpt2").val();
	var scBxf=$("#dingdanP4").html()*v;
	var bxf=$("#dingdanP5").html()*v;
	var scj=v*$("#dingdanP8").attr("single")+cV*$("#dingdanP3").html();
	var okj=parseInt(v/2)*$("#dingdanP2").html()+(v%2)*$("#dingdanP1").html()+cV*$("#dingdanP3").html();
	var js=(scj-okj)+(scBxf-bxf);
	var zj=okj+bxf;
	$("#dingdanP6").html(v);
	$("#dingdanP7").html(bxf);
	$("#dingdanP8").html(scj);
	$("#dingdanP9").html(okj);
	$("#dingdanP10").html(bxf);
	$("#dingdanP11").html(js);
	$("#dingdanP12").html(zj);
}
$("#ddIpt1").keyup(function(){
	zzsCheck($(this));
	calculation();
})
$("#ddIpt2").keyup(function(){
	ffzsCheck($(this));
	calculation();
})

$("#operateInfo .add").click(function(e){
	e.preventDefault();
	var str='<div class="subContact"><table class="mgt10 contactInfo"><tr><th><i>*</i>游玩人姓名：</th><td><input type="text" class="w184" /></td></tr><tr><th><i>*</i>游玩人手机：</th><td><input type="text" class="w184" maxlength="11" /></td></tr><tr><th><i>*</i>证件类型：</th><td><select id="select" name="select"><option>身份证</option><option>护照</option><option>军官证</option></select></td></tr><tr><th><i>*</i>证件号码：</th><td><input type="text" class="w184" /></td></tr><tr><th>E-mail：</th><td><input type="text" class="w184" /></td></tr><tr><th></th><td><a href="#" class="del">- 删除游玩人</a></td></tr></table></div>';
	$("#operateInfo").before(str);
})
$("#dingdan01 .del").live("click",function(e){
	e.preventDefault();
	var obj=$(this).closest(".subContact");
	obj.remove();
})

$("#dingdan01 :submit").click(function(e){
		e.preventDefault();
		var info=$("#dingdan01 .contactInfo");
		var L=info.size();
		for(var i=0;i<L;i++){
			var ipt=info.eq(i).find("input");
			var sel=info.eq(i).find("select");
			if(ipt.eq(0).val()==""){
				alert("请填写姓名！")
				ipt.eq(0).focus()
				return false
			}
			if(ipt.eq(1).val()==""){
				alert("请填写手机号码！")
				ipt.eq(1).focus()
				return false
			}
			var r=/^[0-9]*[1-9][0-9]*$/    //正整数正则表达式
			if(ipt.eq(1).val().length!=11||ipt.eq(1).val().substring(0,1)!="1"||!r.test(ipt.eq(1).val())){
				alert("手机号码有误！")
				ipt.eq(1).select()
				return false
			}
			if(sel.val()=="身份证"){
				if(ipt.eq(2).val()==""){
					alert("身份证号码不能为空！")
					ipt.eq(2).focus()
					return false
				}	
				if(ipt.eq(2).val().length!=15&&ipt.eq(2).val().length!=18){
					alert("身份证号码应为15或18位！")
					ipt.eq(2).select()
					return false
				}	
			}
			if(ipt.eq(3).val()!=""){
				if(ipt.eq(3).val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) ==-1){
					alert("E-mail填写有误！")
					ipt.eq(3).select()
					return false
				}
			}
		}
		//ajax相关数据	
  })