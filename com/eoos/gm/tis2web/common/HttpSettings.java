/*    */ package com.eoos.gm.tis2web.common;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.net.URI;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class HttpSettings
/*    */ {
/* 11 */   private static Map instances = new HashMap<Object, Object>();
/*    */   
/* 13 */   private String cookie = null;
/*    */   
/* 15 */   private URI uri = null;
/*    */   
/*    */   private HttpSettings(URI uri) {
/* 18 */     this.uri = uri;
/*    */   }
/*    */   
/*    */   public static synchronized HttpSettings getInstance(URI uri) {
/* 22 */     HttpSettings instance = (HttpSettings)instances.get(uri);
/* 23 */     if (instance == null) {
/* 24 */       instance = new HttpSettings(uri);
/* 25 */       instances.put(uri, instance);
/*    */     } 
/* 27 */     return instance;
/*    */   }
/*    */   
/*    */   public String getCookie() {
/* 31 */     String ret = this.cookie;
/* 32 */     if (ret == null) {
/* 33 */       URI uri = Util.shortenPath(this.uri);
/* 34 */       if (uri != null)
/*    */       {
/* 36 */         ret = getInstance(uri).getCookie();
/*    */       }
/*    */     } 
/* 39 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCookie(String cookie) {
/* 44 */     this.cookie = cookie;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\common\HttpSettings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */