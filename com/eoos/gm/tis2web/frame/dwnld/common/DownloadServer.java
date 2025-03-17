/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DownloadServer
/*    */   implements Serializable, IDownloadServer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private URL url;
/*    */   private boolean isAkamai;
/* 17 */   private String description = null;
/*    */   
/*    */   public DownloadServer(URL url, boolean isAkamai, String description) {
/* 20 */     this.url = url;
/* 21 */     this.isAkamai = isAkamai;
/* 22 */     this.description = description;
/*    */   }
/*    */   
/*    */   public URL getURL() {
/* 26 */     return this.url;
/*    */   }
/*    */   
/*    */   public boolean isAkamai() {
/* 30 */     return this.isAkamai;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 34 */     if (this == obj)
/* 35 */       return true; 
/* 36 */     if (obj instanceof DownloadServer) {
/* 37 */       DownloadServer server = (DownloadServer)obj;
/* 38 */       boolean ret = Util.equals(this.url, server.url);
/* 39 */       ret = (ret && this.isAkamai == server.isAkamai);
/* 40 */       return ret;
/*    */     } 
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 47 */     int ret = getClass().hashCode();
/* 48 */     ret = HashCalc.addHashCode(ret, this.url);
/* 49 */     ret = HashCalc.addHashCode(ret, this.isAkamai);
/* 50 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     return String.valueOf(this.url) + (this.isAkamai ? "(akamai)" : "");
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 58 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\DownloadServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */