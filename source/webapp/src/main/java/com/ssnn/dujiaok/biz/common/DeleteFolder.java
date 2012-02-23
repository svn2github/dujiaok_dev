package com.ssnn.dujiaok.biz.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DeleteFolder {

   public static void main(String[] args) {
      
      String folder = "E:/workspace/java/dujiaok/dujiaok/doc/Travel-ALL" ;
      deleteFolder(folder , true) ;
   }
   
   static void deleteFolder(String folder , boolean realDelete){
      File file = new File(folder) ;
      if(file.isDirectory()){
         File[] files = file.listFiles() ;
         for(File f : files){
            if(".svn".equals(f.getName())){
               System.out.println(f.getPath());
               try{
                  if(realDelete)
                     FileUtils.deleteDirectory(f) ;   
               }catch(IOException e){
                  System.out.println(e.getMessage());
               }
            }else{
               
               deleteFolder(f.getPath() , realDelete) ;
            }
         }
      }
   }
}