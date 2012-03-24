var $ = function(id){
	return document.getElementById(id) ;
}

var ok;
(function($){
	ok={
		tab:function(id,event,delay,effect){//tabл(ֲ)
			var i=0;
			var ttlS=$("#"+id+" .tabTtl li");
			var cntS=$("#"+id+" .tabCnt");
			function showCur(j){
				var N=ttlS.size();
				var cur=ttlS.eq(j);
				ttlS.removeClass("current");
				cur.addClass("current");
				cntS.hide();
				if(effect){
					cntS.eq(j).hide().fadeIn();	
				}
				else{
					cntS.eq(j).show();	
				}
				i++;
				if(i==N){
					i=0;
				};
			};
			if(!event){
				event="click";
			};
			ttlS.bind(event,function(){
				clearInterval(t);
				i=$(this).index();
				showCur(i);
				if(delay){
					setTimeout(function(){t=setInterval(function(){showCur(i)},delay);},delay);
				};
			});
			if(delay){
				var t=setInterval(function(){showCur(i)},delay);
			};
		},
		placeholder:function(id){//ʾ
			var ipt=jQuery("#"+id);
			var p=ipt.attr("placeholder");
			ipt.each(function(){
				var v=ipt.val();
				if(v!=p){
					ipt.removeClass("gray");
				};
			});
			ipt.focus(function(){
				var v=ipt.val();
				if(v==p){
					ipt.val("")	;
				};
				ipt.removeClass("gray");
			});
			ipt.blur(function(){
				var v=ipt.val();
				if(v==""){
					ipt.val(p);
					ipt.addClass("gray");
				};
			});
		},
		checkPage:function(){//ҳ
			function check(){
				var ipt=$("#page input");
				var v=ipt.val();
				var re = /^[0-9]*[1-9][0-9]*$/ ;
				if(!re.test(v)||v.substr(0,1)=="0"){
					alert("请输入正确的页码");
					ipt.focus();
				};
			};	
			$("#page button").click(function(){
				check();							 
			});
			$("#page input").keydown(function(e){    
				if(e.keyCode==13){
					check();	
				}							  
			});
		},
		selMock:function(){//ģselect
			function show(obj){
				obj.show();
				obj.find("li").removeClass("over");
				var txt=obj.prev().val();
				obj.find("li").filter(function(){return $(this).html()==txt}).addClass("over");
			};
			function upDown(option,key){
				var overOpt=option.filter(".over");
				var i=option.index(overOpt);
				var L=option.size();
				if(key="down"){
					if(i==L-1){
						i=-1;	
					}
					i++;
				}
				else{
					i--;
				}
				option.removeClass("over");
				var curLi=option.eq(i);
				curLi.addClass("over");
				option.parent().prev().val(curLi.html());
			};
			$(".select input").click(function(e){
				e.stopPropagation();							  
				$(".select ul").hide();							  
				var option=$(this).next();
				if(option.css("display")=="none"){
					show(option);
				}
				else{
					option.hide();
				}
			});
			$(".select input").focus(function(e){
				$(".select ul").hide();							  
				var option=$(this).next();
				show(option);
			});
			$(".select input").keyup(function(e){
				var option=$(this).next().find("li");	
				if(e.keyCode==40){
					upDown(option,"down");
				};
				if(e.keyCode==38){
					upDown(option,"up");
				};
				if(e.keyCode==13){
					$(this).val(option.filter(".over").html());
					option.parent().hide();	
				};
			});
			$(".select li").click(function(){
				var html=$(this).html();
				var par=$(this).parent();
				var sel=par.prev();
				sel.val(html);
				par.hide();
			});
			$(".select li").mouseover(function(){
				$(this).parent().find("li").removeClass("over");
				$(this).addClass("over");
			});
			$(document).click(function(){
				$(".select ul").hide();	
			})
		}
	};
})(jQuery);
//JS
$(function(){
	$("#myOrder").hover(
		function(){
			$("#orderPop").show();
		},
		function(){
			$("#orderPop").hide();
		}
	);
	$("#hotPurpose").hover(
		function(){
			$("#hotPurpose ul").addClass("noRbd");
			$("#hotPurpose h2").addClass("current");
			$("#hotPurpose h3").addClass("hasRbd");
			$(this).find("ul").show();
			$(this).find("li.first").addClass("current");
			$(this).find("li.first .outer").show();
		},
		function(){
			$("#hotPurpose h2").removeClass("current");
			$(this).find("ul").hide();
		}
	);
	$("#hotPurpose li").not(".last").hover(
		function(){
			if($(this).index()!=0){
				$(this).prev().addClass("curPrev")
			};
			$("#hotPurpose ul").addClass("noRbd");
			$("#hotPurpose h3").addClass("hasRbd");
			$(this).addClass("current");
			$(this).find(".outer").show();
		},
		function(){
			$(this).prev().removeClass("curPrev");
			$("#hotPurpose ul").removeClass("noRbd");
			$("#hotPurpose h3").removeClass("hasRbd");
			$(this).removeClass("current");
			$(this).find(".outer").hide();
		}
	);
	$("#hotPurpose .close").click(function(e){
		e.preventDefault();
		$(this).parent().hide();
	});
	ok.placeholder("schIpt");
})