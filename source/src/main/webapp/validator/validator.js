/*
	1:	// Element
	2:  // ATTRIBUTE
	3:	// Text
	4:	// CDATA
	5:  // ENTITY_REFERENCE
	6:  // ENTITY
	7:  // ProcessInstruction
	8:  // COMMENT
	9:	// Document
	10: // DOCUMENT_TYPE
	11: // DOCUMENT_FRAGMENT
	12: // NOTATION
*/
var XmlHttp = function() {
	this.xmlDoc = null;

};

XmlHttp.prototype.newInstance = function() {

	function getXmlHttpPrefix() {
		if (getXmlHttpPrefix.prefix)
			return getXmlHttpPrefix.prefix;
		
		var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
		var o;
		for (var i = 0; i < prefixes.length; i++) {
			try {
				// try to create the objects
				o = new ActiveXObject(prefixes[i] + ".XmlHttp");
				return getXmlHttpPrefix.prefix = prefixes[i];
			}
			catch (ex) {};
		}
		
		throw new Error("Could not find an installed XML parser");
	}

	try {
		if (window.XMLHttpRequest) {
			var req = new XMLHttpRequest();
			
			// some versions of Moz do not support the readyState property
			// and the onreadystate event so we patch it!
			if (req.readyState == null) {
				req.readyState = 1;
				req.addEventListener("load", function () {
					req.readyState = 4;
					if (typeof req.onreadystatechange == "function")
						req.onreadystatechange();
				}, false);
			}
			
			return req;
		}
		if (window.ActiveXObject) {
			return new ActiveXObject(getXmlHttpPrefix() + ".XmlHttp");
		}
	}
	catch (ex) {}
	// fell through
	throw new Error("Your browser does not support XmlHttp objects");
};

XmlHttp.prototype.loadXML = function(xml,bAsync) {
	var xmlHttp = this.newInstance();

	xmlHttp.open("GET", xml, bAsync);
	if (bAsync) {
		xmlHttp.onreadystatechange = function () {
			if (xmlHttp.readyState == 4)
				this.xmlDoc = xmlHttp.responseXML;
		}
	}
	xmlHttp.send(null);
	if (!bAsync) {
		this.xmlDoc = xmlHttp.responseXML;
	}
};

XmlHttp.prototype.selectNodes = function(xpath){		
	if (document.all)		// IE
		return this.xmlDoc.selectNodes(xpath) ;
	else{					// Gecko
		var aNodeArray = new Array();
		var xPathResult = this.xmlDoc.evaluate(xpath, this.xmlDoc, this.xmlDoc.createNSResolver(this.xmlDoc.documentElement), XPathResult.ORDERED_NODE_ITERATOR_TYPE, null) ;
		if (xPathResult){
			var oNode = xPathResult.iterateNext() ;
			while(oNode){
				aNodeArray[aNodeArray.length] = oNode ;
				oNode = xPathResult.iterateNext();
			}
		} 
		return aNodeArray ;
	}
};

XmlHttp.prototype.selectSingleNode = function(xpath){
	if (document.all)		// IE
		return this.xmlDoc.selectSingleNode( xpath ) ;
	else{					// Gecko
		var xPathResult = this.xmlDoc.evaluate( xpath, this.xmlDoc,
				this.xmlDoc.createNSResolver(this.xmlDoc.documentElement), 9, null);
		if (xPathResult && xPathResult.singleNodeValue)
			return xPathResult.singleNodeValue ;
		else	
			return null ;
	}
};

// Create the loadXML method and xml getter for Mozilla
if (window.DOMParser &&
	window.XMLSerializer &&
	window.Node && Node.prototype && Node.prototype.__defineGetter__) {

	// XMLDocument did not extend the Document interface in some versions
	// of Mozilla. Extend both!
	//XMLDocument.prototype.loadXML = 
	Document.prototype.loadXML = function (s) {
		
		// parse the string to a new doc	
		var doc2 = (new DOMParser()).parseFromString(s, "text/xml");
		
		// remove all initial children
		while (this.hasChildNodes())
			this.removeChild(this.lastChild);
			
		// insert and import nodes
		for (var i = 0; i < doc2.childNodes.length; i++) {
			this.appendChild(this.importNode(doc2.childNodes[i], true));
		}
	};
	
	
	/*
	 * xml getter
	 *
	 * This serializes the DOM tree to an XML String
	 *
	 * Usage: var sXml = oNode.xml
	 *
	 */
	// XMLDocument did not extend the Document interface in some versions
	// of Mozilla. Extend both!
	/*
	XMLDocument.prototype.__defineGetter__("xml", function () {
		return (new XMLSerializer()).serializeToString(this);
	});
	*/
	Document.prototype.__defineGetter__("xml", function () {
		return (new XMLSerializer()).serializeToString(this);
	});
};

// check object is contains Array
Array.prototype.contains = function(object) {
	for(var i=0;i<this.length;i++){
		if(this[i] == object){
			return true;
		}
	}
}
String.prototype.trim=function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
}

