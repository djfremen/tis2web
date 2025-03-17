/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ 
/*    */ public class DownloadRegistrationProvider
/*    */ {
/* 12 */   private Map map = new ConcurrentHashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   private DownloadRegistrationProvider(ClientContext context) {}
/*    */ 
/*    */   
/*    */   public static DownloadRegistrationProvider getInstance(ClientContext context) {
/* 19 */     synchronized (context.getLockObject()) {
/* 20 */       DownloadRegistrationProvider instance = (DownloadRegistrationProvider)context.getObject(DownloadRegistrationProvider.class);
/* 21 */       if (instance == null) {
/* 22 */         instance = new DownloadRegistrationProvider(context);
/* 23 */         context.storeObject(DownloadRegistrationProvider.class, instance);
/*    */       } 
/* 25 */       return instance;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static String normalizeKey(String urlpath) {
/* 31 */     urlpath = urlpath.toLowerCase(Locale.ENGLISH);
/* 32 */     urlpath = StringUtilities.replace(urlpath, "/", "");
/* 33 */     return urlpath;
/*    */   }
/*    */   
/*    */   public DownloadRegistration getDownloadRegistration(String urlpath) {
/* 37 */     return (DownloadRegistration)this.map.get(normalizeKey(urlpath));
/*    */   }
/*    */   
/*    */   public void setDownloadRegistration(String urlpath, DownloadRegistration downloadRegistration) {
/* 41 */     this.map.put(normalizeKey(urlpath), downloadRegistration);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\DownloadRegistrationProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */