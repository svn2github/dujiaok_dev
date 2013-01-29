var ok;
(function($){
	ok={
		tab:function(id,eve,delay,effect){
			var i=0,
            	t=null,
				ttlS=$("#"+id+" .tabTtl li"),
				cntS=$("#"+id+" .tabCnt");
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
			if(!eve){
				eve="click";
			};
			ttlS.bind(eve,function(){
				clearInterval(t);
				i=$(this).index();
				showCur(i);
				if(delay){
					t=setInterval(function(){showCur(i)},delay);
				};
			});
			if(delay){
				t=setInterval(function(){showCur(i)},delay);
			};
		},
		placeholder:function(id){//示
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
		checkPage:function(){//页
			function check(){
				var ipt=$("#page input");
				var v=ipt.val();
				var re = /^[0-9]*[1-9][0-9]*$/ ;
				if(!re.test(v)||v.substr(0,1)=="0"){
					alert("璇疯緭鍏ユ纭殑椤电爜");
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
		selMock:function(){//模select
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
		},
		addFav:function(){
			var url=location.href;
			var title=$("title").html();
			if (document.all) {
				window.external.addFavorite(url,title);
			}
			else if(window.sidebar) {
				window.sidebar.addPanel(title,url,"");
			}
		},
		float:function(){
			if($("#w950")[0]){
				var x=$("#w950").offset().left+950+10,
					_x=x+120,
					W=$(document).width(),
					css={"left":x},
					f=$("#float");
				if(_x>W){
					css={"right":0};
				}
				f.css(css);
				f.find("b").click(function(e){
					f.remove();
				})
				$("#addFav").click(function(e){
					e.preventDefault();
					ok.addFav();
				})
				$("#backTop").click(function(e){
					e.preventDefault();
					function autoscroll(){
						var t=null,
							sT=$(window).scrollTop();
						if(sT>0){
							$(window).scrollTop(sT-20);
							t=setTimeout(autoscroll,5);  
						} 
						else{
							clearTimeout(t);	
						} 
					}
					autoscroll();
				})
				if($.browser.msie&&$.browser.version==6){
					$(window).scroll(function(){
						var t=parseInt($(window).scrollTop(),10)+164;
						f.css({"top":t});
					})
				}
			}
			
			
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
	
	//主页和频道页搜索
	$("[data-searchForm]").click(function(e){
		e.preventDefault();
		var formId = $(this).attr("data-searchForm") ;
		$("#"+formId).submit() ;
	}) ;
	ok.placeholder("schIpt");
	ok.float();
	
	if($("[data-hot-city-select]").size() > 0){
		var url = $("#envRoot").val() + '/ajax/hot_city_ajax.htm' ;
		$.ajax({
			url : url,
			type: 'GET',
			success:function(data){
				var code = data.json.code ;
				var data = data.json.data ;
				if(code == 'success'){
					
					$("[data-hot-city-select]").each(function(){
						var _this = $(this) ;
						if(!_this.is('select')){
							return ;
						}
						
						_this.empty() ;
						_this.append('<option value="">全部</option>')
						for(var i = 0 ; i< data.length ; i++){
							var hotCity = data[i] ;
							var op = '<option value=' + hotCity.cityName + '>' + hotCity.cityName + '</option>'
							_this.append(op) ;
						}
						
					}) ;
					
				}
			} ,
			error:function(){
				alert('初始化热门到达地失败！');
			}
		});
		
		
	}
})