Validator = {
	Require : /.+/,
	Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
	Phone : /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
	Mobile : /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/,
	Url : /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
	IP : /^(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5]).(0|[1-9]\d?|[0-1]\d{2}|2[0-4]\d|25[0-5])$/,
	IdCard : "Validator.IsIdCard(value)",
	Currency : /^\d+(\.\d+)?$/,
	Number : /^\d+$/,
	Zip : /^[1-9]\d{5}$/,
	QQ : /^[1-9]\d{4,8}$/,
	Integer : /^[-\+]?\d+$/,
	Double : /^[-\+]?\d+(\.\d+)?$/,
	English : /^[A-Za-z]+$/,
	Chinese :  /^[\u0391-\uFFE5]+$/,
	Username : /^[a-z]\w{3,}$/i,
	UnSafe : /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
	IsSafe : function(str){return !this.UnSafe.test(str);},
	SafeString : "Validator.IsSafe(value)",
	Filter : "Validator.DoFilter(value, getAttribute('accept'))",
	Limit : "Validator.limit(value.length,getAttribute('min'),  getAttribute('max'))",
	LimitB : "Validator.limit(Validator.LenB(value), getAttribute('min'), getAttribute('max'))",
	Date : "Validator.IsDate(value, getAttribute('min'), getAttribute('format'))",
	Repeat : "value == document.getElementsByName(getAttribute('to'))[0].value",
	Range : "getAttribute('min') < (value|0) && (value|0) < getAttribute('max')",
	Compare : "Validator.compare(value,getAttribute('operator'),getAttribute('to'))",
	Custom : "Validator.Exec(value, getAttribute('regexp'))",
	Group : "Validator.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
	ErrorItem : [document.forms[0]],
	ErrorMessage : ["以下原因导致提交失败：\t\t\t\t"],
	/**
	 * theForm : form
	 * xmlFile : the xmlfile to load , if xmlFile='member' will load validation-member.xml , if null use default validation.xml
	 */
	validate : function(theForm,xmlFile,mode){
		this.obj = theForm || event.srcElement;
		this.ErrorMessage.length = 1;
		this.ErrorItem.length = 1;
		this.ErrorItem[0] = this.obj;
		var nameArray = new Array();
		// read xml by ajax
		var xmlHttp = new XmlHttp();
		xmlFile = (typeof(xmlFile)=='undefined') ? "validation.xml" : "./validation_"+xmlFile+".xml";
		xmlHttp.loadXML(xmlFile,false);
		// var xPath = "//page[@name='" + location.pathname.toLowerCase().substring(location.pathname.toLowerCase().lastIndexOf("/")+1) + "']/form[@id='" + obj.id + "']/item";
		var formId = (document.all) ? this.obj.getAttributeNode("id").value : this.obj.getAttribute("id");
		var xPath = "//form[@id='"+formId+"']/item";
		var items = xmlHttp.selectNodes(xPath);
		// alert("xPath = " + xPath );
		var iLen = items.length;
		for(var i=0;i<iLen;i++){
			with(items[i]){
				var _dataType = getAttribute("dataType");
				var value ;
				//alert(" dataType: " +_dataType + " -- name: " + getAttribute("name"));
				// validate fckeditor value
				if(_dataType=="Fckeditor"){
					Validator.getObject(getAttribute("name"));
					value = FCKeditorAPI.GetInstance(getAttribute("name")).GetXHTML(true);
					if(value==null || ""==value || ("<P>&nbsp;</P>"==value)){
						Validator.AddError(getAttribute("name"), getAttribute("msg"));
					}
				}
				else{
					value = Validator.getValue(getAttribute("name"));
				}
				if(typeof(_dataType) == "object" || typeof(Validator[_dataType]) == "undefined")  continue;
				Validator.ClearState(Validator.getObject(getAttribute("name")));
				if(getAttribute("require") == "false" && value == "") continue;
				var isChecked = false;
				switch(_dataType){
				
					case "IdCard" :
					case "Date" :
					case "Repeat" :
					case "Range" :
					case "Compare" :
					case "Custom" :
					case "Group" : 
					case "Limit" :
					case "LimitB" :
					case "SafeString" :
					case "Filter" :
						if(!eval(Validator[_dataType]))	{			
							// start edit -- 2006-04-06
							if(!nameArray.contains(getAttribute("name"))){
								Validator.AddError(getAttribute("name"), getAttribute("msg"));
								nameArray = nameArray.concat(getAttribute("name"));								
							}
							// end edit -- 2006-04-06
						}
						break;
					default :
						if(!Validator[_dataType].test(value)){
							// start edit -- 2006-04-06
							if(!nameArray.contains(getAttribute("name"))){
								Validator.AddError(getAttribute("name"), getAttribute("msg"));
								nameArray = nameArray.concat(getAttribute("name"));								
							}
							// end edit -- 2006-04-06
						}
						break;
				}
			}
		}
		if(Validator.ErrorMessage.length > 1){
			// start edit 2006-4-10
			if(mode == null){
				mode = 2;
			}
			// end edit 2006-4-10
			mode = mode || 1;
			var errCount = Validator.ErrorItem.length;
			switch(mode){
			case 2 :
				for(var i=1;i<errCount;i++)
					Validator.ErrorItem[i].style.color = "red";
			case 1 :
				alert(Validator.ErrorMessage.join("\n"));
				// when error item is fckeditor,this code is error.
				//Validator.ErrorItem[1].focus();
				break;
			case 3 :
				for(var i=1;i<errCount;i++){
					try{
						var span = document.createElement("SPAN");
						span.id = "__ErrorMessagePanel";
						span.style.color = "red";
						Validator.ErrorItem[i].parentNode.appendChild(span);
						span.innerHTML = Validator.ErrorMessage[i].replace(/\d+:/,"*");
					}
					catch(e){alert(e.description);}
				}
				//Validator.ErrorItem[1].focus();
				break;
			default :
				alert(Validator.ErrorMessage.join("\n"));
				break;
			}
			return false;
		}
		return true;
	},
	getValue : function(fieldname){
		return this.getObject(fieldname).value.trim();
	},
	getObject : function(fieldname){
		var tmpObj = this.obj[fieldname];
		if(typeof(tmpObj) == "undefined"){
			alert("不存在名称为" + fieldname + "的表单项");
			return false;
		}
		try{
			return (tmpObj.length && !tmpObj.tagName)?tmpObj[tmpObj.length-1] : tmpObj;
		}catch(ie){
			alert("不存在名称为" + fieldname + "的表单项\nerror:" + ie.description);
		}
	},
	limit : function(len,min, max){
		min = min || 0;
		max = max || Number.MAX_VALUE;
		return min <= len && len <= max;
	},
	LenB : function(str){
		return str.replace(/[^\x00-\xff]/g,"**").length;
	},
	ClearState : function(elem){
		with(elem){
			if(style.color == "red")
				style.color = "";
			var lastNode = parentNode.childNodes[parentNode.childNodes.length-1];
			if(lastNode.id == "__ErrorMessagePanel")
				parentNode.removeChild(lastNode);
		}
	},
	AddError : function(fieldname, str){		
		Validator.ErrorItem[Validator.ErrorItem.length] = Validator.getObject(fieldname);
		Validator.ErrorMessage[Validator.ErrorMessage.length] = Validator.ErrorMessage.length + ":" + str;
	},
	Exec : function(op, reg){
		return new RegExp(reg,"g").test(op);
	},
	compare : function(op1,operator,op2){
		switch (operator) {
			case "NotEqual":
				return (op1 != op2);
			case "GreaterThan":
				return (op1 > op2);
			case "GreaterThanEqual":
				return (op1 >= op2);
			case "LessThan":
				return (op1 < op2);
			case "LessThanEqual":
				return (op1 <= op2);
			default:
				return (op1 == op2);            
		}
	},
	MustChecked : function(name, min, max){
		var groups = this.ErrorItem[0][name];
		var hasChecked = 0;
		min = min || 1;
		max = max || groups.length;
		for(var i=groups.length-1;i>=0;i-=1)
			if(groups[i].checked) hasChecked++;
		return min <= hasChecked && hasChecked <= max;
	},
	DoFilter : function(input, filter){
return new RegExp("^.+\.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(input);
	},
	IsIdCard : function(number){
		var date, Ai;
		var verify = "10x98765432";
		var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
		var area = ['','','','','','','','','','','','北京','天津','河北','山西','内蒙古','','','','','','辽宁','吉林','黑龙江','','','','','','','','上海','江苏','浙江','安微','福建','江西','山东','','','','河南','湖北','湖南','广东','广西','海南','','','','重庆','四川','贵州','云南','西藏','','','','','','','陕西','甘肃','青海','宁夏','新疆','','','','','','台湾','','','','','','','','','','香港','澳门','','','','','','','','','国外'];
		var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/i);
		if(re == null) return false;
		if(re[1] >= area.length || area[re[1]] == "") return false;
		if(re[2].length == 12){
			Ai = number.substr(0, 17);
			date = [re[9], re[10], re[11]].join("-");
		}
		else{
			Ai = number.substr(0, 6) + "19" + number.substr(6);
			date = ["19" + re[4], re[5], re[6]].join("-");
		}
		if(!this.IsDate(date, "ymd")) return false;
		var sum = 0;
		for(var i = 0;i<=16;i++){
			sum += Ai.charAt(i) * Wi[i];
		}
		Ai +=  verify.charAt(sum%11);
		return (number.length ==15 || number.length == 18 && number == Ai);
	},
	IsDate : function(op, formatString){
		formatString = formatString || "ymd";
		var m, year, month, day;
		switch(formatString){
			case "ymd" :
				m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
				if(m == null ) return false;
				day = m[6];
				month = m[5]*1;
				year =  (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
				break;
			case "dmy" :
				m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
				if(m == null ) return false;
				day = m[1];
				month = m[3]*1;
				year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
				break;
			default :
				break;
		}
		if(!parseInt(month)) return false;
		month = month==0 ?12:month;
		var date = new Date(year, month-1, day);
        return (typeof(date) == "object" && year == date.getFullYear() && month == (date.getMonth()+1) && day == date.getDate());
		function GetFullYear(y){return ((y<30 ? "20" : "19") + y)|0;}
	}
 }
