function zzsCheck(obj){//���˱�����������
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
function ffzsCheck(obj){//��ͯ�����ǷǸ�����
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
	var v=$("#ddIpt1").val(); //������
	var cV=$("#ddIpt2").val(); //��ͯ��
	var orderDays = $("#orderDays").html() ; //��������
	var single = $("#dingdanP1").html() ;
	var double = $("#dingdanP2").html() ;
	var marketPrice = $("#marketPrice").html() ;
	
	var scBxf=$("#dingdanP4").html()*v*orderDays; //�г����շ���
	var bxf=$("#dingdanP5").html()*v;//���շ���
	var scj=v*marketPrice+cV*marketPrice;//�г���
	var okj=parseInt(v/2)*$("#dingdanP2").html()+(v%2)*$("#dingdanP1").html()+cV*$("#dingdanP3").html();//OK��
	var js=(scj-okj)+(scBxf-bxf); //��ʡ
	var zj=okj+bxf; //�ܼ�
	$("#dingdanP6").html(v);
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
var contactStr='<div class="subContact"><table class="mgt10 contactInfo"><tr><th><i>*</i>������������</th><td><input type="text" class="w184" /></td></tr><tr><th><i>*</i>�������ֻ���</th><td><input type="text" class="w184" maxlength="11" /></td></tr><tr><th><i>*</i>֤�����ͣ�</th><td><select id="select" name="select"><option>���֤</option><option>����</option><option>����֤</option></select></td></tr><tr><th><i>*</i>֤�����룺</th><td><input type="text" class="w184" /></td></tr><tr><th>E-mail��</th><td><input type="text" class="w184" /></td></tr><tr><th></th><td><a href="#" class="del">- ɾ��������</a></td></tr></table></div>';
$("#ddIpt1").keyup(function(){
	zzsCheck($(this));
	calculation();
	var v=$(this).val();
	$("#subCBox").html("");	
	if(v>1){
		s="";
		for(var i=1;i<v;i++){
			s+=contactStr;
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

$("#operateInfo .add").click(function(e){
	e.preventDefault();
	$("#subCBox").append(contactStr);
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
				alert("����д������")
				ipt.eq(0).focus()
				return false
			}
			if(ipt.eq(1).val()==""){
				alert("����д�ֻ����룡")
				ipt.eq(1).focus()
				return false
			}
			var r=/^[0-9]*[1-9][0-9]*$/    //������������ʽ
			if(ipt.eq(1).val().length!=11||ipt.eq(1).val().substring(0,1)!="1"||!r.test(ipt.eq(1).val())){
				alert("�ֻ���������")
				ipt.eq(1).select()
				return false
			}
			if(sel.val()=="���֤"){
				if(ipt.eq(2).val()==""){
					alert("���֤���벻��Ϊ�գ�")
					ipt.eq(2).focus()
					return false
				}	
				if(ipt.eq(2).val().length!=15&&ipt.eq(2).val().length!=18){
					alert("���֤����ӦΪ15��18λ��")
					ipt.eq(2).select()
					return false
				}	
			}
			if(ipt.eq(3).val()!=""){
				if(ipt.eq(3).val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) ==-1){
					alert("E-mail��д����")
					ipt.eq(3).select()
					return false
				}
			}
		}
		//ajax�������	
  })