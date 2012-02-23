

<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<%
	String str = request.getParameter("node");
	if(str.equals("-1")){	
out.println("[{iconCls:\"myMail-node\",folderType:\"mySelf\",leaf:true,isTarget:true,id:\"-1/280119\",text:\"ccc\"},{iconCls:\"myMail-node\",folderType:\"mySelf\",leaf:false,isTarget:true,id:\"-1/97832\",text:\"online\"}]");
	}else if(str.equals("-1/97832")){
out.println("  [{folderType:\"mySelf\",iconCls:\"myMail-node\",leaf:false,isTarget:true,id:\"-1/97832/280118\",text:\"aa\"},{folderType:\"mySelf\",iconCls:\"myMail-node\",leaf:true,isTarget:true,id:\"-1/97832/280121\",text:\"bbb\"},{folderType:\"mySelf\",iconCls:\"myMail-node\",leaf:true,isTarget:true,id:\"-1/97832/280122\",text:\"ddfdfd\"}]");
	}else if(str.equals("-1/97832/280118")){
out.println(" [{iconCls:\"myMail-node\",leaf:true,folderType:\"mySelf\",isTarget:true,id:\"-1/97832/280118/280123\",text:\"1111\"},{iconCls:\"myMail-node\",leaf:true,folderType:\"mySelf\",isTarget:true,id:\"-1/97832/280118/280124\",text:\"2222\"}]");
	}
%>
