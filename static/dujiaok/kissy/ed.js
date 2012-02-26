var loadediter = function (container){
	var KE = KISSY.Editor;
	KE(container, {
            attachForm:true,
            baseZIndex:10000,

            pluginConfig: {
                "font-size":{
                    items:[
                        {
                            value:"14px",
                            attrs:{
                                style:'position: relative; border: 1px solid #DDDDDD; margin: 2px; padding: 2px;'
                            },
                            name:"" +
                                    " <span style='font-size:14px'>标准</span>" +
                                    "<span style='position:absolute;top:1px;right:3px;'>14px</span>"
                        },
                        {
                            value:"16px",
                            attrs:{
                                style:'position: relative; border: 1px solid #DDDDDD; margin: 2px; padding: 2px;'
                            },
                            name:"" +
                                    " <span style='font-size:16px'>大</span>" +
                                    "<span style='position:absolute;top:1px;right:3px;'>16px</span>"
                        },
                        {
                            value:"18px",
                            attrs:{
                                style:'position: relative; border: 1px solid #DDDDDD; margin: 2px; padding: 2px;'
                            },
                            name:"" +
                                    " <span style='font-size:18px'>特大</span>" +
                                    "<span style='position:absolute;top:1px;right:3px;'>18px</span>"
                        },
                        {
                            value:"20px",
                            attrs:{
                                style:'position: relative; border: 1px solid #DDDDDD; margin: 2px; padding: 2px;'
                            },
                            name:"" +
                                    " <span style='font-size:20px'>极大</span>" +
                                    "<span style='position:absolute;top:1px;right:3px;'>20px</span>"
                        }
                    ],
                    width:"115px"
                }
                ,"font-family":{
                    items:[
                        {name:"宋体",value:"SimSun"},
                        {name:"黑体",value:"SimHei"},
                        {name:"楷体",value:"KaiTi_GB2312"},
                        {name:"微软雅黑",value:"Microsoft YaHei"},
                        {name:"Times New Roman",value:"Times New Roman"},
                        {name:"Arial",value:"Arial"},
                        {name:"Verdana",value:"Verdana"}
                    ]
                },
                "draft":{
                    interval:5,
                    limit:10,
                    helpHtml:  "<div " +
                            "style='width:200px;'>" +
                            "<div style='padding:5px;'>草稿箱能够自动保存您最新编辑的内容," +
                            "如果发现内容丢失" +
                            "请选择恢复编辑历史</div></div>"
                },
                "resize":{
                    direction:["y"]
                }
            }
        }).use("htmldataprocessor,enterkey,clipboard," +
                "sourcearea,checkbox-sourcearea,preview," +
                "separator," +
                "undo,separator,removeformat,font,color,separator," +
                "list,indent,justify,separator,link,image," +
                "separator,table,resize,draft,maximize"
                , function() {
                    var self = this,
                            htmlDataProcessor = self.htmlDataProcessor,
                            dataFilter = htmlDataProcessor && htmlDataProcessor.dataFilter;
                    if (dataFilter) {
                        dataFilter.addRules({
                            attributes:{
                                style:function(value) {
                                    value = value.replace(/background-color\s*:[^;]+(;|$)/g, "")
                                            .replace(/background\s*:[^;]+(;|$)/g, "");
                                    if (!value) return false;
                                    return value;
                                }
                            }
                        });
                    }
                });
	
	
	}





 