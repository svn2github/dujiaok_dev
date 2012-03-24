//判断时间是否合理开始--czz
	function judgeTime(){
		var slcS=jQuery(".line_sep");						  
		var L=slcS.size();
		var today=new Date();
		today=parseInt(String(today.getFullYear())+String(today.getMonth()+1)+String(today.getDate()));
		for(var i=0;i<L;i++){
			var slc=slcS.eq(i).find(".xui-dp-value");
			var t1=parseInt(slc.eq(0).html().replace(/-/g,""),10);
			var t2=parseInt(slc.eq(1).html().replace(/-/g,""),10);
			if(t1>t2){
				slc.addClass("redBox");
				alert("结束时间早于开始时间!");
				return false;
			}
			else{
				slc.removeClass("redBox");
			}
			if(t1<today){
				slc.addClass("redBox");
				alert("开始时间已过期!");
				return false;
			}
			else{
				slc.removeClass("redBox");
			}
		}
		for(var i=0;i<L;i++){//判断时间段是否重叠
			var slc=slcS.eq(i).find(".xui-dp-value");
			var t1=parseInt(slc.eq(0).html().replace(/-/g,""),10);
			var t2=parseInt(slc.eq(1).html().replace(/-/g,""),10);
			for(var j=i+1;j<L;j++){
				var slcNext=slcS.eq(j).find(".xui-dp-value");
				var t1Next=parseInt(slcNext.eq(0).html().replace(/-/g,""),10);
				var t2Next=parseInt(slcNext.eq(1).html().replace(/-/g,""),10);
				if(t1Next>=t1&&t1Next<=t2){
					slc.addClass("redBox");
					slcNext.addClass("redBox");
					alert("时间段重叠!");
					return false;
				}
				else{
					slc.removeClass("redBox");
					slcNext.removeClass("redBox");
				}
			}
		}
	}
	//判断时间是否合理结束--czz