xui.util.DateUtil={toYYYYMMDD:function(c){var a=(c.getMonth()+1).toString();var b=(c.getDate()).toString();if(a.length<2){a="0"+a}if(b.length<2){b="0"+b}return c.getFullYear()+"-"+a+"-"+b},getYear:function(a){if(xui.lang.isString(a)){return parseInt(a.split("-")[0],10)}return a.getFullYear()},getMonth:function(a){if(xui.lang.isString(a)){return parseInt(a.split("-")[1],10)-1}return a.getMonth()},getMonthDay:function(a){if(xui.lang.isString(a)){return parseInt(a.split("-")[2],10)}return a.getDate()},getHours:function(a){return a.getHours()},getMinutes:function(a){return a.getMinutes()},isYYYYMMDD:function(b){if(!b||typeof b!="string"){return false}if(!(new RegExp(/^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/)).test(b)){return false}var e=this.getYear(b);if(e<1970){return false}var a=this.getMonth(b);if(a<0||a>11){return false}var c=this.getMonthDay(b);if(c<1||c>31){return false}return true},toDate:function(a){if(!this.isYYYYMMDD(a)){return null}return new Date(this.getYear(a),this.getMonth(a),this.getMonthDay(a))},compare:function(b,a){if(!b||!a){return null}var d=(typeof b=="string")?this.toDate(b):b;var c=(typeof a=="string")?this.toDate(a):a;return Date.parse(d)-Date.parse(c)}};