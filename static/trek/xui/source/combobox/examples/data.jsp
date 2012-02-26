<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%
	String str = request.getParameter("callback");
if(str !=null){	
out.println(str+"({totalCount:1000,data:[{sex:\"1\",age:\"2222123\",birthday:\"2008-10-11\",name:\"1111\"},{sex:\"2\",age:\"2222\",birthday:\"2008-10-11\",name:\"2222\"},{sex:\"1\",age:\"1111\",birthday:\"2008-10-11\",name:\"张三\"},{sex:\"1\",age:\"1111\",birthday:\"2008-10-11\",name:\"李四\"},{sex:\"2\",age:\"2222\",birthday:\"2008-10-11\",name:\"2222\"}]})" );
}else{
out.println("{totalCount:1000,data:[{sex:\"111222\",age:\"1111\",birthday:\"2008-10-1\",name:\"1111\"},{sex:\"222\",age:\"2222\",birthday:\"2008-10-2\",name:\"2222\"},{sex:\"333\",age:\"1111\",birthday:\"2008-10-3\",name:\"张三\"},{sex:\"444\",age:\"1111\",birthday:\"2008-10-4\",name:\"李四\"},{sex:\"555\",age:\"2222\",birthday:\"2008-10-5\",name:\"2222\"}]}" );
}



%>