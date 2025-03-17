/*    */ package com.eoos.gm.tis2web.sas.client.system;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageDataProvider
/*    */ {
/* 11 */   private static ImageDataProvider instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized ImageDataProvider getInstance() {
/* 17 */     if (instance == null) {
/* 18 */       instance = new ImageDataProvider();
/*    */     }
/* 20 */     return instance;
/*    */   }
/*    */   
/*    */   public byte[] getData(String name) throws Exception {
/* 24 */     InputStream is = getClass().getClassLoader().getResourceAsStream("com/eoos/gm/tis2web/sas/client/system/resource/" + name);
/* 25 */     return StreamUtil.readFully(is);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\ImageDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */