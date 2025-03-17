/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ 
/*    */ public class DownloadRegistration
/*    */ {
/*    */   private long invalidationTime;
/*    */   
/*    */   private DownloadRegistration(long maxValidity) {
/* 10 */     this.invalidationTime = System.currentTimeMillis() + maxValidity;
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 14 */     return (System.currentTimeMillis() <= this.invalidationTime);
/*    */   }
/*    */   
/*    */   public static DownloadRegistration createInstance(ClientContext context, String urlpath, long maxValidity) {
/* 18 */     DownloadRegistration instance = new DownloadRegistration(maxValidity);
/* 19 */     DownloadRegistrationProvider provider = DownloadRegistrationProvider.getInstance(context);
/* 20 */     provider.setDownloadRegistration(urlpath, instance);
/* 21 */     return instance;
/*    */   }
/*    */   
/*    */   public static DownloadRegistration getInstance(ClientContext context, String urlpath) {
/* 25 */     DownloadRegistrationProvider provider = DownloadRegistrationProvider.getInstance(context);
/* 26 */     return provider.getDownloadRegistration(urlpath);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\DownloadRegistration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */