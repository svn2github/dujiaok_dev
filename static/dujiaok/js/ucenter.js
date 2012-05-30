$(function(){
	//ok.placeholder("addrDtl");
	areaSelect.createSelect($("#area")); 
	var account=[{type:"支付宝"},{type:"财付通"},{type:"银行卡",list:["招商银行","建设银行","交通银行","工商银行"]}];
	var L=account.length;
	var opts='';
	for(var i=0;i<L;i++){
		opts+='<option value=' + account[i].type + '>'+account[i].type+'</option>';
	}
	$("#accountType1").html(opts);
	$("#accountType1").change(function(){
		opts=$(this).find("option");
		var cur=$(this).find(":selected");
		var i=opts.index(cur);
		if(account[i].list){
			var arr=account[i].list;
			var L=arr.length;
			opts='';
			for(var i=0;i<L;i++){
				opts+='<option value=' + arr[i] + '>'+arr[i]+'</option>';
			}
			$("#accountType2").html(opts).show();	
		}
		else{
			$("#accountType2").hide();		
		}
	})
	$("#userInfo :submit").click(function(e){
		e.preventDefault();
		var ipt=$("#userInfo input");
		var sel=$("#userInfo select");
		if(ipt.eq(0).val()==""){
			alert("请填写昵称！")
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
		if(ipt.eq(2).val()==""){
			alert("请输入E-mail！")
			ipt.eq(2).focus()
			return false
		}
		if(ipt.eq(2).val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) ==-1){
			alert("E-mail填写有误！")
			ipt.eq(2).select()
			return false
		}
		if(ipt.eq(3).val()==""){
			alert("请输入账户！")
			ipt.eq(3).focus()
			return false
		}
		if(ipt.eq(4).val()==""){
			alert("请输入户名！")
			ipt.eq(4).focus()
			return false
		}
		if(sel.eq(2).val()==-1){
			alert("请选择邮寄地址所属省份！")
			sel.eq(2).focus()
			return false
		}
		if(sel.eq(3).val()==-1){
			alert("请选择邮寄地址所属地区！")
			sel.eq(3).focus()
			return false
		}
		if(sel.eq(4).val()==-1){
			alert("请选择邮寄地址所属城区！")
			sel.eq(4).focus()
			return false
		}
		if(ipt.eq(5).val()==""||ipt.eq(5).val()==ipt.eq(5).attr("placeholder")){
			alert("请输入详细地址！")
			ipt.eq(5).focus()
			return false
		}
		if(ipt.eq(5).val().length<4){
			alert("详细地址过于简单，请填写详细！")
			ipt.eq(5).select()
			return false
		}
		if(ipt.eq(6).val()==""){
			alert("请输入邮编！")
			ipt.eq(6).focus()
			return false
		}
		if(ipt.eq(6).val().length!=6||!r.test(ipt.eq(6).val())){
			alert("邮编输入有误！")
			ipt.eq(6).select()
			return false
		}
		if(ipt.eq(7).val()==""){
			alert("请输入姓名！")
			ipt.eq(7).focus()
			return false
		}
		//ajax相关数据	
	})
	
})