$(function(){
	ok.placeholder("addrDtl");
	areaSelect.createSelect($("#area")); 
	var account=[{type:"֧����"},{type:"�Ƹ�ͨ"},{type:"���ÿ�",list:["��������","��������","��ͨ����","��������"]},{type:"��������",list:["�й�����","ũҵ����"]}];
	var L=account.length;
	var opts='';
	for(var i=0;i<L;i++){
		opts+='<option>'+account[i].type+'</option>';
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
				opts+='<option>'+arr[i]+'</option>';
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
			alert("����д�ǳƣ�")
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
		if(ipt.eq(2).val()==""){
			alert("������E-mail��")
			ipt.eq(2).focus()
			return false
		}
		if(ipt.eq(2).val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) ==-1){
			alert("E-mail��д����")
			ipt.eq(2).select()
			return false
		}
		if(ipt.eq(3).val()==""){
			alert("�������˻���")
			ipt.eq(3).focus()
			return false
		}
		if(ipt.eq(4).val()==""){
			alert("�����뻧����")
			ipt.eq(4).focus()
			return false
		}
		if(sel.eq(2).val()==-1){
			alert("��ѡ���ʼĵ�ַ����ʡ�ݣ�")
			sel.eq(2).focus()
			return false
		}
		if(sel.eq(3).val()==-1){
			alert("��ѡ���ʼĵ�ַ����������")
			sel.eq(3).focus()
			return false
		}
		if(sel.eq(4).val()==-1){
			alert("��ѡ���ʼĵ�ַ����������")
			sel.eq(4).focus()
			return false
		}
		if(ipt.eq(5).val()==""||ipt.eq(5).val()==ipt.eq(5).attr("placeholder")){
			alert("��������ϸ��ַ��")
			ipt.eq(5).focus()
			return false
		}
		if(ipt.eq(5).val().length<4){
			alert("��ϸ��ַ���ڼ򵥣�����д��ϸ��")
			ipt.eq(5).select()
			return false
		}
		if(ipt.eq(6).val()==""){
			alert("�������ʱ࣡")
			ipt.eq(6).focus()
			return false
		}
		if(ipt.eq(6).val().length!=6||!r.test(ipt.eq(6).val())){
			alert("�ʱ���������")
			ipt.eq(6).select()
			return false
		}
		if(ipt.eq(7).val()==""){
			alert("������������")
			ipt.eq(7).focus()
			return false
		}
		//ajax�������	
	})
	
})