/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.searchlog.ui.html.search;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import java.io.File;
/*    */ import java.net.URL;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class RemoteFile
/*    */   implements Comparable
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger(RemoteFile.class);
/*    */   private File file;
/*    */   private URL url;
/*    */   
/*    */   public RemoteFile(File file, URL url) {
/* 16 */     this.file = file;
/* 17 */     this.url = url;
/*    */   }
/*    */   
/*    */   public File getFile() {
/* 21 */     return this.file;
/*    */   }
/*    */   
/*    */   public URL getURL() {
/* 25 */     return this.url;
/*    */   }
/*    */   
/*    */   public int compareTo(Object o) {
/*    */     try {
/* 30 */       RemoteFile other = (RemoteFile)o;
/* 31 */       int ret = this.url.toString().compareTo(other.getURL().toString());
/* 32 */       if (ret == 0) {
/* 33 */         ret = this.file.getAbsoluteFile().compareTo(other.getFile().getAbsoluteFile());
/*    */       }
/* 35 */       return ret;
/* 36 */     } catch (Exception e) {
/* 37 */       log.warn("unable to compare, returning 0 - exception:" + e, e);
/* 38 */       return 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 43 */     if (this == obj)
/* 44 */       return true; 
/* 45 */     if (obj instanceof RemoteFile) {
/* 46 */       RemoteFile rf = (RemoteFile)obj;
/* 47 */       boolean ret = this.url.toString().equals(rf.getURL().toString());
/* 48 */       ret = (ret && this.file.getAbsoluteFile().equals(rf.file.getAbsoluteFile()));
/* 49 */       return ret;
/*    */     } 
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 58 */     int ret = RemoteFile.class.hashCode();
/* 59 */     ret = HashCalc.addHashCode(ret, this.url.toString());
/* 60 */     ret = HashCalc.addHashCode(ret, this.file);
/*    */     
/* 62 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\searchlo\\ui\html\search\RemoteFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */