<%@ page contentType="text/html; charset=utf-8" %> 
<%@ page import="java.io.*"%> 
<%@ page import="java.util.*"%> 
<%@ page import="javax.servlet.*"%> 
<%@ page import="javax.servlet.http.*"%> 
<%
System.out.println("flash **************** in");
String rootPath;
DataInputStream in = null;
FileOutputStream fileOut = null;
String serverName = request.getServerName();
String realPath = request.getRealPath("/");
rootPath = realPath + "/upload/"; 
String contentType = request.getContentType(); 
try{ 
if(contentType.indexOf("multipart/form-data") >= 0){
in = new DataInputStream(request.getInputStream()); 
int formDataLength = request.getContentLength(); 
byte dataBytes[] = new byte[formDataLength]; 
int byteRead = 0; 
int totalBytesRead = 0;
while(totalBytesRead < formDataLength){
byteRead = in.read(dataBytes,totalBytesRead,formDataLength); 
totalBytesRead += byteRead; 
} 
String file = new String(dataBytes);
String saveFile = file.substring(file.indexOf("filename=\"") + 10); 
saveFile = saveFile.substring(0,saveFile.indexOf("\n")); 
saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\"")); 
int lastIndex = contentType.lastIndexOf("="); 
String boundary = contentType.substring(lastIndex + 1,contentType.length()); 
String fileName = rootPath + saveFile;
int pos; 
pos = file.indexOf("filename=\""); 
pos = file.indexOf("\n",pos) + 1; 
pos = file.indexOf("\n",pos) + 1; 
pos = file.indexOf("\n",pos) + 1; 
int boundaryLocation = file.indexOf(boundary,pos) - 4; 
int startPos = ((file.substring(0,pos)).getBytes()).length;
int endPos = ((file.substring(0,boundaryLocation)).getBytes()).length; 
File checkFile = new File(fileName); 
if(checkFile.exists()){
return;
} 
File fileDir = new File(rootPath); 
if(!fileDir.exists()){ 
fileDir.mkdirs(); 
}
fileOut = new FileOutputStream(fileName);
fileOut.write(dataBytes,startPos,(endPos - startPos)); 
fileOut.close(); 
System.out.print("{isSuccess:true,data:{fileId:'12',name:'"+fileName+"',url:''}}");
}else{ 
String content = request.getContentType(); 
} 
}catch(Exception ex){ 
throw new ServletException(ex.getMessage()); 
} 
%> 
