xui.widgets.Widget=function(a){this.attributes=a;if(typeof a=="string"){this.attributes={id:a}}this.id=this.attributes.id};xui.widgets.Widget.prototype={on:function(b,a,c){xui.util.Event.on(this.id,b,a,c)},getDom:function(){return document.getElementById(this.id)}};xui.widgets.TextBox=function(a){xui.widgets.TextBox.superclass.constructor.call(this,a)};xui.extend(xui.widgets.TextBox,xui.widgets.Widget,{});xui.widgets.Password=function(a){xui.widgets.Password.superclass.constructor.call(this,a)};xui.extend(xui.widgets.Password,xui.widgets.Widget,{});xui.widgets.TextArea=function(a){xui.widgets.TextArea.superclass.constructor.call(this,a)};xui.extend(xui.widgets.TextArea,xui.widgets.Widget,{});xui.widgets.PictureBox=function(a){xui.widgets.PictureBox.superclass.constructor.call(this,a)};xui.extend(xui.widgets.PictureBox,xui.widgets.Widget,{});xui.widgets.CheckBox=function(a){xui.widgets.CheckBox.superclass.constructor.call(this,a)};xui.extend(xui.widgets.CheckBox,xui.widgets.Widget,{on:function(b,a,c){xui.util.Event.on($N(this.id),b,a,c)}});xui.widgets.Select=function(a){xui.widgets.Select.superclass.constructor.call(this,a)};xui.extend(xui.widgets.Select,xui.widgets.Widget,{});xui.widgets.Radio=function(a){xui.widgets.Radio.superclass.constructor.call(this,a)};xui.extend(xui.widgets.Radio,xui.widgets.Widget,{on:function(b,a,c){xui.util.Event.on($N(this.id),b,a,c)}});xui.widgets.Button=function(a){xui.widgets.Button.superclass.constructor.call(this,a)};xui.extend(xui.widgets.Button,xui.widgets.Widget,{});xui.widgets.Link=function(a){xui.widgets.Link.superclass.constructor.call(this,a)};xui.extend(xui.widgets.Link,xui.widgets.Widget,{});