$(function(){
	ok.tab("dtlBigRoll","mouseover",3000,1);
	$("#hDtlHelp").hover(
		function(){
			var _x=parseInt($(this).position().left,10)-5;
			$(this).next().find("i").css({"left":_x}).end().fadeIn();
		},
		function(){
			$(this).next().hide();	
		}
	)
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
})