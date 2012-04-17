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
})