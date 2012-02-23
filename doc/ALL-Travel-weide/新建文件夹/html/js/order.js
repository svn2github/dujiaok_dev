var EV = xui.util.Event,
	DOM = xui.util.Dom;
var Totalorders = function(){};
Totalorders.prototype = {
	Total:function(){
		var SELF = this;
		if($('Adult_Num')){
			SELF.Add_Products();
		}
		SELF.Adult_price();
	},
	bind:function(){
		var SELF = this;
		SELF.Creat_order();
		SELF.Total();
		EV.on($('Adult_Num'),'blur',function(){
			SELF.Total();
			SELF.Creat_order();
			xui.widgets.Tip.clear();
		})
		EV.on($('Child_Num'),'blur',function(){
			SELF.Total();
		})
		EV.on($('Accident_Insurance'),'click',function(){
			xui.widgets.Tip.clear();										   
			SELF.Total();
			SELF.Creat_order();
		})
		EV.on($('Room_rate'),'click',function(){
			SELF.Total();
		})
		EV.on($('add_person'),'click',function(){
			SELF.Creat_person();
		})
		EV.on($('submit'),'click',function(){
			if($('checkAgreement').checked){
				SELF.checkForm();
			}else{
				return
			}
		})
		EV.on(DOM.getElementsByClassName('wpx184'),'keyup',function(){
			if(this.value == ""){
				xui.widgets.Tip.show(this.id,'带*号为必填项,不能为空!请重新输入。');
			}else{
				xui.widgets.Tip._close(this.id+'china');
			}
		})
		EV.on(DOM.getElementsByClassName('wpx184'),'blur',function(){
			if(this.value == ""){
				xui.widgets.Tip.show(this.id,'带*号为必填项,不能为空!请重新输入。');
			}else{
				xui.widgets.Tip._close(this.id+'china');
			}
		})
		EV.on($('checkAgreement'),'click',function(){
			if($('checkAgreement').checked){
				DOM.removeClass($('submit'),'submitbg');
			}else{
				DOM.addClass($('submit'),'submitbg');
			}
		})
	},
	bind2:function(){
		var EL = DOM.getElementsByClassName('Del_person'),
			EL2 = DOM.getElementsByClassName('person_Order');
		var SELF = this;
		EV.on(EL,'click',function(){
			for(i=0;i<EL.length;i++){
				if(EL[i] == this ){
					$('Creat_order').removeChild(EL2[i]);
					SELF.bind3();
				}
			xui.widgets.Tip.clear();
			}
		})
		EV.on(DOM.getElementsByClassName('wpx184'),'keyup',function(){
			if(this.value == ""){
				xui.widgets.Tip.show(this.id,'带*号为必填项,不能为空!请重新输入。');
			}else{
				xui.widgets.Tip._close(this.id+'china');
			}
		})
	},
	bind3:function(EL){
		var EL = DOM.getElementsByClassName('Del_person');
		if(EL.length == 0){
			$('Creat_order').innerHTML = "";
		}
	},
	bind4:function(){
		EV.on(DOM.getElementsByClassName('wpx184'),'keyup',function(){
			if(this.value == ""){
				xui.widgets.Tip.show(this.id,'带*号为必填项,不能为空!请重新输入。');
			}else{
				xui.widgets.Tip._close(this.id+'china');
			}
		})

	},
	/**
	*订单金额总计数据
	*/
	Adult_price:function(){
		var EL = $('Adult_Num').value,
			EL2 = $('Child_Num').value,
			EL3 = $('Adult_price').innerHTML,
			EL4 = $('Child_price').innerHTML,
			EL5 = $('Insurance_mkprice').innerHTML,
			EL6 = $('Adult_price2').innerHTML,
			EL7 = $('Insurance_okprice').innerHTML,
			EL9 = Math.floor(EL/2),
			EL10 = $('Adult_mkprice').value,
			EL11 = $('Child_mkprice').value;
		var SELF = this;

		/**
		*选择保险
		*/
		var EL8;
		if(EL%2==0){
			EL8 = EL9*EL6;
		}else if(EL%2==1){
			EL8 = EL9*EL6+(EL3-0);
		}
		if($('Accident_Insurance').checked){
			SELF.Adult_price_init(EL10*EL+EL11*EL2+EL5*EL,EL8+EL4*EL2,EL*EL7)
		/**
		*未选择保险
		*/
		}else{
			SELF.Adult_price_init(EL10*EL+EL11*EL2,EL8+EL4*EL2,0)
		}
	},
	Adult_price_init:function(num1,num2,num3){
		$('Price').innerHTML = num1;
		$('difference').innerHTML = num2;
		$('Total_bx').innerHTML = num3;
		$('Total_Save').innerHTML = num1-num2-num3;
		$('Order_Total').innerHTML = num2+num3;
	},
	/**
	*附加产品数据
	*/
	Add_Products:function(){
			$('Insurance_oknum').innerHTML = $('Adult_Num').value-0;
			$('Insurance_okTotal').innerHTML = $('Insurance_oknum').innerHTML*$('Insurance_okprice').innerHTML;
	},
	Creat_order:function(){
		var SELF = this;
		var EL = $('Adult_Num').value,
			EL2 = $('Creat_order');
		if($('Accident_Insurance').checked){
			DOM.addClass($('add_order'),'hid')
			if(EL>=2){
				DOM.removeClass($('Creat_order'),'hid');
				var Contact = [];
				Contact.push('<h3 class="dingdanhead">游玩人信息</h3>');
				for(i=1; i<EL;i++){
					Contact.push('<table class="d_ywrxx"  cellpadding="0" cellspacing="0"><tr><th><i>*</i> 游玩人姓名：</th><td><input type="text" name="erh_Name'+i+'" id="erh_Name'+i+'" class="wpx184" /></td></tr>');
					Contact.push('<tr><th><i>*</i> 游玩人手机：</th><td><input type="text" name="erh_Tel'+i+'" id="erh_Tel'+i+'" class="wpx184" /></td></tr>');
					Contact.push('<tr><th><i>*</i> 证件类型：</th><td><select name="select'+i+'" id="select'+i+'"><option>身份证</option><option>护照</option><option>军官证</optio</select></td></tr>');
					Contact.push('<tr><th><i>*</i> 证件号码：</th><td><input type="text" name="erh_Certificate'+i+'" id="erh_Certificate'+i+'" class="wpx184" /></td></tr>');
					Contact.push('<tr><th>E-mail：</th><td><input type="text" name="erh_Email'+i+'" id="erh_Email'+i+'" class="wpx1843" /></td></tr>');
					Contact.push('</table>')
				}
				EL2.innerHTML = Contact.join('');
				SELF.bind4();
			}else{
				$('Creat_order').innerHTML = "";
			}
		}else{
			$('Creat_order').innerHTML = "";
			DOM.removeClass($('add_order'),'hid');
		}
	},
	Creat_person:function(){
		var SELF = this;
		var EL =  $('Creat_order').getElementsByTagName('table');
			EL2 = $('Creat_order');
		var Contact = [];
		Contact.push('<h3 class="dingdanhead">游玩人信息</h3>');
		for(i=1; i<EL.length+2;i++){
			Contact.push('<div class="person_Order"><div style="clear:both;"></div>');
			Contact.push('<table class="d_ywrxx"  cellpadding="0" cellspacing="0"><tr><th><i>*</i> 游玩人姓名：</th><td><input type="text" name="erh_Name'+i+'" id="erh_Name'+i+'" class="wpx184" /></td></tr>');
			Contact.push('<tr><th><i>*</i> 游玩人手机：</th><td><input type="text" name="erh_Tel'+i+'" id="erh_Tel'+i+'" class="wpx184" /></td></tr>');
			Contact.push('<tr><th><i>*</i> 证件类型：</th><td><select name="select'+i+'" id="select'+i+'"><option>身份证</option><option>护照</option><option>军官证</optio</select></td></tr>');
			Contact.push('<tr><th><i>*</i> 证件号码：</th><td><input type="text" name="erh_Certificate'+i+'" id="erh_Certificate'+i+'" class="wpx184" /></td></tr>');
			Contact.push('<tr><th>E-mail：</th><td><input type="text" name="erh_Email'+i+'" id="erh_Email'+i+'" class="wpx1843" /></td></tr>');
			Contact.push('</table>')
			Contact.push('<div style="float:left; padding:0 0 30px 100px;"><a href="javascript:" class="Del_person">- 删除游玩人</a></div></div>')
		}
		EL2.innerHTML = Contact.join('');
		DOM.removeClass($('Creat_order'),'hid');
		SELF.bind2();
	},
	checkForm:function(){
		var SELF = this;
		var EL = DOM.getElementsByClassName('wpx184');
		xui.widgets.Tip.clear();
		for (i=0; i<EL.length; i++){
			if(EL[i].value==""){
				xui.widgets.Tip.show(EL[i].id,'带*号为必填项,不能为空!请重新输入。');
			}else if(EL[i] == $("erh_Tel0")&!xui.util.NumberUtil.isNumber(EL[i].value)){
				xui.widgets.Tip.show(EL[i].id,'手机号码必须为11位数字。');
			}else if(EL[i] == $("erh_Tel0")&EL[i].value.length!==11){
				xui.widgets.Tip.show(EL[i].id,'手机号码必须为11位数字。');
			}
		}
		
		SELF.postData();
	},
	checkForm1:function(){
		var EL = DOM.getElementsByClassName('wpx184');
		for (i=0; i<EL.length; i++){
			if(EL[i].value==""||!$('checkAgreement').checked||!xui.util.NumberUtil.isNumber($("erh_Tel0").value)||$("erh_Tel0").value.length!==11){
				return false;
			}
		}
		return false;
	},
	postData:function(){
		var SELF = this;
		SELF.value = "";
		var EL = $('Adult_Num').value;
		for (i=0;i<EL; i++){
			if(SELF.value){
				SELF.value = SELF.value+',\{name:\"'+$('erh_Name'+i).value+'\",tel:\"'+$('erh_Tel'+i).value+'\",select:\"'+$('select'+i).value+'\",Certificate:\"'+$('erh_Certificate'+i).value+'\",Email:\"'+$('erh_Email'+i).value+'\"\}';
			}else{
				SELF.value ='[{name:\"'+$('erh_Name0').value+'\",tel:\"'+$('erh_Tel0').value+'\",select:\"'+$('select0').value+'\",Certificate:\"'+$('erh_Certificate0').value+'\",Email:\"'+$('erh_Email0').value+'\"\}';
			}			
		}
		console.log(SELF.value+"]");
		return false;
	
	}
		
};					   
EV.onDOMReady(function(){
	var b = new Totalorders();
	b.bind();
});  