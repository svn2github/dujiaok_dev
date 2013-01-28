$(function(){
	ok.tab("dtlBigRoll","mouseover",3000,0);
	$("#hDtlHelp").hover(
		function(){
			var _x=parseInt($(this).position().left,10)-5;
			$(this).next().find("i").css({"left":_x}).end().fadeIn();
		},
		function(){
			$(this).next().hide();	
		}
	);
	
	ok.tab("pro_intro","mouseover",0);
	
	if($("#map").html()!=null){
		var mapC=$("#map"),
			myLatlng = new google.maps.LatLng(mapC.attr("x"),mapC.attr("y")),
			title=mapC.attr("title"),
			myOptions = {
				zoom:18,
				center: myLatlng,
				mapTypeId: google.maps.MapTypeId.ROADMAP,
				disableDefaultUI: true,
			    panControl: false,
				zoomControl: false,
				mapTypeControl: false,
				scaleControl: false,
				streetViewControl: false,
				overviewMapControl: true,
				//mapTypeId: google.maps.MapTypeId.HYBRID,
			},
			map = new google.maps.Map(mapC[0], myOptions);
			marker = new google.maps.Marker({
				position: myLatlng,
				title: title
			});
		marker.setMap(map);	
	}
	
	//日历空间
	$("#idCalendar").each(function(){
		var url = $(this).attr('data-url') ;
		var cale = new Calendar("idCalendar", {
			
	    	SelectDay: new Date().setDate(10),
	    	onSelectDay: function(o){ o.className = "onSelect"; },
	    	onToday: function(o){ o.className = "onToday"; },
	    	onFinish: function(){
	    		var self = this;
	    		$.ajax({
	                url: url ,
	                type: 'GET',
	                error: function(){
	                    alert("get data error!") ;
	                },
	                success: function(data){
	                    try{
	        				//var obj = eval('('+data+')') ;
							var obj = data ;
	        				var calendar = obj.calendar ;
							
							$("#idCalendarYear").html(cale.Year); 
							$("#idCalendarMonth").html(cale.Month);
							var flag = calendar  ;
	                		// var flag = [{severtime:"2012-01",data:[{id:10,name:"250元"},{id:15,name:"250"},{id:16,name:"250"}]},{severtime:"2012-02",data:[{id:12,name:"250元"},{id:14,name:"250"},{id:16,name:"250"}]}];
	                		for(var i = 0, len = flag.length; i < len; i++){
	                			var timer = flag[i].severtime.split("-");
	                			var b = self.Year == timer[0] && self.Month == timer[1]?true:false;
	                			if(b){
	                				var pricredate = flag[i].data;
	                				for(var i = 0; i<pricredate.length; i++){
	                					self.Days[pricredate[i].id].innerHTML =  + pricredate[i].id + "<br>"+pricredate[i].name+"";
	                				}
	                			}
	                		}
							
							
	        			}catch(e){
	        				alert('初始化日历失败！');
	        			}
	                }
	            });
	    	}
	    });
		
		$("#idCalendarPre").click(function(){
			cale.PreMonth();
		});
		
		$("#idCalendarNext").click(function(){
			cale.NextMonth();
		});
	   
	});
})