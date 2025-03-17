/*    */ package com.eoos.gm.tis2web.sps.common.gtwo;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DownloadServer
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private URL url;
/*    */   private boolean secured;
/*    */   
/*    */   public DownloadServer(URL url, boolean isSecured) {
/* 18 */     this.url = url;
/* 19 */     this.secured = isSecured;
/*    */   }
/*    */   
/*    */   public URL getURL() {
/* 23 */     return this.url;
/*    */   }
/*    */   
/*    */   public boolean isSecured() {
/* 27 */     return this.secured;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 31 */     if (this == obj)
/* 32 */       return true; 
/* 33 */     if (obj instanceof DownloadServer) {
/* 34 */       DownloadServer server = (DownloadServer)obj;
/* 35 */       boolean ret = Util.equals(this.url, server.url);
/* 36 */       ret = (ret && this.secured == server.secured);
/* 37 */       return ret;
/*    */     } 
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 44 */     int ret = getClass().hashCode();
/* 45 */     ret = HashCalc.addHashCode(ret, this.url);
/* 46 */     ret = HashCalc.addHashCode(ret, this.secured);
/* 47 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 51 */     return String.valueOf(this.url) + (this.secured ? "(secured)" : "");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\DownloadServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */