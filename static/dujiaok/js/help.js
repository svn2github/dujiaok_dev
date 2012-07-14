$(function(){
	$("#helpMenu h3").click(function(){
		var h3=$(this),
			h4=h3.next(),
			cls=h3.hasClass("click");
		if(cls){
			h3.removeClass("click");
			h4.removeClass("show");
		}
		else{
			h3.addClass("click");
			h4.addClass("show");	
		}
	})
})